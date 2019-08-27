package ro.alexmamo.themoviedbapp.utils;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import retrofit2.Call;
import ro.alexmamo.themoviedbapp.R;

import static ro.alexmamo.themoviedbapp.utils.Constants.POSTER_PATH_BASE_URL;
import static ro.alexmamo.themoviedbapp.utils.Constants.TAG;

public class HelperClass {
    public static String getEntirePosterPathUrl(String posterPath) {
        if (posterPath != null) {
            return POSTER_PATH_BASE_URL + posterPath.replace("\\", "").replace("/", "");
        }
        return "";
    }

    public static void logMessage(String message) {
        Log.d(TAG, message);
    }

    public static void createNewCall(Call<?> call) {
        call.clone();
    }

    @BindingAdapter("android:posterPath")
    public static void setPosterToImageView(ImageView posterImageView, String posterPath) {
        String entirePosterPath = getEntirePosterPathUrl(posterPath);
        Glide.with(posterImageView.getContext()).load(entirePosterPath)
                .placeholder(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(posterImageView);
    }
}