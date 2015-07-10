import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ge.quickscope.buisness.MovieDBConfigurationLogic;
import ge.quickscope.buisness.MovieDBConfigurationLogicController;
import ge.quickscope.model.net.MoviesDataSource;

/**
 * Created by Jay on 7/10/2015.
 */
public class MovieDBConfigurationLogicTest {

    private MovieDBConfigurationLogic configurationLogic;
    @Mock
    private MoviesDataSource mockMoviesDataSource;
    @Mock
    private Bus mockBus;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        configurationLogic = new MovieDBConfigurationLogicController(mockMoviesDataSource, mockBus);
    }

    @Test
    public void testMovieDBConfigurationLogicExecution(){
        configurationLogic.execute();
        verify(mockMoviesDataSource, times(1)).getConfiguration();
    }

}
