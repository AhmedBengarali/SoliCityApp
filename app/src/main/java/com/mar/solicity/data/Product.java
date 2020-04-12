package com.mar.solicity.data;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

public class Product {
    @Exclude
    @NonNull
    String productId;
    String productName;
    String productUnit;
    String productQuantity;


    public Product() {
    }

    public Product(@NonNull String productId, String productName, String productUnit, String productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productUnit = productUnit;
        this.productQuantity = productQuantity;
    }

    @Exclude
    @NonNull
    public String getProductId() {
        return productId;
    }

    @Exclude
    public void setProductId(@NonNull String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}
