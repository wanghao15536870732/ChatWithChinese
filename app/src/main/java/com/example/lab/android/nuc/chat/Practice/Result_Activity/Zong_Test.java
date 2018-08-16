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
import android.util.Log;
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

public class Zong_Test  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Test_Adapter_Zh test_adapter_zh;
    private ArrayList<DataBean> lists_zh;
    private TextView textView;
    private TextView last_score;
    private float last_Test_Score = 0;
    private ImageView iv_star;
    public ArrayList<String> wrong_zh_List = new ArrayList<>();
   private final String TAG = "Back On";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_result_layout);
        textView = findViewById(R.id.last_tv_tieshi_item);
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//添加下划线
        initData();
        recyclerView = findViewById(R.id.last_wrong_result_recyclerview);

        last_score =findViewById(R.id.last_tv__score);

        last_Test_Score = getIntent().getFloatExtra("last_Test_Score",100);

        last_score.setText((int)last_Test_Score + "");



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        test_adapter_zh = new Test_Adapter_Zh(this,lists_zh);

        //设置分割线
        recyclerView.addItemDecoration(new RecycleViewDivider2(this,layoutManager.getOrientation()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(test_adapter_zh);

        test_adapter_zh.setOnItemClickListener(new Test_Adapter_Zh.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Test_Adapter_Zh.ItemHolder itemHolder = (Test_Adapter_Zh.ItemHolder)recyclerView.findViewHolderForLayoutPosition(position);

                iv_star = itemHolder.imageView;

                switch (view.getId()){
                    case R.id.star:
                        DataSave dataSave = new DataSave(Zong_Test.this);
                        dataSave.save("collect_text", String.valueOf(lists_zh.get(position)));
                     Toast toast =  Toast.makeText(Zong_Test.this, "收藏成功", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                }
            }
        });
    }

    private void initData() {
        lists_zh = new ArrayList<>();

        wrong_zh_List = getIntent().getStringArrayListExtra("wrong_zh_List");

        for (int  i  = 0 ; i < wrong_zh_List.size();i++){
            DataBean dataBean = new DataBean(wrong_zh_List.get(i));
            lists_zh.add(dataBean);
        }

    }



@Override
	public void onBackPressed() {

		super.onBackPressed();
		Intent intent = new Intent(Zong_Test.this, MainActivity.class);
        Log.e(TAG, "onBackPressed: 444444444444444444");
		startActivity(intent);

	}

}
