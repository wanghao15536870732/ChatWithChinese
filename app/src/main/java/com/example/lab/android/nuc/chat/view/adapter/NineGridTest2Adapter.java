package com.example.lab.android.nuc.chat.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.chat.R;
import com.example.lab.android.nuc.chat.view.adapter.model.NineGridTestModel;

import java.util.List;

public class NineGridTest2Adapter extends RecyclerView.Adapter<NineGridTest2Adapter.ViewHolder> {

    private Context mContext;
    private List<NineGridTestModel> mList;
    protected LayoutInflater inflater;

    public NineGridTest2Adapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from( context );
    }

    public void setList(List<NineGridTestModel> list){
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = inflater.inflate( R.layout.item_nine_grid,parent,false );
        ViewHolder viewHolder = new ViewHolder( convertView );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NineGridTestModel nineGridTestModel = mList.get( position );
        holder.layout.setIsShowAll( nineGridTestModel.isShowAll );
        holder.layout.setUrlList( nineGridTestModel.urlList );
        Glide.with( mContext ).load(nineGridTestModel.image).into( holder.mImageView );
        holder.contact_name.setText( nineGridTestModel.name );
        holder.time.setText( nineGridTestModel.time );
    }

    @Override
    public int getItemCount() {
        return getListSize( mList );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NineGridTestLayout layout;
        ImageView mImageView;
        TextView contact_name;
        TextView time;

        public ViewHolder(View itemView) {
            super( itemView );
            layout = (NineGridTestLayout) itemView.findViewById( R.id.layout_nine_grid);
            mImageView = (ImageView) itemView.findViewById( R.id.image );
            contact_name = (TextView) itemView.findViewById( R.id.name );
            time = (TextView) itemView.findViewById( R.id.time );
        }
    }

    private int getListSize(List<NineGridTestModel> list){
        if (list == null || list.size() == 0){
            return 0;
        }
        return list.size();
    }
}
