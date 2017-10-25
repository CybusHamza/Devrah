package com.app.devrah.Adapters;

/**
 * Created by Hamza Android on 10/24/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.app.devrah.Holders.ViewUtils;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.CheckList_Comments;
import com.app.devrah.pojo.CommentsPojo;
import com.app.devrah.pojo.Level;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ishratkhan on 24/02/16.
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder> {

    List<CommentsPojo> projectsList;
    Context activity;
    ProgressDialog ringProgressDialog;
    public RvAdapter(Context con, List<CommentsPojo> projectsList) {
        activity = con;
        this.projectsList = projectsList;
    }


    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RvViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_item, null));
    }

    @Override
    public void onBindViewHolder(final RvViewHolder holder, final int position) {
       // DataModal dataModal = projectsList.get(position);
        holder.tv.setText(projectsList.get(position).getComments());
        holder.userName.setText(projectsList.get(position).getFullName());
        holder.setLevel(projectsList.get(position).getLevel());
        String fileType=projectsList.get(position).getFileType();
        if(projectsList.get(position).getIsFile().equals("1") && (fileType.equals("image/jpeg") || fileType.equals("image/png")) ){
            holder.attachment.setVisibility(View.VISIBLE);
            holder.tv.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
            Picasso.with(activity)
                    .load("http://m1.cybussolutions.com/kanban/uploads/comment_images/" + projectsList.get(position).getComments())
                    .resize(250,200)
                    .into(holder.attachment);
        }else {
            holder.tv.setVisibility(View.VISIBLE);
            holder.attachment.setVisibility(View.GONE);
            holder.edit.setVisibility(View.VISIBLE);
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editComment(projectsList.get(position).getId(),position,holder.tv.getText().toString());
            }
        });
        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(projectsList.get(position).getLevel()==2){
                    replyToComment(projectsList.get(position).getParentId(),projectsList.get(position).getCardId(),projectsList.get(position).getChecklistId(),position);

                }else
                replyToComment(projectsList.get(position).getId(),projectsList.get(position).getCardId(),projectsList.get(position).getChecklistId(),position);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Confirmation!")
                        .setCancelText("Cancel")
                        .setConfirmText("OK").setContentText("Do you really want to remove checkbox?")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                delete_comment(projectsList.get(position).getId(),position);

                                sDialog.dismiss();
                            }
                        })
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }

    public void addItem(CommentsPojo item) {
        projectsList.add(item);
    }

    class RvViewHolder extends RecyclerView.ViewHolder {
        TextView tv,edit,reply,userName;
        View itemView;
        View marker;
        ImageView delete,attachment;

        public RvViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv = (TextView) itemView.findViewById(R.id.rv_item_tv);
            userName = (TextView) itemView.findViewById(R.id.userName);
            edit = (TextView) itemView.findViewById(R.id.edit);
            reply = (TextView) itemView.findViewById(R.id.reply);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            attachment = (ImageView) itemView.findViewById(R.id.attachment);
            marker =itemView.findViewById(R.id.marker);
        }

        public void setLevel(int level) {
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
            );

            if (level == Level.LEVEL_TWO) {
                params.setMarginStart(ViewUtils.getLevelOneMargin());
                marker.setBackground(ContextCompat.getDrawable(activity,R.drawable.marker_c));
            } else if (level == Level.LEVEL_THREE) {
                params.setMarginStart(ViewUtils.getLevelTwoMargin());
                marker.setBackground(ContextCompat.getDrawable(activity,R.drawable.marker_cc));
            }

            itemView.setLayoutParams(params);
        }
    }
    private void replyToComment(final String id, final String cardId, final String checklistId, final int position){
        LayoutInflater inflater = LayoutInflater.from(activity);
        View customView = inflater.inflate(R.layout.edit_comment_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(customView);
        alertDialog.show();

        TextView cancel, save;
        final EditText etCardName= (EditText) customView.findViewById(R.id.etComment);
        final TextView tvheading= (TextView) customView.findViewById(R.id.headingTitle);
        tvheading.setText("Reply");

        save = (TextView) customView.findViewById(R.id.btn_save);
        save.setText("Send");
        cancel = (TextView) customView.findViewById(R.id.btn_cancel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check=etCardName.getText().toString();
                if(!check.equals("") && check!="" && check.trim().length()>0) {
                    // UpdateProjectName(etCardName.getText().toString());
                    reply_comment(id,cardId,checklistId,position,etCardName.getText().toString());
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etCardName.getWindowToken(), 0);

                    alertDialog.dismiss();

                }else {
                    Toast.makeText(activity,"Please enter comment",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                alertDialog.dismiss();

            }
        });
    }
    private void editComment(final String id, final int i, String name) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View customView = inflater.inflate(R.layout.edit_comment_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(customView);
        alertDialog.show();

        TextView cancel, save;
        final EditText etCardName= (EditText) customView.findViewById(R.id.etComment);
        final TextView tvheading= (TextView) customView.findViewById(R.id.headingTitle);
        tvheading.setText("Edit Comment");
        etCardName.setText(name);

        save = (TextView) customView.findViewById(R.id.btn_save);
        cancel = (TextView) customView.findViewById(R.id.btn_cancel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check=etCardName.getText().toString();
                if(!check.equals("") && check!="" && check.trim().length()>0) {
                    // UpdateProjectName(etCardName.getText().toString());
                    edit_comment(id,i,etCardName.getText().toString());
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etCardName.getWindowToken(), 0);

                    alertDialog.dismiss();

                }else {
                    Toast.makeText(activity,"Please enter comment",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                alertDialog.dismiss();

            }
        });

    }
    private void delete_comment(final String id, final int i) {
        ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.DELETECOMMENT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {

                    projectsList.remove(i);
                    notifyDataSetChanged();
                }
            }

        }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   loading.dismiss();
                ringProgressDialog.dismiss();
                String message = null;
                if (error instanceof NoConnectionError) {

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
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }
    private void edit_comment(final String id, final int i,final String comment) {
        ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.EDITCOMMENTS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals("0"))) {
                    projectsList.get(i).setComments(comment);

                   // projectsList.remove(i);
                    notifyDataSetChanged();
                }
            }

        }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   loading.dismiss();
                ringProgressDialog.dismiss();
                String message = null;
                if (error instanceof NoConnectionError) {

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
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("comments", comment);
                SharedPreferences pref = activity.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                params.put("updated_by",  pref.getString("user_id", ""));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }
    public void reply_comment(final String id,final String cardId,final String checkListId,final int pos,final String comments) {

        final ProgressDialog ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.REPLYCOMMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        if(!response.equals("0")){
                            ((CheckList_Comments)activity).updateData();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

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
                params.put("card_id", cardId);
                params.put("check_id",checkListId);
                params.put("comments",comments);
                SharedPreferences pref = activity.getSharedPreferences("UserPrefs", Context.MODE_APPEND);
                String userID = pref.getString("user_id", "");
                String fullName=pref.getString("first_name","")+" "+pref.getString("last_name","");
                params.put("userId", userID);
                params.put("fullname", fullName);
                params.put("parentId", id);
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
