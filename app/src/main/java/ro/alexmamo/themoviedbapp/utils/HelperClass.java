package ro.alexmamo.themoviedbapp.utils;

import android.util.Log;

import retrofit2.Call;

import static ro.alexmamo.themoviedbapp.utils.Constants.POSTER_PATH_BASE_URL;
import static ro.alexmamo.themoviedbapp.utils.Constants.TAG;

public class HelperClass {
    public static String getEntirePosterPathUrl(String posterPath) {
        return POSTER_PATH_BASE_URL + posterPath.replace("\\", "").replace("/", "");
    }

    public static void logMessage(String message) {
        Log.d(TAG, message);
    }

    public static void createNewCall(Call<?> call) {
        call.clone();
    }
}