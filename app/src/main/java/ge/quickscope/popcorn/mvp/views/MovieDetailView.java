package ge.quickscope.popcorn.mvp.views;

import android.graphics.Bitmap;

import java.util.List;

import ge.quickscope.model.items.Review;

/**
 * Created by Jay on 7/5/2015.
 */
public interface MovieDetailView extends MVPView {

    public void setTitle(String title);

    public void setDescription(String description);

    public void setTag(String tag);

    public void setHomePage(String homePage);

    public void setCompanies(String companies);

    public void showReviews(List<Review> reviews);

    void showMovieCover(Bitmap movieCover);

    void showMovieImage(String imageUrl);

    void startVideoStreaming(String source);

    void showToast(String text);

    void displayLoadingBar();

    void hideLoadingBar();
}
