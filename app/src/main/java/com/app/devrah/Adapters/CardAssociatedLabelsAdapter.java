package com.app.devrah.Adapters;

/**
 * Created by Rizwan Butt on 07-Aug-17.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Holders.CardAssociatedLabelRecyclerHolder;
import com.app.devrah.R;
import com.app.devrah.pojo.CardAssociatedLabelsPojo;

import java.util.List;

public class CardAssociatedLabelsAdapter extends RecyclerView.Adapter<CardAssociatedLabelRecyclerHolder> {

    List<Integer> myResultantList;
    List<String> resultantLabelNames;
    List<CardAssociatedLabelsPojo> labelList;
    Activity activity;

    public   CardAssociatedLabelsAdapter(Activity activity,List<CardAssociatedLabelsPojo> labelList){ //, List<String> resultantLabelNames

        this.activity = activity;
        this.myResultantList = myResultantList;
        this.labelList = labelList;


    }


    @Override
    public CardAssociatedLabelRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_associated_label_view,parent,false);
        CardAssociatedLabelRecyclerHolder holder = new CardAssociatedLabelRecyclerHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(CardAssociatedLabelRecyclerHolder holder, int position) {

        if(labelList.get(position).getLabels().equals("blue")){
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.colorOrangeRed));
        }
       /* holder.rvLabel.setBackgroundColor(myResultantList.get(position));
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
        });*/
    }

    @Override
    public int getItemCount() {
        return myResultantList.size();
    }
}
