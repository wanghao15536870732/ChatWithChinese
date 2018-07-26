package com.example.lab.android.nuc.chat.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DummyFragment extends Fragment{

    public static final String ARG_SECTION_NUMBER = "selection_number";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setGravity(Gravity.START);

        //获取床架你该Fragment时传入的参数Bundle
        Bundle args = getArguments();
        //设置TextView显示的的文本
        textView.setText(args.getInt(ARG_SECTION_NUMBER) + "");
        textView.setTextSize(30);
        //返回该TextView
        return textView;
    }
}
