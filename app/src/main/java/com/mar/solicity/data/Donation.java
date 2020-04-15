package com.mar.solicity.data;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

public class Donation {

    @Exclude
    @NonNull
    String donneId;
    String doneeName;
    String doneeCIN;
    String date;

    public Donation() {
    }

    public Donation(@NonNull String donneId, String doneeName, String doneeCIN, String date) {
        this.donneId = donneId;
        this.doneeName = doneeName;
        this.doneeCIN = doneeCIN;
        this.date = date;
    }

    @NonNull
    public String getDonneId() {
        return donneId;
    }

    public void setDonneId(@NonNull String donneId) {
        this.donneId = donneId;
    }

    public String getDoneeName() {
        return doneeName;
    }

    public void setDoneeName(String doneeName) {
        this.doneeName = doneeName;
    }

    public String getDoneeCIN() {
        return doneeCIN;
    }

    public void setDoneeCIN(String doneeCIN) {
        this.doneeCIN = doneeCIN;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
