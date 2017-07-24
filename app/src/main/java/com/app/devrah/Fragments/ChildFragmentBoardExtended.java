package com.app.devrah.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.ActionMenuView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.devrah.Adapters.BoardsAdapter;
import com.app.devrah.Adapters.CustomViewPagerAdapter;
import com.app.devrah.Adapters.FragmentBoardsAdapter;
import com.app.devrah.R;
import com.app.devrah.pojo.ProjectsPojo;


import java.util.ArrayList;
import java.util.List;


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


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChildFragmentBoardExtended() {
        // Required empty public constructor
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
      //  adapter = new FragmentBoardsAdapter();
        view =inflater.inflate(R.layout.fragment_child_fragment_board_extended, container, false);
        View emV = inflater.inflate(R.layout.empty_list_bg,null);


      //  VPadapter = new CustomViewPagerAdapter(getFragmentManager(),getContext());
     //   View emptyView = inflater.inflate(R.layout.empty_list_view,container,false);
         tvName = (TextView)view.findViewById(R.id.textName);
            boardMenu = (ImageView)view.findViewById(R.id.menuBoards);



       // boardsFragmentPojoData = new ProjectsPojo();
        listPojo = new ArrayList<>();
        tvAddCard = (TextView)view.findViewById(R.id.addCard);
        Bundle bundle = getArguments();
        childname = bundle.getString("data");
        tvName.setText(childname);


        lv = (ListView) view.findViewById(R.id.boardFragmentListView);
        boardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Start - position: " , Toast.LENGTH_SHORT).show();

                View popupView =getActivity().getLayoutInflater().inflate(R.layout.ui_for_popup_view, null);
                PopupWindow popupWindow = new PopupWindow(popupView, ActionMenuView.LayoutParams.WRAP_CONTENT, ActionMenuView.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);



            }
        });


        lv.setEmptyView(emV);

       // lv.setEmptyView(view.findViewById(R.id.empty));

        tvAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCustomDialog();

            }
        });




        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.popup_edit_delete, menu);

        int positionOfMenuItem = 0; // or whatever...
        MenuItem item = menu.getItem(positionOfMenuItem);
        SpannableString s = new SpannableString("My red MenuItem");
        s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
        item.setTitle(s);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
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




















}
