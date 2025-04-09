package com.example.rickandmortyapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rickandmortyapp.core.helpers.DataCharacters
import com.example.rickandmortyapp.data.models.CharactersDTO
import com.example.rickandmortyapp.data.repository.FakeRepository
import com.example.rickandmortyapp.data.repository.FakeRepository.Companion.PAGE
import com.example.rickandmortyapp.domain.models.Character
import com.example.rickandmortyapp.domain.usecase.GetAllCharactersUseCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class CharactersViewModelTest {
    private val repository = FakeRepository()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun should_update_livedata_with_character_list() {
        val useCase = object : GetAllCharactersUseCase {
            override suspend fun call(page: Int): DataCharacters<List<Character?>?> {
                return repository.getAllCharacters(page)
            }
        }

        val viewModel = CharactersViewModel(useCase)
        runBlocking {
            viewModel.setData(useCase.call(PAGE))
            val dataCharacters = viewModel.getCharacterStateFlow().value
            if (dataCharacters is DataCharacters.Success) {
                assertThat(
                    dataCharacters.characters, Matchers.contains(
                        repository.character1, repository.character2, repository.character3,
                        repository.character4, repository.character5, repository.character6
                    )
                )
            }
        }
    }
}