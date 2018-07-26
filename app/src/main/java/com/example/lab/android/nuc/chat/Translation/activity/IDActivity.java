package com.example.lab.android.nuc.chat.Translation.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.Translation.ToastUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/***
 * 设置appid 和密钥  将appid和密钥写到手机内存中。
 */
public class IDActivity extends AppCompatActivity {
    private EditText mEtId,mEtPw;
    private Button mBtnSet;
    private RadioGroup radioGroup;
    private RadioButton radioButton1,radioButton2;

    private String choose;

    public void setChoose(String choose) {
        this.choose = choose;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_id);
        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mEtId=findViewById(R.id.et_id);
        mEtPw=findViewById(R.id.et_pw);
        mBtnSet=findViewById(R.id.btn_set);
        radioGroup=findViewById(R.id.rg_choose);
        radioButton1=findViewById(R.id.rb_baidu);
        radioButton2=findViewById( R.id.rb_youdao);


        final String[] strs = readerVlaue();
        if (strs != null) {
            mEtId.setText(strs[0]);
            mEtPw.setText(strs[1]);
                switch (strs[2]) {
                    case "baidu" :
                        radioButton1.setChecked(true);
                        break;
                    case "youdao" :
                        radioButton2.setChecked(true);
                        break;
               }

        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton=radioGroup.findViewById(i);
                switch (radioButton.getText().toString()){
                    case "百度翻译api":
                        setChoose("baidu");
                        break;
                    case "有道智云api":
                        setChoose("youdao");
                        break;
                }
                ToastUtil.showToast(IDActivity.this,radioButton.getText().toString());

            }
        });


        mBtnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String appid = mEtId.getText().toString();
                String pw = mEtPw.getText().toString();


                File dir = getApplicationContext().getFilesDir();//查找这个应用下的所有文件所在的目录
                Log.d("文件夹：" , dir.getAbsolutePath());
                FileWriter writer;
                try {
                    writer = new FileWriter(dir.getAbsolutePath() + "/userinfo.txt");
                    writer.append(appid+","+pw+","+choose);
                    writer.close();
                    ToastUtil.showToast(IDActivity.this,"设置成功");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public String[] readerVlaue() {
        //读取数据
        File dir = getApplicationContext().getFilesDir();//查找这个应用下的所有文件所在的目录
        FileReader reader;
        try {
            reader = new FileReader(dir.getAbsolutePath() + "/userinfo.txt");
            BufferedReader breader = new BufferedReader(reader);
            String line = breader.readLine();
            String strs[] = line.split(",");
            breader.close();
            return strs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}

