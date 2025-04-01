package com.example.rickandmortyapp.domain.repository

import com.example.rickandmortyapp.core.helpers.DataCharacters
import com.example.rickandmortyapp.domain.models.Character

/**
 * @author Axel Sanchez
 */
interface CharacterRepository {
    suspend fun getAllCharacters(page: Int): DataCharacters<List<Character?>?>
    suspend fun getCharacter(idCharacter: Int): Character?
    suspend fun getLocalCharacters(page: Int): List<Character?>
    suspend fun getRemoteCharacters(page: Int): DataCharacters<List<Character?>?>
}