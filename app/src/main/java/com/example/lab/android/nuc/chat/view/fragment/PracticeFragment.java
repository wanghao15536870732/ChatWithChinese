package com.example.lab.android.nuc.chat.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab.android.nuc.chat.Practice.Test.Ise_Demo;
import com.example.lab.android.nuc.chat.Practice.Test.Ise_Demo_Sentence;
import com.example.lab.android.nuc.chat.Practice.Test.Ise_Demo_Zh;
import com.example.lab.android.nuc.chat.Practice.Test.Ise_Demo_Zi;
import com.example.lab.android.nuc.chat.R;


public class PracticeFragment extends Fragment implements View.OnClickListener {
    private View root;
    private Button characterButton;
    private Button wordButton;
    private Button sentenceButton;
    private Button systemButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_practice, container, false);
        init();


        return root;
    }

    private void init() {
        characterButton = root.findViewById(R.id.bt_character);
        wordButton = root.findViewById(R.id.bt_word);
        sentenceButton = root.findViewById(R.id.bt_sentence);
        systemButton = root.findViewById(R.id.bt_system);
        characterButton.setOnClickListener(this);
        wordButton.setOnClickListener(this);
        sentenceButton.setOnClickListener(this);
        systemButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_character:
                startActivity(new Intent(root.getContext(), Ise_Demo_Zi.class));
                break;
            case R.id.bt_word:
                startActivity(new Intent(root.getContext(), Ise_Demo.class));
                break;
            case R.id.bt_sentence:
                startActivity(new Intent(root.getContext(), Ise_Demo_Sentence.class));
                break;
            case R.id.bt_system:
                startActivity(new Intent(root.getContext(), Ise_Demo_Zh.class));
                break;

        }

    }
}
