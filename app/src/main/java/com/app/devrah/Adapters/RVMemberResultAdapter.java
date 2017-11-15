package com.app.devrah.Adapters;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Holders.ViewHolderMember;
import com.app.devrah.R;
import com.app.devrah.Views.ManageMembers.ManageCardMembers;
import com.app.devrah.pojo.MembersPojo;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hamza Android on 10/3/2017.
 */
public class RVMemberResultAdapter extends RecyclerView.Adapter<ViewHolderMember> {
    Activity activity;
    private List<MembersPojo> memberList;
    String cardId,listId,boardId,projectId;
    public RVMemberResultAdapter(Activity context, List<MembersPojo> memberList,String cardId,String listId,String boardId,String projectId){
        this.memberList = memberList;
        this.activity = context;
        this.cardId = cardId;
        this.listId = listId;
        this.boardId = boardId;
        this.projectId = projectId;


    }

    @Override
    public ViewHolderMember onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_members,parent,false);
        ViewHolderMember holder = new ViewHolderMember(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolderMember holder, int position) {

            if ((memberList.get(position).getProfile_pic().equals("null") || memberList.get(position).getProfile_pic().equals("")) && (memberList.get(position).getGp_pic().equals("null") || memberList.get(position).getGp_pic().equals(""))) {
                holder.initials.setVisibility(View.VISIBLE);
                holder.initials.setText(memberList.get(position).getInetial());
            } else if (!memberList.get(position).getProfile_pic().equals("null") && !memberList.get(position).getProfile_pic().equals("")) {
                holder.initials.setVisibility(View.GONE);
                try {
                    /*Picasso.with(activity)
                            .load("http://m1.cybussolutions.com/kanban/uploads/profile_pictures/" + memberList.get(position).getProfile_pic())
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .fetch( new Callback() {
                                @Override
                                public void onSuccess() {
                                    h
                                }

                                @Override
                                public void onError() {

                                }

                            });*/
                    holder.member= (CircleImageView) holder.itemView.findViewById(R.id.memberPic);
                    holder.member.setMaxWidth(50);
                    holder.member.setMaxHeight(50);
                      Picasso.with(activity)
                              .load("http://m1.cybussolutions.com/devrah/uploads/profile_pictures/" + memberList.get(position).getProfile_pic())
                              .resize(50, 50)
                              .centerInside()
                              .into(holder.member);

                }catch (Exception e){
                    e.printStackTrace();
                }

            } else {
                holder.initials.setVisibility(View.GONE);
                try {
                    holder.member= (CircleImageView) holder.itemView.findViewById(R.id.memberPic);
                    holder.member.setMaxWidth(50);
                    holder.member.setMaxHeight(50);
                    Picasso.with(activity)
                            .load(memberList.get(position).getGp_pic())
                            .resize(50, 50)
                            .centerInside()
                            .into(holder.member);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        holder.member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();

                ManageCardMembers manageCardMembers = new ManageCardMembers();
                // LabelColorFragment.textLabelName="";
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer1, manageCardMembers).addToBackStack("Frag4").commit();

                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
               /* Intent intent = new Intent(activity, Manage_Card_Members.class);
                intent.putExtra("P_id",projectId);
                intent.putExtra("b_id",boardId);
                intent.putExtra("c_id",cardId);
                intent.putExtra("l_id",listId);
                activity.startActivity(intent);*/
            }
        });
        holder.initials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();

                ManageCardMembers manageCardMembers = new ManageCardMembers();
                // LabelColorFragment.textLabelName="";
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer1, manageCardMembers).addToBackStack("Frag4").commit();

                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
              /*  Intent intent = new Intent(activity, Manage_Card_Members.class);
                intent.putExtra("P_id",projectId);
                intent.putExtra("b_id",boardId);
                intent.putExtra("c_id",cardId);
                intent.putExtra("l_id",listId);
                activity.startActivity(intent);*/
            }
        });
    }


    @Override
    public int getItemCount() {
        return memberList.size();
    }
}
