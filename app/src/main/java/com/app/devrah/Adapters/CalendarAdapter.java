package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.BoardExtended.BoardExtended;
import com.app.devrah.Views.cards.CardActivity;
import com.app.devrah.pojo.CalendarPojo;
import com.app.devrah.pojo.CardAssociatedCalendarCheckBox;
import com.app.devrah.pojo.CardAssociatedCalendarCoverPojo;
import com.app.devrah.pojo.CardAssociatedCalendarLabelsPojo;
import com.app.devrah.pojo.CardAssociatedCalendarMembersPojo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hamza Android on 10/16/2017.
 */
public class CalendarAdapter extends BaseAdapter implements View.OnTouchListener {
    List<CalendarPojo> projectsList;
    Activity activity;
    private LayoutInflater inflater;
    List<CardAssociatedCalendarLabelsPojo> labelList;
    List<CardAssociatedCalendarMembersPojo> memberList;
    List<CardAssociatedCalendarCoverPojo> coverList;
    int membercount;
    String BoardsListData;
    AlertDialog alertDialog;
    List<CardAssociatedCalendarCheckBox> cardCheckboxPojoList;
    String boardId;
    String projectId;
    String bTitle,pTitle,isWorkBoard;

    public CalendarAdapter(Activity activity, List<CalendarPojo> projectsList, List<CardAssociatedCalendarLabelsPojo> labelList, List<CardAssociatedCalendarMembersPojo> memberList, List<CardAssociatedCalendarCoverPojo> coverList, int membercount, AlertDialog alertDialog,List<CardAssociatedCalendarCheckBox> cardCheckboxPojoList,String boardId,String projectId,String bTitle,String pTitle,String isWorkBoard) {
        this.activity = activity;
        this.projectsList = projectsList;
        this.labelList = labelList;
        this.memberList = memberList;
        this.coverList=coverList;
        this.membercount=membercount;
        this.alertDialog=alertDialog;
        this.cardCheckboxPojoList=cardCheckboxPojoList;
        this.boardId=boardId;
        this.projectId=projectId;
        this.bTitle=bTitle;
        this.pTitle=pTitle;
        this.isWorkBoard=isWorkBoard;
     //   this.list_id=list_id;

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

        ViewHolder vh;
        final ViewHolder holder = new ViewHolder();
        if (inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.cards_calendar_view, null);


        vh = new ViewHolder();
        convertView.setTag(vh);


        holder.favouriteIcon= (ImageView) convertView.findViewById(R.id.favouriteIcon);
        holder.checkBox= (CheckBox) convertView.findViewById(R.id.cbComplete);
        holder.attachment= (ImageView) convertView.findViewById(R.id.cardImage);

        holder.membersView = (LinearLayout)convertView.findViewById(R.id.membersListView);
        holder.labelsView = (LinearLayout)convertView.findViewById(R.id.labelsLayout);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkBox.isChecked()){
                  //  projectsList.get(position).setIsCardComplete("1");
                    updateCardCompleted(boardId,projectsList.get(position).getId(),position,"1",projectsList.get(position).getDueDate(),projectsList.get(position).getStartDate());
                   // ((BoardExtended)activity).updateChildFragmentsCardData(projectsList.get(position).getId(),"1");
                    //holder.checkBox.setChecked(false);
                }else {
                   // projectsList.get(position).setIsCardComplete("0");
                    updateCardCompleted(boardId,projectsList.get(position).getId(),position,"0",projectsList.get(position).getDueDate(),projectsList.get(position).getStartDate());
                   // holder.checkBox.setChecked(true);
                    //  ((BoardExtended)activity).updateChildFragmentsCardData(projectsList.get(position).getId(),"0");

                }
               // notifyDataSetChanged();
            }
        });
        /*if(!projectsList.get(position).getAttachment().equals("") || projectsList.get(position).getAttachment()!=null){
            if(projectsList.get(position).getIsCover().equals("1")) {
                holder.attachment.setVisibility(View.VISIBLE);
                Picasso.with(activity)
                        .load("http://m1.cybussolutions.com/kanban/uploads/card_uploads/" + projectsList.get(position).getAttachment())
                        .into(holder.attachment);
            }
        }*/

        holder.data = (TextView) convertView.findViewById(R.id.tvFragmentBoardsList);
        holder.nOfAttachments = (TextView) convertView.findViewById(R.id.nOfAttachments);
        holder.attachmentIcon = (ImageView) convertView.findViewById(R.id.attachmentIcon);
        holder.subscribe = (ImageView) convertView.findViewById(R.id.subscribedIcon);
        holder.lockIcon = (ImageView) convertView.findViewById(R.id.lockedIcon);
        holder.descriptionIcon = (ImageView) convertView.findViewById(R.id.descriptionIcon);
        holder.dueDate= (TextView) convertView.findViewById(R.id.dateLabel);
        holder.checkboxIcon = (ImageView) convertView.findViewById(R.id.checkboxIcon);
        holder.noOfCheckedCheckbox = (TextView) convertView.findViewById(R.id.noOfCheckedCheckbox);
        holder.headingDueDate = (TextView) convertView.findViewById(R.id.headingDate);
        holder.nOfAttachments.setVisibility(View.GONE);
        holder.attachmentIcon.setVisibility(View.GONE);

        holder.data.setText(projectsList.get(position).getData());
        BoardsListData = projectsList.get(position).getData();

        holder.dueDate.setVisibility(View.GONE);
        if(!projectsList.get(position).getDueDate().equals("null")) {
            holder.dueDate.setVisibility(View.VISIBLE);
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date myDate = null;
            try {
                String myTime=projectsList.get(position).getDueDate();
                myDate = dateFormat.parse(myTime);
                SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM,yy");
                String finalDate = timeFormat.format(myDate);
                holder.dueDate.setText( finalDate );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            holder.dueDate.setText("");

        }
        holder.attachment.setVisibility(View.GONE);
        String cover[]=coverList.get(position).getIsCover();
        String fileName[]=coverList.get(position).getFileName();
        for(int i=0;i<cover.length;i++) {
            if (cover[i].equals("1")) {
                holder.attachment.setVisibility(View.VISIBLE);
                Glide.with(activity)
                        .load(End_Points.IMAGES_BASE_URL+"card_uploads/" + fileName[i])
                        .apply(new RequestOptions().override(activity.getResources().getDimensionPixelSize(R.dimen.cover_size_width),activity.getResources().getDimensionPixelSize(R.dimen.cover_size_height)).centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .skipMemoryCache(true))
                        .into(holder.attachment);
            } else {
                holder.attachment.setVisibility(View.GONE);
            }
        }

        if(!projectsList.get(position).getnOfAttachments().equals("0")){
            holder.dueDate.setVisibility(View.VISIBLE);
            holder.nOfAttachments.setVisibility(View.VISIBLE);
            holder.attachmentIcon.setVisibility(View.VISIBLE);
            holder.nOfAttachments.setText(projectsList.get(position).getnOfAttachments());
        }else {
            holder.nOfAttachments.setVisibility(View.GONE);
            holder.attachmentIcon.setVisibility(View.GONE);
        }
       /* String memberSubscribed[]=memberList.get(position).getMemberSubscribed();
        SharedPreferences pref = activity.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        for(int i=0;i<memberSubscribed.length;i++){
            if(memberList.get(i))

        }*/
        if(memberList.get(position).getMemberSubscribed().equals("1")){
            holder.dueDate.setVisibility(View.VISIBLE);
            holder.subscribe.setVisibility(View.VISIBLE);
        }else {
            holder.subscribe.setVisibility(View.GONE);
        }
        if(projectsList.get(position).getCardDescription().equals("") || projectsList.get(position).getCardDescription().equals("null")){
            holder.descriptionIcon.setVisibility(View.GONE);
        }else {
            holder.dueDate.setVisibility(View.VISIBLE);
            holder.descriptionIcon.setVisibility(View.VISIBLE);
        }
        if(projectsList.get(position).getIsCardComplete().equals("1")){
            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setChecked(false);
        }

        // if(projectsList.get(position).getnOfAttachments().length()>0){
        // holder.nOfAttachments.setText(projectsList.get(position).getnOfAttachments());
        //}
        // SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
      /* if(projectsList.get(position).getIsCardLocked().equals("1") && !projectsList.get(position).getAssignedTo().equals(pref.getString("user_id",""))){
          //  Collections.emptyList();
           projectsList.remove(projectsList.get(position));
        //    notifyDataSetChanged();
       }*/
        if(projectsList.get(position).getIsCardComplete().equals("1") && !holder.dueDate.getText().equals("")){
            holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_green_login));
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date myDate = null;
            try {
                String myTime=projectsList.get(position).getCardCompletionDate();
                myDate = dateFormat.parse(myTime);
                SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM,yy");
                String finalDate = timeFormat.format(myDate);
                holder.dueDate.setText( finalDate );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if (!projectsList.get(position).getIsCardComplete().equals("1") && !holder.dueDate.getText().equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss", Locale.getDefault());

            Date d1 = null;
            Date d2=null;
            Calendar c = Calendar.getInstance();
            //Date = simpledateformat.format(calander.getTime());
            String time=projectsList.get(position).getDuetTime();
            if(time.equals("") || time.equals("null"))
                time="00:00:00";
            Calendar c1=Calendar.getInstance();
            String[] splitDate=projectsList.get(position).getDueDate().split("-");

            c1.set(Calendar.YEAR,Integer.valueOf(splitDate[0]));
            c1.set(Calendar.MONTH,Integer.valueOf(splitDate[1])-1);
            c1.set(Calendar.DATE,Integer.valueOf(splitDate[2]));
            try {

                // d1 = format.parse(projectsList.get(position).getDueDate()+" "+time);
                String date1=format.format(c1.getTime());
                String date=format.format(c.getTime());
                d1=format.parse(date1);
                d2=format.parse(date);
                if(isBeforeDay(d1,d2)){
                    int diff=d2.getDate()-d1.getDate();
                    int diffMonth=d2.getMonth()-d1.getMonth();
                    int diffYear=d2.getYear()-d1.getYear();
                    if(diffYear==0) {
                        if(diffMonth==0) {
                            if (diff > 2)
                                holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_red));
                            else if (diff <= 2 && diff > 0)
                                holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_pink));
                            else
                                holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_orange));
                        }else if(diffMonth>0){
                            int daysBetween = daysBetween(c1.getTime(), c.getTime());
                            if(daysBetween<=2 && daysBetween>0){
                                holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_pink));
                            }else
                                holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_red));
                        }
                    }else if(diffYear>0){
                        int daysBetween = daysBetween(c1.getTime(), c.getTime());
                        if(daysBetween<=2 && daysBetween>0){
                            holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_pink));
                        }else
                            holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_red));
                    }
                }else if(isAfterDay(d1,d2)){
                    int diff=d1.getDate()-d2.getDate();
                    int diffMonth=d1.getMonth()-d2.getMonth();
                    int diffYear=d1.getYear()-d2.getYear();

                    if(diffYear==0) {
                        if(diffMonth==0) {
                            if (diff >= 2)
                                holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_gray));
                            else if (diff < 2)
                                holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_orange));
                            else
                                holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_orange));
                        }else if(diffMonth>0){
                            int daysBetween = daysBetween(c.getTime(), c1.getTime());
                            if(daysBetween<2){
                                holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_orange));
                            }else
                                holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_gray));
                        }
                    }else if(diffYear>0){
                        int daysBetween = daysBetween(c.getTime(), c1.getTime());
                        if(daysBetween<2){
                            holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_orange));
                        }else
                            holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_gray));
                    }
                }else {
                    holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_orange));
                }
                /*long diff = d2.getDate() - d1.getDate();
               // long diffDays = diff / (24 * 60 * 60 * 1000);
                if(diff>0 && diff<2){
                    holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_gray));
                }else if(diff<0 && diff>-2){
                    holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_red));
                }else {
                    holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_orange));
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            holder.dueDate.setBackground(null);
        }


        if(cardCheckboxPojoList.get(position).getTotalCheckBoxes()>0){
            holder.checkboxIcon.setVisibility(View.VISIBLE);
            holder.noOfCheckedCheckbox.setVisibility(View.VISIBLE);
            holder.noOfCheckedCheckbox.setText(cardCheckboxPojoList.get(position).getCheckedCheckBox()+"/"+cardCheckboxPojoList.get(position).getTotalCheckBoxes());
        }else {
            holder.checkboxIcon.setVisibility(View.GONE);
            holder.noOfCheckedCheckbox.setVisibility(View.GONE);
        }
        if (position > 0) {
            if (projectsList.get(position).getDueDate().equalsIgnoreCase(projectsList.get(position - 1).getDueDate())) {
                holder.headingDueDate.setVisibility(View.GONE);
            } else {
                holder.headingDueDate.setText("Due : "+projectsList.get(position).getDueDate());
                holder.headingDueDate.setVisibility(View.VISIBLE);
            }
        } else {
            holder.headingDueDate.setText("Due : "+projectsList.get(position).getDueDate());
            holder.headingDueDate.setVisibility(View.VISIBLE);
        }
        if(projectsList.get(position).getIsCardLocked().equals("1")){
            holder.dueDate.setVisibility(View.VISIBLE);
            holder.lockIcon.setVisibility(View.VISIBLE);
        }else {
            holder.lockIcon.setVisibility(View.GONE);
        }
        holder.membersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Intent intent = new Intent(activity,CardActivity.class);
                intent.putExtra("CardHeaderData", projectsList.get(position).getData());
                intent.putExtra("card_id",projectsList.get(position).getId());
                intent.putExtra("cardduedate",projectsList.get(position).getDueDate());
                intent.putExtra("cardduetime",projectsList.get(position).getDuetTime());
                intent.putExtra("cardstartdate",projectsList.get(position).getStartDate());
                intent.putExtra("cardstarttime",projectsList.get(position).getStartTime());
                intent.putExtra("cardCompletionDate", projectsList.get(position).getCardCompletionDate());
                intent.putExtra("cardDescription",projectsList.get(position).getCardDescription());
                intent.putExtra("isComplete",projectsList.get(position).getIsCardComplete());
                intent.putExtra("isLocked",projectsList.get(position).getIsCardLocked());
                intent.putExtra("isSubscribed",memberList.get(position).getMemberSubscribed());
                intent.putExtra("list_id",projectsList.get(position).getListId());
                intent.putExtra("project_id", projectId);
                intent.putExtra("board_id", boardId);
                intent.putExtra("board_name", bTitle);
                intent.putExtra("project_title", pTitle);
                intent.putExtra("work_board", isWorkBoard);
                intent.putExtra("fromMyCards","false");
                activity.finish();
                activity.startActivity(intent);
            }
        });
        holder.labelsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Intent intent = new Intent(activity,CardActivity.class);
                intent.putExtra("CardHeaderData", projectsList.get(position).getData());
                intent.putExtra("card_id",projectsList.get(position).getId());
                intent.putExtra("cardduedate",projectsList.get(position).getDueDate());
                intent.putExtra("cardduetime",projectsList.get(position).getDuetTime());
                intent.putExtra("cardstartdate",projectsList.get(position).getStartDate());
                intent.putExtra("cardstarttime",projectsList.get(position).getStartTime());
                intent.putExtra("cardCompletionDate", projectsList.get(position).getCardCompletionDate());
                intent.putExtra("cardDescription",projectsList.get(position).getCardDescription());
                intent.putExtra("isComplete",projectsList.get(position).getIsCardComplete());
                intent.putExtra("isLocked",projectsList.get(position).getIsCardLocked());
                intent.putExtra("isSubscribed",memberList.get(position).getMemberSubscribed());
                intent.putExtra("list_id",projectsList.get(position).getListId());
                intent.putExtra("project_id", projectId);
                intent.putExtra("board_id", boardId);
                intent.putExtra("board_name", bTitle);
                intent.putExtra("project_title", pTitle);
                intent.putExtra("work_board", isWorkBoard);
                intent.putExtra("fromMyCards","false");
                activity.finish();
                activity.startActivity(intent);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity,CardActivity.class);
                intent.putExtra("CardHeaderData", projectsList.get(position).getData());
                intent.putExtra("card_id",projectsList.get(position).getId());
                intent.putExtra("cardduedate",projectsList.get(position).getDueDate());
                intent.putExtra("cardduetime",projectsList.get(position).getDuetTime());
                intent.putExtra("cardstartdate",projectsList.get(position).getStartDate());
                intent.putExtra("cardstarttime",projectsList.get(position).getStartTime());
                intent.putExtra("cardCompletionDate", projectsList.get(position).getCardCompletionDate());
                intent.putExtra("cardDescription",projectsList.get(position).getCardDescription());
                intent.putExtra("isComplete",projectsList.get(position).getIsCardComplete());
                intent.putExtra("isLocked",projectsList.get(position).getIsCardLocked());
                intent.putExtra("isSubscribed",memberList.get(position).getMemberSubscribed());
                intent.putExtra("list_id",projectsList.get(position).getListId());
                intent.putExtra("project_id", projectId);
                intent.putExtra("board_id", boardId);
                intent.putExtra("board_name", bTitle);
                intent.putExtra("project_title", pTitle);
                intent.putExtra("work_board", isWorkBoard);
                intent.putExtra("fromMyCards","false");
                activity.finish();
                activity.startActivity(intent);

            }
        });

        String[] labels=labelList.get(position).getLabels();
        String[] labelNames=labelList.get(position).getLabelText();
        if(labels.length<1){
            HorizontalScrollView scrollView= (HorizontalScrollView) convertView.findViewById(R.id.labelScroller);
            scrollView.setVisibility(View.GONE);
        }else {
            HorizontalScrollView scrollView= (HorizontalScrollView) convertView.findViewById(R.id.labelScroller);
            scrollView.setVisibility(View.VISIBLE);
        }
        holder.labelsView.removeAllViews();

        for (int i = 0; i < labels.length; i++) {
            TextView image = new TextView(activity);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(90,50);
            layoutParams.setMargins(10,0,0,0);
            //image.setLayoutParams(new android.view.ViewGroup.LayoutParams(60, 60));
            image.setLayoutParams(layoutParams);
            // image.setBackground(activity.getResources().getDrawable(R.drawable.bg_circle));
            // image.setImageDrawable(activity.getResources().getDrawable(R.drawable.bg__dashboard_calender));20170704090751.jpg
            image.setMaxHeight(20);
            image.setMaxWidth(50);

            // image.setText(l);
            //image.setBackgroundColor(activity.getResources().getColor(R.color.light_black));
            // image.setTextColor(activity.getResources().getColor(R.color.black));
            image.setGravity(Gravity.CENTER);

            // Adds the view to the layout

            if(labels[i].equals("blue")) {
                image.setBackgroundColor(activity.getResources().getColor(R.color.blue));
                //  break;
            }else if(labels[i].equals("sky-blue")) {
                image.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
                //break;
            }else if(labels[i].equals("red")) {
                image.setBackgroundColor(activity.getResources().getColor(R.color.colorRed));
                //break;
            }else if(labels[i].equals("yellow")) {
                image.setBackgroundColor(activity.getResources().getColor(R.color.colorYellow));
                //break;
            }else if(labels[i].equals("purple")) {
                image.setBackgroundColor(activity.getResources().getColor(R.color.purple));
                //textView.setText(labelNames[i]);
                //break;
            }else if(labels[i].equals("pink")) {
                image.setBackgroundColor(activity.getResources().getColor(R.color.pink));
                //break;
            }else if(labels[i].equals("orange")) {
                image.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));
                //break;
            }else if(labels[i].equals("black")) {
                image.setBackgroundColor(activity.getResources().getColor(R.color.black));
                //break;
            }else if(labels[i].equals("green")) {
                image.setBackgroundColor(activity.getResources().getColor(R.color.colorGreen));

                //break;
            }else if(labels[i].equals("dark-green")) {
                image.setBackgroundColor(activity.getResources().getColor(R.color.darkgreen));
                //break;
            }else if(labels[i].equals("lime")) {
                image.setBackgroundColor(activity.getResources().getColor(R.color.green));
                //break;
            }
            holder.labelsView.addView(image);
        }
        String imageUrl[]=memberList.get(position).getMembers();
        String initials[]=memberList.get(position).getInitials();
        String gp_picture[]=memberList.get(position).getGp_pictures();
        if(imageUrl.length<1){
            HorizontalScrollView scrollView= (HorizontalScrollView) convertView.findViewById(R.id.memberScroller);
            scrollView.setVisibility(View.GONE);
        }else {
            HorizontalScrollView scrollView= (HorizontalScrollView) convertView.findViewById(R.id.memberScroller);
            scrollView.setVisibility(View.VISIBLE);
        }
        holder.membersView.removeAllViews();
        // if(membercount==0) {
        for (int i = 0; i < imageUrl.length; i++) {

            if ((imageUrl[i].equals("null") || imageUrl[i].equals("")) && (gp_picture[i].equals("null") || gp_picture[i].equals(""))) {
                TextView image = new TextView(activity);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(activity.getResources().getDimensionPixelSize(R.dimen.circle_image_size_width),activity.getResources().getDimensionPixelSize(R.dimen.circle_image_size_height));
                layoutParams.setMargins(activity.getResources().getDimensionPixelSize(R.dimen.margin_left),0,0,0);
                //image.setLayoutParams(new android.view.ViewGroup.LayoutParams(60, 60));
                image.setLayoutParams(layoutParams);
                image.setBackground(activity.getResources().getDrawable(R.drawable.round_bkg));
                // image.setImageDrawable(activity.getResources().getDrawable(R.drawable.bg__dashboard_calender));20170704090751.jpg
                image.setMaxHeight(20);
                image.setMaxWidth(20);
                image.setText(initials[i]);
                //image.setBackgroundColor(activity.getResources().getColor(R.color.light_black));
                image.setTextColor(activity.getResources().getColor(R.color.black));
                image.setGravity(Gravity.CENTER);

                // Adds the view to the layout
                holder.membersView.addView(image);
            } else if(!imageUrl[i].equals("null") && !imageUrl[i].equals("")){
                CircleImageView image = new CircleImageView(activity);
                image.setLayoutParams(new android.view.ViewGroup.LayoutParams(activity.getResources().getDimensionPixelSize(R.dimen.circle_alias_size_width),activity.getResources().getDimensionPixelSize(R.dimen.circle_alias_size_height)));
                image.setPadding(7,0,0,0);
                // image.setImageDrawable(activity.getResources().getDrawable(R.drawable.bg__dashboard_calender));20170704090751.jpg
                image.setMaxHeight(20);
                image.setMaxWidth(20);

                Picasso.with(activity)
                        .load(End_Points.IMAGES_BASE_URL+"profile_pictures/" + imageUrl[i])
                        .into(image);

                // Adds the view to the layout
                holder.membersView.addView(image);
            }else {
                CircleImageView image = new CircleImageView(activity);
                image.setLayoutParams(new android.view.ViewGroup.LayoutParams(activity.getResources().getDimensionPixelSize(R.dimen.circle_alias_size_width),activity.getResources().getDimensionPixelSize(R.dimen.circle_alias_size_height)));
                image.setPadding(7,0,0,0);
                // image.setImageDrawable(activity.getResources().getDrawable(R.drawable.bg__dashboard_calender));20170704090751.jpg
                image.setMaxHeight(20);
                image.setMaxWidth(20);

                Picasso.with(activity)
                        .load( gp_picture[i])
                        .into(image);

                // Adds the view to the layout
                holder.membersView.addView(image);
            }
        }
//            membercount=1;
//        }
       /* SharedPreferences pref = activity.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
       if(projectsList.get(position).getIsCardLocked().equals("1") && !projectsList.get(position).getAssignedTo().equals(pref.getString("user_id",""))){
          // projectsList.remove(position);
          // notifyDataSetChanged();
       }*/
        return convertView;
    }
    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
    public static boolean isAfterDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isAfterDay(cal1, cal2);
    }
    public static boolean isAfterDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) return false;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) return true;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) return false;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) return true;
        return cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR);
    }
    public static boolean isBeforeDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isBeforeDay(cal1, cal2);
    }
    public static boolean isBeforeDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) return true;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) return false;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) return true;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) return false;
        return cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static class ViewHolder{
        LinearLayout membersView;
        LinearLayout labelsView;
        TextView data,dueDate,nOfAttachments,noOfCheckedCheckbox,headingDueDate;
        ImageView favouriteIcon,attachmentIcon,lockIcon;
        ImageView attachment,subscribe,descriptionIcon,checkboxIcon;
        CheckBox checkBox;

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
    public  void updateCardCompleted(final String boardId,final String cardId,final int position,final String isCompleted,final String dueDate,final String startDate) {
      //  final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_CARD_COMPLETED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(isCompleted.equals("1")){
                            projectsList.get(position).setIsCardComplete("1");
                            projectsList.get(position).setCardCompletionDate(response);
                        }else if(isCompleted.equals("0")){
                            projectsList.get(position).setIsCardComplete("0");
                        }
                        Intent i = new Intent("updateComplete");
                        // Data you need to pass to activity
                        i.putExtra("cardId",projectsList.get(position).getId());
                        i.putExtra("isComplete",isCompleted);

                       // activity.sendBroadcast(i);

                        notifyDataSetChanged();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("check your internet connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();

                                }
                            })
                            .show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("boardId",boardId);
                params.put("projectid",projectId);
                params.put("strt_dt",startDate);
                params.put("end_dt",dueDate);
                params.put("card_id",cardId);
                params.put("isComp",isCompleted);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);

    }


}