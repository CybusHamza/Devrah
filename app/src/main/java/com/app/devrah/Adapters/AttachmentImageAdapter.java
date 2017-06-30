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
import com.app.devrah.ImageDescription;
import com.app.devrah.R;

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


   public AttachmentImageAdapter( Activity context,List<Bitmap> mList,FragmentManager fm){

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
            holder.imgViewAttachments.setImageBitmap(mList.get(position));
     //   ((CardActivity) activity).getFragmentManager();

        holder.imgViewAttachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Bitmap bitmap = mList.get(position);
                Intent intent = new Intent(activity.getApplicationContext(), ImageDescription.class);
                intent.putExtra("BitmapImage",bitmap);
                activity.startActivity(intent);



               // fm.beginTransaction().add(new AttachmentImages(),"Frag").commit();

               // fm.beginTransaction().add(R.id.fragmentContainer,new AttachmentImages()).addToBackStack("Frag1").commit();


             //   fragmentTransaction.add(R.id.fragmentContainer,colorFragment).addToBackStack("Frag1").commit();

             //   fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//


/////////////////////////////////////////
//                if (activity instanceof CardActivity){
//
//
//                    FragmentTransaction t = ((CardActivity) activity).
//                            getSupportFragmentManager().beginTransaction().
//                            add(new AttachmentImages(),"gth");
//                //    CardActivity activity = (CardActivity(activity))
//                    ;
//
//                }

////////////////////////////////////////////////////////////
//                AttachmentImages fragment = new AttachmentImages();
//                android.app.FragmentManager fragmentManager = ((CardActivity) activity).getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();


             //   CardActivity.startFrag();
//
//                AttachmentImages frag = new AttachmentImages();
//
//
//                fm.beginTransaction().add(frag,"Fragg");
//                android.app.FragmentManager fm =((CardActivity)activity).getFragmentManager();
//              android.app.FragmentTransaction ft=  fm.beginTransaction();
//
//
//                ft.add(CardActivity.container,frag).addToBackStack("Frag1").commit();
//
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//
//
//                activity.getFragmentManager().beginTransaction()
//                        .add(R.id.fragmentContainer,new AttachmentImages())*/


            }
        });


        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void removeAt(int position){

        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mList.size());

    }

}
