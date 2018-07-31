package com.example.lab.android.nuc.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.chat.Base.Contacts.Contact;
import com.example.lab.android.nuc.chat.Communication.AlertDialog;
import com.example.lab.android.nuc.chat.Communication.bean.ChatConst;
import com.example.lab.android.nuc.chat.Communication.widget.PlayButton;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.Communication.ui.ServiceChatActivity;
import com.makeramen.roundedimageview.RoundedImageView;


public class ChatActivity extends Activity {
    private String teacherID;
    private String name;
    private String picurl;
    private String score;
    private String language;
    private String introduceLink;
    private String experience;
    private String plain;
    private String type;
    private TextView tvName, tvScore, tvTitle, tvPlan, tvExperience;
    private ImageView ivCountry;
    private RoundedImageView roundedImageView;
    private Button btVideo, btMessage,btn_introduce, btn_ourse;
    private ImageButton ibFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        teacherID = intent.getStringExtra("teacherID");
        name = intent.getStringExtra("name");
        picurl = intent.getStringExtra("picurl");
        score = intent.getStringExtra("score");
        language = intent.getStringExtra("language");
        introduceLink = intent.getStringExtra("introduceLink");
        experience = intent.getStringExtra("experience");
        plain = intent.getStringExtra("plan");
        type = intent.getStringExtra("type");
        initView();
        setView();
    }

    private void setView() {
        tvName.setText(name);
        tvScore.setText(score);
        tvTitle.setText(type);
        Log.e( "Plain_1", plain );
        plain.replace( "\n","\n");
        Log.e( "Plain",plain );
        tvPlan.setText(plain);
        tvExperience.setText(experience);
        Glide.with(this).load(picurl).into(roundedImageView);
        switch (language) {
            case "汉语":
                ivCountry.setImageDrawable(getDrawable(R.drawable.country_cn));
                break;
            case "ch":
                ivCountry.setImageDrawable(getDrawable(R.drawable.country_ch));
                break;
            case "德语":
                ivCountry.setImageDrawable(getDrawable(R.drawable.country_de));
                break;
            case "ea":
                ivCountry.setImageDrawable(getDrawable(R.drawable.country_ea));
                break;
            case "英语":
                ivCountry.setImageDrawable(getDrawable(R.drawable.country_gb));
                break;
            case "fa":
                ivCountry.setImageDrawable(getDrawable(R.drawable.country_hm));
                break;
            case "lr":
                ivCountry.setImageDrawable(getDrawable(R.drawable.country_lr));
                break;
            case "法语":
                ivCountry.setImageDrawable(getDrawable(R.drawable.country_gr));
                break;
            default:
                ivCountry.setImageDrawable(getDrawable(R.drawable.country_cn));
                break;
        }

        btMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this, ServiceChatActivity.class);
                intent.putExtra(ServiceChatActivity.CONTACT_NAME, name);
                intent.putExtra( ServiceChatActivity.CONTACT_IMAGE, picurl);
                ChatConst.TAG = 1;
                startActivity(intent);
            }
        });
        btn_introduce.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog( ChatActivity.this ).builder()
                        .setMsg( "  My name is 李想 .And I am from dNo.047 OversNes eChinese Middle School of the North University of China." + "\n" +
                                "  It is really a GREat honor to have this opportunity for an interview . "  + "\n" +
                                "  I would like too answer what ever you maye raise , " + "\n" +
                                "  and I hope I can make a good performance today .Nowe let me introduce myself briefly ." )
                        .setNegativeButton( "SURE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        } ).show();
            }
        } );
        btn_ourse.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog( ChatActivity.this ).builder()
                        .setMsg( "  The use of adviser in this case is very rare, " +
                                "which more commonly refers to thesis research " +
                                "advisors/professors as in graduate programs." )
                        .setNegativeButton( "SURE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        } ).show();
            }
        } );
        ibFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        tvName = findViewById(R.id.tv_teacher_name_info);
        tvScore = findViewById(R.id.tv_score_info);
        tvTitle = findViewById(R.id.tv_teacher_title);
        tvPlan = findViewById(R.id.tv_teacher_plan);
        tvExperience = findViewById(R.id.tv_teacher_introduce);
        roundedImageView = findViewById(R.id.iv_teacher_info);
        btVideo = findViewById(R.id.bt_course);
        ivCountry = findViewById(R.id.iv_country);
        btMessage = findViewById(R.id.bt_message);
        ibFinish = findViewById(R.id.ib_return);
        btn_introduce = findViewById( R.id.bt_teacher_introduce );
        btn_ourse = findViewById( R.id.bt_course );
    }
}
