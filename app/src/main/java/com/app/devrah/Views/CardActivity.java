package com.app.devrah.Views;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.pojo.AttachmentsImageFilePojo;
import com.app.devrah.pojo.AttachmentsPojo;
import com.app.devrah.pojo.CardAssociatedLabelsPojo;
import com.app.devrah.pojo.CardAssociatedMembersPojo;
import com.app.devrah.pojo.CardCommentData;
import com.app.devrah.pojo.DrawerPojo;
import com.app.devrah.pojo.MembersPojo;
import com.app.devrah.pojo.ProjectsPojo;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.app.devrah.Network.End_Points.GET_LABELS;

public class CardActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS=0;
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
    String dueDateTime,startDateTime;
    static RVLabelAdapter rvAdapterLabel;
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
    static List<String> lableid;
    CheckBox cbDueDate,cbStartDate;
    List<AttachmentsPojo> attachmentsList;
    List<AttachmentsImageFilePojo> attachmentsList1;
    private ListView mDrawerList;
   static String cardId;
    List<ProjectsPojo> listPojo1,labelsPojoList;
    List<CardAssociatedLabelsPojo> cardLabelsPojoList;
    static  List<CardAssociatedLabelsPojo> cardLabelsPojoListstat;
    List<CardAssociatedMembersPojo> cardMembersPojoList;
    static  String name,strColor;
    static  int color;
    ProgressDialog ringProgressDialog;


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
/*
               int pos = RVLabelAdapter.pos;



                addlabel( RVLabelAdapter.colornames.get(pos),name);*/


                rvLabelResult.setVisibility(View.VISIBLE);
                labelAdd.setVisibility(View.GONE);
                rvLabel.setVisibility(View.GONE);


                return true;
            }
        });



    }

    public  static void showDatColors(List<String> data)
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
        rvAdapterLabel = new RVLabelAdapter(Mactivity, colorList, listt, labelNameList, asliList,data,lableid,"continue");
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

        dueDateTime = intent.getStringExtra("cardduedate");
        startDateTime = intent.getStringExtra("cardstartdate");


        getCardList();

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


        fm = getSupportFragmentManager();
        labelAdd = (TextView) findViewById(R.id.tvAddLabel);
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


        LACheckList = (LinearLayout) findViewById(R.id.LinearLayoutChecklist);
        rvCard = (RelativeLayout) findViewById(R.id.bg_card);
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        simpleProgressBar.setScaleY(3f);    //height of progress bar
        staticCheckList = (LinearLayout) findViewById(R.id.linearLayoutCheckboxHeading);
        cbDueDate = (CheckBox) findViewById(R.id.tvDue);
        cbStartDate = (CheckBox) findViewById(R.id.tvStartDate);
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

        if(!startDateTime.equals("")) {
            cbStartDate.setText(startDateTime);
            cbStartDate.setVisibility(View.VISIBLE);
        }

        if(!dueDateTime.equals("")) {
            cbDueDate.setText(dueDateTime);
            cbDueDate.setVisibility(View.VISIBLE);
        }

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
                        if (ActivityCompat.checkSelfPermission(CardActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {


                            if (Build.VERSION.SDK_INT > 22) {

                                requestPermissions(new String[]{Manifest.permission
                                                .CAMERA},
                                        REQUEST_PERMISSIONS);
                                alertDialog.dismiss();
                      /*  Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);*/
                                // Toast.makeText(MainActivity.this.getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);
                            alertDialog.dismiss();

                        }


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

                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");
                labelNameList.add("");

                RVLabelAdapter.index = -1;

                FragmentManager fm = getSupportFragmentManager();

                LabelColorFragment colorFragment = new LabelColorFragment();

                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer, colorFragment).addToBackStack("Frag1").commit();

                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
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

        cbStartDate.setOnClickListener(new View.OnClickListener() {
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
            Uri tempUri = getImageUri(getApplicationContext(), bitmap);

            String picturePath=getPathFromURI(tempUri);
            Bitmap bm = BitmapFactory.decodeFile(picturePath);
            if (!picturePath.equals("")) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formattedDate = df.format(c.getTime());

                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, bao);
                byte[] ba = bao.toByteArray();
                b64 = Base64.encodeToString(ba,Base64.NO_WRAP);
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
            }
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

            try {
                uploadFile(outputFileUri.getPath(),sourceFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

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

                FragmentManager fm = getSupportFragmentManager();

                RVLabelAdapter.index = -1;
                LabelColorFragment colorFragment = new LabelColorFragment();

                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer, colorFragment).addToBackStack("Frag1").commit();

                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);




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
        spinnerstartDate = (Spinner) customView.findViewById(R.id.spinnerstartDate);
        spinnerstartTime = (Spinner) customView.findViewById(R.id.spinnerstartTime);
        spinnerDate = (Spinner) customView.findViewById(R.id.spinnerDate);
        spinnerTime = (Spinner) customView.findViewById(R.id.spinnerTime);


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
                                            FilePojo.setIsCover(jsonObject.getString("make_cover"));
                                            attachmentsList1.add(FilePojo);
                                        } else {
                                            membersPojo.setNameOfFile(jsonObject.getString("original_name"));
                                            membersPojo.setDateUpload(jsonObject.getString("added_on"));
                                            membersPojo.setIsCover(jsonObject.getString("make_cover"));
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

                                    FilesAdapter adapter = new FilesAdapter(attachmentsList, Mactivity);
                                    rvFiles.setAdapter(adapter);
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


    public void updateUI()
    {
        getCardList();
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
                params.put("board_id",BoardExtended.boardId);
                params.put("projectId",BoardExtended.projectId);
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

        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(requestCode==REQUEST_PERMISSIONS){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);

                }
            } else {

                Toast.makeText(CardActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }

        }
        if(requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
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
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "Please wait ...", "Uploading image ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,"http://m1.cybussolutions.com/kanban/upload_image_card.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {
                    // getCardList();
                    saveNewAttachmentByCardId(response.trim().toString(),originalName);

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
                Map<String, String> map = new HashMap<String, String>();
                map.put("image", b64);
                map.put("name", formattedDate);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CardActivity.this);
        requestQueue.add(request);
    }
    private void saveNewAttachmentByCardId(final String attachmentName,final String originalName) {
        ringProgressDialog = ProgressDialog.show(CardActivity.this, "Please wait ...", " ...", true);
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
                params.put("boardId",BoardExtended.boardId);
                params.put("projectId",BoardExtended.projectId);
                params.put("cardId",cardId);
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
    private void updateCardNameDialog() {

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
                params.put("projectId",BoardExtended.projectId);
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
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
