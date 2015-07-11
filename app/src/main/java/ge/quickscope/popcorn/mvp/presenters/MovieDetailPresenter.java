package ge.quickscope.popcorn.mvp.presenters;

import android.os.Handler;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import ge.quickscope.buisness.MovieDBMovieDetailLogic;
import ge.quickscope.model.items.ImagesComponent;
import ge.quickscope.model.items.MovieDetail;
import ge.quickscope.model.items.MovieTableItem;
import ge.quickscope.model.items.ProductionCompany;
import ge.quickscope.model.items.ReviewComponent;
import ge.quickscope.model.net.MovieTableController;
import ge.quickscope.model.net.MovieTableSource;
import ge.quickscope.popcorn.mvp.views.MovieDetailView;
import ge.quickscope.popcorn.ui.activities.MovieCatalogActivity;
import ge.quickscope.shared.helper.GlobalConstants;

/**
 * Created by Jay on 7/5/2015.
 */
public class MovieDetailPresenter extends Presenter {
    private final Bus mainBus;
    private MovieDetailView movieDetailView;
    private final MovieDBMovieDetailLogic movieDetailLogic;

    private MovieDetail movieDetail;

    @Inject
    public MovieDetailPresenter(MovieDBMovieDetailLogic movieDetailLogic, Bus bus){
        this.movieDetailLogic = movieDetailLogic;
        this.mainBus = bus;
    }

    public void attachView(MovieDetailView movieDetailView){
        this.movieDetailView = movieDetailView;
        this.movieDetailView.showMovieCover(MovieCatalogActivity.posterCache.get(0));
        this.movieDetailLogic.execute();
    }

    public void showDescription(String description){
        movieDetailView.setDescription(description);
    }

    public void showTag(String tag){
        movieDetailView.setTag(tag);
    }

    public void showTitle(String title){
        movieDetailView.setTitle(title);
    }

    public void showCompanies(List<ProductionCompany> companies){
        String companiesNames = "";
        for(int i = 0; i < companies.size(); i++){
            ProductionCompany company = companies.get(i);
            companiesNames += company.getName();
            if(i != companies.size() - 1)
                companiesNames += ", ";
        }
        if(!companies.isEmpty())
            movieDetailView.setCompanies(companiesNames);
    }

    public void showHomePage(String homePage){
        if(!homePage.isEmpty())
            movieDetailView.setHomePage(homePage);
    }

    public void showMovieImage(List<ImagesComponent.MovieImage> movieImageList){
        if(movieImageList != null && movieImageList.size() > 0){
            int randIndex = new Random().nextInt(movieImageList.size());
            movieDetailView.showMovieImage(GlobalConstants.BASE_URL + movieImageList.get(randIndex).getFile_path());
        }
    }

    @Subscribe
    public void onMovieDetailInfoReceived(MovieDetail movieDetail){
        this.movieDetail = movieDetail;
        showDescription(movieDetail.getOverview());
        showTitle(movieDetail.getTitle());
        showTag(movieDetail.getTagline());
        showCompanies(movieDetail.getProduction_companies());
        showHomePage(movieDetail.getHomepage());
        showMovieImage(movieDetail.getMovieImagesList());
    }

    @Subscribe
    public void onReviewsReceived(final ReviewComponent reviewComponent){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (reviewComponent.getResults().size() > 0)
                    movieDetailView.showReviews(reviewComponent.getResults());
            }
        }, 300);
    }

    @Override
    public void start() {
        mainBus.register(this);
    }

    @Override
    public void stop() {
        mainBus.unregister(this);
    }

    public void getVideoLinks(){
        MovieTableSource src = new MovieTableController(mainBus);
        src.getMovieTableItems(movieDetail.getImdb_id(), movieDetail.getTitle());//"tt0816692", "Interstellar");
    }

    private static final String TEMP_HASH = "GWwRaQFPCcKeDuJNKpzRMQ";
    @Subscribe
    public void onVideoLinksReceived(ArrayList<MovieTableItem> movieTableItems){
        if(movieTableItems != null && !movieTableItems.isEmpty()){
            MovieTableItem item = movieTableItems.get(0);
            String source = item.getSource();
            int index = source.indexOf("md5=");
            source = source.substring(0, index + 4);
            source += TEMP_HASH;
            movieDetailView.startVideoStreaming(source);
        }else{
            movieDetailView.hideLoadingBar();
            movieDetailView.showToast("Movie isn't published yet, Please try later.");
        }
    }
}
