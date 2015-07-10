package ge.quickscope.model.net;

import com.squareup.otto.Bus;

import java.util.ArrayList;

import ge.quickscope.model.items.MovieTableItem;
import ge.quickscope.shared.helper.GlobalConstants;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Jay on 7/9/2015.
 */
public class MovieTableController implements MovieTableSource {

    private final MovieTableAPI movieTableAPI;
    private final Bus bus;

    public MovieTableController(Bus bus){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(GlobalConstants.MOVIE_TABLE_API_URL)
                .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
                .build();
        this.movieTableAPI = restAdapter.create(MovieTableAPI.class);
        this.bus = bus;
    }

    @Override
    public void getMovieTableItems(String movieId, String movieName) {
        movieTableAPI.getMovieTableItems(movieId, movieName, requestCallback);
    }

    public Callback requestCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {
            if (o instanceof ArrayList) {
                if(!((ArrayList) o).isEmpty() && ((ArrayList) o).get(0) instanceof MovieTableItem){
                    ArrayList<MovieTableItem> movieTableItems = (ArrayList<MovieTableItem>) o;
                    bus.post(movieTableItems);
                }else{
                    bus.post(new ArrayList<MovieTableItem>());
                }
            }
        }

        @Override
        public void failure(RetrofitError error) {
            bus.post(new ArrayList<MovieTableItem>());
            System.out.println("SHIT HAPPENS EVERY FUCKING BUILD : " + error.getMessage());
        }
    };
}
