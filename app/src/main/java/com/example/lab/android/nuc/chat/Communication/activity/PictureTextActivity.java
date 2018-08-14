package com.example.lab.android.nuc.chat.Communication.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab.android.nuc.chat.Communication.utils.Constants;
import com.example.lab.android.nuc.chat.Communication.utils.PhotoUtils;
import com.example.lab.android.nuc.chat.R;

import java.io.File;

public class PictureTextActivity extends AppCompatActivity {


    private ImageView mImageView;

    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_picture_text );

        mImageView = (ImageView) findViewById( R.id.image_view );
        mTextView = (TextView) findViewById( R.id.text_view );
        mTextView.setSelected( true );
        mTextView.setTextIsSelectable( true );

        Intent intent = getIntent();
        String path = intent.getStringExtra( "path" );
        String text = intent.getStringExtra( "text" );
        String url = intent.getStringExtra( Constants.IMAGE_URL);
        PhotoUtils.displayImageCacheElseNetwork(mImageView, path, url);
        mTextView.setText( text );
        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "文字识别" );
        }
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
}
