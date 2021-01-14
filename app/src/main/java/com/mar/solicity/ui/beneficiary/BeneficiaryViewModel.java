package com.mar.solicity.ui.beneficiary;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.mar.solicity.data.Beneficiary;


import java.util.ArrayList;
import java.util.Objects;

public class BeneficiaryViewModel extends ViewModel {


    private DatabaseReference dbBeneficiary = FirebaseDatabase.getInstance().getReference("Beneficiaries");

    private MutableLiveData<Boolean> _result = new MutableLiveData<>();

    @NotNull
    LiveData result() {
        return (LiveData) this._result;
    }

    private MutableLiveData<ArrayList<Beneficiary>> _beneficiarieslist = new MutableLiveData<>();

    @NotNull
    LiveData<ArrayList<Beneficiary>> beneficiariesList() {
        return _beneficiarieslist;
    }

    void addBeneficiary(Beneficiary beneficiary) {
        String id = dbBeneficiary.push().getKey();
        dbBeneficiary.child(id).setValue(beneficiary)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            _result.postValue(null);
                        } else {
                            _result.postValue(true);
                        }
                    }
                });

    }

    void fetchBeneficiaries() {

        dbBeneficiary.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<Beneficiary> beneficiaries = new ArrayList<>();
                    beneficiaries.clear();
                    for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                        Beneficiary beneficiary = (Beneficiary) datasnapshot.getValue(Beneficiary.class);
                        assert beneficiary != null;
                        beneficiary.setBeneficiaryId(Objects.requireNonNull(datasnapshot.getKey()));
                        beneficiaries.add(beneficiary);
                    }

                    _beneficiarieslist.postValue(beneficiaries);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void searchBeneficiary(final String s) {
        dbBeneficiary.orderByChild("beneficiaryCIN")
                .startAt(s)
                .endAt(s+"\uf8ff")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    ArrayList<Beneficiary> beneficiaries = new ArrayList<>();
                    beneficiaries.clear();
                    for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                        Beneficiary beneficiary = (Beneficiary) datasnapshot.getValue(Beneficiary.class);
//                        assert beneficiary != null;
                        beneficiary.setBeneficiaryId(Objects.requireNonNull(datasnapshot.getKey()));
//                        if (beneficiary.getBeneficiaryCIN().equals(s)){
                            beneficiaries.add(beneficiary);
//                        }
                    }

                    _beneficiarieslist.postValue(beneficiaries);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    void updateBeneficiary(Beneficiary benef) {

        dbBeneficiary.child(benef.getBeneficiaryId()).setValue(benef)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            _result.postValue(null);
                        } else {
                            _result.postValue(true);
                        }
                    }
                });
    }

    void deleteBeneficiary(Beneficiary beneficiary) {
        String id = beneficiary.getBeneficiaryId();
        dbBeneficiary.child(id).setValue(null)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            _result.postValue(null);

                        } else {
                            _result.postValue(true);
                        }
                    }
                });
    }



    public LiveData<ArrayList<Beneficiary>> getLiveData() {
        return getLiveData();
    }
}