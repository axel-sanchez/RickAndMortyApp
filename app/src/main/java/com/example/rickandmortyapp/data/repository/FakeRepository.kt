package com.example.rickandmortyapp.data.repository

import com.example.rickandmortyapp.data.models.DataCharacters
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.domain.repository.CharacterRepository

/**
 * @author Axel Sanchez
 */
class FakeRepository: CharacterRepository {

    val character1 = CharacterRAM(1, "Rick Sanchez", "Human", "Alive", "Male")
    val character2 = CharacterRAM(2, "Morty Smith", "Human", "Alive", "Male")
    val character3 = CharacterRAM(3, "Summer Smith", "Human", "Alive", "Female")
    val character4 = CharacterRAM(4, "Beth Smith", "Human", "Alive", "Female")
    val character5 = CharacterRAM(5, "Jerry Smith", "Human", "Alive", "Male")
    val character6 = CharacterRAM(6, "Abadango Cluster Princess", "Alien", "Alive", "Female")

    private val dataCharacters = DataCharacters(results = listOf(character1, character2, character3, character4, character5, character6))

    override suspend fun getAllCharacters(page: Int) = dataCharacters

    override suspend fun getCharacter(idCharacter: Int) = character1

    override suspend fun getLocalCharacters(page: Int): List<CharacterRAM?> {
        return listOf()
    }

    override suspend fun getRemoteCharacters(page: Int) = dataCharacters

    companion object{
        const val PAGE = 1
    }
}