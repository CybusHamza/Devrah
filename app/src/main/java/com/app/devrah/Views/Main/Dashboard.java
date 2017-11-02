package com.app.devrah.Views.Main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.devrah.FireBase_Notifications.RegistrationIntentService;
import com.app.devrah.R;
import com.app.devrah.Views.Favourites.FavouritesActivity;
import com.app.devrah.Views.Messages.MessagesActivity;
import com.app.devrah.Views.MyCards.MyCardsActivity;
import com.app.devrah.Views.Notifications.NotificationsActivity;
import com.app.devrah.Views.Project.ProjectsActivity;
import com.app.devrah.Views.Teams.MenuActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener{

    TextView tvTime, tvQuote,tvAlias,tvAuthor;
    String[] quotes,authorName;
    ImageView imgProjects, imgFavourites, imgMyCards, imgNotifications, imgInbox, imgMenu;
    String quote;
    Random rand;
    CircleImageView imgProfile;
    // int random;
    String currentImage,initials;
    Activity mActivity;
    String gLogin;
    String[] ProfileArray = {"Edit Profile", "Logoff", "Change Password"};
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
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

        tvTime = (TextView) findViewById(R.id.tvTime);
        tvQuote = (TextView) findViewById(R.id.tvQuote);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvAlias = (TextView) findViewById(R.id.alias_img);
        imgProjects = (ImageView) findViewById(R.id.dashboard_projects);
        imgFavourites = (ImageView) findViewById(R.id.imageFavourites);
        imgMyCards = (ImageView) findViewById(R.id.imageView6);
        imgNotifications = (ImageView) findViewById(R.id.imageNotification);
        imgInbox = (ImageView) findViewById(R.id.imageInbox);
        imgMenu = (ImageView) findViewById(R.id.imageMenu);
        imgProfile = (CircleImageView) findViewById(R.id.imageProfile);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        currentImage = pref.getString("profile_pic", "");
        initials = pref.getString("initials", "");
        gLogin = pref.getString("Glogin", "");


        if(!currentImage.equals("") && !currentImage.equals("null")) {
            Picasso.with(getApplicationContext())
                    .load("http://m1.cybussolutions.com/kanban/uploads/profile_pictures/" + currentImage)
                    .into(imgProfile);
        }else {
            tvAlias.setVisibility(View.VISIBLE);
            tvAlias.setText(initials);
        }
        /*Intent intent1=getIntent();
        if(intent1.getStringExtra("Glogin").equals("true")){
            String url=intent1.getStringExtra("Gprofile");
            if(!url.equals("")) {
                Picasso.with(getApplicationContext())
                        .load(url)
                        .into(imgProfile);
            }
        }*/

        imgProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ProjectsActivity.class);
                // finish();
                startActivity(intent);

            }
        });
        imgFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
                //  finish();
                startActivity(intent);

            }
        });
        imgMyCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), MyCardsActivity.class);
                // finish();
                startActivity(intent);

            }
        });
        imgNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), NotificationsActivity.class);
                //   finish();
                startActivity(intent);

            }
        });
        imgInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), MessagesActivity.class);
                finish();
                startActivity(intent);

            }
        });
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
              //  finish();
                //  Intent intent = new Intent(getApplicationContext(), CreateNewTeamActivity.class);
                startActivity(intent);

            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleApiClient.connect();
                if(gLogin.equals("true")){
                    ProfileArray= new String[]{"Logoff"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                    builder.setTitle("Settings")
                            .setItems(ProfileArray, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    // of the selected item
                                    switch (which) {
                                        case 0:

                                            if (mGoogleApiClient.isConnected()) {
                                                mGoogleApiClient.clearDefaultAccountAndReconnect().setResultCallback(new ResultCallback<Status>() {

                                                    @Override
                                                    public void onResult(Status status) {

                                                        mGoogleApiClient.disconnect();
                                                        SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = pref.edit();
                                                        editor.clear();
                                                        editor.apply();
                                                        Intent logOutIntent = new Intent(Dashboard.this, Login.class);
                                                        finish();
                                                        startActivity(logOutIntent);
                                                    }
                                                });

                                            }



                                            break;
                                    }

                                }
                            });
                    builder.show();
                }else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                    builder.setTitle("Settings")
                            .setItems(ProfileArray, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    // of the selected item
                                    switch (which) {
                                        case 0:
                                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                            finish();
                                            startActivity(intent);
                                            break;
                                        case 1:

                                            SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.clear();
                                            editor.apply();
                                            Intent logOutIntent = new Intent(Dashboard.this, Login.class);
                                            finish();
                                            startActivity(logOutIntent);


                                            break;
                                        case 2:
                                            changePasswordDialog();
                                            break;
                                    }

                                }
                            });
                    builder.show();
                }
                //return builder.create();

            }
        });


        quotes = new String[]{
                "Now is the time for all good men to come to the aid of their country",
                "Having small touches of colour makes it more colourful than having the whole thing in colour.",
                "I regret that I have but one life to give for my country",
                "Some people like what you do, some people hate what you do, but most people simply don’t give a damn.",
                "Information is not knowledge, Lets not confuse the both.",
                "Interesting things never happen to me. I happen to them.",
                "Do, or do not. There is no “try”.",
                "Not everyone notices the flowers you plant, but everyone will notice the fire you start.",
                "The simplest way to achieve simplicity is through thoughtful reduction.",
                "Design is a formal response to a strategic question.",
                "Solving any problem is more important than being right.",
                "Sometimes there is no need to be either clever or original.",
                "If you’re not failing every now and again, it’s a sign you aren’t doing anything very innovative.",
                "When is the last time you saw a Lamborghini sale?",
                "Write drunk; edit sober.",
                "Some people never go crazy. What truly horrible lives they must live.",
                "No one ever discovered anything new by coloring inside the lines.",
                "Everything is designed. Few things are designed well.",
                "Remember it takes a lot of shit, to create a beautiful flower.",
                "I never let my schooling get in the way of my education."
        };
        authorName = new String[]{
                "Charles E. Weller",
                " DIETER RAMS ",
                "Nathan Hale",
                "Charles Bukowski",
                "W. Edwards Deming",
                "BERNARD SHAW ",
                "Yoda",
                "Unknown",
                "John Maeda",
                "Mariona Lopez",
                "Milton Glaser",
                "Unknown",
                "Woody Allen ",
                "Chris Campbell",
                " Jeff Goins",
                "Charles Bukowski",
                "Thomas Vasquez",
                "Brian Reed",
                "Jacob Cass",
                "Mark Twain"
        };


        final Calendar c = Calendar.getInstance();



        //  tvTime.setText(hour +":" +String.valueOf(minute) + ":" + String.valueOf(seconds) + " "+ amOrpm);

        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {


            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                int hours =c.get(Calendar.HOUR);
                int minutes = c.get(Calendar.MINUTE);
                int seconds = c.get(Calendar.SECOND);
                if(hours==0){
                    hours=12;
                }

                int zone = c.get(Calendar.AM_PM);

                String amOrpm = ((zone == Calendar.AM) ? "am" : "pm");
                ///+ ":" + (seconds < 10 ? "0" + seconds : seconds)
                tvTime.setText((hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes)+ " " + amOrpm);
               // tvTime.setText(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + " " + amOrpm);
            }

            public void onFinish() {

            }
        };
        newtimer.start();


/////////////////////
        CountDownTimer timer = new CountDownTimer(1000000000, 140000) {


            public void onTick(long millisUntilFinished) {
                //  Calendar c = Calendar.getInstance();
//

                rand = new Random();

                int random = rand.nextInt(10);
                //   int zone = c.get(Calendar.AM_PM);
//
//                String amOrpm=((zone==Calendar.AM)?"am":"pm");


                //  tvTime.setText(c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND) +" "+ amOrpm);
                //   int m= c.get(Calendar.SECOND)%10 + 1;


                tvQuote.setText('"'+quotes[random]+'"');
                tvAuthor.setText(authorName[random]);


            }

            public void onFinish() {

            }
        };
        timer.start();


        Intent intent = new Intent(Dashboard.this, RegistrationIntentService.class);
        startService(intent);

    }

    private void changePasswordDialog() {
        LayoutInflater inflater = LayoutInflater.from(Dashboard.this);
        View subView = inflater.inflate(R.layout.custom_dialog_for_change_password, null);
        final EditText changePassword = (EditText) subView.findViewById(R.id.etPass);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Password");
        builder.setMessage("");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
    @Override
    public void onBackPressed() {
       // clearBackstack();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        super.onBackPressed();
    }
    public void clearBackstack() {

        int backStackEntry = getSupportFragmentManager().getBackStackEntryCount();

        if ( backStackEntry > 0) {
            for (int i = 0; i < backStackEntry; i++) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
            for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                Fragment mFragment = getSupportFragmentManager().getFragments().get(i);
                if (mFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
                }
            }
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
