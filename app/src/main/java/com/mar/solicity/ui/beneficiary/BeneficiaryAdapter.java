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
import com.mar.solicity.ui.RecyclerViewClickListener;

import java.util.ArrayList;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.BenViewHolder> {

    private ArrayList<Beneficiary> beneficiaryArrayList = new ArrayList<>();


    public RecyclerViewClickListener listener = null;

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

    public void addBeneficiary(Beneficiary beneficiary) {
        if (!beneficiaryArrayList.contains(beneficiary)) {

            this.beneficiaryArrayList.add(beneficiary);
            System.out.println("hello");
            notifyDataSetChanged();
        } else {
            int index = beneficiaryArrayList.indexOf(beneficiary);
            if (beneficiary.getDeleted()) {
                System.out.println("index" + index);
                beneficiaryArrayList.remove(index);
            } else {
                beneficiaryArrayList.set(index, beneficiary);
                notifyDataSetChanged();
            }
        }

//        public void addBeneficiary(Beneficiary beneficiary) {
//            if (!this.beneficiaryArrayList.contains(beneficiary)) {
//                this.beneficiaryArrayList.add(beneficiary);
//                notifyDataSetChanged();
//                System.out.println("true"+beneficiary);
//    }
//        }


    }

    class BenViewHolder extends RecyclerView.ViewHolder {
        TextView Ben_name;
        ImageButton Ben_edit, Ben_Delete;

        public BenViewHolder(@NonNull View itemView) {
            super(itemView);
            Ben_name = itemView.findViewById(R.id.textViewBenName);
            Ben_edit = itemView.findViewById(R.id.button_Edit_Ben);
            Ben_Delete = itemView.findViewById(R.id.button_Delete_Ben);
        }
    }

}