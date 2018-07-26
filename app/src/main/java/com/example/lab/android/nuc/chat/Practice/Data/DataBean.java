package com.example.lab.android.nuc.chat.Practice.Data;

public class DataBean {

        String  textView1;
        String  textView2;
        DataBean(String textView1, String textView2){
            this.textView1 = textView1;
            this.textView2 =  textView2;
        }


        public String getText() {
            String text = textView1;
            return text;
        }

        public String  getContent() {
            String content = textView2;
            return content;
        }

}
