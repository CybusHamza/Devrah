package com.app.devrah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import com.app.devrah.Adapters.TeamAdapterMenu;
import com.app.devrah.Network.End_Points;
import com.app.devrah.pojo.All_Teams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MenuActivity extends AppCompatActivity {


    EditText etTeamName;
    Toolbar toolbar;
    TextView createNewTeam;
    List<All_Teams> teamList;
    ListView lvTeamData;
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    ProgressDialog ringProgressDialog;
    TeamAdapterMenu adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        lvTeamData = (ListView) findViewById(R.id.lvTeams);

        lvTeamData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CreateNewTeamActivity.class);
                startActivity(intent);
                finish();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Menu");
        toolbar.setNavigationIcon(R.drawable.back_arrow_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//////////////////////////CODE TO ADD TEAMS/////////////////////////////////////////
//        ProjectsPojo pojo = new ProjectsPojo();
//        pojo.setData(etTeamName.getText().toString());
//        teamList.add(pojo);
//        adapter = new TeamAdapterMenu(MenuActivity.this, teamList);
//        lvTeamData.setAdapter(adapter);

//////////////////////////////////////////////////////////////////////////////////////

        createNewTeam = (TextView) findViewById(R.id.tvCreateNewTeam);

        createNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMembersDialog();

            }
        });

        getMyTeams();

    }

    public void showMembersDialog() {
        LayoutInflater inflater = LayoutInflater.from(MenuActivity.this);
        View view = inflater.inflate(R.layout.alert_dialog_for_menu_create_members, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
        etTeamName = (EditText) view.findViewById(R.id.input_teamName);
        final EditText etTeamDescription = (EditText) view.findViewById(R.id.input_team_description);
        TextView tvDone = (TextView) view.findViewById(R.id.addMember);
        TextView tvCanvel = (TextView) view.findViewById(R.id.cancel);




        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                creatNewTeam(etTeamName.getText().toString(),etTeamDescription.getText().toString());


                alertDialog.dismiss();

            }
        });

        tvCanvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(view);
        alertDialog.show();


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(MenuActivity.this, Dashboard.class);
        startActivity(intent);

    }

    public void getMyTeams() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_TEAMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            ringProgressDialog.dismiss();
                            teamList = new ArrayList<>();

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i = 0 ;i <jsonArray.length();i++)
                            {
                                JSONObject  jsonObject = jsonArray.getJSONObject(i);

                                All_Teams all_teams = new All_Teams();
                                all_teams.setId(jsonObject.getString("id"));
                                all_teams.setName(jsonObject.getString("team_name"));

                                teamList.add(all_teams);
                            }

                            TeamAdapterMenu teamAdapterMenu = new TeamAdapterMenu(MenuActivity.this,teamList);
                            lvTeamData.setAdapter(teamAdapterMenu);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(MenuActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(MenuActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

                params.put("userId",pref.getString("user_id",""));

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


    public void creatNewTeam(final String team_name, final String team_descr) {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.ADD_NEW_TRAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        getMyTeams();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(MenuActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(MenuActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

                params.put("user_id",pref.getString("user_id",""));
                params.put("team_name",team_name);
                params.put("team_descr",team_descr);

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
