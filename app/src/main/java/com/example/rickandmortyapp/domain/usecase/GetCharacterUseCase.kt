package com.example.rickandmortyapp.domain.usecase

import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.domain.repository.CharacterRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetCharacterUseCase{
    suspend fun call(idCharacter: Int): CharacterRAM?
}

@Singleton
class GetCharacterUseCaseImpl @Inject constructor(private val repository: CharacterRepository): GetCharacterUseCase {
    override suspend fun call(idCharacter: Int): CharacterRAM? {
        return repository.getCharacter(idCharacter)
    }
}