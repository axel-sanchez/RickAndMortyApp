package com.example.rickandmortyapp.presentation.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.rickandmortyapp.data.repository.FakeRepository
import com.example.rickandmortyapp.data.repository.FakeRepository.Companion.PAGE
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.helpers.RecyclerViewItemCountAssertion
import com.example.rickandmortyapp.helpers.inThePosition
import com.example.rickandmortyapp.presentation.adapter.CharacterAdapter
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharactersFragmentTest{
    private val repository = FakeRepository()

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun should_show_recyclerview_and_hide_progress_and_message() {
        Espresso.onView(withId(R.id.rvCharacters))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.cpiLoading))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
        Espresso.onView(withId(R.id.cvEmptyState))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun should_recyclerview_has_six_elements() {
        Espresso.onView(withId(R.id.rvCharacters))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rvCharacters))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        Espresso.onView(withId(R.id.rvCharacters)).check(RecyclerViewItemCountAssertion(6))
    }

    @Test
    fun should_show_recyclerview_when_press_back_from_details_fragment() {
        Espresso.onView(withId(R.id.rvCharacters))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rvCharacters))
            .perform(RecyclerViewActions.scrollToPosition<CharacterAdapter.ViewHolder>(0))
        Espresso.onView(withId(R.id.rvCharacters)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.pressBack()
        Espresso.onView(withId(R.id.rvCharacters))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.cpiLoading))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
        Espresso.onView(withId(R.id.cvEmptyState))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun should_verify_that_second_element_is_correct(){
        Espresso.onView(withId(R.id.rvCharacters))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rvCharacters))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        Espresso.onView(withId(R.id.rvCharacters)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(withId(R.id.tvName))
            .check(ViewAssertions.matches(withText(repository.character1.name)))
    }

    @Test
    fun should_recyclerview_show_correct_information(){
        Espresso.onView(withId(R.id.rvCharacters)).check(
            ViewAssertions.matches(
                inThePosition(
                    3,
                    ViewMatchers.hasDescendant(withText(repository.character4.name))
                )
            )
        )
        Espresso.onView(withId(R.id.rvCharacters)).check(
            ViewAssertions.matches(
                inThePosition(
                    0,
                    ViewMatchers.hasDescendant(withText(repository.character1.species))
                )
            )
        )
    }
}