package com.example.lab.android.nuc.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.lab.android.nuc.chat.Base.contacts.People;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.view.adapter.holder.AdapterViewHolder;
import com.example.lab.android.nuc.chat.view.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {
    private RecyclerViewAdapter mArticleAdapter;
    private RecyclerView mRecyclerArticle;

    private void assignViews() {
        mRecyclerArticle = (RecyclerView) findViewById(R.id.recycler_article);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        assignViews();
        initIncomeRecyclerView();
        loadDate();
    }


    private void initIncomeRecyclerView() {
        mRecyclerArticle.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerArticle.setItemAnimator(null);
        mArticleAdapter = new RecyclerViewAdapter<People>( R.layout.item_recyle) {
            @Override
            public void convert(People model, AdapterViewHolder holder, int position) {
                final TextView textView = holder.getView(R.id.tv_name);
                RelativeLayout relativeLayout = holder.getView(R.id.rl_item);
                holder.setText(R.id.tv_name, model.getName());
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = ArticleActivity.this.getIntent();
                        Bundle bundle = intent.getExtras();
                        bundle.putString("article", textView.getText().toString());//添加要返回给页面1的数据
                        intent.putExtras(bundle);
                        ArticleActivity.this.setResult(Activity.RESULT_OK, intent);//返回页面1
                        ArticleActivity.this.finish();
                    }
                });
            }
        };
        mRecyclerArticle.setAdapter(mArticleAdapter);
    }

    private void loadDate() {
        People people = new People();
        people.setName("饮食             --     学做西红柿炒鸡蛋");
        People people1 = new People();
        people1.setName("交通            --     坐公共汽车的N个理由");
        People people2 = new People();
        people2.setName("运动与健康      --     生命在于运动");
        People people3 = new People();
        people3.setName("运动与健康      --     生命在于运动");
        People people4 = new People();
        people4.setName("健康长寿的秘诀   --     生命在于运动");
        People people5 = new People();
        people5.setName("校园生活        --       聊聊老师和选课");
        People people6 = new People();
        people6.setName("竞选演讲        --      老师和学生的区别");
        People people7 = new People();
        people7.setName("入乡随俗        --      入乡不随俗会有麻烦");
        People people8 = new People();
        people8.setName("上网            --      网络带来的问题");


        List<People> tempList = new ArrayList<>();
        tempList.add(people);
        tempList.add(people1);
        tempList.add(people2);
        tempList.add(people3);
        tempList.add(people4);
        tempList.add(people5);
        tempList.add(people6);
        tempList.add(people7);
        tempList.add(people8);
        mArticleAdapter.replaceAll(tempList);
    }

}
