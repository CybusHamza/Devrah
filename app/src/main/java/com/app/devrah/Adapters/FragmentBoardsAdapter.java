package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.app.devrah.Views.CardActivity;
import com.app.devrah.R;
import com.app.devrah.pojo.CardAssociatedLabelsPojo;
import com.app.devrah.pojo.CardAssociatedMembersPojo;
import com.app.devrah.pojo.ProjectsPojo;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/5/2017.
 */

public class FragmentBoardsAdapter extends BaseAdapter{
     int count=1;
     int count1=0;
    int membercount;
    List<CardAssociatedLabelsPojo> cardLabelsPojoList;
    //CardAssociatedLabelsAdapter cardAssociatedLabelsAdapter;
    List<ProjectsPojo> projectsList;
    List<CardAssociatedLabelsPojo> labelList;
    List<CardAssociatedMembersPojo> memberList;
    Activity activity;
    String BoardsListData;
    private LayoutInflater inflater;


    public FragmentBoardsAdapter(Activity activity, List<ProjectsPojo> projectsList,List<CardAssociatedLabelsPojo> labelList,List<CardAssociatedMembersPojo> memberList,int membercount) {
        this.activity = activity;
        this.projectsList = projectsList;
        this.labelList = labelList;
        this.memberList = memberList;
        this.membercount=membercount;

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

        holder.membersView = (LinearLayout)convertView.findViewById(R.id.membersListView);


        holder.labelsLinearLayout= (LinearLayout) convertView.findViewById(R.id.labelsLayout);
        holder.labelsLinearLayout1= (LinearLayout) convertView.findViewById(R.id.labelsLayout2);
        holder.labelsLinearLayout2= (LinearLayout) convertView.findViewById(R.id.labelsLayout3);
        holder.labelsLinearLayout3= (LinearLayout) convertView.findViewById(R.id.labelsLayout4);
        holder.labelsLinearLayout4= (LinearLayout) convertView.findViewById(R.id.labelsLayout5);
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
        holder.label12= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label12);
        holder.label13= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label13);
        holder.label14= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label14);
        holder.label15= (LinearLayout) convertView.findViewById(R.id.row_cardscreen_label15);
        holder.labelText1= (TextView) convertView.findViewById(R.id.labelName1);
        holder.labelText2= (TextView) convertView.findViewById(R.id.labelName2);
        holder.labelText3= (TextView) convertView.findViewById(R.id.labelName3);
        holder.labelText4= (TextView) convertView.findViewById(R.id.labelName4);
        holder.labelText5= (TextView) convertView.findViewById(R.id.labelName5);
        holder.labelText6= (TextView) convertView.findViewById(R.id.labelName6);
        holder.labelText7= (TextView) convertView.findViewById(R.id.labelName7);
        holder.labelText8= (TextView) convertView.findViewById(R.id.labelName8);
        holder.labelText9= (TextView) convertView.findViewById(R.id.labelName9);
        holder.labelText10= (TextView) convertView.findViewById(R.id.labelName10);
        holder.labelText11= (TextView) convertView.findViewById(R.id.labelName11);
        holder.labelText12= (TextView) convertView.findViewById(R.id.labelName12);
        holder.labelText13= (TextView) convertView.findViewById(R.id.labelName13);
        holder.labelText14= (TextView) convertView.findViewById(R.id.labelName14);
        holder.labelText15= (TextView) convertView.findViewById(R.id.labelName15);
        holder.labelsLinearLayout.setVisibility(View.GONE);
        holder.labelsLinearLayout1.setVisibility(View.GONE);
        holder.labelsLinearLayout2.setVisibility(View.GONE);
        holder.labelsLinearLayout3.setVisibility(View.GONE);
        holder.labelsLinearLayout4.setVisibility(View.GONE);
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
        holder.label12.setVisibility(View.INVISIBLE);
        holder.label13.setVisibility(View.INVISIBLE);
        holder.label14.setVisibility(View.INVISIBLE);
        holder.label15.setVisibility(View.INVISIBLE);

        String[] labels=labelList.get(position).getLabels();
        String[] labelNames=labelList.get(position).getLabelText();
       int length=labels.length;
        if (length>0){
            count1=0;
            holder.labelsLinearLayout.setVisibility(View.VISIBLE);
           /* holder.label1.setVisibility(View.VISIBLE);
            holder.label2.setVisibility(View.VISIBLE);
            holder.label3.setVisibility(View.VISIBLE);*/
            if(length>3){
                holder.labelsLinearLayout1.setVisibility(View.VISIBLE);
               /* holder.label4.setVisibility(View.VISIBLE);
                holder.label5.setVisibility(View.VISIBLE);
                holder.label6.setVisibility(View.VISIBLE);*/

            }if(length>7){
                holder.labelsLinearLayout1.setVisibility(View.VISIBLE);
                /*holder.label4.setVisibility(View.VISIBLE);
                holder.label5.setVisibility(View.VISIBLE);
                holder.label6.setVisibility(View.VISIBLE);*/
                holder.labelsLinearLayout2.setVisibility(View.VISIBLE);
               /* holder.label7.setVisibility(View.VISIBLE);
                holder.label8.setVisibility(View.VISIBLE);
                holder.label9.setVisibility(View.VISIBLE);*/

            }if(length>9){
                holder.labelsLinearLayout1.setVisibility(View.VISIBLE);
                /*holder.label4.setVisibility(View.VISIBLE);
                holder.label5.setVisibility(View.VISIBLE);
                holder.label6.setVisibility(View.VISIBLE);*/
                holder.labelsLinearLayout2.setVisibility(View.VISIBLE);
                /*holder.label7.setVisibility(View.VISIBLE);
                holder.label8.setVisibility(View.VISIBLE);
                holder.label9.setVisibility(View.VISIBLE);*/
                holder.labelsLinearLayout3.setVisibility(View.VISIBLE);
               /* holder.label10.setVisibility(View.VISIBLE);
                holder.label11.setVisibility(View.VISIBLE);
                holder.label12.setVisibility(View.VISIBLE);*/

            }if(length>12){
                holder.labelsLinearLayout1.setVisibility(View.VISIBLE);
               /* holder.label4.setVisibility(View.VISIBLE);
                holder.label5.setVisibility(View.VISIBLE);
                holder.label6.setVisibility(View.VISIBLE);*/
                holder.labelsLinearLayout2.setVisibility(View.VISIBLE);
               /* holder.label7.setVisibility(View.VISIBLE);
                holder.label8.setVisibility(View.VISIBLE);
                holder.label9.setVisibility(View.VISIBLE);*/
                holder.labelsLinearLayout3.setVisibility(View.VISIBLE);
               /* holder.label10.setVisibility(View.VISIBLE);
                holder.label11.setVisibility(View.VISIBLE);
                holder.label12.setVisibility(View.VISIBLE);*/
                holder.labelsLinearLayout4.setVisibility(View.VISIBLE);
               /* holder.label13.setVisibility(View.VISIBLE);
                holder.label14.setVisibility(View.VISIBLE);
                holder.label15.setVisibility(View.VISIBLE);*/
            }

                for(int i=0;i<labels.length;i++) {
                    if(labels[i].equals("blue")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setVisibility(View.VISIBLE);
                        TextView textView=(TextView)convertView.findViewById(holder.labelNamearrayIds[count1]);
                        textView.setText("");
                        //textView.setText(labelNames[i]);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.blue));

                        count1++;
                      //  break;
                    }else if(labels[i].equals("sky-blue")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
                        TextView textView=(TextView)convertView.findViewById(holder.labelNamearrayIds[count1]);
                        textView.setText("");
                        //textView.setText(labelNames[i]);
                        count1++;
                        //break;
                    }else if(labels[i].equals("red")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorRed));
                        TextView textView=(TextView)convertView.findViewById(holder.labelNamearrayIds[count1]);
                        textView.setText("");
                        //textView.setText(labelNames[i]);
                        count1++;
                        //break;
                    }else if(labels[i].equals("yellow")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorYellow));
                        TextView textView=(TextView)convertView.findViewById(holder.labelNamearrayIds[count1]);
                        textView.setText("");
                        //textView.setText(labelNames[i]);
                        count1++;
                        //break;
                    }else if(labels[i].equals("purple")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.purple));
                        TextView textView=(TextView)convertView.findViewById(holder.labelNamearrayIds[count1]);
                        textView.setText("");
                        //textView.setText(labelNames[i]);
                        //break;
                    }else if(labels[i].equals("pink")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.pink));
                        TextView textView=(TextView)convertView.findViewById(holder.labelNamearrayIds[count1]);
                        textView.setText("");
                        //textView.setText(labelNames[i]);
                        count1++;
                        //break;
                    }else if(labels[i].equals("orange")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));
                        TextView textView=(TextView)convertView.findViewById(holder.labelNamearrayIds[count1]);
                        textView.setText("");
                        //textView.setText(labelNames[i]);
                        count1++;
                        //break;
                    }else if(labels[i].equals("black")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.black));
                        TextView textView=(TextView)convertView.findViewById(holder.labelNamearrayIds[count1]);
                        textView.setText("");
                        //textView.setText(labelNames[i]);
                        count1++;
                        //break;
                    }else if(labels[i].equals("green")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorGreen));
                        TextView textView=(TextView)convertView.findViewById(holder.labelNamearrayIds[count1]);
                        textView.setText("");
                        //textView.setText(labelNames[i]);
                        count1++;
                        //break;
                    }else if(labels[i].equals("dark-green")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.darkgreen));
                        TextView textView=(TextView)convertView.findViewById(holder.labelNamearrayIds[count1]);
                        textView.setText("");
                        //textView.setText(labelNames[i]);
                        count1++;
                        //break;
                    }else if(labels[i].equals("lime")) {
                        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(holder.arrayIds[count1]);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.lightGreen));
                        TextView textView=(TextView)convertView.findViewById(holder.labelNamearrayIds[count1]);
                        textView.setText("");
                        //textView.setText(labelNames[i]);
                        count1++;
                        //break;
                    }

                }

            }
        /*else if(length==2) {
            for (int i = 0; i < labels.length; i++) {
                if(count1==0) {

                }
                else if(count1==1) {


            }
        }else if(length==3) {
            for (int i = 0; i < labels.length; i++) {

                }

            }
        }*/else {
            count1=0;
            holder.labelsLinearLayout.setVisibility(View.GONE);
            holder.labelsLinearLayout1.setVisibility(View.GONE);
            holder.labelsLinearLayout2.setVisibility(View.GONE);
            holder.labelsLinearLayout3.setVisibility(View.GONE);
            holder.labelsLinearLayout4.setVisibility(View.GONE);
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
            holder.label12.setVisibility(View.INVISIBLE);
            holder.label13.setVisibility(View.INVISIBLE);
            holder.label14.setVisibility(View.INVISIBLE);
            holder.label15.setVisibility(View.INVISIBLE);
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
        holder.nOfAttachments = (TextView) convertView.findViewById(R.id.nOfAttachments);
        holder.attachmentIcon = (ImageView) convertView.findViewById(R.id.attachmentIcon);
        if(!projectsList.get(position).getnOfAttachments().equals("0")){
            holder.nOfAttachments.setVisibility(View.VISIBLE);
            holder.attachmentIcon.setVisibility(View.VISIBLE);
        holder.nOfAttachments.setText(projectsList.get(position).getnOfAttachments());
        }else {
            holder.nOfAttachments.setVisibility(View.INVISIBLE);
            holder.attachmentIcon.setVisibility(View.INVISIBLE);
        }
        holder.data.setText(projectsList.get(position).getData());
            BoardsListData = projectsList.get(position).getData();
        holder.dueDate= (TextView) convertView.findViewById(R.id.dateLabel);
        holder.dueDate.setText(projectsList.get(position).getDueDate());
       // if(projectsList.get(position).getnOfAttachments().length()>0){
       // holder.nOfAttachments.setText(projectsList.get(position).getnOfAttachments());
        //}


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

        String imageUrl[]=memberList.get(position).getMembers();
        String initials[]=memberList.get(position).getInitials();
        holder.membersView.removeAllViews();
       // if(membercount==0) {
            for (int i = 0; i < imageUrl.length; i++) {

                if (imageUrl[i].equals("null")) {
                    TextView image = new TextView(activity);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(80,80);
                    layoutParams.setMargins(5,0,0,0);
                    //image.setLayoutParams(new android.view.ViewGroup.LayoutParams(60, 60));
                    image.setLayoutParams(layoutParams);
                    // image.setImageDrawable(activity.getResources().getDrawable(R.drawable.bg__dashboard_calender));20170704090751.jpg
                    image.setMaxHeight(20);
                    image.setMaxWidth(20);
                    image.setText(initials[i]);
                    image.setBackgroundColor(activity.getResources().getColor(R.color.light_black));
                    image.setTextColor(activity.getResources().getColor(R.color.black));
                    image.setGravity(Gravity.CENTER);

                    // Adds the view to the layout
                    holder.membersView.addView(image);
                } else {
                    ImageView image = new ImageView(activity);
                    image.setLayoutParams(new android.view.ViewGroup.LayoutParams(100, 80));
                    // image.setImageDrawable(activity.getResources().getDrawable(R.drawable.bg__dashboard_calender));20170704090751.jpg
                    image.setMaxHeight(20);
                    image.setMaxWidth(20);

                    Picasso.with(activity)
                            .load("http://m1.cybussolutions.com/kanban/uploads/profile_pictures/" + imageUrl[i])
                            .into(image);

                    // Adds the view to the layout
                    holder.membersView.addView(image);
                }
            }
//            membercount=1;
//        }

        return convertView;
    }


    public static class ViewHolder{

        LinearLayout membersView;
        TextView data,dueDate,nOfAttachments;
        ImageView favouriteIcon,attachmentIcon;
        ImageView attachment;
        RecyclerView recyclerView;
        LinearLayout labelsLinearLayout,labelsLinearLayout1,labelsLinearLayout2,labelsLinearLayout3,labelsLinearLayout4;
        LinearLayout label1,label2,label3,label4,label5,label6,label7,label8,label9,label10,label11,label12,label13,label14,label15;
        TextView labelText1,labelText2,labelText3,labelText4,labelText5,labelText6,labelText7,labelText8,labelText9,labelText10,labelText11,labelText12,labelText13,labelText14,labelText15;
        int[] arrayIds = new int[] {R.id.row_cardscreen_label1,R.id.row_cardscreen_label2,R.id.row_cardscreen_label3,R.id.row_cardscreen_label4,R.id.row_cardscreen_label5,R.id.row_cardscreen_label6,R.id.row_cardscreen_label7,R.id.row_cardscreen_label8,R.id.row_cardscreen_label9,R.id.row_cardscreen_label10,R.id.row_cardscreen_label11,R.id.row_cardscreen_label12,R.id.row_cardscreen_label13,R.id.row_cardscreen_label14,R.id.row_cardscreen_label15};
        int[] labelNamearrayIds=new int[]{R.id.labelName1,R.id.labelName2,R.id.labelName3,R.id.labelName4,R.id.labelName5,R.id.labelName6,R.id.labelName7,R.id.labelName8,R.id.labelName9,R.id.labelName10,R.id.labelName11,R.id.labelName12,R.id.labelName13,R.id.labelName14,R.id.labelName15};

    }

}
