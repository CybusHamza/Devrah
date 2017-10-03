package com.app.devrah.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.devrah.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hamza Android on 10/3/2017.
 */
public class ViewHolderMember  extends RecyclerView.ViewHolder{
    public CircleImageView member;
    public TextView initials;
    public ViewHolderMember(View itemView) {
        super(itemView);

        member = (CircleImageView) itemView.findViewById(R.id.memberPic);
        initials = (TextView) itemView.findViewById(R.id.initials);
    }
}
