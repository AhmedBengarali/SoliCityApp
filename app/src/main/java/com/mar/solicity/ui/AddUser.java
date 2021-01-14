package com.mar.solicity.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mar.solicity.MainActivity;
import com.mar.solicity.R;
import com.mar.solicity.data.Users;
import com.mar.solicity.loginandregister.LoginActivity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class AddUser extends Fragment {
    public static final String TAG = "TAG";
    EditText uEmail, uPassword, uName, uPhone;
    CircularProgressButton mRegistrationBtn;
    Spinner userRole;

    boolean isEmailValid, isPasswordValid, isNameValid, isPhoneValid;
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    ;
    //    ProgressBar progressBar;
    private DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("Users");
    String userID;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_user, container, false);

        uName = view.findViewById(R.id.register_text_name);
        uEmail = view.findViewById(R.id.register_text_email);
        uPhone = view.findViewById(R.id.register_text_phone);
        uPassword = view.findViewById(R.id.register_text_password);
        userRole = view.findViewById(R.id.user_role);
        mRegistrationBtn = view.findViewById(R.id.button_register);

        mRegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txt_email = uEmail.getText().toString().trim();
                final String txt_password = uPassword.getText().toString().trim();
                final String txt_name = uName.getText().toString().trim();
                final String txt_phone = uPhone.getText().toString().trim();
                final String txt_user_role = userRole.getSelectedItem().toString();

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
                    Toast.makeText(getActivity(), "One Moment !", Toast.LENGTH_SHORT).show();

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
                                userInfo.setUserRole(txt_user_role);
                                dbUsers.child(userID).setValue(userInfo);
                                Toast.makeText(getActivity(), "User Created Successfully", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getActivity(), "An Error Has occurred ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }
            }
        });


        return view;

    }


}
