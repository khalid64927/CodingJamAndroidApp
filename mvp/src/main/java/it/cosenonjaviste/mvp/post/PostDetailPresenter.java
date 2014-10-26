package it.cosenonjaviste.mvp.post;

import javax.inject.Inject;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;

public class PostDetailPresenter extends RxMvpPresenter<PostDetailModel> {

    public static final String POST = "Post";

    @Inject public PostDetailPresenter() {
    }

    public static void open(ContextBinder contextBinder, Post item) {
        PresenterArgs args = populateArgs(contextBinder.createArgs(), item);
        contextBinder.startNewActivity(PostDetailMvpConfig.class, args);
    }

    @Override public PostDetailModel createModel(PresenterArgs args) {
        PostDetailModel model = new PostDetailModel();
        model.setPost(args.getObject(POST));
        return model;
    }

    public static PresenterArgs populateArgs(PresenterArgs args, Post item) {
        args.putObject(POST, item);
        return args;
    }
}
