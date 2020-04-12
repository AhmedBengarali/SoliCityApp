package com.mar.solicity.ui.donation;

import android.view.View;

import com.mar.solicity.data.Beneficiary;
import com.mar.solicity.data.Donation;

public interface RecyclerViewClickListener {

    void onRecyclerViewItemClicked(View view, Donation d);

}


