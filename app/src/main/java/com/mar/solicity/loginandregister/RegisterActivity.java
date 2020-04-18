package com.mar.solicity.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.mar.solicity.MainActivity;
import com.mar.solicity.R;
import com.mar.solicity.data.Users;


import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText uEmail, uPassword, uName, uPhone;
    CircularProgressButton mRegistrationBtn;

    boolean isEmailValid, isPasswordValid, isNameValid, isPhoneValid;
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    ;
    //    ProgressBar progressBar;
    private DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("Users");
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        uName = findViewById(R.id.register_text_name);
        uEmail = findViewById(R.id.register_text_email);
        uPhone = findViewById(R.id.register_text_phone);
        uPassword = findViewById(R.id.register_text_password);
        mRegistrationBtn = findViewById(R.id.button_register);


//        progressBar = findViewById(R.id.progressbar);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        mRegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txt_email = uEmail.getText().toString().trim();
                final String txt_password = uPassword.getText().toString().trim();
                final String txt_name = uName.getText().toString().trim();
                final String txt_phone = uPhone.getText().toString().trim();

                // Check for a valid email address.
                if (uName.getText().toString().isEmpty()) {
                    uName.setError(getResources().getString(R.string.email_error));
                    isNameValid = false;
                } else {
                    isNameValid = true;
                }

                // Check for a valid email address.
                if (uEmail.getText().toString().isEmpty()) {
                    uEmail.setError(getResources().getString(R.string.email_error));
                    isEmailValid = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(uEmail.getText().toString()).matches()) {
                    uEmail.setError(getResources().getString(R.string.error_invalid_email));
                    isEmailValid = false;
                } else {
                    isEmailValid = true;
                }
                // Check for a valid Phone Number.
                if (uPhone.getText().toString().isEmpty()) {
                    uPhone.setError(getResources().getString(R.string.phone_error));
                    isPhoneValid = false;
                } else if (uPhone.getText().length() < 8) {
                    uPhone.setError(getResources().getString(R.string.error_invalid_phone));
                    isPhoneValid = false;
                } else {
                    isPhoneValid = true;
                }


                // Check for a valid password.
                if (uPassword.getText().toString().isEmpty()) {
                    uPassword.setError(getResources().getString(R.string.password_error));
                    isPasswordValid = false;
                } else if (uPassword.getText().length() < 6) {
                    uPassword.setError(getResources().getString(R.string.error_invalid_password));
                    isPasswordValid = false;
                } else {
                    isPasswordValid = true;
                }

                if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {
                    uPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(getApplicationContext(), "One Moment !", Toast.LENGTH_SHORT).show();

//                    progressBar.setVisibility(View.VISIBLE);
                    // Register the User in The FireBase

                    fAuth.createUserWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userID = fAuth.getCurrentUser().getUid();
                                Users userInfo = new Users();
                                userInfo.setUserName(txt_name);
                                userInfo.setUserEmail(txt_email);
                                userInfo.setUserPhone(txt_phone);
                                dbUsers.child(userID).setValue(userInfo);
                                Toast.makeText(RegisterActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "An Error Has occurred ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
