package com.app.devrah.Views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.app.devrah.Adapters.AdapterMembers;
import com.app.devrah.Adapters.AttachmentImageAdapter;
import com.app.devrah.Adapters.CustomDrawerAdapter;
import com.app.devrah.Adapters.FilesAdapter;
import com.app.devrah.Adapters.RVLabelAdapter;
import com.app.devrah.Adapters.RVLabelResultAdapter;
import com.app.devrah.Adapters.RVadapterCheckList;
import com.app.devrah.Adapters.RecyclerViewAdapterComments;
import com.app.devrah.R;
import com.app.devrah.pojo.AttachmentsPojo;
import com.app.devrah.pojo.CardAssociatedLabelsPojo;
import com.app.devrah.pojo.CardAssociatedMembersPojo;
import com.app.devrah.pojo.CardCommentData;
import com.app.devrah.pojo.ColorsPojo;
import com.app.devrah.pojo.DrawerPojo;
import com.app.devrah.pojo.MembersPojo;
import com.app.devrah.pojo.ProjectsPojo;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.app.devrah.Network.End_Points.GET_LABELS;

public class CardActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    public static View view;
    public static boolean onFocus = false;
    public static Context mcontext;
    public static CardActivity Mactivity;
    public static String labelName;
    public static RelativeLayout container;
    public static ProgressBar simpleProgressBar;
    public static Menu menu;
    public static RecyclerView rv, rvChecklist, rvLabel, rvLabelResult, rvAttachmentImages;
    public static TextView labelAdd;
    static List<String> asliList;
    static List<String> labelNameList;
    static RVLabelAdapter rvAdapterLabel;
    static List<Integer> colorList;
    static List<Integer> listt;
    static List<Integer> resultColorList;
    private static FloatingActionMenu fabm;
    public List<Bitmap> bitmapList;
    CollapsingToolbarLayout collapsingToolbarLayout;
    List<File> fileList;
    List<DrawerPojo> dataList;
    List<MembersPojo> cardMembersPojoList1;
    ListView lvMembers;
    CustomDrawerAdapter DrawerAdapter;
    DrawerLayout drawerLayout;
    AttachmentImageAdapter imageAdapter;
    LinearLayout LACheckList, staticCheckList;
    List<MembersPojo> membersPojoList;
    CheckBox cbDueDate;
    RelativeLayout rvCard;
    FloatingActionButton FABdueDate, FABmembers, FABattachments, FABchecklist, FABLabel;
    Toolbar toolbar;
    RVadapterCheckList rVadapterCheckList;
    Spinner spinnerDate, spinnerTime;
    List<String> spinnerDateList, spinnertTimeList;
    Activity activity;
    EditText etComment, etCheckList;
    String CardHeading;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    TextView tvMembers,heading;
    RecyclerViewAdapterComments adapter;
    RVadapterCheckList rvAdapterChecklist;
    List<CardCommentData> listPojo;
    RecyclerView rvFiles;
    FragmentManager fm;
    List<String> manhusLabelList;
    List<ProjectsPojo> checkListPojo;
    List<AttachmentsPojo> attachmentsList;
    private ListView mDrawerList;
    String cardId;
    List<ProjectsPojo> listPojo1,labelsPojoList;
    List<CardAssociatedLabelsPojo> cardLabelsPojoList;
    List<CardAssociatedMembersPojo> cardMembersPojoList;

    public static void addLabel() {


        menu.clear();
        onFocus = false;
///        labelDone.setVisibility(View.GONE);

        //  Toast.makeText(Mactivity,"Label Done Clicked",Toast.LENGTH_SHORT).show();

        rvLabel.setVisibility(View.GONE);
        labelAdd.setVisibility(View.GONE);
        rvLabelResult.setLayoutManager(new LinearLayoutManager(Mactivity, LinearLayoutManager.HORIZONTAL, true));


        listt = rvAdapterLabel.getData(resultColorList);
        asliList = rvAdapterLabel.getDataString();
        // labelNameList = rvAdapterLabel


//        RVLabelResultAdapter adapter = new RVLabelResultAdapter(Mactivity,listt,labelNameList);
//        rvLabelResult.setAdapter(adapter);
//        menuChanger(menu,onFocus);

//
       /* RVLabelResultAdapter adapter = new RVLabelResultAdapter(Mactivity, listt, asliList);
        rvLabelResult.setAdapter(adapter);*/
        menuChanger(menu, onFocus);


    }

    public static void showLabelsMenu() {

        menuChanger(menu, true);

//            labelDone.setVisibility(View.VISIBLE);
        if (rvLabel.getVisibility() == View.GONE) {
            rvLabel.setVisibility(View.VISIBLE);
            labelAdd.setVisibility(View.VISIBLE);
        }
        if (labelAdd.getVisibility() == View.GONE) {
            labelAdd.setVisibility(View.VISIBLE);


        }

        MenuItem menuItem = menu.findItem(R.id.tick);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Toast.makeText(Mactivity,"Show wala menu",Toast.LENGTH_SHORT).show();
                addLabel();

                return true;
            }
        });

        rvLabel.setLayoutManager(new LinearLayoutManager(mcontext));
        ColorsPojo colorsPojo = new ColorsPojo();
        //   colorList.add(colorsPojo.getColor());
        rvAdapterLabel = new RVLabelAdapter(Mactivity, colorList, listt, labelNameList, asliList);

        rvLabel.setAdapter(rvAdapterLabel);
        asliList = rvAdapterLabel.getDataString();
        fabm.close(true);

        rvAdapterLabel.notifyDataSetChanged();
    }

    public static void menuChanger(Menu menu, boolean hasFocus) {

        menu.clear();
        if (hasFocus) {

            Mactivity.getMenuInflater().inflate(R.menu.menu, menu);
        } else {
            Mactivity.getMenuInflater().inflate(R.menu.my_menu, menu);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        RVLabelAdapter adapter = new RVLabelAdapter();


        Intent intent=  getIntent();

        cardId = intent.getStringExtra("card_id");


        getCardList(cardId);

        labelNameList = new ArrayList<>();
        asliList = new ArrayList<>();
        container = (RelativeLayout) findViewById(R.id.fragmentContainer);
        rvFiles = (RecyclerView) findViewById(R.id.rv_files_cardscreen);
        fileList = new ArrayList<>();
        attachmentsList = new ArrayList<>();
        labelName = null;
        mcontext = getApplicationContext();
        listPojo = new ArrayList<>();
        bitmapList = new ArrayList<>();
        checkListPojo = new ArrayList<>();
        membersPojoList = new ArrayList<>();
        // labelNameList = new ArrayList<>();
        colorList = new ArrayList<>();
        rvAttachmentImages = (RecyclerView) findViewById(R.id.rvImagesAttachment);
        resultColorList = new ArrayList<>();
        listt = new ArrayList<>();
        colorList.add(getResources().getColor(R.color.colorAccent));

        fm = getSupportFragmentManager();
        labelAdd = (TextView) findViewById(R.id.tvAddLabel);
        Mactivity = CardActivity.this;
        colorList.add(getResources().getColor(R.color.colorPrimaryDark));
        colorList.add(getResources().getColor(R.color.colorGreen));

        colorList.add(getResources().getColor(R.color.colorOrangeRed));
        colorList.add(getResources().getColor(R.color.colorOrange));
        colorList.add(getResources().getColor(R.color.colorYellow));

        labelNameList.add("");
        labelNameList.add("");
        labelNameList.add("");
        labelNameList.add("");
        labelNameList.add("");
        labelNameList.add("");


        LACheckList = (LinearLayout) findViewById(R.id.LinearLayoutChecklist);
        rvCard = (RelativeLayout) findViewById(R.id.bg_card);
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        simpleProgressBar.setScaleY(3f);    //height of progress bar
        staticCheckList = (LinearLayout) findViewById(R.id.linearLayoutCheckboxHeading);
        cbDueDate = (CheckBox) findViewById(R.id.tvDue);
        etCheckList = (EditText) findViewById(R.id.etCheckBox);
        // checklistAddBtn = (Button)findViewById(R.id.addChecklist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back_arrow_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dataList = new ArrayList<>();


        dataList.add(new DrawerPojo("Copy"));
        dataList.add(new DrawerPojo("Move"));
        dataList.add(new DrawerPojo("Subscribe"));
        dataList.add(new DrawerPojo("Lock Card"));

        dataList.add(new DrawerPojo("Leave Card"));


        rvLabelResult = (RecyclerView) findViewById(R.id.rv_labels_card_screen_result);
//
//        setTitle();
        rvChecklist = (RecyclerView) findViewById(R.id.rv_recycler_checklist);
        rvLabel = (RecyclerView) findViewById(R.id.rv_recycler_labels);
        tvMembers = (TextView) findViewById(R.id.tvMembers);
        heading = (TextView) findViewById(R.id.heading);


        tvMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMembersDialog();
            }
        });


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        FABdueDate = (FloatingActionButton) findViewById(R.id.dueDate);
        FABmembers = (FloatingActionButton) findViewById(R.id.members);
        FABattachments = (FloatingActionButton) findViewById(R.id.attachments);
        FABchecklist = (FloatingActionButton) findViewById(R.id.CheckList);
        FABLabel = (FloatingActionButton) findViewById(R.id.labels);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


//        collapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        CardHeading = getIntent().getStringExtra("CardHeaderData");
        collapsingToolbarLayout.setTitle(CardHeading);

        etComment = (EditText) findViewById(R.id.etComment);


        FABattachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabm.close(true);
                LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
                View view = inflater.inflate(R.layout.custom_attachments_layout_dialog, null);


                final AlertDialog alertDialog = new AlertDialog.Builder(CardActivity.this).create();

                LinearLayout linearLayoutCamera = (LinearLayout) view.findViewById(R.id.linearLayoutCamera);
                LinearLayout otherFiles = (LinearLayout) view.findViewById(R.id.otherFiles);

                linearLayoutCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                        alertDialog.dismiss();


                    }
                });
                otherFiles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                        chooseFile.setType("*/*");
                        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                        startActivityForResult(chooseFile, READ_REQUEST_CODE);


                    }
                });


                alertDialog.setView(view);
                alertDialog.show();


            }
        });

        labelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();

                LabelColorFragment colorFragment = new LabelColorFragment();

                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer, colorFragment).addToBackStack("Frag1").commit();

                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                Fragment fragment = new LabelColorFragment();
            }
        });


        FABchecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onFocus = true;
                menuChanger(menu, onFocus);
                LACheckList.setVisibility(View.VISIBLE);
                staticCheckList.setVisibility(View.VISIBLE);
                fabm.close(true);


            }
        });


        FABmembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFocus = true;

                showMembersDialog();
                fabm.close(true);
            }
        });


        cbDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dueDate();
            }
        });

        FABdueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dueDate();

            }
        });

        fabm = (FloatingActionMenu) findViewById(R.id.menu);


        rv = (RecyclerView) findViewById(R.id.rv_recycler_view);

//        menuChanger(menu,false);

        openDrawer();

    }

    public void openDrawer() {
        DrawerAdapter = new CustomDrawerAdapter(this, R.layout.list_item_drawer, dataList);
        mDrawerList.setAdapter(DrawerAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            Uri selectedImage = data.getData();
            //  File imageFile = new File(selectedImage.toString());

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //  rotateImage(bitmap,selectedImage.toString());


            rvAttachmentImages.setLayoutManager(new LinearLayoutManager(Mactivity, LinearLayoutManager.HORIZONTAL, true));
            //    bitmapList.add(rotateBitmap(bitmap));
            bitmapList.add(bitmap);
            imageAdapter = new AttachmentImageAdapter(Mactivity, bitmapList, fm);
            rvAttachmentImages.setAdapter(imageAdapter);


        }
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri uri = data.getData();
            String src = uri.getPath();
            // String fileName = FilenameUtils.getName(src);


            String fileName = null;
            if (uri.getScheme().equals("file")) {
                fileName = uri.getLastPathSegment();
            } else {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(uri, new String[]{
                            MediaStore.Images.ImageColumns.DISPLAY_NAME
                    }, null, null, null);

                    if (cursor != null && cursor.moveToFirst()) {
                        fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
                        //     Log.d("Name", "name is " + fileName);
                    }
                } finally {

                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }


            Calendar c = Calendar.getInstance();




            //  Toast.makeText(getApplicationContext(),"Intent Result",Toast.LENGTH_SHORT).show();
            AttachmentsPojo attachmentsPojo = new AttachmentsPojo();
            attachmentsPojo.setNameOfFile(fileName);
            attachmentsPojo.setDateUpload(c.get(Calendar.DATE) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR));

            attachmentsList.add(attachmentsPojo);
            rvFiles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            FilesAdapter adapter = new FilesAdapter(attachmentsList, Mactivity);
            rvFiles.setAdapter(adapter);

        }

    }

    public void addDataInComments() {


        fabm.setVisibility(View.VISIBLE);

        CardCommentData cardCommentData = new CardCommentData();
        cardCommentData.setCardCommentid("Id Ov Person ");
        cardCommentData.setCardName(CardHeading);
        cardCommentData.setComment(etComment.getText().toString());
        listPojo.add(cardCommentData);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RecyclerViewAdapterComments(listPojo, getApplicationContext());
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void showMembersDialog() {
        LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
        View view = inflater.inflate(R.layout.custom_alertdialog_card_members_layout, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(CardActivity.this).create();
        lvMembers = (ListView) view.findViewById(R.id.membersListView);
        TextView tvDone = (TextView) view.findViewById(R.id.addMember);
        final AdapterMembers membersAdapter = new AdapterMembers(CardActivity.this, cardMembersPojoList1);

        lvMembers.setAdapter(membersAdapter);

       /* MembersPojo membersPojo = new MembersPojo();
        for (int i = 0; i <= 5; i++) {
            membersPojo.setName("Aqsa");
            membersPojo.setUserId("asdfgh");
            membersPojoList.add(membersPojo);
        }
        final AdapterMembers membersAdapter = new AdapterMembers(CardActivity.this, membersPojoList);

        lvMembers.setAdapter(membersAdapter);*/


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


                // MembersPojo data = (MembersPojo) membersAdapter.getItem(position);
                //  data.getName();
               /* tvMembers.setVisibility(View.VISIBLE);
                tvMembers.setText(data.getName());*/
                alertDialog.dismiss();


            }
        });

        alertDialog.setView(view);
//
        alertDialog.show();


    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.menu = menu;

        menuChanger(menu, false);
        MenuItem DrawerLabel = menu.findItem(R.id.menu);

        DrawerLabel.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                drawerLayout.openDrawer(Gravity.RIGHT);
                openDrawer();
                //   Toast.makeText(getApplicationContext(),"Nothing",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        etComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                menuChanger(menu, hasFocus);


                if (hasFocus) {
                    MenuItem menuItem = menu.findItem(R.id.tick);
                    menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            // Toast.makeText(CardActivity.this, "asdfghj", Toast.LENGTH_SHORT).show();
                            addDataInComments();

                            //   etComment.setText("");
                            //  etComment.clearComposingText();
                            // menuChanger(menu,false);
                            etComment.clearFocus();
                            return true;
                        }

                    });
                } else
                    menuChanger(menu, hasFocus);


            }
        });


        rvLabel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //  Toast.makeText(getApplicationContext(),"Menu Item Clicked",Toast.LENGTH_SHORT).show();

                menuChanger(menu, hasFocus);
                if (hasFocus) {

                    View item = findViewById(R.id.tick);


                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addLabel();
                            rvLabel.clearFocus();

                            //  return true;
                        }
                    });
//                    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                           addLabel();
//                           rvLabel.clearFocus();
//
//                            return true;
//                        }
//                    });
                } else {

                    menuChanger(menu, hasFocus);


                }
            }
        });


        etCheckList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, final boolean hasFocus) {
                menuChanger(menu, hasFocus);

                if (hasFocus == true) {


                    MenuItem tick = menu.findItem(R.id.tick);
                    tick.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            //  Toast.makeText(getApplicationContext(), "Menu Btn Pressed", Toast.LENGTH_SHORT).show();

                            addDataInChecklist();
                            menuChanger(menu, hasFocus);
                            etCheckList.clearFocus();


                            return true;

                        }
                    });


                } else {
                    // addDataInChecklist();
                    menuChanger(menu, false);
                    // etCheckList.clearFocus();
                }


            }
        });


        FABLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onFocus = true;
//                menuChanger(menu,onFocus);
                showLabelsMenu();


            }
        });

        rvLabelResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onFocus = true;
//                menuChanger(menu,onFocus);
                showLabelsMenu();
                //    doneLabelResult();

            }
        });


        return true;
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().findFragmentByTag("Frag1") != null) {
//            CardActivity.showLabelsMenu();

            getSupportFragmentManager().popBackStackImmediate("Frag1", 0);
//            showLabelsMenu();


            //   colorList.add(LabelColorFragment.getColor());
//            ColorsPojo colorsPojo = new ColorsPojo();
//            colorList.add(colorsPojo.getColor());
//            showLabelsMenu();
        }


        if (getSupportFragmentManager().findFragmentByTag("Frag2") != null) {
//            CardActivity.showLabelsMenu();

            getSupportFragmentManager().popBackStackImmediate("Frag2", 0);
            showLabelsMenu();


            //   colorList.add(LabelColorFragment.getColor());
//            ColorsPojo colorsPojo = new ColorsPojo();
//            colorList.add(colorsPojo.getColor());
//            showLabelsMenu();
        } else {
            super.onBackPressed();
        }

    }

    //    public void doneLabelResult(){}

    public void addDataInChecklist() {

        ProjectsPojo cardCommentData = new ProjectsPojo();
        //  cardCommentData.setCardCommentid("Id Ov Person");
//                cardCommentData.setCardName(CardHeading);
//                cardCommentData.setComment(etComment.getText().toString());
//               // listPojo.add(cardCommentData);
        cardCommentData.setData(etCheckList.getText().toString());
        checkListPojo.add(cardCommentData);
        // adapter = new RecyclerViewAdapterComments();
        //  adapter = new RecyclerViewAdapterComments();

        rvChecklist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rvAdapterChecklist = new RVadapterCheckList(checkListPojo, getApplicationContext());
        rvChecklist.setAdapter(rvAdapterChecklist);

        rvAdapterChecklist.notifyDataSetChanged();


    }

    public void dueDate() {

        fabm.close(true);
        // Toast.makeText(getApplicationContext(),"Due Date Button Pressed",Toast.LENGTH_LONG).show();


        LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
        View customView = inflater.inflate(R.layout.custom_date_time_spinner_cards_screen, null);
        // View nothingSelectedDate = inflater.inflate(R.layout.nothing_selected_spinnerdate,null);
        final AlertDialog alertDialog = new AlertDialog.Builder(CardActivity.this).create();


        final Calendar myCurrentDT = Calendar.getInstance();
        TextView tvCancel = (TextView) customView.findViewById(R.id.tvCancel);
        TextView tvDone = (TextView) customView.findViewById(R.id.tvDone);
        spinnerDate = (Spinner) customView.findViewById(R.id.spinnerDate);
        spinnerTime = (Spinner) customView.findViewById(R.id.spinnerTime);


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


        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(CardActivity.this, R.layout.nothing_selected_spinnerdate, spinnerDateList);
        dateAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(CardActivity.this, R.layout.nothing_selected_spinnerdate, spinnertTimeList);
        timeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerDate.setSelection(0);
        spinnerTime.setSelection(0);

        spinnerDate.setAdapter(dateAdapter);
        spinnerTime.setAdapter(timeAdapter);


        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR); // current year
        final int mMonth = c.get(Calendar.MONTH); // current month
        final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        final int mHour = c.get(Calendar.HOUR);
        final int mMin = c.get(Calendar.MINUTE);

        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                switch (selectedItem) {

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
                                        spinnerDateList.add(0, dayOfMonth + "/" + monthOfYear + "/" + year);
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
                                spinnertTimeList.add(0, selectedHour + ":" + selectedMinute);

                                //  spinnerTime.setSelection(spinnertTimeList.size() - 1);

                                spinnerTime.setSelection(0);
                            }
                        }, mHour, mMin, true);//Yes 24 hour time
                        timePickerDialog.setTitle("Select Time");
                        timePickerDialog.show();

                        break;
                }

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
//    public static void startFrag(){
//
//
//
//    }


    public void getCardList(final String lsitId) {
        final SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, GET_LABELS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if (!(response.equals("false"))) {

                            listPojo1 = new ArrayList<>();
                            labelsPojoList = new ArrayList<>();
                            cardLabelsPojoList = new ArrayList<>();
                            cardMembersPojoList = new ArrayList<>();
                            cardMembersPojoList1 = new ArrayList<>();

                            try {
                                ProjectsPojo projectsPojo = null;
                                JSONObject mainObject=new JSONObject(response);
                                //JSONArray jsonArrayCards = mainObject.getJSONArray("cards");
                                JSONArray jsonArrayLabels = mainObject.getJSONArray("labels");
                                JSONArray jsonArrayMembers = mainObject.getJSONArray("members");
                                JSONArray jsonArrayAttachments = mainObject.getJSONArray("attachments");
                                //JSONObject cardsObject = jsonArray.getJSONObject(0);

                               /* for (int i = 0; i < jsonArrayCards.length(); i++) {
                                    JSONObject jsonObject = jsonArrayCards.getJSONObject(i);
                                    JSONArray jsonArray=jsonArrayAttachments.getJSONArray(i);
                                    //JSONObject jsonObject1 = jsonArrayAttachments.getJSONObject(i);

                                    projectsPojo = new ProjectsPojo();
                                    // CardAssociatedLabelsPojo labelsPojo = new CardAssociatedLabelsPojo();

                                    projectsPojo.setId(jsonObject.getString("id"));
                                    projectsPojo.setData(jsonObject.getString("card_name"));
                                    projectsPojo.setAttachment(jsonObject.getString("file_name"));
                                    projectsPojo.setDueDate(jsonObject.getString("card_start_date"));
                                    projectsPojo.setnOfAttachments(String.valueOf(jsonArray.length()));
                                    // projectsPojo.setBoardAssociatedLabelsId(jsonObject.getString("board_assoc_label_id"));
                                    //projectsPojo.setLabels(jsonObject.getString("label_color"));

//                                    cardLabelsPojoList.add(labelsPojo);

                                    listPojo1.add(projectsPojo);
                                    // getLabelsList(jsonObject.getString("id"));

                                }*/
                                for(int j=0;j<jsonArrayLabels.length();j++){

                                    JSONArray jsonArray=jsonArrayLabels.getJSONArray(j);
                                    // String[] labels = new String[jsonArray.length()];
                                    //String[] labelText = new String[jsonArray.length()];
                                    for (int k=0;k<jsonArray.length();k++){
                                        CardAssociatedLabelsPojo labelsPojo = new CardAssociatedLabelsPojo();
                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        labelsPojo.setLabelTextCards(jsonObject.getString("label_text"));
                                        labelsPojo.setLabelColorCards(jsonObject.getString("label_color"));
                                       /* labels[k]=jsonObject.getString("label_color");

                                        if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }*/
                                        cardLabelsPojoList.add(labelsPojo);
                                    }
                                    rvLabelResult.setLayoutManager(new LinearLayoutManager(Mactivity, LinearLayoutManager.HORIZONTAL, true));
                                    RVLabelResultAdapter adapter = new RVLabelResultAdapter(Mactivity, listt, asliList,cardLabelsPojoList);
                                    rvLabelResult.setAdapter(adapter);

                                }

                                for(int j=0;j<jsonArrayMembers.length();j++){
                                    // CardAssociatedMembersPojo membersPojo = new CardAssociatedMembersPojo();
                                    JSONArray jsonArray=jsonArrayMembers.getJSONArray(j);
                                    // String[] members = new String[jsonArray.length()];
                                    //String[] labelText = new String[jsonArray.length()];
                                    for (int k=0;k<jsonArray.length();k++){
                                        MembersPojo membersPojo=new MembersPojo();
                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        membersPojo.setProfile_pic(jsonObject.getString("profile_pic"));
                                        membersPojo.setInetial(jsonObject.getString("initials"));
                                        membersPojo.setName(jsonObject.getString("first_name")+" "+jsonObject.getString("last_name"));
                                        //  members[k]=jsonObject.getString("profile_pic");
                                        // labelText[k]=jsonObject.getString("initials");
                                       /* if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }*/
                                        cardMembersPojoList1.add(membersPojo);
                                    }

                                    //  labelsPojo.setLabelText(labelText);

                                }
                                for(int j=0;j<jsonArrayAttachments.length();j++){

                                    heading.setVisibility(View.VISIBLE);
                                    AttachmentsPojo membersPojo = new AttachmentsPojo();
                                    JSONArray jsonArray=jsonArrayAttachments.getJSONArray(j);

                                    JSONObject jsonObject=jsonArray.getJSONObject(j);
                                    membersPojo.setNameOfFile(jsonObject.getString("original_name"));
                                    membersPojo.setDateUpload(jsonObject.getString("added_on"));
                                       /* if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }*/

                                    //  labelsPojo.setLabelText(labelText);
                                    attachmentsList.add(membersPojo);



                                }
                                rvFiles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                                FilesAdapter adapter = new FilesAdapter(attachmentsList, Mactivity);
                                rvFiles.setAdapter(adapter);


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

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(CardActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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


                    Toast.makeText(CardActivity.this, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("cardId",cardId);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);

    }
}
