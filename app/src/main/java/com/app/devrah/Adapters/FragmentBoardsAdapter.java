package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.devrah.BoardExtended;
import com.app.devrah.CardActivity;
import com.app.devrah.R;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/5/2017.
 */

public class FragmentBoardsAdapter extends BaseAdapter{
     int count=1;

    List<ProjectsPojo> projectsList;
    Activity activity;
    String BoardsListData;
    private LayoutInflater inflater;


    public FragmentBoardsAdapter(Activity activity, List<ProjectsPojo> projectsList) {
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

        final BoardsAdapter.ViewHolder holder = new BoardsAdapter.ViewHolder();

        if (inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.custom_layout_fragment_boards_list, null);

        holder.favouriteIcon= (ImageView) convertView.findViewById(R.id.favouriteIcon);
        holder.favouriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==1) {
                    Drawable d = v.getResources().getDrawable(R.drawable.mark_favourite);
                    holder.favouriteIcon.setImageDrawable(d);
                    count=2;
                }
                else if(count==2){
                    Drawable d = v.getResources().getDrawable(R.drawable.favourite_star_icon);
                    holder.favouriteIcon.setImageDrawable(d);

                }
            }
        });
        holder.data = (TextView) convertView.findViewById(R.id.tvFragmentBoardsList);
        holder.data.setText(projectsList.get(position).getData());
            BoardsListData = projectsList.get(position).getData();



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity,CardActivity.class);
                intent.putExtra("CardHeaderData",BoardsListData);
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
