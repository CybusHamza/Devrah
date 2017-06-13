package com.app.devrah;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.app.devrah.Adapters.ProjectsAdapter;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.ArrayList;
import java.util.List;


public class Projects extends Fragment implements View.OnClickListener{




    Button btnAddProject;
    View view;
    ProjectsAdapter adapter;
    List<ProjectsPojo> listPojo;
        ProjectsPojo projectPojoData;
    ListView lv;
    EditText edt;

    String projectData;





    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Projects() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Projects.
     */
    // TODO: Rename and change types and number of parameters
    public static Projects newInstance(String param1, String param2) {
        Projects fragment = new Projects();
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

        view = inflater.inflate(R.layout.fragment_projects, container, false);
        btnAddProject = (Button)view.findViewById(R.id.buttonAddProject);
        listPojo = new ArrayList<>();
        lv = (ListView)view.findViewById(R.id.projectsListView);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(),BoardsActivity.class);
//                startActivity(intent);
//
//            }
//        });


        btnAddProject.setOnClickListener(this);

//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_projects, container, false);
        return  view;
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

        switch (v.getId())
        {

            case R.id.buttonAddProject:

                showDialog();
                Toast.makeText(getContext(),"Button Pressed",Toast.LENGTH_LONG).show();
                break;

        }

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void  showDialog(){

    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.custom_alert_dialogbox,null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        edt = (EditText)customView.findViewById(R.id.input_watever);

        TextView addCard = (TextView)customView.findViewById(R.id.btn_add_board);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                projectData = edt.getText().toString();

                if (!(projectData.isEmpty())) {
                    projectPojoData = new ProjectsPojo();
                    projectPojoData.setData(projectData);
                    listPojo.add(projectPojoData);
                    adapter = new ProjectsAdapter(getActivity(), listPojo);


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


//        AlertDialog.Builder dialog =new AlertDialog.Builder(getContext());
//        dialog.setView(R.layout.custom_alert_dialogbox);
//        dialog.show();






//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());
//        LayoutInflater inflater = this.getActivity().
//                getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.custom_alert_dialogbox, null);
//        dialogBuilder.setView(dialogView);
//
//        final EditText edt = (EditText) dialogView.findViewById(R.id.input_watever);
//
//      //  dialogBuilder.setTitle("Custom dialog");
//    //    dialogBuilder.setMessage("Enter text below");
//        dialogBuilder.setCancelable(false);
//        dialogBuilder.setView(R.layout.custom_alert_dialogbox);
//
//
//        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //do something with edt.getText().toString();
//                projectData = edt.getText().toString();
//
//                if (!(projectData.isEmpty())) {
//                    projectPojoData = new ProjectsPojo();
//                    projectPojoData.setData(projectData);
//                    listPojo.add(projectPojoData);
//                    adapter = new ProjectsAdapter(getActivity(), listPojo);
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
////        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dialog, int whichButton) {
////                //pass
////            }
////        });
//        AlertDialog b = dialogBuilder.create();
//        b.show();
//

    }


}
