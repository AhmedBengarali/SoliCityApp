package com.mar.solicity.ui.beneficiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mar.solicity.R;

public class BeneficiaryFragment extends Fragment {
    FloatingActionButton addbtn;

    private BeneficiaryViewModel beneficiaryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        beneficiaryViewModel =
                ViewModelProviders.of(this).get(BeneficiaryViewModel.class);
        final View view = inflater.inflate(R.layout.fragment_beneficiary, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddBeneficiaryDialogFragment().show(BeneficiaryFragment.this.getChildFragmentManager(), "");

            }
        });

        return view;
    }

}
