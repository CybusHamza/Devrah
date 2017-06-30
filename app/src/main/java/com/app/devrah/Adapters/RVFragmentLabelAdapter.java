package com.app.devrah.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.devrah.Holders.ViewHolderRVcardScreenLabelResult;
import com.app.devrah.Holders.View_holder_label;
import com.app.devrah.R;

import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/20/2017.
 */

public class RVFragmentLabelAdapter extends RecyclerView.Adapter<ViewHolderRVcardScreenLabelResult> {

    Activity activity;
    List<Integer> colorList;
    List<Integer> positionList;


        public RVFragmentLabelAdapter(Activity activity, List<Integer> colorList){


            this.activity = activity;
            this.colorList = colorList;

        }


    @Override
    public ViewHolderRVcardScreenLabelResult onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rv_card_screen_labelresult,parent,false);
        ViewHolderRVcardScreenLabelResult holder = new ViewHolderRVcardScreenLabelResult(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderRVcardScreenLabelResult holder, int position) {
        holder.rvLabel.setBackgroundColor(colorList.get(position));

    }



    @Override
    public int getItemCount() {
        return colorList.size();
    }
}
