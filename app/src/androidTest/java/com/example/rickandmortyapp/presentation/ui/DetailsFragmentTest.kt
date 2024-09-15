package com.example.rickandmortyapp.presentation.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rickandmortyapp.data.repository.FakeRepository
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.presentation.adapter.CharacterAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest{
    private val repository = FakeRepository()

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.rvCharacters)).perform(RecyclerViewActions.scrollToPosition<CharacterAdapter.ViewHolder>(0))
        onView(withId(R.id.rvCharacters)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                ViewActions.click()
            ))
    }

    @Test
    fun should_show_character_title_and_description() {
        onView(withId(R.id.tvName))
            .check(ViewAssertions.matches(withText(repository.character1.name)))
        onView(withId(R.id.tvSpecies))
            .check(ViewAssertions.matches(withText(repository.character1.species)))
    }
}