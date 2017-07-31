package com.app.devrah.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.devrah.Adapters.TeamMembersAdapter;
import com.app.devrah.R;
import com.app.devrah.pojo.TeamMembersPojo;

import java.util.ArrayList;
import java.util.List;

public class CreateNewTeamActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    String title;
    View logo;

    ListView lv;
    List<TeamMembersPojo> listPojo;
    TeamMembersPojo membersPojoData;
    TeamMembersAdapter adapter;

    Button addMember,addBulkMember;

    ImageView search;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_team);
        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("Add Members");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
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
                Intent intent=new Intent(CreateNewTeamActivity.this,MenuActivity.class);
                finish();
                startActivity(intent);
                onBackPressed();
            }
        });
        addMember= (Button) findViewById(R.id.btnAddMember);
        addBulkMember= (Button) findViewById(R.id.btnAddBulk);
        search = (ImageView)findViewById(R.id.searchBtn);
        etSearch = (EditText)findViewById(R.id.etSearch);
        etSearch.setVisibility(View.INVISIBLE);
        search.setOnClickListener(this);
       etSearch.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               String nameToSearch=etSearch.getText().toString();
               ArrayList<TeamMembersPojo> filteredLeaves=new ArrayList<TeamMembersPojo>();

               for (TeamMembersPojo data: listPojo) {
                   if (data.getData().toLowerCase().contains(nameToSearch.toLowerCase())  )
                   {
                       filteredLeaves.add(data);
                   }


               }
                /*leaveDatas.clear();
                leaveDatas.addAll(filteredLeaves);
                leaves_adapter.notifyDataSetChanged();*/
               adapter = new TeamMembersAdapter(CreateNewTeamActivity.this,filteredLeaves);
               lv.setAdapter(adapter);

           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });

        addBulkMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBulkMembers();
            }
        });

        listPojo = new ArrayList<>();
        lv = (ListView)findViewById(R.id.membersListView);
        membersPojoData = new TeamMembersPojo();
        membersPojoData.setData("rizwan");
        listPojo.add(membersPojoData);
        adapter = new TeamMembersAdapter(this, listPojo);


        lv.setAdapter(adapter);
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
            toolbar.setTitle("Add Members");
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
    private void addMember(){
        LayoutInflater inflater = LayoutInflater.from(CreateNewTeamActivity.this);
        View subView = inflater.inflate(R.layout.custom_dialog_for_add_member, null);
        final EditText member = (EditText)subView.findViewById(R.id.etMember);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Member");
        builder.setMessage("Member");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!member.getText().toString().equals("")) {
                    membersPojoData = new TeamMembersPojo();
                    membersPojoData.setData(member.getText().toString());
                    listPojo.add(membersPojoData);
                    lv.setAdapter(adapter);
                }else {
                    Toast.makeText(getApplicationContext(),"Please Enter Some Name",Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
    private void addBulkMembers(){
        LayoutInflater inflater = LayoutInflater.from(CreateNewTeamActivity.this);
        View subView = inflater.inflate(R.layout.custom_dialog_for_add_bulk_members, null);
        final EditText member = (EditText)subView.findViewById(R.id.etMember);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Bulk Member");
        builder.setMessage("Member");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String[] members=member.getText().toString().split(";");
                if(!member.getText().toString().equals("")) {
                    for (int i = 0; i < members.length; i++) {
                        membersPojoData = new TeamMembersPojo();
                        membersPojoData.setData(members[i]);
                        listPojo.add(membersPojoData);

                    }

                    lv.setAdapter(adapter);
                }else {
                    Toast.makeText(getApplicationContext(),"Please Enter Some Name",Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.searchBtn:
                etSearch.setVisibility(View.VISIBLE);
        }
    }
}
