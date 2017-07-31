package com.app.devrah.Views;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.app.devrah.Adapters.BoardsAdapter;
import com.app.devrah.R;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReferenceBoard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReferenceBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReferenceBoard extends Fragment implements View.OnClickListener{



    View view;
    ListView lv;
    Button btnAddWBoard;
    List<ProjectsPojo> myList;
    BoardsAdapter adapter;
    ImageView search;
    EditText etSearch;
    TextView tvReferenceBoard;



    EditText edt;






    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ReferenceBoard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReferenceBoard.
     */
    // TODO: Rename and change types and number of parameters
    public static ReferenceBoard newInstance(String param1, String param2) {
        ReferenceBoard fragment = new ReferenceBoard();
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
        view = inflater.inflate(R.layout.fragment_reference_board, container, false);

        lv = (ListView)view.findViewById(R.id.lvRBoard);
        btnAddWBoard = (Button)view.findViewById(R.id.buttonAddRBoard);
        myList = new ArrayList<>();
        btnAddWBoard.setOnClickListener(this);


        tvReferenceBoard =(TextView) view.findViewById(R.id.tvReferenceBoard);
        search = (ImageView)view.findViewById(R.id.searchBar);
        etSearch = (EditText)view.findViewById(R.id.etSearchBarRB);

        etSearch.setVisibility(View.INVISIBLE);
        search.setOnClickListener(this);




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
                /*leaveDatas.clear();
                leaveDatas.addAll(filteredLeaves);
                leaves_adapter.notifyDataSetChanged();*/
                adapter = new BoardsAdapter(getActivity(),filteredLeaves);
                lv.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });




        return view;}

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
                tvReferenceBoard.setVisibility(View.INVISIBLE);
        }
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }







    public void  showDialog(){




        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_alert_dialogbox,null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();



        edt = (EditText)customView.findViewById(R.id.input_watever);
       TextView tvAddCard = (TextView) customView.findViewById(R.id.btn_add_board);
        tvAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String projectData = edt.getText().toString();
                if (!(projectData.isEmpty())) {
                    ProjectsPojo projectPojoData = new ProjectsPojo();
                    projectPojoData.setData(projectData);
                    myList.add(projectPojoData);
                    adapter = new BoardsAdapter(getActivity(), myList);


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
//                String projectData = title.getText().toString();
//                if (!(projectData.isEmpty())) {
//                    ProjectsPojo projectPojoData = new ProjectsPojo();
//                    projectPojoData.setData(projectData);
//                    myList.add(projectPojoData);
//                    adapter = new BoardsAdapter(getActivity(), myList);
//
//
//                    lv.setAdapter(adapter);
//
//                }
//                else {
//                    Toast.makeText(getActivity(),"No Text Entered",Toast.LENGTH_SHORT).show();
//
//                }
//                    dialog.dismiss();
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


    }

























}
