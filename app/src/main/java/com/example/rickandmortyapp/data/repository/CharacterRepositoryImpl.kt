package com.example.rickandmortyapp.data.repository

import com.example.rickandmortyapp.core.ApiError
import com.example.rickandmortyapp.core.DataCharacters
import com.example.rickandmortyapp.data.models.CharactersDTO
import com.example.rickandmortyapp.domain.repository.CharacterRepository
import com.example.rickandmortyapp.data.models.CharactersDTO.*
import com.example.rickandmortyapp.data.source.CharacterLocalSource
import com.example.rickandmortyapp.data.source.CharacterRemoteSource
import com.example.rickandmortyapp.helpers.Constants
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

    override suspend fun getAllCharacters(page: Int): DataCharacters<List<CharacterRAM?>?> {
        val localCharacters = getLocalCharacters(page)
        if (localCharacters.isNotEmpty()) {
            return DataCharacters.Success(characters = localCharacters)
        }

        val remoteDataCharacters = getRemoteCharacters(page)

        if (remoteDataCharacters is DataCharacters.Success){
            if (!remoteDataCharacters.characters.isNullOrEmpty()) {
                addCharacterInDB(remoteDataCharacters.characters, page)
            }
        }

        return remoteDataCharacters
    }

    override suspend fun getCharacter(idCharacter: Int): CharacterRAM? {
        return charactersLocalSource.getCharacter(idCharacter)
    }

    override suspend fun getLocalCharacters(page: Int): List<CharacterRAM?> {
        return charactersLocalSource.getAllCharacters(page)
    }

    override suspend fun getRemoteCharacters(page: Int): DataCharacters<List<CharacterRAM?>?> {
        return charactersRemoteSource.getAllCharacters(page).value ?: DataCharacters.Error(apiError = ApiError.GENERIC)
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