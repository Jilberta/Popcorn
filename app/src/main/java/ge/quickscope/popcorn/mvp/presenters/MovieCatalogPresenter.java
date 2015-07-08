package ge.quickscope.popcorn.mvp.presenters;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import ge.quickscope.buisness.MovieDBConfigurationLogic;
import ge.quickscope.buisness.MovieDBPopularMoviesLogic;
import ge.quickscope.model.items.MovieCatalogComponent;
import ge.quickscope.popcorn.mvp.views.MovieCatalogView;
import ge.quickscope.shared.helper.GlobalConstants;

/**
 * Created by Jay on 7/5/2015.
 */
public class MovieCatalogPresenter extends Presenter {
    private final Bus mainbus;
    private MovieDBConfigurationLogic configurationLogic;
    private MovieDBPopularMoviesLogic popularMoviesLogic;
    private MovieCatalogView movieCatalogView;
    private boolean isLoading = false;
    private boolean registered;

    @Inject
    public MovieCatalogPresenter(MovieDBConfigurationLogic configurationLogic, MovieDBPopularMoviesLogic popularMoviesLogic, Bus bus){
        this.configurationLogic = configurationLogic;
        this.popularMoviesLogic = popularMoviesLogic;
        this.mainbus = bus;
    }

    public void attachView(MovieCatalogView movieCatalogView){
        this.movieCatalogView = movieCatalogView;
    }

    @Subscribe
    public void onPopularMoviesCatalogReceived(MovieCatalogComponent movieCatalogComponent){
        if(movieCatalogView.isCatalogEmpty()){
            movieCatalogView.hideIsLoading();
            movieCatalogView.displayMovies(movieCatalogComponent.getResults());
        } else {
            movieCatalogView.hideSnackBar();
            movieCatalogView.addMovieCatalog(movieCatalogComponent.getResults());
        }
        isLoading = false;
    }

    @Subscribe
    public void onConfigurationCallbackReceived(String imageUrl){
        GlobalConstants.BASE_URL = imageUrl;
        popularMoviesLogic.execute();
    }

    public void onRecycleListEnd(){
        popularMoviesLogic.execute();
        movieCatalogView.displaySnackBar();
        isLoading = true;
    }

    @Override
    public void start() {
        if(movieCatalogView.isCatalogEmpty()){
            mainbus.register(this);
            registered = true;
            movieCatalogView.displayIsLoading();
            configurationLogic.execute();
//            popularMoviesLogic.execute();
        }
    }

    @Override
    public void stop() {

    }

    public boolean isLoading(){
        return this.isLoading;
    }

    public void setIsLoading(boolean isLoading){
        this.isLoading = isLoading;
    }

}
