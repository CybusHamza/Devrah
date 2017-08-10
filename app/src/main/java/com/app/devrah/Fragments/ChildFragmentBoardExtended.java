package com.app.devrah.Fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.app.devrah.pojo.CardAssociatedLabelsPojo;
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
import static com.app.devrah.Network.End_Points.GET_CARDS_FOR_LIST;
import static com.app.devrah.Network.End_Points.GET_CARD_ASSOC_LABELS;
import static com.app.devrah.Network.End_Points.SAVE_CARD_BY_LIST_ID;


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
    List<ProjectsPojo> listPojo,labelsPojoList;
    List<CardAssociatedLabelsPojo> cardLabelsPojoList;
  //  CardAssociatedLabelsAdapter cardAssociatedLabelsAdapter;
    CustomViewPagerAdapter VPadapter;
    ProjectsPojo boardsFragmentPojoData;
    CardAssociatedLabelsPojo labelsPojo;
    ListView lv;
    RecyclerView cardAssociatedLabelRecycler;

    RelativeLayout relativeLayout;

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    ProgressDialog ringProgressDialog;

    String id,p_id,b_id,list_id;
    int row;


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

//        cardAssociatedLabelRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));


         tvName = (TextView)view.findViewById(R.id.textName);
            boardMenu = (ImageView)view.findViewById(R.id.menuBoards);




        tvAddCard = (TextView)view.findViewById(R.id.addCard);
        Bundle bundle = this.getArguments();
        childname = bundle.getString("data");
        p_id = bundle.getString("p_id");
        b_id = bundle.getString("b_id");
        list_id = bundle.getString("list_id");
        row=0;

        tvName.setText(childname);

        getCardList(list_id);

        lv = (ListView) view.findViewById(R.id.boardFragmentListView);
        //relativeLayout=(RelativeLayout)findViewById(R.id.layoutTestRecycleView);
//        cardAssociatedLabelRecycler=(RecyclerView)view.findViewById(R.id.labelsListView);

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
                   /* boardsFragmentPojoData = new ProjectsPojo();
                    boardsFragmentPojoData.setData(boardsFragmentData);
                    listPojo.add(boardsFragmentPojoData);
                    adapter = new FragmentBoardsAdapter(getActivity(), listPojo);


                    lv.setAdapter(adapter);*/
                   row++;
                   saveCardByListId(boardsFragmentData,row);
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


    public void getCardList(final String lsitId) {
        final SharedPreferences pref = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, GET_CARDS_FOR_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if (!(response.equals("false"))) {

                            listPojo = new ArrayList<>();
                            labelsPojoList = new ArrayList<>();
                            cardLabelsPojoList = new ArrayList<>();

                            try {
                                ProjectsPojo projectsPojo = null;
                                JSONObject mainObject=new JSONObject(response);
                                JSONArray jsonArrayCards = mainObject.getJSONArray("cards");
                                JSONArray jsonArrayLabels = mainObject.getJSONArray("labels");
                                //JSONObject cardsObject = jsonArray.getJSONObject(0);

                                row=jsonArrayCards.length();
                                for (int i = 0; i < jsonArrayCards.length(); i++) {
                                    JSONObject jsonObject = jsonArrayCards.getJSONObject(i);

                                     projectsPojo = new ProjectsPojo();
                                 // CardAssociatedLabelsPojo labelsPojo = new CardAssociatedLabelsPojo();

                                    projectsPojo.setId(jsonObject.getString("id"));
                                    projectsPojo.setData(jsonObject.getString("card_name"));
                                    projectsPojo.setAttachment(jsonObject.getString("file_name"));
                                    projectsPojo.setDueDate(jsonObject.getString("card_start_date"));
                                    projectsPojo.setBoardAssociatedLabelsId(jsonObject.getString("board_assoc_label_id"));
                                    //projectsPojo.setLabels(jsonObject.getString("label_color"));

//                                    cardLabelsPojoList.add(labelsPojo);

                                    listPojo.add(projectsPojo);
                                   // getLabelsList(jsonObject.getString("id"));

                                }
                                for(int j=0;j<jsonArrayLabels.length();j++){
                                    CardAssociatedLabelsPojo labelsPojo = new CardAssociatedLabelsPojo();
                                    JSONArray jsonArray=jsonArrayLabels.getJSONArray(j);
                                    String[] labels = new String[jsonArray.length()];
                                    String[] labelText = new String[jsonArray.length()];
                                    for (int k=0;k<jsonArray.length();k++){

                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        labels[k]=jsonObject.getString("label_color");
                                        if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }
                                    }
                                    labelsPojo.setLabels(labels);
                                    labelsPojo.setLabelText(labelText);
                                    cardLabelsPojoList.add(labelsPojo);
                                }
                                adapter = new FragmentBoardsAdapter(getActivity(), listPojo,cardLabelsPojoList);
                                lv.setAdapter(adapter);
                                /*try {
                                    cardAssociatedLabelsAdapter = new CardAssociatedLabelsAdapter(getActivity(), cardLabelsPojoList);
                                    cardAssociatedLabelRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
                                    cardAssociatedLabelRecycler.setAdapter(cardAssociatedLabelsAdapter);
                                }catch (Exception e){
                                    String s=e.toString();
                                }*/
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
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    getActivity().finish();
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(getActivity(), "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    getActivity().finish();
                                }
                            })
                            .show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", BoardExtended.boardId);
                params.put("project_id", BoardExtended.projectId);
                // params.put("userId", pref.getString("user_id",""));
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
    public void saveCardByListId(final String cardName, final int row) {
        ringProgressDialog = ProgressDialog.show(getContext(), "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        final SharedPreferences pref = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, SAVE_CARD_BY_LIST_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    String[] labels=new String[0];
                        ringProgressDialog.dismiss();
                       /* if (!(response.equals("false"))) {
                            boardsFragmentPojoData = new ProjectsPojo();
                            labelsPojo = new CardAssociatedLabelsPojo();
                            boardsFragmentPojoData.setId(response.trim().toString());
                            boardsFragmentPojoData.setData(cardName);
                            labelsPojo.setLabels(labels);
                            listPojo.add(boardsFragmentPojoData);
                            cardLabelsPojoList.add(labelsPojo);
                            adapter = new FragmentBoardsAdapter(getActivity(), listPojo,cardLabelsPojoList);

                            adapter.notifyDataSetChanged();
                            lv.setAdapter(adapter);


                        }*/getCardList(list_id);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    getActivity().finish();
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(getActivity(), "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    getActivity().finish();
                                }
                            })
                            .show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", BoardExtended.boardId);
                params.put("prjct_id", BoardExtended.projectId);
                params.put("userId", pref.getString("user_id",""));
                params.put("list_id", list_id);
                params.put("name", cardName);
                params.put("row", String.valueOf(row));
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

/*public void getLabelsList(final String cardId){
    StringRequest request = new StringRequest(Request.Method.POST, GET_CARD_ASSOC_LABELS,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    if (!(response.equals("false"))) {


                        cardLabelsPojoList = new ArrayList<>();

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                           // row=jsonArray.length();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                CardAssociatedLabelsPojo labelsPojo = new CardAssociatedLabelsPojo();

                                labelsPojo.setLabel(jsonObject.getString("label_color"));
                                cardLabelsPojoList.add(labelsPojo);


                            }

                                cardAssociatedLabelsAdapter=new CardAssociatedLabelsAdapter(getActivity(),cardLabelsPojoList);
                                cardAssociatedLabelRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
                                cardAssociatedLabelRecycler.setAdapter(cardAssociatedLabelsAdapter);

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
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error!")
                        .setConfirmText("OK").setContentText("No Internet Connection")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                getActivity().finish();
                            }
                        })
                        .show();
            } else if (error instanceof TimeoutError) {


                Toast.makeText(getActivity(), "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error!")
                        .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                getActivity().finish();
                            }
                        })
                        .show();
            }
        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

            Map<String, String> params = new HashMap<>();
            params.put("board_id", BoardExtended.boardId);
           *//* params.put("project_id", BoardExtended.projectId);
            // params.put("userId", pref.getString("user_id",""));
            params.put("list_id", lsitId);*//*
            return params;
        }
    };

    request.setRetryPolicy(new DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
    requestQueue.add(request);
}*/




}
