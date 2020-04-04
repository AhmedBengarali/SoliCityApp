package com.mar.solicity.loginandregister;

import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.mar.solicity.R;

public class PasswordResetActivity extends AppCompatActivity {
    EditText editTextEmail;
    Button resetButton;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        fAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.reset_text_email);
        resetButton = findViewById(R.id.button_reset_password);
        progressBar = findViewById(R.id.progressbar);

        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String txtMail = editTextEmail.getText().toString().trim();
                if (editTextEmail.getText().toString().isEmpty()) {
                    editTextEmail.setError(getResources().getString(R.string.email_error));
                } else if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches()) {
                    editTextEmail.setError(getResources().getString(R.string.error_invalid_email));

                } else {
                    editTextEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    progressBar.setVisibility(View.VISIBLE);
                    fAuth.sendPasswordResetEmail(txtMail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PasswordResetActivity.this, "Reset Link Is Sent \n Please Check Your Link", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            editTextEmail.setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PasswordResetActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }
            }
        });




    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
