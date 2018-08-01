package com.example.lab.android.nuc.chat.view.activity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.utils.views.ActionSheetDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.netease.nrtc.video.gl.EglBase;
import com.nostra13.universalimageloader.utils.L;

import java.util.Map;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener{
    private Button changePicture;
    Spinner native_language, study_language, level;
    private Context context;
    Button changeInfoBtn;
    private EditText nicheng;

    public static String MOTHER_LANGUAGE = "汉语";
    public static String LEARN_LANGUAGE = "英语";
    public static String NICHENG = "末年之夏";
    public static String ACCOUNT = "1484290617";
    public static String EMAIL = "hyyyrwang@gmain.com";
    private String name,nativeLanguage,learnLanguage,LEVEL;

    private String UserId;

    final String[] ctype = new String[]{"汉语", "英语", "俄语", "西班牙语", "法语", "柬埔寨语", "捷克语", "匈牙利语"
            , "印度尼西亚语", "哈萨克语", "老挝语", "蒙古语", "波兰语", "塞尔维亚语", "土耳其语", "越南语", "日语", "韩语"};
    final String[] ctype1 = new String[]{"低", "中", "高"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_information );
        context = InformationActivity.this;
        //获取已经注册的userId
        Intent intent = getIntent();
        UserId = intent.getStringExtra( "USERID" );
        initView();
        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "个人信息" );
        }
    }

    private void initView(){
        changePicture = (Button) findViewById( R.id.change_picture );
        native_language = findViewById(R.id.native_language);
        study_language = findViewById(R.id.study_language);
        level = findViewById(R.id.level);
        changeInfoBtn = (Button) findViewById( R.id.change_Info );

        changeInfoBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nicheng.getText() == null){
                    Toast.makeText( context, "请填写昵称！", Toast.LENGTH_SHORT ).show();
                }else {
                    name = nicheng.getText().toString();
                    OkGo.<String>post( "http://47.95.7.169:8080/login")
                            .tag(this)
                            .isMultipart(true)
                            .params( "userID",UserId )
                            .params( "name",name )
                            .params( "nativeLanguage",nativeLanguage )
                            .params( "studyLanguage",learnLanguage )
                            .params( "languageLevel",LEVEL )
                            .execute( new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Toast.makeText(InformationActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                @Override
                                public void onError(Response<String> response) {
                                    super.onError( response );
                                    Toast.makeText(InformationActivity.this, "修改失败,请重试", Toast.LENGTH_SHORT).show();
                                }
                            } );
                }
            }
        } );
        nicheng = (EditText) findViewById( R.id.nick_name_edt );
        if (nicheng.getText() != null){
            NICHENG = nicheng.getText().toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ctype1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        native_language.setAdapter(adapter);
        study_language.setAdapter(adapter);
        level.setAdapter(adapter1);
        native_language.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nativeLanguage = ctype[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        study_language.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                learnLanguage = ctype[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        level.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LEVEL = ctype1[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        changePicture.setOnClickListener( this );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_picture:
                new ActionSheetDialog( context )
                        .builder()
                        .setCancelable( false )
                        .setCanceledOnTouchOutside( false )
                        .addSheetItem( "拍照", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                } )
                        .addSheetItem( "拍照", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                } ).show();
                break;
        }
    }


}
