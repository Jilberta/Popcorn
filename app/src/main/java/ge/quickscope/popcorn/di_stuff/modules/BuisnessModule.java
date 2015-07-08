package ge.quickscope.popcorn.di_stuff.modules;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ge.quickscope.model.net.MovieSource;

/**
 * Created by Jay on 7/8/2015.
 */
@Module
public class BuisnessModule {
    @Provides
    @Singleton
    Bus provideBus(){
        return new Bus();
    }

    @Provides
    @Singleton
    MovieSource provideMovieSource(Bus bus){
        return new MovieSource(bus);
    }
}
