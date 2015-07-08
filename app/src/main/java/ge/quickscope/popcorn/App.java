package ge.quickscope.popcorn;

import android.app.Application;

import ge.quickscope.popcorn.di_stuff.components.DaggerApplicationComponent;
import ge.quickscope.popcorn.di_stuff.components.ApplicationComponent;
import ge.quickscope.popcorn.di_stuff.modules.ApplicationModule;
import ge.quickscope.popcorn.di_stuff.modules.BuisnessModule;

/**
 * Created by Jay on 7/8/2015.
 */
public class App extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencyInjector();
    }

    private void initDependencyInjector(){
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .buisnessModule(new BuisnessModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}
