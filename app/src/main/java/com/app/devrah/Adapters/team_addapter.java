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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hamza Android on 8/9/2017.
 */

public class team_addapter extends BaseAdapter {

    Activity activity;
    ArrayList<MembersPojo> memberssList;
    private LayoutInflater inflater;


    public  team_addapter(Activity activity, ArrayList<MembersPojo> memberssList) {
        this.activity = activity;
        this.memberssList = memberssList;

    }
    @Override
    public int getCount() {
        return memberssList.size();
    }

    @Override
    public Object getItem(int i) {
        return memberssList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = new ViewHolder();
        if (inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.member_grid_view_row, null);

        holder.name = (TextView) view.findViewById(R.id.alias);
        holder.alias_img = (TextView) view.findViewById(R.id.alias_img);
        holder.imgProfile = (CircleImageView) view.findViewById(R.id.img);

        MembersPojo membersPojo = memberssList.get(i);

        holder.name.setText(membersPojo.getName());



        if((membersPojo.getProfile_pic().equals("null") || membersPojo.getProfile_pic().equals("")) && (membersPojo.getGp_pic().equals("null") || membersPojo.getGp_pic().equals("")))
        {
            holder.alias_img.setVisibility(View.VISIBLE);
            holder.alias_img.setText(membersPojo.getInetial());
        }else if(!membersPojo.getGp_pic().equals("null") && !membersPojo.getGp_pic().equals("")){
            holder.alias_img.setVisibility(View.INVISIBLE);
            Picasso.with(activity)
                    .load(membersPojo.getGp_pic())
                    .into( holder.imgProfile );
        }
        else {
            holder.alias_img.setVisibility(View.INVISIBLE);
            Picasso.with(activity)
                    .load("http://m1.cybussolutions.com/kanban/uploads/profile_pictures/" + membersPojo.getProfile_pic())
                    .into( holder.imgProfile );

        }

        return view;
    }

    public static class ViewHolder{
        TextView name,alias_img ;
        CircleImageView imgProfile;
    }

}
