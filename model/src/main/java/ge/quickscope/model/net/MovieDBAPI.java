package ge.quickscope.model.net;

import ge.quickscope.model.items.ConfigResponse;
import ge.quickscope.model.items.ImagesComponent;
import ge.quickscope.model.items.MovieCatalogComponent;
import ge.quickscope.model.items.MovieDetail;
import ge.quickscope.model.items.ReviewComponent;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Jay on 7/6/2015.
 */
public interface MovieDBAPI {

    @GET("/movie/popular")
    void getPopularMovies(
            @Query("api_key") String apiKey,
            Callback<MovieCatalogComponent> callback);

    @GET("/movie/{id}")
    void getMovieDetail (
            @Query("api_key") String apiKey,
            @Path("id") String id,
            Callback<MovieDetail> callback
    );

    @GET("/movie/popular")
    void getPopularMoviesByPage(
            @Query("api_key") String apiKey,
            @Query("page") String page,
            Callback<MovieCatalogComponent> callback
    );

    @GET("/configuration")
    void getConfiguration (
            @Query("api_key") String apiKey,
            Callback<ConfigResponse> response
    );

    @GET("/movie/{id}/reviews")
    void getReviews (
            @Query("api_key") String apiKey,
            @Path("id") String id,
            Callback<ReviewComponent> response
    );

    @GET("/movie/{id}/images")
    void getImages (
            @Query("api_key") String apiKey,
            @Path("id") String movieId,
            Callback<ImagesComponent> response
    );
}
