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

import com.app.devrah.R;
import com.app.devrah.Views.Board.BoardsActivity;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.List;


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
        holder.description = (TextView) convertView.findViewById(R.id.tvDescription);
        holder.status = (TextView) convertView.findViewById(R.id.btnActive);
        holder.isactive = (ImageView) convertView.findViewById(R.id.icon_active);
        holder.data.setText(projectsList.get(position).getData());
        if(projectsList.get(position).getProjectStatus().equals("1")) {
            holder.status.setText("Active");
            holder.status.setTextColor(activity.getResources().getColor(R.color.colorYellow));
            holder.isactive.setImageResource(R.drawable.active_yellow);
           // holder.status.setBackgroundColor(activity.getResources().getColor(R.color.lightGreen));
        }else if(projectsList.get(position).getProjectStatus().equals("3")) {
            holder.status.setText("Completed");
            holder.status.setTextColor(activity.getResources().getColor(R.color.lightGreen));
            holder.isactive.setImageResource(R.drawable.active);
            //holder.status.setBackgroundColor(activity.getResources().getColor(R.color.darkgreen));
        }else if(projectsList.get(position).getProjectStatus().equals("2")){
            holder.status.setText("In-Active");
            holder.status.setTextColor(activity.getResources().getColor(R.color.colorRed));
            holder.isactive.setImageResource(R.drawable.inactive_icon);
            //holder.status.setBackgroundColor(activity.getResources().getColor(R.color.colorRed));
        }
        if(projectsList.get(position).getData().equals("No data found") && projectsList.get(position).getId().equals("")) {
            holder.status.setVisibility(View.INVISIBLE);
            holder.isactive.setVisibility(View.INVISIBLE);
        }else {
            holder.status.setVisibility(View.VISIBLE);
            holder.isactive.setVisibility(View.VISIBLE);
        }

        if(!projectsList.get(position).getProjectDescription().equals("null")){

            holder.description.setText(projectsList.get(position).getProjectDescription());
        }else {
            holder.description.setText("");
        }

            convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!projectsList.get(position).getData().equals("No data found") && !projectsList.get(position).getId().equals("")) {
                    Intent intent = new Intent(activity, BoardsActivity.class);
                    intent.putExtra("pid", projectsList.get(position).getId());
                    intent.putExtra("ptitle", projectsList.get(position).getData());
                    intent.putExtra("status", projectsList.get(position).getProjectStatus());
                    intent.putExtra("project_created_by", projectsList.get(position).getProjectCreatedBy());
                    activity.startActivity(intent);
                }

            }
        });
       /* convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDragAndDrop(data,shadowBuilder,v,0 );
                return true;
            }
        });*/


        return convertView;
    }


    public static class ViewHolder{
        TextView data,status;
        TextView description;
        ImageView isactive;
    }

}
