package com.mar.solicity.ui.donation;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mar.solicity.R;
import com.mar.solicity.data.Donation;

import java.util.Calendar;

public class EditDonationDialogFragment extends DialogFragment {
    private final Donation donation;
    Button addDonationBtn;
    ImageButton selectDate;
    EditText doneeTextName, doneeDate;
    ProgressBar progressBar;
    boolean isNameValid, isDateValid;
    private DonationViewModel viewModel;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public EditDonationDialogFragment(Donation donation) {
        this.donation = donation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this).get(DonationViewModel.class);
        final View view = inflater.inflate(R.layout.fragment_edit_donee_dialog, container, false);

        addDonationBtn = view.findViewById(R.id.button_save_edit_donee);
        selectDate = view.findViewById(R.id.button_Edit_date);
        doneeTextName = view.findViewById(R.id.edit_donee_name);
        doneeDate = view.findViewById(R.id.edit_donee_date);
        progressBar = view.findViewById(R.id.edit_donee_dialog_progressbar);

        doneeTextName.setText(donation.getDoneeName());
        doneeDate.setText(donation.getDate());

        doneeDate.setEnabled(false);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                doneeDate.setText(date);
            }
        };


        addDonationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doneeName = doneeTextName.getText().toString();
                String Date = doneeDate.getText().toString();

                // Check for a valid Name.
                if (doneeTextName.getText().toString().isEmpty()) {
                    doneeTextName.setError(getResources().getString(R.string.name_error));

                    isNameValid = false;
                } else {
                    isNameValid = true;
                }
                // Check for a valid date.
                if (doneeDate.getText().toString().isEmpty()) {
                    doneeDate.setError(getResources().getString(R.string.name_error));

                    isDateValid = false;
                } else {
                    isDateValid = true;
                }

                if (isNameValid && isDateValid) {
                    progressBar.setVisibility(View.VISIBLE);

                    donation.setDoneeName(doneeName);
                    donation.setDate(Date);
                    viewModel.updateDonation(donation);

                }

            }
        });
        viewModel.result().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if (o == null) {
                    Toast.makeText(requireContext(), "operation success  ", Toast.LENGTH_SHORT).show();
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
