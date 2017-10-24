package com.app.devrah.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.app.devrah.Adapters.CheckListCommentsAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.CommentsPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CheckListComments extends AppCompatActivity {
    Toolbar toolbar;
    EditText etComments;
    ImageView sendComments;
    ListView lvComments;
    List<CommentsPojo> listPojo;
    CheckListCommentsAdapter adapter;
    String checkListId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_comments);
        Intent intent=  getIntent();
        checkListId=intent.getStringExtra("checklid");


        toolbar = (Toolbar) findViewById(R.id.header);
        etComments= (EditText) findViewById(R.id.commenttext);
        lvComments= (ListView) findViewById(R.id.listComments);
        sendComments= (ImageView) findViewById(R.id.send);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
        toolbar.inflateMenu(R.menu.my_menu);
        toolbar.setTitle("Comments");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etComments.getWindowToken(), 0);
        etComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etComments.setCursorVisible(true);
            }
        });
        sendComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comments=etComments.getText().toString();
                if(!comments.equals("") && comments.trim().length()>0) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etComments.getWindowToken(), 0);
                    addComments(etComments.getText().toString());
                    etComments.setCursorVisible(false);
                }else {
                    Toast.makeText(getApplicationContext(),"Please Enter some comment",Toast.LENGTH_LONG).show();
                }
            }
        });
        getComments();
    }
    public void getComments() {

        final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.GETCHECKLISTCOMMENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        if(response.equals("false")){


                        }else {
                            try {
                                JSONObject object = new JSONObject(response);
                                String data = object.getString("checklist_comments");
                                JSONArray jsonArray = new JSONArray(data);
                                listPojo = new ArrayList<>();
                                for (int i=0;i<jsonArray.length();i++){
                                    CommentsPojo commentsPojo=new CommentsPojo();
                                    JSONObject obj = new JSONObject(jsonArray.getString(i));
                                    commentsPojo.setId(obj.getString("id"));
                                    commentsPojo.setCardId(obj.getString("card_id"));
                                    commentsPojo.setChecklistId(obj.getString("checklist_id"));
                                    commentsPojo.setComments(obj.getString("comments"));
                                    commentsPojo.setFullName(obj.getString("fullname"));
                                    listPojo.add(commentsPojo);
                                }

                                adapter = new CheckListCommentsAdapter(CheckListComments.this, listPojo);

                                lvComments.setAdapter(adapter);


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

                    new SweetAlertDialog(CheckListComments.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(CheckListComments.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("card_id",CardActivity.cardId);
                params.put("check_id",checkListId);
               // SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
               // String userID = pref.getString("user_id", "");
                //params.put("userId", userID);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }
    public void addComments(final String comments) {

        final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.ADDCOMMENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        if(!response.equals("0")){
                            getComments();
                            etComments.setText("");
                            etComments.clearFocus();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CheckListComments.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(CheckListComments.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("card_id",CardActivity.cardId);
                params.put("check_id",checkListId);
                params.put("comments",comments);
                 SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                 String userID = pref.getString("user_id", "");
                String fullName=pref.getString("first_name","")+" "+pref.getString("last_name","");
                params.put("userId", userID);
                params.put("fullname", fullName);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

}
