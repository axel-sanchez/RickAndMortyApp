package com.example.rickandmortyapp.domain.usecase

import com.example.rickandmortyapp.core.DataCharacters
import com.example.rickandmortyapp.data.models.CharactersDTO
import com.example.rickandmortyapp.data.models.CharactersDTO.*
import com.example.rickandmortyapp.domain.repository.CharacterRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetAllCharactersUseCase{
    suspend fun call(page: Int): DataCharacters<List<CharacterRAM?>?>
}

@Singleton
class GetAllCharactersUseCaseImpl @Inject constructor(private val repository: CharacterRepository):
    GetAllCharactersUseCase {
    override suspend fun call(page: Int): DataCharacters<List<CharacterRAM?>?> {
        return repository.getAllCharacters(page)
    }
}