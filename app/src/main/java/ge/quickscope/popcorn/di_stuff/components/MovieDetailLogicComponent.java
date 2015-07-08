package ge.quickscope.popcorn.di_stuff.components;

import dagger.Component;
import ge.quickscope.popcorn.di_stuff.modules.MovieDetailLogicModule;
import ge.quickscope.popcorn.di_stuff.scopes.ActivityScope;
import ge.quickscope.popcorn.ui.activities.MovieDetailActivity;

/**
 * Created by Jay on 7/8/2015.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = MovieDetailLogicModule.class)
public interface MovieDetailLogicComponent {
    void inject(MovieDetailActivity movieDetailActivity);
}
