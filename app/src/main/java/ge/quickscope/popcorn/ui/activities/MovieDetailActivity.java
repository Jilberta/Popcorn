package ge.quickscope.popcorn.ui.activities;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ge.quickscope.model.items.Review;
import ge.quickscope.popcorn.App;
import ge.quickscope.popcorn.R;
import ge.quickscope.popcorn.di_stuff.components.DaggerMovieDetailLogicComponent;
import ge.quickscope.popcorn.di_stuff.modules.MovieDetailLogicModule;
import ge.quickscope.popcorn.mvp.presenters.MovieDetailPresenter;
import ge.quickscope.popcorn.mvp.views.MovieDetailView;
import ge.quickscope.popcorn.ui.custom_views.ScrollViewListener;
import ge.quickscope.popcorn.ui.custom_views.ScrollWatcherScrollView;
import ge.quickscope.popcorn.ui.helpers.GuiHelper;
import ge.quickscope.popcorn.ui.helpers.TransitionAnimations;
import ge.quickscope.popcorn.ui.listeners.CustomAnimationListener;
import ge.quickscope.popcorn.ui.listeners.CustomTransitionListener;


public class MovieDetailActivity extends ActionBarActivity implements
        MovieDetailView, ScrollViewListener, Palette.PaletteAsyncListener {

    private static final int TAG_HEADER = 0;
    private static final int DESCRIPTION_HEADER = 1;
    private static final int REVIEWS_HEADER = 2;

    private static final int DESCRIPTION = 0;
    private static final int HOMEPAGE = 1;
    private static final int COMPANY = 2;
    private static final int TAG = 3;
//    private static final int CONFIRMATION = 4;

    private int reviewsColor = -1;
    private int reviewsAuthorColor = -1;

    private Palette.Swatch brightSwatch;

    @Inject
    MovieDetailPresenter detailsPresenter;

    @Bind({
            R.id.content,
            R.id.homepage,
            R.id.company,
            R.id.tagline,
    })
    List<TextView> movieInfoViews;

    @Bind({
            R.id.header_tagline,
            R.id.header_description,
            R.id.header_reviews
    })
    List<TextView> movieHeaders;

    @Bind(R.id.title)
    TextView titleView;
    @Bind(R.id.fab)
    ImageView fabButton;
    @Bind(R.id.activity_detail_container)
    View infoContainer;
    @Bind(R.id.cover)
    ImageView coverView;
    @Nullable @Bind(R.id.image)
    ImageView movieImageView;
    @Bind(R.id.movie_info)
    LinearLayout movieDescriptionContainer;
    @Bind(R.id.activity_detail_scrollview)
    ScrollWatcherScrollView scrollWatcherScrollView;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    private int[] viewLastLocation;
    boolean isTablet;

    private String movieId, movieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        movieId = getIntent().getStringExtra(MovieCatalogActivity.MOVIE_ID_KEY);
        movieName = getIntent().getStringExtra(MovieCatalogActivity.MOVIE_TITLE_KEY);

        isTablet = getContext().getResources().getBoolean(R.bool.is_tablet);
        initDI();
        initAnimation();
    }

    private void initDI(){
        String movieId = getIntent().getStringExtra(MovieCatalogActivity.MOVIE_ID_KEY);
        App app = (App) getApplication();

        DaggerMovieDetailLogicComponent.builder()
                .applicationComponent(app.getApplicationComponent())
                .movieDetailLogicModule(new MovieDetailLogicModule(movieId))
                .build().inject(this);
    }

    private void initAnimation(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if(!isTablet){
                GuiHelper.makeStatusbarTranslucent(this);
                scrollWatcherScrollView.setScrollViewListener(this);
            }
            setEnterTransition();
        } else {
            viewLastLocation = getIntent().getIntArrayExtra(MovieCatalogActivity.MOVIE_LOCATION_KEY);
            setEnterAnimation();
        }
    }

    private void setEnterAnimation(){
        if(!isTablet){
            GuiHelper.startScaleAnimationFromPivotY(
                    viewLastLocation[0], viewLastLocation[1],
                    scrollWatcherScrollView, new CustomAnimationListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            GuiHelper.showViewByScale(fabButton);
                        }
                    }
            );
            animateElementsByScale();
        }
    }

    private void animateElementsByScale() {
        GuiHelper.showViewByScale(fabButton);
        GuiHelper.showViewByScaleY(titleView, new CustomAnimationListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                GuiHelper.showViewByScale(movieDescriptionContainer);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setEnterTransition(){
        getWindow().setSharedElementEnterTransition(TransitionAnimations.setSharedElementEnterTransition(this));
        postponeEnterTransition();

        int position = getIntent().getIntExtra(MovieCatalogActivity.MOVIE_POSITION_KEY, 0);

        coverView.setTransitionName(MovieCatalogActivity.SHARED_ELEMENT_COVER_KEY + position);
        scrollWatcherScrollView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                scrollWatcherScrollView.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }
        });
        getWindow().getSharedElementEnterTransition().addListener(new CustomTransitionListener() {
            @Override
            public void onTransitionEnd(Transition transition) {
                super.onTransitionEnd(transition);
                startScaleAnimation();
            }
        });
    }

    private void startScaleAnimation(){
        GuiHelper.showViewByScale(fabButton);
        GuiHelper.showViewByScaleY(titleView, new CustomAnimationListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                GuiHelper.showViewByScale(movieDescriptionContainer);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        detailsPresenter.attachView(this);
        detailsPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        detailsPresenter.stop();
    }

//    @Override
//    public void finish(String cause) {
//        Toast.makeText(this, cause, Toast.LENGTH_SHORT).show();
//        this.finish();
//    }

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
        titleView.setText(title);
    }

    @Override
    public void setDescription(String description) {
        movieHeaders.get(DESCRIPTION_HEADER).setVisibility(View.VISIBLE);
        movieInfoViews.get(DESCRIPTION).setVisibility(View.VISIBLE);
        movieInfoViews.get(DESCRIPTION).setText(description);
    }

    @Override
    public void setTag(String tag) {
        movieHeaders.get(TAG_HEADER).setVisibility(View.VISIBLE);
        movieInfoViews.get(TAG).setVisibility(View.VISIBLE);
        movieInfoViews.get(TAG).setText(tag);
    }

    @Override
    public void setHomePage(String homePage) {
        movieInfoViews.get(HOMEPAGE).setVisibility(View.VISIBLE);
        movieInfoViews.get(HOMEPAGE).setText(homePage);
    }

    @Override
    public void setCompanies(String companies) {
        movieInfoViews.get(COMPANY).setVisibility(View.VISIBLE);
        movieInfoViews.get(COMPANY).setText(companies);
    }

    @Override
    public void showReviews(List<Review> reviews) {
        final int reviewTopMargin = getResources().getDimensionPixelOffset(
                R.dimen.activity_vertical_margin_half);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, reviewTopMargin, 0, 0);

        movieHeaders.get(REVIEWS_HEADER).setVisibility(View.VISIBLE);
        for(Review result : reviews){
            TextView reviewView = new TextView(this);
            reviewView.setTextAppearance(this, R.style.MoviesHeaderTextView);
            if(reviewsColor != -1)
                reviewView.setTextColor(reviewsColor);
            String reviewAuthor = "Review written by " + result.getAuthor();
            String reviewText = String.format("%s - %s", reviewAuthor, result.getContent());
            Spannable spanColorString = new SpannableString(reviewText);
            spanColorString.setSpan(new ForegroundColorSpan(reviewsAuthorColor), 0, reviewAuthor.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            reviewView.setText(spanColorString);
            movieDescriptionContainer.addView(reviewView, layoutParams);
        }
    }

    @Override
    public void showMovieCover(Bitmap movieCover) {
        coverView.setImageBitmap(movieCover);
        Palette.generateAsync(movieCover, this);
    }

    @Override
    public void showMovieImage(String imageUrl) {
        if(isTablet && movieImageView != null){
            Picasso.with(this).load(imageUrl)
                    .fit().centerCrop()
                    .into(movieImageView);
        }
    }

    boolean isTranslucent = false;
    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldX, int oldY) {
        if (y > coverView.getHeight()) {
            titleView.setTranslationY(y - coverView.getHeight());
            if (!isTranslucent) {
                isTranslucent = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    GuiHelper.setTheStatusbarNotTranslucent(this);
                    if(brightSwatch != null)
                        getWindow().setStatusBarColor(brightSwatch.getRgb());
                }
            }
        }

        if (y < coverView.getHeight() && isTranslucent) {
            titleView.setTranslationY(0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                GuiHelper.makeTheStatusbarTranslucent(this);
                isTranslucent = false;
            }
        }
    }

    @Override
    public void onGenerated(Palette palette) {
        if(palette != null){
            final Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
            final Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
            final Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
            final Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();
            final Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();

            final Palette.Swatch backgroundAndContentColors = (darkVibrantSwatch != null) ? darkVibrantSwatch : darkMutedSwatch;
            final Palette.Swatch titleAndFabColors = (darkVibrantSwatch != null) ? lightVibrantSwatch : lightMutedSwatch;

            setBackgroundAndFabContentColors(backgroundAndContentColors);
            setHeadersTitleColors(titleAndFabColors);
            setVibrantElements(vibrantSwatch);
        }
    }

    public void setBackgroundAndFabContentColors(Palette.Swatch swatch) {
        if (swatch != null) {
            reviewsColor = swatch.getTitleTextColor();
            infoContainer.setBackgroundColor(swatch.getRgb());
            titleView.setTextColor(swatch.getRgb());
            ButterKnife.apply(movieInfoViews, GuiHelper.setter, swatch.getTitleTextColor());
        }
    }

    public void setHeadersTitleColors(Palette.Swatch swatch) {
        if (swatch != null) {
            brightSwatch = swatch;
            titleView.setBackgroundColor(brightSwatch.getRgb());
            reviewsAuthorColor = swatch.getRgb();

            GuiHelper.tintAndSetCompoundDrawable(this, R.drawable.ic_domain_white_24dp,
                    swatch.getRgb(), movieInfoViews.get(HOMEPAGE));
            GuiHelper.tintAndSetCompoundDrawable(this, R.drawable.ic_public_white_24dp,
                    swatch.getRgb(), movieInfoViews.get(COMPANY));
            ButterKnife.apply(movieHeaders, GuiHelper.setter,
                    swatch.getRgb());
        }
    }

    /**
     * Dasaregulirebelia
     * @param vibrantSwatch
     */
    private void setVibrantElements(Palette.Swatch vibrantSwatch) {
        if(vibrantSwatch != null)
            fabButton.getBackground().setColorFilter(vibrantSwatch.getRgb(), PorterDuff.Mode.MULTIPLY);
    }

    @OnClick(R.id.fab)
    public void onClick() {
        displayLoadingBar();
        detailsPresenter.getVideoLinks();//"tt0816692", "Interstellar");
    }

    @Override
    public void startVideoStreaming(String source){
        hideLoadingBar();
        String videoPath = "http://85.118.125.84/secure/19/2015031610253823_medium_eng.mp4?md5=pZhaH30tkKfKaRmNjH58rw";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(source), "video/*");
        startActivity(intent);
    }

    @Override
    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayLoadingBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingBar() {
        progressBar.setVisibility(View.GONE);
    }
}
