package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.devrah.R;
import com.app.devrah.pojo.DrawerPojo;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by AQSA SHaaPARR on 7/19/2017.
 */

public class CustomDrawerAdapter extends BaseAdapter {

    Context context;
    List<DrawerPojo> drawerItemList;
    int layoutResources;
    LayoutInflater inflater;
    //private int[] selectedposition;


    public CustomDrawerAdapter( Context context, int resource,  List<DrawerPojo> objects) {
       // super(context, resource, objects);
        this.context = context;
        this.drawerItemList = objects;
        this.layoutResources = resource;
    }

    @Override
    public int getCount() {
        return drawerItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return drawerItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder drawerHolder;
        View view =convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new ViewHolder();

            convertView = inflater.inflate(layoutResources, parent, false);
            drawerHolder.tvTitle = (TextView) convertView
                    .findViewById(R.id.tvNavigationDrawer);
            drawerHolder.ivIcon = (ImageView) convertView.findViewById(R.id.imageIconNavigationDrawer);


            convertView.setTag(drawerHolder);

        } else {
            drawerHolder = (ViewHolder) convertView.getTag();

        }

        DrawerPojo dItem = (DrawerPojo) this.drawerItemList.get(position);

//        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
//                dItem.getImgResID()));
        drawerHolder.tvTitle.setText(dItem.getName());
        if(dItem.getName().equals("Create New Team")){
            drawerHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.team_icon_drawer));
        }else if(dItem.getName().equals("Change Status Filter") || dItem.getName().equals("Manage Status")){
            drawerHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.filter_drawer));
        }else if(dItem.getName().equals("Manage Members")){
            drawerHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.team_icon_drawer));
        }else if(dItem.getName().equals("Calendar")){
            drawerHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.calendar_icon_drawer));
        }else if(dItem.getName().equals("Copy Project")|| dItem.getName().equals("Copy Board") || dItem.getName().equals("Copy")){
            drawerHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.copy_drawer));
        }else if(dItem.getName().equals("Subscribe") || dItem.getName().equals("Un-subscribe")){
            drawerHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.subscribe_drawer));
        }else if(dItem.getName().equals("Lock Card") || dItem.getName().equals("Unlock Card")){
            drawerHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.lock_drawer));
        }else if(dItem.getName().equals("Update Project Name") || dItem.getName().equals("Update Board Name") ||dItem.getName().equals("Update Card Name")){
            drawerHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.update_drawer));
        }else if(dItem.getName().equals("Delete Project") || dItem.getName().equals("Delete Board") || dItem.getName().equals("Delete Card")){
            drawerHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.delete_drawer));
        }else if(dItem.getName().equals("Move Project") ||dItem.getName().equals("Move Board") || dItem.getName().equals("Move")){
            drawerHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.move_drawer));
        }else if(dItem.getName().equals("Leave Project") ||dItem.getName().equals("Leave Board") || dItem.getName().equals("Leave Card")){
            drawerHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.remove_drawer));
        }else if(dItem.getName().equals("Archive Board")){
            drawerHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.archive_drawer));
        }


        return convertView;
    }



    private class ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
    }



}

