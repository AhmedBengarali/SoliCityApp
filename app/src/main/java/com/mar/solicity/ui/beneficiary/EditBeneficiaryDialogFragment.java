package com.mar.solicity.ui.beneficiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mar.solicity.R;
import com.mar.solicity.data.Beneficiary;


public class EditBeneficiaryDialogFragment  extends DialogFragment {
Button editBeneficiaryBtn;
private final Beneficiary beneficiary;
EditText beneficiaryEditTextName,beneficiaryEditTextPhone,beneficiaryEditTextAddress;
ProgressBar progressBar;
boolean isNameValid, isPhoneValid, isAddressValid;
private BeneficiaryViewModel EditviewModel;

    public EditBeneficiaryDialogFragment(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EditviewModel = ViewModelProviders.of(this).get(BeneficiaryViewModel.class);
        final View view = inflater.inflate(R.layout.fragment_edit_beneficiary_dialog, container, false);

        editBeneficiaryBtn = view.findViewById(R.id.button_edit_ben);
        beneficiaryEditTextName = view.findViewById(R.id.edit_Ben_name);
        beneficiaryEditTextPhone = view.findViewById(R.id.edit_Ben_phone);
        beneficiaryEditTextAddress = view.findViewById(R.id.edit_Ben_address);
        progressBar = view.findViewById(R.id.edit_ben_dialog_progressbar);

        beneficiaryEditTextName.setText(beneficiary.getBeneficiaryName());
        beneficiaryEditTextPhone.setText(beneficiary.getBeneficiaryPhone());
        beneficiaryEditTextAddress.setText(beneficiary.getBeneficiaryAddress());

        editBeneficiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = beneficiaryEditTextName.getText().toString();
                String phone = beneficiaryEditTextPhone.getText().toString();
                String address = beneficiaryEditTextAddress.getText().toString();

                // Check for a va
                if (beneficiaryEditTextName.getText().toString().isEmpty()) {
                    beneficiaryEditTextName.setError(getResources().getString(R.string.email_error));

                    isNameValid = false;
                }else{
                    isNameValid=true;
                }
                // Check for a valid Phone Number.
                if (beneficiaryEditTextPhone.getText().toString().isEmpty()){
                    beneficiaryEditTextPhone.setError(getResources().getString(R.string.phone_error));
                    isPhoneValid = false;
                }else if (beneficiaryEditTextPhone.getText().length() < 8) {
                    beneficiaryEditTextPhone.setError(getResources().getString(R.string.error_invalid_phone));
                    isPhoneValid = false;
                }else {
                    isPhoneValid = true;
                }

//                Check for a valid Address
                if (beneficiaryEditTextAddress.getText().toString().isEmpty()) {
                    beneficiaryEditTextAddress.setError(getResources().getString(R.string.address_error));

                    isAddressValid = false;
                }else{
                    isAddressValid=true;
                }
                if (isNameValid&&isPhoneValid&&isAddressValid){
                    beneficiaryEditTextAddress.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    progressBar.setVisibility(View.VISIBLE);

                    beneficiary.setBeneficiaryName(name);
                    beneficiary.setBeneficiaryPhone(phone);
                    beneficiary.setBeneficiaryAddress(address);
                    EditviewModel.updateBeneficiary(beneficiary);
                }
            }
        });


        EditviewModel.result().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if (o == null){
                    Toast.makeText(requireContext(), "Bénéficiaire Modifié", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(requireContext(), "Une erreur est survenue" , Toast.LENGTH_SHORT).show();
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
        this.setStyle(STYLE_NO_TITLE,android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
    }

}
