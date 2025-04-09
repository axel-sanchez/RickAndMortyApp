package com.example.rickandmortyapp.data.repository

import com.example.rickandmortyapp.core.helpers.ApiError
import com.example.rickandmortyapp.core.helpers.DataCharacters
import com.example.rickandmortyapp.data.mapper.toDomain
import com.example.rickandmortyapp.data.models.CharactersDTO.CharacterRAM
import com.example.rickandmortyapp.domain.repository.CharacterRepository
import com.example.rickandmortyapp.data.source.CharacterLocalSource
import com.example.rickandmortyapp.data.source.CharacterRemoteSource
import com.example.rickandmortyapp.domain.models.Character
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

    override suspend fun getAllCharacters(page: Int): DataCharacters<List<Character?>?> {
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

    override suspend fun getCharacter(idCharacter: Int): Character? {
        return charactersLocalSource.getCharacter(idCharacter)?.toDomain()
    }

    override suspend fun getLocalCharacters(page: Int): List<Character?> {
        return charactersLocalSource.getAllCharacters(page).map { it?.toDomain() }
    }

    override suspend fun getRemoteCharacters(page: Int): DataCharacters<List<Character?>?> {
        return when(val result = charactersRemoteSource.getAllCharacters(page)){
            is DataCharacters.Success -> {
                DataCharacters.Success(result.characters?.map { it?.toDomain() })
            }
            is DataCharacters.Error -> {
                DataCharacters.Error(apiError = result.apiError)
            }
            else -> {
                DataCharacters.Error(apiError = ApiError.GENERIC)
            }
        }
    }

    private suspend fun addCharacterInDB(result: List<Character?>, page: Int) {
        result.forEach { character ->
            character?.page = page
            character?.let {
                charactersLocalSource.insertCharacter(CharacterRAM(it.id, it.name, it.species, it.status, it.gender, it.created, it.episode, it.image,
                    CharacterRAM.Location(it.location?.id?:0, it.location?.name, it.location?.url), CharacterRAM.Origin(it.origin?.id?:0, it.origin?.name, it.origin?.url), it.type, it.url, it.page))
            }
        }
    }
}