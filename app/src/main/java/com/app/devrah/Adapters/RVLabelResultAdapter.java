package com.app.devrah.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Views.CardActivity;
import com.app.devrah.Holders.ViewHolderRVcardScreenLabelResult;
import com.app.devrah.R;

import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/14/2017.
 */

public class RVLabelResultAdapter extends RecyclerView.Adapter<ViewHolderRVcardScreenLabelResult> {

   List<Integer> myResultantList;
    List<String> resultantLabelNames;
    Activity activity;

  public   RVLabelResultAdapter(Activity activity,List<Integer> myResultantList,List<String> labelNameResultList){ //, List<String> resultantLabelNames

        this.activity = activity;
        this.myResultantList = myResultantList;
      this.resultantLabelNames = labelNameResultList;


    }


    @Override
    public ViewHolderRVcardScreenLabelResult onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rv_card_screen_labelresult,parent,false);
        ViewHolderRVcardScreenLabelResult holder = new ViewHolderRVcardScreenLabelResult(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderRVcardScreenLabelResult holder, int position) {

        holder.rvLabel.setBackgroundColor(myResultantList.get(position));
        if (!(resultantLabelNames.get(position).isEmpty())) {
          //  holder.tvLabelName.setTextColor(activity.getResources().getColor(R.color.colorOrangeRed));
            holder.tvLabelName.setText(resultantLabelNames.get(position));
        }
//        holder.tvLabelName.setText(resultantLabelNames.get(position));
//        holder.rvLabel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CardActivity.
//            }
//        });

        holder.rvLabel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        CardActivity.onFocus = true;

        CardActivity.showLabelsMenu();
        CardActivity.rvLabel.setVisibility(View.VISIBLE);


//        if (activity.getApplicationContext()== CardActivity.mcontext){
//
//            ((CardActivity) activity).menuChanger(CardActivity.menu,CardActivity.onFocus=true);
//
//        }
//        else
//            Toast.makeText(activity.getApplicationContext(), "Else Block", Toast.LENGTH_SHORT).show();
    }
});
    }

    @Override
    public int getItemCount() {
        return myResultantList.size();
    }
}
