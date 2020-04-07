package com.mar.solicity.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mar.solicity.R;
import com.mar.solicity.data.Product;


public class AddProductDialogFragment extends DialogFragment {
    ProgressBar progressBar;
    EditText productName, productQuantity;
    Spinner productUnit;
    Button addProduct;
    boolean isNameValid, isQuantityValid;
    private ProductViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        final View view = inflater.inflate(R.layout.fragment_add_product_dialog, container, false);
        productName = view.findViewById(R.id.edit_text_prod_name);
        productQuantity = view.findViewById(R.id.edit_text_prod_quantity);
        productUnit = view.findViewById(R.id.add_prod_unit);
        addProduct = view.findViewById(R.id.button_add_prod);
        progressBar = view.findViewById(R.id.add_prod_dialog_progressbar);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName = productName.getText().toString();
                String pQuantity = productQuantity.getText().toString();
                String pUnit = productUnit.getSelectedItem().toString();

                if (productName.getText().toString().isEmpty()) {
                    productName.setError(getResources().getString(R.string.product_name_error));

                    isNameValid = false;
                }else{
                    isNameValid=true;
                }

                if (productQuantity.getText().toString().isEmpty()) {
                    productQuantity.setError(getResources().getString(R.string.product_name_error));

                    isQuantityValid = false;
                }else if (productQuantity.getText().toString().equals(0)){

                    productQuantity.setError(getResources().getString(R.string.product_name_error));
                    isQuantityValid = false;
                }
                else{
                    isQuantityValid=true;
                }

                if (isNameValid&&isQuantityValid){
                    productQuantity.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    progressBar.setVisibility(View.VISIBLE);

                    Product product = new Product();
                    product.setProductName(pName);
                    product.setProductQuantity(pQuantity);
                    product.setProductUnit(pUnit);

                    viewModel.addProduct(product);
                }
            }
        });
        viewModel.result().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if (o == null){
                    Toast.makeText(requireContext(), "Produit Ajouté", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(requireContext(), "Une erreur est survenue" , Toast.LENGTH_SHORT).show();
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
        this.setStyle(STYLE_NO_TITLE,android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
    }
//        productUnity.setEnabled(false);
}
