package com.app.devrah.Views.Project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
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
import com.app.devrah.Adapters.CustomExpandableListAdapter;
import com.app.devrah.Adapters.GroupProjectAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.readyInterface;
import com.app.devrah.pojo.GroupProjectsPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;
import static com.app.devrah.Network.End_Points.GET_GROUP_PROJECTS;

public class GroupProjects extends Fragment implements View.OnClickListener,readyInterface {


    EditText edt;
    List<String> listDataHeader;

    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listDataStatus;
    View view;
    Button addGrouProjects;
    ExpandableListView expandableLv;
    ListView AddGroupProjectListView;
    List<GroupProjectsPojo> pojoList;
     List<String> MyList;
     List<String> MYIDS;
    JSONArray projectsArray;
    String projectNameS, projectHeading;

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    ArrayList<String> projectName;
    ArrayList<String> projectStatus;
    ArrayList<String> projectids;
    String groupProjectData;
    GroupProjectsPojo groupProjectsPojo;
    GroupProjectAdapter adapter;
    ProgressDialog ringProgressDialog;
    String GroupName;
    private Spinner spinnerProjectGroup;
    AlertDialog alertDialog;
    EditText title, spinnerData;
    String projectData;
    LinearLayout laEt, laSpinner;
    public EditText etDescription;
    String groupId;
    EditText etProjectGroup;
    ArrayAdapter<String> timeAdapter;
    ImageView showSpinner;
    List<String> spinnerValues;
    List<String> spinnerGroupIds;
    String description;
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
    SwipeRefreshLayout mySwipeRefreshLayout;

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
       addGrouProjects = (Button) view.findViewById(R.id.buttonAddProject);
      //  AddGroupProjectListView = (ListView) view.findViewById(R.id.groupProjectsListView);
        pojoList = new ArrayList<>();

        prepareDataList();

        expandableLv = (ExpandableListView) view.findViewById(R.id.lvExp);
        mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        addGrouProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });
        //   spinnerValues = new String[]{};
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        prepareDataList();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
//        addGrouProjects.setOnClickListener(this);
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

       /* switch (v.getId()) {
            case R.id.buttonAddGroupProject:
                //  Toast.makeText(getContext(),"Button Pressed", Toast.LENGTH_LONG).show();
                showDialog();
                break;

        }


*/
    }

    @Override
    public void ready() {

        prepareDataList();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    public void showDialog() {



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
    public void showCustomDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.custom_alert_for_projects, null);
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setCancelable(false);
        title = (EditText) customView.findViewById(R.id.input_title);
        etDescription = (EditText) customView.findViewById(R.id.etEmail);
        showKeyBoard(title);
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
        TextView addCardAndMore = (TextView) customView.findViewById(R.id.addProject1);
        TextView cancel = (TextView) customView.findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(title);
                alertDialog.dismiss();
            }
        });
        addCard.setText("Save and Close");
        addCardAndMore.setText("Save and Add");
        addCardAndMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                projectData = title.getText().toString();
                final String projectDescription = etDescription.getText().toString();

                if (laEt.getVisibility() == View.VISIBLE) {
                    description = etProjectGroup.getText().toString();
                    if (etProjectGroup.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Please Enter Group Name", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        if (projectDescription.length() <= 255) {
                            ProjectGroupEt();
                            etProjectGroup.setText("");
                            title.setText("");
                            etDescription.setText("");
                        } else {
                            Toast.makeText(getActivity(), "Description maximum limit is 255", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    // description = etDescription.getText().toString();

                }
                if (laSpinner.getVisibility() == View.VISIBLE) {
                    int pos = spinnerProjectGroup.getSelectedItemPosition();
                    if (pos != -1) {
                        GroupName = timeAdapter.getItem(spinnerProjectGroup.getSelectedItemPosition());
                        int index = spinnerValues.indexOf(GroupName);
                        groupId = spinnerGroupIds.get(index);
                        //description = spinnerProjectGroup.
                        //Toast.makeText(getActivity().getApplicationContext(), GroupName, Toast.LENGTH_SHORT).show();


                        if (!(projectData.isEmpty())) {
                           /* projectPojoData = new ProjectsPojo();
                            projectPojoData.setData(projectData);
                            projectPojoData.setDescription(description);
                            listPojo.add(projectPojoData);
                            adapter = new ProjectsAdapter(getActivity(), listPojo);
                            lv.setAdapter(adapter);*/
                            if (projectDescription.length() <= 255) {
                                //alertDialog.dismiss();
                                ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "", true);
                                ringProgressDialog.setCancelable(false);
                                ringProgressDialog.show();
                                StringRequest request = new StringRequest(Request.Method.POST, End_Points.ADD_NEW_PROJECT, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        ringProgressDialog.dismiss();
                                        if (response.equals("0")) {
                                            Toast.makeText(getActivity().getApplicationContext(), "Project name already exists!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            title.setText("");
                                            etDescription.setText("");
                                         prepareDataList();
                                            Toast.makeText(getActivity().getApplicationContext(), "Project Added Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        ringProgressDialog.dismiss();
                                        //  alertDialog.dismiss();
                                        Toast.makeText(getActivity().getApplicationContext(), "check your internet connection", Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(getActivity(), "Description maximum limit is 255", Toast.LENGTH_LONG).show();
                                return;
                            }
                        } else {
                            Toast.makeText(getActivity(), "Enter Project Name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        Toast.makeText(getActivity(), "Project Group is not Selected", Toast.LENGTH_SHORT).show();
                    }


                }
                //  final String projectDescription = etDescription.getText().toString();
                // = etDescription.getText().toString();
            }
        });
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                projectData = title.getText().toString();
                final String projectDescription = etDescription.getText().toString();

                if (laEt.getVisibility() == View.VISIBLE) {
                    description = etProjectGroup.getText().toString();
                    if(etProjectGroup.getText().toString().equals("")){
                        Toast.makeText(getActivity(),"Please Enter Group Name",Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        if(projectDescription.length()<=255) {
                            ProjectGroupEt();
                            hideKeyBoard(title);
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getActivity(),"Description maximum limit is 255",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    // description = etDescription.getText().toString();

                }
                if (laSpinner.getVisibility() == View.VISIBLE) {
                    int pos=spinnerProjectGroup.getSelectedItemPosition();
                    if(pos!=-1){
                        GroupName = timeAdapter.getItem(spinnerProjectGroup.getSelectedItemPosition());
                        int index = spinnerValues.indexOf(GroupName);
                        groupId = spinnerGroupIds.get(index);
                        //description = spinnerProjectGroup.
                        //Toast.makeText(getActivity().getApplicationContext(), GroupName, Toast.LENGTH_SHORT).show();


                        if (!(projectData.isEmpty())) {
                           /* projectPojoData = new ProjectsPojo();
                            projectPojoData.setData(projectData);
                            projectPojoData.setDescription(description);
                            listPojo.add(projectPojoData);
                            adapter = new ProjectsAdapter(getActivity(), listPojo);
                            lv.setAdapter(adapter);*/
                            if(projectDescription.length()<=255) {
                                hideKeyBoard(title);
                                alertDialog.dismiss();
                                ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "", true);
                                ringProgressDialog.setCancelable(false);
                                ringProgressDialog.show();
                                StringRequest request = new StringRequest(Request.Method.POST, End_Points.ADD_NEW_PROJECT, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        ringProgressDialog.dismiss();
                                        if (response.equals("0")) {
                                            Toast.makeText(getActivity().getApplicationContext(), "Project name already exists!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            prepareDataList();
                                            Toast.makeText(getActivity().getApplicationContext(), "Project Added Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        ringProgressDialog.dismiss();
                                        alertDialog.dismiss();
                                        Toast.makeText(getActivity().getApplicationContext(), "check your internet connection", Toast.LENGTH_SHORT).show();

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

                            }else
                            {
                                Toast.makeText(getActivity(),"Description maximum limit is 255",Toast.LENGTH_LONG).show();
                                return;
                            }
                        } else {
                            Toast.makeText(getActivity(), "Enter Project Name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else {
                        Toast.makeText(getActivity(), "Project Group is not Selected", Toast.LENGTH_SHORT).show();
                    }



                }
                //  final String projectDescription = etDescription.getText().toString();
                // = etDescription.getText().toString();
            }
        });

        alertDialog.setView(customView);
        alertDialog.show();
    }
    private void showKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
    private void hideKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(title.getWindowToken(), 0);
    }
    public void prepareDataList() {

        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listDataStatus = new HashMap<String, List<String>>();
        projectNames = new ArrayList<>();
         dummy = new ArrayList<String>();


        whatever = new ArrayList<>();
        whateverId = new ArrayList<>();
        projectId = new ArrayList<>();
        projectName = new ArrayList<>();
        projectStatus = new ArrayList<>();
        projectids = new ArrayList<>();

        ////////////////////////// STRING REQUEST ////////////////////////////GET_GROUP_PROJECTS


        StringRequest request = new StringRequest(Request.Method.POST,GET_GROUP_PROJECTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
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

                                String data =  object.getString("group_name");

                                JSONArray jsonArray = new JSONArray(data);
                                if(jsonArray.length()<1){
                                  TextView tv= (TextView) view.findViewById(R.id.hiddenText);
                                    tv.setVisibility(View.VISIBLE);
                                }else {
                                    TextView tv= (TextView) view.findViewById(R.id.hiddenText);
                                    tv.setVisibility(View.INVISIBLE);
                                }


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject obj = new JSONObject(jsonArray.getString(i));

                                    projectName.add(obj.getString("pg_id"));
                                    projectId.add(obj.getString("pg_name"));

                                    listDataChild.put(obj.getString("pg_id"),null);
                                }


                                String data2 =  object.getString("group_projects");

                                JSONArray jsonArray1 = new JSONArray(data2);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    MyList = new ArrayList<>();
                                    for (int j = 0 ; j<jsonArray1.length();j++)
                                    {
                                        JSONObject jsonObject = jsonArray1.getJSONObject(j);
                                        String id = jsonObject.getString("project_group");
                                        String projectid =projectName.get(i) ;
                                        if(projectid.equals(id))
                                        {
                                            String projectDescription=jsonObject.getString("project_description");
                                            String projectCreatedBy=jsonObject.getString("project_created_by");
                                            if(projectDescription.equals("")){
                                                projectDescription=" ";
                                            }
                                            MyList.add( jsonObject.getString("project_name")+","+jsonObject.getString("project_status")+","+jsonObject.getString("project_id")+","+projectDescription+","+projectCreatedBy);
                                            projectStatus.add(jsonObject.getString("project_status"));
                                            projectids.add(jsonObject.getString("project_id"));

                                        }


                                    }

                                    listDataChild.put(projectName.get(i)+"", MyList);
                                    listDataStatus.put(projectName.get(i)+"", projectStatus);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            customAdapter = new CustomExpandableListAdapter(getActivity(), projectId, listDataChild , projectName,projectStatus,projectids,listDataStatus);
                            expandableLv.setAdapter(customAdapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();
//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(getActivity().getApplicationContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
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
                params.put("status", ProjectsActivity.status);
                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);


    }

    public ArrayList<String> gth() {
        ArrayList<String>   projectName = new ArrayList<>();


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
    public void getSpinnerData() {


        spinnerValues = new ArrayList<>();
        spinnerGroupIds = new ArrayList<>();

        ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "", true);
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
                                    .setConfirmText("OK").setContentText("Error")
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
                                timeAdapter.setDropDownViewResource(R.layout.nothing_selected_spinnerdate);
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
                //alertDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    Toast.makeText(getActivity(), "check your internet connectionn", Toast.LENGTH_SHORT).show();


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
           /* projectPojoData = new ProjectsPojo();
            projectPojoData.setData(projectData);
            projectPojoData.setDescription(description);
            listPojo.add(projectPojoData);
            adapter = new ProjectsAdapter(getActivity(), listPojo);
            lv.setAdapter(adapter);*/

            StringRequest request = new StringRequest(Request.Method.POST, End_Points.ADD_NEW_PROJECT_ET, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String checkResponse=response.trim();
                    if(!checkResponse.equals("Project already exist")) {
                        prepareDataList();
                        Toast.makeText(getActivity().getApplicationContext(), "Project Added Successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity().getApplicationContext(), "Error, project's group name already exists!", Toast.LENGTH_SHORT).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity().getApplicationContext(), "check your internet connection", Toast.LENGTH_SHORT).show();

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

}





