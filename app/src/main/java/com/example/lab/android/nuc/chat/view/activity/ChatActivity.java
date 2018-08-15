package com.example.lab.android.nuc.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.Communication.activity.ServiceChatActivity;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;


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
    private ImageView roundedImageView;
    private Button btVideo;
    private ImageButton ibFinish, btMessage;
    private JZVideoPlayerStandard jzVideoPlayerStandard;

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
        jzVideoPlayerStandard.setUp("http://p8nssbtwi.bkt.clouddn.com/video.mp4",
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
        Glide.with(this).load(picurl).into(jzVideoPlayerStandard.thumbImageView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void setView() {
        tvName.setText(name);
        tvScore.setText(score);
        tvTitle.setText(type);
        Log.e("Plain_1", plain);
        plain.replace("\n", "\n");
        Log.e("Plain", plain);
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

                intent.putExtra( "contact_image_uri", picurl);
                startActivity(intent);
            }
        });
        btVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, VideoChatActivity.class);
                intent.putExtra(ServiceChatActivity.CONTACT_NAME, teacherID);
                ChatActivity.this.startActivity(intent);
            }
        });
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
        jzVideoPlayerStandard = findViewById(R.id.videoplayer);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
