package com.app.devrah;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.devrah.Adapters.CustomDrawerAdapter;
import com.app.devrah.pojo.DrawerPojo;

import java.util.ArrayList;
import java.util.List;

public class BoardsActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    Toolbar toolbar;

    View logo;
    EditText edtSeach;

    List<DrawerPojo> dataList;
    Menu menu;
    private int[] tabIcons = {
            R.drawable.work_boards,
            R.drawable.reference_boards,
            //  R.drawable.ic_tab_contacts
    };


    CustomDrawerAdapter adapter;
    private ListView mDrawerList;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList= (ListView) findViewById(R.id.left_drawer);


        viewPager = (ViewPager) findViewById(R.id.viewpagerBoards);
        setupViewPager(viewPager);

        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("Projects");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataList = new ArrayList<>();


        dataList.add(new DrawerPojo("Filter Cards"));
        dataList.add(new DrawerPojo("Copy"));
        dataList.add(new DrawerPojo("Move"));
        dataList.add(new DrawerPojo("Manage Members"));
        dataList.add(new DrawerPojo("Leave Board"));
        //   getSupportActionBar().setDisplayShowHomeEnabled(true);
        logo = getLayoutInflater().inflate(R.layout.search_bar, null);
        toolbar.addView(logo);
        logo.setVisibility(View.INVISIBLE);
        edtSeach = (EditText)toolbar.findViewById(R.id.edtSearch);
        toolbar.inflateMenu(R.menu.search_menu);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id) {

//                    case R.id.action_search:
//                        handleMenuSearch();
//                        return true;

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
                Intent intent=new Intent(BoardsActivity.this,Dashboard.class);
                finish();
                startActivity(intent);
                //  onBackPressed();
            }
        });



        //  toolbar = (Toolbar) findViewById(R.id.toolbar);

      //  toolbar.inflateMenu(R.menu.back_button_menu);
//        setSupportActionBar(toolbar);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(getResources().getColor(R.color.colorGreen));
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"LALALA",Toast.LENGTH_SHORT).show();
//                onBackPressed();
//            }
//        });
//
//

//        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
//        setSupportActionBar(toolbar);


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

//
//        toolbar.setNavigationIcon(R.drawable.back_arrow_white);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"dcvnm",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            }
//        });

        openDrawer();
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //What to do on back clicked
//
//                Toast.makeText(getApplicationContext(),"poiuytr",Toast.LENGTH_SHORT).show();
//
//            }
//        });




//        toolbar = (Toolbar) findViewById(R.id.app_bar);
//
//
//        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
//        toolbar.setTitle("sxcvbn");


//        toolbar = (Toolbar) findViewById(R.id.boards_appbar);
//        toolbar.inflateMenu(R.menu.back_button_menu);
//       // toolbar.inflateMenu(R.menu.menu_with_back_button);
//        toolbar.setTitle("Select Information");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        toolbar.setNavigationIcon(R.drawable.back_arrow_white);

//        if (getSupportActionBar()!= null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }


//       setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow_white);
//
//      //  toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

       // toolbar.setNavigationIcon();
      //  toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));

//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id = item.getItemId();
//
//                switch (id) {
////                    case R.id.action_settings:
////                        return true;
//                    case R.id.back_btn:
//                        Toast.makeText(getApplicationContext(),"poiuytr",Toast.LENGTH_SHORT).show();
//                        return true;
//                }
//
//                return true;
//            }
//        });


//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"sdfnm",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(BoardsActivity.this,Projects.class);
//                startActivity(intent);
//            }
//        });





        tabLayout = (TabLayout) findViewById(R.id.tabsBoards);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    public void  openDrawer(){
        adapter = new CustomDrawerAdapter(this,R.layout.list_item_drawer,dataList);
        mDrawerList.setAdapter(adapter);

    }


    //
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {

//        getMenuInflater().inflate(R.menu.back_button_menu,menu);
//
//
//
//
//        MenuItem item = menu.findItem(R.id.back_btn);
//        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                Toast.makeText(getApplicationContext(),"Whatever",Toast.LENGTH_SHORT).show();
//
//                return true;
//            }
//        });
//        this.menu = menu;
//super


//return super.onCreateOptionsMenu(menu);
//    }
//MenuItem
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        /*int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        else if(id == android.R.id.home){
//            Intent i= new Intent(this, Dashboard.class);
//            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//
//             startActivity(i);
//            finish();
//            return true;
//        }
//*/
//
//        finish();
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




////////////////////////////////////////////////////////////////////////////////////////////////////
//        @Override
//   public boolean onOptionsItemSelected(MenuItem m){
//
//            if (m.getItemId() == R.id.back_btn){
//              Toast.makeText(getApplicationContext(),"Heyyy",Toast.LENGTH_SHORT).show();
//                finish();
//            }
//
//            return super.onOptionsItemSelected(m);
//        }
////////////////////////////////////////////////////////////////////////////////////////////////////

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//     //   finish();
//        return super.onCreateOptionsMenu(menu);
//    }
//@Override
//public boolean onSupportNavigateUp() {
//    onBackPressed();
//    return true;
//}

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//      //  mSearchAction = menu.findItem(R.id.action_search);
//        return super.onPrepareOptionsMenu(menu);
//    }


    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Work Boards");

        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.work_boards, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Reference Boards");

        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.reference_boards, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

//        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabThree.setText("THREE");
//        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_contacts, 0, 0);
//        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter =new  ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new WorkBoard(), "Work Boards");
        adapter.addFrag(new ReferenceBoard(), "Reference Boards");
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


}

