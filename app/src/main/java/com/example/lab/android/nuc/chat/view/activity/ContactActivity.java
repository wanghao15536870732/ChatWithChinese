package com.example.lab.android.nuc.chat.view.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.chat.Base.json.UserBaseInfo;
import com.example.lab.android.nuc.chat.Communication.bean.ChatConst;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.utils.views.RoundRectangleImageView;
import com.example.lab.android.nuc.chat.Communication.activity.ServiceChatActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class ContactActivity extends AppCompatActivity {

    public static  String USERID = "UserID";

    public static final String CONTACT_NAME = "contact_name";

    public static final String CONTACT_IAMGE_ID = "contact_image_id";

    private String learnLanguage,nativeLanguage,languageLevel;
    private static String userID = "1";
    private Button Atbutton;
    private TextView contact_name,native_language,learn_language;
    private String interest_text,toPerson_text,toGoal_text,imageUri;
    private TextView interest,toPerson,toGoal;



    private UserBaseInfo info;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_contact );
        initView();
        //获取点击前的图片跟姓名
        Intent intent = getIntent();
        final String contactName = intent.getStringExtra( CONTACT_NAME );
        final int contactNameId = intent.getIntExtra( CONTACT_IAMGE_ID, 0 );
        userID = intent.getStringExtra( USERID );
        learnLanguage = intent.getStringExtra( "learnLanguage" );
        nativeLanguage = intent.getStringExtra( "nativeLanguage" );
        languageLevel = intent.getStringExtra( "languageLevel" );
        imageUri = intent.getStringExtra( "ImageUri" );
        contact_name.setText( contactName );
        native_language.setText( nativeLanguage );
        learn_language.setText( learnLanguage );

        requestcontact();


        //获取布局实例
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar_1 );
        setSupportActionBar( toolbar );
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById( R.id.collapsing_toolbar );
        final RoundRectangleImageView contactImageView = (RoundRectangleImageView) findViewById( R.id.contact_ImageView );

        Atbutton = (Button) findViewById( R.id.test_bt );

        Atbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Atbutton.getText().toString().equals( "+ 关注" )){
                    Atbutton.setText( "√ 已关注" );
                    Toast.makeText( ContactActivity.this, "已关注", Toast.LENGTH_SHORT ).show();

                }else {
                    Atbutton.setText( "+ 关注" );
                    Toast.makeText( ContactActivity.this, "已取消关注", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled( true );
        }
        // /将文字加载到图片上
        collapsingToolbar.setTitle( contactName );


        Glide.with( this ).load( imageUri ).into( contactImageView );

        FloatingActionButton floatingActionButton_chat = (FloatingActionButton) findViewById( R.id.floatingActionBar_chat );
        floatingActionButton_chat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ContactActivity.this, ServiceChatActivity.class );
                intent.putExtra(ServiceChatActivity.CONTACT_NAME, contactName);
                intent.putExtra(ServiceChatActivity.CONTACT_IMAGE, contactNameId );
                intent.putExtra( "contact_image_uri",imageUri );
                ChatConst.TAG  = 0;
                startActivity( intent );
            }
        } );

        FloatingActionButton floatingActionButton_video = (FloatingActionButton) findViewById( R.id.floatingActionBar_video );
        floatingActionButton_video.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ContactActivity.this, VideoChatActivity.class );
                intent.putExtra( ServiceChatActivity.CONTACT_NAME, contactName);
                ContactActivity.this.startActivity( intent );
            }
        } );

    }

    private void initView(){
        contact_name = (TextView) findViewById( R.id.contact_name );
        native_language = (TextView) findViewById( R.id.tv_native_language );
        learn_language = (TextView) findViewById( R.id.contact_now_learn );
        interest = (TextView) findViewById( R.id.interest );
        toPerson = (TextView) findViewById( R.id.toPerson );
        toGoal = (TextView) findViewById( R.id.toGoal );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        //返回上一个Activity的menu
        return super.onOptionsItemSelected( item );
    }

    private void requestcontact(){
        OkGo.<String>post( "http://47.95.7.169:8080/getUserInfo")
                .tag( this )
                .isMultipart( true)
                .params( "UserID",userID)
                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i( "return","all"  + response.body());
                        info = JSON.parseObject( response.body(), UserBaseInfo.class );
                        interest_text = info.getInterest();
                        toPerson_text = info.getToPerson();
                        toGoal_text = info.getToGoal();
                        Log.i( "return", "ninterest : " + info.getInterest() );
                        Log.i( "return","toPerson : " + info.getToPerson());
                        Log.i( "return","toGoal : " + info.getToGoal() );
                        interest.setText( interest_text );
                        toPerson.setText( toPerson_text );
                        toGoal.setText( toGoal_text );

                    }
                } );

    }
}
