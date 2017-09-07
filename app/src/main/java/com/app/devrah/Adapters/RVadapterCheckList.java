package com.app.devrah.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

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


        holder.checklistName.setText(CheckListItems.get(position).getName());


        holder.checklistName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i==6 ) {
                    holder.checklistName.clearFocus();
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.checklistName.getWindowToken(), 0);
                    edit_checkbox (holder.checklistName.getText().toString(),CheckListItems.get(position).getId(),position);

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

            holder.progressBar.setProgress((int) progress);
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
                    activity.startActivity(intent);
                }

            }
        });

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

                       // notifyDataSetChanged();
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

