package com.app.devrah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BoardExtended extends AppCompatActivity {

    ParentBoardExtendedFragment fragment;
    FragmentBoardExtendedLast lastFrag;
    EditText etPageName;
    Button addFrag;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_extended);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        String s = getIntent().getStringExtra("TitleData");
        toolbar.setTitle(s);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        fragment = (ParentBoardExtendedFragment)this.getSupportFragmentManager().findFragmentById(R.id.ajeebFrag);
    //..    lastFrag = (FragmentBoardExtendedLast)this.getSupportFragmentManager().;
        etPageName =(EditText)findViewById(R.id.editTextPageName);
        addFrag = (Button)findViewById(R.id.btnAddFrag);

        fragment.addPage("To Do");
        fragment.addPage("Doing");
        fragment.addPage("Done");

        addFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etPageName.getText().toString().equals("")) {
                    fragment.addPage(etPageName.getText() + "");
                  //  etPageName.setText(etPageName.getText() + "");
                } else {
                    Toast.makeText(BoardExtended.this, "Page name is empty", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
