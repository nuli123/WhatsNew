package com.example.whatsnew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> newsList){
        super(context, 0, newsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if(convertView==null){
            listView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        News news = (News) getItem(position);

        TextView title = (TextView) listView.findViewById(R.id.title);
        title.setText(news.getTitle());

        TextView category = (TextView) listView.findViewById(R.id.category);
        category.setText(news.getCategory());

        TextView date = (TextView) listView.findViewById(R.id.date);
        date.setText(news.getDate());

        return listView;
    }
}
