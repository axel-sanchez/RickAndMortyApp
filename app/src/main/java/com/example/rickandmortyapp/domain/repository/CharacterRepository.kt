package com.example.rickandmortyapp.domain.repository

import androidx.paging.PagingData
import com.example.rickandmortyapp.data.models.DataCharacters
import com.example.rickandmortyapp.data.models.DataCharacters.*
import kotlinx.coroutines.flow.Flow

/**
 * @author Axel Sanchez
 */
interface CharacterRepository {
    fun getAllCharacters(): Flow<PagingData<CharacterRAM>>
    suspend fun getCharacter(idCharacter: Int): CharacterRAM?
    suspend fun getLocalCharacters(page: Int): List<CharacterRAM?>
    suspend fun getRemoteCharacters(page: Int): DataCharacters
}