package com.example.lab.android.nuc.chat.Practice.Test;

import android.Manifest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab.android.nuc.chat.Practice.Content.Result.Result;
import com.example.lab.android.nuc.chat.Practice.Content.Xml.XmlResultParser;
import com.example.lab.android.nuc.chat.Practice.Data.DataSave;
import com.example.lab.android.nuc.chat.Practice.UI.Adapter.RecyclerViewAdapter_Zh;
import com.example.lab.android.nuc.chat.Practice.UI.PagingScrollHelper;
import com.example.lab.android.nuc.chat.R;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.sunflower.FlowerCollector;

import java.util.ArrayList;

import java.util.List;


public class Ise_Demo_Zh extends AppCompatActivity {
    private SpeechEvaluator mSpeechEvaluator;
    private static String TAG = Ise_Demo.class.getSimpleName();

    private final static String PREFER_NAME = "ise_settings";
    private final static int REQUEST_CODE_SETTINGS = 1;

    //recyclerView 变量
    private RecyclerView recyclerView;
    private List<DataBean> lists;
    private PagingScrollHelper.onPageChangeListener onPageChangeListener;
    private RecyclerViewAdapter_Zh recyclerViewAdapter;


    //private Card_View.DataBean dataBean;
    private String mResultText;
    private Toast mToast;
    private TextView kTextVeiw;
    private Button ise_start_zh;
    private TextView tv_result_zh, tv_more_zh;
    private ImageView iv_getmore_zh;
    private boolean flag = true;


    // 评测语种
    private String language;
    // 评测题型
    private String category;
    // 结果等级
    private String result_level;

    private String mLastResult;

    // 评测监听接口
    private EvaluatorListener mEvaluatorListener = new EvaluatorListener() {

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            Log.d(TAG, "evaluator begin");
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            Log.d(TAG, "evaluator stoped");
        }

        @Override
        public void onResult(EvaluatorResult result, boolean isLast) {
            Log.d(TAG, "evaluator result :" + isLast);

            if (isLast) {
                StringBuilder builder = new StringBuilder();
                builder.append(result.getResultString());


                if (!TextUtils.isEmpty(builder)) {
                    ise_start_zh.setEnabled(true);
                    mResultText = builder.toString();
                }

                mLastResult = builder.toString();
                Toast.makeText(Ise_Demo_Zh.this, "此时请按下按钮", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SpeechError error) {
            if (error != null) {
                Toast.makeText(Ise_Demo_Zh.this, "error:" + error.getErrorCode() + "," + error.getErrorDescription(), Toast.LENGTH_SHORT).show();
                //     mResultText.setHint("请点击“开始评测”按钮");
            } else {
                Log.d(TAG, "evaluator over");
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler_view);


        final ArrayList<String> zh = new ArrayList<>();
        zh.add("法");
        zh.add("瓷");
        zh.add("仁");
        zh.add("孝");
        zh.add("亲人");
        zh.add("朋友");
        zh.add("黄河");
        zh.add("天空");
        zh.add("爱情");
        zh.add("友谊");
        zh.add("亲人");
        zh.add("朋友");
        zh.add("己所不欲，勿施于人");
        zh.add("举头望明月，低头思故乡");
        zh.add("人有悲欢离合，月有阴晴圆缺");
        zh.add("山重水复疑无路，柳暗花明又一村");
        zh.add("少年强，则国强");
        zh.add("曾经沧海难为水");


        final ArrayList<Integer> iv_k = new ArrayList<>();
        iv_k.add(R.drawable.ic_fa);
        iv_k.add(R.drawable.ic_ci);
        iv_k.add(R.drawable.ic_ren);
        iv_k.add(R.drawable.ic_xiao);
        iv_k.add(R.drawable.ic_qingren);
        iv_k.add(R.drawable.ic_pengyou);
        iv_k.add(R.drawable.ic_huanghe);
        iv_k.add(R.drawable.ic_tiankong);
        iv_k.add(R.drawable.ic_jisuo);
        iv_k.add(R.drawable.ic_jutou);
        iv_k.add(R.drawable.ic_renyou);
        iv_k.add(R.drawable.ic_sanchong);


        initData();
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        PagingScrollHelper scrollHelper = new PagingScrollHelper();
        scrollHelper.setUpRecycleView(recyclerView);
        //设置页面滚动监听
        scrollHelper.setOnPageChangeListener(onPageChangeListener);


        recyclerViewAdapter = new RecyclerViewAdapter_Zh(Ise_Demo_Zh.this, lists);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);


        SpeechUtility.createUtility(Ise_Demo_Zh.this, "appid=" + getString(R.string.appid));
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        mSpeechEvaluator = SpeechEvaluator.createEvaluator(Ise_Demo_Zh.this, null);


        mToast = Toast.makeText(Ise_Demo_Zh.this, "", Toast.LENGTH_LONG);


        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter_Zh.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(TAG, "FFFFFF");
                if (null == mSpeechEvaluator) {
                    // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
                    Toast.makeText(Ise_Demo_Zh.this, "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化", Toast.LENGTH_SHORT).show();
                    return;
                }
                RecyclerViewAdapter_Zh.ItemHolder viewHolder = (RecyclerViewAdapter_Zh.ItemHolder) recyclerView.findViewHolderForLayoutPosition(position);

                tv_more_zh = viewHolder.text_more;
                tv_result_zh = viewHolder.text_result;
                ise_start_zh = viewHolder.test;
                iv_getmore_zh = viewHolder.imageView_more;
                switch (view.getId()) {

                    case R.id.btn_test:
                        if (flag) {
                            Toast.makeText(Ise_Demo_Zh.this, "请朗读以上内容", Toast.LENGTH_SHORT).show();
                            setParams();
                            for (int i = 0; i < zh.size(); i++) {
                                mSpeechEvaluator.startEvaluating(zh.get(position), null, mEvaluatorListener);
                            }
                            ise_start_zh.setText("按下停止");
                            flag = false;
                            Log.e(TAG, "onClick: 1");

                        } else if (!flag) {
                            Log.e(TAG, "onClick:>>>>>> ");
                            ise_start_zh.setText("测试");
                            if (mSpeechEvaluator.isEvaluating()) {
                                mSpeechEvaluator.stopEvaluating();
                            }
                            Log.e(TAG, "2");
                            if (!TextUtils.isEmpty(mLastResult)) {
                                XmlResultParser resultParser = new XmlResultParser();
                                Log.e(TAG, "onClick: ????");
                                Result result = resultParser.parse(mLastResult);
                                Log.e(TAG, "3");
                                if (null != result) {
                                    Log.e(TAG, "5");
                                    tv_result_zh.setText(result.toString());
                                    Log.e(TAG, "4 ");
                                } else {
                                    Toast.makeText(Ise_Demo_Zh.this, "解析结果为空", Toast.LENGTH_SHORT).show();
                                }
                            }
                            flag = true;
                            Log.e(TAG, "onClick:2 ");
                        }
                        break;
                    case R.id.iv_shoucang:
                        DataSave dataSave = new DataSave(Ise_Demo_Zh.this);
                        for (int i = 0; i < zh.size(); i++) {
                            Log.e(TAG, "onClick: collect start");
                            dataSave.save("collect.txt", zh.get(position));
                            Log.e(TAG, "onClick: collect succeed");
                        }
                        Toast.makeText(Ise_Demo_Zh.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                        //                      Toast.makeText(Ise_Demo.this, "收藏这个词", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_more:
                        //                      Toast.makeText(Ise_Demo.this, "你会得到更多知识哦！", Toast.LENGTH_SHORT).show();
//
                        //                      for(int  i = 0 ; i < ci_k.size();i++){
                        //                          mResultText.setText(ci_k.get(position));
                        //                      }
                        if (flag) {
                            flag = false;
                            tv_more_zh.setText("点击获取更多");
                            iv_getmore_zh.setVisibility(ImageView.VISIBLE);

                            for (int i = 0; i < 10; i++) {
                                iv_getmore_zh.setImageResource(iv_k.get(position));
                            }

                            tv_more_zh.setText("点击收起");
//                            chapterAdapter.notifyItemChanged(position);
                            Log.e("ggg", "VISIBLE: ");
                        } else if (!flag) {
                            flag = true;
                            tv_more_zh.setText("点击获取更多");
                            // iv_getmore.setVisibility(View.GONE);
                            iv_getmore_zh.setVisibility(ImageView.INVISIBLE);
                            //  chapterAdapter.notifyItemChanged(position);
                            Log.e("ttt", "INVISIBLE: ");
                        }
//                        chapterAdapter.notifyItemChanged(position);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE_SETTINGS == requestCode) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null == mSpeechEvaluator) {
            mSpeechEvaluator.destroy();
            mSpeechEvaluator = null;
        }
    }


    private void setParams() {
        SharedPreferences pref = getSharedPreferences(PREFER_NAME, MODE_PRIVATE);
        // 设置评测语言
        language = pref.getString(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置需要评测的类型
        category = pref.getString(SpeechConstant.ISE_CATEGORY, "read_sentence");
        // 设置结果等级（中文仅支持complete）
        result_level = pref.getString(SpeechConstant.RESULT_LEVEL, "complete");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        String vad_bos = pref.getString(SpeechConstant.VAD_BOS, "5000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        String vad_eos = pref.getString(SpeechConstant.VAD_EOS, "1800");
        // 语音输入超时时间，即用户最多可以连续说多长时间；
        String speech_timeout = pref.getString(SpeechConstant.KEY_SPEECH_TIMEOUT, "-1");

        mSpeechEvaluator.setParameter(SpeechConstant.LANGUAGE, language);
        mSpeechEvaluator.setParameter(SpeechConstant.ISE_CATEGORY, category);
        mSpeechEvaluator.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mSpeechEvaluator.setParameter(SpeechConstant.VAD_BOS, vad_bos);
        mSpeechEvaluator.setParameter(SpeechConstant.VAD_EOS, vad_eos);
        mSpeechEvaluator.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, speech_timeout);
        mSpeechEvaluator.setParameter(SpeechConstant.RESULT_LEVEL, result_level);

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mSpeechEvaluator.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mSpeechEvaluator.setParameter(SpeechConstant.ISE_AUDIO_PATH, Environment.getExternalStorageDirectory().getAbsolutePath() + "/msc/ise.wav");
        //通过writeaudio方式直接写入音频时才需要此设置
        //mIse.setParameter(SpeechConstant.AUDIO_SOURCE,"-1");
    }

    @Override
    protected void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(Ise_Demo_Zh.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(Ise_Demo_Zh.this);
        super.onPause();
    }


    public class DataBean {
        String textView1;
        String textView2;
        int imageView;


        DataBean(String textView1, String textView2, int gg) {
            this.textView1 = textView1;
            this.textView2 = textView2;
            this.imageView = imageView;
        }


        public String getText() {
            String text = textView1;
            return text;
        }

        public String getContent() {
            String content = textView2;
            return content;
        }


        public int getImageView() {
            int id = imageView;
            return id;
        }

        public void setImageView(int imageView) {
            this.imageView = imageView;
        }
    }

    private void initData() {
        lists = new ArrayList<>();
        lists.add(new DataBean("仁", "[ rén ]", R.drawable.timg));
        lists.add(new DataBean("智", "[ zhì ]", R.drawable.timg));
        lists.add(new DataBean("孝", "[ xiào]", R.drawable.timg));
        lists.add(new DataBean("福", "[ fú ]", R.drawable.timg));
        lists.add(new DataBean("爱情", "[ ài qíng ] ", R.drawable.timg));
        lists.add(new DataBean("友谊", "[ yǒu yì ] ", R.drawable.timg));
        lists.add(new DataBean("亲人", "[ qīn rén ]  ", R.drawable.timg));
        lists.add(new DataBean("朋友", "[ péng yǒu ]  ", R.drawable.timg));
        lists.add(new DataBean("少年强，则国强", "[ shǎo nián qiáng ，zé guó qiáng ]", R.drawable.timg));
        lists.add(new DataBean("曾经沧海难为水", "[céng jīng cāng hǎi nán wéi shuǐ ]", R.drawable.timg));
    }
}
