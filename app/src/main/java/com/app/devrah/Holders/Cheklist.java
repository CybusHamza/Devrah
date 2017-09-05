package com.app.devrah.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.devrah.R;

/**
 * Created by Hamza Android on 8/30/2017.
 */

public class Cheklist extends RecyclerView.ViewHolder {

    public TextView checklistName;
    public ProgressBar progressBar;

    public Cheklist(View itemView) {
        super(itemView);
        checklistName  = (TextView)itemView.findViewById(R.id.header);
        progressBar  = (ProgressBar)itemView.findViewById(R.id.simpleProgressBar);



    }
}
