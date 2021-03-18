package com.android.sample.starwars

import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.android.sample.commons.util.EspressoIdlingResource
import com.android.sample.feature.search.R
import com.android.sample.feature.search.ui.search.StarWarsItemViewHolder
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests
     * significantly more reliable.
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun shouldBeAbleToLaunchMainScreen() {
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldBeAbleToSearchItem() {
        onView(withId(R.id.search_view)).perform(click())
        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("a"), pressImeActionButton())
        Thread.sleep(2000)
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<StarWarsItemViewHolder>(9, click()))

        onView(withText(R.string.label_birth_year)).check(matches(isDisplayed()))
        onView(withText(R.string.label_height)).check(matches(isDisplayed()))
        onView(withText(R.string.label_species)).check(matches(isDisplayed()))
        onView(withText(R.string.label_name)).check(matches(isDisplayed()))
        onView(withText(R.string.label_language)).check(matches(isDisplayed()))
        onView(withText(R.string.label_population)).check(matches(isDisplayed()))
        onView(withText(R.string.label_films)).check(matches(isDisplayed()))
    }
}