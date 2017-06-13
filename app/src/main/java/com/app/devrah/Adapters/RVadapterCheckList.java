package com.app.devrah.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.app.devrah.Holders.View_Holder;
import com.app.devrah.Holders.View_Holder_Checklist;
import com.app.devrah.R;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.Collections;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/12/2017.
 */

public class RVadapterCheckList extends RecyclerView.Adapter<View_Holder_Checklist> {

    List<ProjectsPojo> dataList = Collections.emptyList();
    Context context;


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
    public void onBindViewHolder(View_Holder_Checklist holder, int position) {

        holder.cb.setText(dataList.get(position).getData());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

