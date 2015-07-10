import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ge.quickscope.buisness.MovieDBPopularMoviesLogic;
import ge.quickscope.buisness.MovieDBPopularMoviesLogicController;
import ge.quickscope.model.net.MoviesDataSource;

/**
 * Created by Jay on 7/10/2015.
 */
public class MovieDBPopularMoviesLogicTest {

    private MovieDBPopularMoviesLogic movieDBPopularMoviesLogic;
    @Mock
    private MoviesDataSource mockMoviesDataSource;
    @Mock
    private Bus mockBus;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        movieDBPopularMoviesLogic = new MovieDBPopularMoviesLogicController(mockMoviesDataSource, mockBus);
    }

    @Test
    public void testMovieDBPopularMoviesLogicExecution(){
        movieDBPopularMoviesLogic.execute();
        verify(mockMoviesDataSource, times(1)).getMovies();
    }
}
