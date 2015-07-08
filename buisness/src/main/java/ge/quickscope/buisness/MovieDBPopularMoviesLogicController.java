package ge.quickscope.buisness;

import com.squareup.otto.Bus;

import ge.quickscope.model.items.MovieCatalogComponent;
import ge.quickscope.model.net.MoviesDataSource;

/**
 * Created by Jay on 7/6/2015.
 */
public class MovieDBPopularMoviesLogicController implements MovieDBPopularMoviesLogic {

    private final MoviesDataSource movieSource;
    private final Bus uiBus;
    private int page = 1;

    public MovieDBPopularMoviesLogicController(MoviesDataSource movieSource, Bus uiBus) {
        this.movieSource = movieSource;
        this.uiBus = uiBus;

        this.uiBus.register(this);
    }

    @Override
    public void getPopularMovies() {
        movieSource.getMoviesByPage(page);
    }

    @Override
    public void sendMoviesToPresenter(MovieCatalogComponent movieComponent) {
        uiBus.post(movieComponent);
    }

    @Override
    public void unRegister() {
        uiBus.unregister(this);
    }

    @Override
    public void execute() {
        getPopularMovies();
        page++;
    }
}
