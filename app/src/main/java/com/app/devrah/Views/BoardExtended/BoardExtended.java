package com.app.devrah.Views.BoardExtended;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
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
import com.app.devrah.Adapters.CustomViewPagerAdapter;
import com.app.devrah.Network.End_Points;
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
import com.app.devrah.pojo.ProjectsPojo;
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

public class BoardExtended extends AppCompatActivity {
    ListView lv;
    ArrayList<CalendarPojo> listPojo;
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    ProgressDialog ringProgressDialog;
    private boolean isEditOpened = false;
    ParentBoardExtendedFragment fragment;
    //FragmentBoardExtendedLast lastFrag;
    Spinner Projects,Postions;
    Toolbar toolbar;
    String title;
    CalendarAdapter adapter;
    View logo;
    List<DrawerPojo> dataList;
    String b_id, p_id,projectTitle,list_id;
    public static String projectId;
    public static String boardId;
    public static String bTitle;
    public static String pTitle;
    CustomDrawerAdapter DrawerAdapter;
    List<String> spinnerValues;
    List<String> spinnerGroupIds;
    List<String> postions_list;
    CalendarAdapter adapter1;
     //   NavigationDrawerFragment drawerFragment;
//    private int[] tabIcons = {
//            R.drawable.project_group,
//            R.drawable.bg_dashboard_project,
//          //  R.drawable.ic_tab_contacts
//    };
    DrawerLayout drawerLayout;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private ListView mDrawerList;
    public static BoardExtended Mactivity;
    public static Menu menu;
    Boolean Cancelbtn=false;
    CompactCalendarView compactCalendarView;
    List<CardAssociatedCalendarLabelsPojo> cardLabelsPojoList;
    List<CardAssociatedCalendarMembersPojo> cardMembersPojoList;
    List<CardAssociatedCalendarCoverPojo> cardCoverPojoList;
    List<CardAssociatedCalendarCheckBox> cardCheckboxPojoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_extended);
        getList();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        Intent intent = this.getIntent();
        b_id = intent.getStringExtra("b_id");
        p_id = intent.getStringExtra("p_id");
     //   list_id = intent.getStringExtra("list_id");

        projectTitle = intent.getStringExtra("ptitle");
        projectId=p_id;
        boardId=b_id;
        pTitle=projectTitle;
      //  pTitle=projectTitle;


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        title = getIntent().getStringExtra("TitleData");
        bTitle=title;
        toolbar.setTitle(title);
        //toolbar.setTitle(title);
       // final TextView tv= (TextView) toolbar.findViewById(R.id.toolbar_title);
        //tv.setText(title);
        //tv.setCursorVisible(false);
        //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
       /* tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditOpened = false;
                tv.setCursorVisible(true);
                tv.setFocusableInTouchMode(true);
                tv.setInputType(InputType.TYPE_CLASS_TEXT);
                tv.requestFocus();
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.menu);
                toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cancel));
                Cancelbtn=true;
               // menuChanger(menu,true);
                //tv.setText(tv.getText().toString());
            }
        });
        tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId==6 ) {
                    String check=tv.getText().toString();
                    if(!check.equals("")) {
                        isEditOpened = false;
                        tv.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
                        UpdateBoardName(tv.getText().toString());
                        toolbar.getMenu().clear();
                        toolbar.inflateMenu(R.menu.menu_with_back_button);
                        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
                    }else {
                        Toast.makeText(BoardExtended.this,"Board Name is must!",Toast.LENGTH_LONG).show();
                    }

                }
                return true;
            }
        });*/
        // adapter = new CustomViewPagerAdapter(getFragmentManager(),getApplicationContext(),)
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        logo = getLayoutInflater().inflate(R.layout.search_bar, null);
        toolbar.addView(logo);
        logo.setVisibility(View.INVISIBLE);

        dataList = new ArrayList<>();

        dataList.add(new DrawerPojo("Update Board Name"));
        dataList.add(new DrawerPojo("Copy Board"));
        dataList.add(new DrawerPojo("Move Board"));
        dataList.add(new DrawerPojo("Manage Members"));
        dataList.add(new DrawerPojo("Leave Board"));
        dataList.add(new DrawerPojo("Delete Board"));
        dataList.add(new DrawerPojo("Calendar"));


        edtSeach = (EditText) toolbar.findViewById(R.id.edtSearch);
        toolbar.inflateMenu(R.menu.menu_with_back_button);
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
                        final TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
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
                            Toast.makeText(BoardExtended.this,"Board Name is must!",Toast.LENGTH_LONG).show();
                        }
                        return true;

                }

                return true;
            }
        });
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Cancelbtn){
                    final TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
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
                        Intent intent = new Intent(BoardExtended.this, NotificationsActivity.class);
                        finish();
                        startActivity(intent);
                    }else if(intent1.hasExtra("activity")){
                        Intent intent = new Intent(BoardExtended.this, FavouritesActivity.class);
                        finish();
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(BoardExtended.this, BoardsActivity.class);
                        intent.putExtra("pid", p_id);
                        //projectTitle=BoardsActivity.ptitle;
                        intent.putExtra("ptitle", pTitle);
                        intent.putExtra("status", "0");
                        finish();
                        startActivity(intent);
                    }
                    onBackPressed();
                }
            }
        });

        fragment = new ParentBoardExtendedFragment();
        fragment = (ParentBoardExtendedFragment) this.getSupportFragmentManager().findFragmentById(R.id.ajeebFrag);

//        bundle = new Bundle();
//
//        bundle.putString("b_id",b_id);
//        bundle.putString("p_id",p_id);
//        bundle.putString("list_id",list_id);
//        bundle.putString("ptitle",projectTitle);



        //..    lastFrag = (FragmentBoardExt
        //
        // endedLast)this.getSupportFragmentManager().;
        //  etPageName =(EditText)findViewById(R.id.editTextPageName);
        //  addFrag = (Button)findViewById(R.id.btnAddFrag);

        openDrawer();

       /* fragment.addPage("To Do");
        fragment.addPage("Doing");
        fragment.addPage("Done");*/
//      //  fragment.addPage("Add Page");

//Toast.makeText(getApplicationContext(),String.valueOf(),Toast.LENGTH_SHORT).show();  ;
        //CustomView PagerAdapter last index
//        addFrag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!etPageName.getText().toString().equals("")) {
//                    fragment.addPage(etPageName.getText() + "");
//                  //  etPageName.setText(etPageName.getText() + "");
//                } else {
//                    Toast.makeText(BoardExtended.this, "Page name is empty", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
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

    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            handleMenuSearch();
            return;
        } else if (isEditOpened) {
            final TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
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
                Intent intent = new Intent(BoardExtended.this, NotificationsActivity.class);
                finish();
                startActivity(intent);
            }else if(intent1.hasExtra("activity")){
                Intent intent = new Intent(BoardExtended.this, FavouritesActivity.class);
                finish();
                startActivity(intent);
            }else {
                Intent intent = new Intent(BoardExtended.this, BoardsActivity.class);
                intent.putExtra("pid", p_id);

                intent.putExtra("ptitle", pTitle);
                intent.putExtra("status", "0");
                finish();
                startActivity(intent);
            }
        }
        super.onBackPressed();
    }

    public void doSearch() {
//
    }
    private void updateBoardNameDialog() {
        LayoutInflater inflater = LayoutInflater.from(BoardExtended.this);
        View customView = inflater.inflate(R.layout.update_card_name_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(BoardExtended.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(customView);
        alertDialog.show();

        Button cancel, copy;
        final EditText etCardName= (EditText) customView.findViewById(R.id.etCardName);
        final TextView tvheading= (TextView) customView.findViewById(R.id.heading);
        tvheading.setText("Update Board Name");
        etCardName.setText(title);
        etCardName.setSelection(etCardName.getText().length());
        showKeyBoard(etCardName);
        copy = (Button) customView.findViewById(R.id.copy);
        cancel = (Button) customView.findViewById(R.id.close);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check=etCardName.getText().toString();
                if(!check.equals("") && check!="" && check.trim().length()>0) {
                    UpdateBoardName(etCardName.getText().toString());
                    hideKeyBoard(etCardName);
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(BoardExtended.this,"Board Name is must!",Toast.LENGTH_LONG).show();
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

    public void openDrawer() {
        DrawerAdapter = new CustomDrawerAdapter(this, R.layout.list_item_drawer, dataList);
        mDrawerList.setAdapter(DrawerAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {

                    case 0:
                        updateBoardNameDialog();
                        break;


                    case 1:

                        showDialog("copy");
                        break;


                    case 2:

                        showDialog("move");
                        break;


                    case 3:

                        Intent intent = new Intent(BoardExtended.this, Manage_Board_Members.class);
                        intent.putExtra("P_id",p_id);
                        intent.putExtra("b_id",b_id);
                        startActivity(intent);

                        break;

                    case 4:
                        new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.WARNING_TYPE)
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

                    case 5:
                        new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.WARNING_TYPE)
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
                    case 6:
                        showCalendarDialog();

                        break;

                }
            }
        });

    }
private void getDueDates(final String currentDate){
    StringRequest request = new StringRequest(Request.Method.POST,End_Points.GET_DUE_DATE,
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


                Toast.makeText(BoardExtended.this, "check your internet connection", Toast.LENGTH_SHORT).show();

            } else if (error instanceof TimeoutError) {


                Toast.makeText(BoardExtended.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
            }
        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

            Map<String, String> params = new HashMap<>();

            final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
            params.put("uid",  pref.getString("user_id",""));
            params.put("project_id", projectId);
            params.put("board_id", boardId);


            // params.put("password",strPassword );
            return params;
        }
    };

    request.setRetryPolicy(new DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    RequestQueue requestQueue = Volley.newRequestQueue(BoardExtended.this.getApplicationContext());
    requestQueue.add(request);
}
    private void showCalendarDialog() {
        final SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

        LayoutInflater inflater = LayoutInflater.from(BoardExtended.this);
        View customView = inflater.inflate(R.layout.calendar_view_dialog, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(BoardExtended.this,R.style.full_screen_dialog).create();
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        alertDialog.getWindow().setLayout(320, DrawerLayout.LayoutParams.MATCH_PARENT);

        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        wmlp.gravity = Gravity.END;
        alertDialog.setView(customView);
        alertDialog.show();
        final TextView heading= (TextView) customView.findViewById(R.id.header);
        final ImageView backBtn= (ImageView) customView.findViewById(R.id.backBtnCalender);
        compactCalendarView = (CompactCalendarView)customView.findViewById(R.id.compactcalendar_view);
        lv= (ListView) customView.findViewById(R.id.listView);
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
                    Toast.makeText(BoardExtended.this,"No cards due on "+cardsByDate,Toast.LENGTH_LONG).show();
                }else {
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String cardsByDate=dateFormat.format(dateClicked);
                    //getCards(cardsByDate);
                    for(int i=listPojo.size()-1;i>=0;i--){
                        if(listPojo.get(i).getDueDate().equals(cardsByDate)){
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
   /* public void updateChildFragmentsCardData(String id,String isCardComplete){
        fragment.updateChildFragmentData(id,isCardComplete);
    }*/
    private void getCards(final  String dueDate,final AlertDialog alertDialog){
        final SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.GETMYCARDSBYDUEDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if (!(response.equals("false"))) {
                            listPojo = new ArrayList<>();
                            cardLabelsPojoList = new ArrayList<>();
                            cardMembersPojoList = new ArrayList<>();
                            cardCoverPojoList = new ArrayList<>();
                            cardCheckboxPojoList = new ArrayList<>();
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

                                    listPojo.add(projectsPojo);
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
                                    cardLabelsPojoList.add(labelsPojo);
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
                                    cardMembersPojoList.add(membersPojo);
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
                                    cardCoverPojoList.add(membersPojo);
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
                                    cardCheckboxPojoList.add(checkBoxPojo);
                                }

                                adapter = new CalendarAdapter(BoardExtended.this, listPojo,cardLabelsPojoList,cardMembersPojoList,cardCoverPojoList,0,alertDialog,cardCheckboxPojoList);
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


                    Toast.makeText(BoardExtended.this, "check your internet connection", Toast.LENGTH_SHORT).show();

                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardExtended.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("uid",  pref.getString("user_id",""));
                params.put("project_id",projectId);
                params.put("board_id", boardId);
                params.put("card_due_date",dueDate);


                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardExtended.this.getApplicationContext());
        requestQueue.add(request);
    }

    private void LeaveBoard() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.LEAVE_BOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                            if(response.equals("2"))
                            {
                                Toast.makeText(BoardExtended.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.equals("1"))
                            {
                                Toast.makeText(BoardExtended.this, "you are admin you cannot leave this Board", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(BoardExtended.this, "Board Left Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent1 = getIntent();
                                if(intent1.hasExtra("ScreenName")){
                                    Intent intent = new Intent(BoardExtended.this, NotificationsActivity.class);
                                    finish();
                                    startActivity(intent);
                                }else if(intent1.hasExtra("activity")){
                                    Intent intent = new Intent(BoardExtended.this, FavouritesActivity.class);
                                    finish();
                                    startActivity(intent);
                                }else {
                                    Intent intent = new Intent(BoardExtended.this, BoardsActivity.class);
                                    intent.putExtra("pid", p_id);
                                    //projectTitle=BoardsActivity.ptitle;
                                    intent.putExtra("ptitle", pTitle);
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


                    Toast.makeText(BoardExtended.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent=new Intent(BoardExtended.this,BoardsActivity.class);
                                    intent.putExtra("pid",p_id);
                                    intent.putExtra("ptitle",projectTitle);
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardExtended.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent=new Intent(BoardExtended.this,BoardsActivity.class);
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
        RequestQueue requestQueue = Volley.newRequestQueue(BoardExtended.this.getApplicationContext());
        requestQueue.add(request);

    }

    private void deleteBoard() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.DELETE_BOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if(!response.equals("0")){
                            Intent intent = new Intent(BoardExtended.this, BoardsActivity.class);
                            intent.putExtra("pid", p_id);

                            intent.putExtra("ptitle", pTitle);
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


                    Toast.makeText(BoardExtended.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("check your internet connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent = new Intent(BoardExtended.this, BoardsActivity.class);
                                    intent.putExtra("pid", p_id);

                                    intent.putExtra("ptitle", pTitle);
                                    intent.putExtra("status", "0");
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardExtended.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent = new Intent(BoardExtended.this, BoardsActivity.class);
                                    intent.putExtra("pid", p_id);

                                    intent.putExtra("ptitle", pTitle);
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
        RequestQueue requestQueue = Volley.newRequestQueue(BoardExtended.this.getApplicationContext());
        requestQueue.add(request);
    }
    private void showDialog(final String data) {

        LayoutInflater inflater = LayoutInflater.from(BoardExtended.this);
        View customView = inflater.inflate(R.layout.move_board_menu, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(BoardExtended.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(customView);
        alertDialog.show();

        TextView heading, sub,headingTitle;

        TextView cancel, copy;
        final EditText ettitle;
        heading = (TextView) customView.findViewById(R.id.heading);
        headingTitle = (TextView) customView.findViewById(R.id.title);
        sub = (TextView) customView.findViewById(R.id.sub_heading);
        Postions = (Spinner) customView.findViewById(R.id.position);
        Projects = (Spinner) customView.findViewById(R.id.projects_group);
        copy = (TextView) customView.findViewById(R.id.copy);
        cancel = (TextView) customView.findViewById(R.id.close);
        ettitle = (EditText) customView.findViewById(R.id.etTitle);
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
                    hideSoftKeyboard(BoardExtended.this,editText);
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
    public void getList() {

        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, GET_ALL_BOARD_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        if (response.equals("false")) {
                            ParentBoardExtendedFragment.removeAllFrags();
                            ParentBoardExtendedFragment.addPageAt(CustomViewPagerAdapter.customCount());

                        } else {
                            ParentBoardExtendedFragment.removeAllFrags();

                            try {
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    ProjectsPojo projectsPojo = new ProjectsPojo();

                                    projectsPojo.setId(jsonObject.getString("list_id"));
                                    projectsPojo.setData(jsonObject.getString("list_name"));
                                    projectsPojo.setListId(jsonObject.getString("list_id"));
                                    projectsPojo.setListColor(jsonObject.getString("list_color"));

                                    ParentBoardExtendedFragment.addPage(jsonObject.getString("list_name"),p_id,b_id,jsonObject.getString("list_id"),jsonObject.getString("list_color"),"");
                               //     parentBoardExtendedFragment.setArguments(bundle);

                                }


                                ParentBoardExtendedFragment.addPageAt(CustomViewPagerAdapter.customCount());


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


                    Toast.makeText(BoardExtended.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("check your internet connectionn")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent = new Intent(BoardExtended.this, BoardsActivity.class);
                                    intent.putExtra("pid", p_id);

                                    intent.putExtra("ptitle", pTitle);
                                    intent.putExtra("status", "0");
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(BoardExtended.this, "TimeOut Error", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Intent intent = new Intent(BoardExtended.this, BoardsActivity.class);
                                    intent.putExtra("pid", p_id);

                                    intent.putExtra("ptitle", pTitle);
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
                // params.put("password",strPassword );
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BoardExtended.this.getApplicationContext());
        requestQueue.add(request);
    }


    public void UpdateBoardName(final String updatedText) {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_BOARD_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        toolbar.setTitle(updatedText);
                        title=updatedText;
                        bTitle=title;
                        Toast.makeText(BoardExtended.this, "Board Name Updated Successfully", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(Gravity.END);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(BoardExtended.this, SweetAlertDialog.ERROR_TYPE)
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


        RequestQueue requestQueue = Volley.newRequestQueue(BoardExtended.this);
        requestQueue.add(request);


    }
    public void getSpinnerData() {


        spinnerValues = new ArrayList<>();
        spinnerGroupIds = new ArrayList<>();
        postions_list = new ArrayList<>();


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_ALL_PROJECS,
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

                    Toast.makeText(BoardExtended.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardExtended.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

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

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GETPOSITION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            postions_list = new ArrayList<>();
                            if(response.equals("0")){
                                postions_list.add("1"+"");
                            }
                            for (int i = 1; i <=Integer.valueOf(response)+1; i++) {

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

                    Toast.makeText(BoardExtended.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardExtended.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

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

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.MOVE_BORAD_TO_OTHER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        Toast.makeText(BoardExtended.this, "Moved Successfully", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardExtended.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardExtended.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

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

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.COPY_BORAD_TO_OTHER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        Toast.makeText(BoardExtended.this, "Copied Successfully", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {

                    Toast.makeText(BoardExtended.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(BoardExtended.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

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

}
