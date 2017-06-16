package com.app.devrah.Holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.devrah.R;

/**
 * Created by AQSA SHaaPARR on 6/12/2017.
 */

public class View_Holder_Checklist extends RecyclerView.ViewHolder {

    CardView cv ;
  public   CheckBox cb;
   public EditText etChecklist;
   public ImageView menuimg;


    public View_Holder_Checklist(View itemView) {
        super(itemView);
        cv = (CardView)itemView.findViewById(R.id.cvRVChecklist);
        etChecklist = (EditText)itemView.findViewById(R.id.etChecklist);
        cb = (CheckBox)itemView.findViewById(R.id.checklistDataForRV);
        menuimg = (ImageView)itemView.findViewById(R.id.checkListDltMenu);

    }
}
