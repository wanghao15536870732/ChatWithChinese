package com.example.lab.android.nuc.chat.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.chat.view.activity.ContactActivity;
import com.example.lab.android.nuc.chat.view.adapter.SearchAdapter_contact;
import com.example.lab.android.nuc.chat.Base.contacts.Contact;
import com.example.lab.android.nuc.chat.Base.contacts.UserInfo;
import com.example.lab.android.nuc.chat.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class ContactFragment extends Fragment implements SwipeRefreshLayout.OnClickListener,SearchView.OnQueryTextListener{
    private static String UserID = "11";
    private Context mContext;
    private static final String PAGE = "page";
    private static String country_selector = "国家";
    private static int OPENAGAIN = 0;
    private Button countryButton;
    //TabLayout的页数
    private int mPAge;
    private ArrayList<Contact> filteredDataList;
    private SearchAdapter_contact mSearchAdapter;
    private EditText mEditText;
    //先获取联系人列表
    private  ArrayList<Contact> contactList = new ArrayList<Contact>(  );
    //下拉刷新
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private UserInfo info;
    private String name;
    private String studyLanguage;
    private String nativeLanguage;
    private String languageLevel;

    public static android.support.v4.app.Fragment newInstance(int page){
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE,page);
        ContactFragment homeFragment = new ContactFragment();
        //用于防止因旋转而造成的Fragment重建
        homeFragment.setArguments(bundle);
        //返回一个Fragment
        return homeFragment;
    }
    private RecyclerView mRecyclerView;
    private ContactAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts,container,false);
        countryButton = (Button) view.findViewById( R.id.country_selector );
        mRecyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler_view_message);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration( new DividerItemDecoration( getContext(),DividerItemDecoration.VERTICAL ) );
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_contact);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContacts();
            }
        });
        countryButton.setOnClickListener( this );
        mSearchAdapter = new SearchAdapter_contact( contactList);
        SearchView searchView = (SearchView) view.findViewById( R.id.searchView_message );
        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredDataList = (ArrayList<Contact>) filter(contactList, newText);
                mSearchAdapter.setFilter(filteredDataList);
                return true;
            }
        } );
        OPENAGAIN = 1;
        //在创建View的同时更新UI
        updateUI();
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPAge = getArguments().getInt(PAGE);
        requestcontact();
        refreshContacts();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        filteredDataList = filter(contactList,newText);
        mSearchAdapter.setFilter( filteredDataList );
        return true;
    }
    private class ContactsHolder extends RecyclerView.ViewHolder{
        View contactView;
        //加载文字
        public TextView mTextView;
        //加载图片
        public ImageView mImageView;
        private Button counrtyButton;
        private ImageView onffline;
        //在学语言的水平
        private TextView language_level;
        //联系人的母语
        private TextView mother_language;
        //正在学习的语言
        private TextView learning_language;
        @SuppressLint({"ResourceType", "CheckResult"})
        public ContactsHolder(View itemView) {
            super(itemView);
            contactView = itemView;
            mTextView = (TextView) itemView.findViewById(R.id.contact_name_text);
            mImageView = (ImageView) itemView.findViewById(R.id.contact_image);
            onffline = (ImageView) itemView.findViewById( R.id.contact_online );
            language_level = (TextView) itemView.findViewById( R.id.language_level );
            mother_language = (TextView) itemView.findViewById( R.id.mother_tongue );
            learning_language = (TextView) itemView.findViewById( R.id.learn_language );
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactsHolder>{
        private List<Contact> mContacts;
        public ContactAdapter(List<Contact> contactList){
            mContacts = contactList;
        }
        @NonNull
        @Override
        public ContactsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (mContext == null){
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_contacts_item,parent,false);
            final ContactsHolder holder = new ContactsHolder(view);

            holder.contactView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取点击位置的position
                    int position = holder.getAdapterPosition();
                    Contact contact = contactList.get(position);
                    Intent intent = new Intent(mContext, ContactActivity.class);
                    intent.putExtra(ContactActivity.CONTACT_NAME,contact.getName());
                    intent.putExtra(ContactActivity.CONTACT_IAMGE_ID,contact.getImagrId());
                    intent.putExtra( ContactActivity.USERID,UserID );
                    intent.putExtra( "nativeLanguage",contact.getMother_language() );
                    intent.putExtra( "learnLanguage",contact.getLearn_language() );
                    intent.putExtra(  "languageLevel",contact.getLanguage_level());
                    mContext.startActivity(intent);
                }
            });
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Contact contact = contactList.get(position);
                }
            });
            return holder;
        }
        @Override
        public void onBindViewHolder(@NonNull ContactsHolder holder, int position) {
            Contact contact = mContacts.get(position);
            Glide.with(getContext()).load(getContext().getDrawable(contact.getImagrId())).into(holder.mImageView);
//            holder.mImageView.setImageResource(contact.getImagrId());
            holder.mTextView.setText(contact.getName());
            holder.onffline.setImageResource(contact.getLine() );
            holder.language_level.setText( contact.getLanguage_level() );
            holder.learning_language.setText( contact.getLearn_language() );
            holder.mother_language.setText( contact.getMother_language() );

        }
        @Override
        public int getItemCount() {
            return mContacts.size();
        }
    }

    //创建方法用于更新UI
    private void updateUI() {
        //绑定联系人的adapter
        mAdapter = new ContactAdapter(contactList);
        mRecyclerView.setAdapter(mAdapter);

    }
    private Contact[] mContacts= {
            new Contact("Abbott",R.drawable.picture_1,R.drawable.ic_dot_24dp,
                    "汉语","英语","初级水平"),
            new Contact("李沙",R.drawable.picture_2,R.drawable.ic_off_dot_24dp,
                    "法语","汉语","中级水平"),
            new Contact("Abraham",R.drawable.picture_3,R.drawable.ic_off_dot_24dp,
                    "韩语","汉语","高级水平"),
            new Contact("Baron",R.drawable.picture_4,R.drawable.ic_dot_24dp,
                    "日语","俄语","初级水平"),
            new Contact("Bruno",R.drawable.picture_5,R.drawable.ic_off_dot_24dp,
                    "阿拉伯语","英语","中级水平"),
            new Contact("Borg",R.drawable.picture_6,R.drawable.ic_dot_24dp,
                    "俄语","汉语","高级水平"),
            new Contact("Christopher",R.drawable.picture_7,R.drawable.ic_dot_24dp,
                    "汉语","日语","初级水平"),
            new Contact("Derrick",R.drawable.picture_8,R.drawable.ic_off_dot_24dp,
                    "汉语","韩语","中级水平")
    };
    private void refreshContacts(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //线程沉睡以便看到刷新的效果
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contactList.clear();
                        Contact contact = new Contact( name,R.drawable.icon,R.drawable.ic_dot_24dp,
                                studyLanguage,nativeLanguage,languageLevel);
                        contactList.add( 0,contact );
                        mAdapter.notifyItemInserted( 0 );
                        mAdapter.notifyItemChanged(  0,0);
                        mRecyclerView.getLayoutManager().scrollToPosition( 0 );
                        for (int i = 0; i < 10; i++) {
                            Random random = new Random(  );
                            int index = random.nextInt(mContacts.length);
                            contactList.add(mContacts[index]);
                        }
                        mAdapter.notifyDataSetChanged();
                        //刷新结束后隐藏进度条
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


    private void requestcontact(){
        OkGo.<String>post( "http://47.95.7.169:8080/getUserInfo")
                .tag( this )
                .isMultipart( true)
                .params( "UserID",UserID)
                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i( "return","all"  + response.body());
                        info = JSON.parseObject(response.body(),UserInfo.class  );
                        name = info.getName();
                        studyLanguage = info.getStudyLanguage();
                        nativeLanguage = info.getNativeLanguage();
                        languageLevel = info.getLanguageLevel();
                        Log.i( "return", "name : " + info.getName() );
                        Log.i( "return","email : " + info.getEamil() );
                        Log.i( "return","id : " + info.getUserID() );
                        UserID += 1;

                    }
                } );

    }
//    private void reinitContacts(List<Contact>  contactList){
//        contactList.clear();
//        Contact contact = new Contact( name,R.drawable.picture,R.drawable.ic_dot_24dp,
//                studyLanguage,nativeLanguage,languageLevel);
//        contactList.add( 0,contact );
//        mAdapter.notifyItemInserted( 0 );
//        mRecyclerView.getLayoutManager().scrollToPosition( 0 );
//        for (int i = 0; i < 10; i++) {
//            Random random = new Random(  );
//            int index = random.nextInt(mContacts.length);
//            contactList.add(mContacts[index]);
//        }
//
//    }

    private ArrayList<Contact> filter(ArrayList<Contact> dataList, String newText) {
        newText = newText.toLowerCase();
        String text_1,text_2,text_3;
        filteredDataList = new ArrayList<>( );
        for(Contact dataFromDataList:contactList){
            text_1 = dataFromDataList.getName().toLowerCase();

            if(text_1.contains(newText) ){
                filteredDataList.add(dataFromDataList);
            }
        }
        return filteredDataList;
    }

    /**
     * 显示单选对话框
     */
    public void showSingleChioceDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("选择国家:");
        builder.setIcon(R.drawable.logo);
        final String[] items = new String[]{"中国","美国",
                "日本","韩国","法国","俄国","泰国","阿拉伯","越南","希腊"};
        builder.setSingleChoiceItems(items, 2, new DialogInterface.OnClickListener() {/*设置单选条件的点击事件*/
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), items[which], Toast.LENGTH_SHORT).show();
                country_selector = items[which];
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
                countryButton.setText( country_selector );

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "CANCEL", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.country_selector:
                showSingleChioceDialog( getView() );
                break;
            default:
                break;
        }
    }

}

