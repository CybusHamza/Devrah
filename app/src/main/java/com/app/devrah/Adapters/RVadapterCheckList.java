package com.app.devrah.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.devrah.Holders.Cheklist;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.CheckList_Detail;
import com.app.devrah.pojo.check_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;


public class RVadapterCheckList extends RecyclerView.Adapter<Cheklist> {

    Activity activity;
    ArrayList<check_model> CheckListItems;
    ArrayList<check_model> ids;
    ArrayList<String> checkListiItemName;
    ArrayList<String> checkListiItemIds;
    ArrayList<String> checkedItem;
    HashMap<String, ArrayList<check_model>> listDataChild;
    ProgressDialog ringProgressDialog;


    public RVadapterCheckList(Activity activit, ArrayList<check_model> CheckListItems,
                              HashMap<String, ArrayList<check_model>> listDataChild) {

        this.activity = activit;
        this.CheckListItems = CheckListItems;
        this.listDataChild = listDataChild;


    }

    @Override
    public Cheklist onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cheklist_group_header, parent, false);

        return new Cheklist(view);
    }

    @Override
    public void onBindViewHolder(final Cheklist holder, final int position) {

            String check=CheckListItems.get(position).getName().toString();
       // if(!check.equals("") && !check.equals("null")) {
            holder.checklistName.setText(CheckListItems.get(position).getName());
        //}else {
           // holder.checklistName.setText("Checklist");
        //}

        holder.deleteCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Confirmation!")
                        .setCancelText("Cancel")
                        .setConfirmText("OK").setContentText("Do you really want to delete Checklist")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.dismiss();
                                deleteCheckList(CheckListItems.get(position).getId(), position);
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
        holder.checklistName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i==6 ) {
                    String check=holder.checklistName.getText().toString();
                    if(!check.equals("") && check.trim().length()>0) {
                        holder.checklistName.clearFocus();
                        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(holder.checklistName.getWindowToken(), 0);
                        edit_checkbox(holder.checklistName.getText().toString(), CheckListItems.get(position).getId(), position);
                        //CardActivity.menuChanger(CardActivity.menu,false);
                    }else {
                        Toast.makeText(activity,"Checklist name is must!",Toast.LENGTH_LONG).show();
                    }
                }

                return true;
            }
        });
        checkListiItemIds= new ArrayList<String>();
        checkListiItemName= new ArrayList<String>();
        checkedItem= new ArrayList<String>();

        ids= new ArrayList<check_model>();

      
       
        ids =  listDataChild.get(CheckListItems.get(position).getId());

        if(ids == null)
        {

            holder.progressBar.setProgress(0);
        }
        
         else
        {
            for (int i = 0 ; i<ids.size(); i++)
            {
                checkListiItemIds.add(ids.get(i).getId());
                checkListiItemName.add(ids.get(i).getName());
                checkedItem.add(ids.get(i).getChecked());
            }

            float progress = 0;

            for(int i=0;i<checkedItem.size();i++)
            {
                if(checkedItem.get(i).equals("1"))
                {
                    progress++;
                }
            }

            int a= checkedItem.size();
            float b = (progress/a);
            progress = b*100;
            int progres=Integer.valueOf((int) progress);
            if(progres==100){
                holder.tvProgress.setTextColor(Color.GREEN);
                holder.progressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            }else if(progres>25&&progres<=75){
                holder.tvProgress.setTextColor(activity.getResources().getColor(R.color.colorYellow));
                holder.progressBar.getProgressDrawable().setColorFilter(activity.getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_IN);
            }else if(progres>0 &&progres<=25){
                holder.tvProgress.setTextColor(activity.getResources().getColor(R.color.colorRed));
                holder.progressBar.getProgressDrawable().setColorFilter(activity.getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
            }
            holder.progressBar.setProgress((int) progress);
            holder.tvProgress.setText((int)progress+"%");

        }


        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkListiItemIds= new ArrayList<String>();
                checkListiItemName= new ArrayList<String>();
                checkedItem= new ArrayList<String>();

                ids= new ArrayList<check_model>();

                ids =  listDataChild.get(CheckListItems.get(position).getId());

                if(ids == null)
                {
                    Intent  intent = new Intent(activity, CheckList_Detail.class);
                    intent.putExtra("checkListiItemIds","");
                    intent.putExtra("checkListiItemName","");
                    intent.putExtra("checkedItem","");
                    intent.putExtra("checklistid",CheckListItems.get(position).getId());
                    intent.putExtra("name",CheckListItems.get(position).getName());
                    activity.finish();
                    activity.startActivity(intent);
                }


                else
                {
                    for (int i = 0 ; i<ids.size(); i++)
                    {
                        checkListiItemIds.add(ids.get(i).getId());
                        checkListiItemName.add(ids.get(i).getName());
                        checkedItem.add(ids.get(i).getChecked());
                    }

                    Intent  intent = new Intent(activity, CheckList_Detail.class);
                    intent.putExtra("checkListiItemIds",checkListiItemIds);
                    intent.putExtra("checkListiItemName",checkListiItemName);
                    intent.putExtra("checkedItem",checkedItem);
                    intent.putExtra("checklistid",CheckListItems.get(position).getId());
                    intent.putExtra("name",CheckListItems.get(position).getName());
                    activity.finish();
                    activity.startActivity(intent);
                }

            }
        });

    }

    private void deleteCheckList(final String id, final int position) {
        ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.DELETE_CHECKLIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {

                   CheckListItems.remove(position);
                    notifyDataSetChanged();

/*

                        Intent intent = new Intent(activity, CheckList_Detail.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        activity.finish();
                        intent.putExtra("checkListiItemIds",checkListiItemIds);
                        intent.putExtra("checkListiItemName",checkListiItemName);
                        intent.putExtra("checkedItem",checkedItem);
                        intent.putExtra("checklistid",CheckListItems.get(position).getId());
                        intent.putExtra("name",CheckListItems.get(position).getName());
                        activity.startActivity(intent);
*/


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
                            .setConfirmText("OK").setContentText("No Internet Connection")
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


                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("id",id);
                params.put("is_active","0");
                params.put("u_id", pref.getString("user_id",""));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);

    }

    private void edit_checkbox(final String s, final String id, final int position) {
            ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, End_Points.AUPDATE_CHECKLIST_NAME, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    // loading.dismiss();
                    ringProgressDialog.dismiss();
                    if (!(response.equals(""))) {

                        CheckListItems.get(position).setName(s);
                        notifyDataSetChanged();

/*

                        Intent intent = new Intent(activity, CheckList_Detail.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        activity.finish();
                        intent.putExtra("checkListiItemIds",checkListiItemIds);
                        intent.putExtra("checkListiItemName",checkListiItemName);
                        intent.putExtra("checkedItem",checkedItem);
                        intent.putExtra("checklistid",CheckListItems.get(position).getId());
                        intent.putExtra("name",CheckListItems.get(position).getName());
                        activity.startActivity(intent);
*/


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
                                .setConfirmText("OK").setContentText("No Internet Connection")
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


                    final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    params.put("checkName",s);;
                    params.put("id",id);
                    params.put("u_id", pref.getString("user_id",""));


                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            requestQueue.add(request);



    }


    @Override
    public int getItemCount() {
        return CheckListItems.size();
    }
}

