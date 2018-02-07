package com.app.devrah.Views.Board;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
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
import com.app.devrah.Views.Main.ProfileActivity;
import com.app.devrah.pojo.ProjectsPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.app.devrah.Network.End_Points.ADD_WORK_BOARD;
import static com.app.devrah.Network.End_Points.UPDATE_BG_BOARD;
import static com.app.devrah.Network.End_Points.UPDATE_BG_BOARD_IMG;


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
    EditText edtSeach;
    Intent intent;
    String mCurrentPhotoPath,ImageDecode;

    private static int IMG_RESULT = 2;
    String b64;
    Calendar calendar;
    String dateAndTime;
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
                        edtSeach.setText("");
                        try {
                            getWorkBoards();
                        }catch (OutOfMemoryError error){
                            error.printStackTrace();
                        }

                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        etSearch.setVisibility(View.INVISIBLE);
        addBoard.setOnClickListener(this);
        Bundle bundle = this.getArguments();
        projectid = bundle.getString("pid");

        search.setOnClickListener(this);
        edtSeach = (EditText) getActivity().findViewById(R.id.edtSearch);

        edtSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String nameToSearch = edtSeach.getText().toString();
                ArrayList<ProjectsPojo> filteredLeaves = new ArrayList<ProjectsPojo>();

                for (ProjectsPojo data : myList) {
                    if (data.getData().toLowerCase().contains(nameToSearch.toLowerCase())) {
                        filteredLeaves.add(data);
                    }


                }
                if(filteredLeaves.size()<1){
                    ProjectsPojo projectsPojo = new ProjectsPojo();

                    projectsPojo.setBoardID("");
                    projectsPojo.setData("No Board found");
                    projectsPojo.setId("");
                    projectsPojo.setBoardStar("");
                    projectsPojo.setIsFavouriteFromMembers("");

                    filteredLeaves.add(projectsPojo);
                }
                adapter = new BoardsAdapter(getActivity(), filteredLeaves);
                lvWBoard.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        try {
            getWorkBoards();
        }catch (OutOfMemoryError error){
            error.printStackTrace();
        }
       /* etSearch.addTextChangedListener(new TextWatcher() {
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
        });*/






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
        showKeyBoard(edt);
        final TextView addCard = (TextView)customView.findViewById(R.id.btn_add_board);
        final TextView addBoardAndMore = (TextView)customView.findViewById(R.id.btn_add_board1);
        addBoardAndMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String projectData = edt.getText().toString();
                if (!(projectData.isEmpty()) && !projectData.trim().isEmpty()) {

                    addNewBoard(projectData);
                    edt.setText("");
/*
                    ProjectsPojo projectPojoData = new ProjectsPojo();
                    projectPojoData.setData(projectData);
                    myList.add(projectPojoData);
                    adapter = new BoardsAdapter(getActivity(), myList);


                    lvWBoard.setAdapter(adapter);*/

                }
                else {
                    Toast.makeText(getActivity(),"Please Enter Board Name",Toast.LENGTH_SHORT).show();
                }
            }
        });
        final TextView cancel = (TextView)customView.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(edt);
                alertDialog.dismiss();
            }
        });
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String projectData = edt.getText().toString();
                if (!(projectData.isEmpty()) && !projectData.trim().isEmpty()) {

                    addNewBoard(projectData);

/*
                    ProjectsPojo projectPojoData = new ProjectsPojo();
                    projectPojoData.setData(projectData);
                    myList.add(projectPojoData);
                    adapter = new BoardsAdapter(getActivity(), myList);


                    lvWBoard.setAdapter(adapter);*/
                    hideKeyBoard(edt);
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
    private void showKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
    private void hideKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(title.getWindowToken(), 0);
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

    public void upload(String boardId, String projectId, int position) {
        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("bid",boardId);
        intent.putExtra("pos",position);
        startActivityForResult(intent, IMG_RESULT);

    }


    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
    public void LoadImage(final String b64, final String boardid, final int position){
        calendar = Calendar.getInstance();
        dateAndTime=String.valueOf(calendar.get(Calendar.DATE))
                +String.valueOf(calendar.get(Calendar.MONTH))
                + String.valueOf(calendar.get(Calendar.YEAR))
                + String.valueOf(calendar.get(Calendar.HOUR))
                + String.valueOf(calendar.get(Calendar.MINUTE))
                + String.valueOf(calendar.get(Calendar.SECOND));
        final ProgressDialog  ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Updating...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.BASE_URL_FILE_UPLOAD+"upload_cover_board.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                ringProgressDialog.dismiss();
                // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                if (!(response.equals(""))) {
                    //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                   uploadData(response.trim(),boardid,position);
                } else {
                    Toast.makeText(getActivity(), "Picture not uploaded", Toast.LENGTH_SHORT).show();
                }
            }

        }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "check your internet connection";
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }else if (error instanceof TimeoutError) {

                    Toast.makeText(getActivity(),"Time Out error",Toast.LENGTH_SHORT).show();
                }
            }


        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("image", b64);
                map.put("name", dateAndTime);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);





    }

    private void uploadData(final String trim, final String boardid, final int position) {
        final ProgressDialog  ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Updating...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        final SharedPreferences pref = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_BG_BOARD_IMG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        if (!response.equals("")) {
                            //   holder.background.setBackground(activity.getResources().getDrawable(R.drawable.outer_border_message_screen));
                          myList.get(position).setBackGroundPicture(trim);
                          myList.get(position).setIsPicture("1");
                          adapter.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {


                    Toast.makeText(getActivity(), "check your internet connection", Toast.LENGTH_SHORT).show();
                    /*new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    getActivity().finish();
                                }
                            })
                            .show();*/
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(getActivity(), "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                   /* new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    getActivity().finish();
                                }
                            })
                            .show();*/
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", boardid);
                params.put("projectId", projectid);
                params.put("userId", pref.getString("user_id",""));
                params.put("bg_file", trim);
                params.put("is_picture", "1");
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                            //edtSeach.setText("");

                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                if(jsonArray.length()<1){
                                    ProjectsPojo projectsPojo = new ProjectsPojo();

                                    projectsPojo.setBoardID("");
                                    projectsPojo.setData("No Board found");
                                    projectsPojo.setId("");
                                    projectsPojo.setBoardStar("");
                                    projectsPojo.setIsFavouriteFromMembers("");
                                    projectsPojo.setBackGroundPicture("");
                                    projectsPojo.setIsPicture("");

                                    myList.add(projectsPojo);
                                }else {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        ProjectsPojo projectsPojo = new ProjectsPojo();

                                        projectsPojo.setBoardID(jsonObject.getString("id"));
                                        projectsPojo.setData(jsonObject.getString("board_name"));
                                        projectsPojo.setId(jsonObject.getString("project_id"));
                                        projectsPojo.setBoardStar(jsonObject.getString("board_star"));
                                        projectsPojo.setIsFavouriteFromMembers(jsonObject.getString("is_favourite"));
                                        projectsPojo.setBackGroundPicture(jsonObject.getString("background_picture"));
                                        projectsPojo.setIsPicture(jsonObject.getString("is_picture"));
                                        myList.add(projectsPojo);

                                    }
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
