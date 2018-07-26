package com.example.lab.android.nuc.chat.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lab.android.nuc.chat.view.adapter.PageAdapter;
import com.example.lab.android.nuc.chat.utils.views.RoundRectangleImageView;
import com.example.lab.android.nuc.chat.view.fragment.QuestionFragment;
import com.example.lab.android.nuc.chat.R;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

public class
ChatMainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, IOnSearchClickListener, View.OnClickListener {


    public static Context mContext;
//    Toolbar mToolbar;
    TabLayout mTabLayout;

    ViewPager mViewPager;

    private SearchFragment searchFragment;

    RoundRectangleImageView icon;

    //拍照的Uri
    private Uri imageUri;
    //定义滑动菜单的实例
    private DrawerLayout mDrawerLayout;

    private static final int TAKE_PHOTO = 1;

    private FloatingActionButton addQuestionBtn;


    private RelativeLayout rootView;

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_chat);
        initView();
//        ButterKnife.bind(this);
        //先显示默认的标题Chat以后要改为其他的
//        mToolbar.setTitle("SearchDialog");//标题
        //将系统的ToolBar改为自定义的ToolBar
//        setSupportActionBar(mToolbar);
//        PermissionUtils.verifyStoragePermissions(this);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            添加返回功能
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            默认的是返回键，更改为菜单键
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        }
        addQuestionBtn = (FloatingActionButton) findViewById(R.id.fab_add_question);

//        mToolbar.setOnMenuItemClickListener(this);
        searchFragment.setOnSearchClickListener(this);

        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_question, new QuestionFragment())
                    .addToBackStack(null)
                    .commit();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    //循环遍历
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用该功能", Toast.LENGTH_SHORT).show();

                            return;
                        }
                    }
                    Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    /**
     * 创建视图
     */


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        //找到各自的实例
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        searchFragment = SearchFragment.newInstance();

        PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), this);
//        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        ///为viewPager设置adapter
        mViewPager.setAdapter(adapter);

        //将viewPager添加进leLayout当中
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
//            为每个tab设置自定义视图，获取自定视图的方法写在Adapter里面
//            同样也可以直接写在Activity里面
            tab.setCustomView(adapter.getTabView(i));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mToolbar.setTitle("TabLayout Demo");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单文件
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search: //点击搜索
                searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
                break;
        }
        return true;
    }

    @Override
    public void OnSearchClick(String keyword) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }



    private FragmentManager mFragmentManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (requestCode == RESULT_OK) {
                    String question_link = data.getStringExtra("question_link");
                    String question_title = data.getStringExtra("question_title");
                    String question_detail = data.getStringExtra("question_detail");
                    QuestionFragment questionFragment = new QuestionFragment();

                    mFragmentManager = this.getSupportFragmentManager();
                    FragmentTransaction ft = mFragmentManager.beginTransaction();
                    //创建Bundle来封装传递给fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("link", question_link);
                    bundle.putString("title", question_title);
                    bundle.putString("detail", question_detail);
                    //设置传递的对象
                    questionFragment.setArguments(bundle);
                    ft.add(R.id.fragment_question, questionFragment, "questionFragment");
                    ft.commit();

                }
                break;
            default:
        }
    }


}
