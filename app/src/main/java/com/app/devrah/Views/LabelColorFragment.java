package com.app.devrah.Views;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.app.devrah.Adapters.RVLabelAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;
import static com.app.devrah.Views.CardActivity.cardId;
import static com.app.devrah.Views.CardActivity.labelAdd;
import static com.app.devrah.Views.CardActivity.rvLabel;
import static com.app.devrah.Views.CardActivity.rvLabelResult;


public class LabelColorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String textLabelName;
    public static int flag;
    public static int color;
    String lableid;
    public EditText etLabelName;
    RelativeLayout rOne, rTwo, rThree, rFour, rFive, rSix, rSeven, rEight, rNine;
    RVLabelAdapter adapter;
    ImageView imgOne, imgTwo, imgThree, imgFour, imgFive, imgSix, imgSeven, imgEight, ImgNine;
    List<RelativeLayout> layouts;
    List<Integer> myColorList;
    EditText labelName;
    List<ImageView> images;
    TextView tvDone, tvDelete, tvCancel;
    ImageView selectedImageView;
    FragmentManager fm;
    List<ImageView> visibleImages;
    int i;
    String colorselected;
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

    public static int getColor() {


        return color;

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


        Bundle  bundle = getArguments();

        if(bundle != null)
        {
            lableid = bundle.getString("l_id");
        }

        myColorList = new ArrayList<>();
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

        tvDone = (TextView) view.findViewById(R.id.doneLabelName);
        tvDelete = (TextView) view.findViewById(R.id.tvDelete);
        etLabelName = (EditText) view.findViewById(R.id.etLabelName);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);

        rOne = (RelativeLayout) view.findViewById(R.id.rvOne);
        rOne.setBackgroundColor(myColorList.get(0));
        rTwo = (RelativeLayout) view.findViewById(R.id.relativeLayout2);
        rTwo.setBackgroundColor(myColorList.get(1));
        rThree = (RelativeLayout) view.findViewById(R.id.relativeLayout3);
        rThree.setBackgroundColor(myColorList.get(2));
        rFour = (RelativeLayout) view.findViewById(R.id.relativeLayout4);
        rFour.setBackgroundColor(myColorList.get(3));
        rFive = (RelativeLayout) view.findViewById(R.id.relativeLayout5);
        rFive.setBackgroundColor(myColorList.get(4));
        rSix = (RelativeLayout) view.findViewById(R.id.relativeLayout6);
        rSix.setBackgroundColor(myColorList.get(5));
        rSeven = (RelativeLayout) view.findViewById(R.id.relativeLayout7);
        rSeven.setBackgroundColor(myColorList.get(6));
        rNine = (RelativeLayout) view.findViewById(R.id.relativeLayout9);
        rNine.setBackgroundColor(myColorList.get(8));
        rEight = (RelativeLayout) view.findViewById(R.id.relativeLayout8);
        rEight.setBackgroundColor(myColorList.get(7));
        imgOne = (ImageView) view.findViewById(R.id.img_one);

        imgTwo = (ImageView) view.findViewById(R.id.img2);

        imgThree = (ImageView) view.findViewById(R.id.img3);
        imgFour = (ImageView) view.findViewById(R.id.img4);
        imgFive = (ImageView) view.findViewById(R.id.img5);
        imgSix = (ImageView) view.findViewById(R.id.img6);
        imgSeven = (ImageView) view.findViewById(R.id.img7);
        imgEight = (ImageView) view.findViewById(R.id.img8);

        imgNine = (ImageView) view.findViewById(R.id.img9);

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
                        colorselected = "black";
                        break;

                    }


                    case 1: {

                        imgTwo.setVisibility(View.VISIBLE);
                        selectedImageView = imgTwo;
                        colorselected = "blue";
                        break;

                    }

                    case 2: {

                        imgThree.setVisibility(View.VISIBLE);
                        selectedImageView = imgThree;
                        colorselected = "green";
                        break;

                    }
                    case 3: {

                        imgFour.setVisibility(View.VISIBLE);
                        selectedImageView = imgFour;
                           colorselected = "red";
                        break;

                    }

                    case 4: {

                        imgFive.setVisibility(View.VISIBLE);
                        selectedImageView = imgFive;

                        colorselected = "orange";
                        break;

                    }
                    case 5: {

                        imgSix.setVisibility(View.VISIBLE);
                        selectedImageView = imgSix;
                        colorselected = "yellow";
                        break;

                    }

                    case 6: {

                        imgSeven.setVisibility(View.VISIBLE);
                        //  imgSeven.setVisibility(View.VISIBLE);
                        selectedImageView = imgSeven;
                        colorselected = "sky-blue";
                        break;

                    }
                    case 7: {

                        imgEight.setVisibility(View.VISIBLE);
                        selectedImageView = imgEight;
                        colorselected = "green";

                        break;

                    }
                    case 8: {

                        imgNine.setVisibility(View.VISIBLE);
                        selectedImageView = imgNine;
                        colorselected = "purple";

                        break;

                    }
                }

                }


                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
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
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgOne) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    //   tvDone.setText(rOne.getBackground().toString());
                    colorselected = "black";
                    color = Color.TRANSPARENT;

//                        Drawable background = rOne.getBackground();
//                        if (background instanceof ColorDrawable)
//                            color = ((ColorDrawable) background).getColor();

                    color = myColorList.get(0);
                    tvDone.setTextColor(color);

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
                    colorselected = "blue";
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
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgThree) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    color = Color.TRANSPARENT;
                    Drawable background = rThree.getBackground();
                    if (background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();
                    tvDone.setTextColor(color);
                    colorselected = "green";
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
                    color = Color.TRANSPARENT;
                    colorselected = "red";
                    Drawable background = rFour.getBackground();
                    if (background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();
                    tvDone.setTextColor(color);
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
                    colorselected = "orange";
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
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgSix) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);

                    }
                    colorselected = "yellow";
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
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgSeven) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    color = Color.TRANSPARENT;
                    colorselected = "sky-blue";
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
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgEight) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    colorselected = "lime";
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
                for (ImageView layout : images) {
                    if (layout.getVisibility() == View.VISIBLE && layout != imgNine) {

                        visibleImages.add(layout);
                        layout.setVisibility(View.GONE);
                    }
                    colorselected = "purple";
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
             //   Toast.makeText(getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
                String s = etLabelName.getText().toString();
//
                if(RVLabelAdapter.index == -1)
                {
                    addLable(colorselected,s);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etLabelName.getWindowToken(), 0);
                }
                else
                {
                    updateLAble(colorselected,s);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etLabelName.getWindowToken(), 0);

                }

                CardActivity.menuChanger(CardActivity.menu, false);



            }
        });


        return view;


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

                        ((CardActivity)getActivity()).updateUI();
                       // etLabelName.setText("");
                        fm.popBackStack();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(getActivity(), "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
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
                params.put("board_id",BoardExtended.boardId);
                params.put("card_id",cardId);
                params.put("label_text",lableText);
                params.put("label_data",lableid);
                final SharedPreferences pref = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }

    public  void addLable(final String lablecolor, final String lableText) {
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SAVE_NEW_LABELS_CARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                       // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                        ((CardActivity)getActivity()).updateUI();

                        labelAdd.setVisibility(View.GONE);
                        rvLabel.setVisibility(View.GONE);

                        rvLabelResult.setVisibility(View.VISIBLE);

                        fm.popBackStack();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(getActivity(), "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
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
                params.put("board_id",BoardExtended.boardId);
                params.put("projectId",BoardExtended.projectId);
                params.put("card_id",cardId);
                params.put("label_text",lableText);
                params.put("label_color",lablecolor);
                final SharedPreferences pref = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }

    public  void deleteLable() {
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.DELETE_LABLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                        ((CardActivity)getActivity()).updateUI();

                        labelAdd.setVisibility(View.GONE);
                        rvLabel.setVisibility(View.GONE);

                        rvLabelResult.setVisibility(View.VISIBLE);

                        fm.popBackStack();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(getActivity(), "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
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
                params.put("board_id",BoardExtended.boardId);
                params.put("card_id",cardId);
                params.put("brd_label_id",lableid);
                final SharedPreferences pref = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

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
}
