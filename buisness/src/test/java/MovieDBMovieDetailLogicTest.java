import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ge.quickscope.buisness.MovieDBMovieDetailLogic;
import ge.quickscope.buisness.MovieDBMovieDetailLogicController;
import ge.quickscope.model.items.MovieDetail;
import ge.quickscope.model.net.MoviesDataSource;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;

/**
 * Created by Jay on 7/10/2015.
 */
public class MovieDBMovieDetailLogicTest {
    private MovieDBMovieDetailLogic movieDBMovieDetailLogic;
    private String movieTestId = "12";
    @Mock
    private Bus mockBus;
    @Mock
    private MoviesDataSource mockDataSource;

    @Before
    public void init () {
        MockitoAnnotations.initMocks(this);
        movieDBMovieDetailLogic = new MovieDBMovieDetailLogicController(movieTestId, mockBus, mockDataSource);
    }

    @Test
    public void testMovieDBMovieDetailLogicExecution(){
        movieDBMovieDetailLogic.execute();
        verify(mockDataSource, times(1)).getMovies();
    }

    @Test
    public void testDataPost(){
        movieDBMovieDetailLogic.onMovieDetailReceive(new MovieDetail());
        verify(mockBus, times(1)).post(any(MovieDetail.class));
    }
}
