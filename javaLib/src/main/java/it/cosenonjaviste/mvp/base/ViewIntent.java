package it.cosenonjaviste.mvp.base;

public class ViewIntent {
    private Class<? extends RxMvpView<?>> viewClass;

    private PresenterArgs args;

    public ViewIntent(Class<? extends RxMvpView<?>> viewClass, PresenterArgs args) {
        this.viewClass = viewClass;
        this.args = args;
    }

    public Class<? extends RxMvpView<?>> getViewClass() {
        return viewClass;
    }

    public PresenterArgs getArgs() {
        return args;
    }
}
