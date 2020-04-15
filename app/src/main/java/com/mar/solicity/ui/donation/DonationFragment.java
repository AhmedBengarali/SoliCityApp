package com.mar.solicity.ui.donation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mar.solicity.R;
import com.mar.solicity.data.Beneficiary;
import com.mar.solicity.data.Donation;
import com.mar.solicity.ui.beneficiary.BeneficiaryFragment;
import com.mar.solicity.ui.beneficiary.EditBeneficiaryDialogFragment;
import com.mar.solicity.ui.donation.RecyclerViewClickListener;

import java.util.ArrayList;

public class DonationFragment extends Fragment implements RecyclerViewClickListener {
    private RecyclerView recyclerView;
    private DonationAdapter donationAdapter;
    private DonationViewModel donationViewModel;
    AlertDialog.Builder builder;
    private EditText searchDonee;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        donationViewModel =
                ViewModelProviders.of(this).get(DonationViewModel.class);
        View view = inflater.inflate(R.layout.fragment_donation, container, false);

        searchDonee = view.findViewById(R.id.searchDonee);
        donationAdapter = new DonationAdapter();
        builder = new AlertDialog.Builder(requireContext());
        donationAdapter.listener = this;

        recyclerView = view.findViewById(R.id.recycler_view_donee);
        recyclerView.setAdapter(donationAdapter);

        donationViewModel.fetchDonees();
        donationViewModel.donationList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Donation>>() {
            @Override
            public void onChanged(ArrayList<Donation> donations) {
                donationAdapter.setDonations(donations);
            }
        });

        searchDonee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()){
                    donationViewModel.searchDonee(s.toString());
                }else {
                    donationViewModel.fetchDonees();
                }
            }
        });


        FloatingActionButton add_donee = view.findViewById(R.id.add_donee);
        add_donee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddDonationDialogFragment().show(DonationFragment.this.getChildFragmentManager(), "");


            }
        });
        return view;
    }

    @Override
    public void onRecyclerViewItemClicked(View view, final Donation donation) {

        switch (view.getId()) {
            case R.id.button_Edit_donee:
                new EditDonationDialogFragment(donation).show(DonationFragment.this.getChildFragmentManager(), "");
                break;

            case R.id.button_Delete_Donee:
                builder.setTitle(R.string.delete_confirmation);
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        donationViewModel.deleteDonees(donation);
                        donationAdapter.notifyDataSetChanged();
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
