package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.devrah.Views.CreateNewTeamActivity;
import com.app.devrah.R;
import com.app.devrah.pojo.All_Teams;

import java.util.List;

/**
 * Created by AQSA SHaaPARR on 7/20/2017.
 */

public class TeamAdapterMenu extends BaseAdapter {

    List<All_Teams> projectsList;
    Activity activity;
    private LayoutInflater inflater;


    public TeamAdapterMenu(Activity activity, List<All_Teams> projectsList) {
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
            convertView = inflater.inflate(R.layout.row_list_menu_team, null);

        holder.data = (TextView) convertView.findViewById(R.id.tvProjectsData);
        holder.description = (TextView) convertView.findViewById(R.id.tvDescription);
        holder.data.setText(projectsList.get(position).getName());
        String check=projectsList.get(position).getDescription();
        if(!check.equals("") && !check.equals("null")){
            holder.description.setText(projectsList.get(position).getDescription());
        }else {
            holder.description.setText("");
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(activity,CreateNewTeamActivity.class);
                intent.putExtra("teamMemberId",projectsList.get(position).getId());
                activity.startActivity(intent);

            }
        });

        return convertView;
    }


    public static class ViewHolder{
        TextView data,description;
    }




}
