package com.app.devrah.Views.Teams;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.app.devrah.Adapters.TeamMembersAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.TeamMembersPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateNewTeamActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    Toolbar toolbar;
    String title;
    View logo;
    AutoCompleteTextView member;
    ListView lv;
    List<TeamMembersPojo> listPojo;
    TeamMembersPojo membersPojoData;
    TeamMembersAdapter adapter;
    Button addMember, addBulkMember,deleteTeam;
    ImageView search;
    EditText etSearch;
    ProgressDialog ringProgressDialog;
    String teamId, usertobeAddedId,teamAdmin;
    ArrayList<String> ids;
    ArrayList<String> name;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
   // MenuItem searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_team);

        Intent intent = getIntent();
        teamId = intent.getStringExtra("teamMemberId");
        teamAdmin = intent.getStringExtra("teamAdmin");

        ids = new ArrayList<>();
        name = new ArrayList<>();

        getTeamMembers();
        toolbar = findViewById(R.id.header);
        toolbar.setTitle("Add Members");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        logo = getLayoutInflater().inflate(R.layout.search_bar, null);
        toolbar.addView(logo);
        logo.setVisibility(View.INVISIBLE);
        edtSeach = toolbar.findViewById(R.id.edtSearch);
       // searchBar = (MenuItem)findViewById(R.id.action_search);
        toolbar.inflateMenu(R.menu.search_menu_team_activity);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id) {
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
                Intent intent = new Intent(CreateNewTeamActivity.this, MenuActivity.class);
                finish();
                startActivity(intent);
                onBackPressed();
            }
        });
        addMember = findViewById(R.id.btnAddMember);
        addBulkMember = findViewById(R.id.btnAddBulk);
        deleteTeam = findViewById(R.id.btnDeleteTeam);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = pref.getString("user_id", "");
        if(teamAdmin.equals(userId)){
            deleteTeam.setVisibility(View.VISIBLE);
        }else {
            deleteTeam.setVisibility(View.GONE);
        }
        search = findViewById(R.id.searchBtn);
        etSearch = findViewById(R.id.etSearch);
        etSearch.setVisibility(View.INVISIBLE);
        search.setOnClickListener(this);
        edtSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nameToSearch = etSearch.getText().toString();
                ArrayList<TeamMembersPojo> filteredLeaves = new ArrayList<TeamMembersPojo>();

                for (TeamMembersPojo data : listPojo) {
                    if (data.getData().toLowerCase().contains(nameToSearch.toLowerCase())) {
                        filteredLeaves.add(data);
                    }


                }
                /*leaveDatas.clear();
                leaveDatas.addAll(filteredLeaves);
                leaves_adapter.notifyDataSetChanged();*/
                adapter = new TeamMembersAdapter(CreateNewTeamActivity.this, filteredLeaves);
                lv.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nameToSearch = etSearch.getText().toString();
                ArrayList<TeamMembersPojo> filteredLeaves = new ArrayList<TeamMembersPojo>();

                for (TeamMembersPojo data : listPojo) {
                    if (data.getData().toLowerCase().contains(nameToSearch.toLowerCase())) {
                        filteredLeaves.add(data);
                    }


                }
                /*leaveDatas.clear();
                leaveDatas.addAll(filteredLeaves);
                leaves_adapter.notifyDataSetChanged();*/
                adapter = new TeamMembersAdapter(CreateNewTeamActivity.this, filteredLeaves);
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
        deleteTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Alert!")
                        .setCancelText("Cancel")
                        .setConfirmText("OK").setContentText("Do you really want to delete this Team?")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                deleteTeam();
                                sDialog.dismiss();
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

            }
        });


        lv = findViewById(R.id.membersListView);
       /* membersPojoData = new TeamMembersPojo();
        membersPojoData.setData("rizwan");
        listPojo.add(membersPojoData);
        adapter = new TeamMembersAdapter(this, listPojo);


        lv.setAdapter(adapter);*/
    }

    private void deleteTeam() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.DELETE_TEAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        Intent intent = new Intent(CreateNewTeamActivity.this, MenuActivity.class);
                        finish();
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("teamid", teamId);

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
            toolbar.setTitle("Add Members");
            logo.setVisibility(View.INVISIBLE);
            edtSeach.setText("");

            //mSearchAction.setVisible(true);
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
            edtSeach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    doSearch();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);
           // mSearchAction.setVisible(false);

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
        }
        super.onBackPressed();
    }

    private void doSearch() {
        //
        String nameToSearch = edtSeach.getText().toString();
        ArrayList<TeamMembersPojo> filteredLeaves = new ArrayList<TeamMembersPojo>();

        for (TeamMembersPojo data : listPojo) {
            if (data.getData().toLowerCase().contains(nameToSearch.toLowerCase())) {
                filteredLeaves.add(data);
            }


        }
                /*leaveDatas.clear();
                leaveDatas.addAll(filteredLeaves);
                leaves_adapter.notifyDataSetChanged();*/
        adapter = new TeamMembersAdapter(CreateNewTeamActivity.this, filteredLeaves);
        lv.setAdapter(adapter);
    }
    private void showKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
    private void hideKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(title.getWindowToken(), 0);
    }
    private void addMember() {
        LayoutInflater inflater = LayoutInflater.from(CreateNewTeamActivity.this);
        View subView = inflater.inflate(R.layout.custom_dialog_for_add_member, null);
        member = subView.findViewById(R.id.etMember);
       TextView save = subView.findViewById(R.id.copy);
        TextView cancel = subView.findViewById(R.id.close);
        showKeyBoard(member);

        final AlertDialog alertDialog = new AlertDialog.Builder(CreateNewTeamActivity.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(subView);
        alertDialog.show();


      //  final AlertDialog alertDialog = builder.create();
        //alertDialog.setCancelable(false);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!member.getText().toString().equals("")) {
                    //addSingleMemberTeam();

                    addinguser(member.getText().toString());
                   /* membersPojoData = new TeamMembersPojo();
                    membersPojoData.setData(member.getText().toString());
                    listPojo.add(membersPojoData);
                    lv.setAdapter(adapter);*/
                  //  builder.setCancelable(true);
                    hideKeyBoard(member);
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Some Name", Toast.LENGTH_LONG).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // builder.setCancelable(true);
                hideKeyBoard(member);
                alertDialog.dismiss();
            }
        });
        member.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(member.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


 /*       builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });*/

       // builder.show();
    }

    private void searchUser(final String email) {


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SEARCH_USER_TEAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ids = new ArrayList<>();
                        name = new ArrayList<>();

                        try {
                            if(!response.equals("false")) {
                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    ids.add(jsonObject.getString("id"));
                                    name.add(jsonObject.getString("email"));
                                }
                            }

                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<String>(CreateNewTeamActivity.this, android.R.layout.simple_list_item_1, name);
                            member.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("member_single_name", email);

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


    private void addinguser(final String email) {

        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SEARCH_USER_TEAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();

                        try {
                            if(!response.equals("false")) {
                                JSONArray jsonArray = new JSONArray(response);


                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                usertobeAddedId = jsonObject.getString("id");
                                addSingleMemberTeam();
                            }else {
                                Toast.makeText(CreateNewTeamActivity.this,"Member Not Found",Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("member_single_name", email);

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


    private void addBulkMembers() {
        LayoutInflater inflater = LayoutInflater.from(CreateNewTeamActivity.this);
        View subView = inflater.inflate(R.layout.custom_dialog_for_add_bulk_members, null);
        final EditText member = subView.findViewById(R.id.etMember);


        TextView save = subView.findViewById(R.id.copy);
        TextView cancel = subView.findViewById(R.id.close);


        final AlertDialog alertDialog = new AlertDialog.Builder(CreateNewTeamActivity.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(subView);
        alertDialog.show();
        showKeyBoard(member);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String members = member.getText().toString();
                if (!member.getText().toString().equals("")) {
                    if (members.contains(",")) {
                        Toast.makeText(getApplicationContext(), "Please use ';' to add multiple members", Toast.LENGTH_LONG).show();
                    } else{
                        bulkAddMembers(members);
                    /*for (int i = 0; i < members.length; i++) {
                        membersPojoData = new TeamMembersPojo();
                        membersPojoData.setData(members[i]);
                        listPojo.add(membersPojoData);

                    }

                    lv.setAdapter(adapter);*/
                    hideKeyBoard(member);
                        alertDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Some Name", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard(member);
                alertDialog.dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.searchBtn:
                etSearch.setVisibility(View.VISIBLE);
        }
    }

    public void getTeamMembers() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_TEAM_MEMBERS_BY_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        listPojo = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String data = jsonObject.getString("Team_Associations");

                            JSONArray jsonArray = new JSONArray(data);
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                //for (int i = 0; i < jsonObject1.length(); i++) {

                                membersPojoData = new TeamMembersPojo();
                                membersPojoData.setData(jsonObject1.getString("first_name") + " " + jsonObject1.getString("last_name"));
                                membersPojoData.setImage(jsonObject1.getString("profile_pic"));
                                membersPojoData.setId(jsonObject1.getString("teams_id"));
                                membersPojoData.setInitials(jsonObject1.getString("initials"));
                                membersPojoData.setGpimageView(jsonObject1.getString("gp_picture"));
                                membersPojoData.setUserId(jsonObject1.getString("id"));
                                listPojo.add(membersPojoData);
                                //}
                            }

                            adapter = new TeamMembersAdapter(CreateNewTeamActivity.this, listPojo);
                            lv.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("team_id", teamId);

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

    private void addSingleMemberTeam() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.ADD_SINGLE_TEAM_MEMBER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        listPojo = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.has("team_new_id")){
                                if (!jsonObject.getString("team_new_id").equals("0")) {
                                 getTeamMembers();
                                 Toast.makeText(CreateNewTeamActivity.this, "Member Added Successfully", Toast.LENGTH_LONG).show();

                                }
                            } else if (jsonObject.getString("already_exist").equals("0")) {
                                Toast.makeText(CreateNewTeamActivity.this, "This user already exists in this team.", Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                params.put("teamid", teamId);
                params.put("userids", usertobeAddedId);
                params.put("userId", pref.getString("user_id", ""));
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

    private void bulkAddMembers(final String members) {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.ADD_BULK_TEAM_MEMBERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("member_added").equals("1")) {
                                getTeamMembers();
                                Toast.makeText(CreateNewTeamActivity.this, "Member Added Successfully", Toast.LENGTH_LONG).show();

                            }else if(jsonObject.getString("member_added").equals("2")){
                                Toast.makeText(CreateNewTeamActivity.this, "This user already exists in this team", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(CreateNewTeamActivity.this, "Member not found,Please enter a valid email address", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(CreateNewTeamActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                params.put("teamids", teamId);
                params.put("members", members);
                params.put("userId", pref.getString("user_id", ""));
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
}
