package com.app.devrah.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.devrah.R;

/**
 * Created by AQSA SHaaPARR on 6/13/2017.
 */

public class View_holder_label extends RecyclerView.ViewHolder {

   public ImageView imgLabel,editLabel,labelIcon;
  public RelativeLayout rowLabel;
    public TextView tvLabelNames;

    public View_holder_label(View itemView) {


        super(itemView);

        imgLabel = (ImageView)itemView.findViewById(R.id.img_tick);
        rowLabel = (RelativeLayout)itemView.findViewById(R.id.labels_row);
        editLabel = (ImageView)itemView.findViewById(R.id.edit_label);
        labelIcon = (ImageView)itemView.findViewById(R.id.labelIcon);
       tvLabelNames = (TextView)itemView.findViewById(R.id.tvLabelName);
    }
}
