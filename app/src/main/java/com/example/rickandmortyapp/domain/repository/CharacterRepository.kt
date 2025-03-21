package com.example.rickandmortyapp.domain.repository

import com.example.rickandmortyapp.core.DataCharacters
import com.example.rickandmortyapp.data.models.CharactersDTO
import com.example.rickandmortyapp.data.models.CharactersDTO.*

/**
 * @author Axel Sanchez
 */
interface CharacterRepository {
    suspend fun getAllCharacters(page: Int): DataCharacters<List<CharacterRAM?>?>
    suspend fun getCharacter(idCharacter: Int): CharacterRAM?
    suspend fun getLocalCharacters(page: Int): List<CharacterRAM?>
    suspend fun getRemoteCharacters(page: Int): DataCharacters<List<CharacterRAM?>?>
}