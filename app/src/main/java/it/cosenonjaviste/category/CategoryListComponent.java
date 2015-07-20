package it.cosenonjaviste.category;

import dagger.Component;
import it.cosenonjaviste.ApplicationComponent;
import it.cosenonjaviste.PresenterScope;

@PresenterScope
@Component(dependencies = ApplicationComponent.class)
public interface CategoryListComponent {
    void inject(CategoryListFragment fragment);
}
