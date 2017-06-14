package com.app.devrah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.app.devrah.Adapters.FavouritesAdapter;
import com.app.devrah.pojo.FavouritesPojo;
import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {
    ListView lv;
    List<FavouritesPojo> listPojo;
    FavouritesPojo favouritesPojoData;
    FavouritesAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("Favourites");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listPojo = new ArrayList<>();
        lv = (ListView)findViewById(R.id.favouriteListView);
        favouritesPojoData = new FavouritesPojo();
        favouritesPojoData.setData("test");
        listPojo.add(favouritesPojoData);
        adapter = new FavouritesAdapter(this, listPojo);


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
