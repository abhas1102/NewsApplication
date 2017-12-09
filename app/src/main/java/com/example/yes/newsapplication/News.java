package com.example.yes.newsapplication;

/**
 * Created by yes on 12/8/2017.
 */

public class News {

        private String mUrl;
        private String mHeadline;
        private String mContent;
        private String mImage;
        private String mPublishDate;
        private String mContributor;

    public News(String headline, String content, String publishDate, String url, String image, String contributor){

        mUrl = url;
        mHeadline = headline;
        mContent = content;
        mImage = image;
        mPublishDate = publishDate;
        mContributor = contributor;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public String getContent() {
        return mContent;
    }

    public String getPublishDate() {
        return mPublishDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getImage() {
        return mImage;
    }

    public String getContributor() {
        return mContributor;
    }
}
