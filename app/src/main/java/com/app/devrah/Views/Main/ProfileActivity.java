package com.app.devrah.Views.Main;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.devrah.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends AppCompatActivity {

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    ProgressDialog ringProgressDialog;


    private static int IMG_RESULT = 2;
    Intent intent;

    Calendar calendar;
    CircleImageView imageView;
    String dateAndTime;
    String img;
    String b64;
    String mCurrentPhotoPath,ImageDecode;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String currentImage;

    EditText etEmail,etF_name,etL_name,etPhoneNumber,etDevrahTag,etCompany,etPosition,etWebsite;
    String email,f_name,l_name,f_num,devrah_tag,s_company,s_position,s_website,initials,id;

    Button chooseFile,updateProfile,cancelBtn;
    private static final int REQUEST_PERMISSIONS=0;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.header);
        updateProfile = (Button) findViewById(R.id.btnUpdate);
        cancelBtn = (Button) findViewById(R.id.btnCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,Dashboard.class);
                finish();
                startActivity(intent);
            }
        });

        toolbar.setTitle("Edit Profile");

        etF_name = (EditText)findViewById(R.id.etFname);
        etL_name = (EditText)findViewById(R.id.etLName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etCompany = (EditText)findViewById(R.id.etCompany);
        etDevrahTag = (EditText)findViewById(R.id.etDevrahTag);
        etPhoneNumber = (EditText)findViewById(R.id.etPhoneNum);
        etPosition = (EditText)findViewById(R.id.etPosition);
        etWebsite = (EditText)findViewById(R.id.etWebsite);
        imageView=(CircleImageView) findViewById(R.id.profilePic);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        currentImage =pref.getString("profile_pic","");


       // img = SharedPreferences.get


       // imageView.setImageBitmap();
        Picasso.with(getApplicationContext()).load("http://m1.cybussolutions.com/kanban/uploads/profile_pictures/" + currentImage).into(imageView);




        setSupportActionBar(toolbar);

        toolbar.setTitle("Update Profile");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chooseFile= (Button) findViewById(R.id.btnchoosePic);
        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogForProfile();
            }
        });


        calendar = Calendar.getInstance();
        dateAndTime=String.valueOf(calendar.get(Calendar.DATE))
                +String.valueOf(calendar.get(Calendar.MONTH))
                + String.valueOf(calendar.get(Calendar.YEAR))
                + String.valueOf(calendar.get(Calendar.HOUR))
                + String.valueOf(calendar.get(Calendar.MINUTE))
                + String.valueOf(calendar.get(Calendar.SECOND));

       // SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

//                                SharedPreferences.Editor
      email=  pref.getString("email","");
       f_name= pref.getString("first_name","");
       l_name=  pref.getString("last_name","");
       f_num=  pref.getString("phone","");
       devrah_tag=  pref.getString("dev_tag","");
       s_company=  pref.getString("company","");
       s_position=  pref.getString("position","");
       s_website=  pref.getString("website","");
        img=pref.getString("profile_pic","");
        id = pref.getString("user_id","");

        etEmail.setText(email);
        etF_name.setText( f_name);
        etL_name.setText(l_name);
        etPhoneNumber.setText(f_num);
        etCompany.setText(s_company);
        etDevrahTag.setText("@"+devrah_tag);
        etWebsite.setText(s_website);
        etPosition.setText(s_position);



        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
               // if (email.matches(emailPattern)){



                if (b64 != null){
                    LoadImage();
                }else {
                    uploadData();
                }

                //}
               // else {
                 //   Toast.makeText(getApplicationContext(),"Enter Valid Data",Toast.LENGTH_SHORT).show();
               // }



            }
        });

    }

    public void uploadData(){
        ringProgressDialog = ProgressDialog.show(this, "Please wait ...", "Updating...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        email = etEmail.getText().toString();
        l_name= etL_name.getText().toString();
        f_name= etF_name.getText().toString();
        s_website= etWebsite.getText().toString();
        s_company = etCompany.getText().toString();
        s_position = etPosition.getText().toString();
        s_website = etWebsite.getText().toString();
        f_num =etPhoneNumber.getText().toString();
        devrah_tag = etDevrahTag.getText().toString();
        devrah_tag=devrah_tag.substring(1);
      //  img = PreferenceManager.getDefaultSharedPreferencesName(getApplicationContext())
      //  img =
        if(f_name.length()>0 && l_name.length()>0) {
            initials = String.valueOf(f_name.charAt(0)) + String.valueOf(l_name.charAt(0));
        }else if(f_name.length()>0 && l_name.length()==0){
            initials=String.valueOf(f_name.charAt(0));
        }else {
            initials="";
        }

      //  Toast.makeText(getApplicationContext(),"Upadte Pro",Toast.LENGTH_SHORT).show();
        StringRequest request = new StringRequest(Request.Method.POST,"http://m1.cybussolutions.com/kanban/Api_service/updateUserProfile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ringProgressDialog.dismiss();
                        if(!response.equals("0")){
                            Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("email", email);
                            editor.putString("first_name", f_name);
                            editor.putString("last_name", l_name);
                            editor.putString("phone", f_num);
                            editor.putString("company", s_company);
                            editor.putString("dev_tag", devrah_tag);
                            editor.putString("position", s_position);
                            editor.putString("website", s_website);
                            editor.putString("profile_pic", img);
                            editor.apply();
                            Intent intent=new Intent(ProfileActivity.this,Dashboard.class);
                            finish();
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(),"No Change Found!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    Toast.makeText(getApplicationContext(),"check your internet connection",Toast.LENGTH_SHORT).show();

            } else if (error instanceof TimeoutError) {

                 Toast.makeText(getApplicationContext(),"Time Out error",Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("last_name",l_name);
                params.put("first_name",f_name);
                params.put("website",s_website);
                params.put("company",s_company);
                params.put("position",s_position);
                params.put("email",email);
                params.put("dev_tag",devrah_tag);
                params.put("phone",f_num);
                params.put("profile_pic",img);
                params.put("initials",initials);

//                params.put("user_name",strEmail);
//                params.put("password",strPassword );
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




    private void startDialogForProfile() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                ProfileActivity.this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        /*Intent intent = new Intent(MainActivity.this, AlbumSelectActivity.class);
                        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 4);
                        startActivityForResult(intent, Constants.REQUEST_CODE);*/

                        if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


                            if (Build.VERSION.SDK_INT > 22) {

                                requestPermissions(new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE},
                                        REQUEST_PERMISSIONS);

                            }

                        } else {
                            upload();

                        }

                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {


                            if (Build.VERSION.SDK_INT > 22) {

                                requestPermissions(new String[]{Manifest.permission
                                                .CAMERA},
                                        REQUEST_IMAGE_CAPTURE);
                      /*  Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);*/
                                // Toast.makeText(MainActivity.this.getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            camera();

                        }

                    }
                });
        myAlertDialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_PERMISSIONS || requestCode==REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                if(requestCode==0) {
                    upload();
                }
                else if(requestCode==1){
                    camera();
                }
            } else {

                Toast.makeText(ProfileActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void camera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
           /* uri = getOutputMediaFileUri();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);*/
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        //  imageView.mCurrentShape = DrawingView.SMOOTHLINE;
        //imageView.reset();
    }

    private void upload() {
        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMG_RESULT);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK ) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");


                imageView.setImageBitmap(imageBitmap);
                b64 = encodeImage(imageBitmap);
                //imageView.setImageBitmap(imageBitmap);

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);
                // Toast.makeText(MainActivity.this,"Here "+ getRealPathFromURI(tempUri),Toast.LENGTH_LONG).show();
                mCurrentPhotoPath=getPathFromURI(tempUri);

            }
            //  if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            if (requestCode == IMG_RESULT  && resultCode == RESULT_OK && data != null) {
                // textView.setText(stringBuffer.toString());
                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};


                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();
               // CircleImageView imageView=(CircleImageView) findViewById(R.id.profilePic);

                Bitmap unscaled= BitmapFactory.decodeFile(ImageDecode);
                int w=imageView.getWidth();
                int h=imageView.getHeight();

               Bitmap scaled=unscaled.createScaledBitmap(unscaled,w,h,true);

//                imageView=(DrawingView)findViewById(R.id.drawingview);
                mCurrentPhotoPath=ImageDecode;
                b64 = encodeImage(scaled);

                imageView.setImageBitmap(scaled);
//
                // imageView.setImageBitmap(BitmapFactory
                //       .decodeFile(ImageDecode));

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again"+e.toString(), Toast.LENGTH_LONG)
                    .show();
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    ////CONVERT TO BITMAP////
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                Intent intent=new Intent(this,Dashboard.class);
                finish();
                startActivity(intent);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void LoadImage(){
      final ProgressDialog  ringProgressDialog = ProgressDialog.show(this, "Please wait ...", "Updating...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,"http://m1.cybussolutions.com/kanban/upload_image_mobile.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                ringProgressDialog.dismiss();
               // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                if (!(response.equals(""))) {
                    //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    img = response.trim();
                    uploadData();
//                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("img",response);
//                    editor.apply();






                } else {
                    Toast.makeText(ProfileActivity.this, "Picture not uploaded", Toast.LENGTH_SHORT).show();
                }
            }

        }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "check your internet connection";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }else if (error instanceof TimeoutError) {

                    Toast.makeText(getApplicationContext(),"Time Out error",Toast.LENGTH_SHORT).show();
                }
            }


        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("image", b64);
                map.put("name", dateAndTime);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        requestQueue.add(request);





    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(ProfileActivity.this,Dashboard.class);
        finish();
        startActivity(a);
        super.onBackPressed();
    }
}
