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

public class AiTeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerAiTe;
    private RecyclerViewAdapter mAiTeAdapter;

    private void assignViews() {
        mRecyclerAiTe = (RecyclerView) findViewById( R.id.recycler_ai_te);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_te);
        assignViews();
        initIncomeRecyclerView();
        loadDate();
    }


    private void initIncomeRecyclerView() {
        mRecyclerAiTe.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerAiTe.setItemAnimator(null);
        mAiTeAdapter = new RecyclerViewAdapter<People>(R.layout.item_recyle) {
            @Override
            public void convert(People model, AdapterViewHolder holder, int position) {
                final TextView textView = holder.getView(R.id.tv_name);
                RelativeLayout relativeLayout = holder.getView(R.id.rl_item);
                holder.setText(R.id.tv_name, model.getName());
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = AiTeActivity.this.getIntent();
                        Bundle bundle = intent.getExtras();
                        bundle.putString("ai_te", textView.getText().toString());//添加要返回给页面1的数据
                        intent.putExtras(bundle);
                        AiTeActivity.this.setResult(Activity.RESULT_OK, intent);//返回页面1
                        AiTeActivity.this.finish();
                    }
                });
            }
        };
        mRecyclerAiTe.setAdapter(mAiTeAdapter);
    }

    private void loadDate() {
        People people = new People();
        people.setName("李阳  --  汉语教师");
        People people1 = new People();
        people1.setName("Abbott  --  英语教师");
        People people2 = new People();
        people2.setName("Baron  --  韩语教师");
        People people3 = new People();
        people3.setName("李太阳  --  汉语教师");
        People people4 = new People();
        people4.setName("Zanna --  法语教师");
        People people5 = new People();
        people5.setName("Xaviera  --  俄语教师");
        People people6 = new People();
        people6.setName("Abbott  --  阿拉伯教师");
        People people7 = new People();
        people7.setName("Winifred  --  泰语教师");
        People people8 = new People();
        people8.setName("Virginia  --  汉语教师");

        List<People> tempList = new ArrayList<>();
        tempList.add(people);
        tempList.add(people1);
        tempList.add(people2);
        tempList.add( people3 );
        tempList.add( people4 );
        tempList.add( people5 );
        tempList.add( people6 );
        tempList.add( people7 );
        tempList.add( people8 );
        mAiTeAdapter.replaceAll(tempList);
    }

}