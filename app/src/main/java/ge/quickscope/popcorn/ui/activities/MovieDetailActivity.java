package ge.quickscope.popcorn.ui.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import ge.quickscope.model.items.Review;
import ge.quickscope.popcorn.R;
import ge.quickscope.popcorn.mvp.views.MovieDetailView;

public class MovieDetailActivity extends ActionBarActivity implements
        MovieDetailView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
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
    public void setTitle(String title) {

    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public void setTag(String tag) {

    }

    @Override
    public void setHomePage(String homePage) {

    }

    @Override
    public void setCompanies(String companies) {

    }

    @Override
    public void showReviews(List<Review> reviews) {

    }

    @Override
    public void showMovieCover(Bitmap movieCover) {

    }

    @Override
    public void showMovieImage(String imageUrl) {

    }

}
