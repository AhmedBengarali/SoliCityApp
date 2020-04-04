package com.mar.solicity.ui.beneficiary;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.mar.solicity.data.Beneficiary;

public class BeneficiaryViewModel extends ViewModel {
    DatabaseReference dbBeneficiary;
    private MutableLiveData<Boolean> _result = new MutableLiveData<>();

    @NotNull
    public LiveData result() {
        return (LiveData)this._result;
    }

    public void addBeneficiary(Beneficiary beneficiary){
        dbBeneficiary = FirebaseDatabase.getInstance().getReference("Beneficiaries");
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

    public LiveData<String> getText() {
        return null;
    }
}