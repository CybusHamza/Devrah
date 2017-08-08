package com.app.devrah.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.devrah.R;

/**
 * Created by Rizwan Butt on 07-Aug-17.
 */

public class CardAssociatedLabelRecyclerHolder extends RecyclerView.ViewHolder {
    public LinearLayout rvLabel;
    public TextView tvLabelName;
    public CardAssociatedLabelRecyclerHolder(View itemView) {
        super(itemView);
        rvLabel = (LinearLayout) itemView.findViewById(R.id.row_cardscreen_label);
        tvLabelName = (TextView)itemView.findViewById(R.id.labelName);
    }
}
