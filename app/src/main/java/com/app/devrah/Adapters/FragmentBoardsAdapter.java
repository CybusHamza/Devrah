package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.devrah.R;
import com.app.devrah.Views.BoardExtended.BoardExtended;
import com.app.devrah.Views.cards.CardActivity;
import com.app.devrah.pojo.CardAssociatedCheckBox;
import com.app.devrah.pojo.CardAssociatedCoverPojo;
import com.app.devrah.pojo.CardAssociatedLabelsPojo;
import com.app.devrah.pojo.CardAssociatedMembersPojo;
import com.app.devrah.pojo.ProjectsPojo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
    List<CardAssociatedCoverPojo> coverList;
    Activity activity;
    String BoardsListData;
    private LayoutInflater inflater;
    String list_id;
    List<CardAssociatedCheckBox> cardCheckboxPojoList;
    BroadcastReceiver broadcastReceiver;

    public FragmentBoardsAdapter(Activity activity, List<ProjectsPojo> projectsList, List<CardAssociatedLabelsPojo> labelList, List<CardAssociatedMembersPojo> memberList, List<CardAssociatedCoverPojo> coverList, int membercount, String list_id, List<CardAssociatedCheckBox> cardCheckboxPojoList, BroadcastReceiver broadcastReceiver) {
        this.activity = activity;
        this.projectsList = projectsList;
        this.labelList = labelList;
        this.memberList = memberList;
        this.membercount=membercount;
        this.coverList=coverList;
        this.list_id=list_id;
        this.cardCheckboxPojoList=cardCheckboxPojoList;
        this.broadcastReceiver=broadcastReceiver;

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
//        holder.memberScrollView = (HorizontalScrollView) convertView.findViewById(R.id.memberScroller);
//        holder.labelScrollView = (HorizontalScrollView) convertView.findViewById(R.id.labelScroller);

        holder.membersView = (LinearLayout)convertView.findViewById(R.id.membersListView);
        holder.labelsView = (LinearLayout)convertView.findViewById(R.id.labelsLayout);

        holder.attachment.setVisibility(View.GONE);
        String cover[]=coverList.get(position).getIsCover();
        String fileName[]=coverList.get(position).getFileName();
        for(int i=0;i<cover.length;i++) {
            if (cover[i].equals("1")) {
                holder.attachment.setVisibility(View.VISIBLE);
                Glide.with(activity)
                        .load("http://m1.cybussolutions.com/devrah/uploads/card_uploads/" + fileName[i])
                        .apply(new RequestOptions().override(activity.getResources().getDimensionPixelSize(R.dimen.cover_size_width),activity.getResources().getDimensionPixelSize(R.dimen.cover_size_height)).centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .skipMemoryCache(true))
                       .into(holder.attachment);
            } else {
                holder.attachment.setVisibility(View.GONE);
            }
        }
      /*  Picasso.Builder builder = new Picasso.Builder(activity);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }});*/

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
        holder.lockIcon = (ImageView) convertView.findViewById(R.id.lockedIcon);
        holder.subscribe = (ImageView) convertView.findViewById(R.id.subscribedIcon);
        holder.descriptionIcon = (ImageView) convertView.findViewById(R.id.descriptionIcon);
        holder.checkboxIcon = (ImageView) convertView.findViewById(R.id.checkboxIcon);
        holder.noOfCheckedCheckbox = (TextView) convertView.findViewById(R.id.noOfCheckedCheckbox);
        holder.dueDate= (TextView) convertView.findViewById(R.id.dateLabel);
        holder.nOfAttachments.setVisibility(View.GONE);
        holder.attachmentIcon.setVisibility(View.GONE);

        holder.data.setText(projectsList.get(position).getData());
            BoardsListData = projectsList.get(position).getData();

        holder.dueDate.setVisibility(View.GONE);
        if(!projectsList.get(position).getDueDate().equals("null") && !projectsList.get(position).getDueDate().equals("0000-00-00")) {
            holder.dueDate.setVisibility(View.VISIBLE);
            holder.dueDate.setText(projectsList.get(position).getDueDate() );
        }
        else {
            holder.dueDate.setText("");

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
        if(projectsList.get(position).getIsCardComplete().equals("1") && !holder.dueDate.getText().equals("")){
            holder.dueDate.setBackground(activity.getResources().getDrawable(R.drawable.bg_green_login));
        }else {
            holder.dueDate.setBackground(null);
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
        if(cardCheckboxPojoList.get(position).getTotalCheckBoxes()>0){
            holder.dueDate.setVisibility(View.VISIBLE);
            holder.checkboxIcon.setVisibility(View.VISIBLE);
            holder.noOfCheckedCheckbox.setVisibility(View.VISIBLE);
            holder.noOfCheckedCheckbox.setText(cardCheckboxPojoList.get(position).getCheckedCheckBox()+"/"+cardCheckboxPojoList.get(position).getTotalCheckBoxes());
        }else {
            holder.checkboxIcon.setVisibility(View.GONE);
            holder.noOfCheckedCheckbox.setVisibility(View.GONE);
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
                Intent intent = new Intent(activity,CardActivity.class);
                intent.putExtra("CardHeaderData", projectsList.get(position).getData());
                intent.putExtra("card_id",projectsList.get(position).getId());
                intent.putExtra("cardduedate",projectsList.get(position).getDueDate());
                intent.putExtra("cardduetime",projectsList.get(position).getDuetTime());
                intent.putExtra("cardstartdate",projectsList.get(position).getStartDate());
                intent.putExtra("cardstarttime",projectsList.get(position).getStartTime());
                intent.putExtra("cardDescription",projectsList.get(position).getCardDescription());
                intent.putExtra("isComplete",projectsList.get(position).getIsCardComplete());
                intent.putExtra("isLocked",projectsList.get(position).getIsCardLocked());
                intent.putExtra("isSubscribed",memberList.get(position).getMemberSubscribed());
                intent.putExtra("list_id",list_id);
                intent.putExtra("project_id", BoardExtended.projectId);
                intent.putExtra("board_id", BoardExtended.boardId);
                intent.putExtra("board_name", BoardExtended.bTitle);
                intent.putExtra("project_title", BoardExtended.pTitle);
                intent.putExtra("fromMyCards","false");
                activity.finish();
                activity.startActivity(intent);
            }
        });
        holder.labelsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,CardActivity.class);
                intent.putExtra("CardHeaderData", projectsList.get(position).getData());
                intent.putExtra("card_id",projectsList.get(position).getId());
                intent.putExtra("cardduedate",projectsList.get(position).getDueDate());
                intent.putExtra("cardduetime",projectsList.get(position).getDuetTime());
                intent.putExtra("cardstartdate",projectsList.get(position).getStartDate());
                intent.putExtra("cardstarttime",projectsList.get(position).getStartTime());
                intent.putExtra("cardDescription",projectsList.get(position).getCardDescription());
                intent.putExtra("isComplete",projectsList.get(position).getIsCardComplete());
                intent.putExtra("isLocked",projectsList.get(position).getIsCardLocked());
                intent.putExtra("isSubscribed",memberList.get(position).getMemberSubscribed());
                intent.putExtra("list_id",list_id);
                intent.putExtra("project_id", BoardExtended.projectId);
                intent.putExtra("board_id", BoardExtended.boardId);
                intent.putExtra("board_name", BoardExtended.bTitle);
                intent.putExtra("project_title", BoardExtended.pTitle);
                intent.putExtra("fromMyCards","false");
                activity.finish();
                activity.startActivity(intent);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity,CardActivity.class);
                intent.putExtra("CardHeaderData", projectsList.get(position).getData());
                intent.putExtra("card_id",projectsList.get(position).getId());
                intent.putExtra("cardduedate",projectsList.get(position).getDueDate());
                intent.putExtra("cardduetime",projectsList.get(position).getDuetTime());
                intent.putExtra("cardstartdate",projectsList.get(position).getStartDate());
                intent.putExtra("cardstarttime",projectsList.get(position).getStartTime());
                intent.putExtra("cardDescription",projectsList.get(position).getCardDescription());
                intent.putExtra("isComplete",projectsList.get(position).getIsCardComplete());
                intent.putExtra("isLocked",projectsList.get(position).getIsCardLocked());
                intent.putExtra("isSubscribed",memberList.get(position).getMemberSubscribed());
                intent.putExtra("list_id",list_id);
                intent.putExtra("project_id", BoardExtended.projectId);
                intent.putExtra("board_id", BoardExtended.boardId);
                intent.putExtra("board_name", BoardExtended.bTitle);
                intent.putExtra("project_title", BoardExtended.pTitle);
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
                    image.setBackground(activity.getResources().getDrawable(R.drawable.bg_circle));
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
                    // image.setImageDrawable(activity.getResources().getDrawable(R.drawable.bg__dashboard_calender));20170704090751.jpg
                   image.setPadding(7,0,0,0);
                    image.setMaxHeight(20);
                    image.setMaxWidth(20);

                    Picasso.with(activity)
                            .load("http://m1.cybussolutions.com/devrah/uploads/profile_pictures/" + imageUrl[i])
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

    public static class ViewHolder{

        LinearLayout membersView;
        LinearLayout labelsView;
        TextView data,dueDate,nOfAttachments,noOfCheckedCheckbox;
        ImageView favouriteIcon,attachmentIcon,lockIcon;
        ImageView attachment,subscribe,descriptionIcon,checkboxIcon;
       // RecyclerView recyclerView;
        //LinearLayout labelsLinearLayout,labelsLinearLayout1,labelsLinearLayout2,labelsLinearLayout3,labelsLinearLayout4;
        //LinearLayout label1,label2,label3,label4,label5,label6,label7,label8,label9,label10,label11,label12,label13,label14,label15;
        //TextView labelText1,labelText2,labelText3,labelText4,labelText5,labelText6,labelText7,labelText8,labelText9,labelText10,labelText11,labelText12,labelText13,labelText14,labelText15;
       /* int[] arrayIds = new int[] {R.id.row_cardscreen_label1,R.id.row_cardscreen_label2,R.id.row_cardscreen_label3,R.id.row_cardscreen_label4,R.id.row_cardscreen_label5,R.id.row_cardscreen_label6,R.id.row_cardscreen_label7,R.id.row_cardscreen_label8,R.id.row_cardscreen_label9,R.id.row_cardscreen_label10,R.id.row_cardscreen_label11,R.id.row_cardscreen_label12,R.id.row_cardscreen_label13,R.id.row_cardscreen_label14,R.id.row_cardscreen_label15};
        int[] labelNamearrayIds=new int[]{R.id.labelName1,R.id.labelName2,R.id.labelName3,R.id.labelName4,R.id.labelName5,R.id.labelName6,R.id.labelName7,R.id.labelName8,R.id.labelName9,R.id.labelName10,R.id.labelName11,R.id.labelName12,R.id.labelName13,R.id.labelName14,R.id.labelName15};
*/
    }

}
