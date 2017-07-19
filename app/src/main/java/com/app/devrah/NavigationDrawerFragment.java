//package com.app.devrah;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.DrawerLayout;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ListView;
//
//import com.app.devrah.Adapters.CustomDrawerAdapter;
//
//
//public class NavigationDrawerFragment extends Fragment {
//
//      private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//     private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    private DrawerLayout mDrawerLayout;
//    private ListView mDrawerListView;
//    private View mFragmentContainerView;
//    private int mCurrentSelectedPosition = 0;
//
//
//
//
//    public NavigationDrawerFragment() {
//        // Required empty public constructor
//    }
//
//    public NavigationDrawerFragment(){}
//
//    public static NavigationDrawerFragment newInstance(String param1, String param2) {
//        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v =inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
//        mDrawerListView = (ListView) v.findViewById(R.id.lvDrawer);
//        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//              //  se
//            }
//        });
//        mDrawerListView.setAdapter(new CustomDrawerAdapter(getActivity().getActionBar().getThemedContext(),
//                ));
//
//
//        return v;
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
////    @Override
////    public void onAttach(Context context) {
////        super.onAttach(context);
////        if (context instanceof OnFragmentInteractionListener) {
////            mListener = (OnFragmentInteractionListener) context;
////        } else {
////            throw new RuntimeException(context.toString()
////                    + " must implement OnFragmentInteractionListener");
////        }
////    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//}
