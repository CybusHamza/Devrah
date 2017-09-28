package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.devrah.R;
import com.app.devrah.Views.viewMessage;
import com.app.devrah.pojo.InboxPojo;

import java.util.List;

/**
 * Created by Rizwan Butt on 18-Jul-17.
 */

public class InboxAdapter extends BaseAdapter {

    List<InboxPojo> projectsList;
    Activity activity;
    private LayoutInflater inflater;
    String messageType;


    public InboxAdapter(Activity activity, List<InboxPojo> projectsList,String messageType) {
        this.activity = activity;
        this.projectsList = projectsList;
        this.messageType = messageType;

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

        final ViewHolder holder = new ViewHolder();
        if (inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.custom_layout_for_inbox_items, null);

        holder.data = (TextView) convertView.findViewById(R.id.activities_data);
        holder.to = (TextView) convertView.findViewById(R.id.to);
        holder.date = (TextView) convertView.findViewById(R.id.date);
        holder.project = (TextView) convertView.findViewById(R.id.project);
        holder.boardName = (TextView) convertView.findViewById(R.id.board);
        holder.card = (TextView) convertView.findViewById(R.id.boardName);
        holder.img = (ImageView) convertView.findViewById(R.id.img);



        final InboxPojo inboxPojo = projectsList.get(position);


        if (inboxPojo.isread.equals("0") && messageType.equals("inbox"))
        {
            holder.img.setImageResource(R.drawable.close_email);
        }
        else{

            holder.img.setImageResource(R.drawable.open_email);
        }
        holder.data.setText("Subject : "+inboxPojo.getSubject());
        if(messageType.equals("inbox")) {
            if (!inboxPojo.getFrom().equals("null"))
                holder.to.setText("From : " + inboxPojo.getFrom());
            else
                holder.to.setText("From : ");
        }else {
            if (!inboxPojo.getFrom().equals("null"))
                holder.to.setText("To : " + inboxPojo.getFrom());
            else
                holder.to.setText("To : ");
        }
        holder.date.setText(inboxPojo.getDate());
        if(inboxPojo.getCardif().equals("null"))
        {
            holder.boardName.setText("Card : ");
        }
        else{
            holder.boardName.setText("Card : "+inboxPojo.getCardif());
        }

        if(inboxPojo.getP_id().equals("null"))
        {
            holder.project.setText("Project : ");
        }
        else{
            holder.project.setText("Project : "+inboxPojo.getP_id());
        }

        if(inboxPojo.getB_id().equals("null"))
        {
            holder.card.setText("Board : ");
        }
        else{
            holder.card.setText("Board : "+inboxPojo.getB_id());
        }




        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity,viewMessage.class);
                intent.putExtra("message_id",inboxPojo.getId());
                intent.putExtra("isRead",inboxPojo.getIsread());
                intent.putExtra("to",inboxPojo.getFrom());
                intent.putExtra("project",inboxPojo.getP_id());
                intent.putExtra("card",inboxPojo.getCardif());
                intent.putExtra("board",inboxPojo.getB_id());
                intent.putExtra("subject",inboxPojo.getSubject());
                intent.putExtra("message",inboxPojo.getMessage());
                intent.putExtra("messageType",messageType);

                activity.startActivity(intent);

            }
        });

        return convertView;
    }


    public static class ViewHolder{
        TextView data,to,date,boardName,project,card;
        ImageView img;
    }

}
