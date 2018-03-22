package com.app.devrah.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import com.app.devrah.Holders.ViewHolderMember;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.ManageMembers.ManageCardMembers;
import com.app.devrah.Views.cards.CardActivity;
import com.app.devrah.pojo.MembersPojo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.devrah.Views.BoardExtended.BoardExtended.boardId;
import static com.app.devrah.Views.BoardExtended.BoardExtended.projectId;

/**
 * Created by Hamza Android on 10/3/2017.
 */
public class RVMemberResultAdapter extends RecyclerView.Adapter<ViewHolderMember> {
    Activity activity;
    private ListView currentMember;
    private ArrayList<String> cardMembers;
    private List<MembersPojo> memberList;
    String cardId,listId,boardId,projectId;
    private ArrayList<MembersPojo> membersPojos;
    private team_adapter_cards addapter;
    public RVMemberResultAdapter(Activity context, List<MembersPojo> memberList,String cardId,String listId,String boardId,String projectId){
        this.memberList = memberList;
        this.activity = context;
        this.cardId = cardId;
        this.listId = listId;
        this.boardId = boardId;
        this.projectId = projectId;


    }

    @NonNull
    @Override
    public ViewHolderMember onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_members,parent,false);
        ViewHolderMember holder = new ViewHolderMember(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolderMember holder, int position) {

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
                    holder.member= holder.itemView.findViewById(R.id.memberPic);
                    holder.member.setMaxWidth(60);
                    holder.member.setMaxHeight(60);
                      Picasso.with(activity)
                              .load(End_Points.IMAGES_BASE_URL+"profile_pictures/" + memberList.get(position).getProfile_pic())
                              .resize(60, 60)
                              .centerInside()
                              .into(holder.member);

                }catch (Exception e){
                    e.printStackTrace();
                }

            } else {
                holder.initials.setVisibility(View.GONE);
                try {
                    holder.member= holder.itemView.findViewById(R.id.memberPic);
                    holder.member.setMaxWidth(60);
                    holder.member.setMaxHeight(60);
                    Picasso.with(activity)
                            .load(memberList.get(position).getGp_pic())
                            .resize(60, 60)
                            .centerInside()
                            .into(holder.member);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        holder.member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewMemberDialogue();
            }
        });
        holder.initials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewMemberDialogue();
            }
        });
    }

    private void addNewMemberDialogue() {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.magnage_members_popup1,null);
        final AlertDialog alertDialog = new  AlertDialog.Builder(activity).create();
        currentMember = view.findViewById(R.id.grid_view);
        //TeamMember = view.findViewById(R.id.grid_view_team);
        TextView heading = view.findViewById(R.id.heading);
        heading.setText("Manage Card Member");
        //Team_list = view.findViewById(R.id.search_team);
        Button btnClose= view.findViewById(R.id.close);
        Button  btnSave= view.findViewById(R.id.save);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CardActivity)activity).updateUI();
                alertDialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CardActivity)activity).updateUI();
                alertDialog.dismiss();
                Toast.makeText(activity,"Members managed Successfully !",Toast.LENGTH_LONG).show();

            }
        });
        currentMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(membersPojos.get(i).getIsCardMember().equals("1")) {
                    String  usertoadd = membersPojos.get(i).getUserId();

                    deletemember(usertoadd);
                }else {
                    String usertoadd = membersPojos.get(i).getUserId();
                    addmember(usertoadd);
                }

            }
        });
        getCardmembers();
        alertDialog.setView(view);
//
        alertDialog.show();
    }
    @Override
    public int getItemCount() {
        return memberList.size();
    }
    public void addmember(final String usertoadd) {


        final ProgressDialog ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.ASSOSIATE_USER_CARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        getCardmembers();

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

                params.put("brd_id",boardId);
                params.put("prjct_id", projectId);
                params.put("card_id",cardId);
                params.put("list_id",listId);

                SharedPreferences pref = activity.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

                params.put("userId",usertoadd);

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
    public void deletemember(final String usertoadd) {


        final ProgressDialog ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.DELETE_CARD_MEMBER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        getCardmembers();

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

                params.put("user_id", usertoadd);
                params.put("card_id",cardId);
                params.put("list_id",listId);

                SharedPreferences pref = activity.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

                params.put("userId", pref.getString("user_id", ""));

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
    public void getCardmembers() {

        final ProgressDialog ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_CARD_MEMBERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        membersPojos = new ArrayList<>();
                        cardMembers=new ArrayList<>();
                        if(response.equals("false")){
                           /* addapter = new team_adapter_cards(getActivity(), membersPojos);

                            currentMember.setAdapter(addapter);*/

                        }else {
                            try {
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    MembersPojo membersPojo = new MembersPojo();


                                    membersPojo.setName(jsonObject.getString("first_name"));
                                    membersPojo.setProfile_pic(jsonObject.getString("profile_pic"));
                                    membersPojo.setUserId(jsonObject.getString("id"));
                                    membersPojo.setInetial(jsonObject.getString("initials"));
                                    membersPojo.setGp_pic(jsonObject.getString("gp_picture"));
                                    cardMembers.add(jsonObject.getString("id"));

                                    //  membersPojos.add(membersPojo);
/*
                                    addapter = new team_addapter(getActivity(), membersPojos);

                                    currentMember.setAdapter(addapter);*/

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        getmembers();

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
    public void getmembers() {

        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_BOARD_MEMBERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        membersPojos = new ArrayList<>();
                        if(response.equals("false")){
                            addapter = new team_adapter_cards(activity, membersPojos);

                            currentMember.setAdapter(addapter);

                        }else {
                            try {
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    MembersPojo membersPojo = new MembersPojo();


                                    membersPojo.setName(jsonObject.getString("first_name")+" "+jsonObject.getString("last_name"));
                                    membersPojo.setProfile_pic(jsonObject.getString("profile_pic"));
                                    membersPojo.setUserId(jsonObject.getString("id"));
                                    membersPojo.setInetial(jsonObject.getString("initials"));
                                    membersPojo.setGp_pic(jsonObject.getString("gp_picture"));
                                    String isCardMember="0";
                                    for(int j=0;j<cardMembers.size();j++){
                                        if(jsonObject.getString("id").equals(cardMembers.get(j))){
                                            isCardMember="1";
                                        }
                                    }
                                    membersPojo.setIsCardMember(isCardMember);

                                    membersPojos.add(membersPojo);

                                    addapter = new team_adapter_cards(activity, membersPojos);

                                    currentMember.setAdapter(addapter);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

                params.put("board_id", boardId);

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
