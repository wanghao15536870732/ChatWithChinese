package com.example.lab.android.nuc.chat.Communication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.GeneralParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.WordSimple;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.Communication.utils.Constants;
import com.example.lab.android.nuc.chat.Communication.utils.PhotoUtils;
import com.example.lab.android.nuc.chat.utils.views.ActionSheetDialog;
import com.example.lab.android.nuc.chat.view.activity.FaceDetectorActivity;
import com.example.lab.android.nuc.chat.view.activity.MainActivity;

import java.io.File;
import java.io.IOException;


public class ImageBrowserActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_image_brower_layout);
        imageView = (ImageView) findViewById( R.id.imageView);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        final String path = intent.getStringExtra( Constants.IMAGE_LOCAL_PATH);
        String url = intent.getStringExtra(Constants.IMAGE_URL);
        PhotoUtils.displayImageCacheElseNetwork(imageView, path, url);
        findViewById(R.id.lly_image_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView.setOnLongClickListener( new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                new ActionSheetDialog( ImageBrowserActivity.this )
                        .builder()
                        .setCancelable( false )
                        .setCanceledOnTouchOutside( false )
                        .addSheetItem( "提取图中文字", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        recGeneral( path );
                                    }
                                } )
                        .addSheetItem( "保存到手机", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText( ImageBrowserActivity.this, "图片已保存至" + path, Toast.LENGTH_SHORT ).show();
                                    }
                                } )
                        .addSheetItem( "收藏", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText( ImageBrowserActivity.this, "已收藏", Toast.LENGTH_SHORT ).show();
                                    }
                                } ).show();
                return false;
            }
        } );
    }

    private void recGeneral(String filePath) {
        GeneralParams param = new GeneralParams();
        param.setDetectDirection(true);
        param.setImageFile(new File(filePath));
        OCR.getInstance(this).recognizeGeneral(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                StringBuilder sb = new StringBuilder();
                for (WordSimple word : result.getWordList()) {
                    sb.append(word.getWords());
                    sb.append("\n");
                }
                Toast.makeText( ImageBrowserActivity.this, sb.toString(), Toast.LENGTH_LONG ).show();
            }

            @Override
            public void onError(OCRError error) {
                Toast.makeText( ImageBrowserActivity.this, error.toString(), Toast.LENGTH_SHORT ).show();
            }
        });
    }
}
