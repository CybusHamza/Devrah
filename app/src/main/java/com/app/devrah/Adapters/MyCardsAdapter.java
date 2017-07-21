package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.devrah.BoardsActivity;
import com.app.devrah.R;
import com.app.devrah.pojo.MyCardsPojo;

import java.util.List;

/**
 * Created by Rizwan Butt on 14-Jun-17.
 */

public class MyCardsAdapter extends BaseAdapter implements View.OnTouchListener {
    List<MyCardsPojo> projectsList;
    Activity activity;
    private LayoutInflater inflater;


    public MyCardsAdapter(Activity activity, List<MyCardsPojo> projectsList) {
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

        ViewHolder vh;
        MyCardsAdapter.ViewHolder holder = new MyCardsAdapter.ViewHolder();
        if (inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.custom_layout_for_my_cards, null);


        vh = new ViewHolder();
        convertView.setTag(vh);

        convertView.setOnTouchListener(this);
        holder.data = (TextView) convertView.findViewById(R.id.projectName);
        holder.data.setText("Project Name : "+projectsList.get(position).getProjectname());
        holder.boardData = (TextView) convertView.findViewById(R.id.boardName);
        holder.boardData.setText("Board Name : "+projectsList.get(position).getBoardname());
        holder.cardName = (TextView) convertView.findViewById(R.id.tvFavouritesData);
        holder.cardName.setText(projectsList.get(position).getCard_name());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,BoardsActivity.class);
                activity.startActivity(intent);

            }
        });

        return convertView;
    }


    public static class ViewHolder{
        TextView data,boardData,cardName;

        public float lastTouchedX;
        public float lastTouchedY;

    }

    public void reLoadView()
    {
        notifyDataSetChanged();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ViewHolder vh = (ViewHolder) view.getTag();

        vh.lastTouchedX = motionEvent.getX();
        vh.lastTouchedY = motionEvent.getY();

        return false;
    }


}
