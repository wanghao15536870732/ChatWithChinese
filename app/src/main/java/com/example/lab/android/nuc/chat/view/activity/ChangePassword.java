package com.example.lab.android.nuc.chat.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.lab.android.nuc.chat.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    EditText password1, password2, uesrname;
    Button change;
    String u, p1, p2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        uesrname = findViewById(R.id.change_password_user);
        password1 = findViewById(R.id.change_password_p1);
        password2 = findViewById(R.id.change_password_p2);
        change = findViewById(R.id.change_password_button);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u = uesrname.getText().toString();
                p1 = password1.getText().toString();
                p2 = password2.getText().toString();
                OkGo.<String>post("http://47.95.7.169:8080/cpassword")
                        .tag(this)
                        .params("password", p1)
                        .params("UserID", u)
                        .params("newPassword", p2)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Map<String, Object> map = JSON.parseObject(response.body(), new TypeReference<Map<String, Object>>() {
                                });
                                if (map.get("status").equals("ok")) {
                                    Toast.makeText(ChangePassword.this, "修改成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ChangePassword.this, "修改失败，请从试", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
