package com.app.devrah.Views.Notifications;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.app.devrah.Adapters.ActivitiesAdpater;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.AcitivitiesPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Rizwan Butt on 14-Jun-17.
 */

public class ActivitiesFragment extends Fragment implements View.OnClickListener{

    Button btnAddProject;
    View view;
    ActivitiesAdpater adapter;
    List<AcitivitiesPojo> listPojo;
    AcitivitiesPojo projectPojoData;
    ListView lv;
    EditText edt;

    String projectData;





    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private NotificationsFragment.OnFragmentInteractionListener mListener;

    public ActivitiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Projects.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivitiesFragment newInstance(String param1, String param2) {
        ActivitiesFragment fragment = new ActivitiesFragment();
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

        view = inflater.inflate(R.layout.fragment_acitivities, container, false);
        // btnAddProject = (Button)view.findViewById(R.id.buttonAddProject);
        lv = (ListView)view.findViewById(R.id.activitiesListView);
        /*listPojo = new ArrayList<>();
        lv = (ListView)view.findViewById(R.id.activitiesListView);
        projectPojoData = new AcitivitiesPojo();
        for(int i=0;i<3;i++) {
            projectPojoData.setData("test" + i);
            listPojo.add(projectPojoData);

        }
        adapter = new ActivitiesAdpater(getActivity(), listPojo);


        lv.setAdapter(adapter);*/
        getActivitiesData();

        return  view;
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

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    public void getActivitiesData() {
        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.ACTIVITIES_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        try {
                            listPojo = new ArrayList<>();
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArrayProjectGroups=jsonObject.getJSONArray("project_groups");
                            JSONArray jsonArrayProjects=jsonObject.getJSONArray("projects");
                            JSONArray jsonArrayProjectMembers=jsonObject.getJSONArray("project_members");
                            if(jsonArrayProjectGroups.length()<1 && jsonArrayProjects.length()<1 && jsonArrayProjectMembers.length()<1){
                                listPojo = new ArrayList<>();
                                AcitivitiesPojo acitivitiesPojo=new AcitivitiesPojo();
                                acitivitiesPojo.setData("");
                                acitivitiesPojo.setDate("");
                                acitivitiesPojo.setUserName("");
                                acitivitiesPojo.setDataArray("");
                                acitivitiesPojo.setProjectId("");
                                acitivitiesPojo.setBoardId("");
                                acitivitiesPojo.setListId("");
                                acitivitiesPojo.setCardId("");
                                acitivitiesPojo.setBase("No Data Found");
                                listPojo.add(acitivitiesPojo);
                                adapter=new ActivitiesAdpater(getActivity(),listPojo);
                                lv.setAdapter(adapter);
                            }

                            if(jsonArrayProjectGroups.length()>0){
                                for (int i=0;i<jsonArrayProjectGroups.length();i++){
                                    JSONObject object=jsonArrayProjectGroups.getJSONObject(i);
                                    AcitivitiesPojo acitivitiesPojo=new AcitivitiesPojo();
                                    if(object.getString("action_done").equals("1"))
                                    acitivitiesPojo.setData("added");
                                    else if(object.getString("action_done").equals("2"))
                                        acitivitiesPojo.setData("updated");
                                    else if(object.getString("action_done").equals("3"))
                                        acitivitiesPojo.setData("removed "+object.getString("action_user")+" from");
                                    acitivitiesPojo.setDate(object.getString("action_datetime"));
                                    acitivitiesPojo.setUserName(object.getString("action_by"));
                                    acitivitiesPojo.setDataArray(object.getString("name"));
                                    acitivitiesPojo.setBase("Project Group");
                                    acitivitiesPojo.setProjectId("");
                                    acitivitiesPojo.setBoardId("");
                                    acitivitiesPojo.setListId("");
                                    acitivitiesPojo.setCardId("");
                                    listPojo.add(acitivitiesPojo);
                                }
                            }
                            if(jsonArrayProjects.length()>0){
                                for (int i=0;i<jsonArrayProjects.length();i++){
                                    JSONObject object=jsonArrayProjects.getJSONObject(i);
                                    AcitivitiesPojo acitivitiesPojo=new AcitivitiesPojo();
                                    if(object.getString("action_done").equals("1"))
                                        acitivitiesPojo.setData("added");
                                    else if(object.getString("action_done").equals("2"))
                                        acitivitiesPojo.setData("updated");
                                    else if(object.getString("action_done").equals("3"))
                                        acitivitiesPojo.setData("removed "+object.getString("action_user")+" from");
                                    acitivitiesPojo.setDate(object.getString("action_datetime"));
                                    acitivitiesPojo.setUserName(object.getString("action_by"));
                                    acitivitiesPojo.setDataArray(object.getString("name"));
                                    acitivitiesPojo.setBase("Project");
                                    acitivitiesPojo.setProjectId("");
                                    acitivitiesPojo.setBoardId("");
                                    acitivitiesPojo.setListId("");
                                    acitivitiesPojo.setCardId("");
                                    listPojo.add(acitivitiesPojo);
                                }
                            }
                            if(jsonArrayProjectMembers.length()>0){
                                for (int i=0;i<jsonArrayProjectMembers.length();i++){
                                    JSONObject object=jsonArrayProjectMembers.getJSONObject(i);
                                    AcitivitiesPojo acitivitiesPojo=new AcitivitiesPojo();
                                    if(object.getString("action_done").equals("1"))
                                        acitivitiesPojo.setData("added "+object.getString("action_user")+" in");
                                    else if(object.getString("action_done").equals("2"))
                                        acitivitiesPojo.setData("updated");
                                    else if(object.getString("action_done").equals("3"))
                                        acitivitiesPojo.setData("removed "+object.getString("action_user")+" from");
                                    acitivitiesPojo.setDate(object.getString("action_datetime"));
                                    acitivitiesPojo.setUserName(object.getString("action_by"));
                                    acitivitiesPojo.setDataArray(object.getString("name"));
                                    acitivitiesPojo.setBase("Project");
                                    acitivitiesPojo.setProjectId("");
                                    acitivitiesPojo.setBoardId("");
                                    acitivitiesPojo.setListId("");
                                    acitivitiesPojo.setCardId("");
                                    listPojo.add(acitivitiesPojo);
                                }
                            }
                            adapter=new ActivitiesAdpater(getActivity(),listPojo);
                            lv.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                       /* if(!response.equals("false")){
                            listPojo = new ArrayList<>();
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    AcitivitiesPojo acitivitiesPojo=new AcitivitiesPojo();
                                    if(jsonObject.get("remarks").equals("Save_projects"))
                                        acitivitiesPojo.setData("added project");
                                    else  if(jsonObject.get("remarks").equals("save_project_groups"))
                                        acitivitiesPojo.setData("added group");
                                    else
                                    acitivitiesPojo.setData(jsonObject.getString("remarks"));
                                    acitivitiesPojo.setDate(jsonObject.getString("action_time"));
                                    acitivitiesPojo.setUserName(jsonObject.getString("first_name")+ " " + jsonObject.getString("last_name"));
                                     if(!(jsonObject.getString("project_id").equals("null")) && jsonObject.getString("board_id").equals("null") && jsonObject.getString("list_id").equals("null") && jsonObject.getString("card_id").equals("null") && jsonObject.getString("action").equals("Insert") && (jsonObject.getString("table_name").equals("projects") || jsonObject.getString("table_name").equals("project_groups"))) {
                                        //String dataArray = jsonObject.getString("data_array");
                                        String dataArray=jsonObject.getString("data_array");
                                        JSONObject jsonObject1=new JSONObject(dataArray);
                                         if(jsonObject1.has("project_name"))
                                             acitivitiesPojo.setDataArray(jsonObject1.getString("project_name"));
                                         else if(jsonObject1.has("pg_name"))
                                             acitivitiesPojo.setDataArray(jsonObject1.getString("pg_name"));
                                         else
                                             acitivitiesPojo.setDataArray("");

                                         acitivitiesPojo.setProjectId(jsonObject.getString("project_id"));
                                         acitivitiesPojo.setBoardId("");
                                         acitivitiesPojo.setListId("");
                                         acitivitiesPojo.setCardId("");
                                       // acitivitiesPojo.setDataArray(jsonObject1.getString("project_name"));
//                                    if(!dataArray.equals("null")) {
//                                        String[] subString = dataArray.split(",");
//                                        String[] finalString = subString[0].split(":");
                                       // acitivitiesPojo.setDataArray(dataArray);
                                    }
                                    else if(!(jsonObject.getString("project_id").equals("null")) && !jsonObject.getString("board_id").equals("null") && jsonObject.getString("list_id").equals("null") && jsonObject.getString("card_id").equals("null") && jsonObject.getString("action").equals("insert") && jsonObject.getString("table_name").equals("boards")) {
                                        //String dataArray = jsonObject.getString("data_array");
                                        String dataArray=jsonObject.getString("data_array");
                                        JSONObject jsonObject1=new JSONObject(dataArray);
                                         if(jsonObject1.has("board_name"))
                                             acitivitiesPojo.setDataArray(jsonObject1.getString("board_name"));
                                         else
                                             acitivitiesPojo.setDataArray("");
                                        acitivitiesPojo.setProjectId(jsonObject.getString("project_id"));
                                        acitivitiesPojo.setBoardId(jsonObject.getString("board_id"));
                                        acitivitiesPojo.setListId("");
                                        acitivitiesPojo.setCardId("");
                                        // acitivitiesPojo.setDataArray(jsonObject1.getString("project_name"));
//                                    if(!dataArray.equals("null")) {
//                                        String[] subString = dataArray.split(",");
//                                        String[] finalString = subString[0].split(":");
                                        // acitivitiesPojo.setDataArray(dataArray);
                                    }
                                     else if(!(jsonObject.getString("project_id").equals("null")) && !jsonObject.getString("board_id").equals("null") && !jsonObject.getString("list_id").equals("null") && jsonObject.getString("card_id").equals("null") && jsonObject.getString("action").equals("insert") && jsonObject.getString("table_name").equals("lists")) {
                                         //String dataArray = jsonObject.getString("data_array");
                                         String dataArray=jsonObject.getString("data_array");
                                         JSONObject jsonObject1=new JSONObject(dataArray);
                                         if(jsonObject1.has("list_name"))
                                         acitivitiesPojo.setDataArray(jsonObject1.getString("list_name"));
                                         else
                                             acitivitiesPojo.setDataArray("");
                                         acitivitiesPojo.setProjectId(jsonObject.getString("project_id"));
                                         acitivitiesPojo.setBoardId(jsonObject.getString("board_id"));
                                         acitivitiesPojo.setListId(jsonObject.getString("list_id"));
                                         acitivitiesPojo.setCardId("");
                                         // acitivitiesPojo.setDataArray(jsonObject1.getString("project_name"));
//                                    if(!dataArray.equals("null")) {
//                                        String[] subString = dataArray.split(",");
//                                        String[] finalString = subString[0].split(":");
                                         // acitivitiesPojo.setDataArray(dataArray);
                                     }else if(!(jsonObject.getString("project_id").equals("null")) && !jsonObject.getString("board_id").equals("null") && !jsonObject.getString("list_id").equals("null") && !jsonObject.getString("card_id").equals("null") && jsonObject.getString("action").equals("insert") && jsonObject.getString("table_name").equals("cards")) {
                                         //String dataArray = jsonObject.getString("data_array");
                                         String dataArray=jsonObject.getString("data_array");
                                         JSONObject jsonObject1=new JSONObject(dataArray);
                                         if(jsonObject1.has("card_name"))
                                         acitivitiesPojo.setDataArray(jsonObject1.getString("card_name"));
                                         else
                                         acitivitiesPojo.setDataArray("");
                                         acitivitiesPojo.setProjectId(jsonObject.getString("project_id"));
                                         acitivitiesPojo.setBoardId(jsonObject.getString("board_id"));
                                         acitivitiesPojo.setListId(jsonObject.getString("list_id"));
                                         acitivitiesPojo.setCardId(jsonObject.getString("card_id"));
                                         // acitivitiesPojo.setDataArray(jsonObject1.getString("project_name"));
//                                    if(!dataArray.equals("null")) {
//                                        String[] subString = dataArray.split(",");
//                                        String[] finalString = subString[0].split(":");
                                         // acitivitiesPojo.setDataArray(dataArray);
                                     }else {
                                         acitivitiesPojo.setDataArray("");
                                         acitivitiesPojo.setProjectId("");
                                         acitivitiesPojo.setBoardId("");
                                         acitivitiesPojo.setListId("");
                                         acitivitiesPojo.setCardId("");
                                     }
                                    //}

                                    listPojo.add(acitivitiesPojo);
                                }

                                adapter=new ActivitiesAdpater(getActivity(),listPojo);
                                lv.setAdapter(adapter);

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                        if(response.equals("false")){
                            listPojo = new ArrayList<>();
                            AcitivitiesPojo acitivitiesPojo=new AcitivitiesPojo();
                            acitivitiesPojo.setData("");
                            acitivitiesPojo.setDate("");
                            acitivitiesPojo.setUserName("");
                            acitivitiesPojo.setDataArray("No data found");
                            acitivitiesPojo.setProjectId("");
                            acitivitiesPojo.setBoardId("");
                            acitivitiesPojo.setListId("");
                            acitivitiesPojo.setCardId("");
                            listPojo.add(acitivitiesPojo);
                            adapter=new ActivitiesAdpater(getActivity(),listPojo);
                            lv.setAdapter(adapter);
                        }*/


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();

//             new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
//                     .setTitleText("Error!")
//                     .setConfirmText("OK").setContentText("No Internet Connection")
//                     .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                         @Override
//                         public void onClick(SweetAlertDialog sDialog) {
//                             sDialog.dismiss();
//                         }
//                     })
//                     .show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(getActivity().getApplicationContext(), "Timeout Error", Toast.LENGTH_SHORT).show();

//             new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
//                     .setTitleText("Error!")
//                     .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
//                     .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                         @Override
//                         public void onClick(SweetAlertDialog sDialog) {
//                             sDialog.dismiss();
//                         }
//                     })
//                     .show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userId = pref.getString("user_id", "");

                params.put("userId", userId);
               // params.put("project_id", "");
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

}
