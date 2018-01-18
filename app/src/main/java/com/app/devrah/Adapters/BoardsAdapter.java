package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.app.devrah.Views.Board.BoardsActivity;
import com.app.devrah.Views.BoardExtended.BoardExtended;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AQSA SHaaPARR on 6/1/2017.
 */

public class BoardsAdapter extends BaseAdapter {

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;

    List<ProjectsPojo> projectsList;
    String ListItemString;
    Activity activity;
    private LayoutInflater inflater;


    public BoardsAdapter(Activity activity, List<ProjectsPojo> projectsList) {
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

        final ViewHolder holder = new ViewHolder();
        if (inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.row_boards, null);

        holder.data = (TextView) convertView.findViewById(R.id.tvBoardsData);
        ListItemString = projectsList.get(position).getData();
        holder.data.setText(ListItemString);
        holder.attachment= (ImageView) convertView.findViewById(R.id.cardImage);
        holder.favouriteIcon= (ImageView) convertView.findViewById(R.id.favouriteIcon);

       if(projectsList.get(position).getIsFavouriteFromMembers().equals("1")){
           Drawable d = convertView.getResources().getDrawable(R.drawable.star_default);
           holder.favouriteIcon.setImageDrawable(d);
          /* if(projectsList.get(position).getBoardStar().equals("1")){
               Drawable d = convertView.getResources().getDrawable(R.drawable.star_default);
               holder.favouriteIcon.setImageDrawable(d);
           }else  if(projectsList.get(position).getBoardStar().equals("2")){
               Drawable d = convertView.getResources().getDrawable(R.drawable.mark_favourite);
               holder.favouriteIcon.setImageDrawable(d);
           }else if(projectsList.get(position).getBoardStar().equals("3")){
               Drawable d = convertView.getResources().getDrawable(R.drawable.star_default);
               holder.favouriteIcon.setImageDrawable(d);
           }*/
        }
        else if(projectsList.get(position).getIsFavouriteFromMembers().equals("2")){
           Drawable d = convertView.getResources().getDrawable(R.drawable.mark_favourite);
           holder.favouriteIcon.setImageDrawable(d);
           /* Drawable d = convertView.getResources().getDrawable(R.drawable.favourite_star_icon);
            holder.favouriteIcon.setImageDrawable(d);*/
        }else if(projectsList.get(position).getIsFavouriteFromMembers().equals("3")){
           Drawable d = convertView.getResources().getDrawable(R.drawable.favourite_star_icon);
           holder.favouriteIcon.setImageDrawable(d);
       }
        holder.favouriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(projectsList.get(position).getData().equals("No Board found") && projectsList.get(position).getBoardID().equals("")){
                    holder.favouriteIcon.setClickable(false);
                }else {
                    if (projectsList.get(position).getIsFavouriteFromMembers().equals("1")) {
                        mardkBoardStar("2", projectsList.get(position).getBoardID(), projectsList.get(position).getId(), position);
                    } else if (projectsList.get(position).getIsFavouriteFromMembers().equals("2")) {
                        mardkBoardStar("3", projectsList.get(position).getBoardID(), projectsList.get(position).getId(), position);
                    } else {
                        mardkBoardStar("1", projectsList.get(position).getBoardID(), projectsList.get(position).getId(), position);
                    }
                }
            }
        });

        final View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).showl();
                if(projectsList.get(position).getData().equals("No Board found") && projectsList.get(position).getBoardID().equals("")){
                 finalConvertView.setClickable(false);
                }else {

                    Intent intent = new Intent(activity, BoardExtended.class);

                    intent.putExtra("TitleData", projectsList.get(position).getData());
                    intent.putExtra("p_id", projectsList.get(position).getId());
                    intent.putExtra("b_id", projectsList.get(position).getBoardID());
                    intent.putExtra("ptitle", BoardsActivity.ptitle);
                    intent.putExtra("work_board", "1");
                    //  intent.putExtra("list_id",projectsList.get(position).getListId());
                    //intent.putExtra("ptitle",);

                    activity.startActivity(intent);
                }

            }
        });

        return convertView;
    }

    private void mardkBoardStar(final String starType, final String boardId,final String projectId, final int position) {

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.MARDK_FAVOURITE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(starType.equals("1")){
                           projectsList.get(position).setIsFavouriteFromMembers("1");
                        }else if(starType.equals("2")){
                            projectsList.get(position).setIsFavouriteFromMembers("2");
                        }else {
                            projectsList.get(position).setIsFavouriteFromMembers("3");
                        }

                        notifyDataSetChanged();
                        ((BoardsActivity)activity).updateData();

                       /*int current=Integer.valueOf(projectsList.get(position).getBoardStar());
                        if(current>1) {
                            for (int i = 0; i < projectsList.size(); i++) {
                                int swap = Integer.valueOf(projectsList.get(i).getBoardStar());
                                if (current > swap && position > i) {
                                    Collections.swap(projectsList, i, position);
                                } else if (current < swap && position < i) {
                                    Collections.swap(projectsList, i, position);
                                }
                            }
                        }else {
                            int i=projectsList.size()-1;

                            Boolean check=false;
                            while (check==false && i>position) {
                                int swap = Integer.valueOf(projectsList.get(i).getBoardStar());
                                if (swap > current) {
                                    Collections.swap(projectsList, i, position);
                                    check=true;
                                } else {
                                    i--;
                                }
                            }
                            for (int j = 0; j < projectsList.size(); j++) {
                                int swap = Integer.valueOf(projectsList.get(j).getBoardStar());
                                if (current > swap && position > j) {
                                    Collections.swap(projectsList, j, position);
                                } else if (current < swap && position < j) {
                                    Collections.swap(projectsList, j, position);
                                }
                            }
                        }*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {

                    Toast.makeText(activity,"No Internet Connection!",Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(activity,"Connection Timeout error!",Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> params = new HashMap<>();
                SharedPreferences pref =activity.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String userId = pref.getString("user_id", "");
                params.put("userId",userId);
                params.put("board_star", starType);
                params.put("board_id", boardId);
                params.put("project_id", projectId);

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }


    public static class ViewHolder{
        TextView data;
        ImageView favouriteIcon,attachment;
        RecyclerView recyclerView;
    }


}
