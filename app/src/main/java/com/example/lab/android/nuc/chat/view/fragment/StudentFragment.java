package com.example.lab.android.nuc.chat.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.alibaba.fastjson.JSON;
import com.example.lab.android.nuc.chat.view.adapter.Binder.StudentViewBinder;
import com.example.lab.android.nuc.chat.Base.Stu_Tea.GetTeacher;
import com.example.lab.android.nuc.chat.Base.Stu_Tea.Item.Student;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.Base.Stu_Tea.SetTeacher;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class StudentFragment extends Fragment {
    private View root;
    private RecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private Items mItems;


    public StudentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_student, container, false);
        initView();
        http();
        return root;
    }

    private void initView() {
        mRecyclerView = root.findViewById(R.id.rv_student);
    }

    private void http() {
        final GetTeacher[] getTeacher = {new GetTeacher()};
        OkGo.<String>post("http://47.95.7.169:8080/getTeacher")
                .tag(this)
                .isMultipart(true)
                .params("name", "10")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        getTeacher[0] = JSON.parseObject(response.body(), GetTeacher.class);
//                        teachers[0] = JSON.parseArray(response.body(), SetTeacher.class);
                        initRecycleView(getTeacher[0]);
                        Log.e("chat body:", response.body());
                    }
                });
    }

    private void initRecycleView(GetTeacher getTeacher) {
        List<SetTeacher> teacherInfo = getTeacher.getSetTeachers();
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(Student.class, new StudentViewBinder());
        mRecyclerView.setAdapter(mAdapter);
        mItems = new Items();
        for (int i = 0; i < teacherInfo.size(); i++) {
            mItems.add(new Student(teacherInfo.get(i)));
        }
        mAdapter.setItems(mItems);
        mAdapter.notifyDataSetChanged();
    }
}
