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
import com.app.devrah.Adapters.NotificationAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.NotificationsPojo;
import com.app.devrah.pojo.boardNotificationsPojo;
import com.app.devrah.pojo.cardNotificationsPojo;

import org.json.JSONArray;
import org.json.JSONObject;

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
                             boardlistPojo = new ArrayList<>();
                             cardlistPojo = new ArrayList<>();

                             try {
                                 JSONObject mainObject = new JSONObject(response);
                                 JSONArray jsonArrayProjectNotifications = mainObject.getJSONArray("project_notifications");
                                 for (int i = 0; i < jsonArrayProjectNotifications.length(); i++) {
                                     JSONObject jsonObject = jsonArrayProjectNotifications.getJSONObject(i);

                                     NotificationsPojo notificationsPojo = new NotificationsPojo();
                                     notificationsPojo.setUserName(jsonObject.getString("first_name")+" "+jsonObject.getString("last_name"));
                                     notificationsPojo.setData(jsonObject.getString("project_name"));
                                     notificationsPojo.setDate(jsonObject.getString("project_assigned_on"));
                                     notificationsPojo.setProjectId(jsonObject.getString("project_id"));
                                     notificationsPojo.setProjectTitle(jsonObject.getString("project_name"));
                                     notificationsPojo.setBoardId("0");
                                     notificationsPojo.setCardId("");
                                     notificationsPojo.setCardDueDate("");
                                     notificationsPojo.setCardStartDate("");
                                     notificationsPojo.setCardDueTime("");
                                     notificationsPojo.setCardStartTime("");
                                     notificationsPojo.setCardDescription("");
                                     notificationsPojo.setIsComplete("");
                                     notificationsPojo.setIsSubscribed("");
                                     notificationsPojo.setIsLocked("");
                                     notificationsPojo.setListId("");
                                     notificationsPojo.setBoard_name("");
                                     notificationsPojo.setLabel(" added you to the project ");


                                     listPojo.add(notificationsPojo);
                                     // getLabelsList(jsonObject.getString("id"));

                                 }
                                 JSONArray jsonArrayBoardNotifications = mainObject.getJSONArray("boards_notifications");
                                 for (int i = 0; i < jsonArrayBoardNotifications.length(); i++) {
                                     JSONObject jsonObject = jsonArrayBoardNotifications.getJSONObject(i);

                                     NotificationsPojo boardnotificationsPojo = new NotificationsPojo();
                                     boardnotificationsPojo.setUserName(jsonObject.getString("first_name")+" "+jsonObject.getString("last_name"));
                                     boardnotificationsPojo.setData(jsonObject.getString("board_name"));
                                     boardnotificationsPojo.setProjectId(jsonObject.getString("project_id"));
                                     boardnotificationsPojo.setBoardId(jsonObject.getString("board_id"));
                                     boardnotificationsPojo.setProjectTitle(jsonObject.getString("project_name"));
                                     boardnotificationsPojo.setDate(jsonObject.getString("board_assigned_on"));
                                     boardnotificationsPojo.setCardId("");
                                     boardnotificationsPojo.setCardDueDate("");
                                     boardnotificationsPojo.setCardStartDate("");
                                     boardnotificationsPojo.setCardDueTime("");
                                     boardnotificationsPojo.setCardStartTime("");
                                     boardnotificationsPojo.setCardDescription("");
                                     boardnotificationsPojo.setIsComplete("");
                                     boardnotificationsPojo.setIsSubscribed("");
                                     boardnotificationsPojo.setIsLocked("");
                                     boardnotificationsPojo.setListId("");
                                     boardnotificationsPojo.setBoard_name("");
                                     boardnotificationsPojo.setLabel(" added you to the board ");

                                     listPojo.add(boardnotificationsPojo);
                                     // getLabelsList(jsonObject.getString("id"));

                                 }
                                 JSONArray jsonArrayCardNotifications = mainObject.getJSONArray("card_notifications");
                                 for (int i = 0; i < jsonArrayCardNotifications.length(); i++) {
                                     JSONObject jsonObject = jsonArrayCardNotifications.getJSONObject(i);

                                     NotificationsPojo cardnotificationsPojo = new NotificationsPojo();
                                     cardnotificationsPojo.setUserName(jsonObject.getString("first_name")+" "+jsonObject.getString("last_name"));
                                     cardnotificationsPojo.setData(jsonObject.getString("card_name"));
                                     cardnotificationsPojo.setCardId(jsonObject.getString("card_id"));
                                     cardnotificationsPojo.setCardDueDate(jsonObject.getString("card_end_date"));
                                     cardnotificationsPojo.setCardStartDate(jsonObject.getString("card_start_date"));
                                     cardnotificationsPojo.setCardDueTime(jsonObject.getString("card_due_time"));
                                     cardnotificationsPojo.setCardStartTime(jsonObject.getString("card_start_time"));
                                     cardnotificationsPojo.setCardDescription(jsonObject.getString("card_description"));
                                     cardnotificationsPojo.setIsComplete(jsonObject.getString("card_is_complete"));
                                     cardnotificationsPojo.setIsSubscribed(jsonObject.getString("subscribed"));
                                     cardnotificationsPojo.setIsLocked(jsonObject.getString("is_locked"));
                                     cardnotificationsPojo.setDate(jsonObject.getString("card_assigned_on"));
                                     cardnotificationsPojo.setProjectId(jsonObject.getString("project_id"));
                                     cardnotificationsPojo.setProjectTitle(jsonObject.getString("project_name"));
                                     cardnotificationsPojo.setBoardId(jsonObject.getString("board_id"));
                                     cardnotificationsPojo.setListId(jsonObject.getString("list_id"));
                                     cardnotificationsPojo.setBoard_name(jsonObject.getString("board_name"));
                                     cardnotificationsPojo.setLabel(" added you to the card ");

                                     listPojo.add(cardnotificationsPojo);
                                     // getLabelsList(jsonObject.getString("id"));

                                 }
                                 adapter=new NotificationAdapter(getActivity(),listPojo,boardlistPojo,cardlistPojo);
                                 lv.setAdapter(adapter);

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

                                params.put("userId", userId);
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
