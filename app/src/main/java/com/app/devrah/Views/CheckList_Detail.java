package com.app.devrah.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.app.devrah.Adapters.Checklist_detailed_Addapter;
import com.app.devrah.R;

import java.util.ArrayList;


public class CheckList_Detail extends AppCompatActivity {

    ArrayList<String> checkListiItemName;
    ArrayList<String> checkListiItemIds;
    ArrayList<String> checkedItem;
    ListView header;
    String name;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list__detail);

        header = (ListView) findViewById(R.id.check_id);
        Intent intent = getIntent();
        checkListiItemIds = intent.getStringArrayListExtra("checkListiItemIds");
        checkListiItemName = intent.getStringArrayListExtra("checkListiItemName");
        checkedItem = intent.getStringArrayListExtra("checkedItem");
        name = intent.getStringExtra("name");
        toolbar = (Toolbar) findViewById(R.id.header);

        toolbar.setTitle(name);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));


        Checklist_detailed_Addapter checklist_detailed_addapter = new Checklist_detailed_Addapter(CheckList_Detail.this,R.layout.checklist_item_row,checkListiItemName,checkListiItemIds,checkedItem);

        header.setAdapter(checklist_detailed_addapter);

    }
}
