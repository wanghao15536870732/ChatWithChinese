package com.example.lab.android.nuc.chat.Translation.Glossary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.lab.android.nuc.chat.R;

import java.util.ArrayList;


/**
 * Created by ljb on 2018/4/19.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.LinearViewHolder> {


    private Context context;
    private MyOpenHelper myOpenHelper;
    private TextViewInterface textViewInterface;

    private ArrayList<String> mTitle=new ArrayList<>();

    public MyAdapter(Context context, ArrayList<String> title){
        this.context=context;
        this.mTitle=title;
    }
    @Override
    public MyAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder( LayoutInflater.from(context).inflate(R.layout.layout_myword_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyAdapter.LinearViewHolder holder, final int position) {


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
                    textViewInterface.onclick(v,position);
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
        public LinearViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById( R.id.tv_title);

        }
    }

    /**
     *文本点击事件需要的方法
     */
    public void textviewonSetOnclick(TextViewInterface textViewInterface){
        this.textViewInterface=textViewInterface;
    }
    /**
     * 文本点击事件对应的接口
     */
    public interface TextViewInterface{
        public void onclick(View view, int position);
        public void longclick(View view, int position);
    }

    /***
     * 删除历史查询
     * @param pos
     */
    public void removeItem(int pos){
        myOpenHelper=new MyOpenHelper(context);
        String insert = "insert into info(word,result) values(?,?)";
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
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

