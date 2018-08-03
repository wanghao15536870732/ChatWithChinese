package com.example.lab.android.nuc.chat.Communication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.Communication.utils.Constants;
import com.example.lab.android.nuc.chat.Communication.utils.PhotoUtils;


public class ImageBrowserActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_image_brower_layout);
        imageView = (ImageView) findViewById( R.id.imageView);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        String path = intent.getStringExtra( Constants.IMAGE_LOCAL_PATH);
        String url = intent.getStringExtra(Constants.IMAGE_URL);
        PhotoUtils.displayImageCacheElseNetwork(imageView, path, url);
        findViewById(R.id.lly_image_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
