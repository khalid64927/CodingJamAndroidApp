package it.cosenonjaviste.post;

import dagger.Component;
import it.cosenonjaviste.ApplicationComponent;
import it.cosenonjaviste.PresenterScope;

@PresenterScope
@Component(dependencies = ApplicationComponent.class)
public interface PostListComponent {
    void inject(PostListFragment fragment);
}
