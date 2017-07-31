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

import com.app.devrah.Views.BoardsActivity;
import com.app.devrah.R;
import com.app.devrah.pojo.TeamMembersPojo;

import java.util.List;

/**
 * Created by Rizwan Butt on 19-Jun-17.
 */

public class TeamMembersAdapter extends BaseAdapter {

    List<TeamMembersPojo> membersList;
    Activity activity;
    private LayoutInflater inflater;


    public TeamMembersAdapter(Activity activity, List<TeamMembersPojo> membersList) {
        this.activity = activity;
        this.membersList = membersList;

        //  super(activity,R.layout.custom_layout_for_projects,projectsList);
    }


    @Override
    public int getCount() {
        return membersList.size();
    }

    @Override
    public Object getItem(int position) {
        return   membersList.get(position);
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
            convertView = inflater.inflate(R.layout.members_list_items, null);

        holder.data = (TextView) convertView.findViewById(R.id.memberName);
        holder.data.setText(membersList.get(position).getData());


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
        ImageView profilePic;
    }

}