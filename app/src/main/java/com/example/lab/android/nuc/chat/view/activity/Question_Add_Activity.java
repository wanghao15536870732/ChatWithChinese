package com.example.lab.android.nuc.chat.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab.android.nuc.chat.Base.Contacts.Contact;
import com.example.lab.android.nuc.chat.Base.Contacts.UserInfo;
import com.example.lab.android.nuc.chat.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Question_Add_Activity extends AppCompatActivity implements View.OnClickListener{

    public ImageView actionBarBack;

    public TextView putTextView;

    private Contact mContext;

    private EditText question_link,question_title,question_detail;

    private int i = 0;

    SimpleDateFormat formatter = new SimpleDateFormat( "yyyy年MM月dd日   HH:mm:ss" );
    Date curDate = new Date( System.currentTimeMillis() );
    private String str = formatter.format( curDate );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_question__add_ );
        initView();
    }

    public void initView(){
        actionBarBack = (ImageView) findViewById(R.id.question_add_back );
        actionBarBack.setOnClickListener( this);
        putTextView = (TextView) findViewById( R.id.put_in_question );
        putTextView.setOnClickListener( this );
        question_link = (EditText) findViewById( R.id.question_link);
        question_title = (EditText) findViewById( R.id.question_title );
        question_detail = (EditText) findViewById( R.id.question_detail );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.question_add_back:
                AlertDialog.Builder dialog = new AlertDialog.Builder( Question_Add_Activity.this );
                dialog.setTitle( "一路语伴小提示" );
                dialog.setMessage( "确定要放弃这次编辑么？" );
                dialog.setCancelable( true );
                dialog.setPositiveButton( "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                } );
                dialog.setNegativeButton( "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                } );
                dialog.show();
                break;
            case R.id.put_in_question:
                Intent intent = new Intent();
                String link = question_link.getText().toString();
                String title = question_title.getText().toString();
                String detail = question_detail.getText().toString();
                if (title.equals("") || detail.equals( "" )){
                    Toast.makeText( Question_Add_Activity.this, "请填写标题及内容", Toast.LENGTH_SHORT ).show();
                }else {
                    intent.putExtra( "question_link", link);
                    intent.putExtra( "question_title",title );
                    intent.putExtra( "question_detail" ,detail);
                    intent.putExtra( "id",1 );
                    com.example.lab.android.nuc.chat.Base.JSON.Question question = new com.example.lab.android.nuc.chat.Base.JSON.Question();
                    question.setUserID( "10" );
                    question.setQuestionNumber( i );
                    question.setQuestionDetail( detail );
                    question.setQuestionTime( str );
                    question.setQuestionTitle( title );
                    OkGo.<String>post(  "http://47.95.7.169:8080/setQuestion")
                            .tag( this )
                            .isMultipart( true )
                            .params( "UserID",question.getUserID())
                            .params( "name", UserInfo.name)
                            .params( "questionTitle",question.getQuestionTitle())
                            .params( "questionDetail",question.getQuestionDetail())
                            .params("questionTime",question.getQuestionTime() )
                            .params( "picture",UserInfo.picture)
                            .params( "country",UserInfo.country)
                            .params( "questionNumber",i)
                            .execute( new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.i( "return",response.body());
                                    Toast.makeText( Question_Add_Activity.this, "发表成功", Toast.LENGTH_SHORT ).show();
                                    i ++;
                                }
                            } );
                    setResult( RESULT_OK,intent );
                    finish();
                }
                break;
            default:
        }
    }


}
