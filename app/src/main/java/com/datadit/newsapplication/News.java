package com.datadit.newsapplication;

public class News {
    private String newsTitle;
    private String newsURL;
    private String newsDate;
    private String newsAuthor;
    private String newsName;
    private String newsDescription;
    private String newsImageUrl;


    public News(String newsTitle, String newsDate, String newsName, String newsURL, String newsAuthor, String newsDescription, String newsImageUrl) {
        this.newsTitle = newsTitle;
        this.newsName = newsName;
        this.newsDate = newsDate;
        this.newsURL = newsURL;
        this.newsAuthor = newsAuthor;
        this.newsDescription = newsDescription;
        this.newsImageUrl = newsImageUrl;

    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public String getNewsName() {
        return newsName;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }
}
