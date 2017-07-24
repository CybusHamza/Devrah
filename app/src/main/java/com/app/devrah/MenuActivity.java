package com.app.devrah;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.app.devrah.Adapters.AdapterMembers;
import com.app.devrah.Adapters.ProjectsAdapter;
import com.app.devrah.Adapters.TeamAdapterMenu;
import com.app.devrah.pojo.MembersPojo;
import com.app.devrah.pojo.ProjectsPojo;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {


    EditText etTeamName;
    Toolbar toolbar;
    TextView createNewTeam;
    List<ProjectsPojo> teamList;
    ListView lvTeamData;

    TeamAdapterMenu adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        lvTeamData = (ListView)findViewById(R.id.lvTeams);

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        toolbar.setTitle("Menu");
        toolbar.setNavigationIcon(R.drawable.back_arrow_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//////////////////////////CODE TO ADD TEAMS/////////////////////////////////////////
//        ProjectsPojo pojo = new ProjectsPojo();
//        pojo.setData(etTeamName.getText().toString());
//        teamList.add(pojo);
//        adapter = new TeamAdapterMenu(MenuActivity.this, teamList);
//        lvTeamData.setAdapter(adapter);

//////////////////////////////////////////////////////////////////////////////////////
        teamList = new ArrayList<>();
        createNewTeam = (TextView)findViewById(R.id.tvCreateNewTeam);

        createNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMembersDialog();

            }
        });



    }

    public void showMembersDialog(){
        LayoutInflater inflater = LayoutInflater.from(MenuActivity.this);
        View view = inflater.inflate(R.layout.alert_dialog_for_menu_create_members,null);
        final AlertDialog alertDialog = new  AlertDialog.Builder(MenuActivity.this).create();
//        ListView lvMembers = (ListView)view.findViewById(R.id.membersListView);
//        TextView tvDone = (TextView)view.findViewById(R.id.addMember);
         etTeamName = (EditText)view.findViewById(R.id.input_teamName);
        EditText etTeamDescription = (EditText)view.findViewById(R.id.input_team_description);
        TextView tvDone = (TextView)view.findViewById(R.id.addMember);
        TextView tvCanvel = (TextView)view.findViewById(R.id.cancel);
//        MembersPojo membersPojo = new MembersPojo();
//        for (int i=0;i<=5;i++) {
//            membersPojo.setName("Aqsa");
//            membersPojo.setUserId("asdfgh");
//            membersPojoList.add(membersPojo);
//        }


        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                lvTeamData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), CreateNewTeamActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });


alertDialog.dismiss();

            }
        });

        tvCanvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(view);
        alertDialog.show();


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(MenuActivity.this,Dashboard.class);
        startActivity(intent);

    }
}
