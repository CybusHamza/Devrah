package com.app.devrah.Fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.BoardExtended;
import com.app.devrah.Views.ParentBoardExtendedFragment;
import com.app.devrah.pojo.CardAssociatedLabelsPojo;
import com.app.devrah.pojo.CardAssociatedMembersPojo;
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
import static com.app.devrah.Network.End_Points.DELETE_LIST;
import static com.app.devrah.Network.End_Points.GET_CARDS_FOR_LIST;
import static com.app.devrah.Network.End_Points.SAVE_CARD_BY_LIST_ID;
import static com.app.devrah.Network.End_Points.UPDATE_COLOR_BG_LIST;


public class ChildFragmentBoardExtended extends Fragment {

    Button noColor;
    Button blueColor;
    Button brownColor;
    Button grayColor ;
    Button greenColor;
    Button orangeColor;
    Button purpleColor;
    Button yellowColor;
    Button redColor ;

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
    List<CardAssociatedMembersPojo> cardMembersPojoList;
  //  CardAssociatedLabelsAdapter cardAssociatedLabelsAdapter;
    CustomViewPagerAdapter VPadapter;
    ProjectsPojo boardsFragmentPojoData;
    CardAssociatedLabelsPojo labelsPojo;
    ListView lv;
    RecyclerView cardAssociatedLabelRecycler;

    RelativeLayout relativeLayout;

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    ProgressDialog ringProgressDialog;

    String id,p_id,b_id,list_id,list_color;
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
        list_color=bundle.getString("list_color");
        String check=bundle.getString("listName");
        row=0;

        tvName.setText(childname);


        getCardList(list_id);
        if(check=="") {
            if (list_color.equals("") || list_color == null) {
                tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorWhite));
            } else if (list_color.equals("00A2E8")) {
                tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.blue));
            } else if (list_color.equals("B97A57")) {
                tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.brown));
            } else if (list_color.equals("B5E61D")) {
                tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
            } else if (list_color.equals("FF7F27")) {
                tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.orange));
            } else if (list_color.equals("A349A4")) {
                tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPurple));
            } else if (list_color.equals("f2d600")) {
                tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorYellow));
            } else if (list_color.equals("eb5a46")) {
                tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorRed));
            } else if (list_color.equals("C3C3C3")) {
                tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
            }
        }
        lv = (ListView) view.findViewById(R.id.boardFragmentListView);
        //relativeLayout=(RelativeLayout)findViewById(R.id.layoutTestRecycleView);
//        cardAssociatedLabelRecycler=(RecyclerView)view.findViewById(R.id.labelsListView);

        boardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), boardMenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.list_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getActivity(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()) {
                            case R.id.addNewCard:
                                boardMenu.setBackgroundColor(getActivity().getResources().getColor(R.color.lightGreen));
                                // TODO Something when menu item selected
                                return true;

                            case R.id.changeListColor:
                                // TODO Something when menu item selected
                                changeListColorPopup();
                                return true;

                            case R.id.copyList:
                                // TODO Something when menu item selected
                                return true;
                            case R.id.moveList:
                                // TODO Something when menu item selected
                                return true;
                            case R.id.deleteList:
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Error!")
                                        .setCancelText("Cancel")
                                        .setConfirmText("OK").setContentText("Are You sure you want to Remove this list")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                deleteList();
                                                sDialog.dismiss();
                                            }
                                        })
                                        .showCancelButton(true)
                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                            }
                                        })
                                        .show();

                                // TODO Something when menu item selected
                                return true;

                        }
                        return true;
                    }
                });
                popup.show();
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

    private void changeListColorPopup() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_alert_dialogbox_for_bg_color_list,null);
        final AlertDialog alertDialog = new  AlertDialog.Builder(getContext()).create();

        alertDialog.setCancelable(false);
         noColor = (Button)customView.findViewById(R.id.noColor);
         blueColor = (Button)customView.findViewById(R.id.blueColor);
         brownColor = (Button)customView.findViewById(R.id.brownColor);
         grayColor = (Button)customView.findViewById(R.id.grayColor);
         greenColor = (Button)customView.findViewById(R.id.greenColor);
         orangeColor = (Button)customView.findViewById(R.id.orangeColor);
         purpleColor = (Button)customView.findViewById(R.id.purpleColor);
         yellowColor = (Button)customView.findViewById(R.id.yellowColor);
         redColor = (Button)customView.findViewById(R.id.redColor);

        noColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                changeListBgColor("");
            }
        });
        blueColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
               // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.blue));
                changeListBgColor("00A2E8");
            }
        });
        brownColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.brown));
                changeListBgColor("B97A57");
            }
        });
        grayColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
               // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.light_black));
                changeListBgColor("B97A57");
            }
        });
        greenColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                changeListBgColor("B5E61D");
            }
        });
        orangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
               // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorOrange));
                changeListBgColor("FF7F27");
            }
        });
        purpleColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
              //  tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPurple));
                changeListBgColor("A349A4");
            }
        });
        yellowColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
               // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorYellow));
                changeListBgColor("f2d600");
            }
        });
        redColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
               // tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorRed));
                changeListBgColor("eb5a46");
            }
        });

        alertDialog.setView(customView);
        alertDialog.show();
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

        alertDialog.setCancelable(false);
        edt = (EditText)customView.findViewById(R.id.input_watever);
        TextView addCard = (TextView)customView.findViewById(R.id.btn_add_board);
        addCard.setText("Add Card");
        final TextView cancelbtn = (TextView) customView.findViewById(R.id.btn_cancel);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String projectData = edt.getText().toString();
                String  boardsFragmentData = edt.getText().toString();

                if (!(boardsFragmentData.equals(""))) {
                   /* boardsFragmentPojoData = new ProjectsPojo();
                    boardsFragmentPojoData.setData(boardsFragmentData);
                    listPojo.add(boardsFragmentPojoData);
                    adapter = new FragmentBoardsAdapter(getActivity(), listPojo);


                    lv.setAdapter(adapter);*/
                   row++;
                   saveCardByListId(boardsFragmentData,row);
                    alertDialog.dismiss();
                }
                else {
                    Toast.makeText(getActivity(),"Please Enter Card Name",Toast.LENGTH_SHORT).show();
                }




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
                            cardMembersPojoList = new ArrayList<>();

                            try {
                                ProjectsPojo projectsPojo = null;
                                JSONObject mainObject=new JSONObject(response);
                                JSONArray jsonArrayCards = mainObject.getJSONArray("cards");
                                JSONArray jsonArrayLabels = mainObject.getJSONArray("labels");
                                JSONArray jsonArrayMembers = mainObject.getJSONArray("members");
                                JSONArray jsonArrayAttachments = mainObject.getJSONArray("attachments");
                                //JSONObject cardsObject = jsonArray.getJSONObject(0);

                                row=jsonArrayCards.length();
                                for (int i = 0; i < jsonArrayCards.length(); i++) {
                                    JSONObject jsonObject = jsonArrayCards.getJSONObject(i);
                                    JSONArray jsonArray=jsonArrayAttachments.getJSONArray(i);
                                    //JSONObject jsonObject1 = jsonArrayAttachments.getJSONObject(i);

                                     projectsPojo = new ProjectsPojo();
                                 // CardAssociatedLabelsPojo labelsPojo = new CardAssociatedLabelsPojo();

                                    projectsPojo.setId(jsonObject.getString("id"));
                                    projectsPojo.setData(jsonObject.getString("card_name"));
                                    projectsPojo.setAttachment(jsonObject.getString("file_name"));
                                    projectsPojo.setDueDate(jsonObject.getString("card_start_date"));
                                    projectsPojo.setnOfAttachments(String.valueOf(jsonArray.length()));
                                   // projectsPojo.setBoardAssociatedLabelsId(jsonObject.getString("board_assoc_label_id"));
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

                                for(int j=0;j<jsonArrayMembers.length();j++){
                                    CardAssociatedMembersPojo membersPojo = new CardAssociatedMembersPojo();
                                    JSONArray jsonArray=jsonArrayMembers.getJSONArray(j);
                                    String[] members = new String[jsonArray.length()];
                                    String[] labelText = new String[jsonArray.length()];
                                    for (int k=0;k<jsonArray.length();k++){

                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        members[k]=jsonObject.getString("profile_pic");
                                        labelText[k]=jsonObject.getString("initials");
                                       /* if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }*/
                                    }
                                    membersPojo.setMembers(members);
                                    membersPojo.setInitials(labelText);
                                  //  labelsPojo.setLabelText(labelText);
                                    cardMembersPojoList.add(membersPojo);
                                }

                                adapter = new FragmentBoardsAdapter(getActivity(), listPojo,cardLabelsPojoList,cardMembersPojoList,0);
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
    public void deleteList() {
        ringProgressDialog = ProgressDialog.show(getContext(), "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        final SharedPreferences pref = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, DELETE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();

                        ParentBoardExtendedFragment.removeSpecificPage(ParentBoardExtendedFragment.getCurrentPosition(),"delete");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
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
                params.put("boardId", BoardExtended.boardId);
                params.put("projectId", BoardExtended.projectId);
                params.put("listId", list_id);
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
    public void changeListBgColor(final String bgColor) {
        ringProgressDialog = ProgressDialog.show(getContext(), "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        final SharedPreferences pref = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_COLOR_BG_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        if(bgColor.equals("")) {
                            tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorWhite));
                        }else if (bgColor.equals("00A2E8")){
                            tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.blue));
                        }else if (bgColor.equals("B97A57")){
                            tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.brown));
                        }else if (bgColor.equals("B5E61D")){
                            tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                        }else if (bgColor.equals("FF7F27")){
                            tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.orange));
                        }else if (bgColor.equals("A349A4")){
                            tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPurple));
                        }else if (bgColor.equals("f2d600")){
                            tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorYellow));
                        }else if (bgColor.equals("eb5a46")){
                            tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.colorRed));
                        }else if (bgColor.equals("C3C3C3")){
                            tvName.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
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
                params.put("board_id", BoardExtended.boardId);
                params.put("prjct_id", BoardExtended.projectId);
                params.put("list_id", list_id);
                params.put("bgcolor", bgColor);
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
