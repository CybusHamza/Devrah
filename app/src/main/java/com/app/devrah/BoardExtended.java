package com.app.devrah;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BoardExtended extends AppCompatActivity {

    ParentBoardExtendedFragment fragment;
    FragmentBoardExtendedLast lastFrag;
    EditText etPageName;
    Button addFrag;
    Toolbar toolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_extended);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        String s = getIntent().getStringExtra("TitleData");
        toolbar.setTitle(s);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.inflateMenu(R.menu.menu);
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
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.search_workboard));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.search_workboard));

            isSearchOpened = true;
        }
    }
    @Override
    public void onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }
    private void doSearch() {
//
    }
}
