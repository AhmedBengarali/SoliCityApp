package com.mar.solicity.ui.donation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.mar.solicity.data.Beneficiary;
import com.mar.solicity.data.Donation;

import java.util.ArrayList;
import java.util.Objects;

public class DonationViewModel extends ViewModel {

    private DatabaseReference dbDonation = FirebaseDatabase.getInstance().getReference("Donee");

    private MutableLiveData<Boolean> _result = new MutableLiveData<>();

    @NotNull
    LiveData result() {
        return (LiveData) this._result;
    }

    private MutableLiveData<ArrayList<Donation>> _donationlist = new MutableLiveData<>();

    @NotNull
    LiveData<ArrayList<Donation>> donationList() {
        return _donationlist;
    }

    void addDonation(Donation donation) {
        String id = dbDonation.push().getKey();
        dbDonation.child(id).setValue(donation)
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

    void fetchDonees() {
        dbDonation.orderByChild("date")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            ArrayList<Donation> donations = new ArrayList<>();
                            donations.clear();
                            for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                                Donation donation = (Donation) datasnapshot.getValue(Donation.class);
                                assert donation != null;
                                donation.setDonneId(Objects.requireNonNull(datasnapshot.getKey()));
                                donations.add(donation);
                            }

                            _donationlist.postValue(donations);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    void searchDonee(final String s) {
        dbDonation.orderByChild("doneeCIN")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            ArrayList<Donation> donations = new ArrayList<>();
                            donations.clear();
                            for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                                Donation donation = (Donation) datasnapshot.getValue(Donation.class);
                                assert donation != null;
                                donation.setDonneId(Objects.requireNonNull(datasnapshot.getKey()));
                        if (donation.getDoneeCIN().equals(s)){
                                donations.add(donation);
                        }
                            }

                            _donationlist.postValue(donations);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    void updateDonation(Donation donation) {

        dbDonation.child(donation.getDonneId()).setValue(donation)
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


    void deleteDonees(Donation donation) {
        String id = donation.getDonneId();
        dbDonation.child(id).setValue(null)
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
}