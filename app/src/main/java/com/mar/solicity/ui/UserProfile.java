package com.mar.solicity.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mar.solicity.R;


public class UserProfile extends Fragment {

    TextView userName, email, phone, userRole;
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("Users");
    String userEmail;
    private static final String TAG = "User Profile";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_profile, container, false);


        userName = view.findViewById(R.id.username_textview);
        email = view.findViewById(R.id.email_textview);
        phone = view.findViewById(R.id.phone_textview);
        userRole = view.findViewById(R.id.user_role_textview);
        userEmail= fAuth.getCurrentUser().getEmail();
        Log.d(TAG, "userEmail: " + userEmail);

        dbUsers.addValueEventListener(new ValueEventListener() {
            String uName, uEmail,uPhone, uRole;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot uID: dataSnapshot.getChildren()){
                    if (uID.child("userEmail").getValue().equals(userEmail)){
                        uName = uID.child("userName").getValue(String.class);
                        uEmail = uID.child("userEmail").getValue(String.class);
                        uPhone = uID.child("userPhone").getValue(String.class);
                        uRole = uID.child("userRole").getValue(String.class);
                        break;
                    }
                }
                userName.setText(uName);
                email.setText(uEmail);
                phone.setText(uPhone);
                userRole.setText(uRole);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return view;

    }
}
