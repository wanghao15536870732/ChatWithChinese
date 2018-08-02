package com.example.lab.android.nuc.chat.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lab.android.nuc.chat.view.adapter.SearchAdapter;
import com.example.lab.android.nuc.chat.Base.Search.SearchTag;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.utils.views.DragBubbleView;


import java.util.ArrayList;

public class MessageFragment extends Fragment implements SearchView.OnQueryTextListener ,DragBubbleView.OnBubbleStateListener,
    View.OnClickListener{

    private Context mContext;

    private RecyclerView mRecyclerView;
    private ArrayList<SearchTag> mSearchTagArrayList,filteredDataList;
    private SearchAdapter mSearchAdapter;
    private EditText mEditText;

    //消息气泡
    private DragBubbleView mDragBubbleView;


    public static  Fragment newInstance(){
        Bundle bundle = new Bundle();
        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setArguments(bundle);
        return messageFragment;
    }
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_message,container,false);
        initQuestion();
        mOnRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListaner(View v, int position) {
                SearchTag searchTag =  mSearchTagArrayList.get( position );
            }
        };


        mRecyclerView = (RecyclerView) view.findViewById( R.id.card_recycler_view );

        mRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ));
        mSearchAdapter = new SearchAdapter( mSearchTagArrayList,mOnRecyclerviewItemClickListener);

        mRecyclerView.setAdapter( mSearchAdapter );
        mRecyclerView.addItemDecoration( new DividerItemDecoration( getContext(), DividerItemDecoration.VERTICAL ) );
        SearchView mSearchView = (SearchView) view.findViewById( R.id.searchView );
        mSearchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredDataList = (ArrayList<SearchTag>) filter(mSearchTagArrayList, newText);
                mSearchAdapter.setFilter(filteredDataList);
                return true;
            }
        } );
        return view;
    }
    private void initQuestion() {
        mSearchTagArrayList = new ArrayList<>(  );
        mSearchTagArrayList.add(new SearchTag("Abbott","李沙：[图片]",
                "http://p8nssbtwi.bkt.clouddn.com/student_2.jpg",0,"下午 8:29","2"));

        mSearchTagArrayList.add(new SearchTag("李沙","李沙：ni hao,my name is 李沙",
                "http://p8nssbtwi.bkt.clouddn.com/ic_s4.jpg",0,"昨天上午 7:22","1"));

        mSearchTagArrayList.add(new SearchTag("Abraham","Abraham：[图片]",
                "http://p8nssbtwi.bkt.clouddn.com/student_4.jpg",0,"昨天下午 1:12","3"));

        mSearchTagArrayList.add(new SearchTag("Baron","Baron：What？what you say soon？I ...",
                "http://p8nssbtwi.bkt.clouddn.com/student_3.jpg",0,"前天下午 6:01","1"));

        mSearchTagArrayList.add(new SearchTag("Bruno","Bruno：[图片]",
                "http://p8nssbtwi.bkt.clouddn.com/teacher_4.jpg",0,"前天下午 8:29","4"));

        mSearchTagArrayList.add(new SearchTag("Borg","Borg：see you latter.",
                "http://p8nssbtwi.bkt.clouddn.com/student_2.jpg",0,"星期六上午 7:22","9"));

        mSearchTagArrayList.add(new SearchTag("Christopher","Christopher：[图片]",
                "http://p8nssbtwi.bkt.clouddn.com/teacher_6.jpg",0,"星期六下午 1:17","2"));

        mSearchTagArrayList.add(new SearchTag("Derrick","Derrick：Have a dinner？",
                "http://p8nssbtwi.bkt.clouddn.com/teacher_8.jpg",0,"星期六下午 5:15","1"));

        mSearchTagArrayList.add(new SearchTag("Angelina","Angelina：Do you like it ?",
                "http://p8nssbtwi.bkt.clouddn.com/ic_s1.jpeg",0,"星期六晚上 9:42","3"));

        mSearchTagArrayList.add(new SearchTag("Gillian","Gillian：I think you should make ...",
                "http://p8nssbtwi.bkt.clouddn.com/ic_s7.jpeg",0,"星期五上午 8:09","7"));

        mSearchTagArrayList.add(new SearchTag("Derrick","Derrick：where are you now? I ...",
                "http://p8nssbtwi.bkt.clouddn.com/ic_s12.jpeg",0,"星期五下午 6:45","1"));

        mSearchTagArrayList.add(new SearchTag("Louisa","Louisa：Have a dinner？",
                "http://p8nssbtwi.bkt.clouddn.com/ic_s8.jpeg",0,"星期五晚上 7:58","3"));

    };
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filteredDataList = filter(mSearchTagArrayList,newText);
        mSearchAdapter.setFilter( filteredDataList );
        return true;
    }
    private ArrayList<SearchTag> filter(ArrayList<SearchTag> dataList, String newText) {
        newText = newText.toLowerCase();
        String text;
        filteredDataList = new ArrayList<>();
        for(SearchTag dataFromDataList:mSearchTagArrayList){
            text = dataFromDataList.title.toLowerCase();

            if(text.contains(newText)){
                filteredDataList.add(dataFromDataList);
            }
        }
        return filteredDataList;
    }



    @Override
    public void onDrag() {
        Log.e("---> ", "拖拽气泡");
    }

    @Override
    public void onMove() {
        Log.e("---> ", "移动气泡");
    }

    @Override
    public void onRestore() {
        Log.e("---> ", "气泡恢复原来位置");
    }

    @Override
    public void onDismiss() {
        Log.e("---> ", "气泡消失");
    }

    @Override
    public void onClick(View v) {
        mDragBubbleView.reCreate();
    }

    public interface OnRecyclerviewItemClickListener{
        void onItemClickListaner(View v,int position);
    }
}
