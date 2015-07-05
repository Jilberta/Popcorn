package ge.quickscope.popcorn.mvp.views;

import java.util.List;

import ge.quickscope.model.items.Movie;

/**
 * Created by Jay on 7/5/2015.
 */
public interface MovieCatalogView extends MVPView {

    void displayMovies(List<Movie> movieCatalogList);

    boolean isCatalogEmpty();

    void addMovieCatalog(List<Movie> movieCatalogList);

    void displayIsLoading();

    void hideIsLoading();

    void displayIsLoadingBar();

    void hideIsLoadingBBar();
}
