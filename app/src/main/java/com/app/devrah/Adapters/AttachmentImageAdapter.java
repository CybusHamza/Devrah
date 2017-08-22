package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Holders.ImageAttachmentsHolder;
import com.app.devrah.Views.ImageDescription;
import com.app.devrah.R;
import com.app.devrah.pojo.AttachmentsImageFilePojo;
import com.app.devrah.pojo.AttachmentsPojo;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/21/2017.
 */

public class AttachmentImageAdapter extends RecyclerView.Adapter<ImageAttachmentsHolder> {


    Activity activity;
    FragmentManager fm;
    LayoutInflater inflater;
    List<Bitmap> mList = Collections.emptyList();
    private List<AttachmentsImageFilePojo> attachmentList;

   public AttachmentImageAdapter( Activity context,List<Bitmap> mList,FragmentManager fm,List<AttachmentsImageFilePojo> attachmentList){
       this.attachmentList = attachmentList;
       this.activity = context;
       this.mList = mList;
       this.fm = fm;

   }


    @Override
    public ImageAttachmentsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_images_attachment,parent,false);
        ImageAttachmentsHolder holder = new ImageAttachmentsHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ImageAttachmentsHolder holder, final int position) {
           // holder.imgViewAttachments.setImageBitmap(mList.get(position));
        if(!(attachmentList.get(position).getImageFile().equals(""))){

            Picasso.with(activity)
                    .load("http://m1.cybussolutions.com/kanban/uploads/card_uploads/" + attachmentList.get(position).getImageFile())
                    .into(holder.imgViewAttachments);
        }
     //   ((CardActivity) activity).getFragmentManager();

        holder.imgViewAttachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



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

    @Override
    public int getItemCount() {
        return attachmentList.size();
    }


    public void removeAt(int position){

        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mList.size());

    }

}
