package com.app.devrah;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Adapters.RVFragmentLabelAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ColorLabelFragmentRV.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ColorLabelFragmentRV#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorLabelFragmentRV extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public static RecyclerView recyclerView;
    RVFragmentLabelAdapter mAdapter;
    List<Integer> colorList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ColorLabelFragmentRV() {
        // Required empty public constructor
    }

    public static ColorLabelFragmentRV newInstance(String param1, String param2) {
        ColorLabelFragmentRV fragment = new ColorLabelFragmentRV();
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


        colorList = new ArrayList<>();
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.colorAccent);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color_label_fragment_rv, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rvFragmentLabel);

     //   this, LinearLayoutManager.HORIZONTAL, false
       // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new RVFragmentLabelAdapter(getActivity(),colorList);
        recyclerView.setAdapter(mAdapter);

        return view;    }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
