package com.example.lab.android.nuc.chat.view.activity;

import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.chat.Base.bean.CommentBean;
import com.example.lab.android.nuc.chat.Base.bean.CommentDetailBean;
import com.example.lab.android.nuc.chat.Base.bean.ReplyDetailBean;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.utils.views.CommentExpandableListView;
import com.example.lab.android.nuc.chat.view.adapter.CommentExpandAdapter;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CommentActivity";

    private CircleImageView image;
    private TextView name,detail,time,title;
    private RoundedImageView country;



    private android.support.v7.widget.Toolbar toolbar;
    private TextView bt_comment;
    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter adapter;
    private CommentBean commentBean;
    private List<CommentDetailBean> commentsList;
    private BottomSheetDialog dialog;
    private String testJson = "{\n" +
            "\t\"code\": 1000,\n" +
            "\t\"message\": \"查看评论成功\",\n" +
            "\t\"data\": {\n" +
            "\t\t\"total\": 3,\n" +
            "\t\t\"list\": [{\n" +
            "\t\t\t\t\"id\": 42,\n" +
            "\t\t\t\t\"nickName\": \"李想\",\n" +
            "\t\t\t\t\"userLogo\": \"http://p8nssbtwi.bkt.clouddn.com/teacher_3.jpg\",\n" +
            "\t\t\t\t\"content\": \"\n" +
            "我的建议还是重在积累。我曾经试过每次把一个俄语单词翻译好之后，再用翻译好的英文去找对应的英文，有时候也还能直接通过听一个陌生的俄语单词，直接通过它的发音知道它的意思。比如有一次我们的俄语外教跟我们说「сюрприз」,说实话，我之前真不知道它的意思，我甚至以为是我们的外教在用英语跟我们解释，但一听它的发音我就知道，噢，这是「惊喜」！，为什么？它的读音直接让人联想到「surprise」,但后来一查，事实证明这就是一个英俄互译的例子。在这里我觉得可以向大家推荐一款神奇的词典”欧路词典“，神级词典，支持各种小众语言和英语的互译，也支持灵格斯的导入。\n" +
            "\n\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"三分钟前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"Zoe\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\t\"commentId\": \"42\",\n" +
            "\t\t\t\t\t\"content\": \"因为一直觉着自己俄语水平比较水配不上专八证书，所以默默自称砖八，见笑啦~互助阶段也是深有体会，谢谢推荐的软件，知乎没玩多久，第一次被艾特，有点小激动，嘿嘿\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"一个小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 41,\n" +
            "\t\t\t\t\"nickName\": \"Brandon\",\n" +
            "\t\t\t\t\"userLogo\": \"http://p8nssbtwi.bkt.clouddn.com/teacher_4.jpg\",\n" +
            "\t\t\t\t\"content\": \"我们老师说 学俄语的初步是英语打俄语\n" +
            "然后是俄语打英语\n" +
            "最后是互不打架 自由切换\n" +
            "三个阶段 只是你处的阶段不一样 好好学 会分开运用自如的\n" +
            "如果还是混淆的话 只能说火候未到啦~\n" +
            "学俄语的孩纸都是勇士\n" +
            "加油\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"一天前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"commentId\": \"41\",\n" +
            "\t\t\t\t\t\"content\": \"英语的cosmos和俄语的космос都来源于希腊语，它俩的关系就像两兄弟，弟弟和哥哥长得像是因为他们的特征都遗传自一个爹，而不是因为哥哥遗传给了弟弟。\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"三小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\"nickName\": \"Dennis\",\n" +
            "\t\t\t\t\"userLogo\": \"http://p8nssbtwi.bkt.clouddn.com/teacher_5.jpg\",\n" +
            "\t\t\t\t\"content\": \"笨语言这个东西一定要多用，不要害羞，多张口，生活里、专业上，把能想到的东西都试着用俄语表达试试。养成这一个习惯，你的俄语肯定会更快入门。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 0,\n" +
            "\t\t\t\t\"createDate\": \"三天前\",\n" +
            "\t\t\t\t\"replyList\": []\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment );
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById( R.id.toolbar);
        expandableListView = (CommentExpandableListView) findViewById(R.id.detail_page_lv_comment);
        bt_comment = (TextView) findViewById(R.id.detail_page_do_comment);
        bt_comment.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("问题详情");
        commentsList = generateTestData();
        initExpandableListView(commentsList);
        image = (CircleImageView) findViewById( R.id.detail_page_userLogo );
        name = (TextView) findViewById( R.id.detail_page_userName );
        time = (TextView) findViewById( R.id.detail_page_time );
        detail = (TextView) findViewById( R.id.detail_page_story );
        country = (RoundedImageView) findViewById( R.id.country_comment );
        title = (TextView) findViewById( R.id.detail_page_title );
        Intent intent = getIntent();
        String NAME = intent.getStringExtra( "comment_name" );
        String IMAGE = intent.getStringExtra( "comment_image" );
        String TIME = intent.getStringExtra( "comment_time" );
        String DETAIL = intent.getStringExtra( "comment_detail" );
        int COUNTRY_IMAGE = intent.getIntExtra( "comment_country_image" ,1);
        name.setText( NAME );
        time.setText( TIME );
        detail.setText( DETAIL );
        Glide.with( this ).load( IMAGE ).into( image );
        Log.e( "wang",String.valueOf(  COUNTRY_IMAGE ));
        Glide.with( this ).load( COUNTRY_IMAGE ).into( country );
//        country.setImageResource( COUNTRY_IMAGE );
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetailBean> commentList){
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(this, commentList);
        expandableListView.setAdapter(adapter);
        for(int i = 0; i<commentList.size(); i++){
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(groupPosition);
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(CommentActivity.this,"点击了回复", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });

    }

    /**
     * func:生成测试数据
     * @return 评论数据
     */
    private List<CommentDetailBean> generateTestData(){
        Gson gson = new Gson();
        commentBean = gson.fromJson(testJson, CommentBean.class);
        List<CommentDetailBean> commentList = commentBean.getData().getList();
        return commentList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.detail_page_do_comment){

            showCommentDialog();
        }
    }

    /**
     * func:弹出评论框
     */
    private void showCommentDialog(){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    CommentDetailBean detailBean = new CommentDetailBean("小明", commentContent,"刚刚");
                    adapter.addTheCommentData(detailBean);
                    Toast.makeText(CommentActivity.this,"评论成功", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(CommentActivity.this,"评论内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor( Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor( Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * func:弹出回复框
     */
    private void showReplyDialog(final int position){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){

                    dialog.dismiss();
                    ReplyDetailBean detailBean = new ReplyDetailBean("小红",replyContent);
                    adapter.addTheReplyData(detailBean, position);
                    expandableListView.expandGroup(position);
                    Toast.makeText(CommentActivity.this,"回复成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CommentActivity.this,"回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor( Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor( Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

}
