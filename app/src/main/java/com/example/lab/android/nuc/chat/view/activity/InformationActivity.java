package com.example.lab.android.nuc.chat.view.activity;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.utils.views.ActionSheetDialog;
import com.netease.nrtc.video.gl.EglBase;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener{
    private Button changePicture;
    Spinner native_language, study_language, level;
    private Context context;

    final String[] ctype = new String[]{"汉语", "英语", "俄语", "西班牙语", "法语", "柬埔寨语", "捷克语", "匈牙利语"
            , "印度尼西亚语", "哈萨克语", "老挝语", "蒙古语", "波兰语", "塞尔维亚语", "土耳其语", "越南语", "日语", "韩语"};
    final String[] ctype1 = new String[]{"低", "中", "高"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_information );
        context = InformationActivity.this;
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        study_language.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        level.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
