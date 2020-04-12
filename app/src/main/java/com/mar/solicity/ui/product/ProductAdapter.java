package com.mar.solicity.ui.product;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mar.solicity.R;
import com.mar.solicity.data.Product;
import com.mar.solicity.ui.beneficiary.RecyclerViewClickListener;

import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProdViewHolder> {

    private ArrayList<Product> productsArrayList = new ArrayList<>();
    public ProductFragment listener = null;


    @NonNull
    @Override
    public ProductAdapter.ProdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_product, parent, false);
        return new ProductAdapter.ProdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProdViewHolder holder, final int position) {
        holder.itemView.setTag(productsArrayList.get(position));

        holder.prod_text.setText("Quantité :");


        holder.prod_name.setText(productsArrayList.get(position).getProductName());
        holder.prod_quantity.setText(productsArrayList.get(position).getProductQuantity());
        holder.prod_unit.setText(productsArrayList.get(position).getProductUnit());
        holder.prod_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecyclerViewItemClicked(v, productsArrayList.get(position));
            }
        });
        holder.prod_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecyclerViewItemClicked(v, productsArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public void setAllProduct(ArrayList<Product> product) {
        productsArrayList = (ArrayList<Product>) product;
        notifyDataSetChanged();
    }

    public void setInStockProduct(ArrayList<Product> prod) {
        productsArrayList = (ArrayList<Product>) prod;
        notifyDataSetChanged();
    }

    public void setOutOfStockProduct(ArrayList<Product> prod) {
        productsArrayList = (ArrayList<Product>) prod;
        notifyDataSetChanged();
    }

    class ProdViewHolder extends RecyclerView.ViewHolder {
        TextView prod_text, prod_name, prod_quantity, prod_unit;
        ImageButton prod_edit, prod_Delete;

        public ProdViewHolder(@NonNull View itemView) {
            super(itemView);
            prod_text = itemView.findViewById(R.id.text_view_quant);
            prod_name = itemView.findViewById(R.id.textViewProdName);
            prod_quantity = itemView.findViewById(R.id.text_view_prod_quantity);
            prod_unit = itemView.findViewById(R.id.text_view_prod_unit);

            prod_edit = itemView.findViewById(R.id.button_edit_prod);
            prod_Delete = itemView.findViewById(R.id.button_Delete_prod);
        }
    }
}
