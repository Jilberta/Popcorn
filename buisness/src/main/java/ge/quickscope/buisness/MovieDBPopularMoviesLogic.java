package ge.quickscope.buisness;

import ge.quickscope.model.items.MovieCatalogComponent;

/**
 * Created by Jay on 7/5/2015.
 */
public interface MovieDBPopularMoviesLogic extends Logic {

    public void getPopularMovies();
    public void sendMoviesToPresenter(MovieCatalogComponent movieComponent);
    public void unRegister();
}
