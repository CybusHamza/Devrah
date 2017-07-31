package com.app.devrah.Views;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity {

    ProgressDialog ringProgressDialog;

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    EditText etName,etEmail,etPsw,etConfirmPsw;
    Button btnSignUp;

    String strName,strEmail,strPassword,strConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        etName= (EditText) findViewById(R.id.input_name);
        etEmail= (EditText) findViewById(R.id.input_email);
        etPsw= (EditText) findViewById(R.id.input_password);
        etConfirmPsw= (EditText) findViewById(R.id.input_confirm_password);
        btnSignUp= (Button) findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strName=etName.getText().toString();
                strEmail=etEmail.getText().toString();
                strPassword=etPsw.getText().toString();
                strConfirmPassword=etConfirmPsw.getText().toString();
                if(strName.equals("") || strEmail.equals("")|| strPassword.equals(""))
                {
                    Toast.makeText(SignUpActivity.this, "Fields cannot be empty ", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (strEmail.matches(emailPattern) && strPassword.length() >= 8 && strPassword.equals(strConfirmPassword)) {
                        Signup();
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
                                    .setConfirmText("OK").setContentText(response)
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
                                    .setConfirmText("OK").setContentText("Sign up Successful")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismiss();

                                            finish();

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
//                params.put("last_name", strl_name);
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
}
