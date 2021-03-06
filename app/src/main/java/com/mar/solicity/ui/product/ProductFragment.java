package com.mar.solicity.ui.product;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.List;


public class ProductFragment extends Fragment implements RecyclerViewClickListener {
    private RecyclerView recyclerView;
    private ProductViewModel productViewModel;
    private ProductAdapter productAdapter;
    AlertDialog.Builder builder;
    private Spinner spinner;
    ArrayAdapter<String> dataAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productViewModel =
                ViewModelProviders.of(this).get(ProductViewModel.class);
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        setHasOptionsMenu(false);
        productAdapter = new ProductAdapter();
        builder = new AlertDialog.Builder(requireContext());
        productAdapter.listener = this;
        recyclerView = view.findViewById(R.id.recycler_view_products);
        recyclerView.setAdapter(productAdapter);

        spinner = view.findViewById(R.id.filter_spinner);

        List<String> filter = new ArrayList<>();
        filter.add(0,"Tous les produits");
        filter.add("Produits En Stock");
        filter.add("Produits En rupture de stock");

        dataAdapter = new ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, filter);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Tous les produits"))
                {
                    productViewModel.fetchAllProducts();
                    productViewModel.productsList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {

                        @Override
                        public void onChanged(ArrayList<Product> prod) {
                            productAdapter.setAllProduct(prod);
                        }
                    });
                }else if (parent.getItemAtPosition(position).equals("Produits En Stock"))
                {
                    productViewModel.fetchInStockProduct();
                    productViewModel.inStockProducts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {

                        @Override
                        public void onChanged(ArrayList<Product> prod) {
                            productAdapter.setInStockProduct(prod);

                        }
                    });
                }else
                {
                    productViewModel.fetchOutOfStockProduct();
                    productViewModel.outOfStockProducts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {

                        @Override
                        public void onChanged(ArrayList<Product> prod) {
                            productAdapter.setOutOfStockProduct(prod);
                        }
                    });
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        switch (view.getId()) {
            case R.id.button_edit_prod:
                new EditProductDialogFragment(product).show(ProductFragment.this.getChildFragmentManager(), "");
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
