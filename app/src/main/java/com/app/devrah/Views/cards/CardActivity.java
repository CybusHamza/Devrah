package com.app.devrah.Views.cards;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.RecoverySystem;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.app.devrah.Adapters.RVMemberResultAdapter;
import com.app.devrah.Adapters.RVadapterCheckList;
import com.app.devrah.Adapters.RecyclerViewAdapterComments;
import com.app.devrah.Adapters.team_adapter_cards;
import com.app.devrah.Adapters.team_addapter;
import com.app.devrah.Holders.callBack;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.Board.BoardsActivity;
import com.app.devrah.Views.BoardExtended.BoardExtended;
import com.app.devrah.Views.BoardExtended.BoardScreen;
import com.app.devrah.Views.ManageMembers.ManageCardMembers;
import com.app.devrah.Views.MyCards.MyCardsActivity;
import com.app.devrah.Views.Notifications.NotificationsActivity;
import com.app.devrah.pojo.AttachmentsImageFilePojo;
import com.app.devrah.pojo.AttachmentsPojo;
import com.app.devrah.pojo.CardAssociatedLabelsPojo;
import com.app.devrah.pojo.CardAssociatedMembersPojo;
import com.app.devrah.pojo.CardCommentData;
import com.app.devrah.pojo.DrawerPojo;
import com.app.devrah.pojo.MembersPojo;
import com.app.devrah.pojo.ProjectsPojo;
import com.app.devrah.pojo.check_model;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mindorks.paracamera.Camera;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.app.devrah.Holders.FilePath.getDataColumn;
import static com.app.devrah.Holders.FilePath.isDownloadsDocument;
import static com.app.devrah.Holders.FilePath.isExternalStorageDocument;
import static com.app.devrah.Holders.FilePath.isMediaDocument;
import static com.app.devrah.Network.End_Points.GET_LABELS;
import static com.app.devrah.Views.BoardExtended.BoardExtended.boardId;
import static com.app.devrah.Views.BoardExtended.BoardExtended.projectId;

public class CardActivity extends AppCompatActivity  implements callBack {
    private static final int REQUEST_PERMISSIONS=0;
    private static final int READ_REQUEST_CODE = 42;
    public static View view;
    public static boolean onFocus = false;
    public static Context mcontext;
    public static CardActivity Mactivity;
    public static String labelName;
    public static RelativeLayout container;
    public static RelativeLayout container1;
    public static ProgressBar simpleProgressBar;
    public static Menu menu;
    public static RecyclerView rv, rvLabel, rvLabelResult, rvAttachmentImages,rvMembersResult;
    public static TextView labelAdd;
    public static String textLabelName;
    static List<String> asliList;
    static List<String> labelNameList;
    static String dueDate,dueTime,startDate,startTime,cardDescription,cardIsComplete;
    static RVLabelAdapter rvAdapterLabel;
    RVMemberResultAdapter memberAdapter;
    static List<Integer> colorList;
    static List<String> colorList1;
    static List<Integer> listt;
    static List<Integer> resultColorList;
    String b64,formattedDate;
    private static FloatingActionMenu fabm;
    public List<Bitmap> bitmapList;
    CollapsingToolbarLayout collapsingToolbarLayout;
    List<File> fileList;
    List<DrawerPojo> dataList;
    List<MembersPojo> cardMembersPojoList1;
    ListView lvMembers;
    CustomDrawerAdapter DrawerAdapter;
    DrawerLayout drawerLayout;
    String[] fileName;
    String file;
    private String upLoadServerUri = null;
    private String imagepath=null;
    private int serverResponseCode = 0;
    String filepath;
    AttachmentImageAdapter imageAdapter;
    LinearLayout LACheckList, staticCheckList;
    List<MembersPojo> membersPojoList;
    RelativeLayout rvCard;
    FloatingActionButton FABdueDate, FABmembers, FABattachments, FABchecklist, FABLabel;
    Toolbar toolbar;
    RVadapterCheckList rVadapterCheckList;
    Spinner spinnerstartDate,spinnerstartTime,spinnerDate, spinnerTime;
    List<String> spinnerDateList, spinnertTimeList,spinnerStartDateList,spinnerStartTimeList;
    static Activity activity;
    EditText etComment, etCheckList;
    static String CardHeading,list_id;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    TextView tvMembers;
    ImageView heading;
    RecyclerViewAdapterComments adapter;
    RVadapterCheckList rvAdapterChecklist;
    List<CardCommentData> listPojo;
    RecyclerView rvFiles;
    FragmentManager fm;
    ArrayList<String> checklistID;
    ArrayList <check_model> CheckListname;
//    List<String> checklistID;
//    List<String> CheckListname;
    List<String> checkid;
    List<String> checklistid;
    List<String> manhusLabelList;
    List<ProjectsPojo> checkListPojo;
    static List<String> lableid;
    TextView cbDueDate,cbDueTime,cbStartDate,cbStartTime;
    Button isCompletedBtn;
    List<AttachmentsPojo> attachmentsList;
    List<AttachmentsImageFilePojo> attachmentsList1;
    private ListView mDrawerList;
    public static String cardId;
    public static String projectStatus;

    List<ProjectsPojo> listPojo1,labelsPojoList;
    List<CardAssociatedLabelsPojo> cardLabelsPojoList;
    static  List<CardAssociatedLabelsPojo> cardLabelsPojoListstat;
    List<CardAssociatedMembersPojo> cardMembersPojoList;
    static  String name,strColor;
    static  int color;
    ProgressDialog ringProgressDialog;
    team_addapter addapter;
    AdapterMembers membersAdapter;
    boolean isUpdate =  false;
    AlertDialog alertDialog;
    HashMap<String, ArrayList<check_model>> listDataChild;
    ArrayList<check_model> childDAta;
    RecyclerView expandableLv;
    ArrayList<String> check_name;
    ArrayList<String> checkstatus;
    ArrayList<String> check_id ;
    RVadapterCheckList customAdapter;
    EditText etDescription;

    Spinner boardSpinner,listSpinner,positionSpinner;
    CheckBox checklistcb,labelcb,attachmentcb,membercb;
    EditText etTitleCopyCard;
    ArrayList<String> boards_name ;
    ArrayList<String> boards_ids ;
    ArrayList<String> lists_name ;
    ArrayList<String> list_ids ;
    List<String> postions_list;
    static String isFromMyCardsScreen;
    List<String> spinnerValues;
    List<String> spinnerGroupIds;
    List<String> postions_listProjects;
    Spinner Projects;
   // public static String textLabelName;
    Camera camera;
    int flag;
    static String isCardLocked,isCardSubscribed;
    static ImageView labelIcon;
    //public static String labelid;
   static ProgressDialog progressBar;
    ListView currentMember;
    ArrayList<String> cardMembers;
    ArrayList<MembersPojo> membersPojos;
    team_adapter_cards addapterCards;
    public static void showLabelsMenu() {


        menuChanger(menu, true);

//            labelDone.setVisibility(View.VISIBLE);
        if (rvLabel.getVisibility() == View.GONE) {
            rvLabel.setVisibility(View.VISIBLE);
            labelAdd.setVisibility(View.VISIBLE);
            labelIcon.setVisibility(View.INVISIBLE);
        }
        if (labelAdd.getVisibility() == View.GONE) {
            labelAdd.setVisibility(View.VISIBLE);
            labelIcon.setVisibility(View.INVISIBLE);


        }

        MenuItem menuItem = menu.findItem(R.id.tick);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Toast.makeText(Mactivity,"Show wala menu",Toast.LENGTH_SHORT).show();
/*
               int pos = RVLabelAdapter.pos;



                addlabel( RVLabelAdapter.colornames.get(pos),name);*/

                labelIcon.setVisibility(View.VISIBLE);
                rvLabelResult.setVisibility(View.VISIBLE);
                labelAdd.setVisibility(View.GONE);
                rvLabel.setVisibility(View.GONE);

                menuChanger(menu, false);
                ((CardActivity)activity).updateUI();

                return true;
            }
        });



    }

    public  static void showDatColors(List<String> data,List<String> labelNameList,List<String> lablid,List<String> isCardAssigned)
    {

        showLabelsMenu();

        if (rvLabel.getVisibility() == View.GONE) {
            rvLabel.setVisibility(View.VISIBLE);
            labelAdd.setVisibility(View.VISIBLE);
        }
        if (labelAdd.getVisibility() == View.GONE) {
            labelAdd.setVisibility(View.VISIBLE);


        }

        rvLabel.setLayoutManager(new LinearLayoutManager(mcontext));
        rvAdapterLabel = new RVLabelAdapter(Mactivity, colorList, listt, labelNameList, asliList,data,lablid,"continue",isCardAssigned);
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

        activity = CardActivity.this;
        Intent intent=  getIntent();

        cardId = intent.getStringExtra("card_id");
        list_id = intent.getStringExtra("list_id");
        dueDate = intent.getStringExtra("cardduedate");
        dueTime = intent.getStringExtra("cardduetime");
        startDate = intent.getStringExtra("cardstartdate");
        startTime = intent.getStringExtra("cardstarttime");
        cardDescription = intent.getStringExtra("cardDescription");
        cardIsComplete = intent.getStringExtra("isComplete");
        isCardLocked = intent.getStringExtra("isLocked");
        isCardSubscribed = intent.getStringExtra("isSubscribed");
        isFromMyCardsScreen = intent.getStringExtra("fromMyCards");
        if(isFromMyCardsScreen.equals("board"))
            projectStatus=intent.getStringExtra("projectStatus");
        //if(isFromMyCardsScreen.equals("true")){
            BoardExtended.bTitle=intent.getStringExtra("board_name");
            projectId=intent.getStringExtra("project_id");
            BoardExtended.boardId=intent.getStringExtra("board_id");
            BoardExtended.pTitle=intent.getStringExtra("project_title");
            BoardExtended.isWorkBoard=intent.getStringExtra("work_board");
        //}

        try{
            getCardList();
        }catch (OutOfMemoryError error){
            error.printStackTrace();
        }
        View header = getLayoutInflater().inflate(R.layout.header_for_drawer, null);


        labelNameList = new ArrayList<>();
        asliList = new ArrayList<>();
        container = findViewById(R.id.fragmentContainer);
        container1 = findViewById(R.id.fragmentContainer1);
        rvFiles = findViewById(R.id.rv_files_cardscreen);
        labelIcon= findViewById(R.id.labelIcon);
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
        rvAttachmentImages = findViewById(R.id.rvImagesAttachment);
        resultColorList = new ArrayList<>();
        listt = new ArrayList<>();


        fm = getSupportFragmentManager();
        labelAdd = findViewById(R.id.tvAddLabel);
        Mactivity = CardActivity.this;
/*        colorList.add(getResources().getColor(R.color.colorAccent));
        colorList.add(getResources().getColor(R.color.colorPrimaryDark));
        colorList.add(getResources().getColor(R.color.lightGreen));

        colorList.add(getResources().getColor(R.color.colorOrangeRed));
        colorList.add(getResources().getColor(R.color.colorOrange));
        colorList.add(getResources().getColor(R.color.colorYellow));
        colorList.add(getResources().getColor(R.color.colorPurple));
        colorList.add(getResources().getColor(R.color.wierdBlue));
        colorList.add(getResources().getColor(R.color.colorYellow));

        labelNameList.add("");
        labelNameList.add("");
        labelNameList.add("");
        labelNameList.add("");
        labelNameList.add("");
        labelNameList.add("");
        labelNameList.add("");
        labelNameList.add("");
        labelNameList.add("");*/
        camera = new Camera.Builder()
                .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
                .setTakePhotoRequestCode(1)
                .setDirectory("pics")
                .setName("ali_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                .build(CardActivity.this);
        LACheckList = findViewById(R.id.LinearLayoutChecklist);
        rvCard = findViewById(R.id.bg_card);
        simpleProgressBar = findViewById(R.id.simpleProgressBar);
        simpleProgressBar.setScaleY(3f);    //height of progress bar
        staticCheckList = findViewById(R.id.linearLayoutCheckboxHeading);
        cbDueDate = findViewById(R.id.tvDue);
        cbDueTime = findViewById(R.id.tvDueTime);
        cbStartDate = findViewById(R.id.tvStartDate);
        cbStartTime = findViewById(R.id.tvStartTime);
        isCompletedBtn= findViewById(R.id.btnComplete);
        if(cardIsComplete.equals("1")){
            isCompletedBtn.setText("Unmark Completed");
            isCompletedBtn.setBackgroundColor(getResources().getColor(R.color.orange));
        }else if(cardIsComplete.equals("0")){
            isCompletedBtn.setText("Mark Completed");
            isCompletedBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        etCheckList = findViewById(R.id.etCheckBox);
        // checklistAddBtn = (Button)findViewById(R.id.addChecklist);
        toolbar = findViewById(R.id.toolbar);


        dataList = new ArrayList<>();

        dataList.add(new DrawerPojo("Update Card Name"));
        dataList.add(new DrawerPojo("Copy"));
        dataList.add(new DrawerPojo("Move"));
        if(isCardSubscribed.equals("0") || isCardSubscribed.equals("null") || isCardSubscribed.equals("")) {
            dataList.add(new DrawerPojo("Subscribe"));
        }else {
            dataList.add(new DrawerPojo("Un-subscribe"));
        }
        if(isCardLocked.equals("0")) {
            dataList.add(new DrawerPojo("Lock Card"));
        }else {
            dataList.add(new DrawerPojo("Unlock Card"));
        }
        dataList.add(new DrawerPojo("Delete Card"));
     //   dataList.add(new DrawerPojo("Leave Card"));


        rvLabelResult = findViewById(R.id.rv_labels_card_screen_result);
        rvMembersResult = findViewById(R.id.rv_members_list);
//
//        setTitle();rvChecklist
        expandableLv = findViewById(R.id.rv_recycler_checklist);

        rvLabel = findViewById(R.id.rv_recycler_labels);
        tvMembers = findViewById(R.id.tvMembers);
        heading = findViewById(R.id.heading);

        etDescription = findViewById(R.id.description);
        if(!cardDescription.equals("null"))
            etDescription.setText(cardDescription);
        etDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDescription.setCursorVisible(true);
//                etDescription.setBackgroundResource(android.R.drawable.edit_text);
                menuChanger(menu, true);
                final MenuItem tick = menu.findItem(R.id.tick);
                tick.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //  Toast.makeText(getApplicationContext(), "Menu Btn Pressed", Toast.LENGTH_SHORT).show();
                        menuChanger(menu, false);
                        etDescription.clearFocus();
                        tick.setVisible(false);
                        addDescription(etDescription.getText().toString());
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etDescription.getWindowToken(), 0);
                        etDescription.setCursorVisible(false);
//                        etDescription.setBackgroundResource(android.R.color.transparent);
                        return true;
                    }
                });
            }
        });

        if(!startDate.equals("") && !startDate.equals("null") && !startDate.equals("0000-00-00")) {
           SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date myDate = null;
            try {
                String myTime=startDate;
                myDate = dateFormat.parse(myTime);
                SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MMM-yy");
                String finalDate = timeFormat.format(myDate);
                cbStartDate.setText(finalDate);
                cbStartDate.setVisibility(View.VISIBLE);
                 } catch (ParseException e) {
                e.printStackTrace();
            }

        }else if(startDate.equals("null") && startDate.equals("0000-00-00")){
            cbStartDate.setText("Start Date");
            cbStartDate.setVisibility(View.VISIBLE);
        }
        if(!startTime.equals("") && !startTime.equals("null")) {
            cbStartTime.setText(startTime);
            cbStartTime.setVisibility(View.VISIBLE);
        }else {
            cbStartTime.setText("Start Time");
            cbStartTime.setVisibility(View.VISIBLE);
        }

        if(!dueDate.equals("") && !dueDate.equals("null") && !dueDate.equals("0000-00-00")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date myDate = null;
            try {
                String myTime=dueDate;
                myDate = dateFormat.parse(myTime);
                SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MMM-yy");
                String finalDate = timeFormat.format(myDate);
                cbDueDate.setText(finalDate);
                cbDueDate.setVisibility(View.VISIBLE);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else if(dueDate.equals("null") && dueDate.equals("0000-00-00")){
            cbDueDate.setText("Due Date");
            cbDueDate.setVisibility(View.VISIBLE);
        }
        if(!dueTime.equals("") && !dueTime.equals("null")) {
            cbDueTime.setText(dueTime);
            cbDueTime.setVisibility(View.VISIBLE);
        }else{
            cbDueTime.setText("Due Time");
            cbDueTime.setVisibility(View.VISIBLE);
        }
        isCompletedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCompletedBtn.getText().equals("Mark Completed")){
                    if(!cbDueDate.getText().toString().equals("Due Date")) {
                        updateCardCompleted("1");
                    }else {
                        Toast.makeText(CardActivity.this,"Select due date to complete card",Toast.LENGTH_LONG).show();
                    }
                }else if(isCompletedBtn.getText().equals("Unmark Completed")){
                    updateCardCompleted("0");
                }
            }
        });

        tvMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showMembersDialog();
            }
        });


       // collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
       // FABdueDate = (FloatingActionButton) findViewById(R.id.dueDate);
        FABmembers = findViewById(R.id.members);
        FABattachments = findViewById(R.id.attachments);
        FABchecklist = findViewById(R.id.CheckList);
        FABLabel = findViewById(R.id.labels);



        drawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);


//        collapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        CardHeading = getIntent().getStringExtra("CardHeaderData");
        //collapsingToolbarLayout.setTitle(CardHeading);
       // collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.bg_projects));
        etComment = findViewById(R.id.etComment);

        toolbar.setTitle(CardHeading);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            getChecklistData();
        }catch (OutOfMemoryError error){
            error.printStackTrace();
        }
        toolbar.setNavigationIcon(R.drawable.back_arrow_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        FABattachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fabm.close(true);
                final LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
                View view = inflater.inflate(R.layout.custom_attachments_layout_dialog, null);


                final AlertDialog alertDialog = new AlertDialog.Builder(CardActivity.this).create();

                LinearLayout linearLayoutCamera = view.findViewById(R.id.linearLayoutCamera);
                LinearLayout otherFiles = view.findViewById(R.id.otherFiles);

                linearLayoutCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            if(ActivityCompat.checkSelfPermission(CardActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                                if (Build.VERSION.SDK_INT > 22) {

                                    requestPermissions(new String[]{Manifest.permission
                                                    .WRITE_EXTERNAL_STORAGE},
                                            4);

                                    alertDialog.dismiss();
                                }

                            }else if(ActivityCompat.checkSelfPermission(CardActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                                if (Build.VERSION.SDK_INT > 22) {

                                    requestPermissions(new String[]{Manifest.permission
                                                    .CAMERA},
                                            REQUEST_PERMISSIONS);

                                    alertDialog.dismiss();
                                }

                            }

                         else {
                            /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);*/


// Build the camera

                                try {
                                    camera.takePicture();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            alertDialog.dismiss();

                        }


                    }
                });
                otherFiles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ActivityCompat.checkSelfPermission(CardActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                            if (Build.VERSION.SDK_INT > 22) {

                                requestPermissions(new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE},
                                        10);

                                alertDialog.dismiss();
                            }

                        }else {
                            alertDialog.dismiss();

                            Intent intent = new Intent();
                            intent.setType("*/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                           // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            //   intent.setSelector(Intent.getIntent().removeCategory(););

                            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 2);
                        }


                    }
                });


                alertDialog.setView(view);
                alertDialog.show();


            }
        });

        labelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                colorList = new ArrayList<Integer>();
                labelNameList = new ArrayList<String>();

                colorList.add(getResources().getColor(R.color.colorAccent));
                colorList.add(getResources().getColor(R.color.colorPrimaryDark));
                colorList.add(getResources().getColor(R.color.lightGreen));

                colorList.add(getResources().getColor(R.color.colorOrangeRed));
                colorList.add(getResources().getColor(R.color.colorOrange));
                colorList.add(getResources().getColor(R.color.colorYellow));
                colorList.add(getResources().getColor(R.color.colorPurple));
                colorList.add(getResources().getColor(R.color.wierdBlue));
                colorList.add(getResources().getColor(R.color.colorYellow));
                //colorList.add(getResources().getColor(R.color.pink));

                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
               // labelNameList.add("");

                RVLabelAdapter.index = -1;
                addNewLabelDialogue();
               /* FragmentManager fm = getSupportFragmentManager();

                LabelColorFragment colorFragment = new LabelColorFragment();
                    LabelColorFragment.textLabelName="";
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, colorFragment).addToBackStack("Frag1").commit();

                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);*/
//                Fragment fragment = new LabelColorFragment();
            }
        });

        rvLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(CardActivity.this, "Hello Lable Click", Toast.LENGTH_SHORT).show();



            }
        });

        FABchecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabm.close(true);
                addNewCheckListDialogue("Checklist");

                /*onFocus = true;
                menuChanger(menu, onFocus);
                LACheckList.setVisibility(View.VISIBLE);
                staticCheckList.setVisibility(View.VISIBLE);
                fabm.close(true);*/


            }
        });


        FABmembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFocus = true;

               // showMembersDialog();
               /* Intent intent = new Intent(CardActivity.this, Manage_Card_Members.class);
                intent.putExtra("P_id",projectId);
                intent.putExtra("b_id",boardId);
                intent.putExtra("c_id",cardId);
                intent.putExtra("l_id",list_id);
                startActivity(intent);*/

               // FragmentManager fm = getSupportFragmentManager();
                fabm.close(true);
               /* ManageCardMembers manageCardMembers = new ManageCardMembers();
               // LabelColorFragment.textLabelName="";
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer1, manageCardMembers).commit();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);*/
               addNewMemberDialogue();

            }
        });


        cbDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dueDate();
                if(isCompletedBtn.getText().equals("Mark Completed"))
                pickDueDate("dueDate");

            }
        });
        cbDueTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCompletedBtn.getText().equals("Mark Completed"))
                pickATime("dueTime");

            }
        });

        cbStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCompletedBtn.getText().equals("Mark Completed"))
                pickDueDate("startDate");
                //  dueDate();
            }
        });
        cbStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCompletedBtn.getText().equals("Mark Completed"))
                pickATime("startTime");
            }
        });

        /*FABdueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dueDate();

            }
        });*/

        fabm = findViewById(R.id.menu);
        fabm.setClosedOnTouchOutside(true);
        fabm.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabm.toggle(true);
            }
        });
        fabm.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {

            @Override
            public void onMenuToggle(boolean opened) {
                if(opened) {
                    cbDueDate.setEnabled(false);
                    cbDueTime.setEnabled(false);
                    cbStartDate.setEnabled(false);
                    cbStartTime.setEnabled(false);
                    isCompletedBtn.setEnabled(false);
                    etDescription.setEnabled(false);
                    drawerLayout.setEnabled(false);
                    etCheckList.setEnabled(false);



                }else {
                    cbDueDate.setEnabled(true);
                    cbDueTime.setEnabled(true);
                    cbStartDate.setEnabled(true);
                    cbStartTime.setEnabled(true);
                    isCompletedBtn.setEnabled(true);
                    etDescription.setEnabled(true);
                    drawerLayout.setEnabled(true);
                    etCheckList.setEnabled(true);
                    expandableLv.setEnabled(true);

                }
            }

        });


        rv = findViewById(R.id.rv_recycler_view);

//        menuChanger(menu,false);
        mDrawerList.addHeaderView(header);
        openDrawer();

      //  getChecklistData();

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
        myColorList.add(getResources().getColor(R.color.black));

        myColorList.add(getResources().getColor(R.color.colorPrimaryDark));
        myColorList.add(getResources().getColor(R.color.colorGreen));

        myColorList.add(getResources().getColor(R.color.colorOrangeRed));
        myColorList.add(getResources().getColor(R.color.colorOrange));
        myColorList.add(getResources().getColor(R.color.colorYellow));
        myColorList.add(getResources().getColor(R.color.wierdBlue));
        myColorList.add(getResources().getColor(R.color.lightGreen));
        myColorList.add(getResources().getColor(R.color.purple));
        myColorList.add(getResources().getColor(R.color.green));
        myColorList.add(getResources().getColor(R.color.pink));
        // myColorList.add(getResources().getColor(R.color.pink));

        images = new ArrayList<>();
        layouts = new ArrayList<>();
        visibleImages = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
        View view = inflater.inflate(R.layout.fragment_color_fragment,null);
        final AlertDialog alertDialog = new  AlertDialog.Builder(CardActivity.this).create();
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

                        new SweetAlertDialog(CardActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Confirmation!")
                                .setCancelText("Cancel")
                                .showCancelButton(true)
                                .setConfirmText("OK").setContentText("Are you sure you want to delete this label")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {

                                        sDialog.dismiss();
                                        //deleteLable();

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
                    Toast.makeText(CardActivity.this,"Please Select label!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(RVLabelAdapter.index == -1)
                {
                    addLable(colorselected[0],s,alertDialog);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etLabelName.getWindowToken(), 0);
                    tvDone.setClickable(false);
                   // alertDialog.dismiss();
                }
               /* else
                {
                    updateLAble(finalColorselected,s);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etLabelName.getWindowToken(), 0);
                    tvDone.setClickable(false);

                }*/

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



    public  void addLable(final String lablecolor, final String lableText, final AlertDialog alertDialog) {
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SAVE_NEW_LABELS_CARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                        updateUI();

                        labelAdd.setVisibility(View.GONE);
                        rvLabel.setVisibility(View.GONE);

                        rvLabelResult.setVisibility(View.VISIBLE);
                        alertDialog.dismiss();
                       // fm.popBackStack();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(CardActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("board_id",BoardExtended.boardId);
                params.put("projectId",BoardExtended.projectId);
                params.put("card_id",cardId);
                params.put("label_text",lableText);
                params.put("label_color",lablecolor);
                final SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
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

    private void addNewCheckListDialogue(final String Checklist) {
        LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
        View customView = inflater.inflate(R.layout.custom_alert_dialogbox,null);
        final AlertDialog alertDialog = new  AlertDialog.Builder(CardActivity.this).create();

        alertDialog.setCancelable(false);
        final EditText edt = customView.findViewById(R.id.input_watever);
        final TextView addCard = customView.findViewById(R.id.btn_add_board);
        final TextView addCardAndMore= customView.findViewById(R.id.btn_add_board1);
        final TextView headingtv = customView.findViewById(R.id.headingTitle);
        headingtv.setText("Add CheckList");
        addCard.setText("Save and Close");
        edt.setText(Checklist);
        edt.setSelection(edt.getText().length());
        showKeyBoard(edt);
        final TextView cancel = customView.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(edt.getWindowToken(), 0);
                alertDialog.dismiss();

            }
        });
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String checkListName = edt.getText().toString();
                if (!(checkListName.isEmpty())  && checkListName.trim().length()>0) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
                    addnewChecklist(checkListName);
                    alertDialog.dismiss();

                }
                else {
                    Toast.makeText(CardActivity.this,"Name is must",Toast.LENGTH_SHORT).show();
                }
            }
        });
        addCardAndMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkListName = edt.getText().toString();
                if (!(checkListName.isEmpty())  && checkListName.trim().length()>0) {
                    addnewChecklist(checkListName);

                }
                else {
                    Toast.makeText(CardActivity.this,"Name is must",Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setView(customView);
        alertDialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                drawerLayout.openDrawer(Gravity.RIGHT);
                openDrawer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void openDrawer() {
        DrawerAdapter = new CustomDrawerAdapter(this, R.layout.list_item_drawer, dataList);
        mDrawerList.setAdapter(DrawerAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        updateCardNameDialog();
                        break;
                    case 2:
                        showDialogCopy();
                        break;
                    case 3:
                        showDialog("move");
                        break;
                    case 4:
                        if(isCardSubscribed.equals("0") || isCardSubscribed.equals("null") || isCardSubscribed.equals("")){
                            subscribe();
                        }else {
                            unSubscribe();
                        }
                        break;
                    case 5:
                        String label;
                        if(isCardLocked.equals("1")){
                            new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Alert!")
                                    .setCancelText("Cancel")
                                    .setConfirmText("OK").setContentText("Do You really Want to Unlock the Card ?")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            unlockCard();
                                            //  alertRemove(membersList.get(position).getId(),position);
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
                        }else {
                            new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Alert!")
                                    .setCancelText("Cancel")
                                    .setConfirmText("OK").setContentText("Do You really Want to lock the Card ?")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            lockCard();
                                            //  alertRemove(membersList.get(position).getId(),position);
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
                        }

                        break;
                    case 6:
                        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Alert!")
                                .setCancelText("Cancel")
                                .setConfirmText("OK").setContentText("Are you sure you want to delete this card")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        deleteCard();
                                        //  alertRemove(membersList.get(position).getId(),position);
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
                    case 7:
                        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Alert!")
                                .setCancelText("Cancel")
                                .setConfirmText("OK").setContentText("Do you really want to leave card "+CardHeading)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        leaveCard();
                                        //  alertRemove(membersList.get(position).getId(),position);
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

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {

         //   Uri selectedImage = data.getData();
            //  File imageFile = new File(selectedImage.toString());

            //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Bitmap bitmap = camera.getCameraBitmap();
            Uri tempUri = getImageUri(getApplicationContext(), bitmap);

            String picturePath=getPathFromURI(tempUri);
            Bitmap bm = BitmapFactory.decodeFile(picturePath);
            if (!picturePath.equals("")) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formattedDate = df.format(c.getTime());

                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] ba = bao.toByteArray();
                b64 = Base64.encodeToString(ba,Base64.DEFAULT);
                uploadImage(formattedDate,b64,picturePath);
                //   LoadImage();

            }

            //  rotateImage(bitmap,selectedImage.toString());


          /*  rvAttachmentImages.setLayoutManager(new LinearLayoutManager(Mactivity, LinearLayoutManager.HORIZONTAL, true));
            //    bitmapList.add(rotateBitmap(bitmap));
            bitmapList.add(bitmap);
            imageAdapter = new AttachmentImageAdapter(Mactivity, bitmapList, fm,attachmentsList1);
            rvAttachmentImages.setAdapter(imageAdapter);*/


        }
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
/*
            Uri uri = data.getData();
            String src = uri.getPath();
        */    Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            // String fileName = FilenameUtils.getName(src);


            String fileName = null;
            if (selectedImage.getScheme().equals("file")) {
                fileName = selectedImage.getLastPathSegment();
            } else {
                Cursor cursor = null;
                try {
                     cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                       //ileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
                        //     Log.d("Name", "name is " + fileName);

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                         filepath = cursor.getString(columnIndex);


                    }
                } finally {

                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }

            if(filepath.contains(".mp4")){
                Toast.makeText(activity,"File format is not supported",Toast.LENGTH_LONG).show();
            }
            else {
                Calendar c = Calendar.getInstance();
                //  Toast.makeText(getApplicationContext(),"Intent Result",Toast.LENGTH_SHORT).show();
                AttachmentsPojo attachmentsPojo = new AttachmentsPojo();
                attachmentsPojo.setNameOfFile(fileName);
                attachmentsPojo.setDateUpload(c.get(Calendar.DATE) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR));

                new UploadFile().execute(filepath);
            }


          /*  attachmentsList.add(attachmentsPojo);
            rvFiles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            FilesAdapter adapter = new FilesAdapter(attachmentsList, Mactivity);
            rvFiles.setAdapter(adapter);
            Uri outputFileUri = null;
            File path = getFilesDir();
            File itemFile = new File(path,fileName);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "folder_name" + File.separator);
// have the object build the directory structure, if needed.
            wallpaperDirectory.mkdirs();
// create a File object for the output file
            File outputFile = new File(wallpaperDirectory, fileName);
            File sourceFile = new File(fileName);

            if(isExternalStorageReadable()){
                if(sourceFile.isFile())
                    Toast.makeText(CardActivity.this,"readable",Toast.LENGTH_LONG).show();
            }
//now attach OutputStream to the file object, instead of a String representation

            try {
                FileOutputStream fos = new FileOutputStream(outputFile);
                outputFileUri=Uri.fromFile(outputFile);;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
            // String path1 = FilePath.getPath(this,uri );
           /* File source = new File(src);
            File destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/folder_name/");
            try {
                copy(source,destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!destination.exists()) {

                destination.mkdirs();
            }*/
            //String  selectedPath = getRealPathFromURI(this,uri);
            //File selectedFile = new File(uri.getPath());





        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getData().getPath();

            Uri selectedImageUri = data.getData();
            Bitmap bitmap=BitmapFactory.decodeFile(imagepath);

            String path = "";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                path  = getPath(CardActivity.this, selectedImageUri);
            }

            if(path!=null && !path.equals("")) {
                if(path.contains(".mp4")){
                    Toast.makeText(activity,"File format is not supported",Toast.LENGTH_LONG).show();
                }else {
                    fileName = path.split("/");
                    file = fileName[fileName.length - 1];

                    UploadFile uploadFile = new UploadFile();
                    uploadFile.delegate = CardActivity.this;
                    uploadFile.execute(path);

                    /*ringProgressDialog = ProgressDialog.show(activity, "Please wait ...", "Uploading File ...", true);
                    ringProgressDialog.setCancelable(false);
                    ringProgressDialog.show();*/
                }
            }else {
                Toast.makeText(this,"file not found",Toast.LENGTH_LONG).show();
            }


        }


    }

    public void getChecklistData() {
        ringProgressDialog = ProgressDialog.show(activity, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_CHECKLIST_DATA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ringProgressDialog.dismiss();
                LinearLayout linearLayout= findViewById(R.id.checkListLayout);
                if(!response.equals("false")) {

                    try {
                        //   projectNames = new ArrayList<>();

                        String firstname, email, lastName, profilePic;

                        //  JSONArray array = new JSONArray(response);

                        checklistID = new ArrayList<>();
                        CheckListname = new ArrayList<>();
                        checkid = new ArrayList<>();
                        checklistid = new ArrayList<>();

                        listDataChild = new HashMap<>();
                        if (!response.equals("false")) {
                            // LinearLayout linearLayout = (LinearLayout) findViewById(R.id.checkListLayout);
                            linearLayout.setVisibility(View.VISIBLE);
                            JSONObject object = new JSONObject(response);

                            String data = object.getString("checklist_heading");
                            if (!data.equals("false")) {
                                linearLayout.setVisibility(View.VISIBLE);
                                JSONArray jsonArray = new JSONArray(data);


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject obj = new JSONObject(jsonArray.getString(i));

                                    check_model model = new check_model();
                                    model.setId(obj.getString("id"));
                                    model.setName(obj.getString("checklist_name"));
                                    //checkid.add(obj.getString("card_id"));


                                    CheckListname.add(model);

                                    listDataChild.put(CheckListname.get(i).getId() + "", null);

                                    // String id  = obj.getString("id");
                                    //  listDataChild.put(id,null);
                                }


                                String data2 = object.getString("checklist_data");

                                JSONArray jsonArray1 = new JSONArray(data2);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    check_name = new ArrayList<>();
                                    childDAta = new ArrayList<>();
                                    check_id = new ArrayList<>();
                                    for (int j = 0; j < jsonArray1.length(); j++) {

                                        JSONObject jsonObject = jsonArray1.getJSONObject(j);
                                        String id = jsonObject.getString("checklist_id");
                                        String projectid = CheckListname.get(i).getId();
                                        if (projectid.equals(id)) {
                                            check_model model = new check_model();
                                            model.setName(jsonObject.getString("checkbox_name"));
                                            model.setChecked(jsonObject.getString("is_checked"));


                                            model.setId(jsonObject.getString("check_id"));

                                            childDAta.add(model);

                                        }

                                        listDataChild.put(CheckListname.get(i).getId() + "", childDAta);

//
//                            JSONObject jsonObject = jsonArray1.getJSONObject(j);
//
//                            check_model model = new check_model()
//;                           model.setName( jsonObject.getString("checkbox_name"));
//                            model.setIdchecked(jsonObject.getString("is_checked"));
//                            model.setId(jsonObject.getString("check_id"));
//
//                            childDAta.add(model);


                                    }

                                    //    listDataChild.put(checklistID.get(i)+"", check_name);
                                }
                            } else {

                                linearLayout.setVisibility(View.GONE);
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    linearLayout.setVisibility(View.GONE);
                }


                expandableLv.setLayoutManager(new LinearLayoutManager(CardActivity.this));
                customAdapter = new RVadapterCheckList(CardActivity.this,CheckListname,listDataChild);
                expandableLv.setAdapter(customAdapter);

            }

        }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();
                String message = null;
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                    Toast.makeText(activity,"Connection TimeOut! Please check your internet connection.",Toast.LENGTH_LONG).show();

                    /*new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("card_id", cardId);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);

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
        alertDialog = new AlertDialog.Builder(CardActivity.this).create();
        lvMembers = view.findViewById(R.id.membersListView);
        TextView tvDone1 = view.findViewById(R.id.addMember);
        TextView addNewMember = view.findViewById(R.id.assignNewMemberBtn);
          membersAdapter = new AdapterMembers(CardActivity.this, cardMembersPojoList1);

        lvMembers.setAdapter(membersAdapter);

       /* MembersPojo membersPojo = new MembersPojo();
        for (int i = 0; i <= 5; i++) {
            membersPojo.setName("Aqsa");
            membersPojo.setUserId("asdfgh");
            membersPojoList.add(membersPojo);
        }
        final AdapterMembers membersAdapter = new AdapterMembers(CardActivity.this, membersPojoList);

        lvMembers.setAdapter(membersAdapter);*/


        tvDone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        addNewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //addNewMemberDialogue();

            }
        });

        lvMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {




                new SweetAlertDialog(CardActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Error!")
                        .setCancelText("Cancle")
                        .setConfirmText("OK").setContentText("Are You sure you want to remove  member from this card")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                alertDialog.dismiss();
                                deleteMember(cardMembersPojoList1.get(position).getUserId());
                                isUpdate = true;
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


            }
        });

        alertDialog.setView(view);
//
        alertDialog.show();


    }

    private void addNewMemberDialogue() {

        LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
        View view = inflater.inflate(R.layout.magnage_members_popup1,null);
        final AlertDialog alertDialog = new  AlertDialog.Builder(CardActivity.this).create();
        currentMember = view.findViewById(R.id.grid_view);
        //TeamMember = view.findViewById(R.id.grid_view_team);
       TextView heading = view.findViewById(R.id.heading);
        heading.setText("Manage Card Member");
        //Team_list = view.findViewById(R.id.search_team);
        Button  btnClose= view.findViewById(R.id.close);
        Button  btnSave= view.findViewById(R.id.save);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI();
                alertDialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              updateUI();
                alertDialog.dismiss();
                Toast.makeText(CardActivity.this,"Members managed Successfully !",Toast.LENGTH_LONG).show();

            }
        });
        currentMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(membersPojos.get(i).getIsCardMember().equals("1")) {
                  String  usertoadd = membersPojos.get(i).getUserId();

                    deletemember(usertoadd);
                }else {
                   String usertoadd = membersPojos.get(i).getUserId();
                    addmember(usertoadd);
                }

            }
        });
        getCardmembers();
        alertDialog.setView(view);
//
        alertDialog.show();
    }
    public void getCardmembers() {

        final ProgressDialog ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_CARD_MEMBERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        membersPojos = new ArrayList<>();
                        cardMembers=new ArrayList<>();
                        if(response.equals("false")){
                           /* addapter = new team_adapter_cards(getActivity(), membersPojos);

                            currentMember.setAdapter(addapter);*/

                        }else {
                            try {
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    MembersPojo membersPojo = new MembersPojo();


                                    membersPojo.setName(jsonObject.getString("first_name"));
                                    membersPojo.setProfile_pic(jsonObject.getString("profile_pic"));
                                    membersPojo.setUserId(jsonObject.getString("id"));
                                    membersPojo.setInetial(jsonObject.getString("initials"));
                                    membersPojo.setGp_pic(jsonObject.getString("gp_picture"));
                                    cardMembers.add(jsonObject.getString("id"));

                                    //  membersPojos.add(membersPojo);
/*
                                    addapter = new team_addapter(getActivity(), membersPojos);

                                    currentMember.setAdapter(addapter);*/

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        getmembers();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                params.put("card_id", cardId);

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
    public void addmember(final String usertoadd) {


        final ProgressDialog ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.ASSOSIATE_USER_CARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        getCardmembers();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                params.put("brd_id",boardId);
                params.put("prjct_id", projectId);
                params.put("card_id",cardId);
                params.put("list_id",list_id);

                SharedPreferences pref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

                params.put("userId",usertoadd);

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
    public void deletemember(final String usertoadd) {


        final ProgressDialog ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.DELETE_CARD_MEMBER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        getCardmembers();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                params.put("user_id", usertoadd);
                params.put("card_id",cardId);
                params.put("list_id",list_id);

                SharedPreferences pref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

                params.put("userId", pref.getString("user_id", ""));

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
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        CardActivity.menu = menu;

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


        /*etComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        });*/
/*
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
                            //addLabel();
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
*/



        FABLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onFocus = true;
//                menuChanger(menu,onFocus);
                //rvLabel.setLayoutManager(new LinearLayoutManager(mcontext));
               // ColorsPojo colorsPojo = new ColorsPojo();
                //   colorList.add(colorsPojo.getColor());
               // rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                /*rvLabel.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                colorList1 = new ArrayList<>();
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                rvAdapterLabel = new RVLabelAdapter(Mactivity, colorList, listt, labelNameList, asliList,colorList1,"new");
                rvLabel.setAdapter(rvAdapterLabel);
*/
                getBoaardAssignedLabels();
                fabm.close(true);
               /* FragmentManager fm = getSupportFragmentManager();

                RVLabelAdapter.index = -1;
                LabelColorFragment colorFragment = new LabelColorFragment();
                LabelColorFragment.textLabelName="";

                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer, colorFragment).addToBackStack("Frag1").commit();

                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);*/




            }
        });



        return true;
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
                                for (int j=0;j<cardLabelsPojoList.size();j++){
                                    if(jsonObject.getString("id").equals(cardLabelsPojoList.get(j).getLabelid())){
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

    private void addUser(final String userid) {
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.ASSOSIATE_USER_CARD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {
                    getCardList();

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
                Map<String, String> params = new HashMap<String, String>();
                params.put("brd_id",BoardExtended.boardId);
                params.put("prjct_id", projectId);
                params.put("card_id",cardId);
                params.put("list_id",list_id);

               params.put("userId", userid);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
    }

    private void deleteMember(final String userid) {
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.DELETE_USER_CARD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {
                    getCardList();

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
                Map<String, String> params = new HashMap<String, String>();
                params.put("card_id",cardId);
                    params.put("u_id", userid);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
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
        }else if (getSupportFragmentManager().findFragmentByTag("Frag4") != null) {
//            CardActivity.showLabelsMenu();

            getSupportFragmentManager().popBackStackImmediate("Frag4", 0);
//            showLabelsMenu();


            //   colorList.add(LabelColorFragment.getColor());
//            ColorsPojo colorsPojo = new ColorsPojo();
//            colorList.add(colorsPojo.getColor());
//            showLabelsMenu();
        } else if (getSupportFragmentManager().findFragmentByTag("Frag2") != null) {
//            CardActivity.showLabelsMenu();

            getSupportFragmentManager().popBackStackImmediate("Frag2", 0);
            showLabelsMenu();


            //   colorList.add(LabelColorFragment.getColor());
//            ColorsPojo colorsPojo = new ColorsPojo();
//            colorList.add(colorsPojo.getColor());
//            showLabelsMenu();
        }else if(getSupportFragmentManager().findFragmentByTag("Frag3") != null ){
            getSupportFragmentManager().popBackStackImmediate("Frag3", 0);
        }else if(isFromMyCardsScreen.equals("true")){
            super.onBackPressed();
            Intent intent=new Intent(CardActivity.this,MyCardsActivity.class);
            finish();
            startActivity(intent);
        }else if(isFromMyCardsScreen.equals("notifi")){
            super.onBackPressed();
            Intent intent=new Intent(CardActivity.this,NotificationsActivity.class);
            finish();
            startActivity(intent);
        } else if(isFromMyCardsScreen.equals("board")){
            super.onBackPressed();
            Intent intent=new Intent(CardActivity.this,BoardsActivity.class);
            intent.putExtra("pid", projectId);
            intent.putExtra("ptitle",BoardExtended.pTitle);
            intent.putExtra("status", projectStatus);
            finish();
            startActivity(intent);
        }else {
            super.onBackPressed();
            Intent intent=new Intent(CardActivity.this,BoardScreen.class);
            intent.putExtra("b_id",BoardExtended.boardId);
            intent.putExtra("p_id",BoardExtended.projectId);
            intent.putExtra("TitleData",BoardExtended.bTitle);
            intent.putExtra("ptitle",BoardExtended.pTitle);
            if(BoardExtended.isWorkBoard.equals("1"))
            intent.putExtra("work_board",BoardExtended.isWorkBoard);
            else
                intent.putExtra("work_board","0");
            //intent.putExtra("TitleData",CardHeading);
            finish();
            startActivity(intent);
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

   /*     rvChecklist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rvAdapterChecklist = new RVadapterCheckList(checkListPojo, getApplicationContext());
        rvChecklist.setAdapter(rvAdapterChecklist);

        rvAdapterChecklist.notifyDataSetChanged();*/


    }
    private void unSubscribe() {
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.UNSUBSCRIBE_CARD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                getCardList();
                Toast.makeText(CardActivity.this,"User successfully Unsubscribed!",Toast.LENGTH_LONG).show();
                isCardSubscribed="0";
                dataList.set(3,new DrawerPojo("Subscribe"));
                DrawerAdapter.notifyDataSetChanged();
                drawerLayout.closeDrawer(Gravity.END);

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
                Map<String, String> params = new HashMap<String, String>();
                params.put("card_id",cardId);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("uid", pref.getString("user_id",""));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
    }
    private void subscribe() {
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.SUBSCRIBE_CARD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                isCardSubscribed="1";
                dataList.set(3,new DrawerPojo("Un-subscribe"));
                DrawerAdapter.notifyDataSetChanged();
                getCardList();
                Toast.makeText(CardActivity.this,"User successfully subscribed!",Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(Gravity.END);
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("card_id",cardId);
                params.put("list_id",list_id);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("uid", pref.getString("user_id",""));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
    }
    private void deleteCard() {
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.DELETE_CARD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                onBackPressed();
                    Toast.makeText(CardActivity.this,"You have Successfully deleted Card",Toast.LENGTH_LONG).show();

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
                Map<String, String> params = new HashMap<String, String>();
                params.put("cardId",cardId);
                params.put("boardId",BoardExtended.boardId);
                params.put("projectId",BoardExtended.projectId);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
    }
    private void leaveCard() {
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.LEAVE_CARD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
               if(response.equals("1")){
                   Toast.makeText(CardActivity.this,"You are admin of this Card, You cannot leave this card!",Toast.LENGTH_LONG).show();
               }else  if(response.equals("2")){
                    Toast.makeText(CardActivity.this,"You are no longer assigned to this card",Toast.LENGTH_LONG).show();
               }else if(response.equals("0")){
                   Toast.makeText(CardActivity.this,"You have Successfully left Card",Toast.LENGTH_LONG).show();
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

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("card_id",cardId);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
    }

    private void lockCard() {
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.LOCK_CARD_BY_CARD_ID, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                dataList.set(4,new DrawerPojo("Unlock Card"));
                isCardLocked="1";
                DrawerAdapter.notifyDataSetChanged();
                Toast.makeText(CardActivity.this,"Lock Card Successfully",Toast.LENGTH_LONG).show();
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

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("card_id",cardId);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
    }
    private void unlockCard() {
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.UNLOCK_CARD_BY_CARD_ID, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                dataList.set(4,new DrawerPojo("Lock Card"));
                ringProgressDialog.dismiss();
                isCardLocked="0";
                DrawerAdapter.notifyDataSetChanged();
                Toast.makeText(CardActivity.this,"Card Unlocked Successfully",Toast.LENGTH_LONG).show();
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

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("card_id",cardId);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
    }
    public void pickDueDate(final String dateType){
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR); // current year
        final int mMonth = c.get(Calendar.MONTH); // current month
        final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        final int mHour = c.get(Calendar.HOUR);
        final int mMin = c.get(Calendar.MINUTE);

        datePickerDialog = new DatePickerDialog(CardActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if(dateType.equals("dueDate")){
                            updateCardDueDate(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                        }else if(dateType.equals("startDate")){
                            updateCardStartDate(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                        }
                        // set day of month , month and year value in the edit text
//                                                date.setText(dayOfMonth + "/"
//                                                        + (monthOfYear + 1) + "/" + year);
                       /* spinnerStartDateList.remove(0);
                        spinnerStartDateList.add(0, dayOfMonth + "-" + monthOfYear + "-" + year);
                        spinnerstartDate.setSelection(0);*/
                        // spinnerDate.setSelection(spinnerDateList.size() - 1);

                    }
                }, mYear, mMonth, mDay);
       if(!startDate.equals("") && !startDate.equals("null") && dateType.equals("dueDate")) {
           String checkStartDate[] = startDate.split("-");
           Calendar maxDate = Calendar.getInstance();
           maxDate.set(Calendar.DAY_OF_MONTH,Integer.valueOf(checkStartDate[2]));
           maxDate.set(Calendar.MONTH, Integer.valueOf(checkStartDate[1])-1);
           maxDate.set(Calendar.YEAR, Integer.valueOf(checkStartDate[0]));

           datePickerDialog.getDatePicker().setMinDate(maxDate.getTimeInMillis());
       }
        datePickerDialog.show();
    }
    public void pickATime(final String timeType){
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR); // current year
        final int mMonth = c.get(Calendar.MONTH); // current month
        final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        final int mHour = c.get(Calendar.HOUR);
        final int mMin = c.get(Calendar.MINUTE);




        timePickerDialog = new TimePickerDialog(CardActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String am_pm="";
                c.set(Calendar.HOUR_OF_DAY, selectedHour);
                c.set(Calendar.MINUTE, selectedMinute);
                if (selectedHour > 12) {
                    selectedHour -= 12;
                } else if (selectedHour == 0) {
                    selectedHour += 12;
                }
                if (c.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "am";
                else if (c.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "pm";
                if(timeType.equals("dueTime")){
                    updateCardTime(selectedHour+":"+selectedMinute+":00"+" "+am_pm);
                }else if(timeType.equals("startTime")){
                    updateCardStartTime(selectedHour+":"+selectedMinute+":00"+" "+am_pm);
                }
               /* spinnerStartTimeList.remove(0);
                spinnerStartTimeList.add(0, selectedHour + ":" + selectedMinute);

                //  spinnerTime.setSelection(spinnertTimeList.size() - 1);

                spinnerstartTime.setSelection(0);*/
            }
        }, mHour, mMin, false);//Yes 24 hour time
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void dueDate() {

        fabm.close(true);
        // Toast.makeText(getApplicationContext(),"Due Date Button Pressed",Toast.LENGTH_LONG).show();


        LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
        View customView = inflater.inflate(R.layout.custom_date_time_spinner_cards_screen, null);
        // View nothingSelectedDate = inflater.inflate(R.layout.nothing_selected_spinnerdate,null);
        final AlertDialog alertDialog = new AlertDialog.Builder(CardActivity.this).create();


        final Calendar myCurrentDT = Calendar.getInstance();
        TextView tvCancel = customView.findViewById(R.id.tvCancel);
        TextView tvDone = customView.findViewById(R.id.tvDone);
        spinnerstartDate = customView.findViewById(R.id.spinnerstartDate);
        spinnerstartTime = customView.findViewById(R.id.spinnerstartTime);
        spinnerDate = customView.findViewById(R.id.spinnerDate);
        spinnerTime = customView.findViewById(R.id.spinnerTime);


        // spinnerDate.setEmptyView(nothingSelectedDate);
        // spinnerDate.setPrompt("Today");
        spinnerDateList = new ArrayList<>();
        spinnertTimeList = new ArrayList<String>();
        spinnerStartDateList = new ArrayList<>();
        spinnerStartTimeList = new ArrayList<String>();
        spinnerDateList.add("Select Date");
        spinnerStartDateList.add("Select Date");
       /* spinnerDateList.add("Today");
        spinnerDateList.add("Tomorrow");*/
        spinnerDateList.add("Pick a day");
        spinnerStartDateList.add("Pick a day");


        spinnertTimeList.add("Select Time");
        spinnerStartTimeList.add("Select Time");
       /* spinnertTimeList.add("Morning");
        spinnertTimeList.add("After Noon");
        spinnertTimeList.add("Evening");
        spinnertTimeList.add("Night");*/
        spinnertTimeList.add("Pick a time");
        spinnerStartTimeList.add("Pick a time");
        ArrayAdapter<String> startdateAdapter = new ArrayAdapter<String>(CardActivity.this, R.layout.nothing_selected_spinnerdate, spinnerStartDateList);
        startdateAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<String> starttimeAdapter = new ArrayAdapter<String>(CardActivity.this, R.layout.nothing_selected_spinnerdate, spinnerStartTimeList);
        starttimeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(CardActivity.this, R.layout.nothing_selected_spinnerdate, spinnerDateList);
        dateAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(CardActivity.this, R.layout.nothing_selected_spinnerdate, spinnertTimeList);
        timeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerDate.setSelection(0);
        spinnerstartDate.setSelection(0);
        spinnerTime.setSelection(0);
        spinnerstartTime.setSelection(0);

        spinnerstartDate.setAdapter(startdateAdapter);
        spinnerstartTime.setAdapter(starttimeAdapter);
        spinnerDate.setAdapter(dateAdapter);
        spinnerTime.setAdapter(timeAdapter);


        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR); // current year
        final int mMonth = c.get(Calendar.MONTH); // current month
        final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        final int mHour = c.get(Calendar.HOUR);
        final int mMin = c.get(Calendar.MINUTE);
        spinnerstartDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                        spinnerStartDateList.remove(0);
                                        spinnerStartDateList.add(0, dayOfMonth + "/" + monthOfYear + "/" + year);
                                        spinnerstartDate.setSelection(0);
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

        spinnerstartTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = parent.getSelectedItem().toString();

                switch (selectedItem) {
                    case "Pick a time":


                        timePickerDialog = new TimePickerDialog(CardActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                                spinnerStartTimeList.remove(0);
                                spinnerStartTimeList.add(0, selectedHour + ":" + selectedMinute);

                                //  spinnerTime.setSelection(spinnertTimeList.size() - 1);

                                spinnerstartTime.setSelection(0);
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

                if(!spinnertTimeList.get(0).equals("Select Time") && spinnerStartTimeList.get(0).equals("Select Time")) {
                    updateCardTime("", spinnertTimeList.get(0));
                }
                if(spinnertTimeList.get(0).equals("Select Time") && !spinnerStartTimeList.get(0).equals("Select Time")){
                    updateCardTime(spinnerStartTimeList.get(0),"");
                }
                if(!spinnertTimeList.get(0).equals("Select Time") && !spinnerStartTimeList.get(0).equals("Select Time")){
                    updateCardTime(spinnerStartTimeList.get(0), spinnertTimeList.get(0));
                }

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

    public void getmembers() {

        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_BOARD_MEMBERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        membersPojos = new ArrayList<>();
                        if(response.equals("false")){
                            addapterCards = new team_adapter_cards(CardActivity.this, membersPojos);

                            currentMember.setAdapter(addapterCards);

                        }else {
                            try {
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    MembersPojo membersPojo = new MembersPojo();


                                    membersPojo.setName(jsonObject.getString("first_name")+" "+jsonObject.getString("last_name"));
                                    membersPojo.setProfile_pic(jsonObject.getString("profile_pic"));
                                    membersPojo.setUserId(jsonObject.getString("id"));
                                    membersPojo.setInetial(jsonObject.getString("initials"));
                                    membersPojo.setGp_pic(jsonObject.getString("gp_picture"));
                                    String isCardMember="0";
                                    for(int j=0;j<cardMembers.size();j++){
                                        if(jsonObject.getString("id").equals(cardMembers.get(j))){
                                            isCardMember="1";
                                        }
                                    }
                                    membersPojo.setIsCardMember(isCardMember);

                                    membersPojos.add(membersPojo);

                                    addapterCards = new team_adapter_cards(CardActivity.this, membersPojos);

                                    currentMember.setAdapter(addapterCards);
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

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                params.put("board_id", boardId);

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



    public void getCardList() {
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
                            attachmentsList = new ArrayList<>();
                            attachmentsList1 = new ArrayList<>();

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


                                labelNameList = new ArrayList<>();
                                colorList1 = new ArrayList<>();
                                lableid = new ArrayList<>();


                                for(int j=0;j<jsonArrayLabels.length();j++){

                                    JSONArray jsonArray=jsonArrayLabels.getJSONArray(j);
                                    // String[] labels = new String[jsonArray.length()];
                                    //String[] labelText = new String[jsonArray.length()];
                                    for (int k=0;k<jsonArray.length();k++){
                                        CardAssociatedLabelsPojo labelsPojo = new CardAssociatedLabelsPojo();
                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        labelsPojo.setLabelTextCards(jsonObject.getString("label_text"));
                                        labelsPojo.setLabelColorCards(jsonObject.getString("label_color"));
                                        labelsPojo.setLabelid(jsonObject.getString("board_assoc_label_id"));
                                        labelNameList.add(jsonObject.getString("label_text"));
                                        colorList1.add(jsonObject.getString("label_color"));
                                        lableid.add(jsonObject.getString("board_assoc_label_id"));

                                        /* labels[k]=jsonObject.getString("label_color");

                                        if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }*/
                                        cardLabelsPojoList.add(labelsPojo);
                                    }

                                    cardLabelsPojoListstat = cardLabelsPojoList;
                                    rvLabelResult.setLayoutManager(new LinearLayoutManager(Mactivity, LinearLayoutManager.HORIZONTAL, true));
                                    RVLabelResultAdapter adapter = new RVLabelResultAdapter(Mactivity, listt, asliList,cardLabelsPojoList);
                                    rvLabelResult.setAdapter(adapter);
                                    if(cardLabelsPojoListstat.toArray().length>0){
                                        LinearLayout linearLayout= findViewById(R.id.labelsLayout);
                                        linearLayout.setVisibility(View.VISIBLE);
                                        labelIcon.setVisibility(View.VISIBLE);
                                    }else {
                                        LinearLayout linearLayout= findViewById(R.id.labelsLayout);
                                        linearLayout.setVisibility(View.GONE);
                                        labelIcon.setVisibility(View.INVISIBLE);
                                    }
                                }

                                for(int j=0;j<jsonArrayMembers.length();j++){
                                    // CardAssociatedMembersPojo membersPojo = new CardAssociatedMembersPojo();
                                    JSONArray jsonArray=jsonArrayMembers.getJSONArray(j);
                                    // String[] members = new String[jsonArray.length()];
                                    //String[] labelText = new String[jsonArray.length()];
                                    String isMember="0";
                                    for (int k=0;k<jsonArray.length();k++){
                                        MembersPojo membersPojo=new MembersPojo();
                                        JSONObject jsonObject=jsonArray.getJSONObject(k);
                                        membersPojo.setProfile_pic(jsonObject.getString("profile_pic"));
                                        membersPojo.setInetial(jsonObject.getString("initials"));
                                        membersPojo.setUserId(jsonObject.getString("uid"));
                                        membersPojo.setName(jsonObject.getString("first_name")+" "+jsonObject.getString("last_name"));
                                        membersPojo.setGp_pic(jsonObject.getString("gp_picture"));
                                        membersPojo.setTick("1");
                                        if(jsonObject.getString("uid").equals(pref.getString("user_id",""))){
                                            isMember="1";
                                        }
                                        //  members[k]=jsonObject.getString("profile_pic");
                                        // labelText[k]=jsonObject.getString("initials");
                                       /* if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }*/
                                        cardMembersPojoList1.add(membersPojo);
                                    }
                                    if(isMember.equals("1")){
                                      if(dataList.size()==5){
                                            dataList.add(new DrawerPojo("Leave Card"));
                                        }
                                    }
                                    else {
                                        if(dataList.size()==6)
                                        dataList.remove(5);
                                    }
                                   rvMembersResult.setLayoutManager(new LinearLayoutManager(Mactivity, LinearLayoutManager.HORIZONTAL, true));
                                    memberAdapter = new RVMemberResultAdapter(Mactivity,cardMembersPojoList1,cardId,list_id,boardId,projectId);
                                    rvMembersResult.setAdapter(memberAdapter);
                                    if(cardMembersPojoList1.toArray().length>0){
                                        LinearLayout linearLayout= findViewById(R.id.membersLayout);
                                        linearLayout.setVisibility(View.VISIBLE);
                                    }else {
                                        LinearLayout linearLayout= findViewById(R.id.membersLayout);
                                        linearLayout.setVisibility(View.GONE);
                                    }
                                   /* if(membersAdapter != null)
                                    {
                                        membersAdapter.notifyDataSetChanged();

                                        if( isUpdate == true){
                                         //   showMembersDialog();
                                            isUpdate=false;
                                        }
                                    }*/
                                   //  labelsPojo.setLabelText(labelText);

                                }
                                for(int j=0;j<jsonArrayAttachments.length();j++) {

                                    heading.setVisibility(View.VISIBLE);

                                    JSONArray jsonArray = jsonArrayAttachments.getJSONArray(j);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        AttachmentsPojo membersPojo = new AttachmentsPojo();
                                        AttachmentsImageFilePojo FilePojo = new AttachmentsImageFilePojo();
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        if (jsonObject.getString("file_type").equals("image")) {
                                            FilePojo.setAttch_id(jsonObject.getString("id"));
                                            FilePojo.setImageFile(jsonObject.getString("file_name"));
                                            FilePojo.setOriginalFileName(jsonObject.getString("original_name"));
                                            FilePojo.setIsCover(jsonObject.getString("make_cover"));
                                            attachmentsList1.add(FilePojo);
                                        } else {
                                            membersPojo.setFileId(jsonObject.getString("id"));
                                            membersPojo.setNameOfFile(jsonObject.getString("file_name"));
                                            membersPojo.setDateUpload(jsonObject.getString("added_on"));
                                            membersPojo.setIsCover(jsonObject.getString("make_cover"));
                                            membersPojo.setOriginalFileName(jsonObject.getString("original_name"));
                                            attachmentsList.add(membersPojo);

                                        }

                                       /* if(jsonObject.getString("label_text")==null || jsonObject.getString("label_text").equals("null")){
                                            labelText[k]="";
                                        }else {
                                            labelText[k] = jsonObject.getString("label_text");
                                        }*/

                                        //  labelsPojo.setLabelText(labelText);

                                    }
                                    rvAttachmentImages.setLayoutManager(new LinearLayoutManager(Mactivity, LinearLayoutManager.HORIZONTAL, true));
                                    imageAdapter = new AttachmentImageAdapter(Mactivity, bitmapList, fm, attachmentsList1,cardId);
                                    rvAttachmentImages.setAdapter(imageAdapter);
                                    rvFiles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                                    FilesAdapter adapter = new FilesAdapter(attachmentsList, Mactivity,cardId);
                                    rvFiles.setAdapter(adapter);
                                }
                                if(attachmentsList.toArray().length>0 || attachmentsList1.toArray().length>0){
                                    LinearLayout linearLayout= findViewById(R.id.imageAttachmentLayout);
                                    LinearLayout linearLayout1= findViewById(R.id.filesAttachmentLayout);
                                    linearLayout.setVisibility(View.VISIBLE);
                                    linearLayout1.setVisibility(View.VISIBLE);
                                }else {
                                    LinearLayout linearLayout= findViewById(R.id.imageAttachmentLayout);
                                    LinearLayout linearLayout1= findViewById(R.id.filesAttachmentLayout);
                                    linearLayout.setVisibility(View.GONE);
                                    linearLayout1.setVisibility(View.GONE);
                                }
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


                    Toast.makeText(activity, "check your internet connection", Toast.LENGTH_SHORT).show();
                   /* new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("No Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();*/
                } else if (error instanceof TimeoutError) {


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                   /* new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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


    public  void updateCardDueDate(final String dueDat) {
        final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_DUE_DATES_BY_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        Date myDate = null;
                        try {
                            String myTime=dueDat;
                            myDate = dateFormat.parse(myTime);
                            SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MMM-yy");
                            String finalDate = timeFormat.format(myDate);
                            cbDueDate.setText(finalDate);
                            cbDueDate.setVisibility(View.VISIBLE);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dueDate=dueDat;
                        Toast.makeText(activity, "Card is updated", Toast.LENGTH_SHORT).show();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                params.put("boardId",BoardExtended.boardId);
                params.put("projectid",BoardExtended.projectId);
                params.put("card_id",cardId);
                params.put("strt_dt",startDate);
                params.put("end_dt",dueDat);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
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
    public  void updateCardStartDate(final String startDat) {
        final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_START_DATES_BY_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        Date myDate = null;
                        try {
                            String myTime=startDat;
                            myDate = dateFormat.parse(myTime);
                            SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MMM-yy");
                            String finalDate = timeFormat.format(myDate);
                            cbStartDate.setText(finalDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        startDate=startDat;

                        Toast.makeText(activity, "Card is updated", Toast.LENGTH_SHORT).show();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                params.put("boardId",BoardExtended.boardId);
                params.put("projectid",BoardExtended.projectId);
                params.put("card_id",cardId);
                params.put("strt_dt",startDat);
                params.put("end_dt",dueDate);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
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

    public  void updateCardCompleted(final String isCompleted) {
        final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_CARD_COMPLETED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(isCompleted.equals("1")){
                            isCompletedBtn.setText("Unmark Completed");
                            isCompletedBtn.setBackgroundColor(getResources().getColor(R.color.orange));
                        }else if(isCompleted.equals("0")){
                            isCompletedBtn.setText("Mark Completed");
                            isCompletedBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        }
                        Toast.makeText(activity, "Card is updated", Toast.LENGTH_SHORT).show();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                params.put("boardId",BoardExtended.boardId);
                params.put("projectid",BoardExtended.projectId);
                params.put("strt_dt",startDate);
                params.put("end_dt",dueDate);
                params.put("card_id",cardId);
                params.put("isComp",isCompleted);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
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
    public  void addDescription(final String description) {
        final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SET_CARD_DESCRIPTION_BY_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        etDescription.setText(description);
                        cardDescription=description;
                        Toast.makeText(activity, "Card is updated", Toast.LENGTH_SHORT).show();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                params.put("card_id",cardId);
                params.put("descr",description);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
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

    public  void updateCardTime(final String dueTime) {
        final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_CARD_DUE_TIME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cbDueTime.setText(dueTime);

                        Toast.makeText(activity, "Card Due Time updated", Toast.LENGTH_SHORT).show();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                params.put("boardId",BoardExtended.boardId);
                params.put("projectId",BoardExtended.projectId);
                params.put("cardId",cardId);
                params.put("start_time",startTime);
                params.put("due_time",dueTime);
                params.put("complete","");
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
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
    public  void updateCardStartTime(final String startTime) {
        final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_CARD_START_TIME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cbStartTime.setText(startTime);
                        Toast.makeText(activity, "Card Due Time updated", Toast.LENGTH_SHORT).show();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                params.put("boardId",BoardExtended.boardId);
                params.put("projectId",BoardExtended.projectId);
                params.put("cardId",cardId);
                params.put("start_time",startTime);
                params.put("due_time",dueTime);
                params.put("complete","");
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
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


    public void updateUI()
    {
        try{
            getCardList();
        }catch (OutOfMemoryError error){
            error.printStackTrace();
        }

    }

    public static void addlabel(final String lablecolor, final String lableText) {
        final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SAVE_NEW_LABELS_CARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                params.put("projectId", projectId);
                params.put("card_id",cardId);
                params.put("label_text",lableText);
                params.put("label_color",lablecolor);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode==REQUEST_PERMISSIONS) {
            if (requestCode == REQUEST_PERMISSIONS && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (requestCode == REQUEST_PERMISSIONS) {
                        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);*/
                        try {
                            camera.takePicture();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }

            }else {
                Toast.makeText(CardActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==100) {
            if (requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent();
                //sets the select file to all types of files
                intent.setType("*/*");
                //allows to select data and return it
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // starts new activity to select file and return data
                startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), READ_REQUEST_CODE);
            }else {
                Toast.makeText(CardActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==4) {
            if (requestCode == 4 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(CardActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT > 22) {

                        requestPermissions(new String[]{Manifest.permission
                                        .CAMERA},
                                REQUEST_PERMISSIONS);

                    }

                }

            }else {
                Toast.makeText(CardActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==5) {
            if (requestCode == 5 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

            } else {

                Toast.makeText(CardActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==10) {
            if (requestCode == 10 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 2);
            } else {

                Toast.makeText(CardActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void copy(File source, File destination) throws IOException {

        FileChannel in = new FileInputStream(source).getChannel();
        FileChannel out = new FileOutputStream(destination).getChannel();

        try {
            in.transferTo(0, in.size(), out);
        } catch(Exception e){
            // post to log
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public boolean isFilePresent(String fileName) {
        String path = getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }




    public Uri getImageUri(Context inContext, Bitmap inImage) {
    //    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      //  inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    private void uploadImage(final String formattedDate, final String b64, final String originalName) {
        ringProgressDialog = ProgressDialog.show(activity, "Please wait ...", "Uploading image ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.BASE_URL_FILE_UPLOAD+"upload_image_card.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {
                    // getCardList();
                    saveNewAttachmentByCardId(response.trim(),originalName);

                } else {
                    ringProgressDialog.dismiss();
                    Toast.makeText(CardActivity.this, "Picture not uploaded", Toast.LENGTH_SHORT).show();
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

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                Map<String, String> map = new HashMap<String, String>();
                map.put("image", b64);
                map.put("name", formattedDate);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
    }
    private void saveNewAttachmentByCardId(final String attachmentName,final String originalName) {
        ringProgressDialog = ProgressDialog.show(activity, "Please wait ...", " ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.SAVE_NEW_ATTACHMENT_BY_CARD_ID, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {
                    getCardList();

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

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("boardId",BoardExtended.boardId);
                params.put("projectId", projectId);
                params.put("cardId",cardId);
                params.put("listId",list_id);
                params.put("original_name",originalName);
                params.put("name",attachmentName);
                params.put("type_file","image");
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
                params.put("row", "1");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
    }
    public void makeCover(final int pos,final String isCover, final String attach_id,final String cardId){
        ringProgressDialog = ProgressDialog.show(activity, "Please wait ...", "", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.MAKE_COVER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        if(!response.equals("")) {

                                 }
                        //attachmentList.remove(pos);
                        //notifyDataSetChanged();
                        //     }else {
                        // Toast.makeText(activity, "Not updated Cover", Toast.LENGTH_SHORT).show();
                        //   }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                params.put("make_cover",isCover);
                params.put("attach_id",attach_id);
                params.put("card_id",cardId);
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

    private void saveFile(final String attachmentName,final String originalName,final String fileType) {
       /* ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();*/
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.SAVE_NEW_ATTACHMENT_BY_CARD_ID, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                progressBar.dismiss();
               // ringProgressDialog.dismiss();
                if (!(response.equals(""))) {
                    ringProgressDialog.dismiss();
                    getCardList();

                }
            }

        }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   loading.dismiss();
               // ringProgressDialog.dismiss();
                progressBar.dismiss();
                String message = null;
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("boardId",BoardExtended.boardId);
                params.put("projectId", projectId);
                params.put("cardId",cardId);
                params.put("listId",list_id);
                params.put("original_name",originalName);
                params.put("name",attachmentName);
                params.put("type_file",fileType);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
                params.put("row", "1");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
    }

    private void updateCardNameDialog() {
        LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
        View customView = inflater.inflate(R.layout.update_card_name_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(CardActivity.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(customView);
        alertDialog.show();

        Button cancel, copy;
        final EditText etCardName= customView.findViewById(R.id.etCardName);
        etCardName.setText(CardHeading);
        etCardName.setSelection(etCardName.getText().length());
        showKeyBoard(etCardName);
        copy = customView.findViewById(R.id.copy);
        cancel = customView.findViewById(R.id.close);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check=etCardName.getText().toString();
                if(!check.equals("") && check!="" && check.trim().length()>0) {
                    updateCardName(etCardName.getText().toString());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etCardName.getWindowToken(), 0);
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(CardActivity.this,"Card Name is must!",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etCardName.getWindowToken(), 0);

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
    public  void updateCardTime(final String startTime, final String dueTime) {
        final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_CARD_DUE_TIME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(activity, "Card Due Time updated", Toast.LENGTH_SHORT).show();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {


                    Toast.makeText(activity, "No internet", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                params.put("boardId",BoardExtended.boardId);
                params.put("projectId", projectId);
                params.put("cardId",cardId);
                params.put("start_time",startTime);
                params.put("due_time",dueTime);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                params.put("userId", pref.getString("user_id",""));
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
    public void setupUI(final View view, final EditText editText) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    view.clearFocus();
                    hideSoftKeyboard(CardActivity.this,editText);
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

    public static void hideSoftKeyboard(Activity activity,EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    private void showDialogCopy() {

        LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
        View customView = inflater.inflate(R.layout.copy_card_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(CardActivity.this).create();

        alertDialog.setView(customView);
        alertDialog.show();

        TextView heading, boardHeading,listHeading;

        TextView cancel, copy;

        heading = customView.findViewById(R.id.heading);
        boardHeading= customView.findViewById(R.id.boardLabel);
        listHeading= customView.findViewById(R.id.listLabel);
        etTitleCopyCard= customView.findViewById(R.id.etTitle);
        etTitleCopyCard.setText(CardHeading);
        etTitleCopyCard.setSelection(etTitleCopyCard.getText().length());
        showKeyBoard(etTitleCopyCard);
        Projects= customView.findViewById(R.id.projectSpinner);
        boardSpinner= customView.findViewById(R.id.boardSpinner);
        listSpinner= customView.findViewById(R.id.listSpinner);
        positionSpinner= customView.findViewById(R.id.positionSpinner);
        checklistcb= customView.findViewById(R.id.checklistcb);
        labelcb= customView.findViewById(R.id.labelcb);
        attachmentcb= customView.findViewById(R.id.attachmentcb);
        membercb= customView.findViewById(R.id.memberscb);
        setupUI(customView.findViewById(R.id.relativelayout),etTitleCopyCard);
        copy = customView.findViewById(R.id.copy);
        cancel = customView.findViewById(R.id.close);


        //getSpinnerData();
        getProjects();
       // getBorads(BoardExtended.projectId);


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etTitleCopyCard.getText().toString().equals("")){
                    Toast.makeText(CardActivity.this,"Error, card name is must!",Toast.LENGTH_LONG).show();
                }else if(boardSpinner.getSelectedItemPosition()==0 || boardSpinner.getSelectedItemPosition()==-1){
                    Toast.makeText(CardActivity.this,"Error, board name is must!",Toast.LENGTH_LONG).show();
                }else if(listSpinner.getSelectedItemPosition()==0 || listSpinner.getSelectedItemPosition()==-1){
                    Toast.makeText(CardActivity.this,"Error, list name is must!",Toast.LENGTH_LONG).show();
                }else {
                    hideKeyBoard(etTitleCopyCard);
                    alertDialog.dismiss();
                    copyCard();
                }
               // moveCard();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyBoard(etTitleCopyCard);
                alertDialog.dismiss();

            }
        });

    }
    public void getProjects() {

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_ALL_PROJECS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        spinnerValues = new ArrayList<>();
                        spinnerGroupIds = new ArrayList<>();
                        postions_listProjects = new ArrayList<>();
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

                    Toast.makeText(CardActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(CardActivity.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

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
            if(pos!=-1)
            getBorads(spinnerGroupIds.get(pos));



        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }
    private void showDialog(final String data) {

        LayoutInflater inflater = LayoutInflater.from(CardActivity.this);
        View customView = inflater.inflate(R.layout.move_card_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(CardActivity.this).create();

        alertDialog.setView(customView);
        alertDialog.show();

        TextView heading, boardHeading,listHeading;

        TextView cancel, copy;

        heading = customView.findViewById(R.id.heading);
        boardHeading= customView.findViewById(R.id.boardLabel);
        listHeading= customView.findViewById(R.id.listLabel);

        boardSpinner= customView.findViewById(R.id.boardSpinner);
        Projects= customView.findViewById(R.id.projectSpinner);
        listSpinner= customView.findViewById(R.id.listSpinner);
        positionSpinner= customView.findViewById(R.id.positionSpinner);
        copy = customView.findViewById(R.id.copy);
        cancel = customView.findViewById(R.id.close);


        //getSpinnerData();
       // getBorads(BoardExtended.projectId);
        getProjects();

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boardSpinner.getSelectedItemPosition()==0 || boardSpinner.getSelectedItemPosition()==-1){
                    Toast.makeText(CardActivity.this,"Error, board name is must!",Toast.LENGTH_LONG).show();
                }else if(listSpinner.getSelectedItemPosition()==0 || listSpinner.getSelectedItemPosition()==-1){
                    Toast.makeText(CardActivity.this,"Error, list name is must!",Toast.LENGTH_LONG).show();
                }else {
                    moveCard();
                    alertDialog.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                alertDialog.dismiss();

            }
        });

    }
    public void updateCardName(final String updatedText){
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.UPDATE_CARD_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        //collapsingToolbarLayout.setTitle(updatedText);
                        CardHeading=updatedText;
                        toolbar.setTitle(updatedText);
                        Toast.makeText(activity, "Card Name Updated Successfully", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(Gravity.END);



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {
                    ringProgressDialog.dismiss();

                    Toast.makeText(activity, "check your internet connection", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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


                    Toast.makeText(activity, "TimeOut eRROR", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                params.put("updated_txt", updatedText);
                params.put("card_id",cardId);
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
    public void copyCard() {


        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.COPY_NEW_CARD_BY_CARD_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        Toast.makeText(CardActivity.this, "Copy Successfully", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {

                    Toast.makeText(CardActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(CardActivity.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                int pos1 = listSpinner.getSelectedItemPosition();
                int pos = boardSpinner.getSelectedItemPosition();
                int pos2 = positionSpinner.getSelectedItemPosition();
                String checklist,label,member,attachment;
                if(checklistcb.isChecked()){
                    checklist="1";
                }else {
                    checklist="0";
                } if(labelcb.isChecked()){
                    label="1";
                }else {
                    label="0";
                } if(attachmentcb.isChecked()){
                    attachment="1";
                }else {
                    attachment="0";
                } if(membercb.isChecked()){
                    member="1";
                }else {
                    member="0";
                }
               // String[] flag_array={checklist,label,member,attachment};
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);


                params.put("copy_boardsList", boards_ids.get(pos));
                params.put("copy_boardsList_opt", list_ids.get(pos1));
                params.put("copy_boardsList_pos_opt", postions_list.get(pos2));

                params.put("menu_form_nm_title", etTitleCopyCard.getText().toString());
                params.put("userId",pref.getString("user_id",""));
                params.put("card_id",cardId);
                params.put("checklist_flag",checklist);
                params.put("label_flag",label);
                params.put("member_flag",member);
                params.put("attachment_flag",attachment);
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
    public void moveCard() {


        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.MOVE_NEW_CARD_BY_CARD_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        Toast.makeText(CardActivity.this, "Moved Successfully", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();

                if (error instanceof NoConnectionError) {

                    Toast.makeText(CardActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(CardActivity.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                int pos1 = listSpinner.getSelectedItemPosition();
                int pos = boardSpinner.getSelectedItemPosition();
                int pos2 = positionSpinner.getSelectedItemPosition();
                String positionOfCard;
                if(pos2==-1){
                    positionOfCard="0";
                }else {
                    positionOfCard=postions_list.get(pos2);
                }
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);


                params.put("move_boardsList", boards_ids.get(pos));
                params.put("move_boardsList_opt", list_ids.get(pos1));
                params.put("move_boardsList_pos_opt", positionOfCard);

                params.put("menu_form_mv_cpy_nm_title", CardHeading);
                params.put("u_id",pref.getString("user_id",""));
                params.put("card_id",cardId);
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
    public void getBorads(final String p_Id) {
        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_ALL_BOARDS_FOR_CARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        boards_ids = new ArrayList<>();
                        boards_name = new ArrayList<>();

                        boards_name.add(0,"Select");
                        boards_ids.add(0,"0");
                        if(!response.equals("false")) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    boards_name.add(jsonObject.getString("board_name"));
                                    boards_ids.add(jsonObject.getString("bid"));

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                (CardActivity.this,R.layout.nothing_selected_spinnerdate,boards_name);
                        dataAdapter.setDropDownViewResource(R.layout.nothing_selected_spinnerdate);

                        boardSpinner.setAdapter(dataAdapter);

                        boardSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener_List());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
              //  params.put("userId", pref.getString("user_id",""));
                params.put("u_id",pref.getString("user_id",""));
                params.put("project_id",p_Id);
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
    public class CustomOnItemSelectedListener_List implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, final int pos,
                                   long id) {
            if(pos!=0 & pos!=-1)
            getListNames(boards_ids.get(pos));
          //  getPosition(spinnerGroupIds.get(pos));



        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }
    public void getListNames(final String b_id){
        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_ALL_BOARD_LISTS_FOR_CARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        list_ids = new ArrayList<>();
                        lists_name = new ArrayList<>();

                        lists_name.add(0,"Select");
                        list_ids.add(0,"0");

                        try {
                            if(!response.equals("false")) {
                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    lists_name.add(jsonObject.getString("name"));
                                    list_ids.add(jsonObject.getString("id"));

                                }
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                    (CardActivity.this,R.layout.nothing_selected_spinnerdate,lists_name);
                            dataAdapter.setDropDownViewResource(R.layout.nothing_selected_spinnerdate);

                            listSpinner.setAdapter(dataAdapter);

                            listSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener_position());



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("brd_id",b_id);
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
    public class CustomOnItemSelectedListener_position implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, final int pos,
                                   long id) {
            if(pos!=-1 && pos!=0)
          getPosition(list_ids.get(pos));



        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }

    public void getPosition(final String l_id) {

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_POSITION_OF_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        postions_list = new ArrayList<>();

                        for (int i = 1; i <=Integer.valueOf(response)+1; i++) {

                            postions_list.add(i+"");
                        }

                        ArrayAdapter<String> projectADdapter;
                        projectADdapter = new ArrayAdapter<String>(CardActivity.this, R.layout.nothing_selected_spinnerdate, postions_list);
                        projectADdapter.setDropDownViewResource(R.layout.nothing_selected_spinnerdate);
                        positionSpinner.setAdapter(projectADdapter);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NoConnectionError) {

                    Toast.makeText(CardActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {

                    Toast.makeText(CardActivity.this, "Connection time out Error", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("l_id", l_id);
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
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
    public String uploadFile(final String selectedFilePath,File file) throws IOException {

        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead,bytesAvailable,bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        String path = getFilesDir().getAbsolutePath();


        File selectedFile = new File(path+"/"+selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length-1];
        //if(isFilePresent(selectedFilePath)){
        if (!(file.isFile())){


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
            return "";
        }else{
            try{
                FileInputStream fileInputStream = new FileInputStream(file);
                URL url = new URL("http://m1.cybussolutions.com/kanban/upload_file_card.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file",selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer,0,bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0){
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer,0,bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();



                //response code of 200 indicates the server status OK
                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CardActivity.this,"Uploaded ",Toast.LENGTH_LONG).show();
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CardActivity.this,"File Not Found",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(CardActivity.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(CardActivity.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
            //dialog.dismiss();
            return String.valueOf(serverResponseCode);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    @Override
    public void processFinish(String output) {
        /*ringProgressDialog = ProgressDialog.show(activity, "Please wait ...", "Uploading File ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();*/
       // ringProgressDialog.dismiss();
        if(output.equals("200"))
        {
            String fileType;
           // Toast.makeText(mcontext, "uploaded Succesfully"+ fileName, Toast.LENGTH_SHORT).show();
            if(file.contains(".jpg") || file.contains(".jpeg") || file.contains(".png") || file.contains(".gif")){
                fileType="image";
            }else {
                fileType="file";
            }
            saveFile(file,file,fileType);

        }
        else
        {
            progressBar.dismiss();
           // ringProgressDialog.dismiss();
            Toast.makeText(mcontext, "upload error", Toast.LENGTH_SHORT).show();

        }
    }

    private void addnewChecklist(final String Checklist) {
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,End_Points.ADD_NEW_CHECKLIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {
                    getChecklistData();

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

                    new SweetAlertDialog(CardActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("checkName",Checklist);
                params.put("order", "1");
                params.put("card_id",cardId);
                final SharedPreferences pref = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);

                params.put("u_id", pref.getString("user_id",""));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
    }

    static class  UploadFile extends AsyncTask<String,Integer,String> implements RecoverySystem.ProgressListener
    {
        public callBack delegate = null;
        private String upLoadServerUri = null;
        private String imagepath=null;
        private int serverResponseCode = 0;

        @Override
        protected void onPreExecute() {

            progressBar = new ProgressDialog(activity);
            progressBar.setCancelable(true);
            progressBar.setMessage("File uploading ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setProgress(0);
            progressBar.setSecondaryProgress(100);
            progressBar.setMax(100);
            progressBar.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            progressBar.setSecondaryProgress(values[0]);
          //  super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            String fileName = strings[0];

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(fileName);
            long filesize=sourceFile.length();
            filesize = filesize / 1024;

            if (!sourceFile.isFile()) {

                //  dialog.dismiss();

                Log.e("uploadFile", "Source File not exist :" + imagepath);


                return "0";

            } else {
                try {

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(End_Points.BASE_URL_FILE_UPLOAD+"upload_file_card.php");

                    // Open a HTTP  connection to  the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("file", fileName);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
                            + fileName + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of  maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    long total = 0;
                    while (bytesRead > 0) {
                        total += bytesRead;
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                        long dataUploaded = ((total / 1024) * 100 ) /filesize ;
                        publishProgress((int) dataUploaded);
                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    Log.i("uploadFile", "HTTP Response is : "
                            + serverResponseMessage + ": " + serverResponseCode);





                    //close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (MalformedURLException ex) {

                    // dialog.dismiss();
                    ex.printStackTrace();


                    Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                } catch (Exception e) {

                    //  dialog.dismiss();
                    e.printStackTrace();

                    //Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);
                }
                //   dialog.dismiss();
                return serverResponseCode+"";
            }
        }
        
        @Override
        protected void onPostExecute(String s) {
            delegate.processFinish(s);
            progressBar.dismiss();
        }

        @Override
        public void onProgress(int progress) {
            progressBar.setProgress(progress);
        }
    }
}

