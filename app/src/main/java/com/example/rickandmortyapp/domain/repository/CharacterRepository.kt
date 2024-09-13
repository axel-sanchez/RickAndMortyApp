package com.example.rickandmortyapp.domain.repository

import com.example.rickandmortyapp.data.models.DataCharacters
import com.example.rickandmortyapp.data.models.DataCharacters.*

/**
 * @author Axel Sanchez
 */
interface CharacterRepository {
    suspend fun getAllCharacters(page: Int): DataCharacters
    suspend fun getCharacter(idCharacter: Int): CharacterRAM?
    suspend fun getLocalCharacters(page: Int): List<CharacterRAM?>
    suspend fun getRemoteCharacters(page: Int): DataCharacters
}