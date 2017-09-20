package com.app.devrah.Views;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.devrah.Adapters.CustomDrawerAdapter;
import com.app.devrah.Fragments.InboxFragment;
import com.app.devrah.Fragments.SentMessagesFragment;
import com.app.devrah.R;
import com.app.devrah.pojo.DrawerPojo;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Toolbar toolbar;
    ViewPagerAdapter adapter;
    public static String filter;
    CustomDrawerAdapter adapterDrawer;
    List<DrawerPojo> dataList;
    DrawerLayout drawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        filter="1";
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("Messages");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.notifications_viewpager);
        setupViewPager(viewPager);
        dataList = new ArrayList<>();
        dataList.add(new DrawerPojo("Change Message Filter"));
        toolbar.inflateMenu(R.menu.my_menu);
        openDrawer();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id) {

                    case R.id.menu:
                       // drawerLayout.openDrawer(Gravity.RIGHT);

                        //openDrawer();
                        changeFilter();

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
                Intent intent = new Intent(MessagesActivity.this, Dashboard.class);
                finish();
                startActivity(intent);
                //  onBackPressed();
            }
        });


        tabLayout = (TabLayout) findViewById(R.id.notifications_tabs);

        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1){
                    ((SentMessagesFragment)adapter.getItem(position)).sentMessages();
                }else if(position==0){
                    ((InboxFragment)adapter.getItem(position)).getNotifications();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu_filter, menu);

        return super.onCreateOptionsMenu(menu);

    }
    public void openDrawer() {
        adapterDrawer = new CustomDrawerAdapter(this, R.layout.list_item_drawer, dataList);
        mDrawerList.setAdapter(adapterDrawer);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 1:
                        changeFilter();

                        break;

                }

            }
        });

    }

    private void changeFilter() {
        LayoutInflater inflater = LayoutInflater.from(MessagesActivity.this);
        View customView = inflater.inflate(R.layout.filter_asc_desc_layout, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(MessagesActivity.this).create();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.TOP | Gravity.RIGHT;

       // wlp.width = LayoutParams.MATCH_PARENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);


        LinearLayout active, inactive;
        final ImageView imgactive, igminactive,crossIcon;


        active = (LinearLayout) customView.findViewById(R.id.active);
        inactive = (LinearLayout) customView.findViewById(R.id.inactive);


        imgactive = (ImageView) customView.findViewById(R.id.activeimg);
        igminactive = (ImageView) customView.findViewById(R.id.inactiveimg);
        if (filter.equals("1")) {
            imgactive.setVisibility(View.VISIBLE);
            active.setClickable(false);
            active.setEnabled(false);
        } else if (filter.equals("0")) {
            igminactive.setVisibility(View.VISIBLE);
            inactive.setClickable(false);
            inactive.setEnabled(false);
        }
        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter="1";
                imgactive.setVisibility(View.VISIBLE);
                igminactive.setVisibility(View.GONE);

                alertDialog.dismiss();
                int position=viewPager.getCurrentItem();
                if(position==1){
                    ((SentMessagesFragment)adapter.getItem(position)).sentMessages();
                }else if(position==0){
                    ((InboxFragment)adapter.getItem(position)).getNotifications();
                }
            }
        });

        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter="0";
                imgactive.setVisibility(View.GONE);
                igminactive.setVisibility(View.VISIBLE);
                alertDialog.dismiss();
                int position=viewPager.getCurrentItem();
                if(position==1){
                    ((SentMessagesFragment)adapter.getItem(position)).sentMessages();
                }else if(position==0){
                    ((InboxFragment)adapter.getItem(position)).getNotifications();
                }
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

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Inbox");

        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dashboard_resize_group, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Sent");

        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dashboard_resize_projects, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

//        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabThree.setText("THREE");
//        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_contacts, 0, 0);
//        tabLayout.getTabAt(2).setCustomView(tabThree);
    }
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new InboxFragment(), "Inbox");
        adapter.addFrag(new SentMessagesFragment(), "Sent");
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                Intent intent=new Intent(this,Dashboard.class);
                finish();
                startActivity(intent);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void OnPageSelected (int position) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        switch (position){

            case 0 : ((InboxFragment)adapter.getItem(position)).getNotifications();
            case 1 : ((SentMessagesFragment)adapter.getItem(position)).sentMessages();
        }
    }
    @Override
    public void onBackPressed() {
            Intent intent = new Intent(MessagesActivity.this, Dashboard.class);
            finish();
            startActivity(intent);
        super.onBackPressed();
    }

}
