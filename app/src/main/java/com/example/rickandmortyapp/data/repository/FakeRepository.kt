package com.example.rickandmortyapp.data.repository

import com.example.rickandmortyapp.core.helpers.DataCharacters
import com.example.rickandmortyapp.domain.models.Character
import com.example.rickandmortyapp.domain.repository.CharacterRepository

/**
 * @author Axel Sanchez
 */
class FakeRepository: CharacterRepository {

    val character1 = Character(1, "Rick Sanchez", "Human", "Alive", "Male")
    val character2 = Character(2, "Morty Smith", "Human", "Alive", "Male")
    val character3 = Character(3, "Summer Smith", "Human", "Alive", "Female")
    val character4 = Character(4, "Beth Smith", "Human", "Alive", "Female")
    val character5 = Character(5, "Jerry Smith", "Human", "Alive", "Male")
    val character6 = Character(6, "Abadango Cluster Princess", "Alien", "Alive", "Female")

    private val dataCharacters = DataCharacters.Success(characters = listOf(character1, character2, character3, character4, character5, character6))

    override suspend fun getAllCharacters(page: Int) = dataCharacters

    override suspend fun getCharacter(idCharacter: Int) = character1

    override suspend fun getLocalCharacters(page: Int): List<Character?> {
        return listOf()
    }

    override suspend fun getRemoteCharacters(page: Int) = dataCharacters

    companion object{
        const val PAGE = 1
    }
}