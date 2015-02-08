package it.cosenonjaviste.post;

import org.parceler.Parcel;

import it.cosenonjaviste.lib.mvp.OptionalList;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;

@Parcel
public class PostListModel {

    OptionalList<Post> items = new OptionalList<>();

    Category category;

    boolean moreDataAvailable;

    Author author;

    public PostListModel() {
    }

    public PostListModel(Category category) {
        this.category = category;
    }

    public PostListModel(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isMoreDataAvailable() {
        return moreDataAvailable;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.moreDataAvailable = moreDataAvailable;
    }

    public Author getAuthor() {
        return author;
    }

    public OptionalList<Post> getItems() {
        return items;
    }
}