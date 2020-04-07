package com.mar.solicity.ui.product;

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
import com.mar.solicity.data.Product;

import java.util.ArrayList;
import java.util.Objects;

public class ProductViewModel extends ViewModel {

    private DatabaseReference dbProduct = FirebaseDatabase.getInstance().getReference("Products");

    private MutableLiveData<Boolean> _result = new MutableLiveData<>();
    @NotNull
    LiveData result() { return (LiveData)this._result; }

    private MutableLiveData<ArrayList<Product>> _productslist = new MutableLiveData<>();
    @NotNull
    LiveData<ArrayList<Product>> productsList() { return _productslist; }





    void addProduct(Product product){
        String id = dbProduct.push().getKey();
        dbProduct.child(id).setValue(product)
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

    void fetchProducts(){
        dbProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ArrayList<Product> products = new ArrayList<>();
                    for (DataSnapshot datasnapshot: snapshot.getChildren()){
                        Product product = (Product) datasnapshot.getValue(Product.class);
//                        assert beneficiary != null;
                        product.setProductId(Objects.requireNonNull(datasnapshot.getKey()));
                        products.add(product);
                    }
                    _productslist.postValue(products);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void updateProduct(Product product){
        dbProduct.child(product.getProductId()).setValue(product)
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

    void deleteProduct(Product product){
        String id = product.getProductId();
        dbProduct.child(id).setValue(null)
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

}