package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.devrah.R;
import com.app.devrah.Views.Board.BoardsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CustomExpandableListAdapter extends BaseExpandableListAdapter {






    private Activity context;
    private List<String> listDataHeader; // header titles
    private List<String> ids; // header titles
    private List<String> status; // header titles
    private List<String> projectids; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private HashMap<String, List<String>> listStatusData;


    public CustomExpandableListAdapter(Activity context, List<String> listDataHeader, HashMap<String, List<String>> listChildData, ArrayList<String> ids, ArrayList<String> status, ArrayList<String> projectids, HashMap<String, List<String>> listStatusData) {


        this.context = context;
        this._listDataChild = listChildData;
        this.listDataHeader = listDataHeader;
        this.projectids = projectids;
        this.ids = ids;
        this.status = status;
        this.listStatusData = listStatusData;


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

    public int getChildrenExactPositionCount(int groupPosition) {

        String data = ids.get(groupPosition);

        return this.listStatusData.get(data).size();


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

    public Object getChildExactPosition(int groupPosition, int childPosition) {

        String data = ids.get(groupPosition);


        return this.listStatusData.get(data)
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
        ImageView ivGroupIndicator = (ImageView) convertView
                .findViewById(R.id.ivGroupIndicator);
       ivGroupIndicator.setSelected(isExpanded);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;


//        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String child = (String) getChild(groupPosition, childPosition);
        final String[] text=child.split(",");
        final String childText=text[0];
        final String childStatus=text[1];
        final String projectId=text[2];
        String projectDescription;
        if(text[3].equals("")){
            projectDescription="";
        }else {
            projectDescription=text[3];
        }



      //  final String childStatus=(String)getChild(groupPosition,childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.custom_layout_group_projects_list_item, null);
        }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.tvProjectsData);

        ImageView isactive = (ImageView) convertView.findViewById(R.id.icon_active);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BoardsActivity.class);
                intent.putExtra("pid", projectId);
                intent.putExtra("ptitle", childText);
                intent.putExtra("status", childStatus);
                context.startActivity(intent);

            }
        });


        txtListChild.setText(childText);
        TextView statusList = (TextView) convertView
                .findViewById(R.id.btnActive);
        TextView description = (TextView) convertView
                .findViewById(R.id.tvDescription);
        if(!projectDescription.equals("null")){
            description.setText(projectDescription);
        }else {
            description.setText("");
        }
        if (childStatus.equals("1")) {
            statusList.setText("Active");
            statusList.setTextColor(context.getResources().getColor(R.color.colorYellow));
            isactive.setImageResource(R.drawable.active_yellow);
        }else if (childStatus.equals("2")) {
            statusList.setText("In-Active");
            statusList.setTextColor(context.getResources().getColor(R.color.colorRed));
            isactive.setImageResource(R.drawable.inactive_icon);
        } else {
            statusList.setText("Completed");
            statusList.setTextColor(context.getResources().getColor(R.color.lightGreen));
            isactive.setImageResource(R.drawable.active);
        }
        return convertView;

        //  return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
