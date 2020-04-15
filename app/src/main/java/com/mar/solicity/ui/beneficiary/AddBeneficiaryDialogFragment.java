package com.mar.solicity.ui.beneficiary;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mar.solicity.R;
import com.mar.solicity.data.Beneficiary;


public class AddBeneficiaryDialogFragment extends DialogFragment {
    Button addBeneficiaryBtn;
    EditText beneficiaryTextName, beneficiaryTextCin, beneficiaryTextPhone, beneficiaryTextAddress;
    ProgressBar progressBar;
    boolean isNameValid, isCinValid, isPhoneValid, isAddressValid;
    private BeneficiaryViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this).get(BeneficiaryViewModel.class);
        final View view = inflater.inflate(R.layout.fragment_add_beneficiary_dialog, container, false);

        addBeneficiaryBtn = view.findViewById(R.id.button_add_ben);
        beneficiaryTextName = view.findViewById(R.id.edit_text_Ben_name);
        beneficiaryTextCin = view.findViewById(R.id.edit_text_Ben_cin);
        beneficiaryTextPhone = view.findViewById(R.id.edit_text_Ben_phone);
        beneficiaryTextAddress = view.findViewById(R.id.edit_text_Ben_address);
        progressBar = view.findViewById(R.id.add_ben_dialog_progressbar);



        addBeneficiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = beneficiaryTextName.getText().toString();
                String cin = beneficiaryTextCin.getText().toString();
                String phone = beneficiaryTextPhone.getText().toString();
                String address = beneficiaryTextAddress.getText().toString();

                // Check for a valid Name.
                if (beneficiaryTextName.getText().toString().isEmpty()) {
                    beneficiaryTextName.setError(getResources().getString(R.string.name_error));

                    isNameValid = false;
                } else {
                    isNameValid = true;
                }
                // Check for a valid CIN Number.
                if (beneficiaryTextCin.getText().toString().isEmpty()) {
                    beneficiaryTextCin.setError(getResources().getString(R.string.cin_error));
                    isCinValid = false;
                } else if (beneficiaryTextCin.getText().length() < 8 || beneficiaryTextCin.getText().length() > 8) {
                    beneficiaryTextCin.setError(getResources().getString(R.string.error_invalid_cin));
                    isCinValid = false;
                } else {
                    isCinValid = true;
                }

                // Check for a valid Phone Number.
                if (beneficiaryTextPhone.getText().toString().isEmpty()) {
                    beneficiaryTextPhone.setError(getResources().getString(R.string.phone_error));
                    isPhoneValid = false;
                } else if (beneficiaryTextPhone.getText().length() < 8) {
                    beneficiaryTextPhone.setError(getResources().getString(R.string.error_invalid_phone));
                    isPhoneValid = false;
                } else {
                    isPhoneValid = true;
                }

//                Check for a valid Address
                if (beneficiaryTextAddress.getText().toString().isEmpty()) {
                    beneficiaryTextAddress.setError(getResources().getString(R.string.address_error));

                    isAddressValid = false;
                } else {
                    isAddressValid = true;
                }

                if (isNameValid && isCinValid && isPhoneValid && isAddressValid) {
                    beneficiaryTextAddress.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    progressBar.setVisibility(View.VISIBLE);


                    Beneficiary beneficiary = new Beneficiary();
                    beneficiary.setBeneficiaryName(name);
                    beneficiary.setBeneficiaryCIN(cin);
                    beneficiary.setBeneficiaryPhone(phone);
                    beneficiary.setBeneficiaryAddress(address);
                    viewModel.addBeneficiary(beneficiary);
                }


            }
        });
        viewModel.result().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if (o == null) {
                    Toast.makeText(requireContext(), "Bénéficiaires Ajouté", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                dismiss();

            }
        });


        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
    }


}
