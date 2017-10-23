package com.app.devrah.Views;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.app.devrah.Network.End_Points;
import com.app.devrah.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    ProgressDialog ringProgressDialog;
    private static final int REQUEST_PERMISSIONS = 20;

    EditText etEmail, etPsw;
    TextView tvForgotPass;
    boolean checkBoxValue;
    SharedPreferences.Editor editor;
    String strEmail, strPassword;
    Button btnLogin;
    TextView btnSignUp;
    ImageView googleSignIn;
    private GoogleSignInOptions gso;
    private int RC_SIGN_IN = 100;
    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;

    //google api client
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = (EditText) findViewById(R.id.input_email);
        etPsw = (EditText) findViewById(R.id.input_password);
        tvForgotPass = (TextView) findViewById(R.id.forgotPassword);
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordDialog();
            }
        });

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSignUp = (TextView) findViewById(R.id.btn_signup);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String cbValue= pref.getString("Checkbox_value","");
        if (cbValue.equals("true")){
            Intent intent = new Intent(Login.this,Dashboard.class);
            startActivity(intent);
            finish();
        }

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signinbutton
        //  signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        //signInButton.setSize(SignInButton.SIZE_WIDE);
        //signInButton.setScopes(gso.getScopeArray());

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
      //  mGoogleApiClient.connect();
        googleSignIn = (ImageView) findViewById(R.id.imgViewGSignIn);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Login.this,  android.Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {


                    ActivityCompat.requestPermissions(Login.this,
                            new String[]{Manifest.permission
                                    .GET_ACCOUNTS},
                            REQUEST_PERMISSIONS);

                }

                else
                {
                    onSignInClicked();

                }

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = etEmail.getText().toString();
                strPassword = etPsw.getText().toString();
                if (strEmail.equals("") || strPassword.equals("")) {
                    Toast.makeText(Login.this, "Fields cannot be empty ", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                onSignInClicked();
            } else {


                Toast.makeText(Login.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }



    }

    private void onSignInClicked() {
        //Setting onclick listener to signing button
        //signInButton.setOnClickListener(this);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        if (mGoogleApiClient.isConnected()) {

            // clearCookies();
            mGoogleApiClient.clearDefaultAccountAndReconnect();
                    /*Plus.AccountApi
                            .clearDefaultAccount(mGoogleApiClient);

                    mGoogleApiClient.disconnect();

                    mGoogleApiClient.connect();*/
        }

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void forgotPasswordDialog() {
        // canvas.setMode(CanvasView.Mode.TEXT);
        AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);
        alert.setTitle("Forgot Password"); //Set Alert dialog title here
        alert.setMessage("Enter Your Email Here"); //Message here

        // Set an EditText view to get user input
        final EditText input = new EditText(Login.this);
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //You will get as string input data in this variable.
                // here we convert the input to a string and show in a toast.
                String srt = input.getEditableText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(!srt.matches(emailPattern)) {
                    Toast.makeText(Login.this, "Please enter a valid Email", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    forgotPass(srt);
                }


            } // End of onClick(DialogInterface dialog, int whichButton)
        }); //End of alert.setPositiveButton
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();
            }
        }); //End of alert.setNegativeButton
        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }
    public void login() {

        ringProgressDialog = ProgressDialog.show(this, "Please wait ...", "Checking Credentials ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if (response.equals("Incorrect User name or password")) {
                            new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Alert!")
                                    .setConfirmText("OK").setContentText("Incorrect Username or Password")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismiss();

                                        }
                                    })
                                    .show();
                        } else {

                            String userid;

                            try {

                                String firstname, email, lastName,phone,company,dev_tag,position,website, profilePic,initials;

                                JSONObject object = new JSONObject(response);

                                userid = object.getString("id");
                                firstname = object.getString("first_name");
                                email = object.getString("email");
                                lastName = object.getString("last_name");
                                phone=object.getString("phone");
                                company=object.getString("company");
                                dev_tag=object.getString("dev_tag");
                                position=object.getString("position");
                                website=object.getString("website");
                                profilePic = object.getString("profile_pic").trim();
                                initials = object.getString("initials");


                                SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                editor = pref.edit();
//
//                                SharedPreferences.Editor

                                editor.putString("user_id", userid);// Saving string
                                editor.putString("email", email);
                                editor.putString("first_name", firstname);
                                editor.putString("last_name", lastName);
                                editor.putString("phone", phone);
                                editor.putString("company", company);
                                editor.putString("dev_tag", dev_tag);
                                editor.putString("position", position);
                                editor.putString("website", website);
                                editor.putString("profile_pic", profilePic);
                                editor.putString("initials", initials);
                                editor.putString("Glogin", "false");
                                editor.apply();


                                    editor.putString("Checkbox_value", "true");
                                    editor.apply();


//                                if (checkBoxValue== true){
//
//                                    Intent intent = new Intent(Login.this,Dashboard.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                                else {
//
//
//                                }


                                Intent intent = new Intent(Login.this, Dashboard.class);
                                intent.putExtra("activityName", "");
                                intent.putExtra("Glogin", "");
                                startActivity(intent);
                                finish();

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

                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("user_name", strEmail);
                params.put("password", strPassword);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }


    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            /*Intent intent = new Intent(Login.this, Dashboard.class);
            intent.putExtra("activityName", "");
            intent.putExtra("Glogin", "true");
            intent.putExtra("Gname",acct.getDisplayName().toString());
            intent.putExtra("Gemail",acct.getEmail().toString());*/
            String url;
            if(acct.getPhotoUrl()!=null) {
                url = acct.getPhotoUrl().toString();

            }else {
                url="";
            }
            if(acct.getDisplayName()!=null) {
                Signup(acct.getDisplayName().toString(), acct.getEmail().toString(), url);
            }

           /* intent.putExtra("Gprofile",url.toString());
            startActivity(intent);
            finish();*/
            //Displaying name and email
//            textViewName.setText(acct.getDisplayName().toString());
//            textViewEmail.setText(acct.getEmail().toString());

            //Initializing image loader
           /* imageLoader = CustomVolleyRequest.getInstance(TestActivity.this)
                    .getImageLoader();

           *//* imageLoader.get(acct.getPhotoUrl().toString(),
                    ImageLoader.getImageListener(profilePhoto,
                            R.mipmap.ic_launcher,
                            R.mipmap.ic_launcher));

            //Loading image
            profilePhoto.setImageUrl(acct.getPhotoUrl().toString(), imageLoader);*/

        } else {
            //If login fails
           // Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }
    public void Signup(final String name, final String email, final String profileurl) {
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        Random num = new Random();
        int rnum = num.nextInt(99999999);
        if (rnum < 0) {
            rnum = rnum * -1;
        }
        final String strInitials;
        final String firstName;
        final String lastName;
        String[] fullName=name.split(" ");
        if(fullName.length==2) {
            firstName = fullName[0];
            lastName = fullName[1];
             strInitials = firstName.charAt(0) + "" + lastName.charAt(0);
        }else{

           strInitials = String.valueOf(name.charAt(0));
            firstName=name;
            lastName="";
        }

        final int finalRnum = rnum;
        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SIGN_UP_GOOGLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        if (response.equals("User already Registered with an this Email Address")) {
                            new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
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

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                            editor = pref.edit();
//
//                                SharedPreferences.Editor

                            editor.putString("user_id", response.toString());// Saving string
                            editor.putString("email", email);
                            editor.putString("first_name", name);
                            editor.putString("last_name", "");
                            editor.putString("profile_pic", profileurl);
                            editor.putString("initials", strInitials);
                            editor.putString("Glogin", "true");
                            editor.putString("Checkbox_value", "true");
                            editor.apply();
                            Intent intent = new Intent(Login.this, Dashboard.class);
                            intent.putExtra("activityName", "");
                            intent.putExtra("Glogin", "");
                            startActivity(intent);
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
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
                params.put("email", email);
                params.put("password", String.valueOf(finalRnum));
                params.put("first_name", firstName);
                params.put("last_name", lastName);
                params.put("profile_image", profileurl);
                params.put("initials", strInitials);
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


        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(request);


    }
    public void forgotPass(final String email){
        final ProgressDialog loading = ProgressDialog.show(Login.this, "", "Please wait...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, End_Points.FORGOT_PASSWORD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if(response.trim().equals("notexist")){
                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Your Email is Incorrect")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();
                }else if(response.trim().equals("1")){
                    new SweetAlertDialog(Login.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success")
                            .setConfirmText("OK").setContentText("Check Your Email to Reset Password")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();
                }else {
                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Something Wrong")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();
                }


                // dbManager.delete_both_history_tables();
                //getHistory();
                // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        String message = null;
                        if (error instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
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

                            new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error!")
                                    .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismiss();

                                        }
                                    })
                                    .show();
                            //  Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("forgot_email",email);
//                map.put("user_id", userId);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.

            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    if(googleSignInResult.isSuccess())
                    handleSignInResult(googleSignInResult);

                }
            });
        }


    }





    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d("", "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e("", "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                Toast.makeText(Login.this,"Check your internet connection and try again",Toast.LENGTH_LONG).show();
            }
        } else {
            // Show the signed-out UI
            //    showSignedOutUI();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mShouldResolve = false;
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }
}
