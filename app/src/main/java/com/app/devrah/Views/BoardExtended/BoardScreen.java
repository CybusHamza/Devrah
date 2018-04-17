package com.app.devrah.Views.BoardExtended;

/**
 * Created by Rizwan Jillani on 13-Mar-18.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.allyants.boardview.BoardView;
import com.allyants.boardview.Model.CardAssociatedCheckBox;
import com.allyants.boardview.Model.CardAssociatedCoverPojo;
import com.allyants.boardview.Model.CardAssociatedLabelsPojo;
import com.allyants.boardview.Model.CardAssociatedMembersPojo;
import com.allyants.boardview.Model.ChildObjModel;
import com.allyants.boardview.Model.MainModelObj;
import com.allyants.boardview.Network.End_Points;
import com.allyants.boardview.SimpleBoardAdapter;
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
import com.app.devrah.Adapters.CalendarAdapter;
import com.app.devrah.Adapters.CustomDrawerAdapter;
import com.app.devrah.R;
import com.app.devrah.Views.Board.BoardsActivity;
import com.app.devrah.Views.Favourites.FavouritesActivity;
import com.app.devrah.Views.ManageMembers.Manage_Board_Members;
import com.app.devrah.Views.Notifications.NotificationsActivity;
import com.app.devrah.Views.cards.CardActivity;
import com.app.devrah.pojo.CalendarPojo;
import com.app.devrah.pojo.CardAssociatedCalendarCheckBox;
import com.app.devrah.pojo.CardAssociatedCalendarCoverPojo;
import com.app.devrah.pojo.CardAssociatedCalendarLabelsPojo;
import com.app.devrah.pojo.CardAssociatedCalendarMembersPojo;
import com.app.devrah.pojo.DrawerPojo;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.app.devrah.Network.End_Points.GET_ALL_BOARD_LIST;
import static com.app.devrah.Network.End_Points.GET_CARDS_FOR_LIST;

public class BoardScreen extends AppCompatActivity {
    ProgressDialog ringProgressDialog;
    SimpleBoardAdapter boardAdapter;
    ArrayList<SimpleBoardAdapter.SimpleColumn> data = new ArrayList<>();
    BoardView boardView;
    ArrayList<ChildObjModel> listPojo;
    ArrayList<CardAssociatedLabelsPojo> cardLabelsPojoList;
    ArrayList<CardAssociatedMembersPojo> cardMembersPojoList;
    ArrayList<CardAssociatedCoverPojo> cardCoverPojoList;
    ArrayList<CardAssociatedCheckBox> cardCheckboxPojoList;
    ArrayList<MainModelObj> mainModelObjs;
    ArrayList<Pair<Long, String>> mItemArray;
    int pos=0;
    String toMoveCardId,toMoveListId,toMoveCardName;
    String p_id,b_id,projectTitle,title;
    Toolbar toolbar;
    private EditText edtSeach;
    View logo;
    String isWorkBoard;
    DrawerLayout drawerLayout;
    CustomDrawerAdapter DrawerAdapter;
    private boolean isSearchOpened = false;
    List<DrawerPojo> dataList;
    private ListView mDrawerList;
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    Spinner Projects,Postions;
    CalendarAdapter adapter;
    List<String> spinnerValues;
    List<String> spinnerGroupIds;
    List<String> postions_list;
    CompactCalendarView compactCalendarView;
    ListView lv;
    List<CardAssociatedCalendarLabelsPojo> cardLabelsPojoList1;
    List<CardAssociatedCalendarMembersPojo> cardMembersPojoList1;
    List<CardAssociatedCalendarCoverPojo> cardCoverPojoList1;
    List<CardAssociatedCalendarCheckBox> cardCheckboxPojoList1;
    ArrayList<CalendarPojo> listPojo1;
    Boolean Cancelbtn=false;
    private boolean isEditOpened = false;
    ArrayList<SimpleBoardAdapter.SimpleColumn> dataInner;
    Button addNewList;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_lib);
        boardView = findViewById(R.id.boardView);
        drawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);
        addNewList= findViewById(R.id.buttonAddList);
        Intent intent = this.getIntent();
        b_id = intent.getStringExtra("b_id");
        p_id = intent.getStringExtra("p_id");
        //   list_id = intent.getStringExtra("list_id");
        toolbar = findViewById(R.id.app_bar);
        title = getIntent().getStringExtra("TitleData");
        toolbar.setTitle(title);
        projectTitle = intent.getStringExtra("ptitle");
        if(intent.getStringExtra("work_board").equals("1"))
            isWorkBoard=intent.getStringExtra("work_board");
        else
            isWorkBoard="0";
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        logo = getLayoutInflater().inflate(R.layout.search_bar, null);
        toolbar.addView(logo);
        logo.setVisibility(View.INVISIBLE);

        dataList = new ArrayList<>();
        View header = getLayoutInflater().inflate(R.layout.header_for_drawer, null);

        dataList.add(new DrawerPojo("Update Board Name"));
        dataList.add(new DrawerPojo("Copy Board"));
        dataList.add(new DrawerPojo("Move Board"));
        dataList.add(new DrawerPojo("Manage Members"));
        dataList.add(new DrawerPojo("Leave Board"));
        dataList.add(new DrawerPojo("Delete Board"));
        dataList.add(new DrawerPojo("Calendar"));
        if(intent.getStringExtra("work_board").equals("1")) {
            dataList.add(new DrawerPojo("Archive Board"));
        }

        edtSeach = toolbar.findViewById(R.id.edtSearch);
        edtSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String nameToSearch = edtSeach.getText().toString();

                 dataInner = new ArrayList<>();
                int position=0;
                if(!nameToSearch.equals("")) {
                    for (int i = 0; i < boardAdapter.columns.size(); i++) {

                        ArrayList<ChildObjModel> filteredLeaves = new ArrayList<>();
                        for (ChildObjModel data1 : data.get(i).projectsList) {
                            if (data1.getCard_name().toLowerCase().contains(nameToSearch.toLowerCase())) {
                                filteredLeaves.add(data1);
                            }

                        }
                        position++;
                        dataInner.add(new SimpleBoardAdapter.SimpleColumn(mainModelObjs.get(i).getList_name(), filteredLeaves, mainModelObjs));


                    }
                    boardAdapter = new SimpleBoardAdapter(getApplicationContext(), BoardScreen.this, dataInner, b_id, p_id, mainModelObjs, boardView);
                    boardView.setAdapter(boardAdapter);
                }
                String columnName;
              //  for(MainModelObj data2 : mainModelObjs) {




              // }



            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                  //  for(int i=0;i<boardAdapter.getColumnCount();i++) {
                       // data.add(new SimpleBoardAdapter.SimpleColumn(mainModelObjs.get(i).getList_name(), listPojo, mainModelObjs));
                        boardAdapter = new SimpleBoardAdapter(getApplicationContext(),BoardScreen.this,data,b_id,p_id,mainModelObjs,boardView);
                        boardView.setAdapter(boardAdapter);

                    //}
                }
            }
        });
        addNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        toolbar.inflateMenu(R.menu.menu_with_back_button);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id) {
//                    case R.id.action_settings:
//                        return true;
                    case R.id.action_search:
                        handleMenuSearch();
                        return true;

                    case R.id.action_settings:
                        drawerLayout.openDrawer(Gravity.END);
                        openDrawer();
                        return true;
                    case R.id.tick:
                        final TextView tv = toolbar.findViewById(R.id.toolbar_title);
                        String check=tv.getText().toString();
                        if(!check.equals("")) {
                            tv.clearFocus();
                            tv.setCursorVisible(false);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
                            UpdateBoardName(tv.getText().toString());
                            toolbar.getMenu().clear();
                            toolbar.inflateMenu(R.menu.menu_with_back_button);
                            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
                        }else {
                            Toast.makeText(BoardScreen.this,"Board Name is must!",Toast.LENGTH_LONG).show();
                        }
                        return true;

                }

                return true;
            }
        });

        getList();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Cancelbtn){
                    final TextView tv = toolbar.findViewById(R.id.toolbar_title);
                    tv.setText(title);
                    tv.clearFocus();
                    tv.setCursorVisible(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.menu_with_back_button);
                    toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
                    Cancelbtn=false;
                }else {
                    Intent intent1 = getIntent();
                    if(intent1.hasExtra("ScreenName")){
                        Intent intent = new Intent(BoardScreen.this, NotificationsActivity.class);
                        finish();
                        startActivity(intent);
                    }else if(intent1.hasExtra("activity")){
                        Intent intent = new Intent(BoardScreen.this, FavouritesActivity.class);
                        finish();
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(BoardScreen.this, BoardsActivity.class);
                        intent.putExtra("pid", p_id);
                        //projectTitle=BoardsActivity.ptitle;
                        intent.putExtra("ptitle", projectTitle);
                        intent.putExtra("status", "0");
                        finish();
                        startActivity(intent);
                    }
                    onBackPressed();
                }
            }
        });

        openDrawer();
        mDrawerList.addHeaderView(header);

        boardView.setOnDoneListener(new BoardView.DoneListener() {
            @Override
            public void onDone() {
                Log.e("scroll","done");
            }
        });

        boardView.setOnItemClickListener(new BoardView.ItemClickListener() {
            @Override
            public void onClick(View v, int column_pos, int item_pos) {
                boardView.scrollToColumn(column_pos,false);
                if(isSearchOpened && !edtSeach.getText().toString().equals("")){
                    Intent intent = new Intent(BoardScreen.this, CardActivity.class);
                    intent.putExtra("CardHeaderData", dataInner.get(column_pos).projectsList.get(item_pos).getCard_name());
                    intent.putExtra("card_id", dataInner.get(column_pos).projectsList.get(item_pos).getCardId());
                    intent.putExtra("cardduedate", dataInner.get(column_pos).projectsList.get(item_pos).getCard_end_date());
                    intent.putExtra("cardduetime", dataInner.get(column_pos).projectsList.get(item_pos).getCard_due_time());
                    intent.putExtra("cardstartdate", dataInner.get(column_pos).projectsList.get(item_pos).getCard_start_date());
                    intent.putExtra("cardstarttime", dataInner.get(column_pos).projectsList.get(item_pos).getCard_start_time());
                    intent.putExtra("cardCompletionDate", dataInner.get(column_pos).projectsList.get(item_pos).getCardCompletionDate());
                    intent.putExtra("cardDescription", dataInner.get(column_pos).projectsList.get(item_pos).getCard_description());
                    intent.putExtra("isComplete", dataInner.get(column_pos).projectsList.get(item_pos).getCard_is_complete());
                    intent.putExtra("isLocked", dataInner.get(column_pos).projectsList.get(item_pos).getIs_locked());
                    intent.putExtra("isSubscribed", dataInner.get(column_pos).projectsList.get(item_pos).getSubscribed());
                    intent.putExtra("list_id", dataInner.get(column_pos).projectsList.get(item_pos).getList_id());
                    intent.putExtra("project_id", p_id);
                    intent.putExtra("board_id", b_id);
                    intent.putExtra("board_name", title);
                    intent.putExtra("project_title", projectTitle);
                    intent.putExtra("work_board", isWorkBoard);
                    intent.putExtra("fromMyCards", "false");
                    finish();
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(BoardScreen.this, CardActivity.class);
                    intent.putExtra("CardHeaderData", data.get(column_pos).projectsList.get(item_pos).getCard_name());
                    intent.putExtra("card_id", data.get(column_pos).projectsList.get(item_pos).getCardId());
                    intent.putExtra("cardduedate", data.get(column_pos).projectsList.get(item_pos).getCard_end_date());
                    intent.putExtra("cardduetime", data.get(column_pos).projectsList.get(item_pos).getCard_due_time());
                    intent.putExtra("cardstartdate", data.get(column_pos).projectsList.get(item_pos).getCard_start_date());
                    intent.putExtra("cardstarttime", data.get(column_pos).projectsList.get(item_pos).getCard_start_time());
                    intent.putExtra("cardDescription", data.get(column_pos).projectsList.get(item_pos).getCard_description());
                    intent.putExtra("isComplete", data.get(column_pos).projectsList.get(item_pos).getCard_is_complete());
                    intent.putExtra("cardCompletionDate", data.get(column_pos).projectsList.get(item_pos).getCardCompletionDate());
                    intent.putExtra("isLocked", data.get(column_pos).projectsList.get(item_pos).getIs_locked());
                    intent.putExtra("isSubscribed", data.get(column_pos).projectsList.get(item_pos).getSubscribed());
                    intent.putExtra("list_id", data.get(column_pos).projectsList.get(item_pos).getList_id());
                    intent.putExtra("project_id", p_id);
                    intent.putExtra("board_id", b_id);
                    intent.putExtra("board_name", title);
                    intent.putExtra("project_title", projectTitle);
                    intent.putExtra("work_board", isWorkBoard);
                    intent.putExtra("fromMyCards", "false");
                    finish();
                    startActivity(intent);
                }
            }
        });
        boardView.setOnHeaderClickListener(new BoardView.HeaderClickListener() {
            @Override
            public void onClick(View v, int column_pos) {
                Log.e("ee",String.valueOf(column_pos));
            }
        });
        boardView.setOnDragColumnListener(new BoardView.DragColumnStartCallback() {
            @Override
            public void startDrag(View view, int i) {
                Log.e("Start Drag Column",String.valueOf(i));

                //  moveCard(listPojo.get(i).getCardId(),mainModelObjs.get(i3).getId(),listPojo.get(i).getCard_name(),i2);
            }

            @Override
            public void changedPosition(View view, int i, int i1) {
                Log.e("Change Drag Column",String.valueOf(i1));

            }


            @Override
            public void endDrag(View view, int i, int i1) {
                Log.e("End Drag Column",String.valueOf(i1));
                if(i!=i1) {

                    ArrayList<ChildObjModel> projectsList = data.get(i).projectsList;
                    ArrayList<ChildObjModel> projectsList1 = data.get(i1).projectsList;


                    MainModelObj obj=mainModelObjs.get(i1);
                    MainModelObj obj1=mainModelObjs.get(i);
                    mainModelObjs.set(i, obj);
                    mainModelObjs.set(i1, obj1);
                    data.set(i, new SimpleBoardAdapter.SimpleColumn(mainModelObjs.get(i).getList_name(), projectsList1, mainModelObjs));
                    data.set(i1, new SimpleBoardAdapter.SimpleColumn(mainModelObjs.get(i1).getList_name(), projectsList, mainModelObjs));

                    // String array1=mainModelObjs.get(i).getId()+"%"+String.valueOf(i);
                    //String array2=mainModelObjs.get(i1).getId()+"%"+String.valueOf(i1);
                    StringBuilder arrayFinal = null;
                    for (int j = 0; j < mainModelObjs.size(); j++) {
                        if (j == 0) {
                            arrayFinal = new StringBuilder(mainModelObjs.get(j).getId() + "%" + (j + 1));
                        } else {
                            arrayFinal.append(",").append(mainModelObjs.get(j).getId()).append("%").append(j + 1);
                        }
                    }
                    updateListPosition(arrayFinal.toString());
                    boardAdapter = new SimpleBoardAdapter(getApplicationContext(),BoardScreen.this,data,b_id,p_id,mainModelObjs,boardView);
                    boardView.setAdapter(boardAdapter);
                    boardView.scrollToList(i1);
//                    boardView.scrollToList();



                }

            }
        });

        boardView.setOnFooterClickListener(new BoardView.FooterClickListener() {
            @Override
            public void onClick(View v, int column_pos) {
                Log.e("Footer Click","Column: "+String.valueOf(column_pos));
            }
        });
        boardView.setOnDragItemListener(new BoardView.DragItemStartCallback() {
            @Override
            public void startDrag(View view, int i, int i1) {
                Log.e("Start Drag Item","Item: "+String.valueOf(i1)+"; Column:"+String.valueOf(i));
//                Log.e("size",String.valueOf(listPojo.size()));

            }

            @Override
            public void changedPosition(View view, int i, int i1, int i2, int i3) {
                Log.e("Change Drag Item","Item: "+String.valueOf(i2)+"; Column:"+String.valueOf(i3));

            }

            @Override
            public void endDrag(View view, int i, int i1, int i2, int i3) {
                if(i==i3) {
                    if(i1!=i2) {
                        Log.e("End Drag Item", "Item: " + String.valueOf(i2) + "; Column:" + String.valueOf(i3));

                        Log.e("Main Object:", String.valueOf(mainModelObjs.get(i3).getId()));
                        Log.e("changed size", String.valueOf(listPojo.size()));
                        ArrayList<ChildObjModel> projectsList = data.get(i).projectsList;

                        ChildObjModel projectsPojo = new ChildObjModel();
                        projectsPojo.setCardId(projectsList.get(i1).getCardId());
                        projectsPojo.setCard_name(projectsList.get(i1).getCard_name());
                        projectsPojo.setAttachment(projectsList.get(i1).getAttachment());
                        projectsPojo.setCard_end_date(projectsList.get(i1).getCard_end_date());
                        projectsPojo.setCard_due_time(projectsList.get(i1).getCard_due_time());
                        projectsPojo.setCard_start_date(projectsList.get(i1).getCard_start_date());
                        projectsPojo.setCard_description(projectsList.get(i1).getCard_description());
                        projectsPojo.setCard_is_complete(projectsList.get(i1).getCard_is_complete());
                        projectsPojo.setCard_start_time(projectsList.get(i1).getCard_start_time());
                        projectsPojo.setIs_locked(projectsList.get(i1).getIs_locked());
                        projectsPojo.setSubscribed(projectsList.get(i1).getSubscribed());
                        projectsPojo.setList_id(projectsList.get(i1).getList_id());
                        projectsPojo.setCardCompletionDate(projectsList.get(i1).getCardCompletionDate());
                        // projectsPojo.setCardAssignedMemberId(jsonObject.getString("crd_assigned_membr_id"));
                        projectsPojo.setnOfAttachments(String.valueOf(projectsList.get(i1).getnOfAttachments()));
                        projectsPojo.setAssigned_to(projectsList.get(i1).getAssigned_to());
                        projectsPojo.setCoverList(projectsList.get(i1).getCoverList());
                        projectsPojo.setCheckBoxes(projectsList.get(i1).getCheckBoxes());
                        projectsPojo.setMembersList(projectsList.get(i1).getMembersList());
                        projectsPojo.setLablesList(projectsList.get(i1).getLablesList());
                        projectsPojo.setMemberSubscribed(projectsList.get(i1).getMemberSubscribed());
                        String cardName = projectsList.get(i1).getCard_name();
                        String cardId = projectsList.get(i1).getCardId();
                        if(i1<i2) {
                            data.get(i3).projectsList.add(i2 + 1, projectsPojo);
                            data.get(i).projectsList.remove(i1);
                        }else {
                            data.get(i3).projectsList.add(i2, projectsPojo);
                            data.get(i).projectsList.remove(i1+1);
                        }
                        moveCard(cardId, mainModelObjs.get(i3).getId(), cardName, i2, i3);
                    }
                }else {
                    Log.e("End Drag Item", "Item: " + String.valueOf(i2) + "; Column:" + String.valueOf(i3));

                    Log.e("Main Object:", String.valueOf(mainModelObjs.get(i3).getId()));
                    Log.e("changed size", String.valueOf(listPojo.size()));
                    ArrayList<ChildObjModel> projectsList = data.get(i).projectsList;

                    ChildObjModel projectsPojo = new ChildObjModel();
                    projectsPojo.setCardId(projectsList.get(i1).getCardId());
                    projectsPojo.setCard_name(projectsList.get(i1).getCard_name());
                    projectsPojo.setAttachment(projectsList.get(i1).getAttachment());
                    projectsPojo.setCard_end_date(projectsList.get(i1).getCard_end_date());
                    projectsPojo.setCard_due_time(projectsList.get(i1).getCard_due_time());
                    projectsPojo.setCard_start_date(projectsList.get(i1).getCard_start_date());
                    projectsPojo.setCard_description(projectsList.get(i1).getCard_description());
                    projectsPojo.setCard_is_complete(projectsList.get(i1).getCard_is_complete());
                    projectsPojo.setCard_start_time(projectsList.get(i1).getCard_start_time());
                    projectsPojo.setIs_locked(projectsList.get(i1).getIs_locked());
                    projectsPojo.setSubscribed(projectsList.get(i1).getSubscribed());
                    projectsPojo.setList_id(projectsList.get(i1).getList_id());
                    projectsPojo.setCardCompletionDate(projectsList.get(i1).getCardCompletionDate());
                    // projectsPojo.setCardAssignedMemberId(jsonObject.getString("crd_assigned_membr_id"));
                    projectsPojo.setnOfAttachments(String.valueOf(projectsList.get(i1).getnOfAttachments()));
                    projectsPojo.setAssigned_to(projectsList.get(i1).getAssigned_to());
                    projectsPojo.setCoverList(projectsList.get(i1).getCoverList());
                    projectsPojo.setCheckBoxes(projectsList.get(i1).getCheckBoxes());
                    projectsPojo.setMembersList(projectsList.get(i1).getMembersList());
                    projectsPojo.setLablesList(projectsList.get(i1).getLablesList());
                    projectsPojo.setMemberSubscribed(projectsList.get(i1).getMemberSubscribed());
                    String cardName = projectsList.get(i1).getCard_name();
                    String cardId = projectsList.get(i1).getCardId();

                   // if(i1<i2) {
                        data.get(i3).projectsList.add(i2, projectsPojo);
                        data.get(i).projectsList.remove(i1);
                    //}else {
                      //  data.get(i3).projectsList.add(i2, projectsPojo);
                       // data.get(i).projectsList.remove(i1+1);
                    //}
                    moveCard(cardId, mainModelObjs.get(i3).getId(), cardName, i2, i3);

                }


            }
        });

    }
    public void showDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(BoardScreen.this);
        View customView = layoutInflater.inflate(R.layout.custom_alert_dialog_add_list, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(BoardScreen.this).create();
        alertDialog.setCancelable(false);
        edt = customView.findViewById(R.id.input_watever);
        showKeyBoard(edt);
        final TextView addCard = customView.findViewById(R.id.btn_add_board);
        // final TextView addMoreList = (TextView) customView.findViewById(R.id.btn_add_board1);
        final TextView cancelbtn = customView.findViewById(R.id.btn_cancel);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(edt);
                alertDialog.dismiss();
            }
        });
   /* addMoreList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String projectData = edt.getText().toString();
            if(!projectData.equals("")) {
                // adapter.removeFragAt(adapter.getCount()-1);
                // adapter.notifyDataSetChanged();
                addNewList(projectData, String.valueOf(adapter.getCount() - 1),"more");
                hideKeyBoard(edt);
                alertDialog.dismiss();
                //    CustomViewPagerAdapter.mFragmentList.remove(CustomViewPagerAdapter.customCount() -1);
            }else {
                Toast.makeText(getActivity(),"Please Enter List Name",Toast.LENGTH_SHORT).show();

            }
        }
    });*/
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String projectData = edt.getText().toString();
                if(!projectData.equals("")) {
                    // adapter.removeFragAt(adapter.getCount()-1);
                    // adapter.notifyDataSetChanged();
                    addNewList(projectData, String.valueOf(mainModelObjs.size()),"");
                    //    CustomViewPagerAdapter.mFragmentList.remove(CustomViewPagerAdapter.customCount() -1);
                    hideKeyBoard(edt);
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(BoardScreen.this,"Please Enter List Name",Toast.LENGTH_SHORT).show();

                }



            }
        });


        alertDialog.setView(customView);
        alertDialog.show();
    }
    public void addNewList(final String projectData, final String position,final String more){
        ringProgressDialog = ProgressDialog.show(BoardScreen.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, com.app.devrah.Network.End_Points.SAVE_NEW_LIST_BY_BOARD_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if(!response.equals("0")) {
                            MainModelObj mainModelObj = new MainModelObj();
                            mainModelObj.setId(response);
                            mainModelObj.setList_name(projectData);
                            mainModelObj.setList_color("");
                            ArrayList<ChildObjModel> listPojo=new ArrayList<>();
                            mainModelObj.setChildObjModels(listPojo);
                            mainModelObjs.add(mainModelObj);

                            // mainModelObjs.add(mainModelObj);
                            data.add(new SimpleBoardAdapter.SimpleColumn(projectData,listPojo,mainModelObjs));

                            boardAdapter = new SimpleBoardAdapter(getApplicationContext(),BoardScreen.this,data,b_id,p_id,mainModelObjs,boardView);
                            boardView.setAdapter(boardAdapter);
                          boardView.scrollToList(mainModelObjs.size()-1);
                        }
                        //   ParentBoardExtendedFragment.addPageAt(CustomViewPagerAdapter.customCount());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {
                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();

                   /* new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("check your internet connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();

                                }
                            })
                            .show();*/
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(BoardScreen.this, "TimeOut eRROR", Toast.LENGTH_SHORT).show();

                  /*  new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();

                                }
                            })
                            .show();*/
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("prjct_id", p_id);
                params.put("board_id", b_id);
                params.put("name",projectData);
                params.put("column_order",position);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(BoardScreen.this);
        requestQueue.add(request);
    }
    public void openDrawer() {
        DrawerAdapter = new CustomDrawerAdapter(this, R.layout.list_item_drawer, dataList);
        mDrawerList.setAdapter(DrawerAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {

                    case 1:
                        updateBoardNameDialog();
                        break;


                    case 2:

                        showDialog("copy");
                        break;


                    case 3:

                        showDialog("move");
                        break;


                    case 4:

                        Intent intent = new Intent(BoardScreen.this, Manage_Board_Members.class);
                        intent.putExtra("P_id",p_id);
                        intent.putExtra("b_id",b_id);
                        startActivity(intent);

                        break;

                    case 5:
                        new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Confirmation!")
                                .setCancelText("Cancel")
                                .setConfirmText("OK").setContentText("Cards are associated with this board. Do you really want to leave?")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        LeaveBoard();
                                        sDialog.dismiss();
                                    }
                                })
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();
                        break;

                    case 6:
                        new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Confirmation!")
                                .setCancelText("Cancel")
                                .setConfirmText("OK").setContentText("Do you really want to delete Board?")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismiss();
                                        deleteBoard();
                                    }
                                })
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();
                        break;
                    case 7:
                        showCalendarDialog();

                        break;
                    case 8:
                        new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Confirmation!")
                                .setCancelText("Cancel")
                                .setConfirmText("OK").setContentText("Do you really want to Archive Board?")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismiss();
                                        archiveBoard();
                                    }
                                })
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();

                        break;

                }
            }
        });

    }
    public void doSearch() {
//
    }
    private void updateBoardNameDialog() {
        LayoutInflater inflater = LayoutInflater.from(BoardScreen.this);
        View customView = inflater.inflate(R.layout.update_card_name_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(BoardScreen.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(customView);
        alertDialog.show();

        Button cancel, copy;
        final EditText etCardName= customView.findViewById(R.id.etCardName);
        final TextView tvheading= customView.findViewById(R.id.heading);
        tvheading.setText("Update Board Name");
        etCardName.setText(title);
        etCardName.setSelection(etCardName.getText().length());
        showKeyBoard(etCardName);
        copy = customView.findViewById(R.id.copy);
        cancel = customView.findViewById(R.id.close);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check=etCardName.getText().toString();
                if(!check.equals("") && check!="" && check.trim().length()>0) {
                    UpdateBoardName(etCardName.getText().toString());
                    hideKeyBoard(etCardName);
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(BoardScreen.this,"Board Name is must!",Toast.LENGTH_LONG).show();
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
    private void showCalendarDialog() {
        final SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

        LayoutInflater inflater = LayoutInflater.from(BoardScreen.this);
        View customView = inflater.inflate(R.layout.calendar_view_dialog, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(BoardScreen.this,R.style.full_screen_dialog).create();
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        alertDialog.getWindow().setLayout(320, DrawerLayout.LayoutParams.MATCH_PARENT);

        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        wmlp.gravity = Gravity.END;
        alertDialog.setView(customView);
        alertDialog.show();
        final TextView heading= customView.findViewById(R.id.header);
        final ImageView backBtn= customView.findViewById(R.id.backBtnCalender);
        compactCalendarView = customView.findViewById(R.id.compactcalendar_view);
        lv= customView.findViewById(R.id.listView);
        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        Date dat=new Date();
        heading.setText(dateFormatForMonth.format(dat));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

       /* for(int i=1;i<10;i=i+2) {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            cal.set(Calendar.DAY_OF_MONTH,i);
            cal.set(Calendar.MONTH, 9);
            cal.set(Calendar.YEAR, 2017);
            long time = cal.getTimeInMillis();
            Event event=new Event(Color.GREEN,time,"event");
            compactCalendarView.addEvent(event);
        }*/
        List<Event> events = compactCalendarView.getEvents(1433701251000L); // can also take a Date object

        // events has size 2 with the 2 events inserted previously
//        Log.d(TAG, "Events: " + events);

        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);

                if(events.toArray().length==0){
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
                    String cardsByDate=dateFormat.format(dateClicked);
                    Toast.makeText(BoardScreen.this,"No cards due on "+cardsByDate,Toast.LENGTH_LONG).show();
                }else {
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String cardsByDate=dateFormat.format(dateClicked);
                    //getCards(cardsByDate);
                    for(int i=listPojo1.size()-1;i>=0;i--){
                        if(listPojo1.get(i).getDueDate().equals(cardsByDate)){
                            lv.setSelection(i);
                        }
                    }

                }

//                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
//                int month=firstDayOfNewMonth.getMonth();
//                String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

                // String monthname=(String)android.text.format.DateFormat.format("MMM", month);
                heading.setText(dateFormatForMonth.format(firstDayOfNewMonth));

                //  Toast.makeText(CalenderEvents.this,firstDayOfNewMonth.getDate(),Toast.LENGTH_LONG).show();
                //   Log.d("month", "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String cardsByDate=dateFormat.format(dat);
        getCards("",alertDialog);
        getDueDates(cardsByDate);
    }
    private void getDueDates(final String currentDate){
        StringRequest request = new StringRequest(Request.Method.POST, com.app.devrah.Network.End_Points.GET_DUE_DATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            if (!response.equals("false")) {
                                JSONArray jsonArray = new JSONArray(response);
                                compactCalendarView.removeAllEvents();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String dueDate=jsonObject.getString("card_end_date");
                                    String[] dueDateSplit=dueDate.split("-");

                                    if(dueDateSplit.length==3) {
                                        int dayOfMonth = Integer.parseInt(dueDateSplit[2]);
                                        int month = Integer.parseInt(dueDateSplit[1]);
                                        int year = Integer.parseInt(dueDateSplit[0]);
                                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        cal.set(Calendar.MONTH, month-1);
                                        cal.set(Calendar.YEAR, year);
                                        long time = cal.getTimeInMillis();
                                        Event event = new Event(Color.GREEN, time, "event");
                                        compactCalendarView.addEvent(event);
                                    }


                                }
                                //  if(compactCalendarView.getEvents())
                            }
                            //getCards("");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {


                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardScreen.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("uid",  pref.getString("user_id",""));
                params.put("project_id", p_id);
                params.put("board_id", b_id);


                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardScreen.this.getApplicationContext());
        requestQueue.add(request);
    }
    private void getCards(final  String dueDate,final AlertDialog alertDialog){
        final SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, com.app.devrah.Network.End_Points.GETMYCARDSBYDUEDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if (!(response.equals("false"))) {
                            listPojo1 = new ArrayList<>();
                            cardLabelsPojoList1 = new ArrayList<>();
                            cardMembersPojoList1 = new ArrayList<>();
                            cardCoverPojoList1 = new ArrayList<>();
                            cardCheckboxPojoList1 = new ArrayList<>();
                            try {
                                CalendarPojo projectsPojo = null;
                                JSONObject mainObject=new JSONObject(response);
                                JSONArray jsonArrayCards = mainObject.getJSONArray("cards");
                                JSONArray jsonArrayLabels = mainObject.getJSONArray("labels");
                                JSONArray jsonArrayMembers = mainObject.getJSONArray("members");
                                JSONArray jsonArrayAttachments = mainObject.getJSONArray("attachments");
                                JSONArray jsonArrayAttachmentsCover = mainObject.getJSONArray("attachment_cover");
                                JSONArray jsonArrayCheckbox = mainObject.getJSONArray("checkbox");

                                //JSONObject cardsObject = jsonArray.getJSONObject(0);

                                //  row=jsonArrayCards.length();
                                for (int i = 0; i < jsonArrayCards.length(); i++) {
                                    JSONObject jsonObject = jsonArrayCards.getJSONObject(i);
                                    JSONArray jsonArray=jsonArrayAttachments.getJSONArray(i);

                                    //JSONObject jsonObject1 = jsonArrayAttachments.getJSONObject(i);

                                    projectsPojo = new CalendarPojo();
                                    // CardAssociatedLabelsPojo labelsPojo = new CardAssociatedLabelsPojo();
                                    projectsPojo.setId(jsonObject.getString("id"));
                                    projectsPojo.setData(jsonObject.getString("card_name"));
                                    projectsPojo.setAttachment(jsonObject.getString("file_name"));
                                    projectsPojo.setDueDate(jsonObject.getString("card_end_date"));
                                    projectsPojo.setDuetTime(jsonObject.getString("card_due_time"));
                                    projectsPojo.setStartDate(jsonObject.getString("card_start_date"));
                                    projectsPojo.setCardCompletionDate(jsonObject.getString("card_completion_date"));
                                    projectsPojo.setCardDescription(jsonObject.getString("card_description"));
                                    projectsPojo.setIsCardComplete(jsonObject.getString("card_is_complete"));
                                    projectsPojo.setStartTime(jsonObject.getString("card_start_time"));
                                    projectsPojo.setIsCardLocked(jsonObject.getString("is_locked"));
                                    projectsPojo.setIsCardSubscribed(jsonObject.getString("subscribed"));
                                    // projectsPojo.setCardAssignedMemberId(jsonObject.getString("crd_assigned_membr_id"));
                                    projectsPojo.setnOfAttachments(String.valueOf(jsonArray.length()));
                                    projectsPojo.setAssignedTo(jsonObject.getString("assigned_to"));
                                    projectsPojo.setListId(jsonObject.getString("list_id"));

                                    // projectsPojo.setBoardAssociatedLabelsId(jsonObject.getString("board_assoc_label_id"));
                                    //projectsPojo.setLabels(jsonObject.getString("label_color"));

//                                    cardLabelsPojoList.add(labelsPojo);

                                    listPojo1.add(projectsPojo);
                                    // getLabelsList(jsonObject.getString("id"));

                                }
                                for(int j=0;j<jsonArrayLabels.length();j++){
                                    CardAssociatedCalendarLabelsPojo labelsPojo = new CardAssociatedCalendarLabelsPojo();
                                    JSONArray jsonArray=jsonArrayLabels.getJSONArray(j);
                                    String[] labels = new String[jsonArray.length()];
                                    String[] labelText = new String[jsonArray.length()];
                                    for (int k=0;k<jsonArray.length();k++){

                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        labels[k]=jsonObject.getString("label_color");
                                        if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }
                                    }
                                    labelsPojo.setLabels(labels);
                                    labelsPojo.setLabelText(labelText);
                                    cardLabelsPojoList1.add(labelsPojo);
                                }

                                for(int j=0;j<jsonArrayMembers.length();j++){
                                    CardAssociatedCalendarMembersPojo membersPojo = new CardAssociatedCalendarMembersPojo();
                                    JSONArray jsonArray=jsonArrayMembers.getJSONArray(j);
                                    String[] members = new String[jsonArray.length()];
                                    String[] labelText = new String[jsonArray.length()];
                                    String[] gp_picture = new String[jsonArray.length()];
                                    String subsribed = "";
                                    // SharedPreferences pref = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                                    for (int k=0;k<jsonArray.length();k++){

                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        members[k]=jsonObject.getString("profile_pic");
                                        labelText[k]=jsonObject.getString("initials");
                                        gp_picture[k]=jsonObject.getString("gp_picture");
                                        if(jsonObject.getString("uid").equals(pref.getString("user_id",""))) {
                                            subsribed = jsonObject.getString("subscribed");
                                        }
                                       /* if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }*/
                                    }
                                    membersPojo.setMembers(members);
                                    membersPojo.setInitials(labelText);
                                    membersPojo.setGp_pictures(gp_picture);
                                    membersPojo.setMemberSubscribed(subsribed);
                                    //  labelsPojo.setLabelText(labelText);
                                    cardMembersPojoList1.add(membersPojo);
                                }
                                for(int j=0;j<jsonArrayAttachmentsCover.length();j++){
                                    CardAssociatedCalendarCoverPojo membersPojo = new CardAssociatedCalendarCoverPojo();
                                    JSONArray jsonArray=jsonArrayAttachmentsCover.getJSONArray(j);
                                    String[] attachmentName = new String[jsonArray.length()];
                                    String[] makeCover = new String[jsonArray.length()];
                                    // String attachmentName=null;
                                    //String makeCover=null;
                                    for (int k=0;k<jsonArray.length();k++){

                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        attachmentName[k]=jsonObject.getString("file_name");
                                        makeCover[k]=jsonObject.getString("make_cover");
                                       /* if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }*/
                                    }
                                    membersPojo.setFileName(attachmentName);
                                    membersPojo.setIsCover(makeCover);
                                    //  labelsPojo.setLabelText(labelText);
                                    cardCoverPojoList1.add(membersPojo);
                                }
                                for(int j=0;j<jsonArrayCheckbox.length();j++){
                                    CardAssociatedCalendarCheckBox checkBoxPojo = new CardAssociatedCalendarCheckBox();
                                    JSONArray jsonArray=jsonArrayCheckbox.getJSONArray(j);
                                    checkBoxPojo.setTotalCheckBoxes(jsonArray.length());
                                    int checked=0;
                                    for (int k=0;k<jsonArray.length();k++){

                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        if(jsonObject.getString("is_checked").equals("1")){
                                            checked++;
                                        }
                                    }
                                    checkBoxPojo.setCheckedCheckBox(String.valueOf(checked));

                                    //  labelsPojo.setLabelText(labelText);
                                    cardCheckboxPojoList1.add(checkBoxPojo);
                                }

                                adapter = new CalendarAdapter(BoardScreen.this, listPojo1,cardLabelsPojoList1,cardMembersPojoList1,cardCoverPojoList1,0,alertDialog,cardCheckboxPojoList1,b_id,p_id,title,projectTitle,isWorkBoard);
                                lv.setAdapter(adapter);

                                /*try {
                                    cardAssociatedLabelsAdapter = new CardAssociatedLabelsAdapter(getActivity(), cardLabelsPojoList);
                                    cardAssociatedLabelRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
                                    cardAssociatedLabelRecycler.setAdapter(cardAssociatedLabelsAdapter);
                                }catch (Exception e){
                                    String s=e.toString();
                                }*/
                            } catch (JSONException e) {
                                e.printStackTrace();


                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardScreen.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("uid",  pref.getString("user_id",""));
                params.put("project_id",p_id);
                params.put("board_id", b_id);
                params.put("card_due_date",dueDate);


                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardScreen.this.getApplicationContext());
        requestQueue.add(request);
    }
    private void showKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
    private void hideKeyBoard(EditText title) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(title.getWindowToken(), 0);
    }
    protected void handleMenuSearch() {
        //ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open

            //action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            // action.setDisplayShowTitleEnabled(true); //show the title in the action bar
            //hides the keyboard

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);
            toolbar.setTitle(title);
            logo.setVisibility(View.INVISIBLE);
            edtSeach.setText("");

            //add the search icon in the action bar
            // mSearchAction.setIcon(getResources().getDrawable(R.drawable.search_workboard));

            isSearchOpened = false;
        } else { //open the search entry

            //     action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            logo.setVisibility(View.VISIBLE);

            //  action.setCustomView(R.layout.search_bar);//add the custom view
            // action.setDisplayShowTitleEnabled(false); //hide the title
            toolbar.setTitle("");

            //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
//            mSearchAction.setIcon(getResources().getDrawable(R.drawable.search_workboard));

            isSearchOpened = true;
        }
    }
    private void LeaveBoard() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, com.app.devrah.Network.End_Points.LEAVE_BOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if(response.equals("2"))
                        {
                            Toast.makeText(BoardScreen.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("1"))
                        {
                            Toast.makeText(BoardScreen.this, "you are admin you cannot leave this Board", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(BoardScreen.this, "Board Left Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent1 = getIntent();
                            if(intent1.hasExtra("ScreenName")){
                                Intent intent = new Intent(BoardScreen.this, NotificationsActivity.class);
                                finish();
                                startActivity(intent);
                            }else if(intent1.hasExtra("activity")){
                                Intent intent = new Intent(BoardScreen.this, FavouritesActivity.class);
                                finish();
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(BoardScreen.this, BoardsActivity.class);
                                intent.putExtra("pid", p_id);
                                //projectTitle=BoardsActivity.ptitle;
                                intent.putExtra("ptitle", projectTitle);
                                intent.putExtra("status", "0");
                                finish();
                                startActivity(intent);
                            }
                            onBackPressed();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent=new Intent(BoardScreen.this,BoardsActivity.class);
                                    intent.putExtra("pid",p_id);
                                    intent.putExtra("ptitle",projectTitle);
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardScreen.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent=new Intent(BoardScreen.this,BoardsActivity.class);
                                    intent.putExtra("pid",p_id);
                                    intent.putExtra("ptitle",projectTitle);
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", b_id);
                params.put("project_id", p_id);
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

                params.put("userId", pref.getString("user_id",""));

                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardScreen.this.getApplicationContext());
        requestQueue.add(request);

    }

    private void deleteBoard() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, com.app.devrah.Network.End_Points.DELETE_BOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if(!response.equals("0")){
                            Intent intent = new Intent(BoardScreen.this, BoardsActivity.class);
                            intent.putExtra("pid", p_id);

                            intent.putExtra("ptitle", projectTitle);
                            intent.putExtra("status", "0");
                            finish();
                            startActivity(intent);
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("check your internet connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent = new Intent(BoardScreen.this, BoardsActivity.class);
                                    intent.putExtra("pid", p_id);

                                    intent.putExtra("ptitle", projectTitle);
                                    intent.putExtra("status", "0");
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardScreen.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent = new Intent(BoardScreen.this, BoardsActivity.class);
                                    intent.putExtra("pid", p_id);

                                    intent.putExtra("ptitle", projectTitle);
                                    intent.putExtra("status", "0");
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", b_id);
                params.put("project_id", p_id);
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

                params.put("userId", pref.getString("user_id",""));

                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardScreen.this.getApplicationContext());
        requestQueue.add(request);
    }
    private void archiveBoard() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, com.app.devrah.Network.End_Points.ARCHIVE_BOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if(!response.equals("0") && !response.equals("notexist") && !response.equals("notCompleted")){
                            Intent intent = new Intent(BoardScreen.this, BoardsActivity.class);
                            intent.putExtra("pid", p_id);

                            intent.putExtra("ptitle", projectTitle);
                            intent.putExtra("status", "0");
                            finish();
                            startActivity(intent);
                        }else if(response.equals("notexist")){
                            Toast.makeText(BoardScreen.this,"No card exists in this board",Toast.LENGTH_LONG).show();
                        }else if(response.equals("notCompleted")){
                            Toast.makeText(BoardScreen.this,"Please complete all cards in the board to archive it",Toast.LENGTH_LONG).show();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("check your internet connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent = new Intent(BoardScreen.this, BoardsActivity.class);
                                    intent.putExtra("pid", p_id);

                                    intent.putExtra("ptitle", projectTitle);
                                    intent.putExtra("status", "0");
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardScreen.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent = new Intent(BoardScreen.this, BoardsActivity.class);
                                    intent.putExtra("pid", p_id);

                                    intent.putExtra("ptitle", projectTitle);
                                    intent.putExtra("status", "0");
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", b_id);
                params.put("project_id", p_id);
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

                params.put("userId", pref.getString("user_id",""));

                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardScreen.this.getApplicationContext());
        requestQueue.add(request);
    }
    private void showDialog(final String data) {

        LayoutInflater inflater = LayoutInflater.from(BoardScreen.this);
        View customView = inflater.inflate(R.layout.move_board_menu, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(BoardScreen.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(customView);
        alertDialog.show();

        TextView heading, sub,headingTitle;

        TextView cancel, copy;
        final EditText ettitle;
        heading = customView.findViewById(R.id.heading);
        headingTitle = customView.findViewById(R.id.title);
        sub = customView.findViewById(R.id.sub_heading);
        Postions = customView.findViewById(R.id.position);
        Projects = customView.findViewById(R.id.projects_group);
        copy = customView.findViewById(R.id.copy);
        cancel = customView.findViewById(R.id.close);
        ettitle = customView.findViewById(R.id.etTitle);
        ettitle.setText(toolbar.getTitle());
        ettitle.setSelection(toolbar.getTitle().length());
        if (data.equals("copy")) {
            setupUI(customView.findViewById(R.id.relativelayout),ettitle);
            showKeyBoard(ettitle);
        }

        getSpinnerData();

        if (data.equals("move")) {
            heading.setText("Move Board");
            sub.setText("Move To Project ");
            copy.setText("Move");
            ettitle.setVisibility(View.GONE);
            headingTitle.setVisibility(View.GONE);
        }


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (data.equals("move")) {
                    moveBoard();
                } else {
                    copyBoard(ettitle.getText().toString());
                }
                hideKeyBoard(ettitle);
                alertDialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyBoard(ettitle);
                alertDialog.dismiss();

            }
        });

    }
    public void setupUI(final View view, final EditText editText) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    view.clearFocus();
                    hideSoftKeyboard(BoardScreen.this,editText);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView,editText);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    public void UpdateBoardName(final String updatedText) {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, com.app.devrah.Network.End_Points.UPDATE_BOARD_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        toolbar.setTitle(updatedText);
                        title=updatedText;
                       // bTitle=title;
                        Toast.makeText(BoardScreen.this, "Board Name Updated Successfully", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(Gravity.END);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("updt_txt", updatedText);
                params.put("board_id", b_id);
                params.put("userId", pref.getString("user_id",""));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(BoardScreen.this);
        requestQueue.add(request);


    }
    public void updateListPosition(final String updatedArrayList) {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        StringRequest request = new StringRequest(Request.Method.POST, com.app.devrah.Network.End_Points.UPDATE_COLOUMN_POSITION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                      //  ringProgressDialog.dismiss();
                   // Toast.makeText(BoardScreen.this,response,Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(BoardScreen.this, SweetAlertDialog.ERROR_TYPE)
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
                //String boardId[]={b_id};
                //String projectId[]={p_id};

                params.put("lists_arr", updatedArrayList);
                params.put("brd_id", b_id);
                params.put("prjct_id",p_id);
                params.put("userId",pref.getString("user_id",""));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(BoardScreen.this);
        requestQueue.add(request);


    }
    public void moveCard(final String cardId, final String listId, final String CardHeading, final int positionOfCard,final int columnPos) {

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.MOVE_NEW_CARD_BY_CARD_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   ringProgressDialog.dismiss();
                        Toast.makeText(BoardScreen.this, "Moved Successfully", Toast.LENGTH_SHORT).show();
                        String[] res=response.split(",");
                        data.get(columnPos).projectsList.get(positionOfCard).setCardId(res[1]);
                        boardAdapter = new SimpleBoardAdapter(getApplicationContext(),BoardScreen.this,data,b_id,p_id,mainModelObjs,boardView);
                        boardView.setAdapter(boardAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardScreen.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                final SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);


                params.put("move_boardsList", b_id);
                params.put("move_boardsList_opt", listId);
                params.put("move_boardsList_pos_opt", String.valueOf(positionOfCard+1));

                params.put("menu_form_mv_cpy_nm_title", CardHeading);
                params.put("u_id", pref.getString("user_id",""));
                params.put("card_id", cardId);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardScreen.this);
        requestQueue.add(request);

    }
    public void getList() {

        ringProgressDialog = ProgressDialog.show(BoardScreen.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, GET_ALL_BOARD_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                      //  ringProgressDialog.dismiss();
                        mainModelObjs = new ArrayList<>();
                        if (response.equals("false")) {
                            ringProgressDialog.dismiss();

                        } else {


                            try {
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    MainModelObj mainModelObj = new MainModelObj();
                                    mainModelObj.setId(jsonObject.getString("list_id"));
                                    mainModelObj.setList_name(jsonObject.getString("list_name"));
                                    mainModelObj.setList_color(jsonObject.getString("list_color"));

                                    mainModelObjs.add(mainModelObj);
                                }
                                if(mainModelObjs.size()>0) {
                                    getCardList(mainModelObjs.get(0).getId(),mainModelObjs.get(0).getList_name(), 0);
                                    pos++;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();


                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardScreen.this, "TimeOut Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("board_id", b_id);
                params.put("project_id", p_id);
                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardScreen.this);
        requestQueue.add(request);
    }

    public void getCardList(final String lsitId,final String coloumName, final int position) {

      /* final ProgressDialog ringProgressDialog = ProgressDialog.show(getContext(), "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();*/
        final SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, GET_CARDS_FOR_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  ringProgressDialog.dismiss();
                        if (!(response.equals("false"))) {

                            listPojo = new ArrayList<>();
                            // labelsPojoList = new ArrayList<>();
                            mItemArray = new ArrayList<>();

                            try {
                                //  final int column = mColumns;
                                ChildObjModel projectsPojo = null;
                                JSONArray mainObject = new JSONArray(response);
                                //JSONObject cardsObject = jsonArray.getJSONObject(0);
                                //    mItemArray = new ArrayList<>();
                                for (int i = 0; i < mainObject.length(); i++) {
                                    cardLabelsPojoList = new ArrayList<>();
                                    cardMembersPojoList = new ArrayList<>();
                                    cardCoverPojoList = new ArrayList<>();
                                    cardCheckboxPojoList = new ArrayList<>();
                                    JSONObject jsonObject = mainObject.getJSONObject(i);
                                    JSONArray jsonArrayCards = jsonObject.getJSONArray("cards");
                                    JSONArray jsonArrayLabels = jsonObject.getJSONArray("labels");
                                    JSONArray jsonArrayMembers = jsonObject.getJSONArray("members");
                                    JSONArray jsonArrayAttachments = jsonObject.getJSONArray("attachments");
                                    JSONArray jsonArrayAttachmentsCover = jsonObject.getJSONArray("attachment_cover");
                                    JSONArray jsonArrayCheckbox = jsonObject.getJSONArray("checkbox");
                                    projectsPojo = new ChildObjModel();
                                    JSONObject jsonObjectCards = jsonArrayCards.getJSONObject(0);
                                    projectsPojo.setCardId(jsonObjectCards.getString("id"));
                                    projectsPojo.setCard_name(jsonObjectCards.getString("card_name"));
                                    projectsPojo.setAttachment(jsonObjectCards.getString("file_name"));
                                    projectsPojo.setCard_end_date(jsonObjectCards.getString("card_end_date"));
                                    projectsPojo.setCard_due_time(jsonObjectCards.getString("card_due_time"));
                                    projectsPojo.setCard_start_date(jsonObjectCards.getString("card_start_date"));
                                    projectsPojo.setCardCompletionDate(jsonObjectCards.getString("card_completion_date"));
                                    projectsPojo.setCard_description(jsonObjectCards.getString("card_description"));
                                    projectsPojo.setCard_is_complete(jsonObjectCards.getString("card_is_complete"));
                                    projectsPojo.setCard_start_time(jsonObjectCards.getString("card_start_time"));
                                    projectsPojo.setIs_locked(jsonObjectCards.getString("is_locked"));
                                    projectsPojo.setSubscribed(jsonObjectCards.getString("subscribed"));
                                    projectsPojo.setList_id(jsonObjectCards.getString("list_id"));
                                    projectsPojo.setListName(coloumName);
                                    projectsPojo.setColumnPosition(i);

                                    // projectsPojo.setCardAssignedMemberId(jsonObject.getString("crd_assigned_membr_id"));
                                    projectsPojo.setnOfAttachments(String.valueOf(jsonArrayAttachments.length()));
                                    projectsPojo.setAssigned_to(jsonObjectCards.getString("assigned_to"));
                                    //      long id = sCreatedItems++;
                                  /*  String ids = jsonObject.getString("id");
                                    long id1 = Integer.valueOf(ids);*/
                                    //   mItemArray.add(new Pair<>(id, jsonObjectCards.getString("card_name")));
                                    for (int j = 0; j < jsonArrayLabels.length(); j++) {
                                        CardAssociatedLabelsPojo labelsPojo = new CardAssociatedLabelsPojo();
                                        JSONObject jsonArray = jsonArrayLabels.getJSONObject(j);
                                        labelsPojo.setLabels(jsonArray.getString("label_color"));
                                        labelsPojo.setLabelText(jsonArray.getString("label_text"));
                                        cardLabelsPojoList.add(labelsPojo);
                                    }
                                    projectsPojo.setLablesList(cardLabelsPojoList);
                                    String subsribed = "";
                                    String isCurrentMemberAssigned = "0";
                                    for (int j = 0; j < jsonArrayMembers.length(); j++) {
                                        CardAssociatedMembersPojo membersPojo = new CardAssociatedMembersPojo();
                                        JSONObject jsonArray = jsonArrayMembers.getJSONObject(j);
                                        if (jsonArray.getString("uid").equals(pref.getString("user_id",""))) {
                                            subsribed = jsonArray.getString("subscribed");
                                            isCurrentMemberAssigned = "1";
                                        }
                                        membersPojo.setMembers(jsonArray.getString("profile_pic"));
                                        membersPojo.setInitials(jsonArray.getString("initials"));
                                        membersPojo.setGp_pictures(jsonArray.getString("gp_picture"));
                                        membersPojo.setMemberSubscribed(subsribed);
                                        membersPojo.setMemberId(jsonArray.getString("uid"));
                                        membersPojo.setIsCurrentMemberAssigned(isCurrentMemberAssigned);
                                        cardMembersPojoList.add(membersPojo);
                                    }
                                    projectsPojo.setMemberSubscribed(subsribed);
                                    projectsPojo.setMembersList(cardMembersPojoList);
                                    for (int j = 0; j < jsonArrayAttachmentsCover.length(); j++) {
                                        CardAssociatedCoverPojo membersPojo = new CardAssociatedCoverPojo();
                                        JSONObject jsonArray = jsonArrayAttachmentsCover.getJSONObject(j);

                                        membersPojo.setFileName(jsonArray.getString("file_name"));
                                        membersPojo.setIsCover(jsonArray.getString("make_cover"));
                                        //  labelsPojo.setLabelText(labelText);
                                        cardCoverPojoList.add(membersPojo);
                                    }
                                    projectsPojo.setCoverList(cardCoverPojoList);
                                    int checked = 0;
                                    for (int j = 0; j < jsonArrayCheckbox.length(); j++) {
                                        CardAssociatedCheckBox checkBoxPojo = new CardAssociatedCheckBox();
                                        JSONObject jsonArray = jsonArrayCheckbox.getJSONObject(j);
                                        checkBoxPojo.setTotalCheckBoxes(jsonArrayCheckbox.length());

                                       /* for (int k = 0; k < jsonArray.length(); k++) {
                                           JSONObject jsonObject = jsonArray.getJSONObject(k);*/
                                        if (jsonArray.getString("is_checked").equals("1")) {
                                            checked++;
                                        }
                                        //}
                                        checkBoxPojo.setCheckedCheckBox(String.valueOf(checked));
                                        cardCheckboxPojoList.add(checkBoxPojo);
                                    }
                                    projectsPojo.setCheckBoxes(cardCheckboxPojoList);
                                    listPojo.add(projectsPojo);
                                }
                                MainModelObj mainModelObj = new MainModelObj();
                                mainModelObj.setList_name(coloumName);
                                mainModelObj.setList_color("");
                                mainModelObj.setChildObjModels(listPojo);
                               // mainModelObjs.add(mainModelObj);
                                data.add(new SimpleBoardAdapter.SimpleColumn(coloumName,listPojo,mainModelObjs));

                                boardAdapter = new SimpleBoardAdapter(getApplicationContext(),BoardScreen.this,data,b_id,p_id,mainModelObjs,boardView);
                                boardView.setAdapter(boardAdapter);
                                if(pos==mainModelObjs.size()){
                                    ringProgressDialog.dismiss();
                                }
                                if(pos<mainModelObjs.size()){
                                    getCardList(mainModelObjs.get(pos).getId(),mainModelObjs.get(pos).getList_name(),pos);
                                    pos++;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();


                            }

                        }
                        if(mainModelObjs.size()==0){
                            ringProgressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                final SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("board_id", b_id);
                params.put("project_id", p_id);
                params.put("userId", pref.getString("user_id",""));
                params.put("list_id", lsitId);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardScreen.this);
        requestQueue.add(request);

    }
    public void getSpinnerData() {


        spinnerValues = new ArrayList<>();
        spinnerGroupIds = new ArrayList<>();
        postions_list = new ArrayList<>();


        StringRequest request = new StringRequest(Request.Method.POST, com.app.devrah.Network.End_Points.GET_ALL_PROJECS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = new JSONObject(array.getString(i));
                                spinnerValues.add(String.valueOf(object.get("project_name")));
                                spinnerGroupIds.add(String.valueOf(object.get("project_id")));

                            }

                            ArrayAdapter<String> projectADdapter;
                            projectADdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.nothing_selected_spinnerdate, spinnerValues);
                            projectADdapter.setDropDownViewResource(R.layout.nothing_selected_spinnerdate);
                            Projects.setAdapter(projectADdapter);
                            Projects.setOnItemSelectedListener(new CustomOnItemSelectedListener_boards());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardScreen.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userId = pref.getString("user_id", "");

                params.put("userId", userId);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    public class CustomOnItemSelectedListener_boards implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, final int pos,
                                   long id) {

            getPosition(spinnerGroupIds.get(pos));



        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }


    public void getPosition(final String P_id) {

        StringRequest request = new StringRequest(Request.Method.POST, com.app.devrah.Network.End_Points.GETPOSITION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        postions_list = new ArrayList<>();
                        if(response.equals("0")){
                            postions_list.add("1"+"");
                        }
                        for (int i = 1; i <=Integer.valueOf(response)+1 && !response.equals("0"); i++) {

                            postions_list.add(i+"");
                        }

                        ArrayAdapter<String> projectADdapter;
                        projectADdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.nothing_selected_spinnerdate, postions_list);
                        projectADdapter.setDropDownViewResource(R.layout.nothing_selected_spinnerdate);
                        Postions.setAdapter(projectADdapter);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardScreen.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("p_id", P_id);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    public void moveBoard() {


        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, com.app.devrah.Network.End_Points.MOVE_BORAD_TO_OTHER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        Toast.makeText(BoardScreen.this, "Moved Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent1 = getIntent();
                        if(intent1.hasExtra("ScreenName")){
                            Intent intent = new Intent(BoardScreen.this, NotificationsActivity.class);
                            finish();
                            startActivity(intent);
                        }else if(intent1.hasExtra("activity")){
                            Intent intent = new Intent(BoardScreen.this, FavouritesActivity.class);
                            finish();
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(BoardScreen.this, BoardsActivity.class);
                            intent.putExtra("pid", p_id);

                            intent.putExtra("ptitle", projectTitle);
                            intent.putExtra("status", "0");
                            finish();
                            startActivity(intent);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardScreen.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                int pos1 = Postions.getSelectedItemPosition();
                int pos = Projects.getSelectedItemPosition();
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);


                params.put("originalboardid", b_id);
                params.put("originalprojectid", p_id);
                params.put("project_id_to", spinnerGroupIds.get(pos));
                params.put("position_to", postions_list.get(pos1));
                params.put("userId",pref.getString("user_id",""));
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    public void copyBoard(final String boardName) {


        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, com.app.devrah.Network.End_Points.COPY_BORAD_TO_OTHER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        Toast.makeText(BoardScreen.this, "Copied Successfully", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardScreen.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardScreen.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                int pos = Projects.getSelectedItemPosition();
                int pos1 = Postions.getSelectedItemPosition();
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);


                params.put("originalboardid", b_id);
                params.put("originalprojectid", p_id);
                params.put("project_id_to", spinnerGroupIds.get(pos));
                params.put("position_to", postions_list.get(pos1));
                params.put("userId",pref.getString("user_id",""));
                params.put("board_name",boardName);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }
    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            handleMenuSearch();
            return;
        } else if (isEditOpened) {
            final TextView tv = toolbar.findViewById(R.id.toolbar_title);
            tv.clearFocus();
            tv.setCursorVisible(false);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
            UpdateBoardName(tv.getText().toString());
            isEditOpened = false;
            return;
        } else{
            Intent intent1 = getIntent();
            if(intent1.hasExtra("ScreenName")){
                Intent intent = new Intent(BoardScreen.this, NotificationsActivity.class);
                finish();
                startActivity(intent);
            }else if(intent1.hasExtra("activity")){
                Intent intent = new Intent(BoardScreen.this, FavouritesActivity.class);
                finish();
                startActivity(intent);
            }else {
                Intent intent = new Intent(BoardScreen.this, BoardsActivity.class);
                intent.putExtra("pid", p_id);

                intent.putExtra("ptitle", projectTitle);
                intent.putExtra("status", "0");
                finish();
                startActivity(intent);
            }
        }
        super.onBackPressed();
    }
}
