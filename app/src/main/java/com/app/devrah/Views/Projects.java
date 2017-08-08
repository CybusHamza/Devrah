package com.app.devrah.Views;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.app.devrah.Adapters.ProjectsAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.ProjectsPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;


public class Projects extends Fragment implements View.OnClickListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public EditText etDescription;
    Button btnAddProject;
    View view;
    ProjectsAdapter adapter;
    String description;
    ImageView showSpinner;
    String groupId;
    EditText etProjectGroup;
    ArrayAdapter<String> timeAdapter;
    LinearLayout laEt, laSpinner;
    // String[] spinnerValues;
    List<String> spinnerValues;
    List<String> spinnerGroupIds;
    String groupData;
    List<ProjectsPojo> listPojo;
    ProjectsPojo projectPojoData;
    ListView lv;
    EditText title, spinnerData;
    String projectData;
    ProgressDialog ringProgressDialog;
    String GroupName;
    private Spinner spinnerProjectGroup;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Projects() {
        // Required empty public constructor
    }

    public static Projects newInstance(String param1, String param2) {
        Projects fragment = new Projects();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_projects, container, false);
        btnAddProject = (Button) view.findViewById(R.id.buttonAddProject);
        listPojo = new ArrayList<>();
        lv = (ListView) view.findViewById(R.id.projectsListView);

        //   spinnerValues = new String[]{};


        //getSpinnerData();

        getProjectsData();
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(),BoardsActivity.class);
//                startActivity(intent);
//
//            }
//        });


        btnAddProject.setOnClickListener(this);

//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_projects, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonAddProject:


                showCustomDialog();
                // showDialog();
                //    Toast.makeText(getContext(),"Button Pressed",Toast.LENGTH_LONG).show();
                break;

        }

    }

    public void showCustomDialog() {


        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.custom_alert_for_projects, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        title = (EditText) customView.findViewById(R.id.input_title);
        etDescription = (EditText) customView.findViewById(R.id.etEmail);

        laEt = (LinearLayout) customView.findViewById(R.id.laEt);
        laSpinner = (LinearLayout) customView.findViewById(R.id.laSpinner);
        etProjectGroup = (EditText) customView.findViewById(R.id.etCustomSpinnerData);


        ImageView showET = (ImageView) customView.findViewById(R.id.showET);
        showSpinner = (ImageView) customView.findViewById(R.id.imageShowSpinner);

        spinnerProjectGroup = (Spinner) customView.findViewById(R.id.spinProjectGroup);
        //   spinnerData = (EditText)customView.findViewById(R.id.etCustomSpinnerData);

        getSpinnerData();

        // getProjectsData();

        showET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                laEt.setVisibility(View.VISIBLE);
                laSpinner.setVisibility(View.GONE);
//                spinnerProjectGroup.setVisibility(View.GONE);
//                spinnerData.setVisibility(View.VISIBLE);
            }
        });

        showSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                laSpinner.setVisibility(View.VISIBLE);
                laEt.setVisibility(View.GONE);

            }
        });


        TextView addCard = (TextView) customView.findViewById(R.id.addProject);
        addCard.setText("Add Project");
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                projectData = title.getText().toString();
                final String projectDescription = etDescription.getText().toString();

                if (laEt.getVisibility() == View.VISIBLE) {
                    description = etProjectGroup.getText().toString();
                    ProjectGroupEt();
                    // description = etDescription.getText().toString();

                }
                if (laSpinner.getVisibility() == View.VISIBLE) {
                    GroupName = timeAdapter.getItem(spinnerProjectGroup.getSelectedItemPosition());
                    int index = spinnerValues.indexOf(GroupName);
                    groupId = spinnerGroupIds.get(index);
                    //description = spinnerProjectGroup.
                    Toast.makeText(getActivity().getApplicationContext(), GroupName, Toast.LENGTH_SHORT).show();


                    if (!(projectData.isEmpty())) {
                        projectPojoData = new ProjectsPojo();
                        projectPojoData.setData(projectData);
                        projectPojoData.setDescription(description);
                        listPojo.add(projectPojoData);
                        adapter = new ProjectsAdapter(getActivity(), listPojo);
                        lv.setAdapter(adapter);

                        StringRequest request = new StringRequest(Request.Method.POST, End_Points.ADD_NEW_PROJECT, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                //   Toast.makeText(getActivity().getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();

                            }
                        }


                        )

                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> params = new HashMap<>();
                                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                String userId = pref.getString("user_id", "");

//                                params.put("id",userId);
//                                params.put("project_description",projectDescription);
//                                params.put("project_name",projectData);
//                                params.put("project_group",description);

                                //  String userId = pref.getString("user_id","");

                                params.put("id", userId);
                                params.put("project_description", projectDescription);
                                params.put("project_name", projectData);
                                params.put("project_group", groupId);
                                //   params.put("")

                                //    params.put("pg_name",GroupName);


                                // params.put()
                                // params.put("password",strPassword );
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                        requestQueue.add(request);


                    } else {
                        Toast.makeText(getActivity(), "No Text Entered", Toast.LENGTH_SHORT).show();
                    }


                }


                //  final String projectDescription = etDescription.getText().toString();
                // = etDescription.getText().toString();

                alertDialog.dismiss();


            }
        });


        alertDialog.setView(customView);
        alertDialog.show();


    }

//    public void  showDialog(){
//
//    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//        View customView = layoutInflater.inflate(R.layout.custom_alert_dialogbox,null);
//        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
//        title = (EditText)customView.findViewById(R.id.input_watever);
//
//        TextView addCard = (TextView)customView.findViewById(R.id.btn_add_board);
//        addCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                projectData = title.getText().toString();
//
//                if (!(projectData.isEmpty())) {
//                    projectPojoData = new ProjectsPojo();
//                    projectPojoData.setData(projectData);
//                    listPojo.add(projectPojoData);
//                    adapter = new ProjectsAdapter(getActivity(), listPojo);
//
//
//                    lv.setAdapter(adapter);
//                }
//                else {
//                    Toast.makeText(getActivity(),"No Text Entered",Toast.LENGTH_SHORT).show();
//                }
//
//                alertDialog.dismiss();
//
//
//
//
//            }
//        });
//
//
//
//        alertDialog.setView(customView);
//alertDialog.show();
//
//
//
//    }
//

    ///////////////////////MAIN METHOD TO SHOW DATA///////////////////////////////////////////////
    public void getProjectsData() {

        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_MEMBER_PROJECTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                          ringProgressDialog.dismiss();
                        if (response.equals("false")) {
                            new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error!")
                                    .setConfirmText("OK").setContentText("Incorrect Username or Password")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismiss();

                                        }
                                    })
                                    .show();
                        } else {

                            String userid;

                            try {

                                String firstname, email, lastName, profilePic;

                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject object = new JSONObject(array.getString(i));


                                    projectPojoData = new ProjectsPojo();
                                    projectPojoData.setData(object.getString("project_name"));
                                    projectPojoData.setId(object.getString("project_id"));
                                    projectPojoData.setProjectStatus(object.getString("project_status"));
                                    listPojo.add(projectPojoData);


                                }


                                adapter = new ProjectsAdapter(getActivity(), listPojo);


                                lv.setAdapter(adapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

              ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    Toast.makeText(getContext(), "No internet", Toast.LENGTH_SHORT).show();

//                    new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText("Error!")
//                            .setConfirmText("OK").setContentText("No Internet Connection")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    sDialog.dismiss();
//                                }
//                            })
//                            .show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(getActivity().getApplicationContext(), "Timeout Error", Toast.LENGTH_SHORT).show();

//                    new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText("Error!")
//                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    sDialog.dismiss();
//                                }
//                            })
//                            .show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userId = pref.getString("user_id", "");

                params.put("id", userId);
                // params.put("password",strPassword );
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);


    }

    public void getSpinnerData() {


        spinnerValues = new ArrayList<>();
        spinnerGroupIds = new ArrayList<>();

        ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Checking Credentials ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_SPINNER_GROUP_PROJECTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //    Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                        ringProgressDialog.dismiss();
                        if (response.equals("false")) {
                            new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error!")
                                    .setConfirmText("OK").setContentText("Incorrect Username or Password")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismiss();

                                        }
                                    })
                                    .show();
                        } else {

                            String userid;

                            try {

                                String firstname, email, lastName, profilePic;

                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject object = new JSONObject(array.getString(i));
                                    spinnerValues.add(String.valueOf(object.get("pg_name")));
                                    spinnerGroupIds.add(String.valueOf(object.get("pg_id")));
                                   // Toast.makeText(getActivity().getApplicationContext(), spinnerValues.get(i), Toast.LENGTH_SHORT).show();
                                    //spinnerValues[i] = String.valueOf(object.get("pg_name"));

//                                    projectPojoData = new ProjectsPojo();
//                                    projectPojoData.setData(object.getString("project_name"));
//                                    projectPojoData.setId(object.getString("project_id"));
//                                    listPojo.add(projectPojoData);
                                }
                                timeAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.nothing_selected_spinnerdate, spinnerValues);
                                timeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                spinnerProjectGroup.setAdapter(timeAdapter);

                                spinnerProjectGroup.setSelection(0);


//                                spinnerProjectGroup.set

//                                adapter = new ProjectsAdapter(getActivity(), listPojo);
//
//
//                                lv.setAdapter(adapter);
//
//


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();


                } else if (error instanceof TimeoutError) {

                    Toast.makeText(getActivity(),"Connection TimeOut! Please check your internet connection."
                            , Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userId = pref.getString("user_id", "");

                params.put("user_id", userId);
                // params.put("password",strPassword );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);

    }

    public void ProjectGroupEt() {

        description = etProjectGroup.getText().toString();


        final String projectDescription = etDescription.getText().toString();
        // = etDescription.getText().toString();

        if (!(projectData.isEmpty())) {
            projectPojoData = new ProjectsPojo();
            projectPojoData.setData(projectData);
            projectPojoData.setDescription(description);
            listPojo.add(projectPojoData);
            adapter = new ProjectsAdapter(getActivity(), listPojo);
            lv.setAdapter(adapter);

            StringRequest request = new StringRequest(Request.Method.POST, End_Points.ADD_NEW_PROJECT_ET, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(getActivity().getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();

                }
            }


            )

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    String userId = pref.getString("user_id", "");

                    params.put("id", userId);
                    params.put("project_description", projectDescription);
                    params.put("project_name", projectData);
                    //    params.put("project_group",description);

                    params.put("pg_name", description);
                    // params.put()
                    // params.put("password",strPassword );
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requestQueue.add(request);


        } else {
            Toast.makeText(getActivity(), "No Text Entered", Toast.LENGTH_SHORT).show();
        }
//
//        alertDialog.dismiss();


    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


//
//        alertDialog.setView(customView);
//        alertDialog.show();


}







