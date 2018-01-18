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
import com.app.devrah.Views.Board.BoardsActivity;
import com.app.devrah.Views.BoardExtended.BoardExtended;
import com.app.devrah.Views.cards.CardActivity;
import com.app.devrah.pojo.NotificationsPojo;
import com.app.devrah.pojo.boardNotificationsPojo;
import com.app.devrah.pojo.cardNotificationsPojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    List<Date> dateList = new ArrayList<Date>();


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
        holder.userName.setText(Html.fromHtml("<b>" + projectsList.get(position).getUserName()  + "</b> "+ "<font color=#C3C3C3>" +projectsList.get(position).getLabel() + "</font>" +"<b> <u>" + projectsList.get(position).getData() + "</u></b> " ));
        holder.date = (TextView) convertView.findViewById(R.id.date);
        if(projectsList.get(position).getLabel().equals("No notification found") && projectsList.get(position).getUserName().equals("")){
            holder.userName.setText(Html.fromHtml("<font color=#FFFFFF>" +projectsList.get(position).getLabel() + "</font>"));
        }
        /* String inputPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        String date=inputFormat.format(projectsList.get(position).getDate());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
        String newDateFormate = outputFormat.format(date);*/
        holder.date.setText(projectsList.get(position).getDate());

//        holder.data = (TextView) convertView.findViewById(R.id.activities_data);
//        holder.data.setText();

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
                if (!projectsList.get(position).getLabel().equals("No notification found")) {
                    if (projectsList.get(position).getLabel().equals(" added you to the project ")) {
                        Intent intent = new Intent(activity, BoardsActivity.class);
                        intent.putExtra("pid", projectsList.get(position).getProjectId());
                        intent.putExtra("ptitle", projectsList.get(position).getData());
                        intent.putExtra("project_created_by", projectsList.get(position).getProjectCreatedBy());
                        intent.putExtra("ScreenName", "activities");
                        activity.finish();
                        activity.startActivity(intent);
                    } else if (projectsList.get(position).getLabel().equals(" added you to the board ") && !projectsList.get(position).getBoardId().equals("0")) {
                        Intent intent = new Intent(activity, BoardExtended.class);
                        intent.putExtra("p_id", projectsList.get(position).getProjectId());
                        intent.putExtra("b_id", projectsList.get(position).getBoardId());
                        intent.putExtra("TitleData", projectsList.get(position).getData());
                        intent.putExtra("ptitle", projectsList.get(position).getProjectTitle());
                        intent.putExtra("work_board", projectsList.get(position).getBoardType());
                        intent.putExtra("ScreenName", "activities");
                        activity.finish();
                        activity.startActivity(intent);
                    } else if (projectsList.get(position).getLabel().equals(" added you to the card ")) {
                        Intent intent = new Intent(activity, CardActivity.class);
                        intent.putExtra("CardHeaderData", projectsList.get(position).getData());
                        intent.putExtra("card_id", projectsList.get(position).getCardId());
                        intent.putExtra("cardduedate", projectsList.get(position).getCardDueDate());
                        intent.putExtra("cardduetime", projectsList.get(position).getCardDueTime());
                        intent.putExtra("cardstartdate", projectsList.get(position).getCardStartDate());
                        intent.putExtra("cardstarttime", projectsList.get(position).getCardStartTime());
                        intent.putExtra("cardDescription", projectsList.get(position).getCardDescription());
                        intent.putExtra("isComplete", projectsList.get(position).getIsComplete());
                        intent.putExtra("isLocked", projectsList.get(position).getIsLocked());
                        intent.putExtra("isSubscribed", projectsList.get(position).getIsSubscribed());
                        intent.putExtra("list_id", projectsList.get(position).getListId());
                        intent.putExtra("board_id", projectsList.get(position).getBoardId());
                        intent.putExtra("project_id", projectsList.get(position).getProjectId());
                        intent.putExtra("board_name", projectsList.get(position).getBoard_name());
                        intent.putExtra("project_title", projectsList.get(position).getProjectTitle());
                        intent.putExtra("fromMyCards", "notifi");
                        intent.putExtra("ScreenName", "activities");
                        activity.finish();
                        activity.startActivity(intent);
                    }
                }

            }
        });
        Collections.sort(projectsList, new Comparator<NotificationsPojo>() {
            public int compare(NotificationsPojo first, NotificationsPojo second)  {
                return second.getDate().compareTo(first.getDate());
            }
        });


        return convertView;
    }


    public static class ViewHolder{
        TextView data,userName,date;
    }

}