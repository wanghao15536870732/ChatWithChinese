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

public class TopicActivity extends AppCompatActivity {
    private RecyclerViewAdapter mTopicAdapter;

    private RecyclerView mRecyclerTopic;

    private void assignViews() {
        mRecyclerTopic = (RecyclerView) findViewById( R.id.recycler_topic);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        assignViews();
        initIncomeRecyclerView();
        loadDate();
    }

    private void initIncomeRecyclerView() {
        mRecyclerTopic.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerTopic.setItemAnimator(null);
        mTopicAdapter = new RecyclerViewAdapter<People>(R.layout.item_recyle) {
            @Override
            public void convert(People people, AdapterViewHolder holder, int position) {
                final TextView textView = holder.getView( R.id.tv_name );
                RelativeLayout relativeLayout = holder.getView( R.id.rl_item );
                holder.setText( R.id.tv_name, people.getName() );
                relativeLayout.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = TopicActivity.this.getIntent();
                        Bundle bundle = intent.getExtras();
                        bundle.putString( "topic", textView.getText().toString() );//添加要返回给页面1的数据
                        intent.putExtras( bundle );
                        TopicActivity.this.setResult( Activity.RESULT_OK, intent );//返回页面1
                        TopicActivity.this.finish();
                    }
                } );
            }


        };
        mRecyclerTopic.setAdapter(mTopicAdapter);
    }

    private void loadDate() {
        People people = new People();
        people.setName("要学好汉语一定要多听、多写、多看、多说、多练习才能学好汉语");
        People people1 = new People();
        people1.setName("上课之前我们应该先把课本的材料预习预习");
        People people2 = new People();
        people2.setName("如果有不明白的字，要主动查词典");


        List<People> tempList = new ArrayList<>();
        tempList.add(people);
        tempList.add(people1);
        tempList.add(people2);
        mTopicAdapter.replaceAll(tempList);
    }

}
