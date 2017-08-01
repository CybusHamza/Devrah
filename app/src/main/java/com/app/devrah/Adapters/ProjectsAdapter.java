package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.devrah.Views.BoardsActivity;
import com.app.devrah.R;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.List;

/**
 * Created by AQSA SHaaPARR on 5/31/2017.
 */

public class ProjectsAdapter extends BaseAdapter {

    List<ProjectsPojo> projectsList;
    Activity activity;
    private LayoutInflater inflater;


    public ProjectsAdapter(Activity activity, List<ProjectsPojo> projectsList) {
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
            convertView = inflater.inflate(R.layout.custom_layout_for_projects, null);

        holder.data = (TextView) convertView.findViewById(R.id.tvProjectsData);
        holder.data.setText(projectsList.get(position).getData());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,BoardsActivity.class);
                intent.putExtra("pid",projectsList.get(position).getId());
                activity.startActivity(intent);

            }
        });

        return convertView;
    }


    public static class ViewHolder{
        TextView data;
    }

}
