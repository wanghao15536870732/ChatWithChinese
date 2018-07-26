package com.example.lab.android.nuc.chat.view.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.chat.view.adapter.Question_detail_Adapter;
import com.example.lab.android.nuc.chat.Base.Contacts.UserInfo;
import com.example.lab.android.nuc.chat.Base.Question.Question_answer;
import com.example.lab.android.nuc.chat.Base.JSON.Comment;
import com.example.lab.android.nuc.chat.view.fragment.CommentDialogFragment;
import com.example.lab.android.nuc.chat.view.fragment.DialogFragmentDataCallback;
import com.example.lab.android.nuc.chat.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class Question_detail_Activity extends AppCompatActivity implements View.OnClickListener,DialogFragmentDataCallback{


    public static final int QUQESTION_NUMBER = 1;
    private List<Question_answer> mQuestionAnswerList = new ArrayList<>(  );

    public static String Question_name = "question_name";

    public static String Question_image_ID = "question_image_id";

    public static String Question_detail_text = "question_detail_text";

    private TextView commentFakeButton;

    private TextView answer_name;

    private ImageView answer_image_id;

    private TextView answer_detail;



    private String str;
    private String question_detail;
    private Question_detail_Adapter adapter;
    public ImageView actionBarBack;
    private int question_number;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_question_detail_ );
        initView();

        RecyclerView recyclerView = (RecyclerView) findViewById( R.id.question_response );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );
        adapter = new Question_detail_Adapter( mQuestionAnswerList );
        recyclerView.setAdapter( adapter );
        commentFakeButton = (TextView) findViewById(R.id.tv_comment_fake_button);
        commentFakeButton.setOnClickListener(this);
        getComment();
        initQuestion_answer();
        Intent intente = getIntent();
        str = intente.getStringExtra( "question_time" );
        question_detail = intente.getStringExtra( "question_detail" );

    }

    public void initView(){
        Intent intent = getIntent();
        String question_name = intent.getStringExtra( Question_name );
        int question_image_id = intent.getIntExtra( Question_image_ID,0);
        String question_detail = intent.getStringExtra( Question_detail_text );

       question_number = 1;

        actionBarBack = (ImageView) findViewById(R.id.question_add_back );
        answer_name = (TextView) findViewById( R.id.question_peo_name );
        answer_image_id = (ImageView) findViewById( R.id.question_peo_Image );
        answer_detail = (TextView) findViewById( R.id.text_content );

        answer_name.setText(question_name );
        Glide.with( this ).load( question_image_id).into( answer_image_id );
        answer_detail.setText( question_detail );

        actionBarBack.setOnClickListener( this );
        getComment();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById( R.id.swipe_refresh_comment );
        mSwipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshComment();
            }
        } );
    }
    private void refreshComment() {
        new Thread( new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(  2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                         getComment();
                         adapter.notifyDataSetChanged();
                         mSwipeRefreshLayout.setRefreshing( false );
                    }
                } );
            }
        } ).start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.question_add_back:
                finish();
                break;
            case R.id.tv_comment_fake_button:
                CommentDialogFragment commentDialogFragment = new CommentDialogFragment();
                commentDialogFragment.show(getFragmentManager(), "CommentDialogFragment");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.question_toolbar ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:
                Toast.makeText( this, "分享成功", Toast.LENGTH_SHORT ).show();
                break;
            case R.id.collect:
                Toast.makeText( this, "收藏成功", Toast.LENGTH_SHORT ).show();
                break;
            case R.id.report:
                Toast.makeText( this, "已举报", Toast.LENGTH_SHORT ).show();
                break;
            default:
        }
        return true;
    }


    public void initQuestion_answer(){
        mQuestionAnswerList.clear();
            Question_answer answer_1 = new Question_answer( R.drawable.apple_pic,"Tom",
                    "我认为者是个很大的问题","昨天下午 8 : 30");
            mQuestionAnswerList.add( answer_1 );
            Question_answer answer_2 = new Question_answer( R.drawable.icon,"Jack",
                    "我认为无所谓啦","今天上午 6 : 12");
            mQuestionAnswerList.add( answer_2 );
            Question_answer answer_3 = new Question_answer( R.drawable.p1,"Kangkang",
                    "中国文化博大精深啊","星期一 早上 9 : 11");
            mQuestionAnswerList.add( answer_3 );

    }


    private Comment mComment;

    String UserID;
    private String questionNumber;
    private String name;
    private String picture;
    private String commentTime;
    private String commentDetail;
    private void getComment(){
        OkGo.<String>post("http://47.95.7.169:8080/getComment" )
                .tag( this )
                .isMultipart( true )
                .params( "questionNumber",1 )
                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e( "return_comment",response.body() );
                        mComment = JSON.parseObject( response.body(),Comment.class);
                        name = mComment.getName();
                        picture= mComment.getPicture();
                        commentTime = mComment.getCommentTime();
                        commentDetail = mComment.getCommentDetail();
                        questionNumber = String.valueOf( mComment.getQuestionNumber() );
                        commentDetail = Comment.commentDetail;
                        commentTime = Comment.commentTime;
                        Question_answer question = new Question_answer( R.drawable.icon,UserInfo.name,
                                commentDetail,commentTime);
                        mQuestionAnswerList.add( 0,question );
                        adapter.notifyItemInserted( 0 );
                    }
                } );
    }
    @Override
    public String getCommentText() {
        return commentFakeButton.getText().toString();
    }

    @Override
    public void setCommentText(String commentTextTemp) {
        commentFakeButton.setText( commentTextTemp );
    }
}
