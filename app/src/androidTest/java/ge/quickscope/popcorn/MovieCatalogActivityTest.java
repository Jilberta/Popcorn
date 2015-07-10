package ge.quickscope.popcorn;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

import ge.quickscope.popcorn.ui.activities.MovieCatalogActivity;

/**
 * Created by Jay on 7/10/2015.
 */
public class MovieCatalogActivityTest extends ActivityInstrumentationTestCase2<MovieCatalogActivity> {
    private MovieCatalogActivity movieCatalogActivity;

    public MovieCatalogActivityTest(){
        super(MovieCatalogActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        movieCatalogActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testLoadingBar(){
        Espresso.onView(withId(R.id.movie_catalog_progress)).check(matches(isDisplayed()));
    }

    public void testRecyclerViewIsDisplayed() throws InterruptedException {
        Thread.sleep(1000);
        Espresso.onView(withId(R.id.movie_catalog_recycler)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.movie_catalog_progress)).check(matches(not(isDisplayed())));
    }

    public void testDetailsActivityIsLaunched() throws InterruptedException {
        Thread.sleep(1000);
        Espresso.onView(withId(R.id.movie_catalog_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
        Espresso.onView(withId(R.id.activity_detail_scrollview)).check(matches(isDisplayed()));
    }
}
