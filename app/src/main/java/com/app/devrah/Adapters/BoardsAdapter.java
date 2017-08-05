package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.devrah.Views.BoardExtended;
import com.app.devrah.R;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/1/2017.
 */

public class BoardsAdapter extends BaseAdapter {



    List<ProjectsPojo> projectsList;
    String ListItemString;
    Activity activity;
    private LayoutInflater inflater;


    public BoardsAdapter(Activity activity, List<ProjectsPojo> projectsList) {
        this.activity = activity;
        this.projectsList = projectsList;

        //  super(activity,R.layout.custom_layout_for_projects,projectsList);
    }


    @Override
    public int getCount() {
        return projectsList.size();
    }

    @Override
    public Object getItem(int position) {
        return   projectsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        if (inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.row_boards, null);

        holder.data = (TextView) convertView.findViewById(R.id.tvBoardsData);
        ListItemString = projectsList.get(position).getData();
        holder.data.setText(ListItemString);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).showl();
                Intent intent = new Intent(activity,BoardExtended.class);
                intent.putExtra("TitleData",ListItemString);
                intent.putExtra("p_id",projectsList.get(position).getId());
                intent.putExtra("b_id",projectsList.get(position).getBoardID());
                intent.putExtra("list_id",projectsList.get(position).getListId());
                intent.putExtra("ptitle",activity.getIntent().getStringExtra("ptitle"));

                activity.startActivity(intent);

            }
        });

        return convertView;
    }


    public static class ViewHolder{
        TextView data;
        ImageView favouriteIcon;
    }










}
