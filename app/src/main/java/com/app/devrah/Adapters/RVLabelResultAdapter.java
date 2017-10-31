package com.app.devrah.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Holders.ViewHolderRVcardScreenLabelResult;
import com.app.devrah.R;
import com.app.devrah.Views.cards.CardActivity;
import com.app.devrah.pojo.CardAssociatedLabelsPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/14/2017.
 */

public class RVLabelResultAdapter extends RecyclerView.Adapter<ViewHolderRVcardScreenLabelResult> {

    List<Integer> myResultantList;
    List<String> resultantLabelNames;
    Activity activity;
    List<CardAssociatedLabelsPojo> labelList;

    public RVLabelResultAdapter(Activity activity, List<Integer> myResultantList, List<String> labelNameResultList, List<CardAssociatedLabelsPojo> labelList) { //, List<String> resultantLabelNames

        this.activity = activity;
        this.myResultantList = myResultantList;
        this.resultantLabelNames = labelNameResultList;
        this.labelList = labelList;


    }


    @Override
    public ViewHolderRVcardScreenLabelResult onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rv_card_screen_labelresult, parent, false);
        ViewHolderRVcardScreenLabelResult holder = new ViewHolderRVcardScreenLabelResult(view);

        // holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.lightGreen));

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderRVcardScreenLabelResult holder, int position) {

        holder.tvLabelName.setText(labelList.get(position).getLabelTextCards());
        final String labelColor = labelList.get(position).getLabelColorCards();
        
        if (labelColor.equals("blue")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.blue));
        } else if (labelColor.equals("sky-blue")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
        } else if (labelColor.equals("red")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.colorRed));
        } else if (labelColor.equals("yellow")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.colorYellow));
        } else if (labelColor.equals("purple")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.purple));
        } else if (labelColor.equals("pink")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.pink));
        } else if (labelColor.equals("orange")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));
        } else if (labelColor.equals("black")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.black));
        } else if (labelColor.equals("green")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.colorGreen));
        } else if (labelColor.equals("dark-green")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.darkgreen));
        } else if (labelColor.equals("lime")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.lightGreen));
        }

        /*holder.rvLabel.setBackgroundColor(myResultantList.get(position));
        if (!(resultantLabelNames.get(position).isEmpty())) {
          //  holder.tvLabelName.setTextColor(activity.getResources().getColor(R.color.colorOrangeRed));
            holder.tvLabelName.setText(resultantLabelNames.get(position));
        }*/
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

                ArrayList<String> colors = new ArrayList<String>();

                for (int i = 0; i < labelList.size(); i++) {
                    colors.add(labelList.get(i).getLabelColorCards());
                }

                CardActivity.showDatColors(colors);
                CardActivity.rvLabelResult.setVisibility(View.GONE);
                CardActivity.labelAdd.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public int getItemCount() {
        return labelList.size();
    }
}
