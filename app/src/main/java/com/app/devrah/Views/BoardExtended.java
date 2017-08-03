package com.app.devrah.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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

    ParentBoardExtendedFragment fragment;
    //FragmentBoardExtendedLast lastFrag;

    Toolbar toolbar;
    String title;
    CustomViewPagerAdapter adapter;
    View logo;
    List<DrawerPojo> dataList;
    String b_id, p_id,projectTitle;
    CustomDrawerAdapter DrawerAdapter;
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
    Bundle bundle;

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
        projectTitle = intent.getStringExtra("ptitle");


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        title = getIntent().getStringExtra("TitleData");
        //toolbar.setTitle(title);
        final TextView tv= (TextView) toolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                intent.putExtra("ptitle",projectTitle);
                finish();
                startActivity(intent);
                onBackPressed();
            }
        });

        fragment = new ParentBoardExtendedFragment();
        fragment = (ParentBoardExtendedFragment) this.getSupportFragmentManager().findFragmentById(R.id.ajeebFrag);

        bundle = new Bundle();

        bundle.putString("b_id",b_id);
        bundle.putString("p_id",p_id);
        bundle.putString("ptitle",projectTitle);



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
        }else{
            Intent intent=new Intent(BoardExtended.this,BoardsActivity.class);
            intent.putExtra("pid",p_id);
            intent.putExtra("ptitle",projectTitle);
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

    }

    public void getList() {
        StringRequest request = new StringRequest(Request.Method.POST, GET_ALL_BOARD_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



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

                                    ParentBoardExtendedFragment.addPage(jsonObject.getString("list_name"));
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

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(BoardExtended.this.getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
//                    new SweetAlertDialog(getActivity().getC, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText("Error!")
//                            .setConfirmText("OK").setContentText("No Internet Connection")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    sDialog.dismiss();
//                                }
//                            })
//                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardExtended.this.getApplicationContext(), "TimeOut eRROR", Toast.LENGTH_SHORT).show();
//                    new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText("Error!")
//                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    sDialog.dismiss();
//                                }
//                            })
//                            .show();
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

}
