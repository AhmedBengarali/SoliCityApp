package com.mar.solicity.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mar.solicity.MainActivity;
import com.mar.solicity.R;


public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn, forgetTextLink;
    boolean isEmailValid, isPasswordValid;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        initializeUI();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String lEmail = mEmail.getText().toString().trim();
                String lPassword = mPassword.getText().toString().trim();

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

                // Check for a valid email address and password.
                if (isEmailValid && isPasswordValid) {
                                mPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);

                    Toast.makeText(getApplicationContext(), "One Moment !", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);
                    fAuth.signInWithEmailAndPassword(lEmail, lPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
        forgetTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PasswordResetActivity.class));
            }
        });
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

    }

    private void initializeUI() {
        mEmail = findViewById(R.id.login_text_email);
        mPassword = findViewById(R.id.login_text_password);
        progressBar = findViewById(R.id.login_progressbar);
        mLoginBtn = findViewById(R.id.button_sign_in);
        mCreateBtn = findViewById(R.id.text_view_register);
        forgetTextLink = findViewById(R.id.text_view_forget_password);
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser!=null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
