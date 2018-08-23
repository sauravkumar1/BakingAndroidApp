package com.example.android.bakingapp;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activity.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Same as Espresso's BasicSample, but with an Idling Resource to help with synchronization.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BakingAppActivitiesTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }


    @Test
    public void checkRecipeText_MainActivity() {
        onView(ViewMatchers.withId(R.id.rv_recipe_cards)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withText("Cheesecake")).check(matches(isDisplayed()));
    }
    @Test
    public void checkStep_SingleRecipeActivity1() {
        onView(ViewMatchers.withId(R.id.rv_recipe_cards)).perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));
        onView(ViewMatchers.withId(R.id.rv_recipe_steps)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
    }

    @Test
    public void checkVideoIsVisible_RecipeStepVideoActivity1() {
        onView(ViewMatchers.withId(R.id.rv_recipe_cards)).perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));
        onView(ViewMatchers.withId(R.id.rv_recipe_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.playerView)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}