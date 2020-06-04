package com.example.whatsnew;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {
    private String mUrl;

    public NewsLoader(Context context, String url){
        super(context);
        this.mUrl=url;
    }

    @Nullable
    @Override
    public ArrayList<News> loadInBackground() {
        if(mUrl==null){
            return null;
        }

        ArrayList<News> newsList = QueryUtils.getNewsFromUrl(mUrl);
        return newsList;
    }

    @Override
    protected void onStartLoading() {
        super.forceLoad();
    }
}
