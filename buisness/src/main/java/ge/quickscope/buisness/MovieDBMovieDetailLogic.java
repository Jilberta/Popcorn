package ge.quickscope.buisness;

import ge.quickscope.model.items.ImagesComponent;
import ge.quickscope.model.items.MovieDetail;
import ge.quickscope.model.items.ReviewComponent;

/**
 * Created by Jay on 7/5/2015.
 */
public interface MovieDBMovieDetailLogic extends Logic {

    void getMovieDetail(String movieId);
    void getMovieReviews(String movieId);
    void getMovieImages(String movieId);
    void onMovieDetailReceive(MovieDetail details);
    void onMovieReviewReceive(ReviewComponent reviews);
    void onMovieImagesReceive(ImagesComponent images);
    void sendDetailMovieToPresenter(MovieDetail details);
}
