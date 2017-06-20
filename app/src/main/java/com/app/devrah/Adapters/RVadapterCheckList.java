package com.app.devrah.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListAdapter;

import com.app.devrah.CardActivity;
import com.app.devrah.Holders.View_Holder;
import com.app.devrah.Holders.View_Holder_Checklist;
import com.app.devrah.R;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.Collections;
import java.util.List;

import static java.lang.Math.ceil;

/**
 * Created by AQSA SHaaPARR on 6/12/2017.
 */

public class RVadapterCheckList extends RecyclerView.Adapter<View_Holder_Checklist> {

    List<ProjectsPojo> dataList = Collections.emptyList();
    Context context;

    float m= 0;
    public  RVadapterCheckList(List<ProjectsPojo> DataList,Context Context){
        this.dataList = DataList;
        this.context =  Context;

    }



    @Override
    public View_Holder_Checklist onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_view_checklist,parent,false);
        View_Holder_Checklist holder = new View_Holder_Checklist(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder_Checklist holder, final int position) {


        holder.etChecklist.setText(dataList.get(position).getData());

        holder.etChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardActivity.onFocus = true;
                CardActivity.menuChanger(CardActivity.menu,CardActivity.onFocus);
                holder.etChecklist.setClickable(true);
                holder.etChecklist.setLongClickable(true);
                holder.etChecklist.setCursorVisible(true);
                holder.etChecklist.setEnabled(true);
            }
        });
        //holder.cb.setText(dataList.get(position).getData());
//        holder.cb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });






        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                float p = 100 / (dataList.size());

                if (isChecked){
                    int size = dataList.size();
//

                    // CardActivity.simpleProgressBar.incrementProgressBy(p);
                    //double p = ceil( 100 / (dataList.size()));
                    m = m + p;

                    CardActivity.simpleProgressBar.setProgress((int)m);


                }
                if (isChecked==false){

                    m = m-p;
                    CardActivity.simpleProgressBar.setProgress((int) m);
                }



            }
        });
        holder.menuimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            removeAt(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void removeAt(int position){

        dataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,dataList.size());

    }


}

