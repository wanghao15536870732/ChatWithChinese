package com.example.lab.android.nuc.chat.Communicatiom.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.Communicatiom.bean.ChatMessageBean;
import com.example.lab.android.nuc.chat.Communicatiom.utils.EmotionHelper;


public class ChatItemTextHolder extends ChatItemHolder {

    protected TextView contentView;

    public ChatItemTextHolder(Context context, ViewGroup root, boolean isLeft) {
        super(context, root, isLeft);
    }

    @Override
    public void initView() {
        super.initView();
        if (isLeft) {
            conventLayout.addView(View.inflate(getContext(), R.layout.chat_item_left_text_layout, null));
            contentView = (TextView) itemView.findViewById(R.id.chat_left_text_tv_content);
        } else {
            conventLayout.addView(View.inflate(getContext(), R.layout.chat_item_right_text_layout, null));
            contentView = (TextView) itemView.findViewById(R.id.chat_right_text_tv_content);
        }
    }

    @Override
    public void bindData(Object o) {
        super.bindData(o);
        ChatMessageBean message = (ChatMessageBean) o;
        if (message != null) {
//        ChatMessageBean textMessage = (ChatMessageBean) message;
            contentView.setText( EmotionHelper.replace(getContext(), message.getUserContent()));
        }
    }
}
