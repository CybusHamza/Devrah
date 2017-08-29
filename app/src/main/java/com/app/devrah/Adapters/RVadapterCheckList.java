package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.app.devrah.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/12/2017.
 */

public class RVadapterCheckList extends BaseExpandableListAdapter {


    private Activity context;
    private List<String> listDataHeader; // header titles
    private List<String> ids; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;


    public RVadapterCheckList(Activity context, List<String> listDataHeader, HashMap<String, List<String>> listChildData, ArrayList<String> ids) {


        this.context = context;
        this._listDataChild = listChildData;
        this.listDataHeader = listDataHeader;

        this.ids = ids;



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
            convertView = infalInflater.inflate(R.layout.cheklist_group_header, null);
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
            convertView = infalInflater.inflate(R.layout.custom_layout_checklist_list_item, null);
        }
        CheckBox txtListChild = (CheckBox) convertView
                .findViewById(R.id.checkbox);


      /*  convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BoardsActivity.class);
                intent.putExtra("pid", projectids.get(groupPosition));
                intent.putExtra("ptitle", childText);
                intent.putExtra("status", status.get(groupPosition));
                context.startActivity(intent);

            }
        });
*/


       /* if (status.get(groupPosition).equals("1")) {
            statusList.setText("Active");
            statusList.setBackgroundColor(context.getResources().getColor(R.color.lightGreen));
        } else {
            statusList.setText("DeActive");
            statusList.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
        }*/
        return convertView;

        //  return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

