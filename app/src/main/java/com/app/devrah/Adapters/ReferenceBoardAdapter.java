package com.app.devrah.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.app.devrah.Views.Board.BoardsActivity;
import com.app.devrah.pojo.ProjectsPojo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static android.content.Context.MODE_PRIVATE;
import static com.app.devrah.Network.End_Points.UPDATE_BG_BOARD;
import static com.app.devrah.Network.End_Points.UPDATE_COLOR_BG_LIST;

/**
 * Created by Rizwan Butt on 03-Aug-17.
 */

public class ReferenceBoardAdapter extends BaseAdapter {
    Button noColor;
    Button blueColor;
    Button pinkColor;
    Button skyBlueColor ;
    Button greenColor;
    Button orangeColor;
    Button purpleColor;
    Button yellowColor;
    Button redColor,lightGreenColor,seaGreenColor,blackColor ;
    Button chooseCover;
    private static final int REQUEST_PERMISSIONS=0;
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;

    List<ProjectsPojo> projectsList;
    String ListItemString;
    Activity activity;
    private LayoutInflater inflater;


    public ReferenceBoardAdapter(Activity activity, List<ProjectsPojo> projectsList) {
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
        holder.background = (RelativeLayout) convertView.findViewById(R.id.layout_background);
        holder.backgroundImage = (ImageView) convertView.findViewById(R.id.backgroundImage);
        ListItemString = projectsList.get(position).getData();
        holder.data.setText(ListItemString);
        holder.favouriteIcon= (ImageView) convertView.findViewById(R.id.favouriteIcon);
        holder.editIcon= (ImageView) convertView.findViewById(R.id.editIcon);
        String bgColor=projectsList.get(position).getBackGroundPicture();
        GradientDrawable drawable = (GradientDrawable) holder.background.getBackground();
        drawable.setColor(activity.getResources().getColor(R.color.transparent));
        if(projectsList.get(position).getIsPicture().equals("0")) {
            holder.backgroundImage.setVisibility(View.GONE);
            switch (bgColor) {
                case "":
                    //  holder.background.setBackground(activity.getResources().getDrawable(R.drawable.outer_border_message_screen));
                    drawable.setColor(activity.getResources().getColor(R.color.transparent));
                    break;
                case "00A2E8":
                    drawable.setColor(activity.getResources().getColor(R.color.blue));
                    break;
                case "FF80CE":
                    drawable.setColor(activity.getResources().getColor(R.color.pinkColor));
                    break;
                case "EB5A46":
                    drawable.setColor(activity.getResources().getColor(R.color.colorRed));
                    break;
                case "4D4D4D":
                    drawable.setColor(activity.getResources().getColor(R.color.black_light));
                    break;
                case "51E898":
                    drawable.setColor(activity.getResources().getColor(R.color.seaGreen));
                    break;
                case "61BD4F":
                    drawable.setColor(activity.getResources().getColor(R.color.lightGreen));
                    break;
                case "A349A4":
                    drawable.setColor(activity.getResources().getColor(R.color.colorPurple));
                    break;
                case "FFF200":
                    drawable.setColor(activity.getResources().getColor(R.color.colorYellow));
                    break;
                case "FF7F27":
                    drawable.setColor(activity.getResources().getColor(R.color.colorOrange));
                    break;
                case "00C2E0":
                    drawable.setColor(activity.getResources().getColor(R.color.skyBlue));
                    break;
                case "3E9A2C":
                    drawable.setColor(activity.getResources().getColor(R.color.darkgreen));
                    break;
                default:
                    // holder.background.setBackground(activity.getResources().getDrawable(R.drawable.outer_border_message_screen));
                    drawable.setColor(activity.getResources().getColor(R.color.transparent));
                    break;
            }
        }else if(projectsList.get(position).getIsPicture().equals("1")){
            holder.backgroundImage.setVisibility(View.VISIBLE);
            Picasso.with(activity)
                    .load(End_Points.IMAGES_BASE_URL+"boards_background/"+bgColor.trim())
                    .transform(new RoundedCornersTransformation(10,1))
                    .resize(1070,190)
                    .centerCrop()
                    .into(holder.backgroundImage);
        }else {
            drawable.setColor(activity.getResources().getColor(R.color.transparent));
        }
        if(projectsList.get(position).getData().equals("No Board found") && projectsList.get(position).getBoardID().equals("")){
            holder.editIcon.setVisibility(View.INVISIBLE);
        }else {
            holder.editIcon.setVisibility(View.VISIBLE);
        }
        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(activity, holder.editIcon);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.board_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Toast.makeText(getActivity(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()) {
                            case R.id.changeBackground:
                                changeListColorPopup(projectsList.get(position).getBoardID(), projectsList.get(position).getId(), position);
                                //  boardMenu.setBackgroundColor(getActivity().getResources().getColor(R.color.lightGreen));
                                // TODO Something when menu item selected
                                return true;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        if(projectsList.get(position).getReferenceBoardStar().equals("1")){
            Drawable d = convertView.getResources().getDrawable(R.drawable.star_default);
            holder.favouriteIcon.setImageDrawable(d);
        }else  if(projectsList.get(position).getReferenceBoardStar().equals("2")){
            Drawable d = convertView.getResources().getDrawable(R.drawable.mark_favourite);
            holder.favouriteIcon.setImageDrawable(d);
        }else  if(projectsList.get(position).getReferenceBoardStar().equals("3")){
            Drawable d = convertView.getResources().getDrawable(R.drawable.favourite_star_icon);
            holder.favouriteIcon.setImageDrawable(d);
        }
        holder.favouriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(projectsList.get(position).getData().equals("No Board found") && projectsList.get(position).getBoardID().equals("")){
                    holder.favouriteIcon.setClickable(false);
                }else {
                    if (projectsList.get(position).getReferenceBoardStar().equals("1")) {
                        mardkBoardStar("2", projectsList.get(position).getBoardID(),projectsList.get(position).getId(), position);
                    } else if (projectsList.get(position).getReferenceBoardStar().equals("2")) {
                        mardkBoardStar("3", projectsList.get(position).getBoardID(),projectsList.get(position).getId(), position);
                    } else {
                        mardkBoardStar("1", projectsList.get(position).getBoardID(),projectsList.get(position).getId(), position);
                    }
                }
            }
        });

        final View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(projectsList.get(position).getData().equals("No Board found") && projectsList.get(position).getBoardID().equals("")){
                    finalConvertView.setClickable(false);
                }else {

                    //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).showl();
                    Intent intent = new Intent(activity, BoardExtended.class);
                    intent.putExtra("TitleData", projectsList.get(position).getData());
                    intent.putExtra("p_id", projectsList.get(position).getId());
                    intent.putExtra("b_id", projectsList.get(position).getBoardID());
                    intent.putExtra("ptitle", activity.getIntent().getStringExtra("ptitle"));
                    intent.putExtra("work_board", "0");

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
                            projectsList.get(position).setReferenceBoardStar("1");
                        }else if(starType.equals("2")){
                            projectsList.get(position).setReferenceBoardStar("2");
                        }else {
                            projectsList.get(position).setReferenceBoardStar("3");
                        }
                        notifyDataSetChanged();

                        ((BoardsActivity)activity).updateData();
                       /* int current=Integer.valueOf(projectsList.get(position).getReferenceBoardStar());
                        if(current>1) {
                            for (int i = 0; i < projectsList.size(); i++) {
                                int swap = Integer.valueOf(projectsList.get(i).getReferenceBoardStar());
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
                                int swap = Integer.valueOf(projectsList.get(i).getReferenceBoardStar());
                                if (swap > current) {
                                    Collections.swap(projectsList, i, position);
                                    check=true;
                                } else {
                                    i--;
                                }
                            }
                            for (int j = 0; j < projectsList.size(); j++) {
                                int swap = Integer.valueOf(projectsList.get(j).getReferenceBoardStar());
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
                params.put("project_id",projectId );

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
        ImageView favouriteIcon,attachment,editIcon,backgroundImage;
        RelativeLayout background;
    }

    private void changeListColorPopup(final String boardId, final String projectId, final int position) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View customView = inflater.inflate(R.layout.custom_alert_dialog_for_bg_color_board,null);
        final AlertDialog alertDialog = new  AlertDialog.Builder(activity).create();

        alertDialog.setCancelable(false);
        noColor = (Button)customView.findViewById(R.id.noColor);
        blueColor = (Button)customView.findViewById(R.id.blueColor);
        pinkColor = (Button)customView.findViewById(R.id.pinkColor);
        skyBlueColor = (Button)customView.findViewById(R.id.skyBlueColor);
        greenColor = (Button)customView.findViewById(R.id.greenColor);
        orangeColor = (Button)customView.findViewById(R.id.orangeColor);
        purpleColor = (Button)customView.findViewById(R.id.purpleColor);
        yellowColor = (Button)customView.findViewById(R.id.yellowColor);
        redColor = (Button)customView.findViewById(R.id.redColor);
        lightGreenColor = (Button)customView.findViewById(R.id.lightGreenColor);
        seaGreenColor = (Button)customView.findViewById(R.id.seaGreenColor);
        blackColor = (Button)customView.findViewById(R.id.blackColor);
        chooseCover = (Button)customView.findViewById(R.id.uploadImage);
        ImageView crossIcon= (ImageView) customView.findViewById(R.id.crossIcon);
        crossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        noColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                changeBoardBgColor("",boardId,projectId,position);
            }
        });
        blueColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.blue));
                changeBoardBgColor("00A2E8",boardId,projectId,position);
            }
        });
        pinkColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.brown));
                changeBoardBgColor("FF80CE",boardId,projectId,position);
            }
        });
        skyBlueColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.light_black));
                changeBoardBgColor("00C2E0",boardId,projectId,position);
            }
        });
        greenColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                changeBoardBgColor("3E9A2C",boardId,projectId,position);
            }
        });
        orangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorOrange));
                changeBoardBgColor("FF7F27",boardId,projectId,position);
            }
        });
        purpleColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //  tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPurple));
                changeBoardBgColor("A349A4",boardId,projectId,position);
            }
        });
        yellowColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorYellow));
                changeBoardBgColor("FFF200",boardId,projectId,position);
            }
        });
        redColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorRed));
                changeBoardBgColor("EB5A46",boardId,projectId,position);
            }
        });
        lightGreenColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorRed));
                changeBoardBgColor("61BD4F",boardId,projectId,position);
            }
        });
        seaGreenColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorRed));
                changeBoardBgColor("51E898",boardId,projectId,position);
            }
        });
        blackColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorRed));
                changeBoardBgColor("4D4D4D",boardId,projectId,position);
            }
        });
        chooseCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDialogForProfile(boardId,projectId,position);
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(customView);
        alertDialog.show();
    }
    private void startDialogForProfile(String boardId,String projectId,int position) {

                        /*Intent intent = new Intent(MainActivity.this, AlbumSelectActivity.class);
                        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 4);
                        startActivityForResult(intent, Constants.REQUEST_CODE);*/

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            if (Build.VERSION.SDK_INT > 22) {

                activity.requestPermissions(new String[]{Manifest.permission
                                .WRITE_EXTERNAL_STORAGE},
                        1);
                ((BoardsActivity)activity).data(boardId,position);
            }

        } else {
            ((BoardsActivity)activity).uploadImage1(boardId,projectId,position);
            // upload();

        }

    }
    public void changeBoardBgColor(final String bgColor,final String boardId,final String projectId,final int position) {

        final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_BG_BOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equals("")) {
                            //   holder.background.setBackground(activity.getResources().getDrawable(R.drawable.outer_border_message_screen));
                            projectsList.get(position).setBackGroundPicture(bgColor);
                            projectsList.get(position).setIsPicture("0");
                            notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "check your internet connection", Toast.LENGTH_SHORT).show();
                    /*new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    getActivity().finish();
                                }
                            })
                            .show();*/
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                   /* new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    getActivity().finish();
                                }
                            })
                            .show();*/
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", boardId);
                params.put("projectId", projectId);
                params.put("userId", pref.getString("user_id",""));
                params.put("bgcolor", bgColor);
                params.put("is_picture", "0");
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

