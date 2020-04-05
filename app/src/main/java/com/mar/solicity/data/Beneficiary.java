package com.mar.solicity.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.firebase.database.Exclude;

import java.util.Objects;

public class Beneficiary {
    @Exclude
    @NonNull
    String beneficiaryId;
    String beneficiaryName;
    String beneficiaryPhone;
    String beneficiaryAddress;

    @Exclude
    Boolean isDeleted = false;


    public Beneficiary() {
    }

    public Beneficiary(@NonNull String beneficiaryId, String beneficiaryName, String beneficiaryPhone, String beneficiaryAddress, Boolean isDeleted) {
        this.beneficiaryId = beneficiaryId;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryPhone = beneficiaryPhone;
        this.beneficiaryAddress = beneficiaryAddress;
        this.isDeleted = isDeleted;
    }

    @Exclude
    @NonNull
    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(@NonNull String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Beneficiary){
            ((Beneficiary) obj).beneficiaryId = beneficiaryId;
            return true;
        }
        else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(beneficiaryId, beneficiaryName);
    }
}


