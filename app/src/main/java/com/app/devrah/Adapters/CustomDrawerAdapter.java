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
          //  drawerHolder.icon = (ImageView) convertView.findViewById(R.id.drawer_icon);

            convertView.setTag(drawerHolder);

        } else {
            drawerHolder = (ViewHolder) convertView.getTag();

        }

        DrawerPojo dItem = (DrawerPojo) this.drawerItemList.get(position);

//        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
//                dItem.getImgResID()));
        drawerHolder.tvTitle.setText(dItem.getName());

        return convertView;
    }

//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.list_item_drawer, null);
//            mViewHolder = new ViewHolder();
//            convertView.setTag(mViewHolder);
//        } else {
//            mViewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        mViewHolder.tvTitle = (TextView) convertView
//                .findViewById(R.id.tvNavigationDrawer);
//        mViewHolder.ivIcon = (ImageView) convertView
//                .findViewById(R.id.imageIconNavigationDrawer);
//
//        mViewHolder.tvTitle.setText(drawerItemList.get(position).getName());
     //   mViewHolder.ivIcon.setImageResource(images[position]);

        //Highlight the selected list item
//        if (position == selectedposition[0]) {
//            convertView.setBackgroundColor(Color.WHITE);
//            mViewHolder.tvTitle.setTextColor(Color.BLUE);
//        } else {
//            convertView.setBackgroundColor(Color.TRANSPARENT);
//            mViewHolder.tvTitle.setTextColor(Color.WHITE);
//        }

//        return convertView;


    private class ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
    }



}

