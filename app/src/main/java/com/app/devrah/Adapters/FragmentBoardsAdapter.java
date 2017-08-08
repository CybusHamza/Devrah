package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.devrah.Views.CardActivity;
import com.app.devrah.R;
import com.app.devrah.pojo.CardAssociatedLabelsPojo;
import com.app.devrah.pojo.ProjectsPojo;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/5/2017.
 */

public class FragmentBoardsAdapter extends BaseAdapter{
     int count=1;
    List<CardAssociatedLabelsPojo> cardLabelsPojoList;
    //CardAssociatedLabelsAdapter cardAssociatedLabelsAdapter;
    List<ProjectsPojo> projectsList;
    List<CardAssociatedLabelsPojo> labelList;
    Activity activity;
    String BoardsListData;
    private LayoutInflater inflater;


    public FragmentBoardsAdapter(Activity activity, List<ProjectsPojo> projectsList,List<CardAssociatedLabelsPojo> labelList) {
        this.activity = activity;
        this.projectsList = projectsList;
        this.labelList = labelList;

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
            convertView = inflater.inflate(R.layout.custom_layout_fragment_boards_list, null);


        holder.favouriteIcon= (ImageView) convertView.findViewById(R.id.favouriteIcon);
        holder.attachment= (ImageView) convertView.findViewById(R.id.cardImage);

        holder.labelsLinearLayout= (LinearLayout) convertView.findViewById(R.id.labelsLayout);
        holder.labelsLinearLayout1= (LinearLayout) convertView.findViewById(R.id.labelsLayout2);
        holder.label1= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label1);
        holder.label2= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label2);
        holder.label3= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label3);
        holder.label4= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label4);
        holder.label5= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label5);
        holder.label6= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label6);
        holder.label7= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label7);
        holder.label8= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label8);

        String[] labels=labelList.get(position).getLabels();
        if (labels.length>0){
            holder.labelsLinearLayout.setVisibility(View.VISIBLE);
            holder.label1.setVisibility(View.VISIBLE);
            holder.label2.setVisibility(View.VISIBLE);
            holder.label3.setVisibility(View.VISIBLE);
            holder.label4.setVisibility(View.VISIBLE);
            if(labels.length==1){
                for(int i=0;i<labels.length;i++) {
                    if(labels[i].equals("blue")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.blue));
                        break;
                    }
                    if(labels[i].equals("sky-blue")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
                        break;
                    }

                }
            }
            if(labels.length==2) {
                for (int i = 0; i < labels.length; i++) {

                        if (labels[i].equals("blue")) {
                            holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.blue));
                            break;
                        }
                        else if (labels[i].equals("sky-blue")) {
                            holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
                            break;
                        }
                        if (labels[i].equals("blue")) {
                            holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.blue));
                            break;
                        }
                        else if (labels[i].equals("sky-blue")) {
                            holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
                            break;
                        }

                }
            }
        }
        /*if(!projectsList.get(position).getBoardAssociatedLabelsId().equals("") || projectsList.get(position).getBoardAssociatedLabelsId()!=null){
            holder.labelsLinearLayout.setVisibility(View.VISIBLE);
            holder.label1.setVisibility(View.VISIBLE);
            holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.colorOrangeRed));
            holder.label2.setVisibility(View.VISIBLE);
            holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.lightGreen));

        }*/

        if(!projectsList.get(position).getAttachment().equals("") || projectsList.get(position).getAttachment()!=null){
            holder.attachment.setVisibility(View.VISIBLE);
            Picasso.with(activity)
                    .load("http://m1.cybussolutions.com/kanban/uploads/card_uploads/" + projectsList.get(position).getAttachment())
                    .into(holder.attachment);
        }
        holder.favouriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==1) {
                    Drawable d = v.getResources().getDrawable(R.drawable.mark_favourite);
                    holder.favouriteIcon.setImageDrawable(d);
                    count=2;
                }
                else if(count==2){
                    Drawable d = v.getResources().getDrawable(R.drawable.favourite_star_icon);
                    holder.favouriteIcon.setImageDrawable(d);

                }
            }
        });
        holder.data = (TextView) convertView.findViewById(R.id.tvFragmentBoardsList);
        holder.data.setText(projectsList.get(position).getData());
            BoardsListData = projectsList.get(position).getData();



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity,CardActivity.class);
                intent.putExtra("CardHeaderData",BoardsListData);
                intent.putExtra("card_id",projectsList.get(position).getId());
                activity.startActivity(intent);

            }
        });

        return convertView;
    }


    public static class ViewHolder{
        TextView data;
        ImageView favouriteIcon;
        ImageView attachment;
        RecyclerView recyclerView;
        LinearLayout labelsLinearLayout,labelsLinearLayout1;
        LinearLayout label1,label2,label3,label4,label5,label6,label7,label8;


    }

}
