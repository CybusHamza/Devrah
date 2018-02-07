package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.devrah.R;
import com.app.devrah.Views.BoardExtended.BoardExtended;
import com.app.devrah.Views.Board.BoardsActivity;
import com.app.devrah.Views.Project.ProjectsActivity;
import com.app.devrah.pojo.AcitivitiesPojo;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        if (inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.custom_layout_for_activities_list_item, null);

        holder.data = (TextView) convertView.findViewById(R.id.activities_data);
        holder.date = (TextView) convertView.findViewById(R.id.date);
        holder.base = (TextView) convertView.findViewById(R.id.tvProjectsData);
        holder.data.setText(Html.fromHtml("<b>" + projectsList.get(position).getUserName()+ "</b>"+" "+"<font color=#C3C3C3>" +projectsList.get(position).getData()+ "</font>"+" "+"<b> <u>"+projectsList.get(position).getDataArray()+ "</u></b> "));
        String date=Html.fromHtml(projectsList.get(position).getDate().replace("<br/>"," ")).toString();
        holder.date.setText(date);
//        if(projectsList.get(position).getDataArray().equals("No data found")  && projectsList.get(position).getProjectId().equals("") && projectsList.get(position).getBoardId().equals("")&& projectsList.get(position).getListId().equals("")&& projectsList.get(position).getCardId().equals("")){
//            holder.data.setText("No data found");
//        }
        holder.base.setText(projectsList.get(position).getBase());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(projectsList.get(position).getBase().equals("No Data Found")  && projectsList.get(position).getProjectId().equals("") && projectsList.get(position).getBoardId().equals("")&& projectsList.get(position).getListId().equals("")&& projectsList.get(position).getCardId().equals("")){

                }else {
                    //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
                  /*  if (!projectsList.get(position).getDataArray().equals("") && !projectsList.get(position).getProjectId().equals("") && projectsList.get(position).getBoardId().equals("") && projectsList.get(position).getListId().equals("") && projectsList.get(position).getCardId().equals("")) {
                        Intent intent = new Intent(activity, ProjectsActivity.class);
                        intent.putExtra("ScreenName", "activities");
                        if(projectsList.get(position).getData().equals("added group"))
                            intent.putExtra("showScreen1", "activities1");
                        activity.finish();
                        activity.startActivity(intent);
                    } else if (!projectsList.get(position).getDataArray().equals("") && !projectsList.get(position).getProjectId().equals("") && !projectsList.get(position).getBoardId().equals("") && projectsList.get(position).getListId().equals("") && projectsList.get(position).getCardId().equals("")) {
                        Intent intent = new Intent(activity, BoardsActivity.class);
                        intent.putExtra("ptitle", projectsList.get(position).getDataArray());
                        intent.putExtra("pid", projectsList.get(position).getProjectId());
                        intent.putExtra("status", "0");
                        intent.putExtra("ScreenName", "activities");
                        activity.finish();
                        activity.startActivity(intent);
                    } else if (!projectsList.get(position).getDataArray().equals("") && !projectsList.get(position).getProjectId().equals("") && !projectsList.get(position).getBoardId().equals("") && !projectsList.get(position).getListId().equals("") && projectsList.get(position).getCardId().equals("")) {
                        Intent intent = new Intent(activity, BoardExtended.class);
                        intent.putExtra("ptitle", "");
                        intent.putExtra("TitleData", projectsList.get(position).getDataArray());
                        intent.putExtra("p_id", projectsList.get(position).getProjectId());
                        intent.putExtra("b_id", projectsList.get(position).getBoardId());
                        intent.putExtra("ScreenName", "activities");
                        activity.finish();
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(activity, "Data not found", Toast.LENGTH_LONG).show();
                    }*/
                }


            }
        });

        return convertView;
    }


    public static class ViewHolder{
        TextView data,date,base;
    }

}