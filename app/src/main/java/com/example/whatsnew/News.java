package com.example.whatsnew;

import java.util.StringJoiner;

public class News {
    private String mCategory;
    private String mTitle;
    private String mDate;
    private String mUrl;
    private String mAuthor;

    public News(String title, String category,  String date, String url, String author){
        mTitle=title;
        mCategory=category;
        mDate=date;
        mUrl=url;
        mAuthor=author;
    }

    public String getTitle(){return this.mTitle;}
    public String getCategory(){return this.mCategory;}
    public String getDate(){return this.mDate;}
    public String getUrl(){return this.mUrl;}
    public String getAuthor(){return this.mAuthor;}
}
