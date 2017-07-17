package com.app.devrah.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.app.devrah.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 7/11/2017.
 */

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;


public CustomExpandableListAdapter(Context context,List<String> listDataHeader,HashMap<String, List<String>> listChildData){


    this.context = context;
    this._listDataChild = listChildData;
    this.listDataHeader = listDataHeader;


}


    @Override
    public int getGroupCount() {

      return this.listDataHeader.size();
        //  return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return this._listDataChild.get(this.listDataHeader.get(groupPosition)).size();
        // 0;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return this.listDataHeader.get(groupPosition);
        //    return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_view_group_header, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;


//        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.custom_layout_group_projects_list_item, null);
        }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.tvProjectsData);

        txtListChild.setText(childText);
        return convertView;

      //  return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
