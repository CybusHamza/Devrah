package com.app.devrah.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.devrah.R;

/**
 * Created by AQSA SHaaPARR on 6/13/2017.
 */

public class View_holder_label extends RecyclerView.ViewHolder {

   public ImageView imgLabel;
  public   LinearLayout rowLabel;

    public View_holder_label(View itemView) {


        super(itemView);

        imgLabel = (ImageView)itemView.findViewById(R.id.img_tick);
        rowLabel = (LinearLayout)itemView.findViewById(R.id.labels_row);

    }
}
