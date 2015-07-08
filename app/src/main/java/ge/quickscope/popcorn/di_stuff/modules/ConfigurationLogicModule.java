package ge.quickscope.popcorn.di_stuff.modules;

import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;
import ge.quickscope.buisness.MovieDBConfigurationLogic;
import ge.quickscope.buisness.MovieDBConfigurationLogicController;
import ge.quickscope.model.net.MovieSource;

/**
 * Created by Jay on 7/8/2015.
 */
@Module
public class ConfigurationLogicModule {

    @Provides
    MovieDBConfigurationLogic provideConfigurationLogic(Bus bus, MovieSource movieSource){
        return new MovieDBConfigurationLogicController(movieSource, bus);
    }
}
