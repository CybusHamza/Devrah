package com.app.devrah;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.devrah.Adapters.CustomDrawerAdapter;
import com.app.devrah.Adapters.CustomViewPagerAdapter;
import com.app.devrah.pojo.DrawerPojo;

import java.util.ArrayList;
import java.util.List;

public class BoardExtended extends AppCompatActivity {

    ParentBoardExtendedFragment fragment;
    //FragmentBoardExtendedLast lastFrag;
    EditText etPageName;
    Button addFrag;
    Toolbar toolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    String title;
    CustomViewPagerAdapter adapter;
    View logo;


    List<DrawerPojo> dataList;

    CustomDrawerAdapter DrawerAdapter;
    private ListView mDrawerList;
    //   NavigationDrawerFragment drawerFragment;
//    private int[] tabIcons = {
//            R.drawable.project_group,
//            R.drawable.bg_dashboard_project,
//          //  R.drawable.ic_tab_contacts
//    };
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_extended);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList= (ListView) findViewById(R.id.left_drawer);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        title = getIntent().getStringExtra("TitleData");
        toolbar.setTitle(title);
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



        edtSeach = (EditText)toolbar.findViewById(R.id.edtSearch);
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
                        drawerLayout.openDrawer(Gravity.RIGHT);
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
//                Intent intent=new Intent(BoardExtended.this,BoardsActivity.class);
//                finish();
//                startActivity(intent);
                onBackPressed();
            }
        });

        fragment = (ParentBoardExtendedFragment)this.getSupportFragmentManager().findFragmentById(R.id.ajeebFrag);
    //..    lastFrag = (FragmentBoardExtendedLast)this.getSupportFragmentManager().;
      //  etPageName =(EditText)findViewById(R.id.editTextPageName);
      //  addFrag = (Button)findViewById(R.id.btnAddFrag);

        openDrawer();

        fragment.addPage("To Do");
        fragment.addPage("Doing");
        fragment.addPage("Done");
//      //  fragment.addPage("Add Page");

//Toast.makeText(getApplicationContext(),String.valueOf(),Toast.LENGTH_SHORT).show();  ;

        fragment.addPageAt(CustomViewPagerAdapter.customCount());   //CustomView PagerAdapter last index
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


    protected void handleMenuSearch(){
       //ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

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
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }
    private void doSearch() {
//
    }

    public void  openDrawer(){
        DrawerAdapter = new CustomDrawerAdapter(this,R.layout.list_item_drawer,dataList);
        mDrawerList.setAdapter( DrawerAdapter);

    }

}
