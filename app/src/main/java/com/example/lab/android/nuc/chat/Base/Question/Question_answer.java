package com.example.lab.android.nuc.chat.Base.Question;

public class Question_answer {
    private int imageId;

    private String answer_name;

    private String answer_detail;

    private String answer_time;

    public Question_answer(int imageId,String name,String answer_detail,String answer_time){
        this.imageId = imageId;
        this.answer_detail = answer_detail;
        this.answer_name = name;
        this.answer_time = answer_time;
    }

    public int getImageId() {
        return imageId;
    }

    public String getAnswer_name() {
        return answer_name;
    }

    public void setAnswer_name(String answer_name) {
        this.answer_name = answer_name;
    }

    public void setAnswer_detail(String answer_detail) {
        this.answer_detail = answer_detail;
    }

    public String getAnswer_detail() {
        return answer_detail;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getAnswer_time() {
        return answer_time;
    }

    public void setAnswer_time(String answer_time) {
        this.answer_time = answer_time;
    }
}
