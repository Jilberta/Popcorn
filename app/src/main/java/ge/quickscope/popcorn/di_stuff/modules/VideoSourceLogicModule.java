package ge.quickscope.popcorn.di_stuff.modules;

import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;
import ge.quickscope.buisness.MovieDBMovieDetailLogic;
import ge.quickscope.buisness.MovieDBMovieDetailLogicController;
import ge.quickscope.model.net.MovieSource;
import ge.quickscope.model.net.MovieTableController;
import ge.quickscope.model.net.MovieTableSource;

/**
 * Created by Jay on 7/10/2015.
 */
@Module
public class VideoSourceLogicModule {
    private final String movieId, movieName;

    public VideoSourceLogicModule(String movieId, String movieName){
        this.movieId = movieId;
        this.movieName = movieName;
    }

//    @Provides
//    MovieDBMovieDetailLogic provideMovieDetailLogic(Bus bus, MovieTableController movieTableController){
//        return new MovieDBMovieDetailLogicController(movieId, bus, movieTableController);
//    }
}
