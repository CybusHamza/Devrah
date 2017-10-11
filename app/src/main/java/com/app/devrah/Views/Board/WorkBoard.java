package com.app.devrah.Views.Board;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.app.devrah.Adapters.BoardsAdapter;
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

import static android.content.Context.MODE_PRIVATE;
import static com.app.devrah.Network.End_Points.ADD_WORK_BOARD;


public class WorkBoard extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    Button addBoard;
    EditText etSearch,edt;
    TextView tvWorkBoard;
    ListView lvWBoard;
    ImageView search;
    BoardsAdapter adapter;
    List<ProjectsPojo> myList;
    private String mParam1;
    String projectid;
    private String mParam2;
    SwipeRefreshLayout mySwipeRefreshLayout;
    private OnFragmentInteractionListener mListener;

    public WorkBoard() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static WorkBoard newInstance(String param1, String param2) {
        WorkBoard fragment = new WorkBoard();
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
        view = inflater.inflate(R.layout.fragment_work_board, container, false);
        lvWBoard = (ListView)view.findViewById(R.id.lvRBoard);
        addBoard = (Button)view.findViewById(R.id.buttonAddRBoard);
        search = (ImageView)view.findViewById(R.id.searchBar);
        etSearch = (EditText)view.findViewById(R.id.etSearchWBoard);
        tvWorkBoard = (TextView)view.findViewById(R.id.tvReferenceBoard);
        mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        //   spinnerValues = new String[]{};
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                       getWorkBoards();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        etSearch.setVisibility(View.INVISIBLE);
        addBoard.setOnClickListener(this);
        Bundle bundle = this.getArguments();
        projectid = bundle.getString("pid");

        search.setOnClickListener(this);


        getWorkBoards();



        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nameToSearch=etSearch.getText().toString();
                ArrayList<ProjectsPojo> filteredLeaves=new ArrayList<ProjectsPojo>();

                for (ProjectsPojo data: myList) {
                    if (data.getData().toLowerCase().contains(nameToSearch.toLowerCase())  )
                    {
                        filteredLeaves.add(data);
                    }


                }

                adapter = new BoardsAdapter(getActivity(),filteredLeaves);
                lvWBoard.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });






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
            case R.id.buttonAddRBoard:
                showDialog();
                break;

            case R.id.searchBar:
                etSearch.setVisibility(View.VISIBLE);
                tvWorkBoard.setVisibility(View.INVISIBLE);
        }
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void  showDialog(){

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_alert_dialogbox,null);
        final AlertDialog alertDialog = new  AlertDialog.Builder(getContext()).create();

            alertDialog.setCancelable(false);
        edt = (EditText)customView.findViewById(R.id.input_watever);
        final TextView addCard = (TextView)customView.findViewById(R.id.btn_add_board);
        final TextView cancel = (TextView)customView.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String projectData = edt.getText().toString();
                if (!(projectData.isEmpty())) {

                    addNewBoard(projectData);

/*
                    ProjectsPojo projectPojoData = new ProjectsPojo();
                    projectPojoData.setData(projectData);
                    myList.add(projectPojoData);
                    adapter = new BoardsAdapter(getActivity(), myList);


                    lvWBoard.setAdapter(adapter);*/

                    alertDialog.dismiss();

                }
                else {
                    Toast.makeText(getActivity(),"Please Enter Board Name",Toast.LENGTH_SHORT).show();
                }







            }
        });

            alertDialog.setView(customView);
        alertDialog.show();


//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());
//        LayoutInflater inflater = this.getActivity().
//                getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.alert_dialogbox_layout, null);
//        dialogBuilder.setView(dialogView);
//
//        final EditText title = (EditText) dialogView.findViewById(R.id.get_data_alertBox);
//
//        dialogBuilder.setTitle("Custom dialog");
//        dialogBuilder.setMessage("Enter text below");
//        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //do something with title.getText().toString();
//               String projectData = title.getText().toString();
//
//                if (!(projectData.isEmpty())) {
//
//                    ProjectsPojo projectPojoData = new ProjectsPojo();
//                    projectPojoData.setData(projectData);
//                    myList.add(projectPojoData);
//                    adapter = new BoardsAdapter(getActivity(), myList);
//
//
//                    lvWBoard.setAdapter(adapter);
//
//                }
//                else {
//
//
//                    Toast.makeText(getActivity(),"No Text Entered",Toast.LENGTH_SHORT).show();
//                }
//                dialog.dismiss();
//
//
//            }
//        });
//        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //pass
//            }
//        });
//        AlertDialog b = dialogBuilder.create();
//        b.show();
//

    }

    public void addNewBoard(final String board)
    {
        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST,ADD_WORK_BOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        getWorkBoards();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(getActivity().getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
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

                params.put("user_id", userId);
                 params.put("board_name",board );
                 params.put("project_id",projectid );
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

    public  void getWorkBoards()
    {

        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_WORK_BOARD_TO_MEMBERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();


                        myList = new ArrayList<>() ;
                        if(response.equals("{\"nodata\":0}"))
                        {
                            Toast.makeText(getActivity(), "Workboard is empty", Toast.LENGTH_SHORT).show();
                        }
                        else {


                            try {
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    ProjectsPojo projectsPojo = new ProjectsPojo();

                                    projectsPojo.setBoardID(jsonObject.getString("id"));
                                    projectsPojo.setData(jsonObject.getString("board_name"));
                                    projectsPojo.setId(jsonObject.getString("project_id"));
                                    projectsPojo.setBoardStar(jsonObject.getString("board_star"));
                                    projectsPojo.setIsFavouriteFromMembers(jsonObject.getString("is_favourite"));

                                    myList.add(projectsPojo);

                                }

                                adapter = new BoardsAdapter(getActivity(), myList);
                                lvWBoard.setAdapter(adapter);

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


                    Toast.makeText(getActivity().getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
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

                params.put("p_id", projectid);
                params.put("uid",userId );
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
