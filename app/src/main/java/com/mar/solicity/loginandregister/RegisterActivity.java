package com.mar.solicity.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mar.solicity.MainActivity;
import com.mar.solicity.R;


import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mEmail;
    EditText mPassword;
    Button mRegistrationBtn;
    TextView mLoginBtn;
    boolean  isEmailValid, isPasswordValid;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mEmail = findViewById(R.id.register_text_email);
        mPassword = findViewById(R.id.register_text_password);
        mRegistrationBtn = findViewById(R.id.button_register);
        mLoginBtn = findViewById(R.id.text_view_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressbar);

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        mRegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txt_email = mEmail.getText().toString().trim();
                 String txt_password = mPassword.getText().toString().trim();

                // Check for a valid email address.
                if (mEmail.getText().toString().isEmpty()) {
                    mEmail.setError(getResources().getString(R.string.email_error));
                    isEmailValid = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()) {
                    mEmail.setError(getResources().getString(R.string.error_invalid_email));
                    isEmailValid = false;
                } else {
                    isEmailValid = true;
                }

                // Check for a valid password.
                if (mPassword.getText().toString().isEmpty()) {
                    mPassword.setError(getResources().getString(R.string.password_error));
                    isPasswordValid = false;
                } else if (mPassword.getText().length() < 6) {
                    mPassword.setError(getResources().getString(R.string.error_invalid_password));
                    isPasswordValid = false;
                } else {
                    isPasswordValid = true;
                }

                if (isEmailValid && isPasswordValid) {
                    mPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(getApplicationContext(), "One Moment !", Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.VISIBLE);
                    // Register the User in The FireBase

                    fAuth.createUserWithEmailAndPassword(txt_email, txt_email).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("email", txt_email);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "An Error Has occurred ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
