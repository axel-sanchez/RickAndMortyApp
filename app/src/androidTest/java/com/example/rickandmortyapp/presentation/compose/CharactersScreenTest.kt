package com.example.rickandmortyapp.presentation.compose

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.rickandmortyapp.data.repository.FakeRepository
import com.example.rickandmortyapp.presentation.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class CharactersScreenTest {
    private val repository = FakeRepository()

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun should_show_list_and_hide_progress_and_message() {
        composeTestRule
            .onNodeWithTag("CharacterList")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("ProgressIndicator")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithTag("EmptyStateCard")
            .assertDoesNotExist()
    }

    @Test
    fun should_list_have_six_elements() {
        composeTestRule
            .onNodeWithTag("CharacterList")
            .assertExists()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag("CharacterItem")
                .fetchSemanticsNodes().size == 6
        }

        composeTestRule
            .onAllNodesWithTag("CharacterItem")
            .assertCountEquals(6)
    }

    @Test
    fun should_show_list_after_back_from_details() {
        composeTestRule
            .onAllNodesWithTag("CharacterItem")[0]
            .performClick()

        composeTestRule.activityRule.scenario.onActivity {
            it.onBackPressedDispatcher.onBackPressed()
        }

        composeTestRule
            .onNodeWithTag("CharacterList")
            .assertIsDisplayed()
    }

    @Test
    fun should_verify_second_element() {
        composeTestRule
            .onAllNodesWithTag("CharacterItem")[0]
            .performClick()

        composeTestRule
            .onNodeWithTag("CharacterName")
            .assertTextEquals(repository.character1.name.toString())
    }

    @Test
    fun should_list_show_correct_info() {
        composeTestRule
            .onAllNodesWithTag("CharacterItem")[3]
            .assert(hasText(repository.character4.name.toString()))

        composeTestRule
            .onAllNodesWithTag("CharacterItem")[0]
            .assert(hasText(repository.character1.species.toString()))
    }
}