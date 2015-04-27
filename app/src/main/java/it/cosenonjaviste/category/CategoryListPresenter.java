package it.cosenonjaviste.category;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.utils.PresenterScope;
import rx.Observable;

@PresenterScope
public class CategoryListPresenter extends RxMvpPresenter<CategoryListModel> {

    private WordPressService wordPressService;

    private boolean loadStarted;

    @Inject public CategoryListPresenter(SchedulerManager schedulerManager, WordPressService wordPressService) {
        super(schedulerManager);
        this.wordPressService = wordPressService;
    }

    @Override public void subscribe() {
        super.subscribe();
        if (model.isEmpty() && !loadStarted) {
            loadData();
        }
    }

    public void loadData() {
        Observable<List<Category>> observable = wordPressService
                .listCategories()
                .map(CategoryResponse::getCategories);

        subscribe(observable,
                () -> {
                    loadStarted = true;
                    getView().startLoading();
                },
                posts -> {
                    model.done(posts);
                    view.update(model);
                }, throwable -> {
                    model.error(throwable);
                    view.update(model);
                });
    }

    public void goToPosts(int position) {
        Category category = model.get(position);
        getView().open(PostListFragment.class, new PostListModel(category));
    }

    @Override public CategoryListFragment getView() {
        return (CategoryListFragment) super.getView();
    }
}
