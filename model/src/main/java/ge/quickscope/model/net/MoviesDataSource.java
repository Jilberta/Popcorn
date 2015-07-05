package ge.quickscope.model.net;

import ge.quickscope.model.DataSource;

/**
 * Created by Jay on 7/6/2015.
 */
public interface MoviesDataSource extends DataSource {

    public void getMoviesByPage(int page);
}
