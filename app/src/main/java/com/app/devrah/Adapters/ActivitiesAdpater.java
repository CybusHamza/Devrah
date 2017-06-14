package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.devrah.BoardsActivity;
import com.app.devrah.R;
import com.app.devrah.pojo.AcitivitiesPojo;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.List;

/**
 * Created by Rizwan Butt on 14-Jun-17.
 */

public class ActivitiesAdpater extends BaseAdapter {

    List<AcitivitiesPojo> projectsList;
    Activity activity;
    private LayoutInflater inflater;


    public ActivitiesAdpater(Activity activity, List<AcitivitiesPojo> projectsList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        if (inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.custom_layout_for_activities_list_item, null);

        holder.data = (TextView) convertView.findViewById(R.id.activities_data);
        holder.data.setText(projectsList.get(position).getData());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity,BoardsActivity.class);
                activity.startActivity(intent);

            }
        });

        return convertView;
    }


    public static class ViewHolder{
        TextView data;
    }

}