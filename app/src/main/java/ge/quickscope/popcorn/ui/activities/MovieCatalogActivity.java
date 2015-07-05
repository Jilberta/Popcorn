package ge.quickscope.popcorn.ui.activities;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.squareup.otto.Bus;

import java.util.List;

import ge.quickscope.buisness.MovieDBPopularMoviesLogic;
import ge.quickscope.buisness.MovieDBPopularMoviesLogicController;
import ge.quickscope.model.items.Movie;
import ge.quickscope.model.net.MovieSource;
import ge.quickscope.model.net.MoviesDataSource;
import ge.quickscope.popcorn.R;
import ge.quickscope.popcorn.mvp.views.MovieCatalogView;


public class MovieCatalogActivity extends ActionBarActivity implements
        MovieCatalogView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button click = (Button) findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bus bus = new Bus();
                MoviesDataSource source = new MovieSource(bus);
                MovieDBPopularMoviesLogic source2 = new MovieDBPopularMoviesLogicController(source, bus);
                source2.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void displayMovies(List<Movie> movieCatalogList) {

    }

    @Override
    public boolean isCatalogEmpty() {
        return false;
    }

    @Override
    public void addMovieCatalog(List<Movie> movieCatalogList) {

    }

    @Override
    public void displayIsLoading() {

    }

    @Override
    public void hideIsLoading() {

    }

    @Override
    public void displayIsLoadingBar() {

    }

    @Override
    public void hideIsLoadingBBar() {

    }

}
