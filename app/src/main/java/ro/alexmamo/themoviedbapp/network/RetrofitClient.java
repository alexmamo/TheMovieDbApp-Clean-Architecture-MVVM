package ro.alexmamo.themoviedbapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ro.alexmamo.themoviedbapp.utils.Constants.BASE_URL;

public class RetrofitClient {
    private static RetrofitClient retrofitClient;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(retrofitClient == null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public TheMovieDbApi getTheMovieDbApi(){
        return retrofit.create(TheMovieDbApi.class);
    }
}