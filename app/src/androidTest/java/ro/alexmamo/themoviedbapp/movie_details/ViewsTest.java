package ro.alexmamo.themoviedbapp.movie_details;

import android.view.View;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ro.alexmamo.themoviedbapp.R;
import ro.alexmamo.themoviedbapp.upcoming_movies.UpcomingMoviesActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class ViewsTest {
    private View view;

    @Rule
    public ActivityTestRule<MovieDetailsActivity> activityRule = new ActivityTestRule<>(MovieDetailsActivity.class, true, true);

    @Before
    public void setUp() {
        view = activityRule.getActivity().getWindow().getDecorView();
    }

    @Test
    public void testImageViewVisibility() {
        onView(ViewMatchers.withId(R.id.poster_path_image_view))
                .inRoot(RootMatchers.withDecorView(Matchers.is(view)))
                .check(matches(isDisplayed()));
    }
}