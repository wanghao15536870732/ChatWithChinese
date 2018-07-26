package com.example.lab.android.nuc.chat.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab.android.nuc.chat.Base.Question.Question_answer;
import com.example.lab.android.nuc.chat.R;

import java.util.List;

public class Question_detail_Adapter extends RecyclerView.Adapter<Question_detail_Adapter.ViewHolder>{

    private List<Question_answer> mAnswerList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView answerImage;

        TextView answerName;
        TextView answerName1;

        TextView answerDetail;
        TextView commmentTime;


        public ViewHolder(View itemView) {
            super( itemView );
            answerImage = (ImageView) itemView.findViewById( R.id.response_image );
            answerName = (TextView) itemView.findViewById( R.id.answer_name );
            answerDetail = (TextView) itemView.findViewById( R.id.answer_detail );
            answerName1 = (TextView) itemView.findViewById( R.id.answer_name1 );
            commmentTime = (TextView) itemView.findViewById( R.id.comment_time );
        }
    }

    public Question_detail_Adapter(List<Question_answer> answerList){
        mAnswerList = answerList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.question_response_item ,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question_answer answer = mAnswerList.get( position );
        holder.answerImage.setImageResource( answer.getImageId() );
        holder.answerName.setText(answer.getAnswer_name());
        holder.answerDetail.setText( answer.getAnswer_detail() );
        holder.answerName1.setText( answer.getAnswer_name());
        holder.commmentTime.setText( answer.getAnswer_time());

    }


    @Override
    public int getItemCount() {
        return mAnswerList.size();
    }
}
