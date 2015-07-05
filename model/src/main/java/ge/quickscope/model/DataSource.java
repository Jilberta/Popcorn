package ge.quickscope.model;

/**
 * Created by Jay on 7/6/2015.
 */
public interface DataSource {

    void getMovies();
    void getDetailMovie(String movieId);
    void getReviews(String movieId);
    void getConfiguration();
    void getImages(String movieId);
}
