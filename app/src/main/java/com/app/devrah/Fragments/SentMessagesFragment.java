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
import com.app.devrah.Adapters.SentMessagesAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.SendNewMessageActivity;
import com.app.devrah.pojo.SentMessagesPojo;

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

public class SentMessagesFragment extends Fragment implements View.OnClickListener{

    Button btnSendNewMessage;
    ProgressDialog ringProgressDialog;

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;

    View view;
    SentMessagesAdapter adapter;
    List<SentMessagesPojo> listPojo;
    SentMessagesPojo projectPojoData;
    ListView lv;
    EditText edt;

    String projectData;





    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private SentMessagesFragment.OnFragmentInteractionListener mListener;

    public SentMessagesFragment() {
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
    public static SentMessagesFragment newInstance(String param1, String param2) {
        SentMessagesFragment fragment = new SentMessagesFragment();
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

        view = inflater.inflate(R.layout.fragment_sent_messages, container, false);

         btnSendNewMessage = (Button)view.findViewById(R.id.buttonSendNewMessage);
        listPojo = new ArrayList<>();
        lv = (ListView)view.findViewById(R.id.sentMessagesListView);


        sentMessages();
       /* projectPojoData = new AcitivitiesPojo();
        for(int i=0;i<3;i++) {
            projectPojoData.setData("test" + i);
            listPojo.add(projectPojoData);

        }
        adapter = new ActivitiesAdpater(getActivity(), listPojo);


        lv.setAdapter(adapter);*/
        btnSendNewMessage.setOnClickListener(this);

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

   /* public void onResume(){
        super.onResume();
        sentMessages();
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.buttonSendNewMessage:
                Intent intent=new Intent(getActivity(), SendNewMessageActivity.class);
                startActivity(intent);
                // showDialog();
                //Toast.makeText(getContext(),"Button Pressed",Toast.LENGTH_LONG).show();
                break;

        }

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    public void sentMessages(){
        SharedPreferences pref = getContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        final String userid=pref.getString("user_id","");
        ringProgressDialog = ProgressDialog.show(getContext(), "Please wait ...","", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SENT_MESSAGES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0;i<array.length();i++){

                                JSONObject object = new JSONObject(array.getString(i));

                                projectPojoData = new SentMessagesPojo();
                                projectPojoData.setData("Subject: "+object.getString("message_subject"));
                                //projectPojoData.setId(object.getString("project_id"));
                                listPojo.add(projectPojoData);



                            }
                            adapter = new SentMessagesAdapter(getActivity(), listPojo);
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

                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
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
                params.put("user_id","2");
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }
}
