package com.mar.solicity.ui.beneficiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mar.solicity.R;
import com.mar.solicity.data.Beneficiary;

import java.util.ArrayList;


public class BeneficiaryFragment extends Fragment implements RecyclerViewClickListener {
    private RecyclerView recyclerView;
    private BeneficiaryAdapter beneficiaryAdapter;
    private BeneficiaryViewModel beneficiaryViewModel;
    private EditText searchBeneficiary;
    AlertDialog.Builder builder;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_beneficiary, container, false);
        beneficiaryViewModel =
                ViewModelProviders.of(this).get(BeneficiaryViewModel.class);

        searchBeneficiary = view.findViewById(R.id.searchBen);
        beneficiaryAdapter = new BeneficiaryAdapter();
        builder = new AlertDialog.Builder(requireContext());
        beneficiaryAdapter.listener = this;

        recyclerView = view.findViewById(R.id.recycler_view_bene);
        recyclerView.setAdapter(beneficiaryAdapter);

        beneficiaryViewModel.fetchBeneficiaries();


        searchBeneficiary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()){
                    beneficiaryViewModel.searchBeneficiary(s.toString());
                }else {
                    beneficiaryViewModel.fetchBeneficiaries();
                }
            }
        });



        beneficiaryViewModel.beneficiariesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Beneficiary>>() {
            @Override
            public void onChanged(ArrayList<Beneficiary> ben) {
                beneficiaryAdapter.setBeneficiaries(ben);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.add_ben);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddBeneficiaryDialogFragment().show(BeneficiaryFragment.this.getChildFragmentManager(), "");


            }
        });

        return view;
    }

    @Override
    public void onRecyclerViewItemClicked(View view, final Beneficiary beneficiary) {

        switch (view.getId()) {
            case R.id.button_Edit_Ben:
                new EditBeneficiaryDialogFragment(beneficiary).show(BeneficiaryFragment.this.getChildFragmentManager(), "");
                break;

            case R.id.button_Delete_Ben:
                builder.setTitle(R.string.delete_confirmation);
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        beneficiaryViewModel.deleteBeneficiary(beneficiary);
                        beneficiaryAdapter.notifyDataSetChanged();
                    }

                }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }


}
