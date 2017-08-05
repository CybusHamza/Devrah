package com.app.devrah.Fragments;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.app.devrah.Adapters.CustomViewPagerAdapter;
import com.app.devrah.Adapters.FragmentBoardsAdapter;
import com.app.devrah.R;
import com.app.devrah.Views.BoardExtended;
import com.app.devrah.pojo.ProjectsPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.app.devrah.Network.End_Points.GET_CARDS_FOR_LIST;


public class ChildFragmentBoardExtended extends Fragment {
       private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
        View view;
    TextView tvAddCard;
    String childname;
    EditText   edt;
    public     TextView tvName;
    ImageView boardMenu;
    FragmentBoardsAdapter adapter;
    List<ProjectsPojo> listPojo;
    CustomViewPagerAdapter VPadapter;
    ProjectsPojo boardsFragmentPojoData;
    ListView lv;


    String id,p_id,b_id,list_id;


    private OnFragmentInteractionListener mListener;

    public ChildFragmentBoardExtended() {

         }


    public static ChildFragmentBoardExtended newInstance(String param1, String param2) {
        ChildFragmentBoardExtended fragment = new ChildFragmentBoardExtended();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        view =inflater.inflate(R.layout.fragment_child_fragment_board_extended, container, false);
        View emV = inflater.inflate(R.layout.empty_list_bg,null);




         tvName = (TextView)view.findViewById(R.id.textName);
            boardMenu = (ImageView)view.findViewById(R.id.menuBoards);




        tvAddCard = (TextView)view.findViewById(R.id.addCard);
        Bundle bundle = this.getArguments();
        childname = bundle.getString("data");
        p_id = bundle.getString("p_id");
        b_id = bundle.getString("b_id");
        list_id = bundle.getString("list_id");


        tvName.setText(childname);

        getList(list_id);

        lv = (ListView) view.findViewById(R.id.boardFragmentListView);
        boardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Start - position: " , Toast.LENGTH_SHORT).show();
//
//                View popupView =getActivity().getLayoutInflater().inflate(R.layout.ui_for_popup_view, null);
//                PopupWindow popupWindow = new PopupWindow(popupView, ActionMenuView.LayoutParams.WRAP_CONTENT, ActionMenuView.LayoutParams.WRAP_CONTENT);
//                popupWindow.setFocusable(true);


            }
        });


        lv.setEmptyView(emV);


        tvAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCustomDialog();

            }
        });

        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.popup_edit_delete, menu);
//
//        int positionOfMenuItem = 0;
//        MenuItem item = menu.getItem(positionOfMenuItem);
//        SpannableString s = new SpannableString("My red MenuItem");
//        s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
//        item.setTitle(s);
//    }



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
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }



    public void showCustomDialog(){


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_alert_dialogbox,null);
        final AlertDialog alertDialog = new  AlertDialog.Builder(getContext()).create();


        edt = (EditText)customView.findViewById(R.id.input_watever);
        TextView addCard = (TextView)customView.findViewById(R.id.btn_add_board);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String projectData = edt.getText().toString();
                String  boardsFragmentData = edt.getText().toString();

                if (!(boardsFragmentData.isEmpty())) {
                    boardsFragmentPojoData = new ProjectsPojo();
                    boardsFragmentPojoData.setData(boardsFragmentData);
                    listPojo.add(boardsFragmentPojoData);
                    adapter = new FragmentBoardsAdapter(getActivity(), listPojo);


                    lv.setAdapter(adapter);
                }
                else {
                    Toast.makeText(getActivity(),"No Text Entered",Toast.LENGTH_SHORT).show();
                }


                alertDialog.dismiss();

            }
        });

        alertDialog.setView(customView);
        alertDialog.show();

    }


    public void getList(final String lsitId) {
        final SharedPreferences pref = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, GET_CARDS_FOR_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if (!(response.equals("false"))) {

                            listPojo = new ArrayList<>();

                            try {
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    ProjectsPojo projectsPojo = new ProjectsPojo();

                                    projectsPojo.setId(jsonObject.getString("id"));
                                    projectsPojo.setData(jsonObject.getString("card_name"));
                                    listPojo.add(projectsPojo);

                                }

                                adapter = new FragmentBoardsAdapter(getActivity(), listPojo);
                                lv.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();


                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {


                    Toast.makeText(getActivity(), "TimeOut eRROR", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", BoardExtended.boardId);
                params.put("project_id", BoardExtended.projectId);
                 params.put("userId", pref.getString("user_id",""));
                 params.put("list_id", lsitId);
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
