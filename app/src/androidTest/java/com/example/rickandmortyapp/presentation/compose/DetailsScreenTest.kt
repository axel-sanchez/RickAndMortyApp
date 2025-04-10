package com.example.rickandmortyapp.presentation.compose

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rickandmortyapp.data.repository.FakeRepository
import com.example.rickandmortyapp.presentation.ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val repository = FakeRepository()

    @Before
    fun navigateToDetails() {
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag("CharacterItem")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onAllNodesWithTag("CharacterItem")[0]
            .performClick()
    }

    @Test
    fun should_show_character_title_and_description() {
        composeTestRule
            .onNodeWithTag("CharacterName")
            .assertTextEquals(repository.character1.name.toString())

        composeTestRule
            .onNodeWithTag("CharacterSpecies")
            .assertTextEquals(repository.character1.species.toString())
    }
}