package com.app.devrah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.app.devrah.Adapters.FavouritesAdapter;
import com.app.devrah.Adapters.MyCardsAdapter;
import com.app.devrah.pojo.FavouritesPojo;
import com.app.devrah.pojo.MyCardsPojo;

import java.util.ArrayList;
import java.util.List;

import static com.app.devrah.R.id.toolbar;

public class MyCardsActivity extends AppCompatActivity {

    ListView lv;
    List<MyCardsPojo> listPojo;
    MyCardsPojo myCardsPojoData;
    MyCardsAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);
        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("My Cards");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listPojo = new ArrayList<>();
        lv = (ListView) findViewById(R.id.cardListView);
        myCardsPojoData = new MyCardsPojo();
        myCardsPojoData.setData("Project:test");
        myCardsPojoData.setBoardData("Board:Test");
        listPojo.add(myCardsPojoData);
        adapter = new MyCardsAdapter(this, listPojo);


        lv.setAdapter(adapter);
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
}
