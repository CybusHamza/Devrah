package com.app.devrah;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.devrah.Adapters.GroupProjectAdapter;
import com.app.devrah.pojo.GroupProjectsPojo;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupProjects.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupProjects#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupProjects extends Fragment implements View.OnClickListener {






    View view;
    Button addGrouProjects;
    ListView AddGroupProjectListView;
    List<GroupProjectsPojo> pojoList;
    String groupProjectData;
    GroupProjectsPojo groupProjectsPojo;
GroupProjectAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GroupProjects() {
        // Required empty public constructor
    }

    public static GroupProjects newInstance(String param1, String param2) {
        GroupProjects fragment = new GroupProjects();
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
        view = inflater.inflate(R.layout.fragment_group_projects, container, false);
        addGrouProjects = (Button) view.findViewById(R.id.buttonAddGroupProject);
        AddGroupProjectListView = (ListView)view.findViewById(R.id.groupProjectsListView);
pojoList = new ArrayList<>();
        addGrouProjects.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;




    }

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

        switch (v.getId()){
                case R.id.buttonAddGroupProject:
                    Toast.makeText(getContext(),"Button Pressed", Toast.LENGTH_LONG).show();
                showDialog();
                break;

        }


    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    public void showDialog(){


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
//                projectData = edt.getText().toString();
//                projectPojoData = new ProjectsPojo();
//                projectPojoData.setData(projectData);
//                listPojo.add(projectPojoData);
//                adapter = new ProjectsAdapter(getActivity(),listPojo);
//
//
//                lv.setAdapter(adapter);


                groupProjectData = edt.getText().toString();

                if (!(groupProjectData.isEmpty())) {

                    groupProjectsPojo = new GroupProjectsPojo();
                    groupProjectsPojo.setGroupProjectData(groupProjectData);
                    pojoList.add(groupProjectsPojo);
                    adapter = new GroupProjectAdapter(getActivity(), pojoList);
                    AddGroupProjectListView.setAdapter(adapter);


                }
                else
                    Toast.makeText(getActivity(),"No Text Entered",Toast.LENGTH_SHORT);


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
