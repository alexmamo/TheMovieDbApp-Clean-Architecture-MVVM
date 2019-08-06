package ro.alexmamo.themoviedbapp.upcoming_movies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import ro.alexmamo.themoviedbapp.R;

import static ro.alexmamo.themoviedbapp.utils.HelperClass.getEntirePosterPathUrl;

public class UpcomingMoviesAdapter extends PagedListAdapter<UpcomingMovie, UpcomingMoviesAdapter.MovieViewHolder> {
    private Context context;
    private OnUpcomingMovieClickListener onUpcomingMovieClickListener;

    UpcomingMoviesAdapter(Context context, OnUpcomingMovieClickListener onUpcomingMovieClickListener) {
        super(diffCallback);
        this.context = context;
        this.onUpcomingMovieClickListener = onUpcomingMovieClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view, onUpcomingMovieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        UpcomingMovie upcomingMovie = getItem(position);
        holder.bindMovie(upcomingMovie);
    }

    private static DiffUtil.ItemCallback<UpcomingMovie> diffCallback = new DiffUtil.ItemCallback<UpcomingMovie>() {
        @Override
        public boolean areItemsTheSame(UpcomingMovie oldMovie, UpcomingMovie newMovie) {
            return oldMovie.id == newMovie.id;
        }

        @Override
        public boolean areContentsTheSame(UpcomingMovie oldMovie, @NonNull UpcomingMovie newMovie) {
            return oldMovie.equals(newMovie);
        }
    };

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, RequestListener<Drawable> {
        private OnUpcomingMovieClickListener onUpcomingMovieClickListener;
        private ImageView posterPathImageView;
        private ProgressBar progressBar;
        private TextView titleTextView;

        MovieViewHolder(View itemView, OnUpcomingMovieClickListener onUpcomingMovieClickListener) {
            super(itemView);
            this.onUpcomingMovieClickListener = onUpcomingMovieClickListener;
            itemView.setOnClickListener(this);
            posterPathImageView = itemView.findViewById(R.id.poster_path_image_view);
            progressBar = itemView.findViewById(R.id.progress_bar);
            titleTextView = itemView.findViewById(R.id.title_text_view);
        }

        void bindMovie(UpcomingMovie upcomingMovie) {
            showProgressBar();
            setPosterPathImageView(upcomingMovie.posterPath);
            setTitleTextView(upcomingMovie.title);
        }

        private void setPosterPathImageView(String posterPath) {
            if (posterPath != null) {
                String entirePosterPath = getEntirePosterPathUrl(posterPath);
                Glide.with(context).load(entirePosterPath)
                        .listener(this)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(posterPathImageView);
            }
        }

        private void setTitleTextView(String title){
            titleTextView.setText(title);
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            hideProgressBar();
            return false;
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            UpcomingMovie clickedUpcomingMovie = getItem(position);
            onUpcomingMovieClickListener.onUpcomingMovieViewClick(clickedUpcomingMovie);
        }

        private void showProgressBar() {
            progressBar.setVisibility(View.VISIBLE);
        }

        private void hideProgressBar() {
            progressBar.setVisibility(View.GONE);
        }
    }
}