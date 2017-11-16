package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.app.devrah.Holders.ViewHolderRVcardScreenLabelResult;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.BoardExtended.BoardExtended;
import com.app.devrah.Views.cards.CardActivity;
import com.app.devrah.pojo.CardAssociatedLabelsPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AQSA SHaaPARR on 6/14/2017.
 */

public class RVLabelResultAdapter extends RecyclerView.Adapter<ViewHolderRVcardScreenLabelResult> {

    List<Integer> myResultantList;
    List<String> resultantLabelNames;
    Activity activity;
    List<CardAssociatedLabelsPojo> labelList;

    public RVLabelResultAdapter(Activity activity, List<Integer> myResultantList, List<String> labelNameResultList, List<CardAssociatedLabelsPojo> labelList) { //, List<String> resultantLabelNames

        this.activity = activity;
        this.myResultantList = myResultantList;
        this.resultantLabelNames = labelNameResultList;
        this.labelList = labelList;


    }


    @Override
    public ViewHolderRVcardScreenLabelResult onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rv_card_screen_labelresult, parent, false);
        ViewHolderRVcardScreenLabelResult holder = new ViewHolderRVcardScreenLabelResult(view);

        // holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.lightGreen));

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderRVcardScreenLabelResult holder, int position) {

        holder.tvLabelName.setText(labelList.get(position).getLabelTextCards());
        final String labelColor = labelList.get(position).getLabelColorCards();
        
        if (labelColor.equals("blue")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.blue));
        } else if (labelColor.equals("sky-blue")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.wierdBlue));
        } else if (labelColor.equals("red")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.colorRed));
        } else if (labelColor.equals("yellow")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.colorYellow));
        } else if (labelColor.equals("purple")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.purple));
        } else if (labelColor.equals("pink")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.pink));
        } else if (labelColor.equals("orange")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));
        } else if (labelColor.equals("black")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.black));
        } else if (labelColor.equals("green")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.colorGreen));
        } else if (labelColor.equals("dark-green")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.darkgreen));
        } else if (labelColor.equals("lime")) {
            holder.rvLabel.setBackgroundColor(activity.getResources().getColor(R.color.green));
        }

        /*holder.rvLabel.setBackgroundColor(myResultantList.get(position));
        if (!(resultantLabelNames.get(position).isEmpty())) {
          //  holder.tvLabelName.setTextColor(activity.getResources().getColor(R.color.colorOrangeRed));
            holder.tvLabelName.setText(resultantLabelNames.get(position));
        }*/
//        holder.tvLabelName.setText(resultantLabelNames.get(position));
//        holder.rvLabel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CardActivity.
//            }
//        });

        holder.rvLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               getBoaardAssignedLabels();
              /* ArrayList<String> colors = new ArrayList<String>();

                for (int i = 0; i < labelList.size(); i++) {
                    colors.add(labelList.get(i).getLabelColorCards());
                }
                CardActivity.showDatColors(colors);
                CardActivity.rvLabelResult.setVisibility(View.GONE);
                CardActivity.labelAdd.setVisibility(View.VISIBLE);*/

            }
        });
    }
    public void getBoaardAssignedLabels(){
        final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_BOARD_ASSIGNED_LABELS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<String> colors = new ArrayList<String>();
                        ArrayList<String> names=new ArrayList<>();
                        ArrayList<String> id=new ArrayList<>();
                        ArrayList<String> isCardAssigned=new ArrayList<>();
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                colors.add(jsonObject.getString("label_color"));
                                names.add(jsonObject.getString("label_text"));
                                id.add(jsonObject.getString("id"));
                                String assigned="0";
                                for (int j=0;j<labelList.size();j++){
                                    if(jsonObject.getString("id").equals(labelList.get(j).getLabelid())){
                                        assigned="1";
                                    }
                                }
                                isCardAssigned.add(assigned);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                       /* for (int i = 0; i < labelList.size(); i++) {
                            colors.add(labelList.get(i).getLabelColorCards());
                            names.add()
                        }*/
                        CardActivity.showDatColors(colors,names,id,isCardAssigned);
                        CardActivity.rvLabelResult.setVisibility(View.GONE);
                        CardActivity.labelAdd.setVisibility(View.VISIBLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "check your internet connection", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("boardId", BoardExtended.boardId);

                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }

    @Override
    public int getItemCount() {
        return labelList.size();
    }
}
