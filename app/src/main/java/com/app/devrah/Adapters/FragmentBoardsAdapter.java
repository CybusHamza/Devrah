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
     int count1=0;
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
        holder.labelsLinearLayout2= (LinearLayout) convertView.findViewById(R.id.labelsLayout3);
        holder.label1= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label1);
        holder.label2= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label2);
        holder.label3= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label3);
        holder.label4= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label4);
        holder.label5= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label5);
        holder.label6= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label6);
        holder.label7= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label7);
        holder.label8= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label8);
        holder.label9= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label9);
        holder.label10= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label10);
        holder.label11= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label11);

        String[] labels=labelList.get(position).getLabels();
       int length=labels.length;
        if (length>0){
            count1=0;
            holder.labelsLinearLayout.setVisibility(View.VISIBLE);
            holder.label1.setVisibility(View.VISIBLE);
            holder.label2.setVisibility(View.VISIBLE);
            holder.label3.setVisibility(View.VISIBLE);
            holder.label4.setVisibility(View.VISIBLE);
            if(length>4){
                holder.labelsLinearLayout1.setVisibility(View.VISIBLE);
                holder.label5.setVisibility(View.VISIBLE);
                holder.label6.setVisibility(View.VISIBLE);
                holder.label7.setVisibility(View.VISIBLE);
                holder.label8.setVisibility(View.VISIBLE);
            }if(length>8){
                holder.labelsLinearLayout2.setVisibility(View.VISIBLE);
                holder.label9.setVisibility(View.VISIBLE);
                holder.label10.setVisibility(View.VISIBLE);
                holder.label11.setVisibility(View.VISIBLE);
            }

                for(int i=0;i<labels.length;i++) {
                    if(labels[i].equals("blue")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.blue));
                        count1++;
                      //  break;
                    }else if(labels[i].equals("sky-blue")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
                        count1++;
                        //break;
                    }else if(labels[i].equals("red")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorRed));
                        count1++;
                        //break;
                    }else if(labels[i].equals("yellow")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorYellow));
                        count1++;
                        //break;
                    }else if(labels[i].equals("purple")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.purple));
                        //break;
                    }else if(labels[i].equals("pink")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.pink));
                        count1++;
                        //break;
                    }else if(labels[i].equals("orange")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));
                        count1++;
                        //break;
                    }else if(labels[i].equals("black")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.black));
                        count1++;
                        //break;
                    }else if(labels[i].equals("green")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorGreen));
                        count1++;
                        //break;
                    }else if(labels[i].equals("dark-green")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.darkgreen));
                        count1++;
                        //break;
                    }else if(labels[i].equals("lime")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.lightGreen));
                        count1++;
                        //break;
                    }

                }

            }
        /*else if(length==2) {
            for (int i = 0; i < labels.length; i++) {
                if(count1==0) {
                    if (labels[i].equals("blue")) {

                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.blue));
                        count1++;

                    } else if (labels[i].equals("sky-blue")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
                        count1++;
                    }else if (labels[i].equals("red")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.colorRed));
                        count1++;
                    }else if (labels[i].equals("yellow")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.colorYellow));
                        count1++;
                    }else if (labels[i].equals("purple")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.purple));
                        count1++;
                    }else if (labels[i].equals("pink")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.pink));
                        count1++;
                    }else if (labels[i].equals("orange")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));
                        count1++;
                    }
                }
                else if(count1==1) {
                    if (labels[i].equals("blue")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.blue));
                    } else if (labels[i].equals("sky-blue")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
                    }else if (labels[i].equals("red")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.colorRed));
                    }else if (labels[i].equals("yellow")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.colorYellow));
                    }else if (labels[i].equals("purple")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.purple));
                    }else if (labels[i].equals("pink")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.pink));
                    }else if (labels[i].equals("orange")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));
                    }
                }

            }
        }else if(length==3) {
            for (int i = 0; i < labels.length; i++) {
                if(count1==0) {
                    if (labels[i].equals("blue")) {

                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.blue));
                        count1++;

                    } else if (labels[i].equals("sky-blue")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
                        count1++;
                    }else if (labels[i].equals("red")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.colorRed));
                        count1++;
                    }else if (labels[i].equals("yellow")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.colorYellow));
                        count1++;
                    }else if (labels[i].equals("purple")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.purple));
                        count1++;
                    }else if (labels[i].equals("pink")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.pink));
                        count1++;
                    }else if (labels[i].equals("orange")) {
                        holder.label1.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));
                        count1++;
                    }
                }
                else if(count1==1) {
                    if (labels[i].equals("blue")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.blue));
                        count1++;
                    } else if (labels[i].equals("sky-blue")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
                        count1++;
                    }else if (labels[i].equals("red")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.colorRed));
                        count1++;
                    }else if (labels[i].equals("yellow")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.colorYellow));
                        count1++;
                    }else if (labels[i].equals("purple")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.purple));
                        count1++;
                    }else if (labels[i].equals("pink")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.pink));
                        count1++;
                    }else if (labels[i].equals("orange")) {
                        holder.label2.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));
                        count1++;
                    }
                }else if(count1==2) {
                    if (labels[i].equals("blue")) {
                        holder.label3.setBackgroundColor(activity.getResources().getColor(R.color.blue));
                        count1++;
                    } else if (labels[i].equals("sky-blue")) {
                        holder.label3.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
                        count1++;
                    }else if (labels[i].equals("red")) {
                        holder.label3.setBackgroundColor(activity.getResources().getColor(R.color.colorRed));
                        count1++;
                    }else if (labels[i].equals("yellow")) {
                        holder.label3.setBackgroundColor(activity.getResources().getColor(R.color.colorYellow));
                        count1++;
                    }else if (labels[i].equals("purple")) {
                        holder.label3.setBackgroundColor(activity.getResources().getColor(R.color.purple));
                        count1++;
                    }else if (labels[i].equals("pink")) {
                        holder.label3.setBackgroundColor(activity.getResources().getColor(R.color.pink));
                        count1++;
                    }else if (labels[i].equals("orange")) {
                        holder.label3.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));
                        count1++;
                    }
                }

            }
        }*/else {
            count1=0;
            holder.labelsLinearLayout.setVisibility(View.GONE);
            holder.labelsLinearLayout1.setVisibility(View.GONE);
            holder.labelsLinearLayout2.setVisibility(View.GONE);
            holder.label1.setVisibility(View.INVISIBLE);
            holder.label2.setVisibility(View.INVISIBLE);
            holder.label3.setVisibility(View.INVISIBLE);
            holder.label4.setVisibility(View.INVISIBLE);
            holder.label5.setVisibility(View.INVISIBLE);
            holder.label6.setVisibility(View.INVISIBLE);
            holder.label7.setVisibility(View.INVISIBLE);
            holder.label8.setVisibility(View.INVISIBLE);
            holder.label9.setVisibility(View.INVISIBLE);
            holder.label10.setVisibility(View.INVISIBLE);
            holder.label11.setVisibility(View.INVISIBLE);
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
        LinearLayout labelsLinearLayout,labelsLinearLayout1,labelsLinearLayout2;
        LinearLayout label1,label2,label3,label4,label5,label6,label7,label8,label9,label10,label11;

        int[] arrayIds = new int[] {R.id.row_cardscreen_label1,R.id.row_cardscreen_label2,R.id.row_cardscreen_label3,R.id.row_cardscreen_label4,R.id.row_cardscreen_label5,R.id.row_cardscreen_label6,R.id.row_cardscreen_label7,R.id.row_cardscreen_label8,R.id.row_cardscreen_label9,R.id.row_cardscreen_label10,R.id.row_cardscreen_label11};


    }

}
