package com.app.devrah.Views.BoardExtended;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Adapters.CustomViewPagerAdapter;
import com.app.devrah.R;


public class ParentBoardExtendedFragment extends Fragment {

      public static final String b_id = "b_id";
    public static final String p_id = "p_id";
    int selectedTabPosition;

   public static CustomViewPagerAdapter adapter;
    View view;
   static ViewPager viewPager;
   static TabLayout tabLayout;

    // TODO: Rename and change types of parameters
    private static String b_id1;
    private static String p_id1;
    private static String list_id1;

    private OnFragmentInteractionListener mListener;

    public ParentBoardExtendedFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ParentBoardExtendedFragment newInstance(String param1, String param2) {
        ParentBoardExtendedFragment fragment = new ParentBoardExtendedFragment();
        Bundle args = new Bundle();
        args.putString(b_id, param1);
        args.putString(p_id, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            b_id1 = getArguments().getString(b_id);
            p_id1 = getArguments().getString(p_id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.parent_fragment_board_extended, container, false);

        viewPager = (ViewPager)view.findViewById(R.id.my_viewpager);
       // tabLayout = (TabLayout)view.findViewById(R.id.my_tab_layout); tabLayout
        adapter = new CustomViewPagerAdapter(getFragmentManager(), getActivity(), viewPager);

        Bundle bundle = this.getArguments();

        if(bundle != null)
        {
            b_id1 = bundle.getString("p_id");
            p_id1 = bundle.getString("b_id");
            list_id1 = bundle.getString("list_id");
        }


        viewPager.setOffscreenPageLimit(5);
        viewPager.setPageMargin(15);

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
            Bundle bundl = new Bundle();
            bundl.putString("p_id", p_id1);
            bundl.putString("b_id", b_id1);
            fragment.setArguments(bundl);
           // adapter.addFrag(fragment,"");
        adapter.addFragAt(fragment,positiom);
        }
        public static void updateBundleData(String pagename,String p_id1,String b_id1,String list_id1,String list_color,String newList,int pos){
            Bundle bundle = new Bundle();
            bundle.putString("data", pagename);
            bundle.putString("p_id", p_id1);
            bundle.putString("b_id", b_id1);
            bundle.putString("list_id", list_id1);
            bundle.putString("list_color", list_color);
            bundle.putString("listName", newList);

            ChildFragmentBoardExtended fragmentChild = new ChildFragmentBoardExtended();
            fragmentChild.setArguments(bundle);
            adapter.addFragAtPos(fragmentChild, pagename,pos);
            adapter.notifyDataSetChanged();
            //LastItemBoardFragment fragLa = new LastItemBoardFragment();

            int m = adapter.getCount() + 1;
//        if (adapter.getCount() > 0) tabLayout.setupWithViewPager(viewPager);


            viewPager.setCurrentItem(pos);
        }
    public static void addPage(String pagename,String p_id1,String b_id1,String list_id1,String list_color,String newList) {
       //this.adapter = adapter;
        Bundle bundle = new Bundle();
        bundle.putString("data", pagename);
        bundle.putString("p_id", p_id1);
        bundle.putString("b_id", b_id1);
        bundle.putString("list_id", list_id1);
        bundle.putString("list_color", list_color);
        bundle.putString("listName", newList);
        ChildFragmentBoardExtended fragmentChild = new ChildFragmentBoardExtended();
        fragmentChild.setArguments(bundle);
        adapter.addFrag(fragmentChild, pagename);
        adapter.notifyDataSetChanged();
        //LastItemBoardFragment fragLa = new LastItemBoardFragment();

        int m = adapter.getCount() + 1;
//        if (adapter.getCount() > 0) tabLayout.setupWithViewPager(viewPager);


        viewPager.setCurrentItem(0);
      //  adapter.addFrag(fragLa,"");




        //setupTabLayout();
    }
    public static void removeSpecificPage(int position,String newList){
        Bundle bundle = new Bundle();
        bundle.putString("p_id", p_id1);
        bundle.putString("b_id", b_id1);
        bundle.putString("listName", newList);
        ChildFragmentBoardExtended fragmentChild = new ChildFragmentBoardExtended();
        fragmentChild.setArguments(bundle);
        adapter.deletePage(position);
      //  adapter.notifyDataSetChanged();
    }
    public static void updateListName(String listName,int position){
        adapter.updateListTitle(listName,position);
        adapter.notifyDataSetChanged();

    }
    public static void  removeAllFrags(){
        ChildFragmentBoardExtended fragmentChild = new ChildFragmentBoardExtended();
        adapter.renewPage();
        adapter.notifyDataSetChanged();
    }
    public static int getCurrentPosition(){
    int i=viewPager.getCurrentItem();
    return i;
    }

    public static void addPageAt(String pagename,int position,String p_id1,String b_id1,String list_id,String newList) {
        //this.adapter = adapter;
        Bundle bundle = new Bundle();
        bundle.putString("p_id", p_id1);
        bundle.putString("b_id", b_id1);
        bundle.putString("list_id", list_id);
        bundle.putString("data", pagename);
        bundle.putString("listName", newList);
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
