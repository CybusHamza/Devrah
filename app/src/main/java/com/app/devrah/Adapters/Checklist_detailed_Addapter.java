package com.app.devrah.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.check_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Hamza Android on 8/30/2017.
 */

public class Checklist_detailed_Addapter extends ArrayAdapter<check_model> {

    Activity activity;
    ArrayList<String> checkListiItemName;
    ArrayList<String> checkListiItemIds;
    ArrayList<String> checkedItem;
    int resource;
    ProgressDialog ringProgressDialog;

    private LayoutInflater layoutInflater;




    public Checklist_detailed_Addapter(@NonNull Activity context, @LayoutRes int resource, ArrayList<String> checkListiItemName, ArrayList<String> checkListiItemIds,ArrayList<String> checkedItem) {
        super(context, resource);

        layoutInflater = LayoutInflater.from(context);
        this.activity = context;
        this.checkListiItemName = checkListiItemName;
        this.checkListiItemIds = checkListiItemIds;
        this.checkedItem = checkedItem;
        this.resource = resource;
    }
    @Override
    public int getCount() {
        return checkListiItemIds.size();
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();

            convertView = layoutInflater.inflate(R.layout.checklist_item_row, parent, false);
            holder.setCheckBox((CheckBox) convertView
                    .findViewById(R.id.check));

            holder.delete = (ImageView) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }


            holder.delete = (ImageView) convertView.findViewById(R.id.delete);


        holder.getCheckBox().setText(checkListiItemName.get(position));
        holder.getCheckBox().setTag(position);
        if(checkedItem.get(position).equals("1"))
        {
            holder.getCheckBox().setChecked(true);

        }
        else{
            holder.getCheckBox().setChecked(false);

        }

        final Holder finalHolder = holder;
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    check_checkbox(checkListiItemIds.get(position));

                    finalHolder.checkBox.setChecked(true);

            }
        });


        return convertView;
    }

    static class Holder {
        CheckBox checkBox;
        ImageView delete;



        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

    }

    private void check_checkbox(final String checkbox) {
        ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.CHECK_CHECKBOX, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {


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

                params.put("check", checkbox);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("id", pref.getString("user_id",""));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }

    private void un_check_checkbox(final String checkbox) {
        ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UN_CHECK_CHECKBOX, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {


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

                params.put("check", checkbox);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("id", pref.getString("user_id",""));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }




}

