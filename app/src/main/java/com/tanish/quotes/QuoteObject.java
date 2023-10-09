package com.tanish.quotes;

public class QuoteObject {
    private int Img;
    private String QId;
    private String Quote;
    private String Author;
    private String Tag;
    private Boolean liked=false;

    public QuoteObject() {
    }
    public QuoteObject(int img, String author){
        Img=img;
        Author = author;
    }


    public QuoteObject(int img, String qId, String quote, String author, String tag) {
        QId = qId;
        Quote = quote;
        Author = author;
        Img=img;
        Tag = tag;

    }

    public QuoteObject(String id, String quote, String author) {
        QId = id;
        Quote = quote;
        Author = author;
    }

    public void setQId(String id) {
        QId = id;
    }

    public void setQuote(String quote) {
        Quote = quote;
    }
    public void setLiked(Boolean like) {
        liked = like;
    }


    public void setAuthor(String author) {
        Author = author;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
    public void setImg(int img) {
        Img = img;
    }

    public String getQId() {
        return QId;
    }
    public Boolean getLiked() {
        return liked;
    }
    public int getImg() {
        return Img;
    }

    public String getQuote() {
        return Quote;
    }

    public String getAuthor() {
        return Author;
    }

    public String getTag() {
        return Tag;
    }


}
