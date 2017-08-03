package com.app.devrah.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.DrawerPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class BoardsActivity extends AppCompatActivity {

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    ProgressDialog ringProgressDialog;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Toolbar toolbar;

    // DrawerPojo drawerPojo;
    List<DrawerPojo> dataList;

    private MenuItem mSearchAction,mDrawer;
    private boolean isSearchOpened = false;
    private boolean isEditOpened = false;
    private EditText edtSeach;
    String title;
    View logo;
    CustomDrawerAdapter adapter;
    private ListView mDrawerList;
    //   NavigationDrawerFragment drawerFragment;
//    private int[] tabIcons = {
//            R.drawable.project_group,
//            R.drawable.bg_dashboard_project,
//          //  R.drawable.ic_tab_contacts
//    };
    DrawerLayout drawerLayout;
    String projectID,projectTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);
        Intent intent  = getIntent();
        projectID = intent.getStringExtra("pid");
        projectTitle = intent.getStringExtra("ptitle");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList= (ListView) findViewById(R.id.left_drawer);
//        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
//                GravityCompat.START);


        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        final TextView tv= (TextView) toolbar.findViewById(R.id.toolbar_title);
        tv.setText(projectTitle);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditOpened=true;
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
                   isEditOpened=false;
                   tv.clearFocus();
                   InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
                    UpdateProjectName(tv.getText().toString());
               }
               return true;
           }
       });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // drawerPojo = new DrawerPojo();

        dataList = new ArrayList<>();
        View header = getLayoutInflater().inflate(R.layout.header_for_drawer,null);

        dataList.add(new DrawerPojo("Create New Team"));
        logo = getLayoutInflater().inflate(R.layout.search_bar, null);
        toolbar.addView(logo);
        logo.setVisibility(View.INVISIBLE);
        edtSeach = (EditText)toolbar.findViewById(R.id.edtSearch);
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

                        Toast.makeText(getApplicationContext(),"Menu",Toast.LENGTH_LONG).show();
                        return true;
                }

                return true;
            }
        });

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BoardsActivity.this,ProjectsActivity.class);
                finish();
                startActivity(intent);
                 onBackPressed();
            }
        });



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);

        tabOne.setText("Work Boards");


        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Reference Boards");

        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.work_boards, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);


        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.reference_boards, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

//        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabThree.setText("THREE");
//        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_contacts, 0, 0);
//        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {

        Bundle bundle = new Bundle();
        bundle.putString("pid",projectID);
        bundle.putString("ptitle",projectTitle);
        ViewPagerAdapter adapter =new  ViewPagerAdapter(getSupportFragmentManager());

        WorkBoard workBoard = new WorkBoard();
        workBoard.setArguments(bundle);


        ReferenceBoard referenceBoard= new ReferenceBoard();
        referenceBoard.setArguments(bundle);

        adapter.addFrag(workBoard, "Work Boards");
        adapter.addFrag(referenceBoard, "Archive Boards");
        //  adapter.addFrag(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
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
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        mSearchAction = menu.findItem(R.id.action_search);
//        mDrawer = menu.findItem(R.id.action_settings);
//
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }


    protected void handleMenuSearch(){
        //ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            //action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            // action.setDisplayShowTitleEnabled(true); //show the title in the action bar
            //hides the keyboard

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);
            //toolbar.setTitle("Projects");
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
            //toolbar.setTitle("");

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
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }else if(isEditOpened) {
            final TextView tv= (TextView) toolbar.findViewById(R.id.toolbar_title);
            tv.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
            UpdateProjectName(tv.getText().toString());
            isEditOpened=false;
            return;
        }else{
            Intent intent=new Intent(BoardsActivity.this,ProjectsActivity.class);
            finish();
            startActivity(intent);
        }
        super.onBackPressed();
    }
    private void doSearch() {
//
    }

    public void  openDrawer(){
        adapter = new CustomDrawerAdapter(this,R.layout.list_item_drawer,dataList);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                    case 1:
                        Toast.makeText(getApplicationContext(),"heyy",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                        finish();

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

                        ringProgressDialog.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(BoardsActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("userId", pref.getString("user_id",""));
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

}
