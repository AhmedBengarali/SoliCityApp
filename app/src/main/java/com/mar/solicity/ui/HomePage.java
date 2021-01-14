package com.mar.solicity.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mar.solicity.MainActivity;
import com.mar.solicity.R;
import com.mar.solicity.loginandregister.LoginActivity;
import com.mar.solicity.ui.beneficiary.BeneficiaryFragment;
import com.mar.solicity.ui.donation.DonationFragment;
import com.mar.solicity.ui.product.ProductFragment;

public class HomePage extends Fragment {
 Button benFrag, productFrag, doneeFrag;



    public HomePage() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        benFrag = view.findViewById(R.id.Ben_frag);
        productFrag = view.findViewById(R.id.product_frag);
        doneeFrag = view.findViewById(R.id.donee_frag);

        benFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeneficiaryFragment beneficiaryFragment = new BeneficiaryFragment();
               getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment,beneficiaryFragment,"tag")
                .addToBackStack(null)
                .commit();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Gestion des Bénéficiaires");

            }
        });
        productFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductFragment productFragment= new ProductFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,productFragment,"tag1")
                        .addToBackStack(null)
                        .commit();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Gestion de Stock");

            }
        });
        doneeFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonationFragment donationFragment= new DonationFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,donationFragment,"tag2")
                        .addToBackStack(null)
                        .commit();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Gestion des Dons");
            }
        });
       /* myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mainActivity.getCurrentUser(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    return view;
    }

}
