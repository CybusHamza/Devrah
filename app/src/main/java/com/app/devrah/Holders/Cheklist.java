package com.app.devrah.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.devrah.R;

/**
 * Created by Hamza Android on 8/30/2017.
 */

public class Cheklist extends RecyclerView.ViewHolder {

    public TextView checklistName;
    public TextView tvProgress;
    public ProgressBar progressBar;
    public LinearLayout ll,commentsLayout;
    public ImageView deleteCheckList,comments;

    public Cheklist(View itemView) {
        super(itemView);
        checklistName  = (TextView) itemView.findViewById(R.id.header);
        tvProgress  = (TextView)itemView.findViewById(R.id.tvCompletedPercentage);
        progressBar  = (ProgressBar)itemView.findViewById(R.id.simpleProgressBar);
        ll  = (LinearLayout)itemView.findViewById(R.id.LinearLayoutheader);
        commentsLayout  = (LinearLayout)itemView.findViewById(R.id.commentsCheckListLayout);
        deleteCheckList  = (ImageView) itemView.findViewById(R.id.deleteCheckList);
        comments  = (ImageView) itemView.findViewById(R.id.commentsCheckList);



    }
}
