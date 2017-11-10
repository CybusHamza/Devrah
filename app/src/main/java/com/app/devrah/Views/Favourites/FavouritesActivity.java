package com.app.devrah.Views.Favourites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

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
import com.app.devrah.Adapters.FavouritesAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.Main.Dashboard;
import com.app.devrah.pojo.FavouritesPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FavouritesActivity extends AppCompatActivity {
    ListView lv;
    List<FavouritesPojo> listPojo;
    FavouritesPojo favouritesPojoData;
    FavouritesAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("Favourites");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listPojo = new ArrayList<>();
        lv = (ListView) findViewById(R.id.favouriteListView);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
        getFavourite();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                Intent intent = new Intent(this, Dashboard.class);
                finish();
                startActivity(intent);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getFavourite() {

        final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();


        String URL = End_Points.FAVOURITES;
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        if(response.equals("false")){

                            FavouritesPojo myCardsPojo = new FavouritesPojo();
                            myCardsPojo.setId("");
                            myCardsPojo.setData("No data found");
                            myCardsPojo.setP_name("");
                            myCardsPojo.setP_status("");
                            myCardsPojo.setBrdid("");
                            listPojo.add(myCardsPojo);
                            adapter = new FavouritesAdapter(FavouritesActivity.this, listPojo);
                            lv.setAdapter(adapter);
                        }else {
                            try {

                                favouritesPojoData = new FavouritesPojo();

                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    FavouritesPojo myCardsPojo = new FavouritesPojo();
                                    myCardsPojo.setId(jsonObject.getString("id"));
                                    myCardsPojo.setData(jsonObject.getString("board_name"));
                                    myCardsPojo.setP_name(jsonObject.getString("project_name"));
                                    myCardsPojo.setP_status(jsonObject.getString("project_id"));
                                    myCardsPojo.setBrdid(jsonObject.getString("brdId"));
                                    listPojo.add(myCardsPojo);
                                }


                                adapter = new FavouritesAdapter(FavouritesActivity.this, listPojo);

                                lv.setAdapter(adapter);


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

                    new SweetAlertDialog(FavouritesActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(FavouritesActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userID = pref.getString("user_id", "");
                params.put("userId", userID);
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
