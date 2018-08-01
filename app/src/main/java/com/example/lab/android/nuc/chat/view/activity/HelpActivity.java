package com.example.lab.android.nuc.chat.view.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.lab.android.nuc.chat.Communication.App;
import com.example.lab.android.nuc.chat.R;

public class HelpActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private WebView WvHelp;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_help);
        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "帮助" );
        }
        mProgressBar = (ProgressBar) findViewById( R.id.help_progress_bar );
        mProgressBar.setMax( 100 );
        WvHelp=findViewById(R.id.wv_help);
        WvHelp.loadUrl("file:///android_asset/YiLuYuBan.html");
        WvHelp.getSettings().setJavaScriptEnabled( true );
        WvHelp.setWebChromeClient( new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    mProgressBar.setVisibility( View.GONE );
                }else {
                    mProgressBar.setVisibility( View.VISIBLE );
                    mProgressBar.setProgress( newProgress );
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                getSupportActionBar().setSubtitle( title );
            }
        } );
        WvHelp.setWebViewClient( new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
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

    @Override
    public void onBackPressed() {
        //canGoBack判断是否有历史信息
        if (WvHelp.canGoBack()){
            //如果有的话，就利用goBack回到前一个历史网页
            WvHelp.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
