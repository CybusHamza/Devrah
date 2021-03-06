package com.app.devrah.Views.Messages;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
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
import com.app.devrah.Adapters.InboxAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.InboxPojo;

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

public class SentMessagesFragment extends Fragment implements View.OnClickListener {

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnSendNewMessage;
    ProgressDialog ringProgressDialog;
    View view;
    InboxAdapter adapter;
    List<InboxPojo> listPojo;
    InboxPojo projectPojoData;
    ListView lv;
    EditText edt;
    String projectData;
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

        btnSendNewMessage = (Button) view.findViewById(R.id.buttonSendNewMessage);

        lv = (ListView) view.findViewById(R.id.sentMessagesListView);


        sentMessages();
       /* projectPojoData = new AcitivitiesPojo();
        for(int i=0;i<3;i++) {
            projectPojoData.setData("test" + i);
            listPojo.add(projectPojoData);

        }
        adapter = new ActivitiesAdpater(getActivity(), listPojo);


        lv.setAdapter(adapter);*/
        btnSendNewMessage.setOnClickListener(this);

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

   /* public void onResume(){
        super.onResume();
        sentMessages();
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonSendNewMessage:
                Intent intent = new Intent(getActivity(), SendNewMessageActivity.class);
                startActivity(intent);
                // showDialog();
                //Toast.makeText(getContext(),"Button Pressed",Toast.LENGTH_LONG).show();
                break;

        }

    }

    public void sentMessages() {
        SharedPreferences pref = getContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        final String userid = pref.getString("user_id", "");
        ringProgressDialog = ProgressDialog.show(getContext(), "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SENT_MESSAGES,
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        listPojo = new ArrayList<>();
                        try {
                            JSONArray array = new JSONArray(response);
                            if(array.length()<1){
                                projectPojoData = new InboxPojo();

                                projectPojoData.setId("");
                                projectPojoData.setB_id("");
                                projectPojoData.setCardif("");
                                projectPojoData.setDate("");
                                projectPojoData.setFrom("");
                                projectPojoData.setIsread("");
                                projectPojoData.setMessage("");
                                projectPojoData.setP_id("");
                                projectPojoData.setSubject("No Messages found");
                                projectPojoData.setProjectId("");
                                projectPojoData.setBoardId("");
                                projectPojoData.setCardId("");

                                listPojo.add(projectPojoData);
                            }
                           // JSONArray array1=new JSONArray(response);
                           // String test="";//new String[array.length()];
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = new JSONObject(array.getString(i));
                                projectPojoData = new InboxPojo();

                                 projectPojoData.setId(object.getString("id"));
                                 projectPojoData.setB_id(object.getString("board_name"));
                                 projectPojoData.setCardif(object.getString("card_name"));
                                 projectPojoData.setDate(object.getString("datetime"));
                               // String from=object.getString("email");
                                JSONArray array1=object.getJSONArray("to_name");
                                String finalData="";
                                for (int j=0;j<array1.length();j++) {
                                    finalData=finalData+","+array1.get(j).toString();
                                    projectPojoData.setFrom(finalData);
                                }
                                 projectPojoData.setIsread(object.getString("is_read"));
                                 projectPojoData.setMessage(object.getString("message"));
                                 projectPojoData.setP_id(object.getString("project_name"));
                                 projectPojoData.setSubject(object.getString("message_subject"));
                                 projectPojoData.setProjectId(object.getString("project_id"));
                                 projectPojoData.setBoardId(object.getString("board_id"));
                                 projectPojoData.setCardId(object.getString("card_id"));
                                 projectPojoData.setEmail(object.getString("email"));

                                listPojo.add(projectPojoData);


                            }
                            adapter = new InboxAdapter(getActivity(), listPojo,"sent");
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
                    Toast.makeText(getContext(),"check your internet connection",Toast.LENGTH_LONG).show();

                   /* new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
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
                    Toast.makeText(getContext(),"Connection TimeOut! Please check your internet connection.",Toast.LENGTH_LONG).show();

                   /* new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
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

                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userId = pref.getString("user_id", "");

                params.put("userId",userId);
                params.put("filter", MessagesActivity.filter);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
