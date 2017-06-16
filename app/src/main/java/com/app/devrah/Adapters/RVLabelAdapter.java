package com.app.devrah.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.app.devrah.Holders.View_Holder_Checklist;
import com.app.devrah.Holders.View_holder_label;
import com.app.devrah.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/13/2017.
 */

public class RVLabelAdapter extends RecyclerView.Adapter<View_holder_label>{

    Activity activity;
    List<Integer> positionList;
    List<Integer> myList;

    public RVLabelAdapter(Activity activity,List<Integer> myList,List<Integer> positionList){

        this.myList = myList;
        this.activity = activity;
        this.positionList = positionList;
    }



    @Override
    public View_holder_label onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_label_row,parent,false);
        View_holder_label holder = new View_holder_label(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final View_holder_label holder, final int position) {



//        holder.itemView.setBackgroundResource(myList.get(position));


      //  holder.itemView.setBackgroundColor(myList.get(position));
       // holder.itemView.setBackgroundColor(myList.get(position));
        holder.rowLabel.setBackgroundColor(myList.get(position));
        holder.rowLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  holder.imgLabel.setVisibility(View.VISIBLE);
                if (holder.imgLabel.getVisibility()==View.GONE){
                    holder.imgLabel.setVisibility(View.VISIBLE);
                 if ( ! positionList.contains(myList.get(position))){
                     positionList.add(myList.get(position));

                 }


                }
                else {

                    holder.imgLabel.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public List<Integer> getData(List<Integer> myList){


        return positionList;
    }


}