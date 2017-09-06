package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Holders.Cheklist;
import com.app.devrah.R;
import com.app.devrah.Views.CheckList_Detail;
import com.app.devrah.pojo.check_model;

import java.util.ArrayList;
import java.util.HashMap;


public class RVadapterCheckList extends RecyclerView.Adapter<Cheklist> {

    Activity activity;
    ArrayList<check_model> CheckListItems;
    ArrayList<check_model> ids;
    ArrayList<String> checkListiItemName;
    ArrayList<String> checkListiItemIds;
    ArrayList<String> checkedItem;
    HashMap<String, ArrayList<check_model>> listDataChild;


    public RVadapterCheckList(Activity activit, ArrayList<check_model> CheckListItems,
                              HashMap<String, ArrayList<check_model>> listDataChild) {

        this.activity = activit;
        this.CheckListItems = CheckListItems;
        this.listDataChild = listDataChild;


    }

    @Override
    public Cheklist onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cheklist_group_header, parent, false);

        return new Cheklist(view);
    }

    @Override
    public void onBindViewHolder(Cheklist holder, final int position) {


        holder.checklistName.setText(CheckListItems.get(position).getName());

        checkListiItemIds= new ArrayList<String>();
        checkListiItemName= new ArrayList<String>();
        checkedItem= new ArrayList<String>();

        ids= new ArrayList<check_model>();

        ids =  listDataChild.get(CheckListItems.get(position).getId());

        for (int i = 0 ; i<ids.size(); i++)
        {
            checkListiItemIds.add(ids.get(i).getId());
            checkListiItemName.add(ids.get(i).getName());
            checkedItem.add(ids.get(i).getChecked());
        }

        float progress = 0;

        for(int i=0;i<checkedItem.size();i++)
        {
            if(checkedItem.get(i).equals("1"))
            {
                progress++;
            }
        }

        int a= checkedItem.size();
        float b = (progress/a);
        progress = b*100;

        holder.progressBar.setProgress((int) progress);
        holder.checklistName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                checkListiItemIds= new ArrayList<String>();
                checkListiItemName= new ArrayList<String>();
                checkedItem= new ArrayList<String>();

                ids= new ArrayList<check_model>();

                ids =  listDataChild.get(CheckListItems.get(position).getId());

                for (int i = 0 ; i<ids.size(); i++)
                {
                    checkListiItemIds.add(ids.get(i).getId());
                    checkListiItemName.add(ids.get(i).getName());
                    checkedItem.add(ids.get(i).getChecked());
                }

                Intent  intent = new Intent(activity, CheckList_Detail.class);
                intent.putExtra("checkListiItemIds",checkListiItemIds);
                intent.putExtra("checkListiItemName",checkListiItemName);
                intent.putExtra("checkedItem",checkedItem);
                intent.putExtra("checklistid",CheckListItems.get(position).getId());
                intent.putExtra("name",CheckListItems.get(position).getName());
                activity.startActivity(intent);
            }

        });
    }


    @Override
    public int getItemCount() {
        return CheckListItems.size();
    }
}

