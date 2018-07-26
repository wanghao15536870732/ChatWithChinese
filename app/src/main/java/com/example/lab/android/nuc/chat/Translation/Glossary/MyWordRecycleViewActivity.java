package com.example.lab.android.nuc.chat.Translation.Glossary;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.Translation.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;


public class MyWordRecycleViewActivity extends AppCompatActivity {
    private RecyclerView mRvMain;
    private MyOpenHelper myOpenHelper;
    private TextView mTvNotice;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.myword_linear_recycle_view);

        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        myOpenHelper=new MyOpenHelper(getApplicationContext());
        loadmyword();

    }

    public ArrayList<String> inputtomyword(){
        ArrayList<String> list=new ArrayList<String>();
        String[] arr;
        SQLiteDatabase db=myOpenHelper.getReadableDatabase();
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




    public void loadmyword(){
        final ArrayList<String> list=inputtomyword();
        Log.d("获取的数组结果：",list.toString());
        mRvMain=findViewById(R.id.rv_main);
        mTvNotice=findViewById( R.id.tv_notice);
        final MyAdapter myAdapter=new MyAdapter(MyWordRecycleViewActivity.this,list);
        mRvMain.setItemAnimator(new DefaultItemAnimator());
        mRvMain.setLayoutManager(new LinearLayoutManager(MyWordRecycleViewActivity.this));
        mRvMain.setAdapter(myAdapter);

        if(list.size()!=0){
            mTvNotice.setVisibility( View.GONE);
        }



        //      调用文本返回事件回调的方法
        myAdapter.textviewonSetOnclick(new MyAdapter.TextViewInterface() {
            @Override
            public void onclick(View view, int position) {

            }

            @Override
            public void longclick(View view, final int position) {
//                Toast.makeText(MyWordRecycleViewActivity.this,"长按"+position,Toast.LENGTH_SHORT).show();
//                showPopMenu(view,position,myAdapter);


                AlertDialog.Builder builder=new AlertDialog.Builder(MyWordRecycleViewActivity.this);
                builder.setTitle("删除？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showToast(MyWordRecycleViewActivity.this,"已删除");
                        myAdapter.removeItem(position);
                        myAdapter.notifyDataSetChanged();

                    }
                }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showToast(MyWordRecycleViewActivity.this,"已取消");

                    }
                }).show();
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

}
