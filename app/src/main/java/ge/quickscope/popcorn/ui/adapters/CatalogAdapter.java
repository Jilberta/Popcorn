package ge.quickscope.popcorn.ui.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import ge.quickscope.model.items.Movie;
import ge.quickscope.popcorn.R;
import ge.quickscope.popcorn.ui.helpers.RecyclerClickListener;
import ge.quickscope.shared.helper.GlobalConstants;

/**
 * Created by Jay on 7/8/2015.
 */
public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogItemHolder> {
    private Context context;
    private List<Movie> moviesList;
    private RecyclerClickListener recyclerClickListener;

    public CatalogAdapter(List<Movie> moviesList){
        this.moviesList = moviesList;
    }

    public List<Movie> getMoviesList(){
        return this.moviesList;
    }

    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener){
        this.recyclerClickListener = recyclerClickListener;
    }

    @Override
    public CatalogItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catalog_item_layout, parent, false);
        this.context = parent.getContext();

        return new CatalogItemHolder(rowView, this.recyclerClickListener);
    }

    @Override
    public void onBindViewHolder(CatalogItemHolder holder, final int position) {
        Movie chosenMovie = moviesList.get(position);
        holder.titleView.setText(chosenMovie.getTitle());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            holder.coverView.setTransitionName("cover" + position);

        String posterUrl = GlobalConstants.BASE_URL + chosenMovie.getPoster_path();

        Picasso.with(context)
                .load(posterUrl)
                .fit().centerCrop()
                .into(holder.coverView, new Callback() {
                    @Override
                    public void onSuccess() {
                        moviesList.get(position).setMovieReady(true);
                    }

                    @Override
                    public void onError() {
                        System.out.println("Error Loading Poster !!!");
                    }
                });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public boolean isMovieReady(int position) {
        return moviesList.get(position).isMovieReady();
    }

    public void setMoviesList(List<Movie> moviesList){
        this.moviesList.addAll(moviesList);
        notifyDataSetChanged();
    }


    class CatalogItemHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
        private final RecyclerClickListener clickListener;
        TextView titleView;
        ImageView coverView;

        public CatalogItemHolder(View itemView, RecyclerClickListener clickListener) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.title);
            coverView = (ImageView) itemView.findViewById(R.id.cover);
            coverView.setDrawingCacheEnabled(true);
            coverView.setOnTouchListener(this);
            this.clickListener = clickListener;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_MOVE){
                clickListener.onClick(v, getPosition(), event.getX(), event.getY());
            }
            return true;
        }
    }
}
