package com.app.devrah.Views.Board;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.app.devrah.Adapters.CalendarAdapter;
import com.app.devrah.Adapters.CalendarAdapterProject;
import com.app.devrah.Adapters.CustomDrawerAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.BoardExtended.BoardExtended;
import com.app.devrah.Views.ManageMembers.manage_members;
import com.app.devrah.Views.Notifications.NotificationsActivity;
import com.app.devrah.Views.Project.ProjectsActivity;
import com.app.devrah.pojo.CalendarPojo;
import com.app.devrah.pojo.CardAssociatedCalendarCheckBox;
import com.app.devrah.pojo.CardAssociatedCalendarCoverPojo;
import com.app.devrah.pojo.CardAssociatedCalendarLabelsPojo;
import com.app.devrah.pojo.CardAssociatedCalendarMembersPojo;
import com.app.devrah.pojo.DrawerPojo;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class BoardsActivity extends AppCompatActivity {

    CompactCalendarView compactCalendarView;
    CalendarAdapterProject calendarAdapter;
    ListView lv;
    ArrayList<CalendarPojo> listPojo;
    List<CardAssociatedCalendarLabelsPojo> cardLabelsPojoList;
    List<CardAssociatedCalendarMembersPojo> cardMembersPojoList;
    List<CardAssociatedCalendarCoverPojo> cardCoverPojoList;
    List<CardAssociatedCalendarCheckBox> cardCheckboxPojoList;

    Switch active, inactive, complete;
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    ProgressDialog ringProgressDialog;
    Spinner project_groups;
    Toolbar toolbar;
    ArrayAdapter<String> projectADdapter;
    // DrawerPojo drawerPojo;
    List<DrawerPojo> dataList;
    List<String> spinnerValues;
    List<String> spinnerGroupIds;
    String title, status;
    View logo;
    CustomDrawerAdapter adapter;
    //   NavigationDrawerFragment drawerFragment;
//    private int[] tabIcons = {
//            R.drawable.project_group,
//            R.drawable.bg_dashboard_project,
//          //  R.drawable.ic_tab_contacts
//    };
    DrawerLayout drawerLayout;
    String projectID, projectTitle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuItem mSearchAction, mDrawer;
    private boolean isSearchOpened = false;
    private boolean isEditOpened = false;
    private EditText edtSeach;
    private ListView mDrawerList;
    public static String ptitle="";
    public static String pstatus;
    Boolean Cancelbtn=false;
    ViewPagerAdapter adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);
        final Intent intent = getIntent();
        projectID = intent.getStringExtra("pid");
        projectTitle = intent.getStringExtra("ptitle");
        ptitle=projectTitle;
        status = intent.getStringExtra("status");
        pstatus=status;
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
//      drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
//      GravityCompat.START);


        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle(projectTitle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
       /* final TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
       // toolbar.setFocusable(true);
        //toolbar.setFocusableInTouchMode(true);
        tv.setText(projectTitle);
        tv.setFocusable(false);
        tv.setCursorVisible(false);
       // tv.setFocusableInTouchMode(true);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditOpened = false;
                tv.setCursorVisible(true);
                tv.setFocusableInTouchMode(true);
                tv.setInputType(InputType.TYPE_CLASS_TEXT);
                tv.requestFocus();
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.menu);
                toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cancel));
                Cancelbtn=true;
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(tv, 0);

                //tv.setText(tv.getText().toString());
            }
        });
        tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 6) {
                    String check=tv.getText().toString();
                    if(!check.equals("")) {
                        isEditOpened = false;
                        tv.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
                        UpdateProjectName(tv.getText().toString());
                        tv.setFocusable(false);
                        tv.setCursorVisible(false);
                        tv.setFocusableInTouchMode(false);
                        toolbar.getMenu().clear();
                        toolbar.inflateMenu(R.menu.menu_with_back_button);
                        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
                    }else {
                        Toast.makeText(BoardsActivity.this,"Project Name is must!",Toast.LENGTH_LONG).show();
                    }

                }
                return true;
            }
        });*/

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // drawerPojo = new DrawerPojo();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = pref.getString("user_id", "");
        dataList = new ArrayList<>();
        View header = getLayoutInflater().inflate(R.layout.header_for_drawer, null);
        dataList.add(new DrawerPojo("Update Project Name"));
        dataList.add(new DrawerPojo("Manage Status"));
        dataList.add(new DrawerPojo("Copy Project"));
        dataList.add(new DrawerPojo("Move Project"));
        dataList.add(new DrawerPojo("Manage Members"));
        dataList.add(new DrawerPojo("Leave Project"));
        dataList.add(new DrawerPojo("Calendar"));
      //  if(intent.hasExtra("project_created_by")) {
        getProjectCreatedBy();
        //}
        logo = getLayoutInflater().inflate(R.layout.search_bar, null);
        toolbar.addView(logo);
        logo.setVisibility(View.INVISIBLE);
        edtSeach = (EditText) toolbar.findViewById(R.id.edtSearch);
        toolbar.inflateMenu(R.menu.search_menu);

        openDrawer();
        mDrawerList.addHeaderView(header);

//        adapter = new CustomDrawerAdapter(this,R.layout.list_item_drawer,dataList);
//        mDrawerList.setAdapter(adapter);
//        //drawerFragment = new NavigationDrawerFragment();

        // drawerFragment.setup((DrawerLayout) findViewById(R.id.drawerlayout), toolbar);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.action_search:
                        handleMenuSearch();
                        return true;

                    case R.id.action_settings:
                        drawerLayout.openDrawer(Gravity.RIGHT);

                        openDrawer();

                      //  Toast.makeText(getApplicationContext(), "Menu", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.tick:
                        final TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
                        String check=tv.getText().toString();
                        if(!check.equals("")) {
                            tv.clearFocus();
                            tv.setCursorVisible(false);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
                            UpdateProjectName(tv.getText().toString());
                            toolbar.getMenu().clear();
                            toolbar.inflateMenu(R.menu.menu_with_back_button);
                            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
                        }else {
                            Toast.makeText(BoardsActivity.this,"Project Name is must!",Toast.LENGTH_LONG).show();
                        }
                        return true;
                }

                return true;
            }
        });

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Cancelbtn){
                    final TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
                    tv.setText(projectTitle);
                    tv.clearFocus();
                    tv.setCursorVisible(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.menu_with_back_button);
                    toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
                    Cancelbtn=false;
                }else {
                    if(intent.hasExtra("ScreenName")){
                        Intent intent = new Intent(BoardsActivity.this, NotificationsActivity.class);
                        finish();
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(BoardsActivity.this, ProjectsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        overridePendingTransition(0, 0);
                        finish();
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                   onBackPressed();
                }
            }
        });


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
    }

    private void getProjectCreatedBy() {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        /*ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();*/
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_PROJECT_CREATED_BY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1"))
                        dataList.add(new DrawerPojo("Delete Project"));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

              //  ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                } else if (error instanceof TimeoutError) {

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("project_id", projectID);
                params.put("userId", pref.getString("user_id", ""));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(BoardsActivity.this);
        requestQueue.add(request);
    }

    public void updateData(){
        int position=viewPager.getCurrentItem();
        if(position==1){
            try {
                ((ReferenceBoard) adapter1.getItem(position)).Refrence();
            }catch (OutOfMemoryError error){
                error.printStackTrace();
            }
        }else if(position==0){
            try {
                ((WorkBoard) adapter1.getItem(position)).getWorkBoards();
            }catch (OutOfMemoryError error){
                error.printStackTrace();
            }
        }
    }
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setTextColor(getResources().getColor(R.color.colorWhite));
        tabOne.setTextSize(15);
        tabOne.setText("Work Boards");


        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setTextColor(getResources().getColor(R.color.colorWhite));
        tabTwo.setTextSize(15);
        tabTwo.setText("Archive Boards");

       // tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.work_boards, 0, 0);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.orange));
        tabLayout.setSelectedTabIndicatorHeight(15);
        tabLayout.getTabAt(1).setCustomView(tabTwo);


       // tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.reference_boards, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

//        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabThree.setText("THREE");
//        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_contacts, 0, 0);
//        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {

        Bundle bundle = new Bundle();
        bundle.putString("pid", projectID);
        bundle.putString("ptitle", projectTitle);
        adapter1 = new ViewPagerAdapter(getSupportFragmentManager());

        WorkBoard workBoard = new WorkBoard();
        workBoard.setArguments(bundle);


        ReferenceBoard referenceBoard = new ReferenceBoard();
        referenceBoard.setArguments(bundle);

        adapter1.addFrag(workBoard, "Work Boards");
        adapter1.addFrag(referenceBoard, "Archive Boards");
        //  adapter.addFrag(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        mSearchAction = menu.findItem(R.id.action_search);
//        mDrawer = menu.findItem(R.id.action_settings);
//
//        return super.onPrepareOptionsMenu(menu);
//    }

    protected void handleMenuSearch() {
        //ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open

            //action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            // action.setDisplayShowTitleEnabled(true); //show the title in the action bar
            //hides the keyboard

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);
            toolbar.setTitle(projectTitle);
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
            UpdateProjectName(tv.getText().toString());
            isEditOpened = false;
            return;
        } else {
            Intent intent = getIntent();
            if(intent.hasExtra("ScreenName")){
                Intent intent1 = new Intent(BoardsActivity.this, NotificationsActivity.class);
                finish();
                startActivity(intent1);
            }else {
                Intent intent1 = new Intent(BoardsActivity.this, ProjectsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0, 0);
                finish();
                startActivity(intent1);
                overridePendingTransition(0, 0);
            }
        }
        super.onBackPressed();
    }

    private void doSearch() {
//
    }
    private void updateProjectNameDialog() {
        LayoutInflater inflater = LayoutInflater.from(BoardsActivity.this);
        View customView = inflater.inflate(R.layout.update_card_name_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(BoardsActivity.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(customView);
        alertDialog.show();

        TextView cancel, copy;
        final EditText etCardName= (EditText) customView.findViewById(R.id.etCardName);
        final TextView tvheading= (TextView) customView.findViewById(R.id.heading);
        tvheading.setText("Update Project Name");
        etCardName.setText(projectTitle);
        etCardName.setSelection(etCardName.getText().length());
        showKeyBoard(etCardName);

        copy = (TextView) customView.findViewById(R.id.copy);
        cancel = (TextView) customView.findViewById(R.id.close);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check=etCardName.getText().toString();
                if(!check.equals("") && check!="" && check.trim().length()>0) {
                    UpdateProjectName(etCardName.getText().toString());
                    hideKeyBoard(etCardName);
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(BoardsActivity.this,"Project Name is must!",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyBoard(etCardName);
                alertDialog.dismiss();

            }
        });

    }
    private void showKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
    private void hideKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(title.getWindowToken(), 0);
    }
    public void openDrawer() {
        adapter = new CustomDrawerAdapter(this, R.layout.list_item_drawer, dataList);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 1:
                        updateProjectNameDialog();
                        break;
                    case 2:
                        showStatus();

                        break;


                    case 3:

                        showDialog("copy");
                        break;


                    case 4:

                        showDialog("move");
                        break;


                    case 5:

                        Intent intent = new Intent(BoardsActivity.this, manage_members.class);
                        intent.putExtra("P_id",projectID);
                        startActivity(intent);

                        break;

                    case 6:
                        new SweetAlertDialog(BoardsActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Confirmation!")
                                .setCancelText("Cancel")
                                .setConfirmText("OK").setContentText("Are You sure you want to leave this project")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismiss();
                                        LeaveProject();
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
                    case 7:
                        showCalendarDialog();

                        break;
                    case 8:
                        final TextView textView = new TextView(BoardsActivity.this);
                        textView.setText(getResources().getString(R.string.deleteProject));
                        new SweetAlertDialog(BoardsActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Confirmation!")
                                .setCancelText("Cancel")
                                .setConfirmText("OK")
                                .setCustomView(textView)
                              //  .setContentText(getResources().getString(R.string.deleteProject))
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismiss();
                                        DeleteProject();
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

    public void UpdateProjectName(final String updatedText) {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_PROJECT_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ptitle=updatedText;
                        projectTitle=updatedText;
                        toolbar.setTitle(updatedText);
                        ringProgressDialog.dismiss();
                        Toast.makeText(BoardsActivity.this, "Project Name Updated Successfully", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(Gravity.END);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(BoardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(BoardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("project_id", projectID);
                params.put("userId", pref.getString("user_id", ""));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(BoardsActivity.this);
        requestQueue.add(request);


    }
    private void showCalendarDialog() {
        final SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

        LayoutInflater inflater = LayoutInflater.from(BoardsActivity.this);
        View customView = inflater.inflate(R.layout.calendar_view_dialog, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(BoardsActivity.this,R.style.full_screen_dialog).create();
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        alertDialog.getWindow().setLayout(320, DrawerLayout.LayoutParams.MATCH_PARENT);

        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        wmlp.gravity = Gravity.END;
        alertDialog.setView(customView);
        alertDialog.show();
        final TextView heading= (TextView) customView.findViewById(R.id.header);
        final ImageView backBtn= (ImageView) customView.findViewById(R.id.backBtnCalender);
        compactCalendarView = (CompactCalendarView)customView.findViewById(R.id.compactcalendar_view);
        lv= (ListView) customView.findViewById(R.id.listView);
        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        Date dat=new Date();
        heading.setText(dateFormatForMonth.format(dat));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

       /* for(int i=1;i<10;i=i+2) {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            cal.set(Calendar.DAY_OF_MONTH,i);
            cal.set(Calendar.MONTH, 9);
            cal.set(Calendar.YEAR, 2017);
            long time = cal.getTimeInMillis();
            Event event=new Event(Color.GREEN,time,"event");
            compactCalendarView.addEvent(event);
        }*/
        List<Event> events = compactCalendarView.getEvents(1433701251000L); // can also take a Date object

        // events has size 2 with the 2 events inserted previously
//        Log.d(TAG, "Events: " + events);

        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);

                if(events.toArray().length==0){
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
                    String cardsByDate=dateFormat.format(dateClicked);
                    Toast.makeText(BoardsActivity.this,"No cards due on "+cardsByDate,Toast.LENGTH_LONG).show();
                }else {
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String cardsByDate=dateFormat.format(dateClicked);
                    //getCards(cardsByDate);
                    for(int i=listPojo.size()-1;i>=0;i--){
                        if(listPojo.get(i).getDueDate().equals(cardsByDate)){
                            lv.setSelection(i);
                        }
                    }

                }

//                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
//                int month=firstDayOfNewMonth.getMonth();
//                String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

                // String monthname=(String)android.text.format.DateFormat.format("MMM", month);
                heading.setText(dateFormatForMonth.format(firstDayOfNewMonth));

                //  Toast.makeText(CalenderEvents.this,firstDayOfNewMonth.getDate(),Toast.LENGTH_LONG).show();
                //   Log.d("month", "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String cardsByDate=dateFormat.format(dat);
        getCards("",alertDialog);
        getDueDates(cardsByDate);
    }
    private void getDueDates(final String currentDate){
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.GET_DUE_DATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            if (!response.equals("false")) {
                                JSONArray jsonArray = new JSONArray(response);
                                compactCalendarView.removeAllEvents();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String dueDate=jsonObject.getString("card_end_date");
                                    String[] dueDateSplit=dueDate.split("-");

                                    if(dueDateSplit.length==3) {
                                        int dayOfMonth = Integer.parseInt(dueDateSplit[2]);
                                        int month = Integer.parseInt(dueDateSplit[1]);
                                        int year = Integer.parseInt(dueDateSplit[0]);
                                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        cal.set(Calendar.MONTH, month-1);
                                        cal.set(Calendar.YEAR, year);
                                        long time = cal.getTimeInMillis();
                                        Event event = new Event(Color.GREEN, time, "event");
                                        compactCalendarView.addEvent(event);
                                    }


                                }
                                //  if(compactCalendarView.getEvents())
                            }
                            //getCards("");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {


                    Toast.makeText(BoardsActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardsActivity.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("uid",  pref.getString("user_id",""));
                params.put("project_id", projectID);
                params.put("board_id", "");
                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardsActivity.this.getApplicationContext());
        requestQueue.add(request);
    }
    private void getCards(final  String dueDate,final AlertDialog alertDialog){
        final SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.GETMYCARDSBYDUEDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if (!(response.equals("false"))) {
                            listPojo = new ArrayList<>();
                            cardLabelsPojoList = new ArrayList<>();
                            cardMembersPojoList = new ArrayList<>();
                            cardCoverPojoList = new ArrayList<>();
                            cardCheckboxPojoList = new ArrayList<>();
                            try {
                                CalendarPojo projectsPojo = null;
                                JSONObject mainObject=new JSONObject(response);
                                JSONArray jsonArrayCards = mainObject.getJSONArray("cards");
                                JSONArray jsonArrayLabels = mainObject.getJSONArray("labels");
                                JSONArray jsonArrayMembers = mainObject.getJSONArray("members");
                                JSONArray jsonArrayAttachments = mainObject.getJSONArray("attachments");
                                JSONArray jsonArrayAttachmentsCover = mainObject.getJSONArray("attachment_cover");
                                JSONArray jsonArrayCheckbox = mainObject.getJSONArray("checkbox");

                                //JSONObject cardsObject = jsonArray.getJSONObject(0);

                                //  row=jsonArrayCards.length();
                                for (int i = 0; i < jsonArrayCards.length(); i++) {
                                    JSONObject jsonObject = jsonArrayCards.getJSONObject(i);
                                    JSONArray jsonArray=jsonArrayAttachments.getJSONArray(i);

                                    //JSONObject jsonObject1 = jsonArrayAttachments.getJSONObject(i);

                                    projectsPojo = new CalendarPojo();
                                    // CardAssociatedLabelsPojo labelsPojo = new CardAssociatedLabelsPojo();
                                    projectsPojo.setId(jsonObject.getString("id"));
                                    projectsPojo.setData(jsonObject.getString("card_name"));
                                    projectsPojo.setAttachment(jsonObject.getString("file_name"));
                                    projectsPojo.setDueDate(jsonObject.getString("card_end_date"));
                                    projectsPojo.setDuetTime(jsonObject.getString("card_due_time"));
                                    projectsPojo.setStartDate(jsonObject.getString("card_start_date"));
                                    projectsPojo.setCardDescription(jsonObject.getString("card_description"));
                                    projectsPojo.setIsCardComplete(jsonObject.getString("card_is_complete"));
                                    projectsPojo.setStartTime(jsonObject.getString("card_start_time"));
                                    projectsPojo.setIsCardLocked(jsonObject.getString("is_locked"));
                                    projectsPojo.setIsCardSubscribed(jsonObject.getString("subscribed"));
                                    // projectsPojo.setCardAssignedMemberId(jsonObject.getString("crd_assigned_membr_id"));
                                    projectsPojo.setnOfAttachments(String.valueOf(jsonArray.length()));
                                    projectsPojo.setAssignedTo(jsonObject.getString("assigned_to"));
                                    projectsPojo.setListId(jsonObject.getString("list_id"));
                                    projectsPojo.setBoardID(jsonObject.getString("board_id"));

                                    // projectsPojo.setBoardAssociatedLabelsId(jsonObject.getString("board_assoc_label_id"));
                                    //projectsPojo.setLabels(jsonObject.getString("label_color"));

//                                    cardLabelsPojoList.add(labelsPojo);

                                    listPojo.add(projectsPojo);
                                    // getLabelsList(jsonObject.getString("id"));

                                }
                                for(int j=0;j<jsonArrayLabels.length();j++){
                                    CardAssociatedCalendarLabelsPojo labelsPojo = new CardAssociatedCalendarLabelsPojo();
                                    JSONArray jsonArray=jsonArrayLabels.getJSONArray(j);
                                    String[] labels = new String[jsonArray.length()];
                                    String[] labelText = new String[jsonArray.length()];
                                    for (int k=0;k<jsonArray.length();k++){

                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        labels[k]=jsonObject.getString("label_color");
                                        if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }
                                    }
                                    labelsPojo.setLabels(labels);
                                    labelsPojo.setLabelText(labelText);
                                    cardLabelsPojoList.add(labelsPojo);
                                }

                                for(int j=0;j<jsonArrayMembers.length();j++){
                                    CardAssociatedCalendarMembersPojo membersPojo = new CardAssociatedCalendarMembersPojo();
                                    JSONArray jsonArray=jsonArrayMembers.getJSONArray(j);
                                    String[] members = new String[jsonArray.length()];
                                    String[] labelText = new String[jsonArray.length()];
                                    String[] gp_picture = new String[jsonArray.length()];
                                    String subsribed = "";
                                    // SharedPreferences pref = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                                    for (int k=0;k<jsonArray.length();k++){

                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        members[k]=jsonObject.getString("profile_pic");
                                        labelText[k]=jsonObject.getString("initials");
                                        gp_picture[k]=jsonObject.getString("gp_picture");
                                        if(jsonObject.getString("uid").equals(pref.getString("user_id",""))) {
                                            subsribed = jsonObject.getString("subscribed");
                                        }
                                       /* if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }*/
                                    }
                                    membersPojo.setMembers(members);
                                    membersPojo.setInitials(labelText);
                                    membersPojo.setGp_pictures(gp_picture);
                                    membersPojo.setMemberSubscribed(subsribed);
                                    //  labelsPojo.setLabelText(labelText);
                                    cardMembersPojoList.add(membersPojo);
                                }
                                for(int j=0;j<jsonArrayAttachmentsCover.length();j++){
                                    CardAssociatedCalendarCoverPojo membersPojo = new CardAssociatedCalendarCoverPojo();
                                    JSONArray jsonArray=jsonArrayAttachmentsCover.getJSONArray(j);
                                    String[] attachmentName = new String[jsonArray.length()];
                                    String[] makeCover = new String[jsonArray.length()];
                                    // String attachmentName=null;
                                    //String makeCover=null;
                                    for (int k=0;k<jsonArray.length();k++){

                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        attachmentName[k]=jsonObject.getString("file_name");
                                        makeCover[k]=jsonObject.getString("make_cover");
                                       /* if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }*/
                                    }
                                    membersPojo.setFileName(attachmentName);
                                    membersPojo.setIsCover(makeCover);
                                    //  labelsPojo.setLabelText(labelText);
                                    cardCoverPojoList.add(membersPojo);
                                }
                                for(int j=0;j<jsonArrayCheckbox.length();j++){
                                    CardAssociatedCalendarCheckBox checkBoxPojo = new CardAssociatedCalendarCheckBox();
                                    JSONArray jsonArray=jsonArrayCheckbox.getJSONArray(j);
                                    checkBoxPojo.setTotalCheckBoxes(jsonArray.length());
                                    int checked=0;
                                    for (int k=0;k<jsonArray.length();k++){

                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        if(jsonObject.getString("is_checked").equals("1")){
                                            checked++;
                                        }
                                    }
                                    checkBoxPojo.setCheckedCheckBox(String.valueOf(checked));

                                    //  labelsPojo.setLabelText(labelText);
                                    cardCheckboxPojoList.add(checkBoxPojo);
                                }

                                calendarAdapter = new CalendarAdapterProject(BoardsActivity.this, listPojo,cardLabelsPojoList,cardMembersPojoList,cardCoverPojoList,0,alertDialog,cardCheckboxPojoList,projectID,projectTitle,status);
                                lv.setAdapter(calendarAdapter);

                                /*try {
                                    cardAssociatedLabelsAdapter = new CardAssociatedLabelsAdapter(getActivity(), cardLabelsPojoList);
                                    cardAssociatedLabelRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
                                    cardAssociatedLabelRecycler.setAdapter(cardAssociatedLabelsAdapter);
                                }catch (Exception e){
                                    String s=e.toString();
                                }*/
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


                    Toast.makeText(BoardsActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardsActivity.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("uid",  pref.getString("user_id",""));
                params.put("project_id",projectID);
               params.put("board_id", "");
                params.put("card_due_date",dueDate);


                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardsActivity.this.getApplicationContext());
        requestQueue.add(request);
    }
    public void LeaveProject() {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.LEAVE_PROJECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        if(response.equals("1")){
                            Toast.makeText(BoardsActivity.this,"You are admin of this project , You can not leave project !",Toast.LENGTH_LONG).show();
                        }else if(response.equals("{\"project_left\":1}")) {
                            Intent intent = new Intent(BoardsActivity.this, ProjectsActivity.class);
                            finish();
                            startActivity(intent);
                        }else {
                            Toast.makeText(BoardsActivity.this,"No data found!",Toast.LENGTH_LONG).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(BoardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(BoardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("project_id", projectID);
                params.put("userId", pref.getString("user_id", ""));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(BoardsActivity.this);
        requestQueue.add(request);


    }
    public void DeleteProject() {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.DELETE_PROJECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        if(response.equals("1")){
                            Toast.makeText(BoardsActivity.this,"You cannot delete this project",Toast.LENGTH_LONG).show();
                        }else if(response.equals("{\"project_left\":1}")) {
                            Intent intent = new Intent(BoardsActivity.this, ProjectsActivity.class);
                            finish();
                            startActivity(intent);
                        }else {
                            Toast.makeText(BoardsActivity.this,"No data found!",Toast.LENGTH_LONG).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(BoardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(BoardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("project_id", projectID);
                params.put("userId", pref.getString("user_id", ""));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(BoardsActivity.this);
        requestQueue.add(request);


    }

    public void showStatus() {
        LayoutInflater inflater = LayoutInflater.from(BoardsActivity.this);
        View customView = inflater.inflate(R.layout.status_menu_drawer, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(BoardsActivity.this).create();
        alertDialog.setCancelable(false);

       // LinearLayout active, inactive, complete;

        final ImageView imgactive, igminactive, imgcomplete,crossIcon;


        active = (Switch) customView.findViewById(R.id.active);
        inactive = (Switch) customView.findViewById(R.id.inactive);
        complete = (Switch) customView.findViewById(R.id.completed);


        /*imgactive = (ImageView) customView.findViewById(R.id.activeimg);
        igminactive = (ImageView) customView.findViewById(R.id.inactiveimg);
        imgcomplete = (ImageView) customView.findViewById(R.id.complete);*/
        crossIcon = (ImageView) customView.findViewById(R.id.crossIcon);
        crossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
              //  drawerLayout.closeDrawer(Gravity.END);
            }
        });
        if (status.equals("1")) {
           // imgactive.setVisibility(View.VISIBLE);
            active.setChecked(true);
            inactive.setChecked(false);
            complete.setChecked(false);
            active.setClickable(false);
            //active.setEnabled(false);
        } else if (status.equals("2")) {
            active.setChecked(false);
            inactive.setChecked(true);
            complete.setChecked(false);
           // igminactive.setVisibility(View.VISIBLE);
            inactive.setClickable(false);
           // inactive.setEnabled(false);
        } else if (!(status.equals("null"))) {
            active.setChecked(false);
            inactive.setChecked(false);
            complete.setChecked(true);
           // imgcomplete.setVisibility(View.VISIBLE);
            complete.setClickable(false);
            //complete.setEnabled(false);
        }

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeStatus("1");
              //  alertDialog.dismiss();
                /*imgactive.setVisibility(View.VISIBLE);
                igminactive.setVisibility(View.GONE);
                imgcomplete.setVisibility(View.GONE);*/
            }
        });

        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeStatus("2");
                //alertDialog.dismiss();
               /* imgactive.setVisibility(View.GONE);
                igminactive.setVisibility(View.VISIBLE);
                imgcomplete.setVisibility(View.GONE);*/
            }
        });


        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeStatus("3");
                //alertDialog.dismiss();

               /*imgactive.setVisibility(View.GONE);
                igminactive.setVisibility(View.GONE);
                imgcomplete.setVisibility(View.VISIBLE);*/
            }
        });


        alertDialog.setView(customView);
        alertDialog.show();


    }

    public void showDialog(final String action) {

        LayoutInflater inflater = LayoutInflater.from(BoardsActivity.this);
        View customView = inflater.inflate(R.layout.move_card_menu, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(BoardsActivity.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(customView);
        alertDialog.show();

        TextView heading, sub,headingTitle;

        TextView cancel, copy;
        final EditText ettitle;

        heading = (TextView) customView.findViewById(R.id.heading);
        headingTitle = (TextView) customView.findViewById(R.id.title);
        sub = (TextView) customView.findViewById(R.id.sub_heading);
        ettitle = (EditText) customView.findViewById(R.id.etTitle);
        project_groups = (Spinner) customView.findViewById(R.id.projects_group);
        copy = (TextView) customView.findViewById(R.id.copy);
        cancel = (TextView) customView.findViewById(R.id.close);
        ettitle.setText(toolbar.getTitle());
        ettitle.setSelection(toolbar.getTitle().length());
        if (action.equals("copy"))
        showKeyBoard(ettitle);

        getSpinnerData();

        if (action.equals("move")) {
            heading.setText("Move Project");
            sub.setText("Move To Group : ");
            copy.setText("Move");
            ettitle.setVisibility(View.GONE);
            headingTitle.setVisibility(View.GONE);
        }else{
            heading.setText("Copy Project");
            sub.setText("Copy To Group : ");
            copy.setText("Copy");
            ettitle.setVisibility(View.VISIBLE);
            headingTitle.setVisibility(View.VISIBLE);
        }


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (action.equals("move")) {
                    moveProject();
                    alertDialog.dismiss();
                } else {
                    copyProject(ettitle.getText().toString());
                    hideKeyBoard(ettitle);
                    alertDialog.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard(ettitle);
                alertDialog.dismiss();

            }
        });


    }

    public void getSpinnerData() {


        spinnerValues = new ArrayList<>();
        spinnerGroupIds = new ArrayList<>();


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_SPINNER_GROUP_PROJECTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = new JSONObject(array.getString(i));
                                spinnerValues.add(String.valueOf(object.get("pg_name")));
                                spinnerGroupIds.add(String.valueOf(object.get("pg_id")));

                            }

                            projectADdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.nothing_selected_spinnerdate, spinnerValues);
                            projectADdapter.setDropDownViewResource(R.layout.nothing_selected_spinnerdate);
                            project_groups.setAdapter(projectADdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardsActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardsActivity.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

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

    public void copyProject(final String projectName) {

        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.COPY_PROJECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if(!response.equals("0")) {
                            Toast.makeText(BoardsActivity.this, "Project has been copied successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardsActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardsActivity.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userId = pref.getString("user_id", "");

                int pos = project_groups.getSelectedItemPosition();
                params.put("userId", userId);
                params.put("project_id", projectID);
                params.put("project_name", projectName);
                params.put("group_id", spinnerGroupIds.get(pos));
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

    public void moveProject() {


        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.MOVE_PROJECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        Toast.makeText(BoardsActivity.this, "Project has been moved successfully", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardsActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardsActivity.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                int pos = project_groups.getSelectedItemPosition();

                params.put("project_id", projectID);
                params.put("group_id", spinnerGroupIds.get(pos));
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

    public void changeStatus(final String stats) {


        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.PROJECT_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        status=stats;
                        if (status.equals("1")) {
                            // imgactive.setVisibility(View.VISIBLE);
                            active.setChecked(true);
                            inactive.setChecked(false);
                            complete.setChecked(false);
                            active.setClickable(false);
                            inactive.setClickable(true);
                            complete.setClickable(true);
                            //active.setEnabled(false);
                        } else if (status.equals("2")) {
                            active.setChecked(false);
                            inactive.setChecked(true);
                            complete.setChecked(false);
                            active.setClickable(true);
                            inactive.setClickable(false);
                            // igminactive.setVisibility(View.VISIBLE);
                            complete.setClickable(true);
                            // inactive.setEnabled(false);
                        } else if (!(status.equals("null"))) {
                            active.setChecked(false);
                            inactive.setChecked(false);
                            complete.setChecked(true);
                            // imgcomplete.setVisibility(View.VISIBLE);
                            complete.setClickable(false);
                            active.setClickable(true);
                            inactive.setClickable(true);
                            //complete.setEnabled(false);
                        }
                       // Toast.makeText(BoardsActivity.this, response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardsActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardsActivity.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userId = pref.getString("user_id", "");


                params.put("project_id", projectID);
                params.put("project_status", stats);
                params.put("userId", userId);

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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
