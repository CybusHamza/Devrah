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
import com.app.devrah.Views.BoardExtended.BoardExtended;
import com.app.devrah.pojo.FavouritesPojo;

import java.util.List;

/**
 * Created by Rizwan Butt on 13-Jun-17.
 */

public class FavouritesAdapter extends BaseAdapter {
    List<FavouritesPojo> projectsList;
    Activity activity;
    private LayoutInflater inflater;


    public FavouritesAdapter(Activity activity, List<FavouritesPojo> projectsList) {
        this.activity = activity;
        this.projectsList = projectsList;

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

        FavouritesAdapter.ViewHolder holder = new FavouritesAdapter.ViewHolder();
        if (inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.custom_layout_for_favourites, null);

        holder.data = (TextView) convertView.findViewById(R.id.tvFavouritesData);
        holder.p_name = (TextView) convertView.findViewById(R.id.projectName);
        holder.favouriteIcon = (ImageView) convertView.findViewById(R.id.btnFavourite);
        holder.data.setText('"'+projectsList.get(position).getData()+'"');
        holder.p_name.setText("Project: "+projectsList.get(position).getP_name());

        if(projectsList.get(position).getData().equals("No favourites found") && projectsList.get(position).getBrdid().equals("")) {
            holder.favouriteIcon.setVisibility(View.INVISIBLE);
            holder.p_name.setText("");
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!projectsList.get(position).getData().equals("No favourites found") && !projectsList.get(position).getBrdid().equals("")) {

                    Intent intent = new Intent(activity, BoardExtended.class);
                    intent.putExtra("TitleData", projectsList.get(position).getData());
                    intent.putExtra("ptitle", projectsList.get(position).getP_name());
                    intent.putExtra("p_id", projectsList.get(position).getP_status());
                    intent.putExtra("b_id", projectsList.get(position).getBrdid());
                    intent.putExtra("work_board", projectsList.get(position).getBoardType());
                    intent.putExtra("activity", "favourites");
                    activity.finish();

                    activity.startActivity(intent);
                }

            }
        });

        return convertView;
    }


    public static class ViewHolder{
        TextView data,p_name;
        ImageView favouriteIcon;
    }

}
