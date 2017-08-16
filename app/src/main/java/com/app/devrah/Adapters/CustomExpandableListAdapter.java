package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.app.devrah.R;
import com.app.devrah.Views.BoardsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 7/11/2017.
 */

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {


    private Activity context;
    private List<String> listDataHeader; // header titles
    private List<String> ids; // header titles
    private List<String> status; // header titles
    private List<String> projectids; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;


public CustomExpandableListAdapter(Activity context, List<String> listDataHeader, HashMap<String, List<String>> listChildData , ArrayList<String> ids,ArrayList<String> status,ArrayList<String> projectids ){


    this.context = context;
    this._listDataChild = listChildData;
    this.listDataHeader = listDataHeader;
    this.projectids = projectids;
    this.ids = ids;
    this.status = status;


}


    @Override
    public int getGroupCount() {

      return this.listDataHeader.size();
        //  return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        String data = ids.get(groupPosition);

            return this._listDataChild.get(data).size();


        // 0;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return this.listDataHeader.get(groupPosition);
        //    return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        String data = ids.get(groupPosition);


        return this._listDataChild.get(data)
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
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.custom_layout_group_projects_list_item, null);
        }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.tvProjectsData);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,BoardsActivity.class);
                intent.putExtra("pid",projectids.get(groupPosition));
                intent.putExtra("ptitle",childText);
                intent.putExtra("status",status.get(groupPosition));
                context.startActivity(intent);

            }
        });

        txtListChild.setText(childText);
        TextView statusList = (TextView) convertView
                .findViewById(R.id.btnActive);
        if(status.get(groupPosition).equals("1")) {
            statusList.setText("Active");
            statusList.setBackgroundColor(context.getResources().getColor(R.color.lightGreen));
        }else {
            statusList.setText("DeActive");
            statusList.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
        }
        return convertView;

      //  return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
