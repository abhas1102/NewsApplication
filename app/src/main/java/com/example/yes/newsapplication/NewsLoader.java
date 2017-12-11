package com.example.yes.newsapplication;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import static com.example.yes.newsapplication.MainActivity.LOG_TAG;

/**
 * Created by yes on 12/4/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        Log.i(LOG_TAG, "NewsLoader executed");
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading executed");
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<News> Books = NewsQueryData.fetchBooksData(mUrl);

        return Books;
    }
}
