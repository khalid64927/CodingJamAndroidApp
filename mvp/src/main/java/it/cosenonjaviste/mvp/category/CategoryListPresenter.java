package it.cosenonjaviste.mvp.category;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.ListPresenter;
import rx.Observable;

public class CategoryListPresenter extends ListPresenter<Category> {

    @Inject WordPressService wordPressService;

    @Override protected Observable<List<Category>> getObservable(int page) {
        return wordPressService.listCategories().map(CategoryResponse::getCategories);
    }
}