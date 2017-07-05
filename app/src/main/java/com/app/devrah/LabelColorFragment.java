package com.app.devrah;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.devrah.Adapters.RVLabelAdapter;
import com.app.devrah.pojo.ColorsPojo;

import java.util.ArrayList;
import java.util.List;


public class LabelColorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RelativeLayout rOne,rTwo,rThree,rFour,rFive,rSix,rSeven,rEight,rNine;
    RVLabelAdapter adapter;
    ImageView imgOne,imgTwo,imgThree,imgFour,imgFive,imgSix,imgSeven,imgEight,ImgNine;
    List<RelativeLayout> layouts;

   public static String textLabelName;

    List<Integer> myColorList;

    EditText labelName;
    List<ImageView> images;
    TextView tvDone,tvDelete,tvCancel;
    public static int flag;
  public EditText etLabelName;
    ImageView selectedImageView;
    public static int color;
    FragmentManager fm;
     List<ImageView> visibleImages;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int i;
    private OnFragmentInteractionListener mListener;
    private ImageView imgNine;

    public LabelColorFragment() {
        // Required empty public constructor
    }


    public static LabelColorFragment newInstance(String param1, String param2) {
        LabelColorFragment fragment = new LabelColorFragment();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myColorList =new ArrayList<>();
adapter = new RVLabelAdapter();
        myColorList.add(getResources().getColor(R.color.colorAccent));
        fm = getFragmentManager();

        myColorList.add(getResources().getColor(R.color.colorPrimaryDark));
        myColorList.add(getResources().getColor(R.color.colorGreen));

        myColorList.add(getResources().getColor(R.color.colorOrangeRed));
        myColorList.add(getResources().getColor(R.color.colorOrange));
        myColorList.add(getResources().getColor(R.color.colorYellow));
        myColorList.add(getResources().getColor(R.color.wierdBlue));
        myColorList.add(getResources().getColor(R.color.lightGreen));
        myColorList.add(getResources().getColor(R.color.purple));

        images = new ArrayList<>();
        layouts = new ArrayList<>();
        visibleImages = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_color_fragment, container, false);

        tvDone = (TextView)view.findViewById(R.id.doneLabelName);
        tvDelete = (TextView)view.findViewById(R.id.tvDelete);
        etLabelName = (EditText)view.findViewById(R.id.etLabelName);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);

        rOne = (RelativeLayout)view.findViewById(R.id.rvOne);
        rOne.setBackgroundColor(myColorList.get(0));
        etLabelName = (EditText)view.findViewById(R.id.etLabelName);
        rTwo = (RelativeLayout)view.findViewById(R.id.relativeLayout2);
        rTwo.setBackgroundColor(myColorList.get(1));
        rThree = (RelativeLayout)view.findViewById(R.id.relativeLayout3);
        rThree.setBackgroundColor(myColorList.get(2));
        rFour = (RelativeLayout)view.findViewById(R.id.relativeLayout4);
        rFour.setBackgroundColor(myColorList.get(3));
        rFive = (RelativeLayout)view.findViewById(R.id.relativeLayout5);
        rFive.setBackgroundColor(myColorList.get(4));
        rSix = (RelativeLayout)view.findViewById(R.id.relativeLayout6);
        rSix.setBackgroundColor(myColorList.get(5));
        rSeven = (RelativeLayout)view.findViewById(R.id.relativeLayout7);
        rSeven.setBackgroundColor(myColorList.get(6));
        rNine = (RelativeLayout)view.findViewById(R.id.relativeLayout9);
        rNine.setBackgroundColor(myColorList.get(8));
        rEight  = (RelativeLayout)view.findViewById(R.id.relativeLayout8);
        rEight.setBackgroundColor(myColorList.get(7));
        imgOne = (ImageView)view.findViewById(R.id.img_one);

        imgTwo = (ImageView)view.findViewById(R.id.img2);

        imgThree = (ImageView)view.findViewById(R.id.img3);
        imgFour = (ImageView)view.findViewById(R.id.img4);
        imgFive = (ImageView)view.findViewById(R.id.img5);
        imgSix = (ImageView)view.findViewById(R.id.img6);
        imgSeven = (ImageView)view.findViewById(R.id.img7);
        imgEight = (ImageView)view.findViewById(R.id.img8);

        imgNine = (ImageView)view.findViewById(R.id.img9);

        if (flag==5){
            tvDelete.setVisibility(View.VISIBLE);
            etLabelName.setText(textLabelName);


         if (  myColorList.contains(RVLabelAdapter.index)){
             i =  myColorList.indexOf(RVLabelAdapter.index);




             tvDone.setTextColor(myColorList.get(i));


             switch (i){

                 case 0:{

                     imgOne.setVisibility(View.VISIBLE);
                     selectedImageView = imgOne;
                     break;

                 }

                 case 1:{

                     imgTwo.setVisibility(View.VISIBLE);
                     selectedImageView = imgTwo;
                     break;

                 }

                 case 2:{

                     imgThree.setVisibility(View.VISIBLE);
                     selectedImageView = imgThree;
                     break;

                 }

                 case 3:{

                     imgFour.setVisibility(View.VISIBLE);
                     selectedImageView = imgFour;
                     break;

                 }

                 case 4:{

                     imgFive.setVisibility(View.VISIBLE);
                     selectedImageView = imgFive;
                     break;

                 }

                 case 5:{

                     imgSix.setVisibility(View.VISIBLE);
                     selectedImageView = imgSix;
                     break;

                 }

                 case 6:{

                     imgSeven.setVisibility(View.VISIBLE);
                   //  imgSeven.setVisibility(View.VISIBLE);
                     selectedImageView = imgSeven;

                     break;

                 }
                 case 7:{

                     imgEight.setVisibility(View.VISIBLE);
                     selectedImageView = imgEight;

                     break;

                 }
                 case 8:{

                     imgNine.setVisibility(View.VISIBLE);
                     selectedImageView = imgNine;

                     break;

                 }


             }


             tvDelete.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     if (CardActivity.colorList.contains(color)){
                         CardActivity.rvAdapterLabel.remove(color);
                         Toast.makeText(getContext(),"Deleted",Toast.LENGTH_SHORT).show();
                         fm.popBackStack();
                        // CardActivity.showLabelsMenu();
                         flag =0;

                     }else
                         Toast.makeText(getContext(),"Doesnt exist",Toast.LENGTH_SHORT).show();

                 }
             });


         }

//                tvDone.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        adapter.myList.remove(i);
//                        adapter.myList.add(i,color);
//                        adapter.labelNameList.add(i,etLabelName.getText().toString());
//                        adapter.notifyDataSetChanged();
//
//                    }
//                });




        }
tvCancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        flag =0;

        fm.popBackStack();
    }
});


        images.add(imgOne);
        images.add(imgTwo);
        images.add(imgThree);
        images.add(imgFour);
        images.add(imgFive);
        images.add(imgSix);
        images.add(imgSeven);
        images.add(imgEight);
        images.add(imgNine);


        rOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   imgOne.setVisibility(View.VISIBLE);
           //     changeVisibility(imgOne);
                for (ImageView layout : images){
                    if (layout.getVisibility()== View.VISIBLE && layout!= imgOne) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                     //   tvDone.setText(rOne.getBackground().toString());

                         color = Color.TRANSPARENT;

//                        Drawable background = rOne.getBackground();
//                        if (background instanceof ColorDrawable)
//                            color = ((ColorDrawable) background).getColor();
                        //tvDone.setTextColor(color);
                    color = myColorList.get(0);




                }

            }
        });



        rTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            imgTwo.setVisibility(View.VISIBLE);
               //changeVisibility(imgTwo);
                for (ImageView layout : images){
                    if (layout.getVisibility()== View.VISIBLE && layout!= imgTwo) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                         color = Color.TRANSPARENT;
                        Drawable background = rTwo.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);
  //                  color = myColorList.get(1);

                }

            }
        });

        rThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgThree.setVisibility(View.VISIBLE);
              //    changeVisibility(imgThree);
                for (ImageView layout : images){
                    if (layout.getVisibility()== View.VISIBLE && layout!= imgThree){

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                         color = Color.TRANSPARENT;
                        Drawable background = rThree.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);
//                    color = myColorList.get(2);

                }

            }
        });

        rFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgFour.setVisibility(View.VISIBLE);
                //  changeVisibility(imgFour);
                for (ImageView layout : images){
                    if (layout.getVisibility()== View.VISIBLE && layout!= imgFour){

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                         color = Color.TRANSPARENT;
                        Drawable background = rFour.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        //tvDone.setTextColor(color);
//                    color = myColorList.get(3);

                }

            }
        });



        rFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgFive.setVisibility(View.VISIBLE);
              //    changeVisibility(imgFive);
                for (ImageView layout : images){
                    if (layout.getVisibility()== View.VISIBLE && layout!= imgFive){

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }

                         color = Color.TRANSPARENT;
                        Drawable background = rFive.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);
                    color = myColorList.get(4);
                }

            }
        });

        rSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgSix.setVisibility(View.VISIBLE);
              //    changeVisibility(imgSix);
                for (ImageView layout : images){
                    if (layout.getVisibility()== View.VISIBLE && layout!= imgSix){

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);

                    }
                         color = Color.TRANSPARENT;
                        Drawable background = rSix.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);
                    color = myColorList.get(5);
                }

            }
        });

        rSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgSeven.setVisibility(View.VISIBLE);
             //     changeVisibility(imgSeven);
                for (ImageView layout : images){
                    if (layout.getVisibility()== View.VISIBLE && layout!= imgSeven){

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                         color = Color.TRANSPARENT;
                        Drawable background = rSeven.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);
                    color = myColorList.get(6);
                }

            }
        });

        rEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgEight.setVisibility(View.VISIBLE);
                  //changeVisibility(imgEight);
                for (ImageView layout : images){
                    if (layout.getVisibility()== View.VISIBLE && layout!= imgEight){

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                         color = Color.TRANSPARENT;
                        Drawable background = rEight.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);
                    color = myColorList.get(7);

                }

            }
        });
        rNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgNine.setVisibility(View.VISIBLE);
                  //changeVisibility(imgNine);
                for (ImageView layout : images){
                    if (layout.getVisibility()== View.VISIBLE && layout!= imgNine){

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                        color = Color.TRANSPARENT;
                        Drawable background = rNine.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);

                    color = myColorList.get(8);

                }

            }
        });

        layouts.add(rOne);
        layouts.add(rTwo);
        layouts.add(rThree);
        layouts.add(rFour);
        layouts.add(rFive);
        layouts.add(rSix);
        layouts.add(rSeven);
        layouts.add(rEight);
        layouts.add(rNine);

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // color;
                Toast.makeText(getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
                String s = etLabelName.getText().toString();
//
        if (flag==5){

            int p = CardActivity.colorList.indexOf(RVLabelAdapter.index);
            CardActivity.colorList.remove(p);
            CardActivity.colorList.add(p,color);
            CardActivity.labelNameList.remove(p);
            CardActivity.labelNameList.add(p,s);
 }
        else {


//                if (!(s.isEmpty())){
//                     CardActivity.labelNameList.add(s);
//                }

            CardActivity.labelNameList.add(s);
            FragmentManager fm = getFragmentManager();
            if (color != Color.TRANSPARENT) {
                CardActivity.colorList.add(color);
                color = Color.TRANSPARENT;

            }
        }

                CardActivity.showLabelsMenu();
                fm.popBackStack();

            }
        });


//final boolean b = true;
//        tvDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Toast.makeText(getContext(),String.valueOf( CardActivity.colorList.indexOf(color)),Toast.LENGTH_SHORT).show();
//
//
//               int col= CardActivity.colorList.get(CardActivity.colorList.indexOf(color));
//                CardActivity.colorList.remove(col);
//                CardActivity.rvAdapterLabel.notifyDataSetChanged();
//
//              //  CardActivity.rvAdapterLabel.remove(color);
////              if (CardActivity.colorList.contains(color)) {
////
////                  CardActivity.colorList.remove(color);
////                       // CardActivity.rvAdapterLabel.remove(color);
////                    CardActivity.rvAdapterLabel.remove(color);
////
////                    Toast.makeText(getContext(),"Deleted",Toast.LENGTH_SHORT).show();
////                //    CardActivity.showLabelsMenu();
////                    fm.popBackStack();
////                }
//
////                if (CardActivity.rvAdapterLabel.myList.contains(color)){
////
////                    CardActivity.rvAdapterLabel.remove(color);
////                    CardActivity.rvAdapterLabel.notifyDataSetChanged();
////
////                }
////
////
////                else {
////
////                    Toast.makeText(getContext(),"It doesnt exist",Toast.LENGTH_SHORT).show();
////                }
////
////                CardActivity.showLabelsMenu();
////
////
//
//                fm.popBackStack();
//
//
//            }
//        });


        return view;


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

        public static int getColor(){



            return color;

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
}
