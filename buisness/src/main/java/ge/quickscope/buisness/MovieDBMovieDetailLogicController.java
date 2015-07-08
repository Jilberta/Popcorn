package ge.quickscope.buisness;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import ge.quickscope.model.DataSource;
import ge.quickscope.model.items.ImagesComponent;
import ge.quickscope.model.items.MovieDetail;
import ge.quickscope.model.items.ReviewComponent;

/**
 * Created by Jay on 7/8/2015.
 */
public class MovieDBMovieDetailLogicController implements MovieDBMovieDetailLogic {

    private final DataSource dataSource;
    private final String movieId;
    private final Bus uiBus;
    private MovieDetail movieDetail;

    public MovieDBMovieDetailLogicController(String movieId, Bus uiBus, DataSource dataSource){
        this.movieId = movieId;
        this.uiBus = uiBus;
        this.dataSource = dataSource;
        uiBus.register(this);
    }


    @Override
    public void getMovieDetail(String movieId) {
        dataSource.getDetailMovie(movieId);
    }

    @Override
    public void getMovieReviews(String movieId) {
        dataSource.getReviews(movieId);
    }

    @Override
    public void getMovieImages(String movieId) {
        dataSource.getImages(movieId);
    }

    @Subscribe
    @Override
    public void onMovieDetailReceive(MovieDetail details) {
        movieDetail = details;
        getMovieImages(movieId);
    }

    @Subscribe
    @Override
    public void onMovieReviewReceive(ReviewComponent reviews) {
        sendDetailMovieToPresenter(movieDetail);
        uiBus.post(reviews);
        uiBus.unregister(this);
    }

    @Subscribe
    @Override
    public void onMovieImagesReceive(ImagesComponent images) {
        movieDetail.setMovieImagesList(images.getBackdrops());
        getMovieReviews(movieId);
    }

    @Override
    public void sendDetailMovieToPresenter(MovieDetail details) {
        uiBus.post(details);
    }

    @Override
    public void execute() {
        getMovieDetail(movieId);
    }
}
