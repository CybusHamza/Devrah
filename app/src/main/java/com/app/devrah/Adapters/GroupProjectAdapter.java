package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.devrah.R;
import com.app.devrah.pojo.GroupProjectsPojo;

import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/1/2017.
 */

public class GroupProjectAdapter extends BaseAdapter {

    List<GroupProjectsPojo> pojoList;
    Activity activity;
    LayoutInflater inflater;


   public GroupProjectAdapter(Activity activity,List<GroupProjectsPojo> list){

        this.activity=activity;
        this.pojoList=list;

    }

    @Override
    public int getCount() {
        return pojoList.size();
    }

    @Override
    public Object getItem(int position) {
      return   pojoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GroupProjectHolder holder = new GroupProjectHolder();
        if (inflater==null){
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        }
        if (convertView==null){

            convertView=inflater.inflate(R.layout.custom_layout_group_projects_list_item,null);
            holder.groupData = (TextView)convertView.findViewById(R.id.tvProjectsData);
            holder.groupData.setText(pojoList.get(position).getGroupProjectData());

        }



        return convertView;
    }

    public class  GroupProjectHolder{

        TextView groupData;

    }

}
