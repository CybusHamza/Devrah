package com.app.devrah.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.app.devrah.R;

/**
 * Created by Hamza Android on 8/30/2017.
 */

public class Cheklist extends RecyclerView.ViewHolder {

    public EditText checklistName;
    public ProgressBar progressBar;
    public LinearLayout ll;

    public Cheklist(View itemView) {
        super(itemView);
        checklistName  = (EditText)itemView.findViewById(R.id.header);
        progressBar  = (ProgressBar)itemView.findViewById(R.id.simpleProgressBar);
        ll  = (LinearLayout)itemView.findViewById(R.id.LinearLayoutheader);



    }
}
