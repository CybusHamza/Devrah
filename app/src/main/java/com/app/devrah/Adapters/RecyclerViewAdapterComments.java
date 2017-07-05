package com.app.devrah.Adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.app.devrah.Holders.View_Holder;
import com.app.devrah.R;
import com.app.devrah.pojo.CardCommentData;

import java.util.Collections;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/7/2017.
 */

public class RecyclerViewAdapterComments extends RecyclerView.Adapter<View_Holder> {


    LayoutInflater inflater;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_view_cardscreen_comment,parent,false);
        View_Holder holder = new View_Holder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
        holder.tvcardName.setText(cardCommenList.get(position).getCardName());
        holder.idCardComment.setText(cardCommenList.get(position).getCardCommentid());
       // holder.imgCard.setImageBitmap();
        holder.comment.setText(cardCommenList.get(position).getComment());
        holder.CommentMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               CardCommentData cardCommentData = cardCommenList.get(position);
//                remove(cardCommentData);


//                if (inflater==null)
//               inflater  = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                inflater.inflate(R.layout.custom_dialog_for_edit_delete_comment,null);

//
//                LayoutInflater inflater = LayoutInflater.from(context);
//                View view = inflater.inflate(R.layout.custom_dialog_for_edit_delete_comment,null);
//                holder.laEditDelete.addView(view);

               // removeAt(position);


                final PopupMenu popup = new PopupMenu(v.getContext(), holder.CommentMenu);
              //  MenuInflater inflater = popup.getMenuInflater();




                popup.getMenuInflater().inflate(R.menu.popup_edit_delete, popup.getMenu());
               // inflater.inflate(R.menu.popup_edit_delete, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){

                            case R.id.edit:
                                Toast.makeText(context.getApplicationContext(),"EDitttttttt",Toast.LENGTH_SHORT).show();
                            break;

                            case R.id.delete:
                                removeAt(position);
                                Toast.makeText(context.getApplicationContext(),"DElete",Toast.LENGTH_SHORT).show();
                        }


                        return true;
                    }
                });


                popup.show();


            }
        });



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
    public void removeAt(int position){


        cardCommenList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,cardCommenList.size());

    }




}
