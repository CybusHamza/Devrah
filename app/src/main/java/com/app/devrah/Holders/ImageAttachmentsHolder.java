package com.app.devrah.Holders;

import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.app.devrah.R;

/**
 * Created by AQSA SHaaPARR on 6/21/2017.
 */

public class ImageAttachmentsHolder extends RecyclerView.ViewHolder{

    public ImageView imgViewAttachments,deleteIcon;
    CardView cardView;

    public ImageAttachmentsHolder(View itemView) {
        super(itemView);

        cardView = (CardView) itemView.findViewById(R.id.cvAttachment);

        imgViewAttachments = (ImageView) itemView.findViewById(R.id.rvImageView);
        deleteIcon = (ImageView)itemView.findViewById(R.id.menuIcon);

    }




}
