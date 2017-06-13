package com.app.devrah.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Holders.View_Holder;
import com.app.devrah.R;
import com.app.devrah.pojo.CardCommentData;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/7/2017.
 */

public class RecyclerViewAdapterComments extends RecyclerView.Adapter<View_Holder> {

    List<CardCommentData> cardCommenList= Collections.emptyList();
    Context context;

    public RecyclerViewAdapterComments(List<CardCommentData> cardCommenList, Context context) {
        this.cardCommenList = cardCommenList;
        this.context = context;
    }

    public RecyclerViewAdapterComments() {

    }


    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_view_cardscreen,parent,false);
        View_Holder holder = new View_Holder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
        holder.tvcardName.setText(cardCommenList.get(position).getCardName());
        holder.idCardComment.setText(cardCommenList.get(position).getCardCommentid());
       // holder.imgCard.setImageBitmap();
        holder.comment.setText(cardCommenList.get(position).getComment());



    }

    @Override
    public int getItemCount() {
        return cardCommenList.size();
    }

    public void insert(int position, CardCommentData data) {
        cardCommenList.add(position, data);
        notifyItemInserted(position);
    }

    public void remove(CardCommentData data) {
        int position = cardCommenList.indexOf(data);
        cardCommenList.remove(position);
        notifyItemRemoved(position);
    }




}
