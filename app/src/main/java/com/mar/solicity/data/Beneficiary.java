package com.mar.solicity.data;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;


public class Beneficiary {
    @Exclude
    @NonNull
    String beneficiaryId;
    String beneficiaryName;
    String beneficiaryCIN;
    String beneficiaryPhone;
    String beneficiaryAddress;


    public Beneficiary() {
    }

    public Beneficiary(@NonNull String beneficiaryId, String beneficiaryName, String beneficiaryCIN, String beneficiaryPhone, String beneficiaryAddress) {
        this.beneficiaryId = beneficiaryId;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryCIN = beneficiaryCIN;
        this.beneficiaryPhone = beneficiaryPhone;
        this.beneficiaryAddress = beneficiaryAddress;
    }

    @Exclude
    @NonNull
    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    @Exclude
    public void setBeneficiaryId(@NonNull String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getBeneficiaryCIN() {
        return beneficiaryCIN;
    }

    public void setBeneficiaryCIN(String beneficiaryCIN) {
        this.beneficiaryCIN = beneficiaryCIN;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryPhone() {
        return beneficiaryPhone;
    }

    public void setBeneficiaryPhone(String beneficiaryPhone) {
        this.beneficiaryPhone = beneficiaryPhone;
    }

    public String getBeneficiaryAddress() {
        return beneficiaryAddress;
    }

    public void setBeneficiaryAddress(String beneficiaryAddress) {
        this.beneficiaryAddress = beneficiaryAddress;
    }


}


