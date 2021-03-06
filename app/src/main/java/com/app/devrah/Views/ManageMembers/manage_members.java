package com.app.devrah.Views.ManageMembers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
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
import com.app.devrah.Adapters.MemberAdapter;
import com.app.devrah.Adapters.team_addapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.All_Teams;
import com.app.devrah.pojo.MembersPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class manage_members extends AppCompatActivity {


    AutoCompleteTextView search =null;
    GridView currentMember, TeamMember;
    Spinner Team_list;
    Button Close;
    String p_id;

    ArrayList<String> teamList;
    ArrayList<String> teamListids;

    ArrayList<MembersPojo> listPojo;
    MembersPojo membersPojoData;
    ArrayAdapter<String> projectADdapter;
    team_addapter team_addapter;
    MemberAdapter member_adapter;
    ArrayList<MembersPojo> membersPojos;
    ProgressDialog ringProgressDialog;
    List<All_Teams> teamLists;
    String teamid,usertoadd;
    Button btnClose,btnSave,addMemberBtn;
    TextView heading;


    ArrayList<String> ids;
    ArrayList<String> name;
    ArrayList<String> devTag;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magnage_members_popup);


        Intent intent = getIntent();

        p_id = intent.getStringExtra("P_id");

        search = (AutoCompleteTextView) findViewById(R.id.search);

        currentMember = (GridView) findViewById(R.id.grid_view);
       // TeamMember = (GridView) findViewById(R.id.grid_view_team);


        heading = (TextView) findViewById(R.id.heading);
        heading.setText("Manage Project Member");

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if  ((i == EditorInfo.IME_ACTION_SEARCH)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                }
                return true;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(search.getText().toString());
               /* String test=to.getText().toString();
                if(test.contains(";")){

                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                usertoadd=ids.get(i);
                addmember(-1);
                search.setText("");
               // Toast.makeText(manage_members.this,ids.get(i).toString()+""+name.get(i).toString(),Toast.LENGTH_LONG).show();
            }
        });

        currentMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(membersPojos.size()==1){
                    Toast.makeText(getApplicationContext(),"You have to keep atleast one user in a project!",Toast.LENGTH_LONG).show();
                }else {
                    new SweetAlertDialog(manage_members.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Remove This member")
                            .setConfirmText("OK").setContentText("Are You sure you want to remove this member from the project")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    //teamid = teamListids .get(i);
                                    usertoadd = membersPojos.get(i).getUserId();
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                    String userId = pref.getString("user_id", "");

                                        deletemember();

                                }
                            }).setCancelText("Cancel")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .show();
                }

            }
        });

        /*TeamMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos=Team_list.getSelectedItemPosition();
                teamid = teamListids .get(pos);
                usertoadd = listPojo.get(i).getUserId();


                addmember();

            }
        });*/

        getmembers();
       // getMyTeams();


        btnClose= (Button) findViewById(R.id.close);
        btnSave= (Button) findViewById(R.id.save);
        addMemberBtn= (Button) findViewById(R.id.buttonAddMember);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Members managed Successfully !",Toast.LENGTH_LONG).show();
                finish();

            }
        });
        addMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTeamMembersDialog();
            }
        });
    }

    private void showTeamMembersDialog() {
        LayoutInflater inflater = LayoutInflater.from(manage_members.this);
        View view = inflater.inflate(R.layout.dilog_for_team_members, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(manage_members.this).create();
        alertDialog.setCancelable(false);
        TeamMember = (GridView) view.findViewById(R.id.grid_view_team);
        Team_list = (Spinner) view.findViewById(R.id.search_team);
        TextView tvDone = (TextView) view.findViewById(R.id.addMember);
        TextView tvCanvel = (TextView) view.findViewById(R.id.cancel);
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


        tvCanvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        TeamMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos=Team_list.getSelectedItemPosition();
                teamid = teamListids .get(pos);
                usertoadd = listPojo.get(i).getUserId();


                addmember(i);

            }
        });

        alertDialog.setView(view);
        alertDialog.show();
        getMyTeams();
    }

    private void searchUser(final String email) {


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SEARCH_USER_TO_ADD_MEMBER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ids = new ArrayList<>();
                        name = new ArrayList<>();
                        devTag = new ArrayList<>();
                        if(!response.equals("false")) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    ids.add(jsonObject.getString("id"));
                                    name.add(jsonObject.getString("email"));
                                    devTag.add(jsonObject.getString("dev_tag"));
                                }

                                adapter = new ArrayAdapter<String>(manage_members.this, android.R.layout.simple_dropdown_item_1line, name);
                                search.setThreshold(1);
                                search.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NoConnectionError) {

                  /*  new SweetAlertDialog(SendNewMessageActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("check your internet connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();*/
                } else if (error instanceof TimeoutError) {

                   /* new SweetAlertDialog(SendNewMessageActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();*/
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
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
    public void getmembers() {


        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_MEMBERS_PROJECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        membersPojos = new ArrayList<>();

                        try {
                            JSONArray jsonArray = new JSONArray(response);


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                MembersPojo membersPojo = new MembersPojo();


                                membersPojo.setName(jsonObject.getString("first_name"));
                                membersPojo.setProfile_pic(jsonObject.getString("profile_pic"));
                                membersPojo.setUserId(jsonObject.getString("id"));
                                membersPojo.setInetial(jsonObject.getString("initials"));
                                membersPojo.setGp_pic(jsonObject.getString("gp_picture"));


                                membersPojos.add(membersPojo);

                                member_adapter = new MemberAdapter(manage_members.this, membersPojos);

                                currentMember.setAdapter(member_adapter);

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

                    new SweetAlertDialog(manage_members.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(manage_members.this, SweetAlertDialog.ERROR_TYPE)
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

                params.put("project_id", p_id);

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(manage_members.this);
        requestQueue.add(request);


    }

    public void getMyTeams() {


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_MEMBER_TEAMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        teamList = new ArrayList<>();
                        teamListids = new ArrayList<>();

                        teamList.add(0, " Select Team");
                        teamListids.add(0, "0");

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                teamListids.add(jsonObject.getString("id"));
                                teamList.add(jsonObject.getString("team_name"));

                            }

                            projectADdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.nothing_selected_spinnerdate, teamList);
                            projectADdapter.setDropDownViewResource(R.layout.nothing_selected_spinnerdate);
                            Team_list.setAdapter(projectADdapter);


                            Team_list.setOnItemSelectedListener(new CustomOnItemSelectedListener_teams());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {
                    Toast.makeText(manage_members.this,"check your internet connection",Toast.LENGTH_LONG).show();
                   /* new SweetAlertDialog(manage_members.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();*/
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(manage_members.this,"Connection TimeOut! Please check your internet connection.",Toast.LENGTH_LONG).show();
                   /* new SweetAlertDialog(manage_members.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();*/
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

                params.put("userId", pref.getString("user_id", ""));

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

    public void getTeamMembers(final String teamId) {


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_TEAM_MEMBERS_BY_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        listPojo = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String data = jsonObject.getString("Team_Associations");

                            JSONArray jsonArray = new JSONArray(data);
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                //for (int i = 0; i < jsonObject1.length(); i++) {

                                membersPojoData = new MembersPojo();
                                membersPojoData.setName(jsonObject1.getString("first_name"));
                                membersPojoData.setProfile_pic(jsonObject1.getString("profile_pic"));
                                membersPojoData.setUserId(jsonObject1.getString("id"));
                                membersPojoData.setInetial(jsonObject1.getString("initials"));
                                membersPojoData.setGp_pic(jsonObject1.getString("gp_picture"));
                                String isAssigned="0";
                                for(int i=0;i<membersPojos.size();i++){
                                    if(jsonObject1.getString("id").equals(membersPojos.get(i).getUserId()))
                                        isAssigned="1";
                                }
                                membersPojoData.setIsCardMember(isAssigned);
                                listPojo.add(membersPojoData);
                                //}
                            }

                            team_addapter = new team_addapter(manage_members.this, listPojo);
                            TeamMember.setAdapter(team_addapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(manage_members.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(manage_members.this, SweetAlertDialog.ERROR_TYPE)
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
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    public void addmember(final int pos) {


        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.ASSOSIATE_USET_TO_PROJECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if(!response.equals("0")) {
                            if(pos!=-1) {
                                listPojo.get(pos).setIsCardMember("1");
                                team_addapter.notifyDataSetChanged();
                            }
                            Toast.makeText(manage_members.this,"Member Added Successfully",Toast.LENGTH_LONG).show();
                            getmembers();
                        }else {
                            Toast.makeText(manage_members.this,"Member Not Added",Toast.LENGTH_LONG).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(manage_members.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(manage_members.this, SweetAlertDialog.ERROR_TYPE)
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

                params.put("usr_id", usertoadd);
                params.put("prjct_id",p_id);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

                params.put("userId", pref.getString("user_id", ""));

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(manage_members.this);
        requestQueue.add(request);


    }

    public void deletemember() {


        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.DELETE_PROJECT_MEMBER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if(response.equals("0")){
                            Toast.makeText(manage_members.this,"Admin user cannot be deleted!",Toast.LENGTH_LONG).show();
                        }else {
                            getmembers();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(manage_members.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(manage_members.this, SweetAlertDialog.ERROR_TYPE)
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

                params.put("member_id", usertoadd);
                params.put("project_id",p_id);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

                params.put("userId", pref.getString("user_id", ""));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(manage_members.this);
        requestQueue.add(request);


    }


    public class CustomOnItemSelectedListener_teams implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, final int pos,
                                   long id) {

            if (pos == 0) {
            //    getTeamMembers("0");
            } else {
                getTeamMembers(teamListids.get(pos));
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }


}
