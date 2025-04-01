package com.example.rickandmortyapp.domain.usecase

import com.example.rickandmortyapp.domain.models.Character
import com.example.rickandmortyapp.domain.repository.CharacterRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetCharacterUseCase{
    suspend fun call(idCharacter: Int): Character?
}

@Singleton
class GetCharacterUseCaseImpl @Inject constructor(private val repository: CharacterRepository): GetCharacterUseCase {
    override suspend fun call(idCharacter: Int): Character? {
        return repository.getCharacter(idCharacter)
    }
}