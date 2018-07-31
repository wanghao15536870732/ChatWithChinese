package com.example.lab.android.nuc.chat.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.view.activity.CommentActivity;
import com.example.lab.android.nuc.chat.view.activity.ContactActivity;
import com.example.lab.android.nuc.chat.view.adapter.model.NineGridTestModel;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

public class NineGridTest2Adapter extends RecyclerView.Adapter<NineGridTest2Adapter.ViewHolder> {

    private Context mContext;
    private List<NineGridTestModel> mList;
    protected LayoutInflater inflater;
    NineGridTestModel nineGridTestModel;

    public NineGridTest2Adapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from( context );
    }

    public void setList(List<NineGridTestModel> list){
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View convertView = inflater.inflate( R.layout.item_nine_grid,parent,false );
        ViewHolder viewHolder = new ViewHolder( convertView );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        nineGridTestModel = mList.get( position );
        holder.layout.setIsShowAll( nineGridTestModel.isShowAll );
        holder.layout.setUrlList( nineGridTestModel.urlList );
        holder.layout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentActivity.class );
                intent.putExtra("question_name",nineGridTestModel.name);
                intent.putExtra( "question_image",nineGridTestModel.imageUri );
                intent.putExtra( "question_time",nineGridTestModel.time );
                intent.putExtra( "question_detail",nineGridTestModel.detail );
                intent.putExtra( "question_country_image",nineGridTestModel.country_image );
                mContext.startActivity(intent);
            }
        } );

        Glide.with( mContext ).load(nineGridTestModel.imageUri).into( holder.mImageView );
        holder.contact_name.setText( nineGridTestModel.name );
        holder.time.setText( nineGridTestModel.time );
        holder.mImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( mContext, ContactActivity.class );
                intent.putExtra( ContactActivity.CONTACT_IAMGE_ID,nineGridTestModel.image );
                intent.putExtra( ContactActivity.CONTACT_NAME,nineGridTestModel.name );
                mContext.startActivity( intent );
            }
        } );
        holder.question_detail.setText( nineGridTestModel.detail );
        holder.country_picture.setImageResource( nineGridTestModel.country_image );
        holder.comment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );
    }

    @Override
    public int getItemCount() {
        return getListSize( mList );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NineGridTestLayout layout;
        ImageView mImageView,country_picture,comment;
        TextView contact_name,time,question_detail,like_size;
        ShineButton mShineButton;

        public ViewHolder(View itemView) {
            super( itemView );
            layout = (NineGridTestLayout) itemView.findViewById( R.id.layout_nine_grid);
            mImageView = (ImageView) itemView.findViewById( R.id.image );
            contact_name = (TextView) itemView.findViewById( R.id.name );
            time = (TextView) itemView.findViewById( R.id.time );
            question_detail = (TextView) itemView.findViewById( R.id.detail);
            mShineButton = (ShineButton) itemView.findViewById( R.id.po_image1 );
            like_size = (TextView) itemView.findViewById( R.id.like_size );
            country_picture = (ImageView) itemView.findViewById( R.id.country_picture );
            comment = (ImageView) itemView.findViewById( R.id.comment );
        }
    }

    private int getListSize(List<NineGridTestModel> list){
        if (list == null || list.size() == 0){
            return 0;
        }
        return list.size();
    }
}
