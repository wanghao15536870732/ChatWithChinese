package com.example.lab.android.nuc.chat.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.view.activity.VoiceToTextActivity;
import com.example.lab.android.nuc.chat.view.activity.dynamic_item_activity;
import com.example.lab.android.nuc.chat.view.adapter.NineGridTest2Adapter;
import com.example.lab.android.nuc.chat.view.adapter.model.NineGridTestModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.List;

public class DynamicsFragment extends Fragment {
    private static final int REQUEST_DYNAMICS = 1;
    private View view;
    private static final String ARG_LIST = "list";
    private LinearLayout commentLayout;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NineGridTest2Adapter mAdapter;
    private TextView commentFakeButton;
    private List<NineGridTestModel> mList = new ArrayList<>( );
    private TextView commentButton;


    /*与悬浮按钮相关*/
    private FloatingActionsMenu mFloatingActionsMenu;
    private FloatingActionButton mActionMap;
    private FloatingActionButton mActionModel;
    private FloatingActionButton mAction_c;
    private FloatingActionButton mAction_d;
    private FloatingActionButton mAction_e;

    public static DynamicsFragment newInstance(){

        Bundle bundle = new Bundle( );
        DynamicsFragment dynamicsFragment = new DynamicsFragment();
        dynamicsFragment.setArguments( bundle );
        return dynamicsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_dynamics,container,false);
        initListData();
        initFloatButton( view );
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        initData();
        super.onResume();
    }

    private void initData(){
        ShineButton shineButton1 = (ShineButton) view.findViewById( R.id.po_image1 );
//        shineButton1.init( (MainActivity) getActivity() );

    }
    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById( R.id.recyclerView );
        commentLayout = (LinearLayout) view.findViewById( R.id.comment_listView );
        commentFakeButton = (TextView) view.findViewById(R.id.tv_comment_fake_button);
        commentFakeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );

        mLayoutManager = new LinearLayoutManager( getContext() );
        mRecyclerView.setLayoutManager( mLayoutManager );
        mAdapter = new NineGridTest2Adapter( getContext() );
        mAdapter.setList( mList );
        mRecyclerView.addItemDecoration( new DividerItemDecoration( getContext(),DividerItemDecoration.VERTICAL ));
        mRecyclerView.setAdapter( mAdapter );

    }



    private void initListData() {
        NineGridTestModel model1 = new NineGridTestModel();
        model1.urlList.add(mUrls[0]);
        model1.name = "Abbott";
        model1.imageUri = imageUrls[0];
        model1.time = "今天 21:08";
        model1.detail = "在学习英语时，有必要记忆一些生僻的单词么？如何学会接近母语者(特指标准语母语者)的语调？";
        model1.country_image = R.drawable.country_ch;
        mList.add(model1);

        NineGridTestModel model2 = new NineGridTestModel();
        model2.urlList.add(mUrls[4]);
        model2.imageUri = imageUrls[7];
        model2.time = "今天 20:50";
        model2.name = "李太阳";
        model2.detail = "在外语系学习外语和出国学习外语有何差别，最近在学德育，有没有什么应该注意的问题？";
        model2.country_image = R.drawable.country_cn;
        mList.add(model2);
//
//        NineGridTestModel model3 = new NineGridTestModel();
//        model3.urlList.add(mUrls[2]);
//        mList.add(model3);

        NineGridTestModel model4 = new NineGridTestModel();
        for (int i = 4; i < mUrls.length; i++) {
            model4.urlList.add(mUrls[i]);
        }
        model4.isShowAll = false;
        model4.imageUri = imageUrls[1];
        model4.name ="Pery";
        model4.time = "今天 12:40";
        model4.detail = "小孩什么时候来时学习外语比较好呢？学习外语的正确姿势是什么？";
        model4.country_image = R.drawable.country_de;
        mList.add(model4);

        NineGridTestModel model5 = new NineGridTestModel();
        for (int i = 0; i < mUrls.length - 4; i++) {
            model5.urlList.add(mUrls[i]);
        }
        model5.isShowAll = true;//显示全部图片
        model5.image = R.drawable.picture_5;
        model5.imageUri = imageUrls[6];
        model5.name = "Zoe";
        model5.time = "昨天 21:51";
        model5.detail = "学习俄语后，开始学习英语,如何克服混淆的问题？学习俄语忘了英语这可咋整？";
        model5.country_image = R.drawable.country_ea;
        mList.add(model5);

        NineGridTestModel model6 = new NineGridTestModel();
        for (int i = 6; i < 12; i++) {
            model6.urlList.add(mUrls[i]);
        }
        model6.image = R.drawable.picture_6;
        model6.imageUri = imageUrls[2];
        model6.name = "Xaviera";
        model6.time = "前天 16:25";
        model6.detail = "中国人是否在学习汉语上投入了太多的时间跟精力？怎样才能解决拼音书写汉语时的同音词问题？";
        model6.country_image = R.drawable.country_gb;
        mList.add(model6);

        NineGridTestModel model7 = new NineGridTestModel();
        for (int i = 0; i < 2; i++) {
            model7.urlList.add(mUrls[i]);
        }
        model7.image = R.drawable.picture_7;
        model7.time  = "08月15日 23:56";
        model7.name = "Virginia";
        model7.imageUri = imageUrls[5];
        model7.detail="有半年时间，应该深化英语还是学习法语？还有去法国哪里学习法语会比较好?";
        model7.country_image = R.drawable.country_gr;
        mList.add(model7);

        NineGridTestModel model8 = new NineGridTestModel();
        for (int i = 3; i < 6; i++) {
            model8.urlList.add(mUrls[i]);
        }
        model8.image = R.drawable.picture_8;
        model8.imageUri = imageUrls[4];
        model8.name = "Winifre";
        model8.time = "08月14日 12:03";
        model8.detail = "论在大连外国语大学学习阿拉伯语始终什么样的体验？阿拉伯语手写容易练成么？";
        model8.country_image = R.drawable.country_hm;
        mList.add(model8);
        NineGridTestModel model9 = new NineGridTestModel();
        for (int i = 7; i < 10; i++) {
            model9.urlList.add(mUrls[i]);
        }
        model9.image = R.drawable.picture_9;
        model9.imageUri = imageUrls[3];
        model9.name = "Brandon";
        model9.time = "08月14日 12:03";
        model9.detail = "论在大连外国语大学学习阿拉伯语始终什么样的体验？阿拉伯语手写容易练成么？";
        model9.country_image = R.drawable.country_lr;
        mList.add(model9);

        OkGo.<String>post( "http://47.95.7.169:8080/getQuestion")
                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                } );
    }

    //图片数据的url;
    private String[] mUrls = new String[]{
            "http://p8nssbtwi.bkt.clouddn.com/question_13.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/question_12.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/question_14.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/question_10.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/question_9.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/question_8.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/question_7.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/question_1.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/question_2.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/question_3.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/question_4.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/picture_15.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/question_6.jpg"
    };

    private String[] imageUrls = new String[]{
            "http://p8nssbtwi.bkt.clouddn.com/download.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/student_2.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/student_3.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/student_4.jpg",
            "http://p8nssbtwi.bkt.clouddn.com/ic_s12.jpeg",
            "http://p8nssbtwi.bkt.clouddn.com/ic_s9.jpeg",
            "http://p8nssbtwi.bkt.clouddn.com/ic_s7.jpeg",
            "http://p8nssbtwi.bkt.clouddn.com/ic_s8.jpeg",
            "http://p8nssbtwi.bkt.clouddn.com/ic_s5.jpeg"
    };

    //悬浮按钮的一些点击事件
    private void initFloatButton(View view){
        mFloatingActionsMenu = (FloatingActionsMenu) view.findViewById( R.id.main_actions_menu );
        /**
         * 添加动态
         */
        mActionMap = (FloatingActionButton) view.findViewById( R.id.action_a );
        mActionMap.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(),dynamic_item_activity.class);
                startActivityForResult( intent,REQUEST_DYNAMICS);
                mFloatingActionsMenu.toggle();
            }
        } );



        mActionModel = (FloatingActionButton) view.findViewById( R.id.action_b );
        mActionModel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getContext(), VoiceToTextActivity.class ) );
                mFloatingActionsMenu.toggle();
            }
        } );

        mAction_c = (FloatingActionButton) view.findViewById( R.id.action_c );
        mAction_c.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFloatingActionsMenu.toggle();
            }
        } );
        mAction_d = (FloatingActionButton) view.findViewById( R.id.action_d );
        mAction_d.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFloatingActionsMenu.toggle();
            }
        } );
    }
}
