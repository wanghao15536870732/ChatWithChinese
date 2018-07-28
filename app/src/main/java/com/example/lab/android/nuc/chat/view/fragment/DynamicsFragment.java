package com.example.lab.android.nuc.chat.view.fragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.view.activity.dynamic_item_activity;
import com.example.lab.android.nuc.chat.view.adapter.NineGridTest2Adapter;
import com.example.lab.android.nuc.chat.view.adapter.model.NineGridTestModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
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
        initView(view);
        initFloatButton( view );
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
        ShineButton shineButton2 = (ShineButton) view.findViewById( R.id.po_image2 );
        ShineButton shineButton3 = (ShineButton) view.findViewById( R.id.po_image3 );

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
        mRecyclerView.setAdapter( mAdapter );

    }


    private void initListData() {
        NineGridTestModel model1 = new NineGridTestModel();
        model1.urlList.add(mUrls[0]);
        model1.image = R.drawable.picture_1;
        model1.name = "王浩";
        model1.time = "今天 21:08";
        mList.add(model1);

        NineGridTestModel model2 = new NineGridTestModel();
        model2.urlList.add(mUrls[4]);
        model2.image = R.drawable.picture_2;
        model2.time = "今天 20:50";
        model2.name = "魏祥一";
        mList.add(model2);
//
//        NineGridTestModel model3 = new NineGridTestModel();
//        model3.urlList.add(mUrls[2]);
//        mList.add(model3);

        NineGridTestModel model4 = new NineGridTestModel();
        for (int i = 0; i < mUrls.length; i++) {
            model4.urlList.add(mUrls[i]);
        }
        model4.isShowAll = false;
        model4.image = R.drawable.picture_4;
        model4.name ="许超";
        model4.time = "今天 12:40";
        mList.add(model4);

        NineGridTestModel model5 = new NineGridTestModel();
        for (int i = 0; i < mUrls.length; i++) {
            model5.urlList.add(mUrls[i]);
        }
        model5.isShowAll = true;//显示全部图片
        model5.image = R.drawable.picture_5;
        model5.name = "王天锐";
        model1.time = "昨天 21:51";
        mList.add(model5);

        NineGridTestModel model6 = new NineGridTestModel();
        for (int i = 0; i < 9; i++) {
            model6.urlList.add(mUrls[i]);
        }
        model6.image = R.drawable.picture_6;
        model6.name = "鲍骞月";
        model6.time = "前天 16:25";
        mList.add(model6);

        NineGridTestModel model7 = new NineGridTestModel();
        for (int i = 3; i < 7; i++) {
            model7.urlList.add(mUrls[i]);
        }
        mList.add(model7);
        model7.image = R.drawable.picture_7;
        model7.time  = "07月18日 23:56";
        model7.name = "李浩";
        mList.add( model7 );

        NineGridTestModel model8 = new NineGridTestModel();
        for (int i = 3; i < 6; i++) {
            model8.urlList.add(mUrls[i]);
        }
        model8.image = R.drawable.picture_8;
        model8.name = "李阳";
        model8.time = "07月18日 12:03";
        mList.add(model8);
    }

    //图片数据的url;
    private String[] mUrls = new String[]{
            "http://d.hiphotos.baidu.com/image/h%3D200/sign=201258cbcd80653864eaa313a7dca115/ca1349540923dd54e54f7aedd609b3de9c824873.jpg",
            "(https://upload-images.jianshu.io/upload_images/9140378-ecc3f1dc6f3bb61c.jpg",
            "http://d.hiphotos.baidu.com/image/h%3D200/sign=ea218b2c5566d01661199928a729d498/a08b87d6277f9e2fd4f215e91830e924b999f308.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3445377427,2645691367&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2644422079,4250545639&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1444023808,3753293381&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=882039601,2636712663&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119861953,350096499&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2437456944,1135705439&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3251359643,4211266111&fm=21&gp=0.jpg",
            "http://img4.duitang.com/uploads/item/201506/11/20150611000809_yFe5Z.jpeg",
            "http://img5.imgtn.bdimg.com/it/u=1717647885,4193212272&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2024625579,507531332&fm=21&gp=0.jpg"
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
