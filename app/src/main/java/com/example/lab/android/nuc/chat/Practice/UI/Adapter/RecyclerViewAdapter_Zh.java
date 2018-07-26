package com.example.lab.android.nuc.chat.Practice.UI.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.lab.android.nuc.chat.Practice.Test.Ise_Demo_Zh;
import com.example.lab.android.nuc.chat.R;

import java.util.List;

public class RecyclerViewAdapter_Zh extends RecyclerView.Adapter implements View.OnClickListener {

    private List<Ise_Demo_Zh.DataBean> dataBeanList;
    private Context context;

    public RecyclerViewAdapter_Zh(Context context, List<Ise_Demo_Zh.DataBean> list) {
        this.context = context;
        this.dataBeanList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.card_view4, parent, false);
        //将创建的View注册点击事件
        itemView.setOnClickListener(RecyclerViewAdapter_Zh.this);

        return new ItemHolder(itemView);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position,List payloads) {
        ItemHolder itemHolder = (ItemHolder) holder;
        if (payloads.isEmpty()){
            itemHolder.itemView.setTag(position);
            //   itemHolder.mIseStartButton.setTag(position);
            //    itemHolder.btn2.setTag(position);
            //    itemHolder.btn3.setTag(position);
            itemHolder.test.setTag(position);
            itemHolder.textView1.setTag(position);
            itemHolder.textView2.setTag(position);
            itemHolder.imageView.setTag(position);
            itemHolder.text_more.setTag(position);
            itemHolder.text_result.setTag(position);
            itemHolder.imageView_more.setTag(position);
            Ise_Demo_Zh.DataBean dataBean = dataBeanList.get(position);
            itemHolder.textView1.setText(dataBean.getText());
            itemHolder.textView2.setText(dataBean.getContent());
            itemHolder.imageView_more.setImageResource(dataBeanList.get(position).getImageView());
        } else{
            int type =(int)payloads.get(0);
            switch (type){
                case 0 :
                    itemHolder.imageView_more.setImageResource(dataBeanList.get(position).getImageView());
                    break;
            }
        }
        //     itemHolder.mIseStartButton.setOnClickListener(RecyclerViewAdapter.this);
        //    itemHolder.btn2.setOnClickListener(RecyclerViewAdapter.this);
        //     itemHolder.btn3.setOnClickListener(RecyclerViewAdapter.this);

        itemHolder.textView1.setOnClickListener(RecyclerViewAdapter_Zh.this);
        itemHolder.textView2.setOnClickListener(RecyclerViewAdapter_Zh.this);
        itemHolder.imageView.setOnClickListener(RecyclerViewAdapter_Zh.this);

        itemHolder.test.setOnClickListener(RecyclerViewAdapter_Zh.this);
        itemHolder.text_more.setOnClickListener(RecyclerViewAdapter_Zh.this);
    }

    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {


        public Button test;
        public TextView textView1, textView2;
        public ImageView imageView,imageView_more;
        public TextView text_more;
        public TextView text_result;


        public ItemHolder(View itemView) {
            super(itemView);

            test = itemView.findViewById(R.id.btn_test);
            textView1 = itemView.findViewById(R.id.tv1);
            textView2 = itemView.findViewById(R.id.tv2);
            imageView = itemView.findViewById(R.id.iv_shoucang);
            text_more = itemView.findViewById(R.id.tv_more);
            imageView_more = itemView.findViewById(R.id.iv_more);
            text_result = itemView.findViewById(R.id.ise_result_text);

        }
    }


    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * item里面有多个控件可以点击
     */


    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, int position);
    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (mOnItemClickListener != null) {
            switch (v.getId()) {
                //    case R.id.ise_start:
                //        mOnItemClickListener.onClick(v, position);
                //         break;
                ///     case R.id.ise_stop:
                //         mOnItemClickListener.onClick(v, position);
                //         break;
                case R.id.btn_test:
                    mOnItemClickListener.onClick(v, position);
                    break;
                case R.id.iv_shoucang:
                    mOnItemClickListener.onClick(v, position);
                    break;
                case R.id.tv_more:
                    mOnItemClickListener.onClick(v, position);
                    break;
                default:
                    //      mOnItemClickListener.onClick(v, position);
                    break;
            }
        }
    }

}
