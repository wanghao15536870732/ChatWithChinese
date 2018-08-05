package com.example.lab.android.nuc.chat.Practice.Result_Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.lab.android.nuc.chat.R;

import java.util.List;

public class Test_Adapter_Ci extends RecyclerView.Adapter implements  View.OnClickListener {
    private Context context;
    private   List<DataBean> dataBeanList;

    public Test_Adapter_Ci(Context context, List<DataBean> dataBeans){
        this.context = context;
        this.dataBeanList = dataBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View itemView = LayoutInflater.from(context)
                 .inflate(R.layout.result_wrong_item,parent,false);

         itemView.setOnClickListener(Test_Adapter_Ci.this);

         return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, List payloads) {
        ItemHolder itemHolder= (ItemHolder) holder;
        if (payloads.isEmpty()){
            itemHolder.imageView.setTag(position);
            itemHolder.textView.setTag(position);
            DataBean dataBean = dataBeanList.get(position);
            itemHolder.textView.setText(dataBean.getTextview());
        }
    }


    @Override
    public int getItemCount() {
       return dataBeanList.size();
    }

    public  class ItemHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;

        public ItemHolder(View itemView) {
            super(itemView);
           textView = itemView.findViewById(R.id.tv_wrong_item);
           imageView = itemView.findViewById(R.id.star);

        }
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, int position);
    }
    @Override
    public void onClick(View view) {
        int position = (int)view.getTag();

        if (mOnItemClickListener != null){
            switch (view.getId()){
                case R.id.star:
                    mOnItemClickListener.onClick(view,position);
                    break;
                 default:
                    break;
            }
        }
    }

}
