package it.cosenonjaviste.mvp.post;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import rx.Observable;

public class PostListPresenter extends RxMvpPresenter<PostListModel> {

    private static final String CATEGORY = "category";

    @Inject WordPressService wordPressService;

    @Inject MvpConfig<PostDetailView, PostDetailPresenter> postDetailMvpConfig;

    @Override public PostListModel createModel(PresenterArgs args) {
        PostListModel postListModel = new PostListModel();
        postListModel.setCategory(args.getObject(CATEGORY));
        return postListModel;
    }

    public void reloadData() {
        Observable<List<Post>> observable = getObservable(0);

        subscribePausable(observable,
                () -> getView().startLoading(model.isEmpty()),
                posts -> {
                    model.done(posts);
                    model.setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                    view.update(model);
                }, throwable -> {
                    model.error(throwable);
                    view.update(model);
                });
    }

    public void goToDetail(Post item) {
        PostDetailPresenter.open(contextBinder, postDetailMvpConfig, item);
    }

    public static PresenterArgs open(ContextBinder contextBinder, Category category) {
        PresenterArgs args = contextBinder.createArgs();
        args.putObject(CATEGORY, category);
        return args;
    }

    public void loadNextPage() {
        int page = calcNextPage(model.size(), WordPressService.POST_PAGE_SIZE);
        Observable<List<Post>> observable = getObservable(page);

        subscribePausable(observable,
                () -> getView().startMoreItemsLoading(),
                posts -> {
                    model.append(posts);
                    model.setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                    view.update(model);
                }, throwable -> {
                    model.error(throwable);
                    view.update(model);
                });
    }

    private Observable<List<Post>> getObservable(int page) {
        Observable<PostResponse> observable;
        Category category = model.getCategory();
        if (category == null) {
            observable = wordPressService.listPosts(page);
        } else {
            observable = wordPressService.listCategoryPosts(category.getId(), page);
        }
        return observable.map(PostResponse::getPosts);
    }

    public static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }

    @Override public PostListView getView() {
        return (PostListView) super.getView();
    }
}
