package com.example.rickandmortyapp.data.source

import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.data.room.CharacterDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface CharacterLocalSource {
    suspend fun getAllCharacters(page: Int): List<CharacterRAM?>
    suspend fun getCharacter(idCharacter: Int): CharacterRAM?
    suspend fun insertCharacter(character: CharacterRAM)
}

@Singleton
class CharacterLocalSourceImpl @Inject constructor(private val database: CharacterDao):
    CharacterLocalSource {
    override suspend fun getAllCharacters(page: Int): List<CharacterRAM?> {
        return database.getAllCharacters(page)
    }

    override suspend fun insertCharacter(character: CharacterRAM) {
        database.insertCharacter(character)
    }

    override suspend fun getCharacter(idCharacter: Int): CharacterRAM? {
        return database.getCharacter(idCharacter)
    }
}