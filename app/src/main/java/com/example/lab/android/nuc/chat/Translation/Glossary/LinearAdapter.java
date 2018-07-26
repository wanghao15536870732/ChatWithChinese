package com.example.lab.android.nuc.chat.Translation.Glossary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.lab.android.nuc.chat.R;

import java.util.ArrayList;


/**
 * Created by ljb on 2018/4/19.
 */

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.LinearViewHolder> {


    private ButtonInterface buttonInterface;
    private TextViewInterface textViewInterface;
    private Context context;
    private ArrayList<String> mTitle;
    private MyHistory myHistory;


    public LinearAdapter(Context context, ArrayList<String> title){
        this.context=context;
        this.mTitle=title;
    }

    @Override
    public LinearAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder( LayoutInflater.from(context).inflate(R.layout.layout_linear_item,parent,false));
    }

    @Override
    public void onBindViewHolder(LinearAdapter.LinearViewHolder holder, final int position) {
        holder.textView.setText(mTitle.get(position));

        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(textViewInterface!=null) {
//                  接口实例化后的对象，调用重写后的方法
                    textViewInterface.longclick(v,position);
                }
                return true;
            }
        });



        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewInterface!=null) {
//                  接口实例化后的对象，调用重写后的方法
                    textViewInterface.onclick(v,position,mTitle);
                }

            }
        });


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonInterface!=null) {
//                  接口实例化后的对象，调用重写后的方法
                    buttonInterface.onclick(v,position);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mTitle==null ? 0 : mTitle.size();
    }


    class LinearViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private Button button;
        public LinearViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById( R.id.tv_title);
            button=itemView.findViewById(R.id.btn_mark);
        }
    }



    /**
     *按钮点击事件需要的方法
     */
    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.buttonInterface=buttonInterface;
    }

    /**
     *文本点击事件需要的方法
     */
    public void textviewonSetOnclick(TextViewInterface textViewInterface){
        this.textViewInterface=textViewInterface;
    }


    /**
     * 按钮点击事件对应的接口
     */
    public interface ButtonInterface{
        public void onclick(View view, int position);
    }

    /**
     * 文本点击事件对应的接口
     */
    public interface TextViewInterface{
        public void onclick(View view, int position, ArrayList<String> list);
        public void longclick(View view, int position);
    }


    /***
     * 删除历史查询
     * @param pos
     */
    public void removeItem(int pos){
        String insert = "insert into info(word,result) values(?,?)";
        myHistory=new MyHistory(context);
        SQLiteDatabase db = myHistory.getWritableDatabase();
        mTitle.remove(pos);
        db.execSQL("delete from info");
        if (mTitle.size()==0){
            db.execSQL("delete from info");
            db.close();
            return;
        }else{
            for(int i=0;i<mTitle.size();i++) {
                String[] str = mTitle.get(i).split("\n");
                String word = str[0];
                 String result = str[1];
                db.execSQL(insert, new Object[]{word, result});
            }
            db.close();
        }

    }


}

