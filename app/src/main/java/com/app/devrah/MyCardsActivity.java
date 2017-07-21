package com.app.devrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import com.app.devrah.Adapters.MyCardsAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.pojo.MyCardsPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyCardsActivity extends AppCompatActivity {

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    ListView lv;
    ArrayList<MyCardsPojo> listPojo;
    MyCardsPojo myCardsPojoData;
    MyCardsAdapter adapter;
    Toolbar toolbar;
    ProgressDialog ringProgressDialog;
    String userID;
    String title;
    View logo;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    String responce;
    int Position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);
        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("My Cards");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        logo = getLayoutInflater().inflate(R.layout.search_bar, null);
        toolbar.addView(logo);
        logo.setVisibility(View.INVISIBLE);
        edtSeach = (EditText) toolbar.findViewById(R.id.edtSearch);
        toolbar.inflateMenu(R.menu.search_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.action_settings:
                        return true;
                    case R.id.action_search:
                        handleMenuSearch();
                        return true;
                }

                return true;
            }
        });
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCardsActivity.this, Dashboard.class);
                finish();
                startActivity(intent);
                onBackPressed();
            }
        });
        // setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listPojo = new ArrayList<>();
        lv = (ListView) findViewById(R.id.cardListView);

        lv.setLongClickable(true);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                MyCardsAdapter.ViewHolder vh = (MyCardsAdapter.ViewHolder) view.getTag();



               final int touchedX = (int) (vh.lastTouchedX + 0.5f);
                final int touchedY = (int) (vh.lastTouchedY + 0.5f);

                Position = position;

                view.startDrag(null, new View.DragShadowBuilder(view) {
                    @Override
                    public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
                        super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
                        shadowTouchPoint.x = touchedX;
                        shadowTouchPoint.y = touchedY;

                    }
                    @Override
                    public void onDrawShadow(Canvas canvas) {
                        super.onDrawShadow(canvas);
                    }
                }, view, 0);

                view.setVisibility(View.INVISIBLE);

                return true;


            }
        });

        lv.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                if (event.getAction() == DragEvent.ACTION_DROP) {

                    View view = (View) event.getLocalState();
                    view.setVisibility(View.VISIBLE);
                }
                if(event.getAction() == DragEvent.ACTION_DRAG_ENDED)
                {


                }
                return true;
            }

        });

                Intent intent = getIntent();


        SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userID = pref.getString("user_id", "");

        if(intent.hasExtra("mycards"))
        {

            String id = intent.getStringExtra("id");
            HashMap<String, String> params = new HashMap<>();
            params.put("", "");


            getMyCards("reload",id);

        }
        else {


            getMyCards("load","0");
        }

        }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    protected void handleMenuSearch() {

        if (isSearchOpened) { //test if the search is open


            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);
            toolbar.setTitle("My Cards");
            logo.setVisibility(View.INVISIBLE);
            edtSeach.setText("");

            isSearchOpened = false;
        } else {
            logo.setVisibility(View.VISIBLE);
            toolbar.setTitle("");

            //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            isSearchOpened = true;
        }
    }

    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }

    private void doSearch() {
        //
    }


    public void getMyCards(final String reload, final String id) {

        if(reload.equals("reload"))
        {

        }
        else {  ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        }



        String URL = End_Points.GETMYCARDS + userID;
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(reload.equals("reload"))
                        {
                            listPojo =  parseJson(response,reload,id);

                        }
                        else {
                            listPojo =  parseJson(response,reload,id);
                            ringProgressDialog.dismiss();
                        }







                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(MyCardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(MyCardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("", "");

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    public ArrayList<MyCardsPojo> parseJson(String responce , String tag ,String id)
    {
        try {

            int position = 0;
            JSONArray jsonArray = new JSONArray(responce);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MyCardsPojo myCardsPojo = new MyCardsPojo();

                myCardsPojo.setBoardname(jsonObject.getString("board_name"));
                myCardsPojo.setBoradid(jsonObject.getString("board_id"));
                myCardsPojo.setCard_name(jsonObject.getString("card_name"));
                myCardsPojo.setCardId(jsonObject.getString("card_id"));
                if(jsonObject.getString("card_id").equals(id))
                {
                    position = i;
                }
                myCardsPojo.setListid(jsonObject.getString("list_id"));
                myCardsPojo.setProjecct_id(jsonObject.getString("project_id"));
                myCardsPojo.setProjectname(jsonObject.getString("project_name"));
                myCardsPojo.setListname(jsonObject.getString("list_name"));

                listPojo.add(myCardsPojo);
            }



            if(tag.equals("reload"))
            {

                adapter = new MyCardsAdapter(MyCardsActivity.this, listPojo);
                lv.setAdapter(adapter);

                lv.setSelection(position);

            }
            else{

                adapter = new MyCardsAdapter(MyCardsActivity.this, listPojo);
                lv.setAdapter(adapter);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listPojo;
    }
}
