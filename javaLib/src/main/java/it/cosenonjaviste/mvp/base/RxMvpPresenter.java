package it.cosenonjaviste.mvp.base;


public abstract class RxMvpPresenter<M> extends RxMvpPausablePresenter<M> {

    protected RxMvpView<M> view;

    public void subscribe(RxMvpView<M> view) {
        this.view = view;
        pausableSubscriptions.resume();
        view.update(model);
    }

    @Override public void pause() {
        view = null;
        super.pause();
    }

    public RxMvpView<M> getView() {
        return view;
    }
}