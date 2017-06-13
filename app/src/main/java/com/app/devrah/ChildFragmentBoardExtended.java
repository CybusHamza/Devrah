package com.app.devrah;

import android.content.Context;
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

import com.app.devrah.Adapters.FragmentBoardsAdapter;
import com.app.devrah.Adapters.ProjectsAdapter;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.id.empty;


public class ChildFragmentBoardExtended extends Fragment {
       private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
        View view;
    TextView tvAddCard;
    String childname;

    FragmentBoardsAdapter adapter;
    List<ProjectsPojo> listPojo;
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
        view =inflater.inflate(R.layout.fragment_child_fragment_board_extended, container, false);
        View emV = inflater.inflate(R.layout.empty_list_bg,null);

     //   View emptyView = inflater.inflate(R.layout.empty_list_view,container,false);
        TextView tvName = (TextView)view.findViewById(R.id.textName);

       // boardsFragmentPojoData = new ProjectsPojo();
        listPojo = new ArrayList<>();
        tvAddCard = (TextView)view.findViewById(R.id.addCard);
        Bundle bundle = getArguments();
        childname = bundle.getString("data");
        tvName.setText(childname);


        lv = (ListView)view.findViewById(R.id.boardFragmentListView);
        lv.setEmptyView(emV);

       // lv.setEmptyView(view.findViewById(R.id.empty));

        tvAddCard.setOnClickListener(new View.OnClickListener() {
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


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    public void  showDialog(){



        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = this.getActivity().
                getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialogbox_layout, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.get_data_alertBox);

        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
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

                dialog.dismiss();


            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();


    }


















}
