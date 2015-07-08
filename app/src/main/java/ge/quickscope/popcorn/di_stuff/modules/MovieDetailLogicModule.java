package ge.quickscope.popcorn.di_stuff.modules;

import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;
import ge.quickscope.buisness.MovieDBMovieDetailLogic;
import ge.quickscope.buisness.MovieDBMovieDetailLogicController;
import ge.quickscope.model.net.MovieSource;

/**
 * Created by Jay on 7/8/2015.
 */
@Module
public class MovieDetailLogicModule {
    private final String movieId;

    public MovieDetailLogicModule(String movieId){
        this.movieId = movieId;
    }

    @Provides
    MovieDBMovieDetailLogic provideMovieDetailLogic(Bus bus, MovieSource movieSource){
        return new MovieDBMovieDetailLogicController(movieId, bus, movieSource);
    }
}
