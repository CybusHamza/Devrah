package com.app.devrah.Views.BoardExtended;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LastItemBoardFragment extends Fragment {

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    ProgressDialog ringProgressDialog;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String p_id;
    String b_id;
    public TextView tvAddPage;
    EditText edt;
    ImageView addList;
    CustomViewPagerAdapter adapter ;
    ParentBoardExtendedFragment fragment;
    List<ProjectsPojo> listPojo;
//    ProjectsAdapter adapter;
    ProjectsPojo projectData;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LastItemBoardFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LastItemBoardFragment newInstance(String param1, String param2) {
        LastItemBoardFragment fragment = new LastItemBoardFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_last_item_board, container, false);
        tvAddPage = (TextView) view.findViewById(R.id.tvAddPage);
        Bundle bundle = getArguments();
        p_id = bundle.getString("p_id");
        b_id = bundle.getString("b_id");
        adapter = new CustomViewPagerAdapter(getFragmentManager());
    //    addList= (ImageView) getActivity().findViewById(R.id.btnAddFrag);
        tvAddPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showDialog();
            }
        });
       /* addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
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

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void showKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }
    }
    private void hideKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(title.getWindowToken(), 0);
        }
    }
    public void showDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View customView = layoutInflater.inflate(R.layout.custom_alert_dialog_add_list, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setCancelable(false);
        edt = (EditText) customView.findViewById(R.id.input_watever);
        showKeyBoard(edt);
        final TextView addCard = (TextView) customView.findViewById(R.id.btn_add_board);
       // final TextView addMoreList = (TextView) customView.findViewById(R.id.btn_add_board1);
        final TextView cancelbtn = (TextView) customView.findViewById(R.id.btn_cancel);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(edt);
                alertDialog.dismiss();
            }
        });
   /* addMoreList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String projectData = edt.getText().toString();
            if(!projectData.equals("")) {
                // adapter.removeFragAt(adapter.getCount()-1);
                // adapter.notifyDataSetChanged();
                addNewList(projectData, String.valueOf(adapter.getCount() - 1),"more");
                hideKeyBoard(edt);
                alertDialog.dismiss();
                //    CustomViewPagerAdapter.mFragmentList.remove(CustomViewPagerAdapter.customCount() -1);
            }else {
                Toast.makeText(getActivity(),"Please Enter List Name",Toast.LENGTH_SHORT).show();

            }
        }
    });*/
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // CustomViewPagerAdapter.mFragmentList.remove(CustomViewPagerAdapter.customCount()-1);


                //  fragment = (ParentBoardExtendedFragment)this.getSupportFragmentManager().findFragmentById(R.id.ajeebFrag);
//
//                fragment = new ParentBoardExtendedFragment().
//                LastItemBoardFragment.this.getFragmentManager()
//                        .findFragmentById(R.id.ajeebFrag);
//                 fragment = LastItemBoardFragment.this.getFragmentManager().findFragmentById()

//                fragment = (ParentBoardExtendedFragment) new ParentBoardExtendedFragment().getFragmentManager().findFragmentById(R.id.ajeebFrag);
//
//                String projectData = title.getText().toString();
//                fragment.addPage(projectData);
//
//                fragment.addPageAt(CustomViewPagerAdapter.customCount());
                String projectData = edt.getText().toString();
                if(!projectData.equals("")) {
                   // adapter.removeFragAt(adapter.getCount()-1);
                    // adapter.notifyDataSetChanged();
                    addNewList(projectData, String.valueOf(adapter.getCount() - 1),"");
                    //    CustomViewPagerAdapter.mFragmentList.remove(CustomViewPagerAdapter.customCount() -1);
                    hideKeyBoard(edt);
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getActivity(),"Please Enter List Name",Toast.LENGTH_SHORT).show();

                }



            }
        });


        alertDialog.setView(customView);
        alertDialog.show();


    }
    public void addNewList(final String projectData, final String position,final String more){
        ringProgressDialog = ProgressDialog.show(getContext(), "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SAVE_NEW_LIST_BY_BOARD_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if(!response.equals("0")) {
                            adapter.removeFragAt(adapter.getCount()-1);
                            adapter.notifyDataSetChanged();
                            adapter = new CustomViewPagerAdapter(getFragmentManager());
                            ParentBoardExtendedFragment.addPageAt(projectData, adapter.getCount(), BoardExtended.projectId, BoardExtended.boardId, response.trim().toString(), "new list");
                            adapter.notifyDataSetChanged();
                            ParentBoardExtendedFragment.addPageAt(CustomViewPagerAdapter.customCount());
                        }
                        //   ParentBoardExtendedFragment.addPageAt(CustomViewPagerAdapter.customCount());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "check your internet connection", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getActivity(), "TimeOut eRROR", Toast.LENGTH_SHORT).show();

                  /*  new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
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
                params.put("prjct_id", BoardExtended.projectId);
                params.put("board_id", BoardExtended.boardId);
                params.put("name",projectData);
                params.put("column_order",position);
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