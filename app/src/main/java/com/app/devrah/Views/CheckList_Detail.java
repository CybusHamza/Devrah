package com.app.devrah.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.devrah.Adapters.Checklist_detailed_Addapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.app.devrah.Views.CardActivity.cardDescription;
import static com.app.devrah.Views.CardActivity.cardId;
import static com.app.devrah.Views.CardActivity.cardIsComplete;
import static com.app.devrah.Views.CardActivity.dueDate;
import static com.app.devrah.Views.CardActivity.dueTime;
import static com.app.devrah.Views.CardActivity.isCardLocked;
import static com.app.devrah.Views.CardActivity.isCardSubscribed;
import static com.app.devrah.Views.CardActivity.list_id;
import static com.app.devrah.Views.CardActivity.startDate;
import static com.app.devrah.Views.CardActivity.startTime;


public class CheckList_Detail extends AppCompatActivity {

    ArrayList<String> checkListiItemName;
    ArrayList<String> checkListiItemIds;
    ArrayList<String> checkedItem;
    ListView header;
    String name,checklistid;
    Toolbar toolbar;
    EditText addCheckbox;
    Button addButton;
    String checkboxname;
    ProgressDialog ringProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list__detail);

        header = (ListView) findViewById(R.id.check_id);
        Intent intent = getIntent();
        checkListiItemIds = intent.getStringArrayListExtra("checkListiItemIds");
        checkListiItemName = intent.getStringArrayListExtra("checkListiItemName");
        checkedItem = intent.getStringArrayListExtra("checkedItem");
        name = intent.getStringExtra("name");
        checklistid = intent.getStringExtra("checklistid");
        toolbar = (Toolbar) findViewById(R.id.header);
        addCheckbox = (EditText) findViewById(R.id.addItem);
        addButton = (Button) findViewById(R.id.send_checkbox);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkboxname = addCheckbox.getText().toString();
                add_checkbox(checkboxname);

            }
        });
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));


        Checklist_detailed_Addapter checklist_detailed_addapter = new Checklist_detailed_Addapter(CheckList_Detail.this,R.layout.checklist_item_row,checkListiItemName,checkListiItemIds,checkedItem,name,checklistid);

        header.setAdapter(checklist_detailed_addapter);

    }



    private void add_checkbox  (final String checkbox) {
        ringProgressDialog = ProgressDialog.show(CheckList_Detail.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.ADD_CHECKITEM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {

                    if (checkedItem == null)
                    {
                        checkedItem = new ArrayList<>();
                        checkListiItemIds = new ArrayList<>();
                        checkListiItemName = new ArrayList<>();
                    }

                    checkedItem.add("0");
                    checkListiItemIds.add(response);
                    checkListiItemName.add(checkbox);


                    Intent  intent = new Intent(CheckList_Detail.this, CheckList_Detail.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    intent.putExtra("checkListiItemIds",checkListiItemIds);
                    intent.putExtra("checkListiItemName",checkListiItemName);
                    intent.putExtra("checkedItem",checkedItem);
                    intent.putExtra("checklistid",checklistid);
                    intent.putExtra("name",name);
                    startActivity(intent);


                }
            }

        }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   loading.dismiss();
                ringProgressDialog.dismiss();
                String message = null;
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CheckList_Detail.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(CheckList_Detail.this, SweetAlertDialog.ERROR_TYPE)
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
                Map<String, String> params = new HashMap<String, String>();

                params.put("checkName", checkbox);
                params.put("listID",checklistid);
                params.put("order", "1");
                final SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("u_id", pref.getString("user_id",""));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CheckList_Detail.this);
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(CheckList_Detail.this,CardActivity.class);
        finish();
        intent.putExtra("card_id",cardId);
        intent.putExtra("list_id",list_id);
        intent.putExtra("cardduedate",dueDate);
        intent.putExtra("cardduetime",dueTime);
        intent.putExtra("cardstartdate",startDate);
        intent.putExtra("cardstarttime",startTime);
        intent.putExtra("cardDescription",cardDescription);
        intent.putExtra("isComplete",cardIsComplete);
        intent.putExtra("isLocked",isCardLocked);
        intent.putExtra("isSubscribed",isCardSubscribed);
        startActivity(intent);


    }
}
