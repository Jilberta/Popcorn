package ge.quickscope.model.net;

import com.squareup.otto.Bus;

import ge.quickscope.model.items.ConfigResponse;
import ge.quickscope.model.items.ImagesComponent;
import ge.quickscope.model.items.MovieCatalogComponent;
import ge.quickscope.model.items.MovieDetail;
import ge.quickscope.model.items.ReviewComponent;
import ge.quickscope.shared.helper.GlobalConstants;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Jay on 7/6/2015.
 */
public class MovieSource implements MoviesDataSource {

    private final MovieDBAPI moviesDBApi;
    private final Bus bus;

    public MovieSource(Bus bus) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(GlobalConstants.MOVIE_DB_URL)
                .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
                .build();

        this.moviesDBApi = restAdapter.create(MovieDBAPI.class);
        this.bus = bus;
    }

    @Override
    public void getMoviesByPage(int page) {
        moviesDBApi.getPopularMoviesByPage(GlobalConstants.MOVIE_DB_API_KEY, page + "", netRequestsCallback);
    }

    @Override
    public void getMovies() {
        moviesDBApi.getPopularMovies(GlobalConstants.MOVIE_DB_API_KEY, netRequestsCallback);
    }

    @Override
    public void getDetailMovie(String movieId) {
        moviesDBApi.getMovieDetail(GlobalConstants.MOVIE_DB_API_KEY, movieId, netRequestsCallback);
    }

    @Override
    public void getReviews(String movieId) {
        moviesDBApi.getReviews(GlobalConstants.MOVIE_DB_API_KEY, movieId, netRequestsCallback);
    }

    @Override
    public void getConfiguration() {
        moviesDBApi.getConfiguration(GlobalConstants.MOVIE_DB_API_KEY, netRequestsCallback);
    }

    @Override
    public void getImages(String movieId) {
        moviesDBApi.getImages(GlobalConstants.MOVIE_DB_API_KEY, movieId, netRequestsCallback);
    }

    public Callback netRequestsCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {
            if (o instanceof MovieDetail) {
                MovieDetail detailResponse = (MovieDetail) o;
                bus.post(detailResponse);
            } else if (o instanceof MovieCatalogComponent) {
                MovieCatalogComponent moviesResponse = (MovieCatalogComponent) o;
                bus.post(moviesResponse);
            } else if (o instanceof ConfigResponse) {
                ConfigResponse configResponse = (ConfigResponse) o;
                bus.post(configResponse);
            } else if (o instanceof ReviewComponent) {
                ReviewComponent reviewComponent = (ReviewComponent) o;
                bus.post(reviewComponent);
            } else if (o instanceof ImagesComponent) {
                ImagesComponent imagesComponent = (ImagesComponent) o;
                bus.post(imagesComponent);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            System.out.println("SHIT HAPPENS EVERY FUCKING BUILD : " + error.getMessage());
        }
    };
}
