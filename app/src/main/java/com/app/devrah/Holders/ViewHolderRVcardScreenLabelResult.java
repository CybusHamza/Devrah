package com.app.devrah.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.devrah.R;

/**
 * Created by AQSA SHaaPARR on 6/14/2017.
 */

public class ViewHolderRVcardScreenLabelResult extends RecyclerView.ViewHolder {

   public LinearLayout rvLabel;
    public TextView tvLabelName;

    public ViewHolderRVcardScreenLabelResult(View itemView) {
        super(itemView);
        rvLabel = (LinearLayout) itemView.findViewById(R.id.row_cardscreen_result);
        tvLabelName = (TextView)itemView.findViewById(R.id.labelName);


    }
}
