package ge.quickscope.model.net;

import java.util.List;

import ge.quickscope.model.items.MovieTableItem;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Jay on 7/9/2015.
 */
public interface MovieTableAPI {

    @GET("/movies/{id}")
    void getMovieTableItems (
            @Path("id") String id,
            @Query("movie_name") String movieName,
            Callback<List<MovieTableItem>> callback
    );
}
