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
import com.app.devrah.Adapters.NotificationAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.AcitivitiesPojo;
import com.app.devrah.pojo.NotificationsPojo;
import com.app.devrah.pojo.boardNotificationsPojo;
import com.app.devrah.pojo.cardNotificationsPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Rizwan Butt on 14-Jun-17.
 */

public class NotificationsFragment extends Fragment implements View.OnClickListener{

        Button btnAddProject;
        View view;
        NotificationAdapter adapter;
        List<NotificationsPojo> listPojo;
        List<boardNotificationsPojo> boardlistPojo;
        List<cardNotificationsPojo> cardlistPojo;
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

        lv = (ListView)view.findViewById(R.id.notificationsListView);

        getNotifications();
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
public void getNotifications() {
         final ProgressDialog ringProgressDialog;
         ringProgressDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
         ringProgressDialog.setCancelable(false);
         ringProgressDialog.show();

         StringRequest request = new StringRequest(Request.Method.POST, End_Points.NOTIFICATIONS,
                 new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {

                             ringProgressDialog.dismiss();
                             listPojo = new ArrayList<>();
                          /*   boardlistPojo = new ArrayList<>();
                             cardlistPojo = new ArrayList<>();
                             SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");*/
                             try {

                                 if(response.equals("")){
                                     listPojo = new ArrayList<>();
                                     NotificationsPojo notificationsPojo = new NotificationsPojo();
                                     notificationsPojo.setUserName("");
                                     notificationsPojo.setData("");
                                     notificationsPojo.setDate("");
                                     notificationsPojo.setProjectId("");
                                     notificationsPojo.setProjectTitle("");
                                     notificationsPojo.setBoardId("");
                                     notificationsPojo.setCardId("");
                                     notificationsPojo.setCardDueDate("");
                                     notificationsPojo.setCardStartDate("");
                                     notificationsPojo.setCardDueTime("");
                                     notificationsPojo.setCardStartTime("");
                                     notificationsPojo.setCardDescription("");
                                     notificationsPojo.setIsComplete("");
                                     notificationsPojo.setIsSubscribed("");
                                     notificationsPojo.setProjectCreatedBy("");
                                     notificationsPojo.setIsLocked("");
                                     notificationsPojo.setListId("");
                                     notificationsPojo.setBoard_name("");
                                     notificationsPojo.setLabel("");
                                     notificationsPojo.setBase("No notification found");
                                     listPojo.add(notificationsPojo);
                                     adapter = new NotificationAdapter(getActivity(), listPojo/*,boardlistPojo,cardlistPojo*/);
                                     lv.setAdapter(adapter);
                                 }else {
                                   //  JSONObject jsonObject = new JSONObject(response);
                                     JSONArray jsonArray = new JSONArray(response);
                                     if (jsonArray.length() > 0) {
                                         for (int i = 0; i < jsonArray.length(); i++) {
                                             JSONObject object = jsonArray.getJSONObject(i);
                                             NotificationsPojo notificationsPojo = new NotificationsPojo();
                                           //  if(object.getString("title").equals("Project")) {
                                                 //  if(object.getString("action_done").equals("1"))
                                              //   notificationsPojo.setData(object.getString("name"));
                                                 notificationsPojo.setDate(object.getString("dateTime"));
                                                // notificationsPojo.setUserName(object.getString("action_by"));
                                               //  notificationsPojo.setProjectTitle(object.getString("name"));
                                                 notificationsPojo.setProjectId(object.getString("project_id"));
                                                 notificationsPojo.setBoardId(object.getString("board_id"));
                                                 notificationsPojo.setCardId(object.getString("card_id"));
                                                 notificationsPojo.setCardDueDate("");
                                                 notificationsPojo.setCardStartDate("");
                                                 notificationsPojo.setCardDueTime("");
                                                 notificationsPojo.setCardStartTime("");
                                                 notificationsPojo.setCardDescription("");
                                                 notificationsPojo.setIsComplete("");
                                                 notificationsPojo.setIsSubscribed("");
                                                 notificationsPojo.setIsLocked("");
                                                 notificationsPojo.setListId(object.getString("list_id"));
                                                 notificationsPojo.setBoard_name("");
                                                 notificationsPojo.setLabel(object.getString("text"));
                                                 notificationsPojo.setBase(object.getString("title"));
                                                 listPojo.add(notificationsPojo);
                                             //}
                                         }
                                     }

                                     adapter = new NotificationAdapter(getActivity(), listPojo/*,boardlistPojo,cardlistPojo*/);
                                     lv.setAdapter(adapter);
                                 }

                             }catch (Exception e){
                                 e.printStackTrace();
                             }

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

                                params.put("user_id", userId);
                                params.put("project_id", "0");
                                params.put("board_id", "0");
                                params.put("card_id", "0");
                                params.put("sort_type", "DESC");
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
