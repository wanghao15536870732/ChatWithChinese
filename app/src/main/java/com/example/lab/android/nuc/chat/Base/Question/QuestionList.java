package com.example.lab.android.nuc.chat.Base.Question;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class QuestionList {
    private static QuestionList sQuestion;

    private List<Question> mQuestions;

    public static QuestionList get(Context context){
        if(sQuestion == null){
            sQuestion = new QuestionList(context);
        }
        return sQuestion;
    }

    private QuestionList(Context context){
        mQuestions = new ArrayList<>();
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }
}
