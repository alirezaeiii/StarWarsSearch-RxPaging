package com.android.sample.starwars

import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.android.sample.feature.search.R
import com.android.sample.feature.search.ui.search.StarWarsViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

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
        onView(withId(R.id.recyclerView)).perform(
            actionOnItemAtPosition<StarWarsViewHolder>(9, click())
        )

        onView(withText(R.string.label_birth_year)).check(matches(isDisplayed()))
        onView(withText(R.string.label_height)).check(matches(isDisplayed()))
        onView(withText(R.string.label_species)).check(matches(isDisplayed()))
        onView(withText(R.string.label_name)).check(matches(isDisplayed()))
        onView(withText(R.string.label_language)).check(matches(isDisplayed()))
        onView(withText(R.string.label_population)).check(matches(isDisplayed()))
        onView(withText(R.string.label_films)).check(matches(isDisplayed()))
    }
}