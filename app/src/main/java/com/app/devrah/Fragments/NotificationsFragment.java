package com.app.devrah.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.app.devrah.Adapters.NotificationAdapter;
import com.app.devrah.Adapters.ProjectsAdapter;
import com.app.devrah.Dashboard;
import com.app.devrah.Login;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.AcitivitiesPojo;
import com.app.devrah.pojo.NotificationsPojo;
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

/**
 * Created by Rizwan Butt on 14-Jun-17.
 */

public class NotificationsFragment extends Fragment implements View.OnClickListener{

        Button btnAddProject;
        View view;
        NotificationAdapter adapter;
        List<NotificationsPojo> listPojo;
        NotificationsPojo projectPojoData;
        ListView lv;
        EditText edt;

        String projectData;





private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

private String mParam1;
private String mParam2;


private NotificationsFragment.OnFragmentInteractionListener mListener;

public NotificationsFragment() {
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
public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
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

        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        // btnAddProject = (Button)view.findViewById(R.id.buttonAddProject);
        listPojo = new ArrayList<>();
        lv = (ListView)view.findViewById(R.id.notificationsListView);


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


}
