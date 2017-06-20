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

import java.util.ArrayList;
import java.util.List;


public class LabelColorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RelativeLayout rOne,rTwo,rThree,rFour,rFive,rSix,rSeven,rEight,rNine;
    ImageView imgOne,imgTwo,imgThree,imgFour,imgFive,imgSix,imgSeven,imgEight,ImgNine;
    List<RelativeLayout> layouts;
    List<ImageView> images;
    TextView tvDone;
    EditText etLabelName;
    public static int color;

     List<ImageView> visibleImages;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        images = new ArrayList<>();
        layouts = new ArrayList<>();
        visibleImages = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_color_fragment, container, false);

        tvDone = (TextView)view.findViewById(R.id.doneLabelName);
        etLabelName = (EditText)view.findViewById(R.id.etLabelName);


        rOne = (RelativeLayout)view.findViewById(R.id.rvOne);
        rTwo = (RelativeLayout)view.findViewById(R.id.relativeLayout2);
        rThree = (RelativeLayout)view.findViewById(R.id.relativeLayout3);
        rFour = (RelativeLayout)view.findViewById(R.id.relativeLayout4);
        rFive = (RelativeLayout)view.findViewById(R.id.relativeLayout5);

        rSix = (RelativeLayout)view.findViewById(R.id.relativeLayout6);
        rSeven = (RelativeLayout)view.findViewById(R.id.relativeLayout7);
        rNine = (RelativeLayout)view.findViewById(R.id.relativeLayout9);
        rEight  = (RelativeLayout)view.findViewById(R.id.relativeLayout8);

        imgOne = (ImageView)view.findViewById(R.id.img_one);

        imgTwo = (ImageView)view.findViewById(R.id.img2);

        imgThree = (ImageView)view.findViewById(R.id.img3);
        imgFour = (ImageView)view.findViewById(R.id.img4);
        imgFive = (ImageView)view.findViewById(R.id.img5);
        imgSix = (ImageView)view.findViewById(R.id.img6);
        imgSeven = (ImageView)view.findViewById(R.id.img7);
        imgEight = (ImageView)view.findViewById(R.id.img8);

        imgNine = (ImageView)view.findViewById(R.id.img9);


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
                    if (layout.getVisibility()== View.VISIBLE && layout!= imgOne){

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);

                     //   tvDone.setText(rOne.getBackground().toString());

                         color = Color.TRANSPARENT;
                        Drawable background = rOne.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);


                    }




                }

            }
        });



        rTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            imgTwo.setVisibility(View.VISIBLE);
               //changeVisibility(imgTwo);
                for (ImageView layout : images){
                    if (layout.getVisibility()== View.VISIBLE && layout!= imgTwo){

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);

                         color = Color.TRANSPARENT;
                        Drawable background = rTwo.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);

                    }




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

                         color = Color.TRANSPARENT;
                        Drawable background = rThree.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);

                    }




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
                         color = Color.TRANSPARENT;
                        Drawable background = rFour.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);

                    }




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


                        int color = Color.TRANSPARENT;
                        Drawable background = rFive.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);


                    }




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


                        int color = Color.TRANSPARENT;
                        Drawable background = rSix.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);


                    }




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
                        int color = Color.TRANSPARENT;
                        Drawable background = rSeven.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);


                    }




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
                        int color = Color.TRANSPARENT;
                        Drawable background = rEight.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);


                    }




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
                        int color = Color.TRANSPARENT;
                        Drawable background = rNine.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();
                        tvDone.setTextColor(color);


                    }




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

//

              //  getActivity().onBackPressed();
                FragmentManager fm = getFragmentManager();
//              //  getActivity().getSupportFragmentManager().popBackStack();
//              //  getFragmentManager().popBackStackImmediate();
//                //getActivity().finish();
//
//                getFragmentManager().
                fm.popBackStack();
//
//

              //  Fragment fragment = new LabelColorFragment();


            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


//
//    public void changeVisibility( ImageView tick){
//
//
//                if (tick.getVisibility()==View.GONE){
//                    tick.setVisibility(View.VISIBLE);
//                }
//                if (tick.getVisibility()==View.VISIBLE){
//                    tick.setVisibility(View.GONE);
//                }
//
//
//
//
//    }

    public void findingItemWithTick(){




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
