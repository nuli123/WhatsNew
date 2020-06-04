package com.example.whatsnew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    private final static String LOGGING_TAG="Main Activity";
    private final static String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2019-01-01&api-key=f8592cfc-0b89-4253-b777-d33f04633dc0";
    private static final int NEWS_LOADER_ID = 1;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<News> newsList = new ArrayList<>();
        ListView list = (ListView) findViewById(R.id.list);
        mAdapter = new NewsAdapter(MainActivity.this, newsList);
        list.setAdapter(mAdapter);
        list.setEmptyView(findViewById(R.id.empty_view));

        // if each news item is clicked, then open the news webpage (url)
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) mAdapter.getItem(position);
                Uri newsUri = Uri.parse(news.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the loaderManager, in order to interact with loaders
            LoaderManager loaderManager = LoaderManager.getInstance(this);
            // Initialize the loader, pass the int ID constant and pass in null for the bundle
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            TextView emptyView = findViewById(R.id.empty_view);
            emptyView.setText(getString(R.string.no_news));
        }

    };

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i(LOGGING_TAG, "onCreatedLoader() is called");
        return new NewsLoader(MainActivity.this, GUARDIAN_REQUEST_URL );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> newsList) {
        Log.i(LOGGING_TAG, "onLoadFinished() is called");
        mAdapter.clear();
        if(newsList!=null && !newsList.isEmpty()){
            mAdapter.addAll(newsList);
        } else{
            TextView emptyView = findViewById(R.id.empty_view);
            emptyView.setText(getString(R.string.no_news));
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        Log.i(LOGGING_TAG, "onLoaderReset() is called");
        mAdapter.clear();
    }


}
