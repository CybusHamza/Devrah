package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.devrah.R;
import com.app.devrah.pojo.MembersPojo;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AQSA SHaaPARR on 6/12/2017.
 */

public class AdapterMembers extends BaseAdapter {

   List<MembersPojo> myList;
    String ListItemString;
    Activity activity;
    private LayoutInflater inflater;


public AdapterMembers(Activity activity,List<MembersPojo> customList){


    this.myList = customList;
    this.activity = activity;

}


    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        ViewHolder holder = new ViewHolder();
        if (inflater==null){

            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        if (convertView == null){


            convertView = inflater.inflate(R.layout.custom_dialog_members_card_row,null);
            holder.userID = (TextView)convertView.findViewById(R.id.tvNameOfPerson);
            holder.profile = (CircleImageView) convertView.findViewById(R.id.imageView);
            holder.alias = (TextView) convertView.findViewById(R.id.alias_img);
            String currentImage=myList.get(position).getProfile_pic();

            if(myList.get(position).getProfile_pic().equals("null") || myList.get(position).getProfile_pic().equals(""))
            {
                holder.alias.setVisibility(View.VISIBLE);
                holder.alias.setText(myList.get(position).getInetial());
            }
            else{
                Picasso.with(activity)
                        .load("http://m1.cybussolutions.com/kanban/uploads/profile_pictures/" + currentImage)
                        .into(holder.profile);

            }

          /*  Picasso.with(activity)
                    .load("http://m1.cybussolutions.com/kanban/uploads/profile_pictures/" + currentImage)
                    .into(holder.profile);*/
            holder.Name = (TextView)convertView.findViewById(R.id.tvIdOfPerson);
            holder.Name.setText(myList.get(position).getInetial());

            holder.userID.setText(myList.get(position).getName());
            //holder.userID.setText(myList.get(position).getProfile_pic());


//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//
//                }
//            });
        }



        return convertView;
    }

    public static class ViewHolder{
        TextView userID,Name,alias;
        CircleImageView profile;
    }


}
