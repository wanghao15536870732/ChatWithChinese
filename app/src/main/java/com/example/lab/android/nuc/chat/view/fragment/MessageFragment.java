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
        mSearchTagArrayList.add(new SearchTag("Abbott","李沙：[图片]",R.drawable.picture_1,0,"下午8:29","2"));
        mSearchTagArrayList.add(new SearchTag("李沙","Abbott：ni hao",R.drawable.picture_2,0,"下午7:22","1"));
        mSearchTagArrayList.add(new SearchTag("Abraham","Baron：[图片]",R.drawable.picture_3,0,"下午1:19","3"));
        mSearchTagArrayList.add(new SearchTag("Baron","Baron：What？",R.drawable.picture_4,0,"下午6:01","10"));
        mSearchTagArrayList.add(new SearchTag("Bruno","Abraham：[图片]",R.drawable.picture_5,0,"下午8:29","14"));
        mSearchTagArrayList.add(new SearchTag("Borg","Baron：see you",R.drawable.picture_6,0,"下午7:22","99+"));
        mSearchTagArrayList.add(new SearchTag("Christopher","Christopher：[图片]",R.drawable.picture_7,0,"下午1:19","2"));
        mSearchTagArrayList.add(new SearchTag("Derrick","Derrick：Have a dinner？",R.drawable.picture_8,0,"下午6:01","1"));

    }
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
