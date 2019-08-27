package ro.alexmamo.themoviedbapp.upcoming_movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import ro.alexmamo.themoviedbapp.R;
import ro.alexmamo.themoviedbapp.databinding.UpcomingMovieDataBinding;

public class UpcomingMoviesAdapter extends PagedListAdapter<UpcomingMovie, UpcomingMoviesAdapter.UpcomingMovieViewHolder> {
    private Context context;
    private OnUpcomingMovieClickListener onUpcomingMovieClickListener;

    UpcomingMoviesAdapter(Context context, OnUpcomingMovieClickListener onUpcomingMovieClickListener) {
        super(diffCallback);
        this.context = context;
        this.onUpcomingMovieClickListener = onUpcomingMovieClickListener;
    }

    @NonNull
    @Override
    public UpcomingMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        UpcomingMovieDataBinding upcomingMovieDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_movie, parent, false);
        return new UpcomingMovieViewHolder(upcomingMovieDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final UpcomingMovieViewHolder holder, int position) {
        UpcomingMovie upcomingMovie = getItem(position);
        if (upcomingMovie != null) {
            holder.bindMovie(upcomingMovie);
        }
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

    class UpcomingMovieViewHolder extends RecyclerView.ViewHolder {
        private UpcomingMovieDataBinding upcomingMovieDataBinding;

        UpcomingMovieViewHolder(UpcomingMovieDataBinding upcomingMovieDataBinding) {
            super(upcomingMovieDataBinding.getRoot());
            this.upcomingMovieDataBinding = upcomingMovieDataBinding;
        }

        void bindMovie(UpcomingMovie upcomingMovie) {
            upcomingMovieDataBinding.setUpcomingMovie(upcomingMovie);
            upcomingMovieDataBinding.setOnUpcomingMovieClickListener(onUpcomingMovieClickListener);
        }
    }

    public interface OnUpcomingMovieClickListener {
        void onUpcomingMovieViewClick(UpcomingMovie upcomingMovie);
    }
}