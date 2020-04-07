package com.mar.solicity.ui.product;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mar.solicity.R;

import com.mar.solicity.data.Beneficiary;
import com.mar.solicity.data.Product;

import java.util.ArrayList;


public class ProductFragment extends Fragment implements RecyclerViewClickListener{
    private RecyclerView recyclerView;
    private ProductViewModel productViewModel;
    private ProductAdapter productAdapter;
    AlertDialog.Builder builder;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productViewModel =
                ViewModelProviders.of(this).get(ProductViewModel.class);
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        productAdapter = new ProductAdapter();

        builder = new AlertDialog.Builder(requireContext());

        productAdapter.listener = this;

        recyclerView = view.findViewById(R.id.recycler_view_bene);
        recyclerView.setAdapter(productAdapter);

        productViewModel.fetchProducts();
//            beneficiaryViewModel.getBeneficiaryUpdates();

        productViewModel.productsList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {

            @Override
            public void onChanged(ArrayList<Product> prod) {
                productAdapter.setProduct(prod);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.add_prod);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddProductDialogFragment().show(ProductFragment.this.getChildFragmentManager(), "");


            }
        });

        return view;
    }



    @Override
    public void onRecyclerViewItemClicked(View view, final Product product) {

        switch (view.getId()){
            case R.id.button_edit_prod:
                new EditProductDialogFragment(product).show(ProductFragment.this.getChildFragmentManager(),"");
                break;

            case R.id.button_Delete_prod:
                builder.setTitle(R.string.delete_confirmation);
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productViewModel.deleteProduct(product);
                    }

                }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }
}
