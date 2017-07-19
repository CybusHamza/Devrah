package com.app.devrah;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.devrah.Adapters.CustomExpandableListAdapter;
import com.app.devrah.Adapters.GroupProjectAdapter;
import com.app.devrah.Adapters.ProjectsAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.pojo.GroupProjectsPojo;
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

public class GroupProjects extends Fragment implements View.OnClickListener {


    EditText edt;
    List<String> listDataHeader;

    HashMap<String, List<String>> listDataChild;
    View view;
    Button addGrouProjects;
    ExpandableListView expandableLv;
    ListView AddGroupProjectListView;
    List<GroupProjectsPojo> pojoList;
     List<String> MyList;
    JSONArray projectsArray;
    String projectNameS, projectHeading;

    ArrayList<String> projectName;
    String groupProjectData;
    GroupProjectsPojo groupProjectsPojo;
    GroupProjectAdapter adapter;


    /////////////////////////////////////////
   public ArrayList<String> whatever, whateverId, projectId, projectNames, dummy;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CustomExpandableListAdapter customAdapter;

    private OnFragmentInteractionListener mListener;

    public GroupProjects() {
        // Required empty public constructor
    }

    public static GroupProjects newInstance(String param1, String param2) {
        GroupProjects fragment = new GroupProjects();
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
        view = inflater.inflate(R.layout.fragment_group_projects, container, false);
        addGrouProjects = (Button) view.findViewById(R.id.buttonAddGroupProject);
        AddGroupProjectListView = (ListView) view.findViewById(R.id.groupProjectsListView);
        pojoList = new ArrayList<>();

        prepareDataList();

        expandableLv = (ExpandableListView) view.findViewById(R.id.lvExp);
        addGrouProjects.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;


    }

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
            case R.id.buttonAddGroupProject:
                //  Toast.makeText(getContext(),"Button Pressed", Toast.LENGTH_LONG).show();
                showDialog();
                break;

        }


    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    public void showDialog() {


//        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());
//        LayoutInflater inflater = this.getActivity().
//                getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.custom_alert_dialogbox, null);
//        dialogBuilder.setView(dialogView);


        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.custom_alert_dialogbox, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        edt = (EditText) customView.findViewById(R.id.input_watever);

        TextView addCard = (TextView) customView.findViewById(R.id.btn_add_board);

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupProjectData = edt.getText().toString();

                if (!(groupProjectData.isEmpty())) {

                    groupProjectsPojo = new GroupProjectsPojo();
                    groupProjectsPojo.setGroupProjectData(groupProjectData);
                    pojoList.add(groupProjectsPojo);
                    adapter = new GroupProjectAdapter(getActivity(), pojoList);
                    AddGroupProjectListView.setAdapter(adapter);


                } else
                    Toast.makeText(getActivity(), "No Text Entered", Toast.LENGTH_SHORT);


                alertDialog.dismiss();

            }
        });


        alertDialog.setView(customView);
        alertDialog.show();


    }

    public void prepareDataList() {


        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        projectNames = new ArrayList<>();
         dummy = new ArrayList<String>();


        whatever = new ArrayList<>();
        whateverId = new ArrayList<>();
        projectId = new ArrayList<>();
        projectName = new ArrayList<>();

        ////////////////////////// STRING REQUEST ////////////////////////////GET_GROUP_PROJECTS


        StringRequest request = new StringRequest(Request.Method.POST, "http://m1.cybussolutions.com/kanban/Api_service/fahim",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  ringProgressDialog.dismiss();
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
                                //   projectNames = new ArrayList<>();

                                String firstname, email, lastName, profilePic;

                                //  JSONArray array = new JSONArray(response);
                                JSONObject object = new JSONObject(response);
                                JSONArray array = object.getJSONArray("group_name");

                                JSONArray projectsArray = object.getJSONArray("group_projects");


                                for (int i = 0; i < projectsArray.length(); i++) {

                                    JSONObject obj = new JSONObject(projectsArray.getString(i));

                                    projectName.add(obj.getString("project_name"));
                                    projectId.add(obj.getString("project_group"));
                                }


                                for (int i = 0; i < array.length(); i++) {

                                    MyList = new ArrayList<>();
                                    JSONObject obj = new JSONObject(array.getString(i));

                                    // obj.get("pg_name");

                                    whateverId.add(obj.getString("pg_id"));
                                    whatever.add(obj.getString("pg_name"));
                                    MyList = gth();
                                    listDataChild.put(whatever.get(i), MyList);
                                    customAdapter = new CustomExpandableListAdapter(getActivity().getApplicationContext(),MyList,listDataChild);
////
//
                                    expandableLv.setAdapter(customAdapter);


                                }




//                            for (int p = 0; p< whatever.size(); p++)
//
//                            {
//
//                                for (int i = 0; i<projectsArray.length();i++)  {
//
//                                    if (projectId.get(i).equals(whateverId.get(p))) {
//
//                                        dummy.add(projectName.get(i));
//                                    }
//
//                                    listDataChild.put(whatever.get(p),dummy);
//                                    customAdapter = new CustomExpandableListAdapter(getActivity().getApplicationContext(),whatever,listDataChild);
////
//
//                                    expandableLv.setAdapter(customAdapter);
//                                   // dummy.clear();
//                                }
//
//
//                              //  dummy.clear();
//
//                            }
//


                                //  listDataChild.put(listDataHeader.get(listDataHeader.indexOf(p)), projectNames);


                                //   int len = object.length();


                                //    for (int i = 0;i<array.length();i++){


                                //  JSONObject object = new JSONObject(array.getString(i));

                                //   if (!(listDataHeader.contains(object.getString("pg_name")))){
//                                String p = object.getString("pg_name");
//
//                                if (listDataHeader.contains(p)){
//                                    projectNames.add(object.getString("project_name"));
//                                    listDataChild.put(listDataHeader.get(listDataHeader.indexOf(p)),projectNames);
//                                  //  projectNames = new ArrayList<>();
//                                }
//                                else {
//
//                                    listDataHeader.add(object.getString("pg_name"));
//                                    projectNames.add(object.getString("project_name"));
//                                    listDataChild.put(listDataHeader.get(listDataHeader.indexOf(p)), projectNames);
//
//                                    listDataChild.containsKey()
//                                }


                                // }


//                                projectPojoData = new ProjectsPojo();
//                                projectPojoData.setData(object.getString("project_name"));
//                                projectPojoData.setId(object.getString("project_id"));
//                                listPojo.add(projectPojoData);


                                //    }


//                            adapter = new ProjectsAdapter(getActivity(), listPojo);
//
//
//                            lv.setAdapter(adapter);
//
//
//
//

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            customAdapter = new CustomExpandableListAdapter(getActivity().getApplicationContext(), whatever, listDataChild);
                            expandableLv.setAdapter(customAdapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(getActivity().getApplicationContext(), "No iNternet", Toast.LENGTH_SHORT).show();
//                    new SweetAlertDialog(getActivity().getC, SweetAlertDialog.ERROR_TYPE)
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


                    Toast.makeText(getActivity().getApplicationContext(),"TimeOut eRROR",Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);


    }

    public List<String> gth() {
        List<String>   projectName = new ArrayList<>();


        for (int p = 0; p < whatever.size(); p++)

        {

//            for (int i = 0; i < projectsArray.size(); i++) {

            for (int i = 0; i < projectName.size(); i++) {

                if (projectId.get(i).equals(whateverId.get(p))) {

                  //  dummy.add(projectName.get(i));
                    //dummy.add(whatever.get(p));
                    projectName.add(whatever.get(p));
                }
//
//                listDataChild.put(whatever.get(p), dummy);
//                customAdapter = new CustomExpandableListAdapter(getActivity().getApplicationContext(), whatever, listDataChild);
////

            //    expandableLv.setAdapter(customAdapter);
                // dummy.clear();
            }

        }
            return projectName;
        }
    }





