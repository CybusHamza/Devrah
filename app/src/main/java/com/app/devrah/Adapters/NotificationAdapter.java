package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.devrah.R;
import com.app.devrah.Views.BoardExtended;
import com.app.devrah.Views.BoardsActivity;
import com.app.devrah.Views.CardActivity;
import com.app.devrah.pojo.NotificationsPojo;
import com.app.devrah.pojo.boardNotificationsPojo;
import com.app.devrah.pojo.cardNotificationsPojo;

import java.util.List;

/**
 * Created by Rizwan Butt on 14-Jun-17.
 */

public class NotificationAdapter extends BaseAdapter {

    List<NotificationsPojo> projectsList;
    List<boardNotificationsPojo> boardNotificationList;
    List<cardNotificationsPojo> cardNotificationsList;
    Activity activity;
    private LayoutInflater inflater;


    public NotificationAdapter(Activity activity, List<NotificationsPojo> projectsList, List<boardNotificationsPojo> boardNotificationList, List<cardNotificationsPojo> cardNotificationsList) {
        this.activity = activity;
        this.projectsList = projectsList;
        this.boardNotificationList = boardNotificationList;
        this.cardNotificationsList = cardNotificationsList;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        if (inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.custom_layout_for_notifications_list_item, null);
        holder.userName = (TextView) convertView.findViewById(R.id.userName);
        //holder.userName.setText(projectsList.get(position).getUserName()+ projectsList.get(position).getLabel()+ projectsList.get(position).getData());
        holder.userName.setText(Html.fromHtml("<b>" + projectsList.get(position).getUserName()  + "</b> "+ projectsList.get(position).getLabel()+ "<font color=#3F51B5>" + projectsList.get(position).getData() + "</font>"));
        holder.date = (TextView) convertView.findViewById(R.id.date);
        holder.date.setText(projectsList.get(position).getDate());
//        holder.data = (TextView) convertView.findViewById(R.id.activities_data);
//        holder.data.setText();


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
                if(projectsList.get(position).getLabel().equals(" added you to the project ")) {
                    Intent intent = new Intent(activity, BoardsActivity.class);
                    intent.putExtra("pid",projectsList.get(position).getProjectId());
                    intent.putExtra("ptitle",projectsList.get(position).getData());
                    intent.putExtra("ScreenName","activities");
                    activity.finish();
                    activity.startActivity(intent);
                }else if(projectsList.get(position).getLabel().equals(" added you to the board ") && !projectsList.get(position).getBoardId().equals("0")){
                    Intent intent = new Intent(activity, BoardExtended.class);
                    intent.putExtra("p_id",projectsList.get(position).getProjectId());
                    intent.putExtra("b_id",projectsList.get(position).getBoardId());
                    intent.putExtra("TitleData",projectsList.get(position).getData());
                    intent.putExtra("ptitle",projectsList.get(position).getProjectTitle());
                    intent.putExtra("ScreenName","activities");
                    activity.finish();
                    activity.startActivity(intent);
                }else if(projectsList.get(position).getLabel().equals(" added you to the card ")){
                    Intent intent = new Intent(activity,CardActivity.class);
                    intent.putExtra("CardHeaderData", projectsList.get(position).getData());
                    intent.putExtra("card_id",projectsList.get(position).getCardId());
                    intent.putExtra("cardduedate",projectsList.get(position).getCardDueDate());
                    intent.putExtra("cardduetime",projectsList.get(position).getCardDueTime());
                    intent.putExtra("cardstartdate",projectsList.get(position).getCardStartDate());
                    intent.putExtra("cardstarttime",projectsList.get(position).getCardStartTime());
                    intent.putExtra("cardDescription",projectsList.get(position).getCardDescription());
                    intent.putExtra("isComplete",projectsList.get(position).getIsComplete());
                    intent.putExtra("isLocked",projectsList.get(position).getIsLocked());
                    intent.putExtra("isSubscribed",projectsList.get(position).getIsSubscribed());
                    intent.putExtra("list_id",projectsList.get(position).getListId());
                    intent.putExtra("board_id",projectsList.get(position).getBoardId());
                    intent.putExtra("project_id",projectsList.get(position).getProjectId());
                    intent.putExtra("board_name",projectsList.get(position).getBoard_name());
                    intent.putExtra("project_title",projectsList.get(position).getProjectTitle());
                    intent.putExtra("fromMyCards","notifi");
                    intent.putExtra("ScreenName","activities");
                    activity.finish();
                    activity.startActivity(intent);
                }
            }
        });

        return convertView;
    }


    public static class ViewHolder{
        TextView data,userName,date;
    }

}