package com.example.lab.android.nuc.chat.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.lab.android.nuc.chat.view.activity.Question_Add_Activity;
import com.example.lab.android.nuc.chat.view.activity.Question_detail_Activity;
import com.example.lab.android.nuc.chat.Base.Contacts.UserInfo;
import com.example.lab.android.nuc.chat.Base.Message.Msg;
import com.example.lab.android.nuc.chat.Base.Question.Question;
import com.example.lab.android.nuc.chat.Base.Question.QuestionList;
import com.example.lab.android.nuc.chat.utils.views.RoundRectangleImageView;
import com.example.lab.android.nuc.chat.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class QuestionFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private String question_link, question_title, question_detail;


    private Context mContext;
    private List<Question> mQuestionList = new ArrayList<>();
    private QuestionAdapter mAdapter;
    private RecyclerView mQuestionRecyclerView;
    QuestionList questionList = QuestionList.get(getActivity());
    List<Question> mQuestions = questionList.getQuestions();
    private ImageView back;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    private FloatingActionButton addQuestionBtn;

    public static android.support.v4.app.Fragment newInstance() {
        Bundle bundle = new Bundle();
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions, container, false);
        Bundle bundle = this.getArguments();
        question_link = bundle.getString("link");
        question_title = bundle.getString("title");
        question_detail = bundle.getString("detail");
        //在布局当中找到实例
        mQuestionRecyclerView = (RecyclerView) view.findViewById(R.id.question_recycler_view);
        mQuestionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mQuestionRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshQuestion();
            }
        });
        //添加问题的数据
        initQuestion();
        //更新界面的UI
        updateUI();

        addQuestionBtn = (FloatingActionButton) view.findViewById(R.id.fab_add_question);
        addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Question_Add_Activity.class);
                startActivityForResult(intent, 1);
            }
        });
        mQuestionRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    private class QuestionHolder extends RecyclerView.ViewHolder {
        //提问人头像
        private RoundRectangleImageView question_peo_Image;
        //提问人名字
        private TextView question_peo_Name;
        //问题提出的时间
        private TextView question_time;
        //所在国家的头像
        private CircleImageView country_Image;
        //所在国家的名字
        private TextView country_Name;

        private TextView mTextView;
        CoordinatorLayout cardView;
        LinearLayout itemLayout;

        public QuestionHolder(View itemView) {
            super(itemView);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.question_item_1);
            cardView = (CoordinatorLayout) itemView;
            question_peo_Image = (RoundRectangleImageView) itemView.findViewById(R.id.question_peo_Image);
            question_peo_Name = (TextView) itemView.findViewById(R.id.question_peo_name);
            question_time = (TextView) itemView.findViewById(R.id.add_time);
            country_Image = (CircleImageView) itemView.findViewById(R.id.country_Image);
            country_Name = (TextView) itemView.findViewById(R.id.country_Name);
            mTextView = (TextView) itemView.findViewById(R.id.text_content);
            addQuestionBtn = (FloatingActionButton) itemView.findViewById(R.id.fab_add_question);

        }
    }

    public class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder> {

        private List<Question> mQuestionList;

        public QuestionAdapter(List<Question> questionList) {
            this.mQuestionList = questionList;
        }

        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.question_item, parent, false);
            final QuestionHolder holder = new QuestionHolder(view);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Question question = mQuestionList.get(position);
                    Intent intent = new Intent(getContext(), Question_detail_Activity.class);
                    intent.putExtra(Question_detail_Activity.Question_name, question.getName());
                    intent.putExtra(String.valueOf(Question_detail_Activity.QUQESTION_NUMBER), question.getQuestionNumber());
                    intent.putExtra(Question_detail_Activity.Question_image_ID, question.getPeople_imageId());
                    intent.putExtra(Question_detail_Activity.Question_detail_text, question.getQuestion_detail());
                    getContext().startActivity(intent);

                }
            });
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Question question = mQuestionList.get(position);
                    Intent intent = new Intent(getContext(), Question_detail_Activity.class);
                    intent.putExtra(Question_detail_Activity.Question_name, question.getName());
                    intent.putExtra(Question_detail_Activity.Question_image_ID, question.getPeople_imageId());
                    intent.putExtra(Question_detail_Activity.Question_detail_text, question.getQuestion_detail());
                    getContext().startActivity(intent);

                }
            });
            return holder;
        }

        @SuppressLint("CheckResult")
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onBindViewHolder(@NonNull final QuestionHolder holder, final int position) {

            //更新TextView的Text

            final Question question = mQuestionList.get(position);

//                Glide.with( getActivity() ).load( mQuestionList.get( position ).getPeople_imageId() ).
//                        into( holder.question_peo_Image );
//            holder.question_peo_Image.setImageResource(question.getPeople_imageId());
//            Glide.with(getContext()).load(getContext().getDrawable(question.getPeople_imageId())).into(holder.question_peo_Image);
//            Picasso.with( getContext() ).load( String.valueOf( getContext().getDrawable( question.getPeople_imageId()) ) ).into( holder.question_peo_Image );
            holder.country_Image.setImageResource(question.getCountry_imageId());
            holder.question_peo_Name.setText(question.getName());
            holder.question_time.setText(question.getTime());
            holder.country_Name.setText(question.getCountry());
            holder.mTextView.setText(question.getQuestion_detail());
        }

        @Override
        public int getItemCount() {
            return mQuestionList.size();
        }

    }

    //绑定RecyclerView和Adapter
    private void updateUI() {
        mAdapter = new QuestionAdapter(mQuestions);
        mQuestionRecyclerView.setAdapter(mAdapter);
        getQuestion();
    }


    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());
    private String str = formatter.format(curDate);

    private Msg mMsg;
    private String questionDetial;
    private String questionTitle;
    private String questionTime;

    private void getQuestion() {
        OkGo.<String>post("http://47.95.7.169:8080/getQuestion")
                .tag(this)
                .isMultipart(true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("return_question", "all" + response.body());
                        List<com.example.lab.android.nuc.chat.Base.JSON.Question> questiion_list = JSON.parseArray(response.body(), com.example.lab.android.nuc.chat.Base.JSON.Question.class);
                        for (int i = 0; i < questiion_list.size(); i++) {
                            com.example.lab.android.nuc.chat.Base.JSON.Question question = questiion_list.get(i);
                            Log.i("return_question", "all" + question);
                            questionTitle = question.getQuestionTitle();
                            questionDetial = question.getQuestionDetail();
                            questionTime = question.getQuestionTime();
                            Log.i("return_question", "question_detail" + mMsg.getQuestionDetail());
                            Log.i("return_question", "question_title" + mMsg.getQuestionTitle());
                            Log.i("return_question", "question_time" + mMsg.getQuestionTime());
                            Question question_1 = new Question(UserInfo.name, R.drawable.apple_pic, str,
                                    UserInfo.country, R.drawable.country, questionDetial, questionTitle);
                            question.setQuestionNumber(1);
                            mQuestions.add(0, question_1);
                            mAdapter.notifyItemInserted(0);
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                });

    }

    //为题数据的添加
    private void initQuestion() {
        Question question_1 = new Question("李沙", R.drawable.picture_1, "十天前",
                "美国", R.drawable.country_1, "Are you making progress?", "");
        mQuestions.add(question_1);
        Question question_2 = new Question("Bruno", R.drawable.picture_2, "三天前",
                "法国", R.drawable.country_2, " What happened?”? ", "");
        mQuestions.add(question_2);
        Question question_3 = new Question("Borg", R.drawable.picture_13, "刚刚",
                "巴西", R.drawable.country_3, "Have you got it?", "");
        mQuestions.add(question_3);
        Question question_4 = new Question("Christopher", R.drawable.picture_4, "一天前",
                "中国", R.drawable.country, "与您合作很愉快。 in English speaking?", "");
        mQuestions.add(question_4);

    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (com.bumptech.glide.util.Util.isOnMainThread()) {
//            Glide.with(mContext).pauseRequests();
//        }
//    }

    public void refreshQuestion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getQuestion();
                        initQuestion();
                        mAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();

    }
}
