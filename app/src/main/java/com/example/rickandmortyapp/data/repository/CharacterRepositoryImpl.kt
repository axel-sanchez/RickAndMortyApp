package com.example.rickandmortyapp.data.repository

import androidx.paging.PagingData
import com.example.rickandmortyapp.data.models.DataCharacters
import com.example.rickandmortyapp.domain.repository.CharacterRepository
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.data.source.CharacterLocalSource
import com.example.rickandmortyapp.data.source.CharacterRemoteSource
import com.example.rickandmortyapp.helpers.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val charactersRemoteSource: CharacterRemoteSource,
    private val charactersLocalSource: CharacterLocalSource
) : CharacterRepository {

    override fun getAllCharacters(): Flow<PagingData<CharacterRAM>> {
        return charactersRemoteSource.getCharacters()
    }

    override suspend fun getCharacter(idCharacter: Int): CharacterRAM? {
        return charactersLocalSource.getCharacter(idCharacter)
    }

    override suspend fun getLocalCharacters(page: Int): List<CharacterRAM?> {
        return charactersLocalSource.getAllCharacters(page)
    }

    override suspend fun getRemoteCharacters(page: Int): DataCharacters {
        return charactersRemoteSource.getAllCharacters(page).value ?: DataCharacters(apiError = Constants.ApiError.GENERIC)
    }

    private suspend fun addCharacterInDB(result: List<CharacterRAM?>, page: Int) {
        result.forEach { character ->
            character?.page = page
            character?.let {
                charactersLocalSource.insertCharacter(it)
            }
        }
    }
}