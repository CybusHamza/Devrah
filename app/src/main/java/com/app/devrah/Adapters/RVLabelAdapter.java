package com.app.devrah.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.devrah.Holders.View_holder_label;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.BoardExtended.BoardExtended;
import com.app.devrah.Views.cards.CardActivity;
import com.app.devrah.Views.cards.LabelColorFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;
import static com.app.devrah.Views.BoardExtended.BoardExtended.boardId;
import static com.app.devrah.Views.cards.CardActivity.cardId;
import static com.app.devrah.Views.cards.CardActivity.labelAdd;
import static com.app.devrah.Views.cards.CardActivity.rvLabel;
import static com.app.devrah.Views.cards.CardActivity.rvLabelResult;
import static com.app.devrah.Views.cards.CardActivity.textLabelName;

/**
 * Created by AQSA SHaaPARR on 6/13/2017.
 */

public class RVLabelAdapter extends RecyclerView.Adapter<View_holder_label> {

    public static int index;
   // String lableid;
    public static int pos;
    public List<String> labelNameList;
    public List<Integer> myList;
    // Activity activity;
    Context context;
    // String labelName;
    String type;
    int flag;
    List<String> asliLabelNames;
    List<String> LableIdList;
    List<Integer> positionList;
    public static List<String> colornames;
    public List<String> isCardAssinged;
    String lableid;
    int color;
    AlertDialog alertDialog;
    //  HashMap<String,String > listName;

    public RVLabelAdapter() {
    }

    //////////////////String labelName Activity activity

    public RVLabelAdapter(Context context, List<Integer> myList, List<Integer> positionList, List<String> labelNameList, List<String> asliLabelNames , List<String> colornames , List<String> LableIdList,String type, List<String> isCardAssinged) {

        this.myList = myList;
        this.context = context;
        //  this.activity = activity;
        this.positionList = positionList;
        this.labelNameList = labelNameList;
        this.asliLabelNames = asliLabelNames;
        this.colornames = colornames;
        this.LableIdList = LableIdList;
        this.type = type;
        this.isCardAssinged=isCardAssinged;
        // this.labelName = labelName;
    }


    @Override
    public View_holder_label onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_label_row, parent, false);
        View_holder_label holder = new View_holder_label(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final View_holder_label holder, final int position) {

        if(position==0)
            holder.labelIcon.setVisibility(View.VISIBLE);
        else
            holder.labelIcon.setVisibility(View.INVISIBLE);

        if(type.equals("continue"))
        {
            if (labelNameList.get(position) != null) {
                holder.tvLabelNames.setText(labelNameList.get(position));
                holder.tvLabelNames.setVisibility(View.VISIBLE);
            }
            if(isCardAssinged.get(position)!=null && isCardAssinged.get(position).equals("1")){
                holder.imgLabel.setVisibility(View.VISIBLE);
            }else {
                holder.imgLabel.setVisibility(View.GONE);
            }
              String labelColor=colornames.get(position);
            if(labelColor.equals("blue")){
                holder.rowLabel.setBackgroundColor(context.getResources().getColor(R.color.blue));
            }else if(labelColor.equals("sky-blue")){
                holder.rowLabel.setBackgroundColor(context.getResources().getColor(R.color.wierdBlue));
            }else if(labelColor.equals("red")){
                holder.rowLabel.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
            }else if(labelColor.equals("yellow")){
                holder.rowLabel.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
            }else if(labelColor.equals("purple")){
                holder.rowLabel.setBackgroundColor(context.getResources().getColor(R.color.purple));
            }else if(labelColor.equals("pink")){
                holder.rowLabel.setBackgroundColor(context.getResources().getColor(R.color.pink));
            }else if(labelColor.equals("orange")){
                holder.rowLabel.setBackgroundColor(context.getResources().getColor(R.color.colorOrange));
            }else if(labelColor.equals("black")){
                holder.rowLabel.setBackgroundColor(context.getResources().getColor(R.color.black));
            }else if(labelColor.equals("green")){
                holder.rowLabel.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            }else if(labelColor.equals("dark-green")){
                holder.rowLabel.setBackgroundColor(context.getResources().getColor(R.color.darkgreen));
            }else if(labelColor.equals("lime")){
                holder.rowLabel.setBackgroundColor(context.getResources().getColor(R.color.green));
            }
        }
        else{



        if (labelNameList.get(position) != null) {
            holder.tvLabelNames.setText(labelNameList.get(position));

            holder.tvLabelNames.setVisibility(View.VISIBLE);
        } else {

            holder.tvLabelNames.setVisibility(View.GONE);

        }
            if(isCardAssinged.get(position)!=null && isCardAssinged.get(position).equals("1")){
                holder.imgLabel.setVisibility(View.VISIBLE);
            }else {
                holder.imgLabel.setVisibility(View.GONE);
            }

        //  listName  = new HashMap<>();
        holder.rowLabel.setBackgroundColor(myList.get(position));

        }
        holder.rowLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCardAssinged.get(position).equals("1")){
                    UnAssignLabelById(LableIdList.get(position),position);
                }else {
                    AssignLabelById(LableIdList.get(position),position);
                }
            }
        });
/*
        holder.rowLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pos = position;


                //  holder.imgLabel.setVisibility(View.VISIBLE);
                if (holder.imgLabel.getVisibility() == View.GONE) {
                    holder.imgLabel.setVisibility(View.VISIBLE);
                    if(type.equals("continue"))
                    {
                        //index = myList.get(position);
                        String labelColor=colornames.get(position);
                        if(labelColor.equals("blue")){
                            index = -13615201;
                            LabelColorFragment.color = context.getResources().getColor(R.color.blue);
                            index = myList.get(position);
                        }else if(labelColor.equals("sky-blue")){
                            index = -862720;
                            LabelColorFragment.color = context.getResources().getColor(R.color.wierdBlue);
                        }else if(labelColor.equals("red")){
                            index = -47862;
                            LabelColorFragment.color = context.getResources().getColor(R.color.colorRed);
                        }else if(labelColor.equals("yellow")){
                            index = -862720;
                            LabelColorFragment.color = context.getResources().getColor(R.color.colorYellow);
                        }else if(labelColor.equals("purple")){
                            LabelColorFragment.color = context.getResources().getColor(R.color.purple);
                            index = -11861886;
                        }else if(labelColor.equals("pink")){
                            index =-16777216;
                            LabelColorFragment.color = context.getResources().getColor(R.color.pink);
                        }else if(labelColor.equals("orange")){
                            index = -32985;
                            LabelColorFragment.color = context.getResources().getColor(R.color.colorOrange);
                        }else if(labelColor.equals("black")){
                            index = -16777216;
                            LabelColorFragment.color = context.getResources().getColor(R.color.black);
                        }else if(labelColor.equals("green")){
                            index = -16713062;
                            LabelColorFragment.color = context.getResources().getColor(R.color.colorGreen);
                        }else if(labelColor.equals("dark-green")){
                            index = -8604862;
                            LabelColorFragment.color = context.getResources().getColor(R.color.darkgreen);
                        }else if(labelColor.equals("lime")){
                            index = -862720;
                            LabelColorFragment.color = context.getResources().getColor(R.color.lightGreen);
                        }
                        positionList.add(LabelColorFragment.color);

                    }
                    else if (!positionList.contains(myList.get(position)))

                        {

                            positionList.add(myList.get(position));

                        }

                    if (holder.imgLabel.getVisibility() == View.VISIBLE) {

                        String name = holder.tvLabelNames.getText().toString();
                        asliLabelNames.add(name);

                    }


                } else {

                    holder.imgLabel.setVisibility(View.GONE);
                }


            }
        });
*/
      /*  holder.rowLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteLable(LableIdList.get(position));
            }
        });*/

        holder.editLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //         Toast.makeText(activity.getApplicationContext(),"dfghj",Toast.LENGTH_SHORT).show();
//                flag = 5;
//                if (flag== 5){
//
//
//                    flag = 0;
//                }

                flag = 5;

                //Fragment fm =new  LabelColorFragment();

//
//                LabelColorFragment colorFragment = new LabelColorFragment();
//
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.add(R.id.fragmentContainer,colorFragment).addToBackStack("Frag1").commit();
//
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                /////////////////////////



              /*  FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LabelColorFragment colorFragment = new LabelColorFragment();

                Bundle bundle = new Bundle();
                bundle.putString("l_id",LableIdList.get(position));
                colorFragment.setArguments(bundle);*/
                lableid=LableIdList.get(position);
                if(type.equals("continue"))
                {
                    String labelColor=colornames.get(position);
                    if(labelColor.equals("blue")){
                        index = -13615201;
                        LabelColorFragment.color = context.getResources().getColor(R.color.blue);
                       // index = myList.get(position);
                    }else if(labelColor.equals("sky-blue")){
                        index = -13369367;
                        color = context.getResources().getColor(R.color.wierdBlue);
                    }else if(labelColor.equals("red")){
                        index = -47862;
                        color = context.getResources().getColor(R.color.colorRed);
                    }else if(labelColor.equals("yellow")){
                        index = -862720;
                        color = context.getResources().getColor(R.color.colorYellow);
                    }else if(labelColor.equals("purple")){
                        color = context.getResources().getColor(R.color.purple);

                        index = -11861886;
                    }else if(labelColor.equals("pink")){
                        index =-62725;
                        color = context.getResources().getColor(R.color.pink);
                    }else if(labelColor.equals("orange")){
                        index = -32985;
                        color = context.getResources().getColor(R.color.colorOrange);
                    }else if(labelColor.equals("black")){
                        index = -16777216;
                        color = context.getResources().getColor(R.color.black);
                    }else if(labelColor.equals("green")){
                        index = -16713062;
                        color = context.getResources().getColor(R.color.colorGreen);
                    }else if(labelColor.equals("dark-green")){
                        index = -8604862;
                        color = context.getResources().getColor(R.color.darkgreen);
                    }else if(labelColor.equals("lime")){
                        index = -4856291;
                        color = context.getResources().getColor(R.color.green);
                    }


                }
                else{


                    color = myList.get(position);
                }

                if (!(labelNameList.get(position).isEmpty())) {

                    CardActivity.textLabelName = labelNameList.get(position);
                    // colorFragment.etLabelName.setText(labelNameList.get(position));
                }else {
                    CardActivity.textLabelName="";
                }
                addNewLabelDialogue();
               /* fragmentTransaction.add(R.id.fragmentContainer, colorFragment).addToBackStack("Frag2").commit();
                //  fragmentTransaction.commit();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);*/
//             //
            }
        });


    }
    private void addNewLabelDialogue(){


        final int[] color = new int[1];
        final String[] lableid = new String[1];
        final ImageView imgNine;// = new ImageView[1];
        final EditText etLabelName;
        final RelativeLayout rOne, rTwo, rThree, rFour, rFive, rSix, rSeven, rEight, rNine,rTen,rEleven;
        RVLabelAdapter adapter;
        final ImageView imgOne, imgTwo, imgThree, imgFour, imgFive, imgSix, imgSeven, imgEight, ImgNine,imgTen,imgEleven;
        List<RelativeLayout> layouts;
        final List<Integer> myColorList;
        final List<ImageView> images;
        final TextView tvDone, tvDelete, tvCancel;
        final ImageView selectedImageView;
        final FragmentManager fm;
        final List<ImageView> visibleImages;
        final int i;
        final String[] colorselected = {null};
        myColorList = new ArrayList<>();
        myColorList.add(context.getResources().getColor(R.color.black));

        myColorList.add(context.getResources().getColor(R.color.colorPrimaryDark));
        myColorList.add(context.getResources().getColor(R.color.colorGreen));

        myColorList.add(context.getResources().getColor(R.color.colorOrangeRed));
        myColorList.add(context.getResources().getColor(R.color.colorOrange));
        myColorList.add(context.getResources().getColor(R.color.colorYellow));
        myColorList.add(context.getResources().getColor(R.color.wierdBlue));
        myColorList.add(context.getResources().getColor(R.color.lightGreen));
        myColorList.add(context.getResources().getColor(R.color.purple));
        myColorList.add(context.getResources().getColor(R.color.green));
        myColorList.add(context.getResources().getColor(R.color.pink));
        // myColorList.add(getResources().getColor(R.color.pink));

        images = new ArrayList<>();
        layouts = new ArrayList<>();
        visibleImages = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_color_fragment,null);
          alertDialog = new  AlertDialog.Builder(context).create();
        tvDone = view.findViewById(R.id.doneLabelName);
        tvDelete = view.findViewById(R.id.tvDelete);
        etLabelName = view.findViewById(R.id.etLabelName);

        tvCancel = view.findViewById(R.id.tvCancel);

        rOne = view.findViewById(R.id.rvOne);
        rOne.setBackgroundColor(myColorList.get(0));
        rTwo = view.findViewById(R.id.relativeLayout2);
        rTwo.setBackgroundColor(myColorList.get(1));
        rThree = view.findViewById(R.id.relativeLayout3);
        rThree.setBackgroundColor(myColorList.get(2));
        rFour = view.findViewById(R.id.relativeLayout4);
        rFour.setBackgroundColor(myColorList.get(3));
        rFive = view.findViewById(R.id.relativeLayout5);
        rFive.setBackgroundColor(myColorList.get(4));
        rSix = view.findViewById(R.id.relativeLayout6);
        rSix.setBackgroundColor(myColorList.get(5));
        rSeven = view.findViewById(R.id.relativeLayout7);
        rSeven.setBackgroundColor(myColorList.get(6));
        rNine = view.findViewById(R.id.relativeLayout9);
        rNine.setBackgroundColor(myColorList.get(8));
        rEight = view.findViewById(R.id.relativeLayout8);
        rEight.setBackgroundColor(myColorList.get(7));
        rTen = view.findViewById(R.id.relativeLayout10);
        rTen.setBackgroundColor(myColorList.get(9));
        rEleven = view.findViewById(R.id.relativeLayout11);
        rEleven.setBackgroundColor(myColorList.get(10));

        imgOne = view.findViewById(R.id.img_one);

        imgTwo = view.findViewById(R.id.img2);

        imgThree = view.findViewById(R.id.img3);
        imgFour = view.findViewById(R.id.img4);
        imgFive = view.findViewById(R.id.img5);
        imgSix = view.findViewById(R.id.img6);
        imgSeven = view.findViewById(R.id.img7);
        imgEight = view.findViewById(R.id.img8);

        imgNine = view.findViewById(R.id.img9);
        imgTen = view.findViewById(R.id.img10);
        imgEleven = view.findViewById(R.id.img11);

        if (flag == 5) {
            tvDelete.setVisibility(View.VISIBLE);
            etLabelName.setText(textLabelName);


            if (myColorList.contains(RVLabelAdapter.index)) {
                i = myColorList.indexOf(RVLabelAdapter.index);


                tvDone.setTextColor(myColorList.get(i));


                if(RVLabelAdapter.index != -1)
                {
                    switch (i) {

                        case 0: {

                            imgOne.setVisibility(View.VISIBLE);
                            selectedImageView = imgOne;
                            colorselected[0] = "black";
                            break;

                        }


                        case 1: {

                            imgTwo.setVisibility(View.VISIBLE);
                            selectedImageView = imgTwo;
                            colorselected[0] = "blue";
                            break;

                        }

                        case 2: {

                            imgThree.setVisibility(View.VISIBLE);
                            selectedImageView = imgThree;
                            colorselected[0] = "green";
                            break;

                        }
                        case 3: {

                            imgFour.setVisibility(View.VISIBLE);
                            selectedImageView = imgFour;
                            colorselected[0] = "red";
                            break;

                        }

                        case 4: {

                            imgFive.setVisibility(View.VISIBLE);
                            selectedImageView = imgFive;

                            colorselected[0] = "orange";
                            break;

                        }
                        case 5: {

                            imgSix.setVisibility(View.VISIBLE);
                            selectedImageView = imgSix;
                            colorselected[0] = "yellow";
                            break;

                        }

                        case 6: {

                            imgSeven.setVisibility(View.VISIBLE);
                            //  imgSeven.setVisibility(View.VISIBLE);
                            selectedImageView = imgSeven;
                            colorselected[0] = "sky-blue";
                            break;

                        }
                        case 7: {

                            imgEight.setVisibility(View.VISIBLE);
                            selectedImageView = imgEight;
                            colorselected[0] = "green";

                            break;

                        }
                        case 8: {

                            imgNine.setVisibility(View.VISIBLE);
                            selectedImageView = imgNine;
                            colorselected[0] = "purple";

                            break;

                        }
                        case 9: {

                            imgTen.setVisibility(View.VISIBLE);
                            selectedImageView = imgTen;
                            colorselected[0] = "lime";

                            break;

                        }
                        case 10: {

                            imgEleven.setVisibility(View.VISIBLE);
                            selectedImageView = imgEleven;
                            colorselected[0] = "pink";

                            break;

                        }
                    }

                }


                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Confirmation!")
                                .setCancelText("Cancel")
                                .showCancelButton(true)
                                .setConfirmText("OK").setContentText("Are you sure you want to delete this label")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {

                                        sDialog.dismiss();

                                          deleteLable();

                                    }
                                } ).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                sweetAlertDialog.dismiss();
                            }
                        })
                                .show();



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
                flag = 0;
                alertDialog.dismiss();
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
        images.add(imgTen);
        images.add(imgEleven);


        String finalColorselected = colorselected[0];
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // color;
                //   Toast.makeText(getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
                String s = etLabelName.getText().toString();
//
                if(colorselected[0] ==null || colorselected[0].equals("")){
                    Toast.makeText(context,"Please Select label!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(RVLabelAdapter.index == -1)
                {
                  /*  addLable(colorselected[0],s);
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etLabelName.getWindowToken(), 0);
                    tvDone.setClickable(false);
                    alertDialog.dismiss();*/
                }
                else
                {
                    updateLAble(colorselected[0],s);
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etLabelName.getWindowToken(), 0);
                    tvDone.setClickable(false);

                }

                CardActivity.menuChanger(CardActivity.menu, false);



            }
        });
        rOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgOne.setVisibility(View.VISIBLE);

                //     changeVisibility(imgOne);
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgOne) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    //   tvDone.setText(rOne.getBackground().toString());
                    colorselected[0] = "black";
                    color[0] = Color.TRANSPARENT;

//                        Drawable background = rOne.getBackground();
//                        if (background instanceof ColorDrawable)
//                            color = ((ColorDrawable) background).getColor();

                    color[0] = myColorList.get(0);
                    tvDone.setTextColor(color[0]);

                }

            }
        });


        rTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgTwo.setVisibility(View.VISIBLE);
                //changeVisibility(imgTwo);
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgTwo) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    colorselected[0] = "blue";
                    color[0] = Color.TRANSPARENT;
                    Drawable background = rTwo.getBackground();
                    if (background instanceof ColorDrawable)
                        color[0] = ((ColorDrawable) background).getColor();
                    tvDone.setTextColor(color[0]);
                    //                  color = myColorList.get(1);

                }

            }
        });

        rThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgThree.setVisibility(View.VISIBLE);
                //    changeVisibility(imgThree);
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgThree) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    color[0] = Color.TRANSPARENT;
                    Drawable background = rThree.getBackground();
                    if (background instanceof ColorDrawable)
                        color[0] = ((ColorDrawable) background).getColor();
                    tvDone.setTextColor(color[0]);
                    colorselected[0] = "green";
//                    color = myColorList.get(2);

                }

            }
        });

        rFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgFour.setVisibility(View.VISIBLE);
                //  changeVisibility(imgFour);
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgFour) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    color[0] = Color.TRANSPARENT;
                    colorselected[0] = "red";
                    Drawable background = rFour.getBackground();
                    if (background instanceof ColorDrawable)
                        color[0] = ((ColorDrawable) background).getColor();
                    tvDone.setTextColor(color[0]);
//                    color = myColorList.get(3);

                }

            }
        });


        rFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgFive.setVisibility(View.VISIBLE);
                //    changeVisibility(imgFive);
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgFive) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    colorselected[0] = "orange";
                    color[0] = Color.TRANSPARENT;
                    Drawable background = rFive.getBackground();
                    if (background instanceof ColorDrawable)
                        color[0] = ((ColorDrawable) background).getColor();
                    tvDone.setTextColor(color[0]);
                    color[0] = myColorList.get(4);
                }

            }
        });

        rSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgSix.setVisibility(View.VISIBLE);
                //    changeVisibility(imgSix);
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgSix) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);

                    }
                    colorselected[0] = "yellow";
                    color[0] = Color.TRANSPARENT;
                    Drawable background = rSix.getBackground();
                    if (background instanceof ColorDrawable)
                        color[0] = ((ColorDrawable) background).getColor();
                    tvDone.setTextColor(color[0]);
                    color[0] = myColorList.get(5);
                }

            }
        });

        rSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgSeven.setVisibility(View.VISIBLE);
                //     changeVisibility(imgSeven);
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgSeven) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    color[0] = Color.TRANSPARENT;
                    colorselected[0] = "sky-blue";
                    Drawable background = rSeven.getBackground();
                    if (background instanceof ColorDrawable)
                        color[0] = ((ColorDrawable) background).getColor();
                    tvDone.setTextColor(color[0]);
                    color[0] = myColorList.get(6);
                }

            }
        });

        rEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgEight.setVisibility(View.VISIBLE);
                //changeVisibility(imgEight);
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgEight) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    colorselected[0] = "dark-green";
                    color[0] = Color.TRANSPARENT;
                    Drawable background = rEight.getBackground();
                    if (background instanceof ColorDrawable)
                        color[0] = ((ColorDrawable) background).getColor();
                    tvDone.setTextColor(color[0]);
                    color[0] = myColorList.get(7);

                }

            }
        });
        rNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgNine.setVisibility(View.VISIBLE);
                //changeVisibility(imgNine);
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgNine) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    colorselected[0] = "purple";
                    color[0] = Color.TRANSPARENT;
                    Drawable background = rNine.getBackground();
                    if (background instanceof ColorDrawable)
                        color[0] = ((ColorDrawable) background).getColor();
                    tvDone.setTextColor(color[0]);

                    color[0] = myColorList.get(8);

                }

            }
        });
        rTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgTen.setVisibility(View.VISIBLE);
                //changeVisibility(imgNine);
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgTen) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    colorselected[0] = "lime";
                    color[0] = Color.TRANSPARENT;
                    Drawable background = rTen.getBackground();
                    if (background instanceof ColorDrawable)
                        color[0] = ((ColorDrawable) background).getColor();
                    tvDone.setTextColor(color[0]);

                    color[0] = myColorList.get(9);

                }

            }
        });
        rEleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgEleven.setVisibility(View.VISIBLE);
                //changeVisibility(imgNine);
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgEleven) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    colorselected[0] = "pink";
                    color[0] = Color.TRANSPARENT;
                    Drawable background = rEleven.getBackground();
                    if (background instanceof ColorDrawable)
                        color[0] = ((ColorDrawable) background).getColor();
                    tvDone.setTextColor(color[0]);

                    color[0] = myColorList.get(10);

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
        layouts.add(rTen);
        layouts.add(rEleven);
        alertDialog.setView(view);
        alertDialog.show();
    }
    public  void updateLAble(final String lablecolor, final String lableText) {
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_LABLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                        labelAdd.setVisibility(View.GONE);
                        rvLabel.setVisibility(View.GONE);
                        rvLabelResult.setVisibility(View.VISIBLE);
                        alertDialog.dismiss();
                        ((CardActivity)context).updateUI();
                        // etLabelName.setText("");


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(context, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("check your internet connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(context, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();

                                }
                            })
                            .show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", boardId);
                params.put("card_id",cardId);
                params.put("label_text",lableText);
                params.put("label_data",lableid);
                final SharedPreferences pref = context.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }
     public  void deleteLable() {
       StringRequest request = new StringRequest(Request.Method.POST, End_Points.DELETE_LABLE,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {


                       //Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                       alertDialog.dismiss();
                       ((CardActivity)context).updateUI();

                       labelAdd.setVisibility(View.GONE);
                       rvLabel.setVisibility(View.GONE);

                       rvLabelResult.setVisibility(View.VISIBLE);


                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
               if (error instanceof NoConnectionError) {


                   Toast.makeText(context, "check your internet connection", Toast.LENGTH_SHORT).show();
                   new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                           .setTitleText("Error!")
                           .setConfirmText("OK").setContentText("check your internet connection")
                           .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                               @Override
                               public void onClick(SweetAlertDialog sDialog) {
                                   sDialog.dismiss();
                               }
                           })
                           .show();
               } else if (error instanceof TimeoutError) {


                   Toast.makeText(context, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                   new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                           .setTitleText("Error!")
                           .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                           .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                               @Override
                               public void onClick(SweetAlertDialog sDialog) {
                                   sDialog.dismiss();

                               }
                           })
                           .show();
               }
           }
       }) {
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {

               Map<String, String> params = new HashMap<>();
               params.put("board_id",boardId);
               params.put("card_id",cardId);
               params.put("brd_label_id",lableid);
               final SharedPreferences pref = context.getSharedPreferences("UserPrefs", MODE_PRIVATE);
               params.put("userId", pref.getString("user_id",""));
               return params;
           }
       };

       request.setRetryPolicy(new DefaultRetryPolicy(
               10000,
               DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
               DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       RequestQueue requestQueue = Volley.newRequestQueue(context);
       requestQueue.add(request);

   }
    private void UnAssignLabelById(final String board_label_id, final int position) {
        final SharedPreferences pref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.UN_ASSIGN_LABEL_BY_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonArray=new JSONObject(response);
                            if(jsonArray.getString("success").equals("1")){
                               isCardAssinged.set(position,"0");
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(context, "check your internet connection", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {


                    Toast.makeText(context, "TimeOut eRROR", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", boardId);
                params.put("userId", pref.getString("user_id",""));
                params.put("card_id", CardActivity.cardId);
                params.put("brd_label_id", board_label_id);

                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    private void AssignLabelById(final String board_label_id, final int position) {
        final SharedPreferences pref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.ASSIGN_LABEL_BY_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                            if(!response.equals("0")){
                                isCardAssinged.set(position,"1");
                                notifyDataSetChanged();
                            }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(context, "check your internet connection", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {


                    Toast.makeText(context, "TimeOut eRROR", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", boardId);
                params.put("userId", pref.getString("user_id",""));
                params.put("card_id", CardActivity.cardId);
                params.put("lbl_id", board_label_id);

                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void remove(int color) {
        notifyDataSetChanged();
        int position = myList.indexOf(color);
        myList.remove(position);
        labelNameList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        if(type.equals("continue")) {
            return labelNameList.size();
        }
        else
        {
            return 1;
        }

    }


    public List<Integer> getData(List<Integer> myList) {


        return positionList;
    }

    public List<String> getDataString() {


        return asliLabelNames;
    }

    public void removeAt(int position) {

        myList.remove(position);
        labelNameList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, myList.size());
        notifyItemRangeChanged(position, labelNameList.size());
        notifyDataSetChanged();

    }

    public void addAt(int position) {
        myList.remove(position);


    }
    public  void deleteLable(final String lableid) {
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.DELETE_LABLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                       /* ((CardActivity)context).updateUI();

                        labelAdd.setVisibility(View.GONE);
                        rvLabel.setVisibility(View.GONE);

                        rvLabelResult.setVisibility(View.VISIBLE);*/

                       //fm.popBackStack();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(context, "check your internet connection", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {


                    Toast.makeText(context, "TimeOut eRROR", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", boardId);
                //params.put("card_id","");
                params.put("brd_label_id",lableid);
                final SharedPreferences pref = context.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }


}