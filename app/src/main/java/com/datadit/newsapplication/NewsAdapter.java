package com.datadit.newsapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {


    private static final String DATE_SEPARATOR = "T";
    private Context context;
    private List<News> newsList;

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    public void refreshNews(List<News> newsList) {
        this.newsList = newsList;
        notifyItemRangeChanged(0, newsList.size());
    }

    public void clear() {
        final int size = newsList.size();
        newsList.clear();
        notifyItemRangeRemoved(0, size);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.news_layout, null);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        final News news = newsList.get(position);
        String newsDate = news.getNewsDate();
        final String title = news.getNewsTitle();
        final String desc = news.getNewsDescription();
        final String name = news.getNewsName();
        final String author = news.getNewsAuthor();
        final String url = news.getNewsURL();
        final String imageUrl = news.getNewsImageUrl();
        if (newsDate.contains(DATE_SEPARATOR)) {
            String[] parts = newsDate.split(DATE_SEPARATOR);
            newsDate = parts[0];
        } else {
            newsDate = "Date Not Available";
        }
        String nSource = "Source: " + name;
        String nDate = "Date: " + newsDate;
        holder.textViewTitle.setText(title);
        holder.textViewName.setText(nSource);
        holder.textViewDate.setText(nDate);
        final String finalNewsDate = newsDate;
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("description", desc);
                intent.putExtra("title", title);
                intent.putExtra("date", finalNewsDate);
                intent.putExtra("name", name);
                intent.putExtra("author", author);
                intent.putExtra("url",url);
                intent.putExtra("imageUrl",imageUrl);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        private View parentView;
        TextView textViewTitle, textViewName, textViewDate;

        public NewsViewHolder(View view) {
            super(view);
            this.parentView = view;
            textViewTitle = view.findViewById(R.id.textViewTitle);
            textViewName = view.findViewById(R.id.textViewName);
            textViewDate = view.findViewById(R.id.textViewDate);
        }
    }
}
