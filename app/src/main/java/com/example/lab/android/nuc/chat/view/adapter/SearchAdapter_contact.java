package com.example.lab.android.nuc.chat.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab.android.nuc.chat.Base.Contacts.Contact;
import com.example.lab.android.nuc.chat.R;

import java.util.ArrayList;

public class SearchAdapter_contact extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Contact> mArrayList;
    public static final int WithoutImage = 1,WithImage = 0;

    public SearchAdapter_contact(ArrayList<Contact> arrayList){
        this.mArrayList = arrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate( R.layout.fragment_contacts_item,parent,false);
        ViewHolder imageViewHolder = new ViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).contact_image.setImageResource(mArrayList.get(position).getImagrId());
        ((ViewHolder) holder).contact_name.setText(mArrayList.get(position).getName());
        ((ViewHolder) holder).contact_language.setText(mArrayList.get(position).getMother_language());
        ((ViewHolder) holder).contact_level.setText(mArrayList.get(position).getLanguage_level());
        ((ViewHolder) holder).contact_target.setText(mArrayList.get(position).getTarget());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView soontext;
        ImageView contact_image;
        TextView contact_name;
        TextView contact_target;
        TextView contact_language;
        TextView contact_level;


        public ViewHolder(View itemView) {
            super(itemView);
            contact_image = (ImageView) itemView.findViewById( R.id.contact_image );
            contact_name = (TextView) itemView.findViewById( R.id.contact_name_text );
            contact_target = (TextView) itemView.findViewById( R.id.learn_language );
            contact_language = (TextView) itemView.findViewById( R.id.mother_tongue );
            contact_level = (TextView) itemView.findViewById( R.id.language_level );

        }
    }

    public void setFilter(ArrayList<Contact> FilteredDataList) {
        mArrayList = FilteredDataList;
        notifyDataSetChanged();
    }

}