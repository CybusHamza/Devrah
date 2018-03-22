package com.app.devrah.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.app.devrah.Network.End_Points;
import com.app.devrah.Views.Board.BoardsActivity;
import com.app.devrah.Views.Teams.CreateNewTeamActivity;
import com.app.devrah.R;
import com.app.devrah.pojo.All_Teams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by AQSA SHaaPARR on 7/20/2017.
 */

public class TeamAdapterMenu extends BaseAdapter {

    List<All_Teams> projectsList;
    Activity activity;
    private LayoutInflater inflater;

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    public TeamAdapterMenu(Activity activity, List<All_Teams> projectsList) {
        this.activity = activity;
        this.projectsList = projectsList;

        //  super(activity,R.layout.custom_layout_for_projects,projectsList);
    }


    @Override
    public int getCount() {
        return projectsList.size();
    }

    @Override
    public Object getItem(int position) {
        return   projectsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        if (inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.row_list_menu_team, null);

        holder.data = convertView.findViewById(R.id.tvProjectsData);
        holder.description = convertView.findViewById(R.id.tvDescription);
        holder.editTeamName = convertView.findViewById(R.id.editIcon);
        holder.data.setText(projectsList.get(position).getName());
        String check=projectsList.get(position).getDescription();
        if(!check.equals("") && !check.equals("null")){
            holder.description.setText(projectsList.get(position).getDescription());
        }else {
            holder.description.setText("");
        }
        if(projectsList.get(position).getName().equals("No Team found")){
            holder.editTeamName.setVisibility(View.INVISIBLE);
        }else {
            holder.editTeamName.setVisibility(View.VISIBLE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show();
                if(!projectsList.get(position).getId().equals("") && !projectsList.get(position).getName().equals("No Team found")) {

                    Intent intent = new Intent(activity, CreateNewTeamActivity.class);
                    intent.putExtra("teamMemberId", projectsList.get(position).getId());
                    intent.putExtra("teamAdmin", projectsList.get(position).getTeamAdmin());
                    activity.startActivity(intent);
                }

            }
        });
        holder.editTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProjectNameDialog(projectsList.get(position).getName(),projectsList.get(position).getId(),position);
            }
        });

        return convertView;
    }

    private void updateProjectNameDialog(String teamName, final String teamId, final int position) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View customView = inflater.inflate(R.layout.update_card_name_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(customView);
        alertDialog.show();

        TextView cancel, copy;
        final EditText etCardName= customView.findViewById(R.id.etCardName);
        final TextView tvheading= customView.findViewById(R.id.heading);
        tvheading.setText("Update Team Name");
        etCardName.setText(teamName);
        etCardName.setSelection(etCardName.getText().length());
        showKeyBoard(etCardName);

        copy = customView.findViewById(R.id.copy);
        cancel = customView.findViewById(R.id.close);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check=etCardName.getText().toString();
                if(!check.equals("") && check!="" && check.trim().length()>0) {
                    UpdateTeamName(etCardName.getText().toString(),teamId,position);
                    hideKeyBoard(etCardName);
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(activity,"Team Name is must!",Toast.LENGTH_LONG).show();
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

    private void UpdateTeamName(final String teamName, final String teamId, final int position) {
        final SharedPreferences pref =activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);

        final ProgressDialog ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_TEAM_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        switch (response) {
                            case "1":
                                projectsList.get(position).setName(teamName);
                                notifyDataSetChanged();
                                Toast.makeText(activity, "Team Name Updated Successfully", Toast.LENGTH_SHORT).show();
                                break;
                            case "-1":
                                Toast.makeText(activity, "Team Name already exists", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(activity, "Error while updating Team Name", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                params.put("updt_txt", teamName);
                params.put("team_id", teamId);
                params.put("userId", pref.getString("user_id", ""));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }

    private void showKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
    private void hideKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(title.getWindowToken(), 0);
    }
    public static class ViewHolder{
        TextView data,description;
        ImageView editTeamName;
    }




}
