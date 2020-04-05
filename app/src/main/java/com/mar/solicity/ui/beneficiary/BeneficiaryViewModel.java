package com.mar.solicity.ui.beneficiary;

import android.content.Context;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.mar.solicity.data.Beneficiary;


import java.util.ArrayList;
import java.util.Objects;

public class BeneficiaryViewModel extends ViewModel {
    private DatabaseReference dbBeneficiary = FirebaseDatabase.getInstance().getReference("Beneficiaries");

    private MutableLiveData<Boolean> _result = new MutableLiveData<>();
    @NotNull
    LiveData result() { return (LiveData)this._result; }

    private MutableLiveData<ArrayList<Beneficiary>> _beneficiarieslist = new MutableLiveData<>();
    @NotNull
    LiveData<ArrayList<Beneficiary>> beneficiariesList() { return _beneficiarieslist; }

     private MutableLiveData<Beneficiary> _beneficiariesliveitems = new MutableLiveData<>();
    @NotNull
    LiveData<Beneficiary> beneficiariesLiveitems() { return _beneficiariesliveitems; }


    void addBeneficiary(Beneficiary beneficiary){
        String id = dbBeneficiary.push().getKey();
        dbBeneficiary.child(id).setValue(beneficiary)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            _result.postValue(null);
                        }else{
                            _result.postValue(true);
                        }
                    }
                });

    }

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
            Beneficiary beneficiary = (Beneficiary)snapshot.getValue(Beneficiary.class);
            assert beneficiary != null;
            beneficiary.setBeneficiaryId(Objects.requireNonNull(snapshot.getKey()));
            _beneficiariesliveitems.postValue(beneficiary);

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dsnapshot, @Nullable String s) {
            Beneficiary beneficiary = (Beneficiary)dsnapshot.getValue(Beneficiary.class);
            assert beneficiary != null;
            beneficiary.setBeneficiaryId(Objects.requireNonNull(dsnapshot.getKey()));
            _beneficiariesliveitems.postValue(beneficiary);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dasnapshot) {
            Beneficiary beneficiary = (Beneficiary)dasnapshot.getValue(Beneficiary.class);
            assert beneficiary != null;
            beneficiary.setBeneficiaryId(Objects.requireNonNull(dasnapshot.getKey()));
            beneficiary.setDeleted(true);
            _beneficiariesliveitems.postValue(beneficiary);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String s) {}

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

    void getBeneficiaryUpdates(){
        dbBeneficiary.addChildEventListener(childEventListener);
    }

    void fetchBeneficiaries(){
        dbBeneficiary.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ArrayList<Beneficiary> beneficiaries = new ArrayList<>();
                    for (DataSnapshot datasnapshot: snapshot.getChildren()){
                        Beneficiary beneficiary = (Beneficiary)datasnapshot.getValue(Beneficiary.class);
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

    void updateBeneficiary(Beneficiary benef){

        dbBeneficiary.child(benef.getBeneficiaryId()).setValue(benef)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            _result.postValue(null);
                        }else{
                            _result.postValue(true);
                        }
                    }
                });
    }

    void deleteBeneficiary(Beneficiary beneficiary){
        String id = beneficiary.getBeneficiaryId();
        dbBeneficiary.child(id).setValue(null)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            _result.postValue(null);
                        }else{
                            _result.postValue(true);
                        }
                    }
                });
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        dbBeneficiary.removeEventListener(childEventListener);
    }

    public LiveData<ArrayList<Beneficiary>> getLiveData() {
        return getLiveData();
    }
}