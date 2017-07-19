package com.app.devrah;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Adapters.CustomViewPagerAdapter;
import com.app.devrah.Fragments.ChildFragmentBoardExtended;


public class ParentBoardExtendedFragment extends Fragment {

      private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int selectedTabPosition;

   public static CustomViewPagerAdapter adapter;
    View view;
   static ViewPager viewPager;
   static TabLayout tabLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ParentBoardExtendedFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ParentBoardExtendedFragment newInstance(String param1, String param2) {
        ParentBoardExtendedFragment fragment = new ParentBoardExtendedFragment();
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


        view = inflater.inflate(R.layout.parent_fragment_board_extended, container, false);

        viewPager = (ViewPager)view.findViewById(R.id.my_viewpager);
       // tabLayout = (TabLayout)view.findViewById(R.id.my_tab_layout); tabLayout
        adapter = new CustomViewPagerAdapter(getFragmentManager(), getActivity(), viewPager);
        viewPager.setPageMargin(80);

        viewPager.setAdapter(adapter);
       LastItemBoardFragment fragment = new LastItemBoardFragment();
//        adapter.addLast(fragment);


      //  setEvents();

        // Inflate the layout for this fragment
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

        public static void addPageAt(int positiom){
            LastItemBoardFragment fragment = new LastItemBoardFragment();
           // adapter.addFrag(fragment,"");
        adapter.addFragAt(fragment,positiom);
        }
    public static void addPage(String pagename) {
       //this.adapter = adapter;
        Bundle bundle = new Bundle();
        bundle.putString("data", pagename);
        ChildFragmentBoardExtended fragmentChild = new ChildFragmentBoardExtended();
        fragmentChild.setArguments(bundle);
        adapter.addFrag(fragmentChild, pagename);
        adapter.notifyDataSetChanged();
        //LastItemBoardFragment fragLa = new LastItemBoardFragment();

        int m = adapter.getCount() + 1;
//        if (adapter.getCount() > 0) tabLayout.setupWithViewPager(viewPager);


        viewPager.setCurrentItem(adapter.getCount() - 1);
      //  adapter.addFrag(fragLa,"");




        //setupTabLayout();
    }


    public static void addPageAt(String pagename,int position) {
        //this.adapter = adapter;
        Bundle bundle = new Bundle();
        bundle.putString("data", pagename);
        ChildFragmentBoardExtended fragmentChild = new ChildFragmentBoardExtended();
        fragmentChild.setArguments(bundle);
        adapter.addFragAt(fragmentChild,position);
      //  adapter.addFrag(fragmentChild, pagename);
        adapter.notifyDataSetChanged();
        //LastItemBoardFragment fragLa = new LastItemBoardFragment();

        int m = adapter.getCount() + 1;
//        if (adapter.getCount() > 0) tabLayout.setupWithViewPager(viewPager);


        viewPager.setCurrentItem(adapter.getCount() - 1);
        //  adapter.addFrag(fragLa,"");




        //setupTabLayout();
    }



//    public void addLastPage(){
//        FragmentBoardExtendedLast last = new FragmentBoardExtendedLast();
//       int l =  adapter.getCount() + 1;
//        adapter.addFrag(last,"");
//        viewPager.
//
//
//
//    }

//    public void setupTabLayout() {
//        selectedTabPosition = viewPager.getCurrentItem();
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));
//        }
//    }




//    private void setEvents() {
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                super.onTabSelected(tab);
//                viewPager.setCurrentItem(tab.getPosition());
//                selectedTabPosition = viewPager.getCurrentItem();
//                Log.d("Selected", "Selected " + tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                super.onTabUnselected(tab);
//                Log.d("Unselected", "Unselected " + tab.getPosition());
//            }
//        });
//    }


}