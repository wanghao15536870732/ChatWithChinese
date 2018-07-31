package com.example.lab.android.nuc.chat.Base.json;

import android.util.Log;

import com.example.lab.android.nuc.chat.Base.contacts.UserInfo;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class Request {

    private static String UserID = "1";

    private static String name = "123";

    private static int questionNumber = 1;

    private void requestcontact(){
        OkGo.<String>post( "http://47.95.7.169:8080/getUserInfo")
                .tag( this )
                .isMultipart( true)
                .params( "UserID",UserID)
                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i( "return","all"  + response.body());
                        Gson gson = new Gson();
                        UserInfo info = gson.fromJson(response.body(),UserInfo.class  );
                        Log.i( "return", "name : " + info.getName() );
                        Log.i( "return","eamil : " + info.getEamil() );
                        Log.i( "return","id : " + info.getUserID() );

                    }
                } );
    }


    private void  setQuestion(){
            OkGo.<String>post(  "http://47.95.7.169:8080/setQuestion")
                    .tag( this )
                    .isMultipart( true )
                    .params( "UserID",UserID )
                    .params( "name",name )
                    .execute( new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                        }
                    } );
    }

    private void getQuestion(){
        OkGo.<String>post( "http://47.95.7.169:8080/getQuestion")
                .tag( this )
                .isMultipart( true )
                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                } );

    }




    private void getComment(){
        OkGo.<String>post("http://47.95.7.169:8080/getComment" )
                .tag( this )
                .isMultipart( true )
                .params( "questionNumber",questionNumber )
                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                } );
    }

//    static class QuestionInfo {
//
//        private String questionTitle;
//        private String questionDetail;
//        private String questionTime;
//        private int questionNumber;
//
//        public int getQuestionNumber() {
//            return questionNumber;
//        }
//
//        public void setQuestionDetail(String questionDetail) {
//            this.questionDetail = questionDetail;
//        }
//
//        public void setQuestionNumber(int questionNumber) {
//            this.questionNumber = questionNumber;
//        }
//
//        public String getQuestionDetail() {
//            return questionDetail;
//        }
//
//        public void setQuestionTime(String questionTime) {
//            this.questionTime = questionTime;
//        }
//
//        public String getQuestionTime() {
//            return questionTime;
//        }
//
//        public String getQuestionTitle() {
//            return questionTitle;
//        }
//
//        public void setQuestionTitle(String questionTitle) {
//            this.questionTitle = questionTitle;
//        }
//    }
}
