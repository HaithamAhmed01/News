package com.example.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news.model.ArticlesItem;

import java.util.List;

public class NewsAdaptar extends RecyclerView.Adapter<NewsAdaptar.ViewHolder> {

    List<ArticlesItem> newList;

    public NewsAdaptar(List<ArticlesItem> newList) {
        this.newList = newList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_news,parent,false);

        return new ViewHolder(view);
    }

    public void changeData(List<ArticlesItem> newList){
        this.newList=newList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticlesItem news=newList.get(position);
        holder.date.setText(news.publishedAt);
        holder.desc.setText(news.description);
        holder.title.setText(news.title);
        Glide.with(holder.itemView)
                .load(news.urlToImage)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return newList==null ?0 :newList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView date;
        protected TextView title;
        protected ImageView image;
        protected TextView desc;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
            date =  rootView.findViewById(R.id.date);
            title =  rootView.findViewById(R.id.title);
            image =  rootView.findViewById(R.id.image);
            desc =  rootView.findViewById(R.id.desc);

        }


    }
}
