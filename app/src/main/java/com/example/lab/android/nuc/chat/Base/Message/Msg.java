package com.example.lab.android.nuc.chat.Base.Message;

public class Msg {

   private String questionTitle;
   private String questionDetail;
   private String questionTime;
   public int questionNumber;

    public static final int TYPE_RECEVIED = 0;

    public static final int TYOE_SEND = 1;

    //消息内容
    public String content;

    //放松的消息的类型
    private int type;


    public Msg(String content,int type){
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
    }

    public String getQuestionTime() {
        return questionTime;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionDetail() {
        return questionDetail;
    }

    public void setQuestionDetail(String questionDetail) {
        this.questionDetail = questionDetail;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
