package com.app.devrah.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Holders.View_holder_label;
import com.app.devrah.R;
import com.app.devrah.Views.LabelColorFragment;

import java.util.List;

/**
 * Created by AQSA SHaaPARR on 6/13/2017.
 */

public class RVLabelAdapter extends RecyclerView.Adapter<View_holder_label> {

    public static int index;
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
    //  HashMap<String,String > listName;

    public RVLabelAdapter() {
    }

    //////////////////String labelName Activity activity

    public RVLabelAdapter(Context context, List<Integer> myList, List<Integer> positionList, List<String> labelNameList, List<String> asliLabelNames , List<String> colornames , List<String> LableIdList,String type) {

        this.myList = myList;
        this.context = context;
        //  this.activity = activity;
        this.positionList = positionList;
        this.labelNameList = labelNameList;
        this.asliLabelNames = asliLabelNames;
        this.colornames = colornames;
        this.LableIdList = LableIdList;
        this.type = type;
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


        if(type.equals("continue"))
        {
            if (labelNameList.get(position) != null) {
                holder.tvLabelNames.setText(labelNameList.get(position));
                holder.tvLabelNames.setVisibility(View.VISIBLE);
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
                holder.rowLabel.setBackgroundColor(context.getResources().getColor(R.color.lightGreen));
            }
        }
        else{



        if (labelNameList.get(position) != null) {
            holder.tvLabelNames.setText(labelNameList.get(position));
            holder.tvLabelNames.setVisibility(View.VISIBLE);
        } else {

            holder.tvLabelNames.setVisibility(View.GONE);

        }

        //  listName  = new HashMap<>();
        holder.rowLabel.setBackgroundColor(myList.get(position));

        }
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

                LabelColorFragment.flag = 5;
                //Fragment fm =new  LabelColorFragment();

//
//                LabelColorFragment colorFragment = new LabelColorFragment();
//
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.add(R.id.fragmentContainer,colorFragment).addToBackStack("Frag1").commit();
//
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                /////////////////////////



                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LabelColorFragment colorFragment = new LabelColorFragment();

                Bundle bundle = new Bundle();
                bundle.putString("l_id",LableIdList.get(position));
                colorFragment.setArguments(bundle);
                if(type.equals("continue"))
                {
                    String labelColor=colornames.get(position);
                    if(labelColor.equals("blue")){
                        index = -13615201;
                        LabelColorFragment.color = context.getResources().getColor(R.color.blue);
                       // index = myList.get(position);
                    }else if(labelColor.equals("sky-blue")){
                        index = -13369367;
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
                        index = -8604862;
                        LabelColorFragment.color = context.getResources().getColor(R.color.lightGreen);
                    }


                }
                else{


                    LabelColorFragment.color = myList.get(position);
                }

                if (!(labelNameList.get(position).isEmpty())) {

                    colorFragment.textLabelName = labelNameList.get(position);
                    // colorFragment.etLabelName.setText(labelNameList.get(position));
                }

                fragmentTransaction.add(R.id.fragmentContainer, colorFragment).addToBackStack("Frag2").commit();
                //  fragmentTransaction.commit();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//             //
            }
        });


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


}