package com.app.devrah.Views.Messages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.richeditor.RichEditor;

import static com.app.devrah.R.id.editor;

public class Reply extends AppCompatActivity {
    EditText  board,card,project,subject;
    MultiAutoCompleteTextView from;
    private RichEditor mEditor;
    String  strfrom ,strmessage ,strboard,strcard,strproject,strsubject,email;
    LinearLayout cancel_button,send_btn;
    Toolbar toolbar;
    String message,projectId,boardId,cardId,msgDate,strTO;
    ArrayList<String> ids;
    ArrayList<String> name;
    ArrayList<String> devTag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("Reply");
        from = (MultiAutoCompleteTextView) findViewById(R.id.etEmails);
        board = (EditText) findViewById(R.id.boardSpinner);
        card = (EditText) findViewById(R.id.cardSpinner);
        project = (EditText) findViewById(R.id.projectSpinner);
        subject = (EditText) findViewById(R.id.etSubject);
        mEditor = (RichEditor) findViewById(R.id.editor);
        cancel_button = (LinearLayout) findViewById(R.id.cancel_button);
        send_btn = (LinearLayout) findViewById(R.id.send_button);
        /*mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.BLACK);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);*/

        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
      //  mEditor.setPlaceholder("Insert text here...");

        Intent intent = getIntent();

        strfrom = intent.getStringExtra("to");
        strmessage = intent.getStringExtra("message");
        strboard = intent.getStringExtra("board");
        strcard = intent.getStringExtra("card");
        strproject = intent.getStringExtra("project");
        strsubject = intent.getStringExtra("subject");
        projectId = intent.getStringExtra("p_id");
        boardId = intent.getStringExtra("b_id");
        cardId = intent.getStringExtra("c_id");
        msgDate = intent.getStringExtra("date");
        email = intent.getStringExtra("email");
       // String showText="-------------On "+msgDate+"  "+strfrom+"-------------"+'\n'+strmessage
      //  mEditor.setPlaceholder();
        /*send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        from.setText(email+",");
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
        subject.setText("Re: "+strsubject);

        //message=strmessage;
       // mEditor.setHtml(String.valueOf(Html.fromHtml(Html.fromHtml(strmessage).toString())));


        from.setClickable(false);
        board.setClickable(false);
        card.setClickable(false);
        project.setClickable(false);
        subject.setClickable(false);

       // from.setEnabled(false);
        board.setEnabled(false);
        card.setEnabled(false);
        project.setEnabled(false);
        //subject.setEnabled(false);
        mEditor. setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                // Do Something

                message = text;
            }
        });
        mEditor = (RichEditor) findViewById(editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(15);
        mEditor.setEditorFontColor(Color.BLACK);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPlaceholder("Insert text here...");
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

        SharedPreferences pref =getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userName=pref.getString("first_name","")+ pref.getString("last_name", "");
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        mEditor.setEditorFontSize(15);
        mEditor.focusEditor();
        String defualtText="\n"+"-------------On "+thisDate+"  "+userName+"-------------"+"\n"+strmessage;
       mEditor.setHtml("<br>"+Html.fromHtml(defualtText).toString());

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(message.equals("")){
                 //   Toast.makeText(Reply.this,"Please Enter some message",Toast.LENGTH_LONG).show();
                //}else {
                strTO = from.getText().toString().trim();
                strTO=strTO.replaceAll(" ","");
                strTO = strTO.trim().replaceAll(",$", "");
                strsubject = subject.getText().toString();
                if(mEditor.getHtml().isEmpty()){
                    Toast.makeText(Reply.this,"Please Enter some message",Toast.LENGTH_LONG).show();
                }else {
                    if(subject.getText().toString().equals("") || from.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(Reply.this, "Fill the mandatory fields ", Toast.LENGTH_SHORT).show();
                    }else {
                        if(strTO.contains(";")){
                            Toast.makeText(Reply.this, "Please use ',' to send message to multiple users", Toast.LENGTH_SHORT).show();
                        }else {
                            sendMessage();
                        }
                    }
                }
                //}
            }
        });
        from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(from.getText().toString());
               /* String test=to.getText().toString();
                if(test.contains(";")){

                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
                                    new ArrayAdapter<String>(Reply.this, android.R.layout.simple_list_item_1, name);
                            from.setAdapter(adapter);
                            //  to.setOnItemSelectedListener(new CustomOnItemSelectedListener_position());
                            from.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

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
                        Intent intent=new Intent(Reply.this,MessagesActivity.class );
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ringProgressDialog.dismiss();
                if (error instanceof NoConnectionError) {

                    new SweetAlertDialog(Reply.this, SweetAlertDialog.ERROR_TYPE)
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

                    new SweetAlertDialog(Reply.this, SweetAlertDialog.ERROR_TYPE)
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


                message=mEditor.getHtml();
                params.put("messageContent",message);
                params.put("subjct_email",strsubject);
                params.put("userId",userId);
                params.put("listofuserids",strTO);
                params.put("add_msg_prjct",projectId);
                params.put("add_msg_brd",boardId);
                params.put("add_msg_crd",cardId);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(Reply.this);
        requestQueue.add(request);


    }
}
