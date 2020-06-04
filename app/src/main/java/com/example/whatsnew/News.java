package com.example.whatsnew;

import java.util.StringJoiner;

public class News {
    private String mCategory;
    private String mTitle;
    private String mDate;
    private String mUrl;

    public News(String title, String category,  String date, String url){
        mTitle=title;
        mCategory=category;

        mDate=date;
        mUrl=url;
    }

    public String getTitle(){return this.mTitle;}
    public String getCategory(){return this.mCategory;}
    public String getDate(){return this.mDate;}
    public String getUrl(){return this.mUrl;}
}
