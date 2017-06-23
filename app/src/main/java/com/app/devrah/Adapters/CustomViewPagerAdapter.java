package com.app.devrah.Adapters;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.devrah.FragmentBoardExtendedLast;
import com.app.devrah.R;

import java.util.ArrayList;

/**
 * Created by AQSA SHaaPARR on 6/2/2017.
 */

public class CustomViewPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private final ArrayList<String> mFragmentTitleList = new ArrayList<>();
    Context context;
    ViewPager viewPager;

    TabLayout tabLayout;


    public CustomViewPagerAdapter(FragmentManager manager, Context context, ViewPager viewPager,
                                  TabLayout tabLayout) {
        super(manager);

        this.context = context;
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;


    }
    @Override
    public float getPageWidth(int position) {
        return 0.8f;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {



        FragmentBoardExtendedLast lastFrag = new FragmentBoardExtendedLast();

        mFragmentList.add(fragment);
           mFragmentTitleList.add(title);


//        if(== mFragmentList.size())
//        {
//
//           mFragmentList.add(lastFrag);
//        }
//        else
//        {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }

        // int f = mFragmentList.lastIndexOf(mFragmentList);

    }


   // public View getTabView(final int position) {
      //  View view = LayoutInflater.from(activity).inflate(R.layout.custom_tab_item, null);
       // TextView tabItemName = (TextView) view.findViewById(R.id.textViewTabItemName);
//        CircleImageView tabItemAvatar =
//                (CircleImageView) view.findViewById(R.id.imageViewTabItemAvatar);
//        ImageButton remove = (ImageButton) view.findViewById(R.id.imageButtonRemove);
//        remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("Remove", "Remove");
//                removeFrag(position);
        //}


//        tabItemName.setText(mFragmentTitleList.get(position));
//        tabItemName.setTextColor(activity.getResources().getColor(android.R.color.background_light));

     //   return view;
    //}

//    public void removeFrag(int position) {
//        removeTab(position);
//        Fragment fragment = mFragmentList.get(position);
//        mFragmentList.remove(fragment);
//        mFragmentTitleList.remove(position);
//        destroyFragmentView(viewPager, position, fragment);
//        notifyDataSetChanged();
//    }

//    public View getTabView(final int position) {
//        View view = LayoutInflater.from(activity).inflate(R.layout.custom_tab_item, null);
//        TextView tabItemName = (TextView) view.findViewById(R.id.textViewTabItemName);
//       //ImageButton remove = (ImageButton) view.findViewById(R.id.imageButtonRemove);
//        remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("Remove", "Remove");
//                removeFrag(position);
//            }
//        });


}
