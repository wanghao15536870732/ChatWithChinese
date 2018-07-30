package com.example.lab.android.nuc.chat.view.adapter.Binder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lab.android.nuc.chat.view.activity.ChatActivity;
import com.example.lab.android.nuc.chat.Base.Stu_Tea.Item.Student;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.Base.Stu_Tea.SetTeacher;
import com.makeramen.roundedimageview.RoundedImageView;

import me.drakeet.multitype.ItemViewBinder;

public class StudentViewBinder extends ItemViewBinder<Student, StudentViewBinder.ViewHolder> {
    private View root;
    private SetTeacher setTeacher;
    private RoundedImageView roundRectangleImageView;
    private TextView tvName, tvScore;
    private ImageView ivCountry;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        root = inflater.inflate(R.layout.item_student, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Student student) {
        setTeacher = student.getSetTeacher();
        initView();
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), ChatActivity.class);
                intent.putExtra("teacherID", setTeacher.getTeacherID());
                intent.putExtra("name", setTeacher.getName());
                intent.putExtra("picurl", setTeacher.getPicurl());
                intent.putExtra("score", setTeacher.getScore());
                intent.putExtra("language", setTeacher.getLanguage());
                intent.putExtra("introduceLink", setTeacher.getIntroduceLink());
                intent.putExtra("experience", setTeacher.getExperience());
                intent.putExtra("plan", setTeacher.getPlain());
                intent.putExtra("type", setTeacher.getType());
                root.getContext().startActivity(intent);

            }
        });
    }


    private void initView() {
        roundRectangleImageView = root.findViewById(R.id.iv_student);
        tvName = root.findViewById(R.id.tv_student_name);
        tvScore = root.findViewById(R.id.tv_student_score);
        ivCountry = root.findViewById(R.id.iv_student_country);
        tvName.setText(setTeacher.getName());
        tvScore.setText(setTeacher.getScore());

        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.ic_user_pic);
        Glide.with(root.getContext()).load(setTeacher.getPicurl()).into(roundRectangleImageView);
        switch (setTeacher.getLanguage()) {
            case "汉语":
                ivCountry.setImageDrawable(root.getContext().getDrawable(R.drawable.country_cn));
                break;
            case "ch":
                ivCountry.setImageDrawable(root.getContext().getDrawable(R.drawable.country_ch));
                break;
            case "德语":
                ivCountry.setImageDrawable(root.getContext().getDrawable(R.drawable.country_de));
                break;
            case "ea":
                ivCountry.setImageDrawable(root.getContext().getDrawable(R.drawable.country_ea));
                break;
            case "英语":
                ivCountry.setImageDrawable(root.getContext().getDrawable(R.drawable.country_gb));
                break;
            case "fa":
                ivCountry.setImageDrawable(root.getContext().getDrawable(R.drawable.country_hm));
                break;
            case "lr":
                ivCountry.setImageDrawable(root.getContext().getDrawable(R.drawable.country_lr));
                break;
            case "法语":
                ivCountry.setImageDrawable(root.getContext().getDrawable(R.drawable.country_gr));
                break;
            default:
                ivCountry.setImageDrawable(root.getContext().getDrawable(R.drawable.country_cn));
                break;
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
