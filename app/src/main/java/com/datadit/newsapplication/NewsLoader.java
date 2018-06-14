package com.datadit.newsapplication;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String queryUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        queryUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (queryUrl == null) {
            return null;
        }

        List<News> newsList = HomeActivity.fetchNewsData(queryUrl);
        return newsList;
    }
}
