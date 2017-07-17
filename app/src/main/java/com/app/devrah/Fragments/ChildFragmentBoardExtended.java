package com.app.devrah.Fragments;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
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




       // boardsFragmentPojoData = new ProjectsPojo();
        listPojo = new ArrayList<>();
        tvAddCard = (TextView)view.findViewById(R.id.addCard);
        Bundle bundle = getArguments();
        childname = bundle.getString("data");
        tvName.setText(childname);


        lv = (ListView) view.findViewById(R.id.boardFragmentListView);

//
//        lv.setDragListListener(new DragListView.DragListListener() {
//            @Override
//            public void onItemDragStarted(int position) {
//                Toast.makeText(getActivity(), "Start - position: " + position, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onItemDragging(int itemPosition, float x, float y) {
//
//            }
//
//            @Override
//            public void onItemDragEnded(int fromPosition, int toPosition) {
//                if (fromPosition != toPosition) {
//                    Toast.makeText(getActivity(), "End - position: " + toPosition, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


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
                alertDialog.dismiss();






            }
        });

        alertDialog.setView(customView);
        alertDialog.show();



    }


//
//    public void  showDialog(){
//
//
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());
//        LayoutInflater inflater = this.getActivity().
//                getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.alert_dialogbox_layout, null);
//        dialogBuilder.setView(dialogView);
//
//        final EditText edt = (EditText) dialogView.findViewById(R.id.get_data_alertBox);
//
//        dialogBuilder.setTitle("Custom dialog");
//        dialogBuilder.setMessage("Enter text below");
//        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //do something with edt.getText().toString();
//              String  boardsFragmentData = edt.getText().toString();
//
//                if (!(boardsFragmentData.isEmpty())) {
//                    boardsFragmentPojoData = new ProjectsPojo();
//                    boardsFragmentPojoData.setData(boardsFragmentData);
//                    listPojo.add(boardsFragmentPojoData);
//                    adapter = new FragmentBoardsAdapter(getActivity(), listPojo);
//
//
//                    lv.setAdapter(adapter);
//                }
//                else {
//                    Toast.makeText(getActivity(),"No Text Entered",Toast.LENGTH_SHORT).show();
//                }
//
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
//
//    }
//

















}
