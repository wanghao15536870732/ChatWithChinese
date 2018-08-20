package com.example.lab.android.nuc.chat.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.lab.android.nuc.chat.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText uesr_name;
    EditText password;
    TextView change_password;
    TextView register;
    Button button;
    String n, p;
    private ImageView iv_see_password;
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在加载布局文件前判断是否登陆过
        sprfMain = PreferenceManager.getDefaultSharedPreferences(this);
        editorMain = sprfMain.edit();
        //.getBoolean("main",false)；当找不到"main"所对应的键值是默认返回false
        if(sprfMain.getBoolean("main",false)){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
        setContentView(R.layout.activity_login);
        button = findViewById(R.id.button);
        change_password = findViewById(R.id.change_password);
        register = findViewById(R.id.register);
        uesr_name = findViewById(R.id.user_name_input);
        password = findViewById(R.id.password_input);
        password.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        iv_see_password = (ImageView) findViewById( R.id.ic_see_password );
        iv_see_password.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPasswordVisibility();
            }
        } );
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ChangePassword.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n = uesr_name.getText().toString();
                p = password.getText().toString();
                OkGo.<String>post("http://47.95.7.169:8080/logon")
                        .tag(this)
                        .isMultipart(true)
                        .params("UserID", n)
                        .params("password", p)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Log.i("return ", response.body());
                                Map<String, Object> map = JSON.parseObject(response.body(), new TypeReference<Map<String, Object>>() {
                                });
                                if (map.containsKey("password")) {
                                    if (map.get("password").equals(p)) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("UserID", n);
                                        editorMain.putBoolean("main",true);
                                        editorMain.commit();
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                                if (map.containsKey("status")) {
                                    if (map.get("status").equals("no"))
                                        Toast.makeText(LoginActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


    }

    private void setPasswordVisibility(){
        if (iv_see_password.isSelected()){
            iv_see_password.setSelected( false );
            //密码不可见
            password.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            iv_see_password.setImageResource( R.mipmap.ic_unopen );
        }else {
            iv_see_password.setSelected( true );
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            iv_see_password.setImageResource( R.mipmap.ic_open );
        }
    }
}
