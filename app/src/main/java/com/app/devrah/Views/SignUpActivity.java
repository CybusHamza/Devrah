package com.app.devrah.Views;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity {

    ProgressDialog ringProgressDialog;
    private static final int REQUEST_PERMISSIONS=0;
    private static int IMG_RESULT = 2;
    String ImageDecode;
    String mCurrentPhotoPath,ba1,mSavedPhotoName;

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    EditText etName,etLName,etEmail,etPsw,etConfirmPsw;
    Button btnSignUp,btnAddProfile;
    ImageView ivProfile;
    TextView back_btn;

    String strName,strl_name,strEmail,strPassword,strConfirmPassword,strInitials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        etName= (EditText) findViewById(R.id.input_name);
        etLName= (EditText) findViewById(R.id.input_last_name);
        etEmail= (EditText) findViewById(R.id.input_email);
      //  ivProfile= (ImageView) findViewById(R.id.profilePicture);
        etPsw= (EditText) findViewById(R.id.input_password);
        etConfirmPsw= (EditText) findViewById(R.id.input_confirm_password);
        btnSignUp= (Button) findViewById(R.id.btn_signup);
        back_btn= (TextView) findViewById(R.id.btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        //btnAddProfile= (Button) findViewById(R.id.uploadImagebtn);
        /*btnAddProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT > 22) {
                        requestPermissions(new String[]{Manifest.permission
                                        .WRITE_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);

                    }
                } else {
                    attachProfile();

                }
            }
        });*/
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strName=etName.getText().toString();
                strl_name=etLName.getText().toString();
                strEmail=etEmail.getText().toString();
                strPassword=etPsw.getText().toString();
                strConfirmPassword=etConfirmPsw.getText().toString();
                if(strName.equals("") || strl_name.equals("") || strEmail.equals("")|| strPassword.equals(""))
                {
                    Toast.makeText(SignUpActivity.this, "Fields cannot be empty ", Toast.LENGTH_SHORT).show();
                }
                else {
                    strInitials=strName.charAt(0)+""+strl_name.charAt(0);
                    if (strEmail.matches(emailPattern) && strPassword.length() >= 8 && strPassword.equals(strConfirmPassword)) {
                        Signup();
                    }else if(!strEmail.matches(emailPattern)){
                        Toast.makeText(getApplicationContext(),"Email format is not correct",Toast.LENGTH_LONG).show();
                    }else if(strPassword.length()<=7){
                        Toast.makeText(getApplicationContext(),"Password length can not be less than 8 characters!",Toast.LENGTH_LONG).show();
                    }
                    else if(!strPassword.equals(strConfirmPassword)){
                        Toast.makeText(getApplicationContext(),"Password Mismatches",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

private void attachProfile() {
   Intent intent = new Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(intent, IMG_RESULT);
    }

    public void Signup() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SIGN_UP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if (response.equals("User already Registered with an this Email Address")) {
                            new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error!")
                                    .setConfirmText("OK").setContentText("User already Registered with this Email Address")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismiss();

                                        }
                                    })
                                    .show();
                        }
                        else {

                            new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Success!")
                                    .setConfirmText("OK").setContentText("Sign up successfully, Check your registered email to activate your account")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismiss();
                                            Intent intent = new Intent(SignUpActivity.this,Login.class);
                                            finish();
                                            startActivity(intent);

                                        }
                                    })
                                    .show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("email", strEmail);
                params.put("password", strPassword);
                params.put("first_name", strName);
                params.put("last_name", strl_name);
                params.put("initials", strInitials);
//                params.put("phone_number", strphone);
//                params.put("website", strwebsite);
//                params.put("adress", stradress);
//                params.put("company_info", strcompanyinfo);
//                params.put("profile_image", mSavedPhotoName);
//                params.put("fax",strfax);

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        requestQueue.add(request);


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

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
                mCurrentPhotoPath=ImageDecode;
                int w=ivProfile.getWidth();
                int h=ivProfile.getHeight();
                Bitmap unscaled= BitmapFactory.decodeFile(ImageDecode);
                Bitmap scaled=unscaled.createScaledBitmap(unscaled,w,h,true);

                cursor.close();
                ivProfile.setImageBitmap(scaled);


            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again"+e.toString(), Toast.LENGTH_LONG)
                    .show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                attachProfile();
            } else {


                Toast.makeText(SignUpActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    /*private void uploadlogo() {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c.getTime());
        Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeToString(ba, Base64.NO_WRAP);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, End_Points.UPLOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ringProgressDialog.dismiss();
                mSavedPhotoName = response;
                //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                Signup();
                //    uploadToServer();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("image", ba1);
                params.put("name", formattedDate);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        requestQueue.add(stringRequest);
    }*/
}
