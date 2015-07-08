package ge.quickscope.popcorn.di_stuff.components;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;
import ge.quickscope.model.net.MovieSource;
import ge.quickscope.popcorn.di_stuff.modules.ApplicationModule;
import ge.quickscope.popcorn.di_stuff.modules.BuisnessModule;

/**
 * Created by Jay on 7/8/2015.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        BuisnessModule.class,
})

public interface ApplicationComponent {
    Bus bus();
    MovieSource movieSource();
}
