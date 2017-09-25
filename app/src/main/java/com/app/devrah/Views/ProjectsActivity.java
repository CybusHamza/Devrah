package com.app.devrah.Views;

import android.content.Context;
import android.content.Intent;
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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.devrah.Adapters.CustomDrawerAdapter;
import com.app.devrah.Adapters.GroupProjectAdapter;
import com.app.devrah.R;
import com.app.devrah.pojo.DrawerPojo;

import java.util.ArrayList;
import java.util.List;


public class ProjectsActivity extends AppCompatActivity {

    Toolbar toolbar;
    // DrawerPojo drawerPojo;
    List<DrawerPojo> dataList;
    String title;
    View logo;
    CustomDrawerAdapter adapter;
    static String status;
    //   NavigationDrawerFragment drawerFragment;
//    private int[] tabIcons = {
//            R.drawable.project_group,
//            R.drawable.bg_dashboard_project,
//          //  R.drawable.ic_tab_contacts
//    };
    DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuItem mSearchAction, mDrawer;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private ListView mDrawerList;
    ViewPagerAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        status="0";
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
//        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
//                GravityCompat.START);


        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("Projects");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        /*final TextView tv= (TextView) toolbar.findViewById(R.id.toolbar_title);
        tv.setText("Projects");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("Project updated");
            }
        });*/
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // drawerPojo = new DrawerPojo();

        dataList = new ArrayList<>();
        View header = getLayoutInflater().inflate(R.layout.header_for_drawer, null);

        dataList.add(new DrawerPojo("Create New Team"));
        dataList.add(new DrawerPojo("Change Status Filter"));
//        dataList.add(new DrawerPojo("Copy Project"));
//        dataList.add(new DrawerPojo("Move Project"));
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
                }

                return true;
            }
        });

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=getIntent();
                if(intent1.hasExtra("ScreenName")){
                    Intent intent = new Intent(ProjectsActivity.this, NotificationsActivity.class);
                    finish();
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ProjectsActivity.this, Dashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
                //  onBackPressed();
            }
        });


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter1 = new ViewPagerAdapter(getSupportFragmentManager());
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1){
                    ((GroupProjects)adapter1.getItem(position)).prepareDataList();
                }else if(position==0){
                    ((Projects)adapter1.getItem(position)).getProjectsData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);

        tabOne.setText("Projects Groups");


        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Projects");

        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dashboard_resize_projects, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabTwo);


        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dashboard_resize_group, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabOne);

//        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabThree.setText("THREE");
//        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_contacts, 0, 0);
//        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {

        adapter1.addFrag(new Projects(), "Projects");
        adapter1.addFrag(new GroupProjects(), "Group Projects");

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
            toolbar.setTitle("Projects");
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
        } else {
            Intent intent1=getIntent();
            if(intent1.hasExtra("ScreenName")){
                Intent intent = new Intent(ProjectsActivity.this, NotificationsActivity.class);
                finish();
                startActivity(intent);
            }else {
                Intent intent = new Intent(ProjectsActivity.this, Dashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }

        }
        super.onBackPressed();
    }

    private void doSearch() {
//
    }

    public void openDrawer() {
        adapter = new CustomDrawerAdapter(this, R.layout.list_item_drawer, dataList);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 1:
                    //    Toast.makeText(getApplicationContext(), "heyy", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                        finish();

                        break;
                    case 2:
                        changeProjectStatus();
                        break;

                }

            }
        });

    }

    private void changeProjectStatus() {
        LayoutInflater inflater = LayoutInflater.from(ProjectsActivity.this);
        View customView = inflater.inflate(R.layout.project_filter_status_menu_drawer, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(ProjectsActivity.this).create();


        LinearLayout active, inactive, complete,all;
        final ImageView imgactive, igminactive, imgcomplete,imgall,crossIcon;


        active = (LinearLayout) customView.findViewById(R.id.active);
        inactive = (LinearLayout) customView.findViewById(R.id.inactive);
        complete = (LinearLayout) customView.findViewById(R.id.completed);
        all = (LinearLayout) customView.findViewById(R.id.all);


        imgactive = (ImageView) customView.findViewById(R.id.activeimg);
        igminactive = (ImageView) customView.findViewById(R.id.inactiveimg);
        imgcomplete = (ImageView) customView.findViewById(R.id.complete);
        imgall = (ImageView) customView.findViewById(R.id.allimg);
        crossIcon = (ImageView) customView.findViewById(R.id.crossIcon);
        crossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                drawerLayout.closeDrawer(Gravity.END);
            }
        });

        if (status.equals("1")) {
            imgactive.setVisibility(View.VISIBLE);
            active.setClickable(false);
            active.setEnabled(false);
        } else if (status.equals("2")) {
            igminactive.setVisibility(View.VISIBLE);
            inactive.setClickable(false);
            inactive.setEnabled(false);
        } else if (status.equals("3")) {
            imgcomplete.setVisibility(View.VISIBLE);
            complete.setClickable(false);
            complete.setEnabled(false);
        }else if (status.equals("0")) {
            imgall.setVisibility(View.VISIBLE);
            all.setClickable(false);
            all.setEnabled(false);
        }

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="1";
                imgactive.setVisibility(View.VISIBLE);
                igminactive.setVisibility(View.GONE);
                imgcomplete.setVisibility(View.GONE);
                imgall.setVisibility(View.GONE);
                alertDialog.dismiss();
                int position=viewPager.getCurrentItem();
                if(position==1){
                    ((GroupProjects)adapter1.getItem(position)).prepareDataList();
                }else if(position==0){
                    ((Projects)adapter1.getItem(position)).getProjectsData();
                }
                drawerLayout.closeDrawer(Gravity.END);
            }
        });

        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="2";
                imgactive.setVisibility(View.GONE);
                igminactive.setVisibility(View.VISIBLE);
                imgcomplete.setVisibility(View.GONE);
                imgall.setVisibility(View.GONE);
                alertDialog.dismiss();
                int position=viewPager.getCurrentItem();
                if(position==1){
                    ((GroupProjects)adapter1.getItem(position)).prepareDataList();
                }else if(position==0){
                    ((Projects)adapter1.getItem(position)).getProjectsData();
                }
                drawerLayout.closeDrawer(Gravity.END);
            }
        });


        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="3";
                imgactive.setVisibility(View.GONE);
                igminactive.setVisibility(View.GONE);
                imgcomplete.setVisibility(View.VISIBLE);
                imgall.setVisibility(View.GONE);
                alertDialog.dismiss();
                int position=viewPager.getCurrentItem();
                if(position==1){
                    ((GroupProjects)adapter1.getItem(position)).prepareDataList();
                }else if(position==0){
                    ((Projects)adapter1.getItem(position)).getProjectsData();
                }
                drawerLayout.closeDrawer(Gravity.END);
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="0";
                imgall.setVisibility(View.VISIBLE);
                imgactive.setVisibility(View.GONE);
                igminactive.setVisibility(View.GONE);
                imgcomplete.setVisibility(View.GONE);
                alertDialog.dismiss();
                int position=viewPager.getCurrentItem();
                if(position==1){
                    ((GroupProjects)adapter1.getItem(position)).prepareDataList();
                }else if(position==0){
                    ((Projects)adapter1.getItem(position)).getProjectsData();
                }
                drawerLayout.closeDrawer(Gravity.END);
            }
        });



        alertDialog.setView(customView);
        alertDialog.show();

    }

    class ViewPagerAdapter extends FragmentPagerAdapter{
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
