package com.app.devrah.Adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.app.devrah.Holders.ImageAttachmentsHolder;
import com.app.devrah.Network.End_Points;
import com.app.devrah.Views.BoardExtended.BoardExtended;
import com.app.devrah.R;
import com.app.devrah.pojo.AttachmentsImageFilePojo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by AQSA SHaaPARR on 6/21/2017.
 */

public class AttachmentImageAdapter extends RecyclerView.Adapter<ImageAttachmentsHolder> {

    ProgressDialog ringProgressDialog;
    Activity activity;
    FragmentManager fm;
    LayoutInflater inflater;
    List<Bitmap> mList = Collections.emptyList();
    private List<AttachmentsImageFilePojo> attachmentList;
    String cardId;
    AlertDialog myalertdialog;
    Boolean success=false;
    private static final int REQUEST_PERMISSIONS=5;

   public AttachmentImageAdapter( Activity context,List<Bitmap> mList,FragmentManager fm,List<AttachmentsImageFilePojo> attachmentList,String cardId){
       this.attachmentList = attachmentList;
       this.activity = context;
       this.mList = mList;
       this.fm = fm;
       this.cardId = cardId;

   }


    @Override
    public ImageAttachmentsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_images_attachment,parent,false);
        ImageAttachmentsHolder holder = new ImageAttachmentsHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ImageAttachmentsHolder holder, final int position) {
           // holder.imgViewAttachments.setImageBitmap(mList.get(position));
        if(!(attachmentList.get(position).getImageFile().equals(""))){

            Glide.with(activity)
                    .load(End_Points.IMAGES_BASE_URL+"card_uploads/" + attachmentList.get(position).getImageFile())
                    .apply(new RequestOptions().override(120,120).centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .skipMemoryCache(true))
                    .into(holder.imgViewAttachments);
        }
        holder.imgViewAttachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImageDialog(attachmentList.size(),position,attachmentList.get(position).getImageFile(),attachmentList.get(position).getIsCover(),attachmentList.get(position).getAttch_id(),attachmentList.get(position).getOriginalFileName());
                }
        });
     //   ((CardActivity) activity).getFragmentManager();

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Confirmation!")
                        .setCancelText("Cancel")
                        .setConfirmText("OK").setContentText("Are You sure you want to Remove this image")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.dismiss();
                                deleteAttachment(cardId,attachmentList.get(position).getAttch_id(),position);
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

              /*  Bitmap bitmap = mList.get(position);
                Intent intent = new Intent(activity.getApplicationContext(), ImageDescription.class);
                intent.putExtra("BitmapImage",bitmap);
                activity.startActivity(intent);*/





            }
        });


       /* holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
            }
        });*/
    }

    @SuppressLint("ClickableViewAccessibility")
    private void downloadImageDialog(final int totalPics, final int pos, final String imageName, final String isCover, final String attachmentId, final String originalFileName) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.custom_diloag_download_image, null);

        builder.setView(dialogView);
        builder.setCancelable(false);
        final int[] post = {pos};
       final ImageView image= dialogView.findViewById(R.id.imageLoaded);
       final TextView label= dialogView.findViewById(R.id.label);
        final Button makeCoverBtn= dialogView.findViewById(R.id.makeCoverBtn);
        final ProgressBar progressBar= dialogView.findViewById(R.id.progress);
        if(isCover.equals("0")){
            makeCoverBtn.setText("Make Cover");
        }else if(isCover.equals("1")){
            makeCoverBtn.setText("Remove Cover");
        }
        makeCoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(makeCoverBtn.getText().equals("Make Cover")){
                    makeCover(post[0],"1",attachmentList.get(post[0]).getAttch_id(),cardId);
                }else {
                    makeCover(post[0],"0",attachmentList.get(post[0]).getAttch_id(),cardId);
                }

            }
        });

        ImageView imageLeft= dialogView.findViewById(R.id.left);
        ImageView imageRight= dialogView.findViewById(R.id.right);
        if(totalPics<2){
            imageLeft.setVisibility(View.INVISIBLE);
            imageRight.setVisibility(View.INVISIBLE);
        }
        imageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position= post[0] -1;
                post[0]--;
                if(position<0) {
                    position = totalPics - 1;
                    post[0]=totalPics-1;
                }
              //  progressBar.setVisibility(View.VISIBLE);
                Glide.with(activity)
                        .load(End_Points.IMAGES_BASE_URL+"card_uploads/" + attachmentList.get(position).getImageFile())
                        .apply(new RequestOptions().override(activity.getResources().getDimensionPixelSize(R.dimen.attachment_popup_width),activity.getResources().getDimensionPixelSize(R.dimen.attachment_popup_height)).centerCrop().onlyRetrieveFromCache(true)
                               )
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }

                        })
                        .into(image);

                label.setText(attachmentList.get(position).getImageFile());
                if(attachmentList.get(position).getIsCover().equals("1"))
                    makeCoverBtn.setText("Remove Cover");
                else if(attachmentList.get(position).getIsCover().equals("0"))
                    makeCoverBtn.setText("Make Cover");
            }
        });
        imageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=post[0]+1;
                post[0]++;
                if(position>totalPics-1) {
                    position = 0;
                    post[0]=0;
                }
             //   progressBar.setVisibility(View.VISIBLE);
                Glide.with(activity)
                        .load(End_Points.IMAGES_BASE_URL+"card_uploads/" + attachmentList.get(position).getImageFile())
                        .apply(new RequestOptions().override(activity.getResources().getDimensionPixelSize(R.dimen.attachment_popup_width),activity.getResources().getDimensionPixelSize(R.dimen.attachment_popup_height)).centerCrop().onlyRetrieveFromCache(true))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }

                        })
                        .into(image);
                label.setText(attachmentList.get(position).getImageFile());
                if(attachmentList.get(position).getIsCover().equals("1"))
                    makeCoverBtn.setText("Remove Cover");
                else if(attachmentList.get(position).getIsCover().equals("0"))
                    makeCoverBtn.setText("Make Cover");
            }
        });
        image.setOnTouchListener(new OnSwipeTouchListener(activity) {
            @Override
            public void onSwipeLeft() {
                // Whatever
                if(totalPics>1) {
                    int position = post[0] - 1;
                    post[0]--;
                    if (position < 0) {
                        position = totalPics - 1;
                        post[0] = totalPics - 1;
                    }
                   // progressBar.setVisibility(View.VISIBLE);
                    Glide.with(activity)
                            .load(End_Points.IMAGES_BASE_URL+"card_uploads/" + attachmentList.get(position).getImageFile())
                            .apply(new RequestOptions().override(activity.getResources().getDimensionPixelSize(R.dimen.attachment_popup_width), activity.getResources().getDimensionPixelSize(R.dimen.attachment_popup_height)).centerCrop().onlyRetrieveFromCache(true))
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                            })
                            .into(image);

                    label.setText(attachmentList.get(position).getImageFile());
                    if (attachmentList.get(position).getIsCover().equals("1"))
                        makeCoverBtn.setText("Remove Cover");
                    else if (attachmentList.get(position).getIsCover().equals("0"))
                        makeCoverBtn.setText("Make Cover");
                }

            }
            @Override
            public void onSwipeRight() {
                if(totalPics>1) {
                    int position = post[0] + 1;
                    post[0]++;
                    if (position > totalPics - 1) {
                        position = 0;
                        post[0] = 0;
                    }
                  //  progressBar.setVisibility(View.VISIBLE);
                    Glide.with(activity)
                            .load(End_Points.IMAGES_BASE_URL+"card_uploads/" + attachmentList.get(position).getImageFile())
                            .apply(new RequestOptions().override(activity.getResources().getDimensionPixelSize(R.dimen.attachment_popup_width), activity.getResources().getDimensionPixelSize(R.dimen.attachment_popup_height)).centerCrop().onlyRetrieveFromCache(true))
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                            })
                            .into(image);
                    label.setText(attachmentList.get(position).getImageFile());
                    if (attachmentList.get(position).getIsCover().equals("1"))
                        makeCoverBtn.setText("Remove Cover");
                    else if (attachmentList.get(position).getIsCover().equals("0"))
                        makeCoverBtn.setText("Make Cover");
                }

            }
        });
        Button downloadImageBtn= dialogView.findViewById(R.id.downloadImageBtn);
        Button cancelBtn= dialogView.findViewById(R.id.cancelBtn);
        label.setText(imageName);
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(activity)
                .load(End_Points.IMAGES_BASE_URL+"card_uploads/" + imageName)
                .apply(new RequestOptions().override(activity.getResources().getDimensionPixelSize(R.dimen.attachment_popup_width),activity.getResources().getDimensionPixelSize(R.dimen.attachment_popup_height)).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

        })
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
                    Picasso.with(activity).load(End_Points.IMAGES_BASE_URL+"card_uploads/" + attachmentList.get(post[0]).getImageFile()).into(picassoImageTarget(activity, "imageDir", attachmentList.get(post[0]).getImageFile()));
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
                Glide.get(activity).clearMemory();
                myalertdialog.dismiss();
            }
        });

        myalertdialog = builder.create();
        myalertdialog.show();
    }
    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureListener());
        }

        public void onSwipeLeft() {

        }

        public void onSwipeRight() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_DISTANCE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float distanceX = e2.getX() - e1.getX();
                float distanceY = e2.getY() - e1.getY();
                if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (distanceX > 0)
                        onSwipeRight();
                    else
                        onSwipeLeft();
                    return true;
                }
                return false;
            }
        }
    }
    private void deleteAttachment(final String cardId, final String attch_id, final int pos) {
        ringProgressDialog = ProgressDialog.show(activity, "Please wait ...", "deleting ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.DELETE_ATTACHMENT_BY_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        if(!response.equals("")) {
                            Toast.makeText(activity, "Attachment deleted", Toast.LENGTH_SHORT).show();
                            attachmentList.remove(pos);
                            notifyDataSetChanged();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "check your internet connection", Toast.LENGTH_SHORT).show();
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
                params.put("boardId", BoardExtended.boardId);
                params.put("projectId",BoardExtended.projectId);
                params.put("card",cardId);
                params.put("attach_id",attch_id);

                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
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

    @Override
    public int getItemCount() {
        return attachmentList.size();
    }


    public void removeAt(int position){

        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mList.size());

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
    public void makeCover(final int pos,final String isCover, final String attach_id,final String cardId){
        ringProgressDialog = ProgressDialog.show(activity, "Please wait ...", "", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.MAKE_COVER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                       if(!response.equals("")) {
                           myalertdialog.dismiss();
                           for (int i = 0; i < attachmentList.size(); i++) {
                               attachmentList.get(i).setIsCover("0");
                           }
                           attachmentList.get(pos).setIsCover(isCover);
                           notifyDataSetChanged();
                           if (isCover.equals("1"))
                               Toast.makeText(activity, "Cover Updated Successfully", Toast.LENGTH_SHORT).show();
                           else
                               Toast.makeText(activity, "Cover Removed Successfully", Toast.LENGTH_SHORT).show();
                       }
                            //attachmentList.remove(pos);
                            //notifyDataSetChanged();
                   //     }else {
                           // Toast.makeText(activity, "Not updated Cover", Toast.LENGTH_SHORT).show();
                     //   }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "check your internet connection", Toast.LENGTH_SHORT).show();
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
                params.put("make_cover",isCover);
                params.put("attach_id",attach_id);
                params.put("card_id",cardId);
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
    public void ShowToast(){
        Toast.makeText(activity, "Image Save to the directory Devrah", Toast.LENGTH_SHORT).show();
    }

}
