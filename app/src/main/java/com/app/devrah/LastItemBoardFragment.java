package com.app.devrah;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.devrah.Adapters.CustomViewPagerAdapter;
import com.app.devrah.Adapters.ProjectsAdapter;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.List;


public class LastItemBoardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public TextView tvAddPage;
    EditText edt;
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

        adapter = new CustomViewPagerAdapter(getFragmentManager());
        tvAddPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
showDialog();
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

    public void showDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.custom_alert_dialogbox, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        edt = (EditText) customView.findViewById(R.id.input_watever);

        final TextView addCard = (TextView) customView.findViewById(R.id.btn_add_board);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // CustomViewPagerAdapter.mFragmentList.remove(CustomViewPagerAdapter.customCount()-1);

                adapter.removeFragAt(adapter.getCount()-1);
                adapter.notifyDataSetChanged();
                //  fragment = (ParentBoardExtendedFragment)this.getSupportFragmentManager().findFragmentById(R.id.ajeebFrag);
//
//                fragment = new ParentBoardExtendedFragment().
//                LastItemBoardFragment.this.getFragmentManager()
//                        .findFragmentById(R.id.ajeebFrag);

//fragment = LastItemBoardFragment.this.getFragmentManager().findFragmentById()

//                fragment = (ParentBoardExtendedFragment) new ParentBoardExtendedFragment().getFragmentManager().findFragmentById(R.id.ajeebFrag);
//
//                String projectData = edt.getText().toString();
//                fragment.addPage(projectData);
//
//                fragment.addPageAt(CustomViewPagerAdapter.customCount());
                String projectData = edt.getText().toString();
            //    CustomViewPagerAdapter.mFragmentList.remove(CustomViewPagerAdapter.customCount() -1);


                ParentBoardExtendedFragment.addPageAt(projectData,adapter.getCount());
                adapter.notifyDataSetChanged();
                ParentBoardExtendedFragment.addPageAt(CustomViewPagerAdapter.customCount());
             //   ParentBoardExtendedFragment.addPageAt(CustomViewPagerAdapter.customCount());
                alertDialog.dismiss();


            }
        });


        alertDialog.setView(customView);
        alertDialog.show();


    }
}