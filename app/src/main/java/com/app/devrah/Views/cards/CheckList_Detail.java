package com.app.devrah.Views.cards;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.app.devrah.Adapters.CustomDrawerAdapter;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.BoardExtended.BoardExtended;
import com.app.devrah.pojo.DrawerPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.app.devrah.Views.cards.CardActivity.CardHeading;
import static com.app.devrah.Views.cards.CardActivity.cardDescription;
import static com.app.devrah.Views.cards.CardActivity.cardId;
import static com.app.devrah.Views.cards.CardActivity.cardIsComplete;
import static com.app.devrah.Views.cards.CardActivity.dueDate;
import static com.app.devrah.Views.cards.CardActivity.dueTime;
import static com.app.devrah.Views.cards.CardActivity.isCardLocked;
import static com.app.devrah.Views.cards.CardActivity.isCardSubscribed;
import static com.app.devrah.Views.cards.CardActivity.isFromMyCardsScreen;
import static com.app.devrah.Views.cards.CardActivity.list_id;
import static com.app.devrah.Views.cards.CardActivity.projectStatus;
import static com.app.devrah.Views.cards.CardActivity.startDate;
import static com.app.devrah.Views.cards.CardActivity.startTime;


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
    DrawerLayout drawerLayout;
    private ListView mDrawerList;
    List<DrawerPojo> dataList;
    CustomDrawerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list__detail);
       // drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
       // mDrawerList = (ListView) findViewById(R.id.left_drawer);
        header = (ListView) findViewById(R.id.check_id);
        Intent intent = getIntent();
        checkListiItemIds = intent.getStringArrayListExtra("checkListiItemIds");
        checkListiItemName = intent.getStringArrayListExtra("checkListiItemName");
        checkedItem = intent.getStringArrayListExtra("checkedItem");
        checklistid = intent.getStringExtra("checklistid");
        name = intent.getStringExtra("name");

        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                intent.putExtra("CardHeaderData",CardHeading);
                intent.putExtra("fromMyCards",isFromMyCardsScreen);
                intent.putExtra("board_name", BoardExtended.bTitle);
                intent.putExtra("project_id",BoardExtended.projectId);
                intent.putExtra("board_id",BoardExtended.boardId);
                intent.putExtra("project_title",BoardExtended.pTitle);
                intent.putExtra("work_board", BoardExtended.isWorkBoard);
                if(isFromMyCardsScreen.equals("board"))
                    intent.putExtra("projectStatus", projectStatus);
                startActivity(intent);
            }
        });
        toolbar.inflateMenu(R.menu.edit);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id) {

                    case R.id.action_edit:
                        updateCheckListNameDialog();
                       /* drawerLayout.openDrawer(Gravity.RIGHT);

                        openDrawer();*/

                        //  Toast.makeText(getApplicationContext(), "Menu", Toast.LENGTH_LONG).show();
                        return true;
                }

                return true;
            }
        });
       // addCheckbox = (EditText) findViewById(R.id.addItem);
        addButton = (Button) findViewById(R.id.send_checkbox);
        dataList = new ArrayList<>();
        dataList.add(new DrawerPojo("Update CheckList Name"));
       if(intent.hasExtra("addNewCheckbox")) {
           showDialog();
       }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
               /* checkboxname = addCheckbox.getText().toString();
                if(!checkboxname.equals("")) {
                    add_checkbox(checkboxname);
                }else {
                    Toast.makeText(CheckList_Detail.this,"Name is must",Toast.LENGTH_LONG).show();
                }*/

            }
        });
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
      //  openDrawer();

        Checklist_detailed_Addapter checklist_detailed_addapter = new Checklist_detailed_Addapter(CheckList_Detail.this,R.layout.checklist_item_row,checkListiItemName,checkListiItemIds,checkedItem,name,checklistid);

        header.setAdapter(checklist_detailed_addapter);

    }

  /*  public void openDrawer() {
        adapter = new CustomDrawerAdapter(this, R.layout.list_item_drawer, dataList);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:s
                        updateCheckListNameDialog();
                        //    Toast.makeText(getApplicationContext(), "heyy", Toast.LENGTH_SHORT).show();


                        break;

                }

            }
        });

    }*/
    public void  showDialog(){

        LayoutInflater inflater = LayoutInflater.from(CheckList_Detail.this);
        View customView = inflater.inflate(R.layout.custom_alert_dialogbox,null);
        final AlertDialog alertDialog = new  AlertDialog.Builder(CheckList_Detail.this).create();

        alertDialog.setCancelable(false);
        final EditText edt = (EditText)customView.findViewById(R.id.input_watever);
        final TextView addCard = (TextView)customView.findViewById(R.id.btn_add_board);
        final TextView addCardAndMore = (TextView)customView.findViewById(R.id.btn_add_board1);
        final TextView headingtv = (TextView)customView.findViewById(R.id.headingTitle);
        headingtv.setText("Add Checkbox");
        addCard.setText("Save and Close");
        showKeyBoard(edt);
        final TextView cancel = (TextView)customView.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(edt);
                alertDialog.dismiss();
            }
        });
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String checkListName = edt.getText().toString();
                if (!(checkListName.isEmpty())&& checkListName.trim().length()>0) {

                    add_checkbox(edt.getText().toString(),"");
                    hideKeyBoard(edt);
                    alertDialog.dismiss();


                }
                else {
                    Toast.makeText(CheckList_Detail.this,"Name is must",Toast.LENGTH_SHORT).show();
                }







            }
        });
        addCardAndMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkListName = edt.getText().toString();
                if (!(checkListName.isEmpty())&& checkListName.trim().length()>0) {
                    add_checkbox(edt.getText().toString(),"1");
                    edt.setText("");
                    hideKeyBoard(edt);
                }
                else {
                    Toast.makeText(CheckList_Detail.this,"Name is must",Toast.LENGTH_SHORT).show();
                }

            }
        });

        alertDialog.setView(customView);
        alertDialog.show();

//

    }
    private void updateCheckListNameDialog() {
        LayoutInflater inflater = LayoutInflater.from(CheckList_Detail.this);
        View customView = inflater.inflate(R.layout.update_card_name_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(CheckList_Detail.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(customView);
        alertDialog.show();

        Button cancel, copy;
        final EditText etCardName= (EditText) customView.findViewById(R.id.etCardName);
        final TextView tvheading= (TextView) customView.findViewById(R.id.heading);
        tvheading.setText("Update CheckList Name");
        etCardName.setText(name);
        etCardName.setSelection(etCardName.getText().length());
        showKeyBoard(etCardName);
        copy = (Button) customView.findViewById(R.id.copy);
        cancel = (Button) customView.findViewById(R.id.close);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check=etCardName.getText().toString();
                if(!check.equals("") && check!="" && check.trim().length()>0) {
                   // UpdateProjectName(etCardName.getText().toString());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etCardName.getWindowToken(), 0);
                    UpdateCheckListName(etCardName.getText().toString());
                    alertDialog.dismiss();

                }else {
                    Toast.makeText(CheckList_Detail.this,"CheckList Name is must!",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyBoard(etCardName);
                alertDialog.dismiss();

            }
        });

    }
    private void showKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
    private void hideKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(title.getWindowToken(), 0);
    }
    private void add_checkbox  (final String checkbox, final String addNewCheck) {
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
                    overridePendingTransition(0, 0);
                    finish();
                    intent.putExtra("checkListiItemIds",checkListiItemIds);
                    intent.putExtra("checkListiItemName",checkListiItemName);
                    intent.putExtra("checkedItem",checkedItem);
                    intent.putExtra("checklistid",checklistid);
                    if(!addNewCheck.equals(""))
                    intent.putExtra("addNewCheckbox","1");
                    intent.putExtra("name",name);
                    startActivity(intent);
                    overridePendingTransition(0, 0);


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
                            .setConfirmText("OK").setContentText("check your internet connection")
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
        intent.putExtra("CardHeaderData",CardHeading);
        intent.putExtra("fromMyCards",isFromMyCardsScreen);
        intent.putExtra("board_name",BoardExtended.bTitle);
        intent.putExtra("project_id",BoardExtended.projectId);
        intent.putExtra("board_id",BoardExtended.boardId);
        intent.putExtra("project_title",BoardExtended.pTitle);
        intent.putExtra("work_board", BoardExtended.isWorkBoard);
        if(isFromMyCardsScreen.equals("board"))
            intent.putExtra("projectStatus", projectStatus);
        startActivity(intent);


    }
    private void UpdateCheckListName(final String s) {
        ringProgressDialog = ProgressDialog.show(CheckList_Detail.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.AUPDATE_CHECKLIST_NAME, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {
                    toolbar.setTitle(s);
                    name=s;
//                    drawerLayout.closeDrawer(Gravity.END);
                    Intent  intent = new Intent(CheckList_Detail.this, CheckList_Detail.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    overridePendingTransition(0, 0);
                    finish();
                    intent.putExtra("checkListiItemIds",checkListiItemIds);
                    intent.putExtra("checkListiItemName",checkListiItemName);
                    intent.putExtra("checkedItem",checkedItem);
                    intent.putExtra("checklistid",checklistid);
                    intent.putExtra("name",name);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
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
                            .setConfirmText("OK").setContentText("check your internet connection")
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


                final SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("checkName",s);;
                params.put("id",checklistid);
                params.put("u_id", pref.getString("user_id",""));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CheckList_Detail.this);
        requestQueue.add(request);



    }
}
