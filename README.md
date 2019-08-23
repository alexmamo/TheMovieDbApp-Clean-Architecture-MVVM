# TheMovieDbApp

TheMovieDbApp, is an aplication build using Java 8 as an example for creating a API call to [themoviedb.org](https://developers.themoviedb.org/3/getting-started/introduction).

The app displayes of list of upcoming movies in a RecyclerView using a [Retrofit2](https://square.github.io/retrofit/) call. For getting the data, the app uses [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) and the pagination is build using the [Android Pagination Library](https://developer.android.com/topic/libraries/architecture/paging) together with MVVM Architecture Pattern. The app has a search feature which also uses pagination. For dependency injection the app uses [Dagger2](https://dagger.dev/).

On phone the app displays a vertical list of covers in portrait mode while on tablet the app displays a horizontal list of covers in landscape mode.

See it on youtube: https://www.youtube.com/watch?v=q_7Jc_D8PvU
