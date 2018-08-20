package com.example.lab.android.nuc.chat.Practice.Result_Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lab.android.nuc.chat.Practice.Data.DataSave;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.view.activity.MainActivity;
import com.example.lab.android.nuc.chat.view.fragment.PracticeFragment;

import java.util.ArrayList;

public class Day_Test_Activity_Sentence  extends AppCompatActivity{
    private RecyclerView recyclerView;
    private  Test_Adapter_Sentence test_adapter_sentence;
    private ArrayList<DataBean> lists;
    private TextView textview;
    private ImageView iv_star;
    public ArrayList<String> wrong_sentence_List = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_test_result);
        textview = findViewById(R.id.tv_tieshi_item);
        textview.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//添加下划线
        initData();
        recyclerView = findViewById(R.id.wrong_result_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        test_adapter_sentence = new Test_Adapter_Sentence(this,lists);
        recyclerView.addItemDecoration(new RecycleViewDivider2(this,layoutManager.getOrientation()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(test_adapter_sentence);

        test_adapter_sentence.setOnItemClickListener(new Test_Adapter_Sentence.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Test_Adapter_Sentence.ItemHolder itemHolder = (Test_Adapter_Sentence.ItemHolder)recyclerView.findViewHolderForLayoutPosition(position);

                iv_star =itemHolder.imageView1;

                switch(view.getId()){
                    case R.id.star:
                        DataSave dataSave = new DataSave(Day_Test_Activity_Sentence.this);
                        dataSave.save("collect_text", String.valueOf(lists.get(position)));
                        Toast toast =  Toast.makeText(Day_Test_Activity_Sentence.this, "收藏成功", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                }
            }
        });
    }

    private void initData() {
        lists = new ArrayList<>();

        wrong_sentence_List = getIntent().getStringArrayListExtra("wrong_sentence_List");

        for (int i = 0 ; i < wrong_sentence_List.size();i++){
            DataBean dataBean = new DataBean( wrong_sentence_List.get(i));
            lists.add(dataBean);

        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Day_Test_Activity_Sentence.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();

    }
}
