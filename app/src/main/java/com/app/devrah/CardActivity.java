package com.app.devrah;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.devrah.Adapters.AdapterMembers;
import com.app.devrah.Adapters.RVadapterCheckList;
import com.app.devrah.Adapters.RecyclerViewAdapterComments;
import com.app.devrah.pojo.CardCommentData;
import com.app.devrah.pojo.MembersPojo;
import com.app.devrah.pojo.ProjectsPojo;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CardActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;




    LinearLayout LACheckList,staticCheckList;
    //ProjectsAdapter adapter;

    ProjectsPojo projectPojoData;
    String  projectData;
    ListView lv;
    List<MembersPojo> membersPojoList;
    CheckBox cbDueDate;
    RelativeLayout rvCard;
    FloatingActionButton FABdueDate,FABmembers,FABattachments,FABchecklist;
    Toolbar  toolbar;
    RVadapterCheckList rVadapterCheckList;

    Spinner spinnerDate,spinnerTime;
    List<String> spinnerDateList,spinnertTimeList;
    Activity activity;
    private FloatingActionMenu fabm;
        EditText editText,etCheckList;
    String CardHeading;
    Button addBtn,checklistAddBtn;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    TextView tvMembers;


    RecyclerViewAdapterComments adapter;
    RVadapterCheckList rvAdapterChecklist;

    RecyclerView rv,rvChecklist;
    List<CardCommentData> listPojo;
    List<ProjectsPojo> checkListPojo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

      listPojo = new ArrayList<>();
        checkListPojo = new ArrayList<>();
        membersPojoList = new ArrayList<>();


        LACheckList = (LinearLayout)findViewById(R.id.LinearLayoutChecklist);
        rvCard = (RelativeLayout)findViewById(R.id.bg_card);
//        tvDue = (TextView)findViewById(R.id.tvDue);

        staticCheckList = (LinearLayout)findViewById(R.id.linearLayoutCheckboxHeading);
        cbDueDate = (CheckBox) findViewById(R.id.tvDue);
        etCheckList = (EditText) findViewById(R.id.etCheckBox);
        checklistAddBtn = (Button)findViewById(R.id.addChecklist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvChecklist = (RecyclerView)findViewById(R.id.rv_recycler_checklist);
        tvMembers = (TextView)findViewById(R.id.tvMembers);


        tvMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMembersDialog();
            }
        });



      //  checklistAddBtn = (Button)findViewById(R.id.)
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar);
        FABdueDate = (FloatingActionButton)findViewById(R.id.dueDate);
        FABmembers = (FloatingActionButton)findViewById(R.id.members);
        FABattachments = (FloatingActionButton)findViewById(R.id.attachments);
        FABchecklist = (FloatingActionButton)findViewById(R.id.CheckList);

       // intent.getStringExtra("CardHeaderData");


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        CardHeading = getIntent().getStringExtra("CardHeaderData");
        collapsingToolbarLayout.setTitle(CardHeading);

        editText = (EditText)findViewById(R.id.etComment);
        addBtn = (Button)findViewById(R.id.addDatainPojo);




        FABattachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
                View view = inflater.inflate(R.layout.custom_attachments_layout_dialog,null);


                AlertDialog alertDialog = new AlertDialog.Builder(CardActivity.this).create();

                LinearLayout linearLayoutCamera = (LinearLayout)view.findViewById(R.id.linearLayoutCamera);

                linearLayoutCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);


                    }
                });



                alertDialog.setView(view);
                alertDialog.show();




            }
        });


        FABchecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LACheckList.setVisibility(View.VISIBLE);
                staticCheckList.setVisibility(View.VISIBLE);
                fabm.close(true);


            }
        });



        FABmembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMembersDialog();
                fabm.close(true);
            }
        });


        checklistAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProjectsPojo cardCommentData = new ProjectsPojo();
                //  cardCommentData.setCardCommentid("Id Ov Person");
//                cardCommentData.setCardName(CardHeading);
//                cardCommentData.setComment(editText.getText().toString());
//               // listPojo.add(cardCommentData);



                cardCommentData.setData(etCheckList.getText().toString());
                checkListPojo.add(cardCommentData);
                // adapter = new RecyclerViewAdapterComments();
                //  adapter = new RecyclerViewAdapterComments();

                rvChecklist.setLayoutManager(layoutManager);

                rvAdapterChecklist = new RVadapterCheckList(checkListPojo, getApplicationContext());
                rvChecklist.setAdapter(rvAdapterChecklist);

                rvAdapterChecklist.notifyDataSetChanged();
              //  adapter.notifyDataSetChanged();

            }
        });

        FABdueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Due Date Button Pressed",Toast.LENGTH_LONG).show();



                LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
                View customView = inflater.inflate(R.layout.custom_date_time_spinner_cards_screen,null);
               // View nothingSelectedDate = inflater.inflate(R.layout.nothing_selected_spinnerdate,null);
                final AlertDialog alertDialog = new AlertDialog.Builder(CardActivity.this).create();


                final Calendar myCurrentDT = Calendar.getInstance();
                TextView tvCancel = (TextView)customView.findViewById(R.id.tvCancel);
                TextView tvDone = (TextView)customView. findViewById(R.id.tvDone);
                spinnerDate = (Spinner)customView.findViewById(R.id.spinnerDate);
                spinnerTime = (Spinner)customView.findViewById(R.id.spinnerTime);


               // spinnerDate.setEmptyView(nothingSelectedDate);
               // spinnerDate.setPrompt("Today");
                spinnerDateList = new ArrayList<>();
                spinnertTimeList = new ArrayList<String>();
                spinnerDateList.add("Select Date");
                spinnerDateList.add("Today");
                spinnerDateList.add("Tomorrow");
                spinnerDateList.add("Pick a day");


                spinnertTimeList.add("Select Time");
                spinnertTimeList.add("Morning");
                spinnertTimeList.add("After Noon");
                spinnertTimeList.add("Evening");
                spinnertTimeList.add("Night");
                spinnertTimeList.add("Pick a time");


                ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(CardActivity.this,R.layout.nothing_selected_spinnerdate,spinnerDateList);
               dateAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(CardActivity.this,R.layout.nothing_selected_spinnerdate,spinnertTimeList);
                timeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinnerDate.setSelection(0);
                spinnerTime.setSelection(0);

                spinnerDate.setAdapter(dateAdapter);
                spinnerTime.setAdapter(timeAdapter);


                final Calendar c = Calendar.getInstance();
               final int mYear = c.get(Calendar.YEAR); // current year
                final int mMonth = c.get(Calendar.MONTH); // current month
                final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                final  int mHour = c.get(Calendar.HOUR);
                final  int mMin = c.get(Calendar.MINUTE);

                spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = parent.getItemAtPosition(position).toString();

                        switch (selectedItem){

                            case "Pick a day":

                                datePickerDialog = new DatePickerDialog(CardActivity.this,
                                        new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {
                                                // set day of month , month and year value in the edit text
//                                                date.setText(dayOfMonth + "/"
//                                                        + (monthOfYear + 1) + "/" + year);
                                                spinnerDateList.remove(0);
                                                spinnerDateList.add(0,dayOfMonth +"/" + monthOfYear + "/" + year);
                                                spinnerDate.setSelection(0);
                                               // spinnerDate.setSelection(spinnerDateList.size() - 1);

                                            }
                                        }, mYear, mMonth, mDay);
                                datePickerDialog.show();
                                break;



                        }





                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



                spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String selectedItem = parent.getSelectedItem().toString();

                        switch (selectedItem) {
                            case "Pick a time":


                            timePickerDialog = new TimePickerDialog(CardActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {



                                    spinnertTimeList.remove(0);
                                    spinnertTimeList.add(0,selectedHour + ":" + selectedMinute);

                                  //  spinnerTime.setSelection(spinnertTimeList.size() - 1);

                                    spinnerTime.setSelection(0);
                                }
                            }, mHour, mMin, true);//Yes 24 hour time
                            timePickerDialog.setTitle("Select Time");
                            timePickerDialog.show();

                            break;
                        }


//                        spinnertTimeList.remove(spinnertTimeList.size() - 1);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });




                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                    }
                });
                tvDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    cbDueDate.setText("Due " + spinnerDateList.get(0) + ", at " + spinnertTimeList.get(0));

                       // tvDue.setText("Due " + spinnerDateList.get(0) + ", at " + spinnertTimeList.get(0));
                        fabm.close(true);
                        cbDueDate.setVisibility(View.VISIBLE);
                        alertDialog.dismiss();

                    }
                });


                alertDialog.setView(customView);
                alertDialog.show();
            }
        });





        //fab1 = (FloatingActionButton)findViewById(R.id.)
       fabm = (FloatingActionMenu)findViewById(R.id.menu);



        rv = (RecyclerView)findViewById(R.id.rv_recycler_view);






        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // CardCommentData card = new CardCommentData("Id of Person",CardHeading,editText.getText().toString());

                CardCommentData cardCommentData = new CardCommentData();
                cardCommentData.setCardCommentid("Id Ov Person");
                cardCommentData.setCardName(CardHeading);
                cardCommentData.setComment(editText.getText().toString());
                listPojo.add(cardCommentData);
           // adapter = new RecyclerViewAdapterComments();
              //  adapter = new RecyclerViewAdapterComments();

                rv.setLayoutManager(layoutManager);

                adapter = new RecyclerViewAdapterComments(listPojo, getApplicationContext());
                rv.setAdapter(adapter);


                adapter.notifyDataSetChanged();
            }
        });
    }
    public void showMembersDialog(){
        LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
        View view = inflater.inflate(R.layout.custom_alertdialog_card_members_layout,null);
        final  AlertDialog alertDialog = new  AlertDialog.Builder(CardActivity.this).create();
        ListView lvMembers = (ListView)view.findViewById(R.id.membersListView);
        TextView tvDone = (TextView)view.findViewById(R.id.addMember);

        MembersPojo membersPojo = new MembersPojo();
        for (int i=0;i<=5;i++) {
            membersPojo.setName("Aqsa");
            membersPojo.setUserId("asdfgh");

            membersPojoList.add(membersPojo);
        }
        final AdapterMembers membersAdapter = new AdapterMembers(CardActivity.this,membersPojoList);

        lvMembers.setAdapter(membersAdapter);


        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



        lvMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  tvMembers.setText(parent.getSelectedItem().toString());


                MembersPojo data = (MembersPojo)membersAdapter.getItem(position);
              //  data.getName();
                tvMembers.setText(data.getName());
                alertDialog.dismiss();


            }
        });

        alertDialog.setView(view);
//
                alertDialog.show();


    }

}
