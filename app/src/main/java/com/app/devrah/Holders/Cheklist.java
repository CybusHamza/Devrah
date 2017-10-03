package com.app.devrah.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.devrah.R;

/**
 * Created by Hamza Android on 8/30/2017.
 */

public class Cheklist extends RecyclerView.ViewHolder {

    public EditText checklistName;
    public TextView tvProgress;
    public ProgressBar progressBar;
    public LinearLayout ll;
    public ImageView deleteCheckList;

    public Cheklist(View itemView) {
        super(itemView);
        checklistName  = (EditText)itemView.findViewById(R.id.header);
        tvProgress  = (TextView)itemView.findViewById(R.id.tvCompletedPercentage);
        progressBar  = (ProgressBar)itemView.findViewById(R.id.simpleProgressBar);
        ll  = (LinearLayout)itemView.findViewById(R.id.LinearLayoutheader);
        deleteCheckList  = (ImageView) itemView.findViewById(R.id.deleteCheckList);



    }
}
