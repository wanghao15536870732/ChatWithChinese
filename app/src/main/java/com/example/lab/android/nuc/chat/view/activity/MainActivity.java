package com.example.lab.android.nuc.chat.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.example.lab.android.nuc.chat.Base.contacts.UserInfo;
import com.example.lab.android.nuc.chat.utils.views.ActionSheetDialog;
import com.example.lab.android.nuc.chat.utils.views.CustomTabView;
import com.example.lab.android.nuc.chat.view.fragment.ChatFragment;
import com.example.lab.android.nuc.chat.view.fragment.CommunityFragment;
import com.example.lab.android.nuc.chat.view.fragment.PracticeFragment;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.Translation.Glossary.MyWordRecycleViewActivity;
import com.google.zxing.WriterException;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.common.BitmapUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.ShareConstant;
import com.xyzlf.share.library.util.ShareUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomTabView.OnTabCheckListener, NavigationView.OnNavigationItemSelectedListener {

    private AlertDialog.Builder alertDialog;
    private boolean hasGotToken = false;
    private NavigationView mNavigationView;
    public static File NEWFILE;
    private ImageView change_head_image;
    private Uri imageuri, imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int FACE_TEST = 3;
    private CustomTabView mCustomTabView;
    private List<Fragment> mFragments = new ArrayList<>();
    private TextView ni_cheng, mother_language, learn_language, account, email;
    private final static int REQ_CODE = 1028;
    private ShareEntity testBean;
    private ImageView imageView;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Intent intent = getIntent();
        userId = intent.getStringExtra( "UserID" );
        mFragments.add( new PracticeFragment() );
        mFragments.add( new ChatFragment() );
        mFragments.add( new CommunityFragment() );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout_main );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
        initView();
        http();
        alertDialog = new AlertDialog.Builder(this);
        //文字提取初始化
        initAccessToken();
    }

    private void http() {
        OkGo.<String>post( "http://47.95.7.169:8080/getUserInfo" )
                .tag( this )
                .isMultipart( true )
                .params( "UserID",userId)
                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        UserInfo userInfo = JSON.parseObject( response.body(), UserInfo.class );
                        Log.e( "response_info",response.body() );
                        ni_cheng.setText( userInfo.getName() );
                        mother_language.setText( userInfo.getNativeLanguage() );
                        learn_language.setText( userInfo.getStudyLanguage());
                        account.setText( userInfo.getUserID());
                        email.setText( userInfo.getEamil() );
                    }
                } );
    }

    private void initView() {
        mNavigationView = (NavigationView) findViewById( R.id.nav_view );
        View headerView = mNavigationView.inflateHeaderView( R.layout.nav_header_main );
        change_head_image = headerView.findViewById( R.id.imageView );
        ni_cheng = (TextView) headerView.findViewById( R.id.new_nicheng );
        mother_language = (TextView) headerView.findViewById( R.id.new_mother_language );
        learn_language = (TextView) headerView.findViewById( R.id.new_learn_language );
        account = (TextView) headerView.findViewById( R.id.new_Info );
        email = (TextView) headerView.findViewById( R.id.new_email );

        change_head_image.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ActionSheetDialog( MainActivity.this )
                        .builder()
                        .setCancelable( false )
                        .setCanceledOnTouchOutside( false )
                        .addSheetItem( "拍照", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        File outputImage = new File( getExternalCacheDir(), "output_iamge.jpg" );
                                        try {
                                            if (outputImage.exists()) {
                                                outputImage.delete();
                                            }
                                            outputImage.createNewFile();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        if (Build.VERSION.SDK_INT >= 24) {
                                            imageuri = FileProvider.getUriForFile( MainActivity.this,
                                                    "com.example.cameraalbumtest.fileprovider", outputImage );
                                        } else {
                                            imageuri = Uri.fromFile( outputImage );
                                        }

                                        Intent intent = new Intent( "android.media.action.IMAGE_CAPTURE" );
                                        intent.putExtra( MediaStore.EXTRA_OUTPUT, imageuri );
                                        startActivityForResult( intent, TAKE_PHOTO );
                                    }
                                } )
                        .addSheetItem( "相册", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        if (ContextCompat.checkSelfPermission( MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) !=
                                                PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions( MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1 );
                                        } else {
                                            openAlbum();
                                        }
                                    }
                                } )
                        .addSheetItem( "人脸识别拍照", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Intent intent = new Intent( MainActivity.this, FaceDetectorActivity.class );
                                        startActivityForResult( intent, FACE_TEST );
                                    }
                                } ).show();
            }
        } );
        mCustomTabView = findViewById( R.id.custom_tab_container );
        CustomTabView.Tab tabHome = new CustomTabView.Tab().setText( "Practice" )
                .setColor( getResources().getColor( android.R.color.darker_gray ) )
                .setCheckedColor( getResources().getColor( android.R.color.black ) )
                .setNormalIcon( R.drawable.ic_practice )
                .setPressedIcon( R.drawable.ic_practice );
        mCustomTabView.addTab( tabHome );
        CustomTabView.Tab tabDis = new CustomTabView.Tab().setText( "Chat" )
                .setColor( getResources().getColor( android.R.color.darker_gray ) )
                .setCheckedColor( getResources().getColor( android.R.color.black ) )
                .setNormalIcon( R.drawable.ic_chat )
                .setPressedIcon( R.drawable.ic_chat );
        mCustomTabView.addTab( tabDis );
        CustomTabView.Tab tabAttention = new CustomTabView.Tab().setText( "Community" )
                .setColor( getResources().getColor( android.R.color.darker_gray ) )
                .setCheckedColor( getResources().getColor( android.R.color.black ) )
                .setNormalIcon( R.drawable.ic_community )
                .setPressedIcon( R.drawable.ic_community );
        mCustomTabView.addTab( tabAttention );

        mCustomTabView.setOnTabCheckListener( this );
        mCustomTabView.setCurrentItem( 0 );
    }

    @Override
    public void onTabSelected(View v, int position) {
        onTabItemSelected( position );
    }

    private void onTabItemSelected(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mFragments.get( 0 );
                break;
            case 1:
                fragment = mFragments.get( 1 );
                break;
            case 2:
                fragment = mFragments.get( 2 );
                break;
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace( R.id.home_container, fragment ).show( fragment ).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.toolbar, menu );
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_Info:
                Intent intent = new Intent( MainActivity.this, InformationActivity.class );
                intent.putExtra( "USERID",userId );
                startActivity( intent );
                break;
            case R.id.nav_word:
                startActivity( new Intent( MainActivity.this, MyWordRecycleViewActivity.class ) );
                break;
            case R.id.nav_help:
                startActivity( new Intent( MainActivity.this, HelpActivity.class ) );
                break;
            case R.id.nav_setting:
                startActivity( new Intent( MainActivity.this, SettingActivity.class ) );
                break;
            case R.id.nav_about:
                startActivity( new Intent( MainActivity.this, AboutActivity.class ) );
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout_main );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    private void openAlbum() {
        Intent intent = new Intent( "android.intent.action.GET_CONTENT" );
        intent.setType( "image/*" );
        startActivityForResult( intent, CHOOSE_PHOTO );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText( this, "you denied the permission", Toast.LENGTH_SHORT );
                }
                break;
            default:
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageuri ) );
                        change_head_image.setImageBitmap( bitmap );
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat( data );
                    } else {
                        handleImageBeforeKitKat( data );
                    }
                }
                break;
            case FACE_TEST:
                if (requestCode == RESULT_OK) {

                }
                break;
            case ShareConstant.REQUEST_CODE:
                /**
                 * 分享回调处理
                 */
                if (requestCode == ShareConstant.REQUEST_CODE) {
                    if (data != null) {
                        int channel = data.getIntExtra(ShareConstant.EXTRA_SHARE_CHANNEL, -1);
                        int status = data.getIntExtra(ShareConstant.EXTRA_SHARE_STATUS, -1);
                        onShareCallback(channel, status);
                    }
                }
                break;
            case REQ_CODE:
                if (CaptureActivity.SCAN_QRCODE_RESULT != "qrcode_result"){
                    String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
                    Toast.makeText( this, result, Toast.LENGTH_LONG ).show();
                }
                if (CaptureActivity.SCAN_QRCODE_BITMAP != "qrcode_bitmap") {
                    Bitmap bitmap = data.getParcelableExtra( CaptureActivity.SCAN_QRCODE_BITMAP );
                }
                break;

            default:
                break;

        }
    }

    /**
     * 分享回调处理
     * @param channel
     * @param status
     */
    private void onShareCallback(int channel, int status) {
        new ShareCallBack().onShareCallback(channel, status);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagepath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri( this, uri )) {
            String docId = DocumentsContract.getDocumentId( uri );
            if ("com.android.providers.media.documents".equals( uri.getAuthority() )) {
                String id = docId.split( ":" )[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagepath = getImagePath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection );
            } else if ("com.android.providers.downloads.documents".equals( uri.getAuthority() )) {
                Uri contenturi = ContentUris.withAppendedId( Uri.parse( "content://downloads/public_downloads" ), Long.valueOf( docId ) );
                imagepath = getImagePath( contenturi, null );
            }
        } else if ("content".equalsIgnoreCase( uri.getScheme() )) {
            imagepath = getImagePath( uri, null );
        } else if ("file".equalsIgnoreCase( uri.getScheme() )) {
            imagepath = uri.getPath();
        }
        disPlayImage( imagepath );
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagepath = getImagePath( uri, null );
        disPlayImage( imagepath );
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query( uri, null, selection, null, null );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString( cursor.getColumnIndex( MediaStore.Images.Media.DATA ) );
            }
            cursor.close();

        }
        return path;
    }

    private void disPlayImage(String imagepath) {
        if (imagepath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile( imagepath );
            change_head_image.setImageBitmap( bitmap );
        } else {
            Toast.makeText( this, "failed to get image", Toast.LENGTH_SHORT );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NEWFILE != null) {
            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile( MainActivity.this, "com.example.cameraalbumtest.fileprovider", NEWFILE );
            } else {
                imageUri = Uri.fromFile( NEWFILE );
            }
            try {
                Bitmap bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageUri ) );
                change_head_image.setImageBitmap( bitmap );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_app:
                showShareDialog();
                break;
            case R.id.my_QR_code:
                Bitmap bitmap = null;
                try{
                    String Info = "账号：" + account.getText() + "邮箱"+ email.getText() +"昵称：" + ni_cheng.getText() + "母语：" + mother_language.getText() +
                            "最近在学：" + learn_language.getText() ;
                    bitmap = BitmapUtils.create2DCode(Info);
                    View view = LayoutInflater.from( this ).inflate( R.layout.dialog_photo,null);
                    imageView = (ImageView) view.findViewById( R.id.crime_photo );
                    imageView.setImageBitmap( bitmap );
                    new AlertDialog.Builder( this )
                            .setView( view )
                            .setTitle( "扫一扫下面的二维图案,加我语伴" )
                            .setIcon( R.drawable.logo )
                            .setPositiveButton( android.R.string.ok,null)
                            .show();
                }catch (WriterException e){
                    e.printStackTrace();
                }
                break;
            case R.id.scan:
                Intent intent = new Intent( MainActivity.this, CaptureActivity.class );
                startActivityForResult(intent,REQ_CODE);
                break;
            default:
                break;
        }
        return true;
    }

    //分享的dialog
    private void showShareDialog() {
        ShareEntity testBean = new ShareEntity( "一路语伴", "发挥语伴独特优势，共创\"一带一路\"美好未来。下载 @一路语伴 APP，开启你的国际交流( 分享自 @一路语伴 ) 戳我下载>>> " );
        testBean.setUrl( "https://www.baidu.com" );
        testBean.setImgUrl( "http://p8nssbtwi.bkt.clouddn.com/logo.jpg" );
        ShareUtil.showShareDialog( MainActivity.this, testBean, ShareConstant.REQUEST_CODE );
    }

    public void shareBigimg(){
        ShareEntity testBean = new ShareEntity( "","");
        testBean.setShareBigImg(true);
    }

    /**
     * 以license文件方式初始化
     */
    private void initAccessToken() {
        OCR.getInstance(this).initAccessToken( new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("licence方式获取token失败", error.getMessage());
            }
        }, getApplicationContext());
    }

    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }
}
