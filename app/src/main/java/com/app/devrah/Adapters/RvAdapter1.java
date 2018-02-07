package com.app.devrah.Adapters;

/**
 * Created by Hamza Android on 10/24/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.app.devrah.Views.CommentsThread.CheckList_Comments;
import com.app.devrah.Views.CommentsThread.Child_Inner_Comments;
import com.app.devrah.pojo.CommentsPojo;
import com.app.devrah.pojo.Level;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by ishratkhan on 24/02/16.
 */
public class RvAdapter1 extends RecyclerView.Adapter<RvAdapter1.RvViewHolder> {

    List<CommentsPojo> projectsList;
    Activity activity;
    ProgressDialog ringProgressDialog;
    AlertDialog myalertdialog;
    private static final int REQUEST_PERMISSIONS=5;
    Boolean success=false;
    public RvAdapter1(Activity con, List<CommentsPojo> projectsList) {
        this.activity = con;
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
        holder.borderLine.setVisibility(View.GONE);
        holder.date.setTextColor(activity.getResources().getColor(R.color.colorWhite));
        holder.date.setText(projectsList.get(position).getDate());
        holder.setLevel(projectsList.get(position).getLevel());
        SharedPreferences pref = activity.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String fullName=pref.getString("first_name","")+" "+pref.getString("last_name","");
        String userId=pref.getString("user_id","");
        if(userId.equals(projectsList.get(position).getCreatedBy()) && projectsList.get(position).getLevel()==2){
            holder.userName.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
        }else {
            holder.userName.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
            holder.delete.setVisibility(View.INVISIBLE);
            holder.edit.setVisibility(View.INVISIBLE);
        }
        /*if(projectsList.get(position).getLevel()==1){
            holder.delete.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
        }else {
            holder.delete.setVisibility(View.VISIBLE);
            holder.edit.setVisibility(View.VISIBLE);
        }*/

        String fileType=projectsList.get(position).getFileType();
        if(projectsList.get(position).getIsFile().equals("1") && (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpg")|| fileType.equals("image/gif")) ){
            holder.attachment.setVisibility(View.VISIBLE);
            holder.tv.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
            Picasso.with(activity)
                    .load(End_Points.IMAGES_BASE_URL+"comment_images/" + projectsList.get(position).getComments())
                    .resize(activity.getResources().getDimensionPixelSize(R.dimen.comment_size_width),activity.getResources().getDimensionPixelSize(R.dimen.comment_size_height))
                    .into(holder.attachment);
        }else if(projectsList.get(position).getIsFile().equals("1")){
            holder.tv.setPaintFlags(holder.tv.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            holder.tv.setVisibility(View.VISIBLE);
            holder.tv.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
            holder.attachment.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
           // if(fullName.equals(projectsList.get(position).getFullName()) && projectsList.get(position).getLevel()==2)
           // holder.edit.setVisibility(View.VISIBLE);
        }else {
            holder.tv.setPaintFlags(0);
            holder.tv.setTextColor(activity.getResources().getColor(R.color.black));
            holder.tv.setVisibility(View.VISIBLE);
            holder.attachment.setVisibility(View.GONE);
            if(userId.equals(projectsList.get(position).getCreatedBy()) && projectsList.get(position).getLevel()==2)
            holder.edit.setVisibility(View.VISIBLE);

        }
        if(!projectsList.get(position).getProfilePic().equals("") && !projectsList.get(position).getProfilePic().equals("null")){
            holder.profilePic.setVisibility(View.VISIBLE);
            holder.marker.setVisibility(View.GONE);
            Picasso.with(activity)
                    .load(End_Points.IMAGES_BASE_URL+"profile_pictures/" + projectsList.get(position).getProfilePic())
                    .resize(activity.getResources().getDimensionPixelSize(R.dimen.comment_size_width),activity.getResources().getDimensionPixelSize(R.dimen.comment_size_height))
                    .into(holder.profilePic);
        }
        else
        {
            holder.marker.setVisibility(View.VISIBLE);
            holder.marker.setText(projectsList.get(position).getInitials());
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editComment(projectsList.get(position).getId(),position,holder.tv.getText().toString());
            }
        });
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(projectsList.get(position).getIsFile().equals("1")) {
                    // String fileType=getMimeType(URLDecoder.decode(projectsList.get(position).getComments()));
                    String url = projectsList.get(position).getComments().replace(" ", "%20");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(End_Points.IMAGES_BASE_URL+"comment_images/" + url));
                    activity.startActivity(browserIntent);
                }
            }
        });
        holder.attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* String url = projectsList.get(position).getComments().replace(" ", "%20");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m1.cybussolutions.com/devrah/uploads/comment_images/" + url));
                activity.startActivity(browserIntent);*/
                downloadImageDialog(position,projectsList.get(position).getComments(),projectsList.get(position).getComments());
            }
        });
        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(projectsList.get(position).getLevel()==1){

                }else {
                    Intent intent = new Intent(activity, Child_Inner_Comments.class);
                    intent.putExtra("checklid", projectsList.get(position).getChecklistId());
                    intent.putExtra("cardId", projectsList.get(position).getCardId());
                    intent.putExtra("id", projectsList.get(position).getId());
                    intent.putExtra("parentComment",projectsList.get(position).getFullName());
                    intent.putExtra("parentCommentData",projectsList.get(position).getComments());
                    intent.putExtra("parentProfilePic",projectsList.get(position).getParentProfilePic());
                    intent.putExtra("parentInitials",projectsList.get(position).getParentInitials());
                    intent.putExtra("parentIsFile",projectsList.get(position).getParentIsFile());
                    intent.putExtra("parentFileType",projectsList.get(position).getParentfileType());
                    activity.startActivity(intent);
                }
                /*if(projectsList.get(position).getLevel()==2){
                    replyToComment(projectsList.get(position).getParentId(),projectsList.get(position).getCardId(),projectsList.get(position).getChecklistId(),position);

                }else
                    replyToComment(projectsList.get(position).getId(),projectsList.get(position).getCardId(),projectsList.get(position).getChecklistId(),position);
      */      }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(projectsList.get(position).getLevel()==1){

                }else {
                    Intent intent = new Intent(activity, Child_Inner_Comments.class);
                    intent.putExtra("checklid", projectsList.get(position).getChecklistId());
                    intent.putExtra("cardId", projectsList.get(position).getCardId());
                    intent.putExtra("id", projectsList.get(position).getId());
                    intent.putExtra("parentComment",projectsList.get(position).getFullName());
                    intent.putExtra("parentCommentData",projectsList.get(position).getComments());
                    intent.putExtra("parentProfilePic",projectsList.get(position).getParentProfilePic());
                    intent.putExtra("parentInitials",projectsList.get(position).getParentInitials());
                    intent.putExtra("parentIsFile",projectsList.get(position).getParentIsFile());
                    intent.putExtra("parentFileType",projectsList.get(position).getParentfileType());
                    activity.startActivity(intent);
                }
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
    private void downloadImageDialog(int position, final String comments, String comments1) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.custom_diloag_download_image, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        ImageView image= (ImageView) dialogView.findViewById(R.id.imageLoaded);
        TextView label= (TextView) dialogView.findViewById(R.id.label);

        Button downloadImageBtn= (Button) dialogView.findViewById(R.id.downloadImageBtn);
        Button cancelBtn= (Button) dialogView.findViewById(R.id.cancelBtn);
        Button makeCover= (Button) dialogView.findViewById(R.id.makeCoverBtn);
        makeCover.setVisibility(View.GONE);
        label.setText(comments);
        Picasso.with(activity)
                .load(End_Points.IMAGES_BASE_URL+"comment_images/" + comments)
                .resize(activity.getResources().getDimensionPixelSize(R.dimen.attachment_popup_width),activity.getResources().getDimensionPixelSize(R.dimen.attachment_popup_height))
                .into(image);
        downloadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


                    if (Build.VERSION.SDK_INT > 22) {

                        activity.requestPermissions(new String[]{Manifest.permission
                                        .WRITE_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);

                    }

                }else {
                    Picasso.with(activity).load(End_Points.IMAGES_BASE_URL+"comment_images/" + comments).into(picassoImageTarget(activity, "imageDir", comments));
                    //  Target target=picassoImageTarget(activity, "imageDir",imageName);
                    //Toast.makeText(activity, "Image Saved to the Directory imageDir", Toast.LENGTH_LONG).show();
                    myalertdialog.dismiss();
                    ShowToast();
                }
               /* if (success) {
                    Toast.makeText(activity, "Image saved with success",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity,
                            "Error during image saving", Toast.LENGTH_LONG).show();
                }*/
                // String url =imageName.replace(" ","%20");
                // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m1.cybussolutions.com/kanban/uploads/card_uploads/"+url));
                //activity. startActivity(browserIntent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myalertdialog.dismiss();
            }
        });

        myalertdialog = builder.create();
        myalertdialog.show();
    }
    public void ShowToast(){
        Toast.makeText(activity, "Image Save to the directory Devrah", Toast.LENGTH_SHORT).show();
    }
    @Override
    public int getItemCount() {
        return projectsList.size();
    }

    public void addItem(CommentsPojo item) {
        projectsList.add(item);
    }

    class RvViewHolder extends RecyclerView.ViewHolder {
        TextView tv,edit,delete,reply,userName,date;
        View itemView,borderLine;
        TextView marker;
        ImageView attachment;
        CardView cardView;
        ImageView profilePic;

        public RvViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            cardView= (CardView) itemView.findViewById(R.id.rv_item_card);
            tv = (TextView) itemView.findViewById(R.id.rv_item_tv);
            date = (TextView) itemView.findViewById(R.id.date);
            userName = (TextView) itemView.findViewById(R.id.userName);
            edit = (TextView) itemView.findViewById(R.id.edit);
            reply = (TextView) itemView.findViewById(R.id.reply);
            delete = (TextView) itemView.findViewById(R.id.delete);
            attachment = (ImageView) itemView.findViewById(R.id.attachment);
            marker =(TextView) itemView.findViewById(R.id.marker);
            profilePic =(ImageView) itemView.findViewById(R.id.imageProfile);
            borderLine =(View) itemView.findViewById(R.id.divider);
        }

        public void setLevel(int level) {
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
            );

            if (level == Level.LEVEL_TWO) {
                params.setMarginStart(ViewUtils.getLevelOneMargin());
                marker.setBackground(ContextCompat.getDrawable(activity,R.drawable.round_bkg));
            } else if (level == Level.LEVEL_THREE) {
                params.setMarginStart(ViewUtils.getLevelTwoMargin());
                marker.setBackground(ContextCompat.getDrawable(activity,R.drawable.round_bkg));
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
                SharedPreferences pref = activity.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
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
    private Target picassoImageTarget(final Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        final File root = new File(Environment.getExternalStorageDirectory()
                + File.separator + "Devrah" + File.separator);
        if (!root.exists()) {

            root.mkdirs();
        }
        ContextWrapper cw = new ContextWrapper(context);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        final File myImageFile = new File(root, formattedDate+imageName); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Create image file
                        //  Toast.makeText(context,"Image Saved to directory "+myImageFile.getAbsolutePath(),Toast.LENGTH_LONG).show();

                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            //   Toast.makeText(context,"Image Saved to directory "+myImageFile.getAbsolutePath(),Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            // Toast.makeText(context,"Failed to save image ",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        } finally {
                            try {
                                success=true;
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("image", "image saved to >>>" + myImageFile.getAbsolutePath());


                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {}
            }
        };
    }
}
