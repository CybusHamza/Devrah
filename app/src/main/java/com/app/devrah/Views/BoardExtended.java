package com.app.devrah.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.app.devrah.Adapters.CustomDrawerAdapter;
import com.app.devrah.Adapters.CustomViewPagerAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.DrawerPojo;
import com.app.devrah.pojo.ProjectsPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.app.devrah.Network.End_Points.GET_ALL_BOARD_LIST;

public class BoardExtended extends AppCompatActivity {

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    ProgressDialog ringProgressDialog;
    private boolean isEditOpened = false;
    ParentBoardExtendedFragment fragment;
    //FragmentBoardExtendedLast lastFrag;
    Spinner Projects,Postions;
    Toolbar toolbar;
    String title;
    CustomViewPagerAdapter adapter;
    View logo;
    List<DrawerPojo> dataList;
    String b_id, p_id,projectTitle,list_id;
    public static String projectId;
    public static String boardId;
    CustomDrawerAdapter DrawerAdapter;
    List<String> spinnerValues;
    List<String> spinnerGroupIds;
    List<String> postions_list;
     //   NavigationDrawerFragment drawerFragment;
//    private int[] tabIcons = {
//            R.drawable.project_group,
//            R.drawable.bg_dashboard_project,
//          //  R.drawable.ic_tab_contacts
//    };
    DrawerLayout drawerLayout;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private ListView mDrawerList;
 //   Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_extended);

        getList();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        Intent intent = this.getIntent();
        b_id = intent.getStringExtra("b_id");
        p_id = intent.getStringExtra("p_id");
     //   list_id = intent.getStringExtra("list_id");

        projectTitle = intent.getStringExtra("ptitle");
        projectId=p_id;
        boardId=b_id;


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        title = getIntent().getStringExtra("TitleData");
        //toolbar.setTitle(title);
        final TextView tv= (TextView) toolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setCursorVisible(false);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditOpened = true;
                tv.setCursorVisible(true);
                tv.setFocusableInTouchMode(true);
                tv.setInputType(InputType.TYPE_CLASS_TEXT);
                tv.requestFocus();
                //tv.setText(tv.getText().toString());
            }
        });
        tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId==6 ) {
                    isEditOpened = false;
                    tv.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
                    UpdateBoardName(tv.getText().toString());

                }
                return true;
            }
        });
        // adapter = new CustomViewPagerAdapter(getFragmentManager(),getApplicationContext(),)
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        logo = getLayoutInflater().inflate(R.layout.search_bar, null);
        toolbar.addView(logo);
        logo.setVisibility(View.INVISIBLE);

        dataList = new ArrayList<>();


        dataList.add(new DrawerPojo("Filter Cards"));
        dataList.add(new DrawerPojo("Copy"));
        dataList.add(new DrawerPojo("Move"));
        dataList.add(new DrawerPojo("Manage Members"));
        dataList.add(new DrawerPojo("Leave Board"));


        edtSeach = (EditText) toolbar.findViewById(R.id.edtSearch);
        toolbar.inflateMenu(R.menu.menu_with_back_button);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id) {
//                    case R.id.action_settings:
//                        return true;
                    case R.id.action_search:
                        handleMenuSearch();
                        return true;

                    case R.id.action_settings:
                        drawerLayout.openDrawer(Gravity.END);
                        openDrawer();
                        return true;


                }

                return true;
            }
        });
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BoardExtended.this,BoardsActivity.class);
                intent.putExtra("pid",p_id);
                //projectTitle=BoardsActivity.ptitle;
                intent.putExtra("ptitle",BoardsActivity.ptitle);
                finish();
                startActivity(intent);
                onBackPressed();
            }
        });

        fragment = new ParentBoardExtendedFragment();
        fragment = (ParentBoardExtendedFragment) this.getSupportFragmentManager().findFragmentById(R.id.ajeebFrag);

//        bundle = new Bundle();
//
//        bundle.putString("b_id",b_id);
//        bundle.putString("p_id",p_id);
//        bundle.putString("list_id",list_id);
//        bundle.putString("ptitle",projectTitle);



        //..    lastFrag = (FragmentBoardExtendedLast)this.getSupportFragmentManager().;
        //  etPageName =(EditText)findViewById(R.id.editTextPageName);
        //  addFrag = (Button)findViewById(R.id.btnAddFrag);

        openDrawer();

       /* fragment.addPage("To Do");
        fragment.addPage("Doing");
        fragment.addPage("Done");*/
//      //  fragment.addPage("Add Page");

//Toast.makeText(getApplicationContext(),String.valueOf(),Toast.LENGTH_SHORT).show();  ;
        //CustomView PagerAdapter last index
//        addFrag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!etPageName.getText().toString().equals("")) {
//                    fragment.addPage(etPageName.getText() + "");
//                  //  etPageName.setText(etPageName.getText() + "");
//                } else {
//                    Toast.makeText(BoardExtended.this, "Page name is empty", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }


    protected void handleMenuSearch() {
        //ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open

            //action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            // action.setDisplayShowTitleEnabled(true); //show the title in the action bar
            //hides the keyboard

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);
            toolbar.setTitle(title);
            logo.setVisibility(View.INVISIBLE);
            edtSeach.setText("");

            //add the search icon in the action bar
            // mSearchAction.setIcon(getResources().getDrawable(R.drawable.search_workboard));

            isSearchOpened = false;
        } else { //open the search entry

            //     action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            logo.setVisibility(View.VISIBLE);

            //  action.setCustomView(R.layout.search_bar);//add the custom view
            // action.setDisplayShowTitleEnabled(false); //hide the title
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


            //add the close icon
//            mSearchAction.setIcon(getResources().getDrawable(R.drawable.search_workboard));

            isSearchOpened = true;
        }
    }

    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            handleMenuSearch();
            return;
        } else if (isEditOpened) {
            final TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
            tv.clearFocus();
            tv.setCursorVisible(false);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
            UpdateBoardName(tv.getText().toString());
            isEditOpened = false;
            return;
        } else{
            Intent intent=new Intent(BoardExtended.this,BoardsActivity.class);
            intent.putExtra("pid",p_id);

            intent.putExtra("ptitle", BoardsActivity.ptitle);
            finish();
            startActivity(intent);
        }
        super.onBackPressed();
    }

    private void doSearch() {
//
    }

    public void openDrawer() {
        DrawerAdapter = new CustomDrawerAdapter(this, R.layout.list_item_drawer, dataList);
        mDrawerList.setAdapter(DrawerAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {

                    case 0:

                        break;


                    case 1:

                        showDialog("copy");
                        break;


                    case 2:

                        showDialog("move");
                        break;


                    case 3:

                        Intent intent = new Intent(BoardExtended.this, Manage_Board_Members.class);
                        intent.putExtra("P_id",p_id);
                        intent.putExtra("b_id",b_id);
                        startActivity(intent);

                        break;

                    case 4:
                        new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Confirm!")
                                .setCancelText("Cancle")
                                .setConfirmText("OK").setContentText("Cards are associated with this board. Do you really want to leave?")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        LeaveBoard();
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
                        break;

                    case 5:
                        new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Confirm!")
                                .setCancelText("Cancle")
                                .setConfirmText("OK").setContentText("You will no longer recieve notifications and view activities of this board. Do you really want to delete?")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        LeaveBoard();
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
                        break;
                }
            }
        });

    }

    private void LeaveBoard() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.LEAVE_BOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                            if(response.equals("2"))
                            {
                                Toast.makeText(BoardExtended.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.equals("1"))
                            {
                                Toast.makeText(BoardExtended.this, "you are admin you cannot leave this Board", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(BoardExtended.this, "Board Left Successfully", Toast.LENGTH_SHORT).show();

                            }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(BoardExtended.this, "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent=new Intent(BoardExtended.this,BoardsActivity.class);
                                    intent.putExtra("pid",p_id);
                                    intent.putExtra("ptitle",projectTitle);
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardExtended.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent=new Intent(BoardExtended.this,BoardsActivity.class);
                                    intent.putExtra("pid",p_id);
                                    intent.putExtra("ptitle",projectTitle);
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", b_id);
                params.put("project_id", p_id);
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

                params.put("userId", pref.getString("user_id",""));

                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardExtended.this.getApplicationContext());
        requestQueue.add(request);

    }

    private void deleteBoard() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, GET_ALL_BOARD_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        if (response.equals("false")) {
                            ParentBoardExtendedFragment.removeAllFrags();
                            ParentBoardExtendedFragment.addPageAt(CustomViewPagerAdapter.customCount());

                        } else {


                            ParentBoardExtendedFragment.removeAllFrags();


                            try {
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    ProjectsPojo projectsPojo = new ProjectsPojo();

                                    projectsPojo.setId(jsonObject.getString("list_id"));
                                    projectsPojo.setData(jsonObject.getString("list_name"));
                                    projectsPojo.setListId(jsonObject.getString("list_id"));

                                    ParentBoardExtendedFragment.addPage(jsonObject.getString("list_name"),p_id,b_id,jsonObject.getString("list_id"),jsonObject.getString("list_color"),"");
                                    //     parentBoardExtendedFragment.setArguments(bundle);

                                }


                                ParentBoardExtendedFragment.addPageAt(CustomViewPagerAdapter.customCount());


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


                    Toast.makeText(BoardExtended.this, "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent=new Intent(BoardExtended.this,BoardsActivity.class);
                                    intent.putExtra("pid",p_id);
                                    intent.putExtra("ptitle",projectTitle);
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardExtended.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent=new Intent(BoardExtended.this,BoardsActivity.class);
                                    intent.putExtra("pid",p_id);
                                    intent.putExtra("ptitle",projectTitle);
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", b_id);
                params.put("project_id", p_id);
                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardExtended.this.getApplicationContext());
        requestQueue.add(request);

    }
    private void showDialog(final String data) {

        LayoutInflater inflater = LayoutInflater.from(BoardExtended.this);
        View customView = inflater.inflate(R.layout.move_board_menu, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(BoardExtended.this).create();

        alertDialog.setView(customView);
        alertDialog.show();

        TextView heading, sub;

        Button cancel, copy;

        heading = (TextView) customView.findViewById(R.id.heading);
        sub = (TextView) customView.findViewById(R.id.sub_heading);
        Postions = (Spinner) customView.findViewById(R.id.position);
        Projects = (Spinner) customView.findViewById(R.id.projects_group);
        copy = (Button) customView.findViewById(R.id.copy);
        cancel = (Button) customView.findViewById(R.id.close);


        getSpinnerData();

        if (data.equals("move")) {
            heading.setText("Move Board");
            sub.setText("Move To Project ");
            copy.setText("Move");
        }


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (data.equals("move")) {
                    moveBoard();
                } else {
                    copyBoard();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                alertDialog.dismiss();

            }
        });

    }

    public void getList() {

        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, GET_ALL_BOARD_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        if (response.equals("false")) {
                            ParentBoardExtendedFragment.removeAllFrags();
                            ParentBoardExtendedFragment.addPageAt(CustomViewPagerAdapter.customCount());

                        } else {


                            ParentBoardExtendedFragment.removeAllFrags();


                            try {
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    ProjectsPojo projectsPojo = new ProjectsPojo();

                                    projectsPojo.setId(jsonObject.getString("list_id"));
                                    projectsPojo.setData(jsonObject.getString("list_name"));
                                    projectsPojo.setListId(jsonObject.getString("list_id"));
                                    projectsPojo.setListColor(jsonObject.getString("list_color"));

                                    ParentBoardExtendedFragment.addPage(jsonObject.getString("list_name"),p_id,b_id,jsonObject.getString("list_id"),jsonObject.getString("list_color"),"");
                               //     parentBoardExtendedFragment.setArguments(bundle);

                                }


                                ParentBoardExtendedFragment.addPageAt(CustomViewPagerAdapter.customCount());


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


                    Toast.makeText(BoardExtended.this, "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent=new Intent(BoardExtended.this,BoardsActivity.class);
                                    intent.putExtra("pid",p_id);
                                    intent.putExtra("ptitle",projectTitle);
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardExtended.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent=new Intent(BoardExtended.this,BoardsActivity.class);
                                    intent.putExtra("pid",p_id);
                                    intent.putExtra("ptitle",projectTitle);
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", b_id);
                params.put("project_id", p_id);
                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardExtended.this.getApplicationContext());
        requestQueue.add(request);
    }


    public void UpdateBoardName(final String updatedText) {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_BOARD_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("updt_txt", updatedText);
                params.put("board_id", b_id);
                params.put("userId", pref.getString("user_id",""));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(BoardExtended.this);
        requestQueue.add(request);


    }
    public void getSpinnerData() {


        spinnerValues = new ArrayList<>();
        spinnerGroupIds = new ArrayList<>();
        postions_list = new ArrayList<>();


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_ALL_PROJECS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = new JSONObject(array.getString(i));
                                spinnerValues.add(String.valueOf(object.get("project_name")));
                                spinnerGroupIds.add(String.valueOf(object.get("project_id")));

                            }

                            ArrayAdapter<String> projectADdapter;
                            projectADdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.nothing_selected_spinnerdate, spinnerValues);
                            projectADdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            Projects.setAdapter(projectADdapter);
                            Projects.setOnItemSelectedListener(new CustomOnItemSelectedListener_boards());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardExtended.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardExtended.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userId = pref.getString("user_id", "");

                params.put("user_id", userId);
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
    public class CustomOnItemSelectedListener_boards implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, final int pos,
                                   long id) {

                getPosition(spinnerGroupIds.get(pos));



        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }


    public void getPosition(final String P_id) {

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GETPOSITION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            postions_list = new ArrayList<>();
                            for (int i = 1; i <=Integer.valueOf(response); i++) {

                                postions_list.add(i+"");
                            }

                            ArrayAdapter<String> projectADdapter;
                            projectADdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.nothing_selected_spinnerdate, postions_list);
                            projectADdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                             Postions.setAdapter(projectADdapter);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardExtended.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardExtended.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("p_id", P_id);
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

    public void moveBoard() {


        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.MOVE_BORAD_TO_OTHER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        Toast.makeText(BoardExtended.this, "Moved Successfully", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardExtended.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardExtended.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                int pos1 = Postions.getSelectedItemPosition();
                int pos = Projects.getSelectedItemPosition();
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);


                params.put("originalboardid", b_id);
                params.put("originalprojectid", p_id);
                params.put("project_id_to", spinnerGroupIds.get(pos));
                params.put("position_to", postions_list.get(pos1));
                params.put("userId",pref.getString("user_id",""));
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

    public void copyBoard() {


        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.COPY_BORAD_TO_OTHER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        Toast.makeText(BoardExtended.this, "Copied Successfully", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardExtended.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardExtended.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                int pos = Projects.getSelectedItemPosition();
                int pos1 = Postions.getSelectedItemPosition();
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);


                params.put("originalboardid", b_id);
                params.put("originalprojectid", p_id);
                params.put("project_id_to", spinnerGroupIds.get(pos));
                params.put("position_to", postions_list.get(pos1));
                params.put("userId",pref.getString("user_id",""));
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
