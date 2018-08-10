package com.example.lab.android.nuc.chat.Translation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.Translation.BaseEnum.DictationResult;
import com.example.lab.android.nuc.chat.Translation.utils.RecognitionManager;
import com.example.lab.android.nuc.chat.Translation.utils.SynthesisManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;
import java.util.List;


public class Main_Voice_Activity extends AppCompatActivity {

    public static final String TEXT = "translation_speech";

    private Button btnRecognizer;  //不带窗口的语音识别
    private Button btnRecognizerDialog; //带窗口的语音识别
    private Button btnSynthesizer; //语音合成
    private EditText mEtInputText;  //接收用户的内容
    private final String TAG = "Main_Voice_Activity";


    List<String> permissionList = new ArrayList<>(  );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main_voice );
        initView();
        setListen();
        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "语音合成" );
        }

        //提取百度翻译的结果
        Intent intent = getIntent();
        String translation_result = intent.getStringExtra( TEXT );
        mEtInputText.setText( translation_result );
        //获取手机录音机使用权限，听写、识别、语义理解需要用到此权限
        if(ContextCompat.checkSelfPermission( Main_Voice_Activity.this, Manifest.
                permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.RECORD_AUDIO );
        }
         //读取手机信息权限
        if(ContextCompat.checkSelfPermission( Main_Voice_Activity.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.READ_PHONE_STATE );
        }
        //SD卡读写的权限（如果需要保存音频文件到本地的话）
        if(ContextCompat.checkSelfPermission( Main_Voice_Activity.this, Manifest.
                permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.READ_EXTERNAL_STORAGE );
        }
        if (! permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions( Main_Voice_Activity.this,permissions,1 );
        }else {
            setRecognitionManager();
        }



    }

    private void setRecognitionManager(){
        RecognitionManager.getSingleton().startRecognitionWithDialog(this, new RecognitionManager.onRecognitionListen() {
            @Override
            public void result(String msg) {
                SynthesisManager.getSingleton().startSpeaking(msg);
            }
            @Override
            public void error(String errorMsg) {

            }
            @Override
            public void onBeginOfSpeech() {

            }
            @Override
            public void onVolumeChanged(int volume, byte[] data) {

            }
            @Override
            public void onEndOfSpeech() {

            }
        });

    }

    private void setListen() {
        btnRecognizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecognizer();
            }
        });

        btnRecognizerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecognizerDialog();
            }
        });

        btnSynthesizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeechSynthesizer(mEtInputText.getText().toString());
            }
        });
    }

    private void initView() {
        btnRecognizer = (Button) findViewById(R.id.btn_dialog);
        btnRecognizerDialog = (Button) findViewById(R.id.btn_with_dialog);
        btnSynthesizer = (Button) findViewById(R.id.btn_synthetize);
        mEtInputText = (EditText) findViewById(R.id.input_text);
    }


    /*-------------------------------带窗口的语音识别--------------------------*/
    private void showRecognizerDialog() {
        //有动画效果
         RecognizerDialog iatDialog;
        // 初始化有交互动画的语音识别器
        iatDialog = new RecognizerDialog(Main_Voice_Activity.this, mInitListener);
        //设置监听，实现听写结果的回调
        iatDialog.setListener(new RecognizerDialogListener() {
            String resultJson = "[";//放置在外边做类的变量则报错，会造成json格式不对
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                System.out.println("-----------------   onResult   -----------------");
                if (!isLast) {
                    resultJson += recognizerResult.getResultString() + ",";
                } else {
                    resultJson += recognizerResult.getResultString() + "]";
                }

                if (isLast) {
                    //解析语音识别后返回的json格式的结果
                    Gson gson = new Gson();
                    List<DictationResult> resultList = gson.fromJson(resultJson,
                            new TypeToken<List<DictationResult>>() {
                            }.getType());
                    String result = "";
                    for (int i = 0; i < resultList.size() - 1; i++) {
                        result += resultList.get(i).toString();
                    }
                    Log.d(TAG,"识别结果"+result);
                    SynthesisManager.getSingleton().startSpeaking(result);
                }
            }
            @Override
            public void onError(SpeechError speechError) {
                //自动生成的方法存根
                speechError.getPlainDescription(true);
            }
        });
        //开始听写，需将sdk中的assets文件下的文件夹拷入项目的assets文件夹下（没有的话自己新建）
        iatDialog.show();
    }
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(Main_Voice_Activity.this, "初始化失败，错误码：" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };


    /*-------------------------------不带窗口的语音识别--------------------------*/
    public void showRecognizer(){
        //1.创建SpeechRecognizer对象，第二个参数：本地识别时传InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(this, null);
        //2.设置听写参数，详见SDK中《MSC Reference Manual》文件夹下的SpeechConstant类
        mIat.setParameter( SpeechConstant.DOMAIN,"iat");
        mIat.setParameter( SpeechConstant.LANGUAGE,"zh_cn");
        mIat.setParameter( SpeechConstant.ACCENT,"mandarin ");
        //保存音频文件到本地（有需要的话）   仅支持pcm和wav，且需要自行添加读写SD卡权限
        mIat.setParameter( SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/mIat.wav");
        // 3.开始听写
        mIat.startListening(mRecoListener);
    }
    //听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        //听写结果回调接口(返回Json格式结果，用户可参见附录13.1)；
        // 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
        // 关于解析Json的代码可参见Demo中JsonParser类；
        // isLast等于true时会话结束。
        String resultJson = "[";//放置在外边做类的变量则报错，会造成json格式不对
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, "result:" + results.getResultString());
            if (!isLast) {
                resultJson += results.getResultString() + ",";
            } else {
                resultJson += results.getResultString() + "]";
            }

            if (isLast) {
                //解析语音识别后返回的json格式的结果
                Gson gson = new Gson();
                List<DictationResult> resultList = gson.fromJson(resultJson,
                        new TypeToken<List<DictationResult>>() {
                        }.getType());
                String result = "";
                for (int i = 0; i < resultList.size() - 1; i++) {
                    result += resultList.get(i).toString();
                }
                Log.d(TAG,"识别结果"+result);
                SynthesisManager.getSingleton().startSpeaking(result);
            }
        }

        //会话发生错误回调接口
        public void onError(SpeechError error) {
            //打印错误码描述
            Log.d(TAG, "error:" + error.getPlainDescription(true));
        }
        //开始录音
        public void onBeginOfSpeech() {
        }
        //volume音量值0~30，data音频数据
        public void onVolumeChanged(int volume, byte[] data) {
        }
        //结束录音
        public void onEndOfSpeech() {
        }
        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };


    /*-------------------------------语音合成--------------------------*/
    public void SpeechSynthesizer(String text){
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(Main_Voice_Activity.this, null);

        /**
         2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
         *
         */
        // 清空参数
        mTts.setParameter( SpeechConstant.PARAMS, null);
        mTts.setParameter( SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        mTts.setParameter( SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter( SpeechConstant.SPEED, "50");//设置语速
        //设置合成音调
        mTts.setParameter( SpeechConstant.PITCH, "50");
        mTts.setParameter( SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter( SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter( SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//        boolean isSuccess = mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts2.wav");
//        Toast.makeText(MainActivity_0.this, "语音合成 保存音频到本地：\n" + isSuccess, Toast.LENGTH_LONG).show();
        //3.开始合成
        int code = mTts.startSpeaking(text, mSynListener);

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //上面的语音配置对象为初始化时：
                Toast.makeText(Main_Voice_Activity.this, "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Main_Voice_Activity.this, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
            }
        }
    }
    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {

        }
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }
        //开始播放
        public void onSpeakBegin() {
        }
        //暂停播放
        public void onSpeakPaused() {
        }
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }
        //恢复播放回调接口
        public void onSpeakResumed() {
        }
        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    for (int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText( this, "必须同意所有的权限才能使用本程序", Toast.LENGTH_SHORT ).show();
                            return;
                        }
                    }
                    setRecognitionManager();
                }else {
                    Toast.makeText( this,"发生未知错误", Toast.LENGTH_SHORT ).show();
                }
                break;
            default:
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
