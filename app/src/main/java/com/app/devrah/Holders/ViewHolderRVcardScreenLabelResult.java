package com.app.devrah.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.app.devrah.R;

/**
 * Created by AQSA SHaaPARR on 6/14/2017.
 */

public class ViewHolderRVcardScreenLabelResult extends RecyclerView.ViewHolder {

   public LinearLayout rvLabel;

    public ViewHolderRVcardScreenLabelResult(View itemView) {
        super(itemView);
        rvLabel = (LinearLayout) itemView.findViewById(R.id.row_cardscreen_result);
    }
}
