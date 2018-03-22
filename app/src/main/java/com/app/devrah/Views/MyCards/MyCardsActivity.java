package com.app.devrah.Views.MyCards;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.app.devrah.Adapters.MyCardsAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.Main.Dashboard;
import com.app.devrah.pojo.MyCardsPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.app.devrah.Network.End_Points.SAVE_CARD_BY_LIST_ID;

public class MyCardsActivity extends AppCompatActivity {

    ArrayList<String> project_name ;
    ArrayList<String> project_ids ;

    // boards arraylist

    ArrayList<String> boards_name ;
    ArrayList<String> boards_ids ;

    ArrayList<String> list_name ;
    ArrayList<String> list_ids ;

    // cards arraylist

    ArrayList<String> card_name ;
    ArrayList<String> card_ids ;

    int p_pos,b_pos,c_pos;

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
    Button addNewCard;
    AlertDialog myalertdialog;
    Spinner projectSpinner,boardSpinner,listSpinner;
    TextView saveCard,cancelCard;
    EditText etCardName;
    public static Boolean isMyCardsActive=false;
    String filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);
        registerReceiver(broadcastReceiver, new IntentFilter("broadCastName"));
        isMyCardsActive=true;
        filter="1";
        SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userID = pref.getString("user_id", "");
        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("My Cards");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
      //  logo = getLayoutInflater().inflate(R.layout.search_bar, null);
       // toolbar.addView(logo);
        //logo.setVisibility(View.INVISIBLE);
        //edtSeach = (EditText) toolbar.findViewById(R.id.edtSearch);
        toolbar.inflateMenu(R.menu.my_menu_filter);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    /*case R.id.action_settings:
                        return true;
                    case R.id.action_search:
                        handleMenuSearch();
                        return true;*/
                    case R.id.menu:
                        changeFilter();
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
                isMyCardsActive=false;
                onBackPressed();
            }
        });
        // setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listPojo = new ArrayList<>();
        lv = (ListView) findViewById(R.id.cardListView);
        addNewCard= (Button) findViewById(R.id.buttonAddCard);
        addNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCardDialog();
            }
        });

        /*lv.setLongClickable(true);

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

        });*/

                Intent intent = getIntent();


        if(intent.hasExtra("mycards"))
        {

            String id = intent.getStringExtra("id");
            HashMap<String, String> params = new HashMap<>();
            params.put("", "");

            overridePendingTransition(0,0);
            getMyCards("reload",id);

        }
        else {


            getMyCards("load","0");
        }

        }
    // Add this inside your class
    BroadcastReceiver broadcastReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle b = intent.getExtras();

            String message = b.getString("message");
            if(message!=""){
                getMyCards("reload",message);
            }

            Log.e("newmesage", "" + message);
        }
    };
    private void addCardDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MyCardsActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.custom_dialog_add_new_card, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        projectSpinner=(Spinner)dialogView.findViewById(R.id.projectSpinner);
        boardSpinner=(Spinner)dialogView.findViewById(R.id.boardSpinner);
        listSpinner=(Spinner)dialogView.findViewById(R.id.listSpinner);
        saveCard=(TextView)dialogView.findViewById(R.id.saveCardBtn);
        cancelCard=(TextView)dialogView.findViewById(R.id.close);
        etCardName= (EditText)dialogView.findViewById(R.id.etCardName);
        cancelCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myalertdialog.dismiss();
            }
        });
        saveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    p_pos = projectSpinner.getSelectedItemPosition();
                    b_pos = boardSpinner.getSelectedItemPosition();
                    c_pos = listSpinner.getSelectedItemPosition();
                    String cardName=etCardName.getText().toString();
                    if(p_pos!=-1 && p_pos!=0 && b_pos!=-1 && b_pos!=0 && c_pos!=-1 && c_pos!=0 && !cardName.equals("") && cardName!="") {
                        myalertdialog.dismiss();
                        addnewCard(project_ids.get(p_pos).toString(),boards_ids.get(b_pos).toString(),list_ids.get(c_pos).toString(),etCardName.getText().toString());
                    }else {
                        if(p_pos==-1 || p_pos==0){
                            Toast.makeText(MyCardsActivity.this,"Project Name is must",Toast.LENGTH_LONG).show();
                        }
                        else if(b_pos==-1 || b_pos==0){
                            Toast.makeText(MyCardsActivity.this,"Board Name is must",Toast.LENGTH_LONG).show();
                        }
                        else if(c_pos==-1 || c_pos==0){
                            Toast.makeText(MyCardsActivity.this,"List Name is must",Toast.LENGTH_LONG).show();
                        }
                        else if(cardName.equals("")){
                            Toast.makeText(MyCardsActivity.this,"Card Name is must",Toast.LENGTH_LONG).show();
                        }
                    }


            }
        });



        myalertdialog = builder.create();
        myalertdialog.show();
        getProjects();
    }
    private void changeFilter() {
        LayoutInflater inflater = LayoutInflater.from(MyCardsActivity.this);
        View customView = inflater.inflate(R.layout.filter_asc_desc_layout, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(MyCardsActivity.this).create();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.TOP | Gravity.RIGHT;

        // wlp.width = LayoutParams.MATCH_PARENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);


       // LinearLayout active, inactive;
        Switch active, inactive;
        final ImageView imgactive, igminactive,crossIcon;
        TextView tvHeading;
        tvHeading= (TextView) customView.findViewById(R.id.tvHeading);
        active = (Switch) customView.findViewById(R.id.newestFirst);
        inactive = (Switch) customView.findViewById(R.id.oldestFirst);
        tvHeading.setText("Change Status Filter");


       /* imgactive = (ImageView) customView.findViewById(R.id.activeimg);
        igminactive = (ImageView) customView.findViewById(R.id.inactiveimg);*/
        if (filter.equals("1")) {
            active.setChecked(true);
            inactive.setChecked(false);
            //imgactive.setVisibility(View.VISIBLE);
            active.setClickable(false);
            //active.setEnabled(false);
        } else if (filter.equals("0")) {
            active.setChecked(false);
            inactive.setChecked(true);
           // igminactive.setVisibility(View.VISIBLE);
            inactive.setClickable(false);
           // inactive.setEnabled(false);
        }
        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter="1";
                /*imgactive.setVisibility(View.VISIBLE);
                igminactive.setVisibility(View.GONE);*/
                alertDialog.dismiss();
                getMyCards("load","0");
            }
        });

        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter="0";
                /*imgactive.setVisibility(View.GONE);
                igminactive.setVisibility(View.VISIBLE);*/
                alertDialog.dismiss();
                getMyCards("load","0");

            }
        });
        crossIcon = (ImageView) customView.findViewById(R.id.crossIcon);
        crossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.setView(customView);
        alertDialog.show();

    }
    private void addnewCard(final String p_id, final String b_id, final String l_id, final String card_name) {
        ringProgressDialog = ProgressDialog.show(MyCardsActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        final SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, SAVE_CARD_BY_LIST_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] labels=new String[0];
                        ringProgressDialog.dismiss();
                        new SweetAlertDialog(MyCardsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Success!")
                                .setConfirmText("OK").setContentText("Card Added Successfully")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismiss();
                                        Intent intent = getIntent();
                                        String id = intent.getStringExtra("id");
                                        getMyCards("load","0");
                                    }
                                })
                                .show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    new SweetAlertDialog(MyCardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("board_id",b_id);
                params.put("prjct_id", p_id);
                params.put("userId", pref.getString("user_id",""));
                params.put("list_id", l_id);
                params.put("name", card_name);
                params.put("row", "1");
                params.put("my_card", "1");
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MyCardsActivity.this);
        requestQueue.add(request);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    protected void onStop(){
        super.onStop();
        try {
            unregisterReceiver(broadcastReceiver);
        }catch (Exception e){
            e.printStackTrace();
        }
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
        isMyCardsActive=false;
        super.onBackPressed();
    }

    private void doSearch() {
        //
    }
    public void updateUI(){

    }

    public void getMyCards(final String reload, final String id) {

        if(reload.equals("reload"))
        {

        }
        else {  ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.show();
        }



        String URL = End_Points.GETMYCARDS;
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(reload.equals("reload"))
                        {
                            listPojo =  parseJson(response,reload,id);

                        }
                        else {
                            listPojo = new ArrayList<>();
                            listPojo =  parseJson(response,reload,id);
                            ringProgressDialog.dismiss();
                        }







                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),"check your internet connection",Toast.LENGTH_LONG).show();

                   /* new SweetAlertDialog(MyCardsActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("check your internet connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();*/
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(),"Connection TimeOut! Please check your internet connection.",Toast.LENGTH_LONG).show();
/*
                    new SweetAlertDialog(MyCardsActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();*/
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("uid", userID);
                params.put("filter", filter);
                params.put("project_id", "0");
                params.put("board_id", "0");

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
            if(jsonArray.length()<1){
                MyCardsPojo myCardsPojo = new MyCardsPojo();
                    myCardsPojo.setBoardname("");
                    myCardsPojo.setBoradid("");
                    myCardsPojo.setCard_name("No Cards found");
                    myCardsPojo.setCardId("");
                    myCardsPojo.setListid("");
                    myCardsPojo.setProjecct_id("");
                    myCardsPojo.setProjectname("");
                    myCardsPojo.setListname("");
                    myCardsPojo.setDueDate("");
                    myCardsPojo.setStartDate("");
                    myCardsPojo.setDueTime("");
                    myCardsPojo.setStartTime("");
                    myCardsPojo.setIsCardComplete("");
                    myCardsPojo.setIsCardLocked("");
                    myCardsPojo.setIsCardSubscribed("");
                    myCardsPojo.setCardDescription("");

                    listPojo.add(myCardsPojo);
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MyCardsPojo myCardsPojo = new MyCardsPojo();
                if(tag.equals("reload") && jsonObject.getString("card_id").equals(id) && jsonObject.getString("project_status").equals("1")){
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
                    myCardsPojo.setDueDate(jsonObject.getString("card_end_date"));
                    myCardsPojo.setStartDate(jsonObject.getString("card_start_date"));
                    myCardsPojo.setDueTime(jsonObject.getString("card_due_time"));
                    myCardsPojo.setStartTime(jsonObject.getString("card_start_time"));
                    myCardsPojo.setIsCardComplete(jsonObject.getString("card_is_complete"));
                    myCardsPojo.setIsCardLocked(jsonObject.getString("is_locked"));
                    myCardsPojo.setIsCardSubscribed(jsonObject.getString("subscribed"));
                    myCardsPojo.setCardDescription(jsonObject.getString("card_description"));

                    listPojo.add(myCardsPojo);


                }else if(!tag.equals("reload")&& jsonObject.getString("project_status").equals("1")){
                    myCardsPojo.setBoardname(jsonObject.getString("board_name"));
                    myCardsPojo.setBoradid(jsonObject.getString("board_id"));
                    myCardsPojo.setCard_name(jsonObject.getString("card_name"));
                    myCardsPojo.setCardId(jsonObject.getString("card_id"));
                    if (jsonObject.getString("card_id").equals(id)) {
                        position = i;
                    }
                    myCardsPojo.setListid(jsonObject.getString("list_id"));
                    myCardsPojo.setProjecct_id(jsonObject.getString("project_id"));
                    myCardsPojo.setProjectname(jsonObject.getString("project_name"));
                    myCardsPojo.setListname(jsonObject.getString("list_name"));
                    myCardsPojo.setDueDate(jsonObject.getString("card_end_date"));
                    myCardsPojo.setStartDate(jsonObject.getString("card_start_date"));
                    myCardsPojo.setDueTime(jsonObject.getString("card_due_time"));
                    myCardsPojo.setStartTime(jsonObject.getString("card_start_time"));
                    myCardsPojo.setIsCardComplete(jsonObject.getString("card_is_complete"));
                    myCardsPojo.setIsCardLocked(jsonObject.getString("is_locked"));
                    myCardsPojo.setIsCardSubscribed(jsonObject.getString("subscribed"));
                    myCardsPojo.setCardDescription(jsonObject.getString("card_description"));

                    listPojo.add(myCardsPojo);
                }
            }



            if(tag.equals("reload"))
            {
               /* adapter = new MyCardsAdapter(MyCardsActivity.this, listPojo);
                lv.setAdapter(adapter);*/
                /*adapter = new MyCardsAdapter(MyCardsActivity.this, listPojo);
                lv.setAdapter(adapter);*/
                adapter.notifyDataSetChanged();

                lv.setSelection(listPojo.size()-1);


            }
            else{

                adapter = new MyCardsAdapter(MyCardsActivity.this, listPojo);
               // adapter.notifyDataSetChanged();

                lv.setAdapter(adapter);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listPojo;
    }
   /* private void appendToList(ArrayList<MyCardsPojo> listPojo) {
        for(String data : newData)
            adapter.add(data);

        adapter.notifyDatasetChanged();
    }*/
    public void getProjects() {
        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_MEMBER_PROJECTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        project_name = new ArrayList<>();
                        project_ids = new ArrayList<>();

                        project_name.add(0,"Select");
                        project_ids.add(0,"0");

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0 ; i < jsonArray.length();i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                project_name.add(jsonObject.getString("project_name"));
                                project_ids.add(jsonObject.getString("project_id"));

                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                    (MyCardsActivity.this,android.R.layout.simple_spinner_dropdown_item,project_name);

                            projectSpinner.setAdapter(dataAdapter);

                            projectSpinner.setOnItemSelectedListener(new MyCardsActivity.CustomOnItemSelectedListener_projects());



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(MyCardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                SharedPreferences pref =getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userId = pref.getString("user_id", "");

                params.put("id", userId);
                params.put("status", "0");

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(MyCardsActivity.this);
        requestQueue.add(request);


    }
    public class CustomOnItemSelectedListener_projects implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, final int pos,
                                   long id) {

            if(pos == 0)
            {
                //Toast.makeText(MyCardsActivity.this,"Please Select Project", Toast.LENGTH_SHORT).show();
            }
            else
            {
                getBorads(project_ids.get(pos));
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }
    public void getBorads(final String p_Id) {
        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_WORK_BOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        boards_ids = new ArrayList<>();
                        boards_name = new ArrayList<>();

                        boards_name.add(0,"Select");
                        boards_ids.add(0,"0");

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0 ; i < jsonArray.length();i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                boards_name.add(jsonObject.getString("board_name"));
                                boards_ids.add(jsonObject.getString("id"));

                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                    (MyCardsActivity.this,android.R.layout.simple_spinner_dropdown_item,boards_name);

                            boardSpinner.setAdapter(dataAdapter);

                            boardSpinner.setOnItemSelectedListener(new MyCardsActivity.CustomOnItemSelectedListener_boards());



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(MyCardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                params.put("p_id",p_Id);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(MyCardsActivity.this);
        requestQueue.add(request);


    }
    public class CustomOnItemSelectedListener_boards implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, final int pos,
                                   long id) {

            if(pos == 0)
            {
               // Toast.makeText(MyCardsActivity.this,"Please Select Board", Toast.LENGTH_SHORT).show();
            }
            else
            {
                getlists(boards_ids.get(pos));
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }
    public void getlists ( final String board_id) {
        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_ALL_BOARD_LISTS_FOR_CARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        list_ids = new ArrayList<>();
                        list_name = new ArrayList<>();

                        list_name.add(0,"Select");
                        list_ids.add(0,"0");

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0 ; i < jsonArray.length();i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                list_name.add(jsonObject.getString("name"));
                                list_ids.add(jsonObject.getString("id"));

                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                    (MyCardsActivity.this,android.R.layout.simple_spinner_dropdown_item,list_name);

                            listSpinner.setAdapter(dataAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(MyCardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                params.put("brd_id",board_id);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(MyCardsActivity.this);
        requestQueue.add(request);


    }
}
