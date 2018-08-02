package com.example.lab.android.nuc.chat.Translation.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lab.android.nuc.chat.R;

import com.example.lab.android.nuc.chat.Translation.Glossary.LinearAdapter;
import com.example.lab.android.nuc.chat.Translation.Glossary.MyHistory;
import com.example.lab.android.nuc.chat.Translation.Glossary.MyOpenHelper;
import com.example.lab.android.nuc.chat.Translation.Glossary.MyWordRecycleViewActivity;
import com.example.lab.android.nuc.chat.Translation.ToastUtil;
import com.example.lab.android.nuc.chat.Translation.getContent;
import com.example.lab.android.nuc.chat.Translation.getContentYouDao;
import com.example.lab.android.nuc.chat.Communication.ui.ServiceChatActivity;
import com.example.lab.android.nuc.chat.Communication.utils.KeyBoardUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main_Translation_Activity extends AppCompatActivity {


    public static final String TRANSLATION_TEXT = "translation_detail";

    private Button mBtnTranslation, mBtnOriginal,mBtnTs,mBtnSpeech;
    private ImageButton mIbTran;
    private EditText mEtInput;
    private TextView mTvResult;
    private RecyclerView mRvMain;
    private int TsId=1,OgId=0;
    private MyOpenHelper myOpenHelper;
    private MyHistory myHistory;
    getContent content ;
    getContentYouDao ContentYouDao;
    String json4;

    private Context mContext;
    public void setJson4(String json4) {
        this.json4 = json4;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url="";

    final String[] array = new String[]{"自动","中文","英文","文言文",
            "日语","韩语","法语","俄语","泰语","阿拉伯语","越南语","希腊语"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main_translation );
        setBauduAppID();


//        Bundle bundle = new Bundle(  );
//        String translation_detail = bundle.getString(TRANSLATION_TEXT ).toString();
//        mEtInput.setText( translation_detail );


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //解决android.os.NetworkOnMainThreadException
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        mBtnSpeech =findViewById( R.id.btn_speech );
        mTvResult = findViewById(R.id.tv_result);
        mEtInput = findViewById(R.id.et_input);
        mBtnOriginal = findViewById(R.id.btn_original);
        mBtnTs = findViewById(R.id.btn_ts);
        mIbTran = findViewById(R.id.iv_tran);
        mTvResult.setVisibility( View.GONE);//翻译结果框默认为隐藏
        content=new getContent();
        myOpenHelper = new MyOpenHelper(getApplicationContext());
        myHistory=new MyHistory(getApplicationContext());

        //加载翻译过的历史
        loadHistory();


        //将聊天界面输入框的text传入到翻译的翻译框里
        Intent intent = getIntent();
        String translation_detail = intent.getStringExtra( TRANSLATION_TEXT ).toString();
        mEtInput.setText( translation_detail );

        /****
         * 设置源语言
         */
        mBtnOriginal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] strs = readerVlaue();
                if (strs == null) {
                    ToastUtil.showToast(Main_Translation_Activity.this,"请先设置appid和密钥");
                    return;
                } else if (strs[2].equals("baidu")) {
                    OriginalLanguage();
                }else if (strs[2].equals("youdao")){
                    return;
                }


            }
        });

        /***
         * 设置目标语言
         */
        mBtnTs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] strs = readerVlaue();
                if (strs == null) {
                    ToastUtil.showToast(Main_Translation_Activity.this,"请先设置appid和密钥");
                    return;
                } else if (strs[2].equals("baidu")) {
                    TranLanguage();
                }else if (strs[2].equals("youdao")){
                    return;
                }

            }
        });




        //翻译按钮
        mBtnTranslation = findViewById(R.id.btn_translation);
        mBtnTranslation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String[] strs = readerVlaue();
                if (strs == null) {
                    ToastUtil.showToast(Main_Translation_Activity.this,"请先设置appid和密钥");
                    return;
                } else if (strs[2].equals("baidu")) {
                   baiduTran(view,null);
                   return;
                }else if (strs[2].equals("youdao")){
                    mBtnOriginal.setText("英语");
                    mBtnTs.setText("中文");
                    YouDao(view,null);
                    return;
                }
            }
        });

        //跳转到科大讯飞的语音合成上
        mBtnSpeech.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Translation_Activity.this,Main_Voice_Activity.class );
                intent.putExtra( Main_Voice_Activity.TEXT,mTvResult.getText().toString() );
                Main_Translation_Activity.this.startActivity( intent);
            }
        } );


        /***
         * 交换源语言和目标语言
         */
        mIbTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] strs = readerVlaue();
                if (strs == null) {
                    ToastUtil.showToast(Main_Translation_Activity.this,"请先设置appid和密钥");
                    return;
                }else if (strs[2].equals("youdao")){
                    return;
                }

                int i=OgId;
                int j=TsId;
                int temp=i;
                int temp1=j;

                mBtnOriginal.setText(array[j].toString());
                mBtnTs.setText(array[i].toString());

                switch (j){
                    case 0:
                        content.setFrom("auto");
                        break;

                    case 1:
                        content.setFrom("zh");
                        break;

                    case 2:
                        content.setFrom("en");
                        break;

                    case 3:
                        content.setFrom("wyw");
                        break;

                    case 4:
                        content.setFrom("jp");
                        break;
                    case 5:
                        content.setFrom( "kor" );
                        break;
                    case 6:
                        content.setFrom( "fra" );
                        break;
                        //"俄语"
                    case 7:
                        content.setFrom( "ru" );
                        break;

                        //"泰语"
                    case 8:
                        content.setFrom( "th" );
                        break;

                        //"阿拉伯语"
                    case 9:
                        content.setFrom( "ara" );
                        break;

                        //"越南语"
                    case 10:
                        content.setFrom( "vie" );
                        break;

                        //"希腊语"
                    case 11:
                        content.setFrom( "el" );
                        break;
                }

                switch (i) {
                    case 0:
                        content.setTo("auto");
                        break;

                    case 1:
                        content.setTo("zh");
                        break;

                    case 2:
                        content.setTo("en");
                        break;

                    case 3:
                        content.setTo("wyw");
                        break;

                    case 4:
                        content.setTo("jp");
                        break;
                    case 5:
                        content.setTo( "kor" );
                        break;
                    case 6:
                        content.setTo( "fra" );
                        break;
                    //"俄语"
                    case 7:
                        content.setTo( "ru" );
                        break;

                    //"泰语"
                    case 8:
                        content.setTo( "th" );
                        break;

                    //"阿拉伯语"
                    case 9:
                        content.setTo( "ara" );
                        break;

                    //"越南语"
                    case 10:
                        content.setTo( "vie" );
                        break;

                    //"希腊语"
                    case 11:
                        content.setTo( "el" );
                        break;
                }
                setTsId(i);
                setOgId(j);
            }
        });


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        KeyBoardUtils.hideKeyBoard( this,mEtInput );
    }





    //向上一级返回数据
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen( GravityCompat.START)) {
            drawer.closeDrawer( GravityCompat.START);
            Intent intent = new Intent();
            String translation_result = mTvResult.getText().toString();
            intent.putExtra( ServiceChatActivity.TRANSLATION_RESULT,translation_result );
            setResult( RESULT_OK,intent );
        } else {
            Intent intent = new Intent();
            String translation_result = mTvResult.getText().toString();
            intent.putExtra( ServiceChatActivity.TRANSLATION_RESULT,translation_result);
            setResult( RESULT_OK,intent );
        }
        super.onBackPressed();
        finish();
    }




    /***
     * 读取appid 和密钥的信息
     * @return appid和密钥的数组
     */
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




    public void setTsId(int tsId) {
        this.TsId = tsId;
    }
    public void setOgId(int ogId) {
        this.OgId = ogId;
    }


    /***
     * 发音
     * @param json
     * @return
     */
    public String Voice(String json) {//发音
        try {
            JSONObject obj=new JSONObject(json);
            String voice=obj.getString("speakUrl");
            return voice;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    /***
     * 翻译结果
     * @param json
     * @return
     */
    public String TranResult(String json) {//翻译结果
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = obj.getJSONArray("translation");
            String result="";
            for (int i=0;i<array.length();i++){
                String tran=array.getString(i);
                result="\n"+tran;
            }

            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 返回的错误码
     * @param json
     * @return
     */
    public String ErrorCode(String json) {
        try {
            JSONObject obj = new JSONObject(json);

            String errorcode = obj.getString("errorCode");

            return errorcode;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 词性
     * @param json
     * @return
     */
    public String Explain(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject obj1 = obj.getJSONObject("basic");
            JSONArray array = obj1.getJSONArray("explains");
            String ttt="";
            for(int i = 0;i < array.length();i ++){
                ttt = array.getString(i)+"\n"+ttt;
            }

            return ttt;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 词语
     * @param json
     * @return
     */
    public String Terms(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = obj.getJSONArray("web");
            String lll="";
            for (int i = 0;i < array.length();i ++){
                obj = array.getJSONObject(i);
                String word = obj.getString("value");
                String key = obj.getString("key");
                lll = key+" : "+word+"\n"+lll;
            }
            return lll;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 要查询的词
     * @param json
     * @return
     */
    public String Query(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            String query = obj.getString("query");
            return query;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void YouDao(View view, String info){

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        ContentYouDao=new getContentYouDao();
        int temp=0;
        String[] strs = readerVlaue();
        if (strs == null) {
            ToastUtil.showToast(Main_Translation_Activity.this,"请先设置appid和密钥");
            return;
        }
        ContentYouDao.setValue(strs[0], strs[1]);


        if(info==null){
            info = mEtInput.getText().toString();
            if(TextUtils.isEmpty(mEtInput.getText())) {
                ToastUtil.showToast(Main_Translation_Activity.this, "请输入要翻译的内容");
                return;
            }
        }else{
            info=info;
            temp=1;
        }


        //过滤换行符
        Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
        Matcher m = CRLF.matcher(info);
        if (m.find()) {
            info = m.replaceAll("");
        }
        ContentYouDao.setQuery(info);
        String reresult = null;
        try {
            reresult = ContentYouDao.requestUrl();
            Log.d("请求的完整url：", reresult);
            String json4 = ContentYouDao.Result();
            setJson4(json4);
            Log.d("返回的json：", json4);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String query = Query(json4);
        String tranresult = TranResult(json4);
        String explain = Explain(json4);
        String terms = Terms(json4);
        String voice = Voice(json4);
        String errorcode = ErrorCode(json4);
        setUrl(voice);
        String result="";


        if(errorcode.equals("0")){
            if(explain == null || terms == null){
                result=query + "    " + "\n" + tranresult;
            }else{
                result=query + "    " + "\n"+tranresult + "\n" + "\n\n"  + explain+"\n" + terms;
            }
        }else {
            ToastUtil.showToast(Main_Translation_Activity.this,"errorcode:"+errorcode);
            return;
        }


        SpannableStringBuilder myWord = new SpannableStringBuilder();

        myWord.append(result);
        Drawable drawable = getResources().getDrawable(R.mipmap.play);
        drawable.setBounds(0,0,50,50);
        ImageSpan imageSpan=new ImageSpan(drawable);
        myWord.setSpan(imageSpan,info.length()+2,info.length()+4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        myWord.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);//设置下划线
            }

            @Override
            public void onClick(View view) {
                MediaPlayer mp = new MediaPlayer();
                mp = MediaPlayer.create(Main_Translation_Activity.this,
                        Uri.parse(url));
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
            }
        },info.length()+2,4+info.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


                mTvResult.setText(myWord);
                mTvResult.setMovementMethod( LinkMovementMethod.getInstance());//开始响应点击事件
        /****
         * 判断是否输入为空    如果没有输入则关闭翻译结果框且弹出警告  如果输入不为空且有返回值  则显示
         */
         if(TextUtils.isEmpty(mEtInput.getText())){
             mTvResult.setVisibility( View.GONE);
             ToastUtil.showToast(Main_Translation_Activity.this,"请输入要翻译的内容");
         }else {
              if (mTvResult.getText().toString() != null){
                 mTvResult.setVisibility( View.VISIBLE);
                  String word = mEtInput.getText().toString();
                  String wordtoresult = mTvResult.getText().toString();
                  String s = tranresult.replace("\n","");
                  if (temp == 0){
                      click2(view, word, s);
                      loadHistory();
                  }
               }
         }



    }

    public void OriginalLanguage(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(Main_Translation_Activity.this);
        builder.setTitle("选择语言").setSingleChoiceItems(array, OgId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToastUtil.showToast(Main_Translation_Activity.this,"已选择"+array[i]);
                Log.d("选择的语言",array[i]);

                setOgId(i);
                mBtnOriginal.setText(array[i].toString());

                switch (array[i]){
                    case "自动":
                        content.setFrom("auto");
                        break;

                    case "中文":
                        content.setFrom("zh");
                        break;

                    case "英文":
                        content.setFrom("en");
                        break;

                    case "文言文":
                        content.setFrom("wyw");
                        break;

                    case "日语":
                        content.setFrom("jp");
                        break;

                    case "韩语":
                        content.setFrom( "kor" );
                        break;

                    case "法语":
                        content.setFrom( "fra" );
                        break;

                    case "俄语":
                        content.setFrom( "ru" );
                        break;

                    case "泰语":
                        content.setFrom( "th" );
                        break;

                    case "阿拉伯语":
                        content.setFrom( "ara" );
                        break;

                    case "越南语":
                        content.setFrom( "vie" );
                        break;

                    case "希腊语":
                        content.setFrom( "el" );
                        break;
                }
                dialogInterface.dismiss();
            }
        }).show();
    }
    public void TranLanguage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Main_Translation_Activity.this);
        builder.setTitle("请选择目标语言").setSingleChoiceItems(array, TsId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToastUtil.showToast(Main_Translation_Activity.this,"已选择"+array[i]);
                Log.d("选择的目标语言",array[i]);

                setTsId(i);
                mBtnTs.setText(array[i].toString());
                switch (array[i]) {
                    case "自动":
                        content.setTo("auto");
                        break;

                    case "中文":
                        content.setTo("zh");
                        break;

                    case "英文":
                        content.setTo("en");
                        break;

                    case "文言文":
                        content.setTo("wyw");
                        break;

                    case "日语":
                        content.setTo("jp");
                        break;

                    case "韩语":
                        content.setTo( "kor" );
                        break;

                    case "法语":
                        content.setTo( "fra" );
                        break;

                    case "俄语":
                        content.setTo( "ru" );
                        break;

                    case "泰语":
                        content.setTo( "th" );
                        break;


                    case "阿拉伯语":
                        content.setTo( "ara" );
                        break;

                    case "越南语":
                        content.setTo( "vie" );
                        break;

                    case "希腊语":
                        content.setTo( "el" );
                        break;
                }
                dialogInterface.dismiss();
            }
        }).show();
    }

    public void baiduTran(View view, String info){

        String[] strs = readerVlaue();
        int temp = 0;
        if (strs == null) {
            ToastUtil.showToast(Main_Translation_Activity.this,"请先设置appid和密钥");
            return;
        }

        if(info == null){
            info = mEtInput.getText().toString();
            if(TextUtils.isEmpty(mEtInput.getText())) {
                ToastUtil.showToast(Main_Translation_Activity.this, "请输入要翻译的内容");
                return;
            }
        }else{
            info=info;
            temp=1;
        }

        content.setValue(strs[0], strs[1]);
        content.setQuery(info);
        try {
            String result = content.requestUrl();
            Log.d("请求的完整url：", result);
            String result2 = content.Result();
            String TranResult = content.JsonToString(result2);
            mTvResult.setText(TranResult);


            /****
             * 判断是否输入为空    如果没有输入则关闭翻译结果框且弹出警告
             * 如果输入不为空且有返回值  则显示
             */
            if(TextUtils.isEmpty(mEtInput.getText())){
                mTvResult.setVisibility( View.GONE);
                ToastUtil.showToast(Main_Translation_Activity.this,"请输入要翻译的内容");
            }else {
                if (mTvResult.getText().toString()!=null){
                    mTvResult.setVisibility( View.VISIBLE);
                    String word = mEtInput.getText().toString();
                    String wordtoresult = mTvResult.getText().toString();
                    if (temp == 0){
                        click2(view, word, wordtoresult);
                        loadHistory();
                    }
                }
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public void click1(View v, String word, String result) {
        String insert = "insert into info(word,result) values(?,?)";
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        db.execSQL(insert, new Object[]{word, result});
        db.close();
    }
    public void click2(View v, String word, String result) {
        String insert = "insert into info(word,result) values(?,?)";
        SQLiteDatabase db = myHistory.getWritableDatabase();
        db.execSQL(insert, new Object[]{word, result});
        db.close();
    }



    public ArrayList<String> inputtohistory(){
        ArrayList<String> list=new ArrayList<String>();
        String[] arr;
        SQLiteDatabase db=myHistory.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from info",null);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                String word=cursor.getString(1);
                String result=cursor.getString(2);
                Log.d("word:",word+"result："+result);
                list.add(word+"\n"+result);
            }
        }
        cursor.close();
        db.close();
        Collections.reverse(list);
        return list;
    }

    public void loadHistory(){
        final ArrayList<String> list=inputtohistory();
        Log.d("获取的数组结果：",list.toString());
        mRvMain=findViewById(R.id.rv_main);
        final LinearAdapter linearAdapter=new LinearAdapter(Main_Translation_Activity.this,list);
        mRvMain.setItemAnimator(new DefaultItemAnimator());
        mRvMain.setLayoutManager(new LinearLayoutManager(Main_Translation_Activity.this));
        mRvMain.setAdapter(linearAdapter);
        //      调用按钮返回事件回调的方法
        linearAdapter.buttonSetOnclick(new LinearAdapter.ButtonInterface() {
            @Override
            public void onclick(View view, int position) {
                ToastUtil.showToast(Main_Translation_Activity.this,"已收藏");
                String[] s=list.get(position).split("\n");
                click1(view,s[0],s[1]);
            }
        });
        //      调用文本返回事件回调的方法
        linearAdapter.textviewonSetOnclick(new LinearAdapter.TextViewInterface() {
            @Override
            public void onclick(View view, int position , ArrayList<String> list) {
                String[] strs = readerVlaue();
                if (strs == null) {
                    ToastUtil.showToast( Main_Translation_Activity.this, "请先设置appid和密钥" );
                    return;
                } else if (strs[2].equals( "baidu" )) {
                    String[] reinfo = list.get( position ).split( "\n" );
                    mEtInput.setText( reinfo[0] );
                    baiduTran( view, reinfo[0] );
                    return;
                } else if (strs[2].equals( "youdao" )) {
                    mBtnOriginal.setText( "英语" );
                    mBtnTs.setText( "中文" );
                    String[] reinfo = list.get( position ).split( "\n" );
                    Log.d( "测试重新输入：", reinfo[0] );
                    mEtInput.setText( reinfo[0] );
                    YouDao( view, reinfo[0] );

                    return;
                }
            }

            @Override
            public void longclick(View view, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Main_Translation_Activity.this);
                builder.setTitle("删除？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showToast(Main_Translation_Activity.this,"已删除");
                        linearAdapter.removeItem(position);
                        linearAdapter.notifyDataSetChanged();

                    }
                }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showToast(Main_Translation_Activity.this,"已取消");

                    }
                }).show();

            }
        });
    }

    private void setBauduAppID(){
        String choose = "baidu";
        String appid = "20180601000170165";
        String pw = "hV3H2qtuOhw8ShIy0wzJ";
        File dir = getApplicationContext().getFilesDir();//查找这个应用下的所有文件所在的目录
        Log.d("文件夹：" , dir.getAbsolutePath());
        FileWriter writer;
        try {
            writer = new FileWriter(dir.getAbsolutePath() + "/userinfo.txt");
            writer.append(appid+","+pw+","+ choose);
            writer.close();
            ToastUtil.showToast(Main_Translation_Activity.this,"设置成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

