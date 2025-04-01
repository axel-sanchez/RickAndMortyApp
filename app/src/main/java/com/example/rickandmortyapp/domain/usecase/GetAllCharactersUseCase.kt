package com.example.rickandmortyapp.domain.usecase

import com.example.rickandmortyapp.core.helpers.DataCharacters
import com.example.rickandmortyapp.domain.models.Character
import com.example.rickandmortyapp.domain.repository.CharacterRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetAllCharactersUseCase{
    suspend fun call(page: Int): DataCharacters<List<Character?>?>
}

@Singleton
class GetAllCharactersUseCaseImpl @Inject constructor(private val repository: CharacterRepository):
    GetAllCharactersUseCase {
    override suspend fun call(page: Int): DataCharacters<List<Character?>?> {
        return repository.getAllCharacters(page)
    }
}