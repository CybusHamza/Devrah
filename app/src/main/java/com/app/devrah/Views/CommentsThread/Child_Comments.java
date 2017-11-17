package com.app.devrah.Views.CommentsThread;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.app.devrah.Adapters.RvAdapter1;
import com.app.devrah.Holders.ViewUtils;
import com.app.devrah.Holders.callBack;
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.app.devrah.Views.cards.CardActivity;
import com.app.devrah.pojo.CommentsPojo;
import com.app.devrah.pojo.Level;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.app.devrah.Holders.FilePath.getDataColumn;
import static com.app.devrah.Holders.FilePath.isDownloadsDocument;
import static com.app.devrah.Holders.FilePath.isExternalStorageDocument;
import static com.app.devrah.Holders.FilePath.isMediaDocument;


public class Child_Comments extends AppCompatActivity implements callBack{
    String[] fileName;
    String file;
    private String upLoadServerUri = null;
    private String imagepath=null;
    private static final int REQUEST_PERMISSIONS=0;
    private static final int READ_REQUEST_CODE = 42;
    RecyclerView rv;
    String checkListId;
    List<CommentsPojo> listPojo;
    RvAdapter1 adapter;
    Toolbar toolbar;
    EditText etComments;
    ImageView sendComments,sendAttachment;
    String b64,formattedDate;
    ProgressDialog ringProgressDialog;
    String filepath,cardId,parentId,fullName,parentCommentsData,parentProfilePic,parentInitials,parentIsFile,parentFileType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list__comments);
        rv = (RecyclerView) findViewById(R.id.rv);
        toolbar = (Toolbar) findViewById(R.id.header);
        etComments= (EditText) findViewById(R.id.commenttext);
        sendComments= (ImageView) findViewById(R.id.send);
        sendAttachment= (ImageView) findViewById(R.id.attachmentIcon);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.my_menu);
        toolbar.setTitle("Replies");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        //view line 1 is handled form xml no need to handle programmatically we are only handling line two and three
        ViewUtils.handleVerticalLines(findViewById(R.id.view_line_2));
        Intent intent=  getIntent();
        checkListId=intent.getStringExtra("checklid");
        cardId=intent.getStringExtra("cardId");
        parentId=intent.getStringExtra("id");
        fullName=intent.getStringExtra("parentComment");
        parentCommentsData=intent.getStringExtra("parentCommentData");
        parentProfilePic=intent.getStringExtra("parentProfilePic");
        parentInitials=intent.getStringExtra("parentInitials");
        parentIsFile=intent.getStringExtra("parentIsFile");
        parentFileType=intent.getStringExtra("parentFileType");
        getComments();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etComments.getWindowToken(), 0);
        etComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etComments.setCursorVisible(true);
            }
        });
        sendComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comments=etComments.getText().toString();
                if(!comments.equals("") && comments.trim().length()>0) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etComments.getWindowToken(), 0);
                    addComments(etComments.getText().toString());
                    etComments.setCursorVisible(false);
                }else {
                    Toast.makeText(getApplicationContext(),"Please Enter some comment",Toast.LENGTH_LONG).show();
                }
            }
        });
        sendAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = LayoutInflater.from(Child_Comments.this);
                View view = inflater.inflate(R.layout.custom_attachments_layout_dialog, null);


                final AlertDialog alertDialog = new AlertDialog.Builder(Child_Comments.this).create();

                LinearLayout linearLayoutCamera = (LinearLayout) view.findViewById(R.id.linearLayoutCamera);
                LinearLayout otherFiles = (LinearLayout) view.findViewById(R.id.otherFiles);

                linearLayoutCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(ActivityCompat.checkSelfPermission(Child_Comments.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                            if (Build.VERSION.SDK_INT > 22) {

                                requestPermissions(new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE},
                                        4);

                                alertDialog.dismiss();
                            }

                        }else if(ActivityCompat.checkSelfPermission(Child_Comments.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                            if (Build.VERSION.SDK_INT > 22) {

                                requestPermissions(new String[]{Manifest.permission
                                                .CAMERA},
                                        REQUEST_PERMISSIONS);

                                alertDialog.dismiss();
                            }

                        }

                        else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);
                            alertDialog.dismiss();

                        }


                    }
                });
                otherFiles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ActivityCompat.checkSelfPermission(Child_Comments.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

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
                            //   intent.setSelector(Intent.getIntent().removeCategory(););

                            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 2);
                        }


                    }
                });


                alertDialog.setView(view);
                alertDialog.show();

            }
        });

/*
        RvAdapter rvAdapter = new RvAdapter(this);
        rvAdapter.addItem(new DataModal(Level.LEVEL_ONE,"India"));
        //Add Level 2 Item
        rvAdapter.addItem(new DataModal(Level.LEVEL_TWO,"Uttar Pradesh"));


        //Add Level 2 Item
        rvAdapter.addItem(new DataModal(Level.LEVEL_TWO,"Tamil Nadu"));


        rvAdapter.addItem(new DataModal(Level.LEVEL_ONE,"U.S."));
        //Add Level 2 Item
        rvAdapter.addItem(new DataModal(Level.LEVEL_TWO,"California"));
        //Add Level 3 Item

        rvAdapter.addItem(new DataModal(Level.LEVEL_ONE,"Netherlands"));
        //Add Level 2 Item
        rvAdapter.addItem(new DataModal(Level.LEVEL_TWO,"Drenthe"));
        //Add Level 3 Item

        //Add Level 2 Item
        rvAdapter.addItem(new DataModal(Level.LEVEL_TWO,"Flevoland"));
        //Add Level 3 Item

        rv.setAdapter(rvAdapter);*/
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

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

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

            new UploadFile().execute(filepath);

        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getData().getPath();

            Uri selectedImageUri = data.getData();
            Bitmap bitmap=BitmapFactory.decodeFile(imagepath);

            String path = "";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                path  = getPath(Child_Comments.this, selectedImageUri);
            }
            if(path!=null && !path.equals("")) {
                fileName = path.split("/");
                file = fileName[fileName.length - 1];

                UploadFile uploadFile = new UploadFile();
                uploadFile.delegate = Child_Comments.this;
                uploadFile.execute(path);

                ringProgressDialog = ProgressDialog.show(Child_Comments.this, "Please wait ...", "Uploading File ...", true);
                ringProgressDialog.setCancelable(false);
                ringProgressDialog.show();
            }else {
                Toast.makeText(this,"file not found",Toast.LENGTH_LONG).show();
            }


        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode==REQUEST_PERMISSIONS) {
            if (requestCode == REQUEST_PERMISSIONS && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (requestCode == REQUEST_PERMISSIONS) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);

                    }
                }

            }else {
                Toast.makeText(Child_Comments.this, "Permission Denied", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Child_Comments.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==4) {
            if (requestCode == 4 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(Child_Comments.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT > 22) {

                        requestPermissions(new String[]{Manifest.permission
                                        .CAMERA},
                                REQUEST_PERMISSIONS);

                    }

                }

            }else {
                Toast.makeText(Child_Comments.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==5) {
            if (requestCode == 5 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

            } else {

                Toast.makeText(Child_Comments.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==10) {
            if (requestCode == 10 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 2);
            } else {

                Toast.makeText(Child_Comments.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
    private void uploadImage(final String formattedDate, final String b64, final String originalName) {
        ringProgressDialog = ProgressDialog.show(Child_Comments.this, "Please wait ...", "Uploading image ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,"http://m1.cybussolutions.com/devrah/upload_image_comment.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                ringProgressDialog.dismiss();
                if (!(response.equals(""))) {
                    String imageName=response.trim().toString();
                    // getCardList();
                    addImageComments(imageName,"image/jpeg");

                } else {
                    ringProgressDialog.dismiss();
                    Toast.makeText(Child_Comments.this, "Picture not uploaded", Toast.LENGTH_SHORT).show();
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

                    new SweetAlertDialog(Child_Comments.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(Child_Comments.this, SweetAlertDialog.ERROR_TYPE)
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

        RequestQueue requestQueue = Volley.newRequestQueue(Child_Comments.this);
        requestQueue.add(request);
    }
    public void updateData(){
        getComments();
    }
    public void getComments() {

        final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.GETCHILDCOMMENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        listPojo = new ArrayList<>();
                        CommentsPojo commentsPojo1=new CommentsPojo();
                        commentsPojo1.setId("");
                        commentsPojo1.setCardId("");
                        commentsPojo1.setChecklistId("");
                        commentsPojo1.setComments(parentCommentsData);
                        commentsPojo1.setFullName(fullName);
                        commentsPojo1.setIsFile(parentIsFile);
                        commentsPojo1.setFileType(parentFileType);
                        commentsPojo1.setParentId("");
                        commentsPojo1.setDate("");
                        commentsPojo1.setCreatedBy("");
                        commentsPojo1.setProfilePic(parentProfilePic);
                        commentsPojo1.setInitials(parentInitials);
                        commentsPojo1.setLevel(Level.LEVEL_ONE);
                        listPojo.add(commentsPojo1);
                        if(response.equals("false")){


                        }else {
                            try {
                                JSONObject object = new JSONObject(response);
                                String data = object.getString("child_comments");
                                //  String childData=object.getString("child_comments");
                                JSONArray jsonArray = new JSONArray(data);
                                // JSONArray jsonArray1=new JSONArray(childData);

                                for (int i=0;i<jsonArray.length();i++){
                                    CommentsPojo commentsPojo=new CommentsPojo();
                                    JSONObject obj = new JSONObject(jsonArray.getString(i));
                                    commentsPojo.setId(obj.getString("id"));
                                    commentsPojo.setCardId(obj.getString("card_id"));
                                    commentsPojo.setChecklistId(obj.getString("checklist_id"));
                                    commentsPojo.setComments(obj.getString("comments"));
                                    commentsPojo.setFullName(obj.getString("fullname"));
                                    commentsPojo.setIsFile(obj.getString("is_Upload"));
                                    commentsPojo.setFileType(obj.getString("file_type"));
                                    commentsPojo.setParentId(obj.getString("id"));
                                    commentsPojo.setDate(obj.getString("created_on"));
                                    commentsPojo.setCreatedBy(obj.getString("created_by"));
                                    commentsPojo.setProfilePic(obj.getString("profile_pic"));
                                    commentsPojo.setInitials(obj.getString("initials"));
                                    commentsPojo.setParentProfilePic(obj.getString("profile_pic"));
                                    commentsPojo.setParentInitials(obj.getString("initials"));
                                    commentsPojo.setParentIsFile(obj.getString("is_Upload"));
                                    commentsPojo.setParentfileType(obj.getString("file_type"));
                                    commentsPojo.setLevel(Level.LEVEL_TWO);
                                    listPojo.add(commentsPojo);
                                   /* JSONArray array=jsonArray1.getJSONArray(i);
                                    if(array.length()>0){
                                        for (int j=0;j<array.length();j++) {

                                            CommentsPojo commentsPojo1 = new CommentsPojo();
                                            JSONObject obj1 = new JSONObject(array.getString(j));
                                            commentsPojo1.setId(obj1.getString("id"));
                                            commentsPojo1.setCardId(obj1.getString("card_id"));
                                            commentsPojo1.setChecklistId(obj1.getString("checklist_id"));
                                            commentsPojo1.setComments(obj1.getString("comments"));
                                            commentsPojo1.setFullName(obj1.getString("fullname"));
                                            commentsPojo1.setIsFile(obj1.getString("is_Upload"));
                                            commentsPojo1.setIsFile(obj1.getString("file_type"));
                                            commentsPojo1.setParentId(obj.getString("id"));
                                            commentsPojo1.setLevel(Level.LEVEL_TWO);
                                            listPojo.add(commentsPojo1);
                                        }
                                    }*/

                                }

                                adapter = new RvAdapter1(Child_Comments.this, listPojo);

                                rv.setAdapter(adapter);


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

                    new SweetAlertDialog(Child_Comments.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(Child_Comments.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("check_id",checkListId);
                params.put("id",parentId);
                // SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                // String userID = pref.getString("user_id", "");
                //params.put("userId", userID);
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
    public void addComments(final String comments) {

        final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.REPLYCOMMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        if(!response.equals("0")){
                            getComments();
                            etComments.setText("");
                            etComments.clearFocus();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(Child_Comments.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(Child_Comments.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("card_id",CardActivity.cardId);
                params.put("check_id",checkListId);
                params.put("comments",comments);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userID = pref.getString("user_id", "");
                String fullName=pref.getString("first_name","")+" "+pref.getString("last_name","");
                params.put("userId", userID);
                params.put("fullname", fullName);
                params.put("parentId", parentId);
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
    public void addImageComments(final String comments,final String fileType) {

        final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.ADDCHILDIMAGECOMMENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        if(!response.equals("0")){
                            getComments();
                            etComments.setText("");
                            etComments.clearFocus();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(Child_Comments.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(Child_Comments.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("card_id",CardActivity.cardId);
                params.put("check_id",checkListId);
                params.put("comments",comments);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userID = pref.getString("user_id", "");
                String fullName=pref.getString("first_name","")+" "+pref.getString("last_name","");
                params.put("userId", userID);
                params.put("fullname", fullName);
                params.put("is_Upload", "1");
                params.put("file_type", fileType);
                params.put("id", parentId);
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
    public void processFinish(String output) {
        if(output.equals("200"))
        {
            String fileType;
            // Toast.makeText(mcontext, "uploaded Succesfully"+ fileName, Toast.LENGTH_SHORT).show();
            if(file.contains(".jpg") || file.contains(".jpeg") || file.contains(".png") || file.contains(".gif")){
                ringProgressDialog.dismiss();
                addImageComments(file,"image/jpeg");
                fileType="image";
            }else {
                ringProgressDialog.dismiss();
                addImageComments(file,"file");
                fileType="file";
            }
            // saveFile(file,file,fileType);

        }
        else
        {
            ringProgressDialog.dismiss();
            Toast.makeText(this, "upload error", Toast.LENGTH_SHORT).show();

        }
    }

    class  UploadFile extends AsyncTask<String,Void,String> {
        public callBack delegate = null;
        private String upLoadServerUri = null;
        private String imagepath = null;
        private int serverResponseCode = 0;

        @Override
        protected void onPreExecute() {


            super.onPreExecute();
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


            if (!sourceFile.isFile()) {

                //  dialog.dismiss();

                Log.e("uploadFile", "Source File not exist :" + imagepath);


                return "0";

            } else {
                try {

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL("http://m1.cybussolutions.com/devrah/upload_file_comments.php");

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

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

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
                return serverResponseCode + "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            delegate.processFinish(s);
        }
    }

}
