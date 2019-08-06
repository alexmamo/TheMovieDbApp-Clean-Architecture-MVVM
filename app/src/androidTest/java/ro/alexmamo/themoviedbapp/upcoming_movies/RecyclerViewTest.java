package ro.alexmamo.themoviedbapp.upcoming_movies;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewTest {
    private View view;

    @Rule
    public ActivityTestRule<UpcomingMoviesActivity> activityRule = new ActivityTestRule<>(UpcomingMoviesActivity.class, true, true);

    @Before
    public void setUp() {
        view = activityRule.getActivity().getWindow().getDecorView();
    }

    @Test
    public void testRecyclerViewVisibility() {
        onView(ViewMatchers.withId(R.id.movies_recycler_view))
                .inRoot(RootMatchers.withDecorView(Matchers.is(view)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testRecyclerViewSecondMovieClick() {
        onView(ViewMatchers.withId(R.id.movies_recycler_view))
                .inRoot(RootMatchers.withDecorView(Matchers.is(view)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }

    @Test
    public void testRecyclerViewScrollToLastItem() {
        RecyclerView recyclerView = activityRule.getActivity().findViewById(R.id.movies_recycler_view);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        int itemCount = 0;
        if (adapter != null) {
            itemCount = adapter.getItemCount();
        }

        onView(ViewMatchers.withId(R.id.movies_recycler_view))
                .inRoot(RootMatchers.withDecorView(Matchers.is(view)))
                .perform(RecyclerViewActions.scrollToPosition(itemCount - 1));
    }
}