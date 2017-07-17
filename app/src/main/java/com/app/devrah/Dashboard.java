package com.app.devrah;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.os.Handler;

public class Dashboard extends AppCompatActivity {

    TextView tvTime, tvQuote;
    String[] quotes;
    ImageView imgProjects,imgFavourites,imgMyCards,imgNotifications,imgInbox,imgMenu,imgProfile;
    String quote;
    Random rand;
   // int random;
    Activity mActivity;
   String[] ProfileArray={"Edit Profile","Logoff","Change Password"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        tvTime = (TextView)findViewById(R.id.tvTime);
        tvQuote = (TextView)findViewById(R.id.tvQuote);
        imgProjects = (ImageView)findViewById(R.id.dashboard_projects);
        imgFavourites = (ImageView)findViewById(R.id.imageFavourites);
        imgMyCards = (ImageView)findViewById(R.id.imageView6);
        imgNotifications = (ImageView)findViewById(R.id.imageNotification);
        imgInbox = (ImageView)findViewById(R.id.imageInbox);
        imgMenu = (ImageView)findViewById(R.id.imageMenu);
        imgProfile = (ImageView)findViewById(R.id.imageProfile);

        imgProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),ProjectsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),FavouritesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgMyCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(),MyCardsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(),NotificationsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(),MessagesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(),CreateNewTeamActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                    builder.setTitle("")
                            .setItems(ProfileArray, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    // of the selected item
                                    switch (which){
                                        case 0:
                                            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                                            startActivity(intent);
                                            break;
                                        case 1:

                                            SharedPreferences pref = getApplicationContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.clear();
                                            editor.commit();
                                            Intent logOutIntent = new Intent(Dashboard.this,MainActivity.class);
                                            startActivity(logOutIntent);
                                            finish();

                                            break;
                                        case 2:
                                            changePasswordDialog();
                                            break;
                                    }

                                }
                            });
                    builder.show();
                    //return builder.create();

            }
        });


        quotes =new String[]{
                "Now is the time for all good men to come to the aid of their country",
                "Having small touches of colour makes it more colourful than having the whole thing in colour.",
                "I regret that I have but one life to give for my country",
                "Some people like what you do, some people hate what you do, but most people simply don’t give a damn.",
                "Information is not knoelwdge, Lets not confuse the both.",
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



        final Calendar c = Calendar.getInstance();
        String hour= String.valueOf(c.get(Calendar.HOUR));
        int minute = c.get(Calendar.MINUTE);
        int seconds =c.get(Calendar.SECOND);







      //  tvTime.setText(hour +":" +String.valueOf(minute) + ":" + String.valueOf(seconds) + " "+ amOrpm);

        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {



            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();

                int zone = c.get(Calendar.AM_PM);

                String amOrpm=((zone==Calendar.AM)?"am":"pm");


                tvTime.setText(c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND) +" "+ amOrpm);
            }
            public void onFinish() {

            }
        };
        newtimer.start();




/////////////////////
        CountDownTimer timer = new CountDownTimer(1000000000, 5000) {



            public void onTick(long millisUntilFinished) {
             //  Calendar c = Calendar.getInstance();
//

                rand = new Random();

            int    random = rand.nextInt(10);
               //   int zone = c.get(Calendar.AM_PM);
//
//                String amOrpm=((zone==Calendar.AM)?"am":"pm");


              //  tvTime.setText(c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND) +" "+ amOrpm);
        //   int m= c.get(Calendar.SECOND)%10 + 1;


            tvQuote.setText(quotes[random]);


            }
            public void onFinish() {

            }
        };
        timer.start();


    }
    private void changePasswordDialog(){
        LayoutInflater inflater = LayoutInflater.from(Dashboard.this);
        View subView = inflater.inflate(R.layout.custom_dialog_for_change_password, null);
        final EditText changePassword = (EditText)subView.findViewById(R.id.etPass);


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


}
