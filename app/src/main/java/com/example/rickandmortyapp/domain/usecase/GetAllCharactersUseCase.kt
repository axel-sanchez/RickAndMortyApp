package com.example.rickandmortyapp.domain.usecase

import androidx.paging.PagingData
import com.example.rickandmortyapp.data.models.DataCharacters
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetAllCharactersUseCase{
    fun call(): Flow<PagingData<CharacterRAM>>
}

@Singleton
class GetAllCharactersUseCaseImpl @Inject constructor(private val repository: CharacterRepository):
    GetAllCharactersUseCase {
    override fun call(): Flow<PagingData<CharacterRAM>> {
        return repository.getAllCharacters()
    }
}