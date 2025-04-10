package com.example.rickandmortyapp.presentation.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rickandmortyapp.data.repository.FakeRepository
import org.junit.Rule
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