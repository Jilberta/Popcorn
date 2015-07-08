package ge.quickscope.popcorn.di_stuff.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ge.quickscope.popcorn.App;

/**
 * Created by Jay on 7/8/2015.
 */
@Module
public class ApplicationModule {
    private final App application;

    public ApplicationModule(App application){
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideAppContext(){
        return application;
    }
}
