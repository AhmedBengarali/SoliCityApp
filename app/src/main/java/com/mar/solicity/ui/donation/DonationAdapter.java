package com.mar.solicity.ui.donation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mar.solicity.R;
import com.mar.solicity.data.Donation;

import java.util.ArrayList;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DoneeViewHolder> {

    private ArrayList<Donation> donationArrayList = new ArrayList<>();
    public RecyclerViewClickListener listener = null;

    String text = "اخر استلام";

    @NonNull
    @Override
    public DoneeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_donee, parent, false);
        return new DoneeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoneeViewHolder holder, final int position) {
        holder.itemView.setTag(donationArrayList.get(position));
        holder.Donee_name.setText(donationArrayList.get(position).getDoneeName());
        holder.textViewDate.setText(text);
        holder.Donee_Date.setText(donationArrayList.get(position).getDate());
        holder.Donee_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecyclerViewItemClicked(v, donationArrayList.get(position));
            }
        });
        holder.Donee_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecyclerViewItemClicked(v, donationArrayList.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return donationArrayList.size();
    }

    public void setDonations(ArrayList<Donation> donations) {
        donationArrayList = donations;
        notifyDataSetChanged();
    }

    class DoneeViewHolder extends RecyclerView.ViewHolder {
        TextView Donee_name, textViewDate, Donee_Date;
        ImageButton Donee_edit, Donee_Delete;

        public DoneeViewHolder(@NonNull View itemView) {
            super(itemView);

            Donee_name = itemView.findViewById(R.id.textViewDoneeName);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            Donee_Date = itemView.findViewById(R.id.text_view_donee_date);
            Donee_edit = itemView.findViewById(R.id.button_Edit_donee);
            Donee_Delete = itemView.findViewById(R.id.button_Delete_Donee);
        }
    }
}
