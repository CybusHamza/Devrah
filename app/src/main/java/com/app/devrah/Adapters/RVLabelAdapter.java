package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.app.devrah.CardActivity;
import com.app.devrah.Holders.View_Holder_Checklist;
import com.app.devrah.Holders.View_holder_label;
import com.app.devrah.LabelColorFragment;
import com.app.devrah.R;
import com.app.devrah.pojo.CardCommentData;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/13/2017.
 */

public class RVLabelAdapter extends RecyclerView.Adapter<View_holder_label>{

   // Activity activity;
    Context context;
     public static int index;
   // String labelName;
    int flag;
  public   List<String> labelNameList;
    List<String> asliLabelNames;
    List<Integer> positionList;
 public    List<Integer> myList;
  //  HashMap<String,String > listName;

    public RVLabelAdapter(){}

                                                //////////////////String labelName Activity activity

    public RVLabelAdapter(Context context,List<Integer> myList,List<Integer> positionList,List<String> labelNameList,List<String> asliLabelNames){

        this.myList = myList;
        this.context = context;
      //  this.activity = activity;
        this.positionList = positionList;
        this.labelNameList = labelNameList;
        this.asliLabelNames = asliLabelNames;
       // this.labelName = labelName;
    }



    @Override
    public View_holder_label onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_label_row,parent,false);
        View_holder_label holder = new View_holder_label(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final View_holder_label holder, final int position) {




      if (labelNameList.get(position) != null) {
          holder.tvLabelNames.setText(labelNameList.get(position));
          holder.tvLabelNames.setVisibility(View.VISIBLE);
      }
        else {

          holder.tvLabelNames.setVisibility(View.GONE);

      }

    //  listName  = new HashMap<>();
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
                 if (holder.imgLabel.getVisibility()==View.VISIBLE){

                     String name = holder.tvLabelNames.getText().toString();
                     asliLabelNames.add(name);

                 }


                }
                else {

                    holder.imgLabel.setVisibility(View.GONE);
                }


            }
        });

        holder.editLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       //         Toast.makeText(activity.getApplicationContext(),"dfghj",Toast.LENGTH_SHORT).show();
//                flag = 5;
//                if (flag== 5){
//
//
//                    flag = 0;
//                }

                LabelColorFragment.flag = 5;
                //Fragment fm =new  LabelColorFragment();

//
//                LabelColorFragment colorFragment = new LabelColorFragment();
//
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.add(R.id.fragmentContainer,colorFragment).addToBackStack("Frag1").commit();
//
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                /////////////////////////

             index=    myList.get(position);

                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LabelColorFragment colorFragment = new LabelColorFragment();
                LabelColorFragment.color = myList.get(position);
                if (!(labelNameList.get(position).isEmpty())) {

                    colorFragment.textLabelName = labelNameList.get(position);
                   // colorFragment.etLabelName.setText(labelNameList.get(position));
                }

                fragmentTransaction.add(R.id.fragmentContainer,colorFragment).addToBackStack("Frag2").commit();
              //  fragmentTransaction.commit();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//             //
            }
        });



    }
    public void remove(int color) {
        notifyDataSetChanged();
        int position = myList.indexOf(color);
        myList.remove(position);
        labelNameList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public List<Integer> getData(List<Integer> myList){


        return positionList;
    }
    public List<String> getDataString(){


        return asliLabelNames;
    }
    public void removeAt(int position){

        myList.remove(position);
        labelNameList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,myList.size());
        notifyItemRangeChanged(position,labelNameList.size());
        notifyDataSetChanged();

    }
    public void addAt(int position){
        myList.remove(position);


    }


}