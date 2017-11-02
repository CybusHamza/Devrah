package com.app.devrah.Views.Messages;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.richeditor.RichEditor;

import static com.app.devrah.R.id.editor;

public class SendNewMessageActivity extends AppCompatActivity {

    private RichEditor mEditor;
    private TextView mPreview;
    Toolbar toolbar;
    EditText subject;
    MultiAutoCompleteTextView to;
    Spinner project,card,board;
    LinearLayout send,cancel;

    ArrayList<String> ids;
    ArrayList<String> name;
    ArrayList<String> devTag;
    String message;

    // projects arraylist

    ArrayList<String> project_name ;
    ArrayList<String> project_ids ;

   // boards arraylist

    ArrayList<String> boards_name ;
    ArrayList<String> boards_ids ;

    // cards arraylist

    ArrayList<String> cards_name ;
    ArrayList<String> cards_ids ;

    long p_pos,b_pos,c_pos;

    String strSubject ,strTO,strTo1 ,strproject,strboard,strcard;
    TextView c_to,c_tv_message;


    TextView emailIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_message);
        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("Send New Message");
        c_to= (TextView) findViewById(R.id.to);
        c_tv_message= (TextView) findViewById(R.id.message);
        c_to.setText(Html.fromHtml( "To "+ "<font color=#eb5a46>" + "*" + "</font>"));
        c_tv_message.setText(Html.fromHtml( "Message "+ "<font color=#eb5a46>" + "*" + "</font>"));

        to = (MultiAutoCompleteTextView) findViewById(R.id.etEmails);
        emailIds = (TextView) findViewById(R.id.emailIds);
        subject = (EditText) findViewById(R.id.etSubject);
        mEditor = (RichEditor) findViewById(R.id.editor);
        project = (Spinner) findViewById(R.id.projectSpinner);
        card = (Spinner) findViewById(R.id.cardSpinner);
        board = (Spinner) findViewById(R.id.boardSpinner);
        send = (LinearLayout) findViewById(R.id.send_button);
        cancel = (LinearLayout) findViewById(R.id.cancel_button);

        to.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(to.getText().toString());
               /* String test=to.getText().toString();
                if(test.contains(";")){

                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                p_pos = project.getSelectedItemPosition();
                b_pos = board.getSelectedItemPosition();
                c_pos = card.getSelectedItemPosition();


                strSubject = subject.getText().toString();
                strTO = to.getText().toString().trim();
                strTO=strTO.replaceAll(" ","");
                strTO = strTO.trim().replaceAll(",$", "");

                if(c_pos == 0 ||c_pos ==-1 )
                {
                    strcard= "";

                }
                else {
                    strcard =  cards_ids.get((int) c_pos);
                }

                if(b_pos == 0 ||b_pos ==-1  )
                {
                    strboard = "";
                } else {
                    strboard =  boards_ids.get((int) b_pos);
                }

                if(p_pos == 0||p_pos ==-1  )
                {
                    strproject = "" ;
                } else {
                    strproject = project_ids.get((int) p_pos);
                }

                if(subject.getText().toString().equals("") || to .getText().toString().equals(""))
                {
                    Toast.makeText(SendNewMessageActivity.this, "Fill the mandatory fields ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(strTO.contains(";")){
                        Toast.makeText(SendNewMessageActivity.this, "Please use ',' to send message to multiple users", Toast.LENGTH_SHORT).show();
                    }else {
                        /*String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if(!strTO.matches(emailPattern) && !strTO.contains(",")){
                            Toast.makeText(getApplicationContext(),"Plz Enter a valid User",Toast.LENGTH_LONG).show();
                        }else {*/
                            sendMessage();
                       // }
                    }
                }





            }
        });


        mEditor. setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                // Do Something

                message = text;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SendNewMessageActivity.this,MessagesActivity.class );
                startActivity(intent);
                finish();
            }
        });

        getProjects();

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SendNewMessageActivity.this,MessagesActivity.class );
                startActivity(intent);
                finish();
                //  onBackPressed();
            }
        });
        mEditor = (RichEditor) findViewById(editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.BLACK);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPlaceholder("Insert text here...");
        //mEditor.setInputEnabled(false);

        /*mPreview = (TextView) findViewById(R.id.preview);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });*/

        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });

        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });

        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED );
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });

        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setNumbers();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertTodo();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                Intent intent=new Intent(this,MessagesActivity.class);
                finish();
                startActivity(intent);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void insertUrlDialog(){
        LayoutInflater inflater = LayoutInflater.from(SendNewMessageActivity.this);
        View subView = inflater.inflate(R.layout.custom_dialog_for_insert_link, null);
        final EditText url = (EditText)subView.findViewById(R.id.etUrl);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Link");
        builder.setMessage("Alert");
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

    public void sendMessage () {
        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.SEND_NEW_MESSAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    ringProgressDialog.dismiss();
                        try {
                            JSONObject object=new JSONObject(response);
                            if(object.getString("message_sent").equals("0")){
                                Toast.makeText(SendNewMessageActivity.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
                            }else {
                                Intent intent=new Intent(SendNewMessageActivity.this,MessagesActivity.class );
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(SendNewMessageActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(SendNewMessageActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                SharedPreferences pref =getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userId = pref.getString("user_id", "");



                params.put("messageContent",message);
                params.put("subjct_email",strSubject);
                params.put("userId",userId);
                params.put("listofuserids",strTO);
                params.put("add_msg_prjct",strproject);
                params.put("add_msg_brd",strboard);
                params.put("add_msg_crd",strcard);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(SendNewMessageActivity.this);
        requestQueue.add(request);


    }


    public void getProjects() {
        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_MEMBER_PROJECTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();
                        project_name = new ArrayList<>();
                        project_ids = new ArrayList<>();

                        project_name.add(0,"Select");
                        project_ids.add(0,"0");

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0 ; i < jsonArray.length();i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                project_name.add(jsonObject.getString("project_name"));
                                project_ids.add(jsonObject.getString("project_id"));

                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                    (SendNewMessageActivity.this,R.layout.custom_dropdown_layout,project_name);

                            project.setAdapter(dataAdapter);

                            project.setOnItemSelectedListener(new CustomOnItemSelectedListener_projects());



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(SendNewMessageActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(SendNewMessageActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                SharedPreferences pref =getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String userId = pref.getString("user_id", "");

                params.put("id", userId);
                params.put("status", "0");

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(SendNewMessageActivity.this);
        requestQueue.add(request);


    }

    public void getBorads(final String p_Id) {
        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_WORK_BOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        boards_ids = new ArrayList<>();
                        boards_name = new ArrayList<>();

                        boards_name.add(0,"Select");
                        boards_ids.add(0,"0");

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0 ; i < jsonArray.length();i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                boards_name.add(jsonObject.getString("board_name"));
                                boards_ids.add(jsonObject.getString("id"));

                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                    (SendNewMessageActivity.this,R.layout.custom_dropdown_layout,boards_name);

                            board.setAdapter(dataAdapter);

                            board.setOnItemSelectedListener(new CustomOnItemSelectedListener_boards());



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(SendNewMessageActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(SendNewMessageActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                params.put("p_id",p_Id);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(SendNewMessageActivity.this);
        requestQueue.add(request);


    }

    private void searchUser(final String email) {


        StringRequest request = new StringRequest(Request.Method.POST, End_Points.SEARCH_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ids = new ArrayList<>();
                        name = new ArrayList<>();
                        devTag = new ArrayList<>();

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ids.add(jsonObject.getString("id"));
                                name.add(jsonObject.getString("email"));
                                devTag.add(jsonObject.getString("dev_tag"));
                            }

                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<String>(SendNewMessageActivity.this, android.R.layout.simple_list_item_1, name);
                            to.setAdapter(adapter);
                          //  to.setOnItemSelectedListener(new CustomOnItemSelectedListener_position());
                            to.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

                            /*to.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                }
                            });*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NoConnectionError) {

                  /*  new SweetAlertDialog(SendNewMessageActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("check your internet connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();*/
                } else if (error instanceof TimeoutError) {

                   /* new SweetAlertDialog(SendNewMessageActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setConfirmText("OK").setContentText("Connection TimeOut! Please check your internet connection.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();*/
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                String emailtoSearch;
                if(email.contains(",")){
                    String[] emails=email.split(", ");
                    emailtoSearch=emails[emails.length-1];
                }else {
                    emailtoSearch=email;
                }
                params.put("member_single_name", emailtoSearch);

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
    public class CustomOnItemSelectedListener_position implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, final int pos,
                                   long id) {
            if(strTo1.length()>0)
                strTo1=strTo1+","+name.get(pos);
            else
                strTo1=name.get(pos);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }
    public void getcards ( final String board_id) {
        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(this, "", "Please wait ...", true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

        final StringRequest request = new StringRequest(Request.Method.POST, End_Points.GET_ALL_CARDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ringProgressDialog.dismiss();

                        cards_ids = new ArrayList<>();
                        cards_name = new ArrayList<>();

                        cards_name.add(0,"Select");
                        cards_ids.add(0,"0");

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0 ; i < jsonArray.length();i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                cards_name.add(jsonObject.getString("card_name"));
                                cards_ids.add(jsonObject.getString("id"));

                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                    (SendNewMessageActivity.this,R.layout.custom_dropdown_layout,cards_name);

                            card.setAdapter(dataAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(SendNewMessageActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(SendNewMessageActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                params.put("brd_id",board_id);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(SendNewMessageActivity.this);
        requestQueue.add(request);


    }



    public class CustomOnItemSelectedListener_projects implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, final int pos,
                                   long id) {

            if(pos == 0)
            {
                //Toast.makeText(SendNewMessageActivity.this,"Please Select Project", Toast.LENGTH_SHORT).show();
            }
            else
            {
                getBorads(project_ids.get(pos));
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }

    public class CustomOnItemSelectedListener_boards implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, final int pos,
                                   long id) {

            if(pos == 0)
            {
               // Toast.makeText(SendNewMessageActivity.this,"Please Select Project", Toast.LENGTH_SHORT).show();
            }
            else
            {
                getcards(boards_ids.get(pos));
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SendNewMessageActivity.this,MessagesActivity.class );
        startActivity(intent);
        finish();
    }


}
