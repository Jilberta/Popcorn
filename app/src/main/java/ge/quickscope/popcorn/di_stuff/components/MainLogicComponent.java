package ge.quickscope.popcorn.di_stuff.components;

import dagger.Component;
import ge.quickscope.popcorn.di_stuff.modules.MainLogicModule;
import ge.quickscope.popcorn.di_stuff.scopes.ActivityScope;
import ge.quickscope.popcorn.ui.activities.MovieCatalogActivity;

/**
 * Created by Jay on 7/8/2015.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = MainLogicModule.class)
public interface MainLogicComponent {
    void inject (MovieCatalogActivity movieCatalogActivity);
}
