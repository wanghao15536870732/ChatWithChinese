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
import com.example.lab.android.nuc.chat.Practice.UI.Adapter.RecyclerViewAdapter_Sentence;
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


public class Ise_Demo_Sentence  extends  AppCompatActivity{

    private SpeechEvaluator mSpeechEvaluator;
    private static String TAG = Ise_Demo_Zi.class.getSimpleName();

    private final static String PREFER_NAME = "ise_settings";
    private final static int REQUEST_CODE_SETTINGS = 1;

    //recyclerView 变量
    private RecyclerView recyclerView;
    private List<DataBean_Sentence> lists_sentence;
    private PagingScrollHelper.onPageChangeListener onPageChangeListener;
    private RecyclerViewAdapter_Sentence recyclerViewAdapter_sentence;



    private String mResultText;
    private Toast mToast;
    private  TextView kTextVeiw;
    private Button ise_start_sentence;
    private TextView tv_result_sentence,tv_more_sentence;
    private ImageView iv_getmore_sentence;
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

                    mResultText = builder.toString();
                }
                mLastResult = builder.toString();
                Toast.makeText(Ise_Demo_Sentence.this, "此时请按下按钮", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SpeechError error) {
            if (error != null) {
                Toast.makeText(Ise_Demo_Sentence.this, "error:" + error.getErrorCode() + "," + error.getErrorDescription(), Toast.LENGTH_SHORT).show();
             //   mResultText.setHint("请点击“开始评测”按钮");
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


        final ArrayList<String >sentence = new ArrayList<>();
        sentence.add("曾经沧海难为水");
        sentence.add("海纳百川，有容乃大");
        sentence.add("己所不欲，勿施于人");
        sentence.add("举头望明月，低头思故乡");
        sentence.add("人有悲欢离合，月有阴晴圆缺");
        sentence.add("山重水复疑无路，柳暗花明又一村");
        sentence.add("少年强则国强");
        sentence.add("夕阳无限好，只是近黄昏");
        sentence.add("野火吹不尽，春风吹又生");
        sentence.add("有朋自远方来，不亦乐乎");


     //   final ArrayList<String> zi_k = new ArrayList<>();
     //   zi_k.add("龙(lóng)：【传(chuán)说(shuō)中(zhōng)的(de)一(yì)种(zhǒng)长(cháng)形(xí)、有(yǒu)鳞(lín)、有(yǒu)角(jiǎo)的(de)神(shén)异(yì)动(dòng)物(wù)，能(néng)走(zǒu)，能(néng)飞(fēi)，能(néng)游(yóu)泳(yǒng)，能(néng)兴(xìng)云(yún)作(zuò)雨(yǔ)。】");
     //   zi_k.add("禅(chán)：【是(shì)一(yī)种(zhǒng)基(jī)于(yú)“静(jìng)”的(de)行(xíng)为(wéi)，源(yuán)于(yú)人(rén)类(lèi)本(běn)能(néng)，经(jīng)过(guò)古(gǔ)代(dài)先(xiān)民(mín)开(kāi)发(fā)，形(xíng)成(chéng)各(gè)种(zhǒng)系(xì)统(tǒng)的(de)修(xiū)行(xín)方法(fǎ)，并(bìng)存(cún)在(zài)于(yú)各(gè)种(zhǒng)教(jiào)派(pài)。】");
     //   zi_k.add("法(fǎ)：【是(shì)国(guó)家(jiā)的(de)产(chǎn)物(wù)。是(shì)指(zhǐ)统(tǒng)治(zhì)者(zhě)（统(tǒng)治(zhì)集(jí)团(tuán)，也(yě)就(jiù)是(shì)政(zhèng)党(dǎng)，包(bāo)括(kuò)国(guó)王(wáng)、君(jūn)主(zhǔ)），为(wèi)了(le)实(shí)现(xiàn)统(tǒng)治(zhì)并(bìng)管(guǎn)理(lǐ)国(guó)家(jiā)的(de)目(mù)的(dì)，经(jīng)过(guò)一(yí)定(dìng)立(lì)法(fǎ)程(chéng)序(xù)，所(suǒ)颁(bān)布(bù)的(de)一(yí)切(qiè)规(guī)范(fàn)的(de)总(zǒng)称(chēng)。】");
     //   zi_k.add("瓷(cí)：【用(yòng)高(gāo)岭(lǐng)土(tǔ)烧(shāo)成(chéng)的(de)一(yī)种(zhǒng)质(zhì)料(liào)，所(suǒ)做(zuò)器(qì)物(wù)比(bǐ)陶(táo)器(qì)细(xì)致(zhì)而(ér)坚(jiān)硬(yìng)。中(zhōng)国(guó)是(shì)瓷(cí)器(qì)的(de)故(gù)乡(xiāng)，瓷(cí)器(qì)是(shì)古(gǔ)代(dài)劳(láo)动(dòng)人(rén)民(mín)的(de)一(yí)个(g)重(zhòng)要(yào)的(de)创(chuàng)造(zào)。瓷(cí)器(qì)的(de)发(fā)明(míng)是(shì)中(zhōng)华(huá)民(mín)族(zú)对(duì)世(shì)界(jiè)文(wén)明(míng)的(de)伟(wěi)大(dà)贡(gòng)献(xiàn)】，");
     //   zi_k.add("仁(rén)：【一(yī)种(zhǒng)道(dào)德(dé)范(fàn)畴(chóu)，指(zhǐ)人(rén)与(yǔ)人(rén)相(xiāng)互(hù)友(yǒu)爱(ài)、帮(bāng)助(zhù)、同(tóng)情(qíng)等(děng)。仁(rén)的(de)产(chǎn)生(shēng)是(shì)社(shè)会(huì)关(guān)系(xì)大(dà)变(biàn)动(dòng)在(zài)伦(lún)理(lǐ)思(sī)想(xiǎng)上(shàng)的(de)表(biǎo)现(xiàn)，是(shì)对(duì)子(zi)与(yǔ)父(fù)、君(jūn)与(yǔ)臣(chén)以(yǐ)及(jí)国(guó)与(yǔ)国(guó)关(guān)系(xì)的(de)伦(lún)理(lǐ)总(zǒng)结(jié)，因(yīn)而(ér)具(jù)有(yǒu)很(hěn)丰(fēng)富(fù)的(de)内(nèi)容(róng)。】");
     //   zi_k.add("智(zhì)：【广(guǎng)义(yì)为(wéi)明(míng)万(wàn)物(wù)阴(yīn)阳(yáng)之(zhī)本(b)，知(zhī)万(wàn)物(wù)阴(yīn)阳(yáng)之(zhī)变(biàn)化(huà)，对(duì)事(shì)物(wù)的(de)过(guò)去(qù)现(xiàn)在(zài)未(wèi)来(lái)的(de)变(biàn)化(huà)对(duì)答(dá)如(rú)流(liú)，胸(xiōng)有(yǒu)成(chéng)竹(zhú)】。");
     //   zi_k.add("孝(xiào): 【中(zhōng)华(huá)文(wén)化(huà)传(chuán)统(tǒng)提(tí)倡(chàng)的(de)行(xíng)为(wéi)，指(zhǐ)儿(ér)女(nǚ)的(de)行(xíng)为(wéi)不(bù)应(yīng)该(gāi)违(wéi)背(bèi)父(fù)母(mǔ)、家(jiā)里(lǐ)的(de)长(zhǎng)辈(bèi)以(yǐ)及(jí)先(xiān)人(rén)的(de)良(li)心(xīn)意(yì)愿(yuàn)，使(shǐ)他(tā)们(men)不(bú)至(zhì)于(yú)行(háng)差(chà)踏(tà)错(cuò)，是(shì)一(yī)种(zhǒng)稳(wěn)定(dìng)伦(lún)常(cháng)关(guān)系(xì)表(biǎo)现(xiàn)。中(zhōng)华(huá)文(wén)化(huà)中(zhōng)有(yǒu)孝(xiào)感(gǎn)动(dòng)天(tiān)，孝(xiào)德(dé)感(gǎn)神(shén)等(děng)等(děng)故(gù)事(shi)。】");
     //   zi_k.add("福(fú)：【即(jí)幸(xìng)福(fú)、福(fú)气(qì)。古(gǔ)称(chēng)富(fù)贵(guì)寿(shòu)考(kǎo)等(děng)齐(qí)备(bèi)为(wéi)福(fú)，也(yě)用(yòng)于(yú)书(shū)信(xìn)问(wèn)候(hòu)当(dāng)中(zhōng)。】");
     //   zi_k.add("德(dé)：【引(yǐn)申(shēn)为(wéi)顺(shùn)应(yìng)自(zì)然(rán)，社(shè)会(huì)，和(hé)人(rén)类(lèi)客(kè)观(guān)规(guī)律(lǜ)去(qù)做(zuò)事(shì)。不(bù)违(wéi)背(bèi)自(zì)然(rán)规(guī)律(lǜ)发(fā)展(zhǎn)去(qù)发(fā)展(zhǎn)社(shè)会(huì)，提(tí)升(shēng)自(zì)己(jǐ)。德(dé)即(jí)是(shì)对(duì)道(dào)，对(duì)自(zì)然(rán)规(guī)律(lǜ)的(de)认(rèn)识(shi)和(hé)理(lǐ)解(jiě)、践(jiàn)行(xíng)、革(gé)命(mìng)，是(shì)人(rén)文(wén)精(jīng)神(shén)的(de)一(yī)种(zhǒng)传(chuán)播(bō)。】");
     //   zi_k.add("侠(xiá)：【指(zhǐ)有(yǒu)能(néng)力(lì)的(de)人(rén)不(bù)求(qiú)回(huí)报(bào)地(dì)去(qù)帮(bāng)助(zhù)比(bǐ)自(zì)己(jǐ)弱(ruò)小(xiǎo)的(de)人(rén).这是(shì)一(yī)种(zhǒng)精(jīng)神(shén)也(yě)是(shì)一(yì)种(zhǒng)社会(huì)追(zhuī)求(qiú)。武(wǔ)侠(xiá)..仁(rén)侠(xiá)..义(yì)侠(xiá)..都(dōu)值(zhí)得(dé)去(qù)尊(zūn)重(zhòng)】");


        final ArrayList<Integer> iv_k = new ArrayList<>();
        iv_k.add(R.drawable.ic_cengjin);
        iv_k.add(R.drawable.ic_haina);
        iv_k.add(R.drawable.ic_jisuo);
        iv_k.add(R.drawable.ic_jutou);
        iv_k.add(R.drawable.ic_renyou);
        iv_k.add(R.drawable.ic_sanchong);
        iv_k.add(R.drawable.ic_shaonian);
        iv_k.add(R.drawable.ic_xiyang);
        iv_k.add(R.drawable.ic_yehuo);
        iv_k.add(R.drawable.ic_youpeng);


        initData_Sentence();
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        PagingScrollHelper scrollHelper = new PagingScrollHelper();
        scrollHelper.setUpRecycleView(recyclerView);
        //设置页面滚动监听
        scrollHelper.setOnPageChangeListener(onPageChangeListener);


        recyclerViewAdapter_sentence = new RecyclerViewAdapter_Sentence(Ise_Demo_Sentence.this, lists_sentence);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter_sentence);


        SpeechUtility.createUtility(Ise_Demo_Sentence.this, "appid=" + getString(R.string.appid));
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        mSpeechEvaluator = SpeechEvaluator.createEvaluator(Ise_Demo_Sentence.this, null);





        mToast = Toast.makeText(Ise_Demo_Sentence.this, "", Toast.LENGTH_LONG);



        recyclerViewAdapter_sentence.setOnItemClickListener(new RecyclerViewAdapter_Sentence.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(TAG, "FFFFFF");
                if (null == mSpeechEvaluator) {
                    // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
                    Toast.makeText(Ise_Demo_Sentence.this, "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化", Toast.LENGTH_SHORT).show();
                    return;
                }
                RecyclerViewAdapter_Sentence.ItemHolder viewHolder = (RecyclerViewAdapter_Sentence.ItemHolder) recyclerView.findViewHolderForLayoutPosition(position);
              //  mResultText = viewHolder.mResTv;
                kTextVeiw = viewHolder.textView1;
                ise_start_sentence = viewHolder.test;
                tv_more_sentence = viewHolder.text_more;
                tv_result_sentence = viewHolder.text_result;
                iv_getmore_sentence = viewHolder.imageView_more;
                switch (view.getId()) {

                    case R.id.btn_test:
                        if(flag){
                            Toast.makeText(Ise_Demo_Sentence.this, "请朗读以上内容", Toast.LENGTH_SHORT).show();
                            setParams();
                            for (int i = 0 ; i < sentence.size() ; i++ ){
                                mSpeechEvaluator.startEvaluating(sentence.get(position), null, mEvaluatorListener);
                            }
                            ise_start_sentence.setText("按下停止");
                            flag = false;
                            Log.e(TAG, "onClick: 1");

                        } else if(!flag){
                            Log.e(TAG, "onClick:>>>>>> " );
                            ise_start_sentence.setText("测试");
                        //    if (mSpeechEvaluator.isEvaluating()){
                                mSpeechEvaluator.stopEvaluating();
                     //       }
                            Log.e(TAG, "2" );
                            if (!TextUtils.isEmpty(mLastResult)){
                                XmlResultParser resultParser = new XmlResultParser();
                                Log.e(TAG, "onClick: ????" );
                                Result result = resultParser.parse(mLastResult);
                                Log.e(TAG, "3" );
                                if (null != result){
                                    Log.e(TAG, "5" );
                                    tv_result_sentence.setText(result.toString());
                                    Log.e(TAG, "4 " );
                                }else{
                                    Toast.makeText(Ise_Demo_Sentence.this, "解析结果为空", Toast.LENGTH_SHORT).show();
                                }
                            }
                            flag = true;
                            Log.e(TAG, "onClick:2 " );
                        }
                        break;
                    case R.id.iv_shoucang:
                        DataSave dataSave = new DataSave(Ise_Demo_Sentence.this);
                        for(int i = 0 ; i < sentence.size();i++){
                            Log.e(TAG, "onClick: collect start" );
                            dataSave.save("collect.txt",sentence.get(position));
                            Log.e(TAG, "onClick: collect succeed" );
                        }
                        Toast.makeText(Ise_Demo_Sentence.this, "收藏成功！", Toast.LENGTH_SHORT).show();
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
                            tv_more_sentence.setText("点击获取更多");
                            iv_getmore_sentence.setVisibility(ImageView.VISIBLE);

                            for(int i = 0 ; i < 10 ; i ++){
                                iv_getmore_sentence.setImageResource(iv_k.get(position));
                            }

                            tv_more_sentence.setText("点击收起");
//                            chapterAdapter.notifyItemChanged(position);
                            Log.e("ggg", "VISIBLE: ");
                        } else if (!flag){
                            flag = true;
                            tv_more_sentence.setText("点击获取更多");
                            // iv_getmore.setVisibility(View.GONE);
                            iv_getmore_sentence.setVisibility(ImageView.INVISIBLE);
                            //  chapterAdapter.notifyItemChanged(position);
                            Log.e("ttt", "INVISIBLE: ");
                        }
//                        chapterAdapter.notifyItemChanged(position);
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
        FlowerCollector.onResume(Ise_Demo_Sentence.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(Ise_Demo_Sentence.this);
        super.onPause();
    }


    public class DataBean_Sentence {
        String textView1;
        String textView2;
        int imageView;

        DataBean_Sentence(String textView1, String textView2,int gg) {
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
    }

    private void initData_Sentence() {
        lists_sentence = new ArrayList<>();
        lists_sentence.add(new DataBean_Sentence("少年强，则国强","[shǎo/nián/qiáng ，zé/guó/qiáng ]",R.drawable.timg));
        lists_sentence.add(new DataBean_Sentence("少年强，则国强","[shǎo/nián/qiáng ，zé/guó/qiáng ]",R.drawable.timg));
        lists_sentence.add(new DataBean_Sentence("少年强，则国强","[shǎo/nián/qiáng ，zé/guó/qiáng ]",R.drawable.timg));
        lists_sentence.add(new DataBean_Sentence("少年强，则国强","[shǎo/nián/qiáng ，zé/guó/qiáng ]",R.drawable.timg));
        lists_sentence.add(new DataBean_Sentence("少年强，则国强","[shǎo/nián/qiáng ，zé/guó/qiáng ]",R.drawable.timg));
        lists_sentence.add(new DataBean_Sentence("少年强，则国强","[shǎo/nián/qiáng ，zé/guó/qiáng ]",R.drawable.timg));
        lists_sentence.add(new DataBean_Sentence("少年强，则国强","[shǎo/nián/qiáng ，zé/guó/qiáng ]",R.drawable.timg));
        lists_sentence.add(new DataBean_Sentence("少年强，则国强","[shǎo/nián/qiáng ，zé/guó/qiáng ]",R.drawable.timg));
        lists_sentence.add(new DataBean_Sentence("少年强，则国强","[shǎo/nián/qiáng ，zé/guó/qiáng ]",R.drawable.timg));
        lists_sentence.add(new DataBean_Sentence("少年强，则国强","[shǎo/nián/qiáng ，zé/guó/qiáng ]",R.drawable.timg));
    }

}
