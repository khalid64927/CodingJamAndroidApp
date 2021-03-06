package it.cosenonjaviste.core.category;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import it.codingjam.lifecyclebinder.BindLifeCycle;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.WordPressService;

public class CategoryListViewModel extends RxListViewModel<Void, CategoryListModel> {

    @Inject WordPressService wordPressService;

    @Inject @BindLifeCycle Navigator navigator;

    @Inject public CategoryListViewModel() {
    }

    @NonNull @Override protected CategoryListModel createModel() {
        return new CategoryListModel();
    }

    @Override protected Disposable reloadData(ObservableBoolean loadingSetter, boolean forceFetch) {
        loadingSetter.set(true);
        return wordPressService
                .listCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> loadingSetter.set(false))
                .subscribe(model::done, throwable -> model.error());
    }

    public void goToPosts(int position) {
        Category category = model.get(position);
        navigator.openPostList(PostListArgument.create(category));
    }
}
