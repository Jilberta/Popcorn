package ge.quickscope.popcorn.ui.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ge.quickscope.model.items.Movie;
import ge.quickscope.model.items.MovieCatalogComponent;
import ge.quickscope.popcorn.App;
import ge.quickscope.popcorn.R;
import ge.quickscope.popcorn.di_stuff.components.DaggerMainLogicComponent;
import ge.quickscope.popcorn.di_stuff.modules.MainLogicModule;
import ge.quickscope.popcorn.mvp.presenters.MovieCatalogPresenter;
import ge.quickscope.popcorn.mvp.views.MovieCatalogView;
import ge.quickscope.popcorn.ui.adapters.CatalogAdapter;
import ge.quickscope.popcorn.ui.helpers.RecyclerClickListener;
import ge.quickscope.popcorn.ui.helpers.RecyclerDecorator;


public class MovieCatalogActivity extends ActionBarActivity implements
        MovieCatalogView, RecyclerClickListener, View.OnClickListener {

    private static final String MOVIE_CATALOG_COMPONENT_KEY = "movieCatalogComponent";
    private final static String BACKGROUND_TRANSLATION_KEY = "backgroundTrans";
    public final static String MOVIE_ID_KEY = "movie_id";
    public final static String MOVIE_TITLE_KEY = "movie_title";
    public final static String MOVIE_LOCATION_KEY = "view_location";
    public final static String MOVIE_POSITION_KEY = "movie_position";
    public final static String SHARED_ELEMENT_COVER_KEY = "cover";

    private CatalogAdapter catalogAdapter;

    public float backgroundTranslation;
    @Nullable
    @Bind(R.id.movie_catalog_background_view)
    View tabletBackground;
    @Bind(R.id.movie_catalog_toolbar)
    Toolbar toolBar;
    @Bind(R.id.movie_catalog_progress)
    ProgressBar progressBar;
    @Bind(R.id.movie_catalog_recycler)
    RecyclerView recycler;
    @Bind(R.id.drawer)
    DrawerLayout drawerLayout;
    @Inject
    MovieCatalogPresenter movieCatalogPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initDI();
        initToolbar();
        initDrawer();
        initRecycler();

        if(savedInstanceState == null)
            movieCatalogPresenter.attachView(this);
        else
            initFromBundle(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        movieCatalogPresenter.start();
    }

    private void initDI(){
        App app = (App) getApplication();

        DaggerMainLogicComponent.builder()
                .applicationComponent(app.getApplicationComponent())
                .mainLogicModule(new MainLogicModule())
                .build().inject(this);
    }

    private void initFromBundle(Bundle savedInstanceState){
        MovieCatalogComponent movieCatalogComponent = (MovieCatalogComponent) savedInstanceState.getSerializable(MOVIE_CATALOG_COMPONENT_KEY);
        movieCatalogPresenter.onPopularMoviesCatalogReceived(movieCatalogComponent);
    }

    private void initToolbar(){
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator();
        toolBar.setNavigationOnClickListener(this);
    }

    private void initDrawer(){
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void initRecycler(){
        recycler.addItemDecoration(new RecyclerDecorator(this));
        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean scrollCheck;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItems = recyclerView.getLayoutManager().getChildCount();
                int totalItems = recyclerView.getLayoutManager().getItemCount();
                int seenItems = ((GridLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if((visibleItems + seenItems) >= totalItems && !movieCatalogPresenter.isLoading()) {
                    movieCatalogPresenter.onRecycleListEnd();
                }
                if(tabletBackground != null){
                    backgroundTranslation = tabletBackground.getY() - (dy / 2);
                    tabletBackground.setTranslationY(backgroundTranslation);
                }
                if(dy > 10){
                    if(!scrollCheck){
                        showToolbar();
                        scrollCheck = true;
                    }
                } else if(dy < -10){
                    if(scrollCheck){
                        hideToolbar();
                        scrollCheck = false;
                    }
                }
            }
        });
    }

    private void showToolbar() {
        toolBar.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.slide_down));
    }

    private void hideToolbar() {
        toolBar.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.slide_up));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(catalogAdapter != null){
            outState.putSerializable(MOVIE_CATALOG_COMPONENT_KEY, new MovieCatalogComponent(catalogAdapter.getMoviesList()));
            outState.putFloat(BACKGROUND_TRANSLATION_KEY, backgroundTranslation);
        }
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
        catalogAdapter = new CatalogAdapter(movieCatalogList);
        catalogAdapter.setRecyclerClickListener(this);
        recycler.setAdapter(catalogAdapter);
    }

    @Override
    public boolean isCatalogEmpty() {
        return (catalogAdapter == null) || catalogAdapter.getMoviesList().isEmpty();
    }

    @Override
    public void addMovieCatalog(List<Movie> movieCatalogList) {
        catalogAdapter.setMoviesList(movieCatalogList);
    }

    @Override
    public void displayIsLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIsLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displaySnackBar() {
        Snackbar loadingBar = Snackbar.with(this)
                .text(getString(R.string.movie_catalog_activity_more_movies))
                .actionLabel(getString(R.string.movie_catalog_activity_snackbar_cancel))
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                .color(getResources().getColor(R.color.theme_primary))
                .actionColor(getResources().getColor(R.color.theme_accent));
        SnackbarManager.show(loadingBar);
    }

    @Override
    public void hideSnackBar() {
        SnackbarManager.dismiss();
    }

    public static SparseArray<Bitmap> posterCache = new SparseArray<Bitmap>(1);
    @Override
    public void onClick(View v, int position, float x, float y) {
        Intent movieDetailActivity = new Intent(MovieCatalogActivity.this, MovieDetailActivity.class);

        String movieId = catalogAdapter.getMoviesList().get(position).getId();
        String movieTitle = catalogAdapter.getMoviesList().get(position).getTitle();
        movieDetailActivity.putExtra(MOVIE_ID_KEY, movieId);
        movieDetailActivity.putExtra(MOVIE_TITLE_KEY, movieTitle);
        movieDetailActivity.putExtra(MOVIE_POSITION_KEY, position);

        ImageView coverView = (ImageView) v.findViewById(R.id.cover);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) coverView.getDrawable();

        if(catalogAdapter.isMovieReady(position) || bitmapDrawable != null){
            posterCache.put(0, bitmapDrawable.getBitmap());
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                startActivityWithSharedView(v, position, movieDetailActivity);
            } else {
                startActivityWithAnimation(v, (int) x, (int) y, movieDetailActivity);
            }
        } else {
            Toast.makeText(this, getString(R.string.movie_catalog_activity_loading_film),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("unchecked")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startActivityWithSharedView(View touchedView, int moviePosition, Intent movieDetailActivityIntent) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                this, new Pair<>(touchedView, SHARED_ELEMENT_COVER_KEY + moviePosition));
        startActivity(movieDetailActivityIntent, options.toBundle());
    }

    private void startActivityWithAnimation(View touchedView, int touchedX, int touchedY, Intent movieDetailActivityIntent) {
        int[] touchedLoc = {touchedX, touchedY};
        int[] screenLoc = new int [2];
        touchedView.getLocationOnScreen(screenLoc);
        int finalLocX = screenLoc[0] + touchedLoc[0];
        int finalLocY = screenLoc[1] + touchedLoc[1];
        int [] finalLoc = {finalLocX, finalLocY};
        movieDetailActivityIntent.putExtra(MOVIE_LOCATION_KEY, finalLoc);
        startActivity(movieDetailActivityIntent);
    }

    @Override
    public void onClick(View v) {

    }
}
