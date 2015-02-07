package it.cosenonjaviste.model;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class Post {
    long id;
    Author author;
    String title;
    Date date;
    String url;
    String excerpt;

    public long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getExcerpt() {
        return excerpt;
    }
}
