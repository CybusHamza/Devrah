package com.app.devrah.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.devrah.R;

public class viewMessage extends AppCompatActivity {


    EditText  from ,message ,board,card,project,subject;
    String  strfrom ,strmessage ,strboard,strcard,strproject,strsubject;
    Button cancel_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        from = (EditText) findViewById(R.id.etEmails);
        board = (EditText) findViewById(R.id.boardSpinner);
        card = (EditText) findViewById(R.id.cardSpinner);
        project = (EditText) findViewById(R.id.projectSpinner);
        subject = (EditText) findViewById(R.id.etSubject);
        message = (EditText) findViewById(R.id.editor);
        cancel_button = (Button) findViewById(R.id.cancel_button);

        Intent intent = getIntent();

        strfrom = intent.getStringExtra("to");
        strmessage = intent.getStringExtra("message");
        strboard = intent.getStringExtra("board");
        strcard = intent.getStringExtra("card");
        strproject = intent.getStringExtra("project");
        strsubject = intent.getStringExtra("subject");


        from.setText(strfrom);
        if(strboard.equals("null")){
            board.setText("NA");
        }else {
            board.setText(strboard);
        }
        if(strcard.equals("null"))
            card.setText("NA");
        else
        card.setText(strcard);
        if(strproject.equals("null"))
            project.setText("NA");
        else
        project.setText(strproject);
        subject.setText(strsubject);

        message.setText(Html.fromHtml(Html.fromHtml(strmessage).toString()));


        from.setClickable(false);
        board.setClickable(false);
        card.setClickable(false);
        project.setClickable(false);
        subject.setClickable(false);
        message.setClickable(false);

        from.setEnabled(false);
        board.setEnabled(false);
        card.setEnabled(false);
        project.setEnabled(false);
        subject.setEnabled(false);
        message.setEnabled(false);


        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
