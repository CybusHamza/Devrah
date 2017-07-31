package com.app.devrah.Adapters;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.app.devrah.Views.LastItemBoardFragment;

import java.util.ArrayList;

/**
 * Created by AQSA SHaaPARR on 6/2/2017.
 */

public class CustomViewPagerAdapter extends FragmentStatePagerAdapter {

    public static final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private final ArrayList<String> mFragmentTitleList = new ArrayList<>();
    LastItemBoardFragment lastFrag = new LastItemBoardFragment();
    Context context;
    ViewPager viewPager;

    TabLayout tabLayout;

  //  public CustomViewPagerAdapter(FragmentManager manager, Context context){}
        public CustomViewPagerAdapter(FragmentManager manager){
            super(manager);

        }

    public CustomViewPagerAdapter(FragmentManager manager, Context context, ViewPager viewPager
                                 ) {            // TabLayout tabLayout
        super(manager);

        this.context = context;
        this.viewPager = viewPager;
      //  this.tabLayout = tabLayout;


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

        public static int customCount(){


         return mFragmentList.size();
        }
//    public void addLast(Fragment fragment){
//        mFragmentList.add(mFragmentList.size(),fragment);
//        notifyDataSetChanged();
//
//    }

    public int getItemPosition (Object object) { return POSITION_NONE; }


    public void addFragAt(Fragment fragment,int position){
        mFragmentList.add(position,fragment);
        notifyDataSetChanged();
    }
    public void removeFragAt(int position){

        mFragmentList.remove(position);
        notifyDataSetChanged();

    }

    public void addFrag(Fragment fragment, String title) {



        //FragmentBoardExtendedLast lastFrag = new FragmentBoardExtendedLast();

        mFragmentList.add(fragment);

           mFragmentTitleList.add(title);

notifyDataSetChanged();


      // mFragmentList.add(mFragmentList.size(),lastFrag);
/////////////////////////////////////////////////////
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



    public void deletePage(int position)
    {
            mFragmentList.remove(position);
        mFragmentTitleList.remove(position);
            notifyDataSetChanged();
        }


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
//
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


//}
