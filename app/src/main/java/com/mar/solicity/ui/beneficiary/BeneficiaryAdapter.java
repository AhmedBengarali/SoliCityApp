package com.mar.solicity.ui.beneficiary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mar.solicity.R;
import com.mar.solicity.data.Beneficiary;

import java.util.ArrayList;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.BenViewHolder> {

    private ArrayList<Beneficiary> beneficiaryArrayList = new ArrayList<>();

    public RecyclerViewClickListener listener = null;
    String text = "CIN : ";

    @NonNull
    @Override
    public BeneficiaryAdapter.BenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_benficiary, parent, false);
        return new BenViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BeneficiaryAdapter.BenViewHolder holder, final int position) {
        holder.itemView.setTag(beneficiaryArrayList.get(position));
        holder.Ben_name.setText(beneficiaryArrayList.get(position).getBeneficiaryName());
        holder.textViewCin.setText(text);
        holder.Ben_cin.setText(beneficiaryArrayList.get(position).getBeneficiaryCIN());
        holder.Ben_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecyclerViewItemClicked(v, beneficiaryArrayList.get(position));
            }
        });
        holder.Ben_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecyclerViewItemClicked(v, beneficiaryArrayList.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return beneficiaryArrayList.size();
    }


    public void setBeneficiaries(ArrayList<Beneficiary> beneficiaries) {
        beneficiaryArrayList = beneficiaries;
        notifyDataSetChanged();
    }


    class BenViewHolder extends RecyclerView.ViewHolder {
        TextView Ben_name, textViewCin, Ben_cin;
        ImageButton Ben_edit, Ben_Delete;

        public BenViewHolder(@NonNull View itemView) {
            super(itemView);
            Ben_name = itemView.findViewById(R.id.textViewBenName);
            textViewCin = itemView.findViewById(R.id.text_view_cin);
            Ben_cin = itemView.findViewById(R.id.text_view_ben_cin);
            Ben_edit = itemView.findViewById(R.id.button_Edit_Ben);
            Ben_Delete = itemView.findViewById(R.id.button_Delete_Ben);
        }
    }

}