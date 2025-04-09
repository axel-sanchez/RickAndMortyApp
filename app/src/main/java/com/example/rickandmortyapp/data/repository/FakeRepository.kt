package com.example.rickandmortyapp.data.repository

import com.example.rickandmortyapp.core.helpers.DataCharacters
import com.example.rickandmortyapp.data.models.CharactersDTO
import com.example.rickandmortyapp.data.models.CharactersDTO.CharacterRAM
import com.example.rickandmortyapp.domain.models.Character
import com.example.rickandmortyapp.domain.repository.CharacterRepository

/**
 * @author Axel Sanchez
 */
class FakeRepository: CharacterRepository {

    val character1 = Character(1, "Rick Sanchez", "Human", "Alive", "Maleeeeeeeeee")
    val character2 = Character(2, "Morty Smith", "Human", "Alive", "Male")
    val character3 = Character(3, "Summer Smith", "Human", "Alive", "Female")
    val character4 = Character(4, "Beth Smith", "Human", "Alive", "Female")
    val character5 = Character(5, "Jerry Smith", "Human", "Alive", "Male")
    val character6 = Character(6, "Abadango Cluster Princess", "Alien", "Alive", "Female")

    val character1Data = with(character1){
        CharacterRAM(id, name, species, status, gender, created, episode, image,
            CharacterRAM.Location(location?.id?:0, location?.name, location?.url),
            CharacterRAM.Origin(origin?.id?:0, origin?.name, origin?.url),
            type, url, page)
    }

    private val character2Data = with(character2){
        CharacterRAM(id, name, species, status, gender, created, episode, image,
            CharacterRAM.Location(location?.id?:0, location?.name, location?.url),
            CharacterRAM.Origin(origin?.id?:0, origin?.name, origin?.url),
            type, url, page)
    }
    private val character3Data = with(character3){
        CharacterRAM(id, name, species, status, gender, created, episode, image,
            CharacterRAM.Location(location?.id?:0, location?.name, location?.url),
            CharacterRAM.Origin(origin?.id?:0, origin?.name, origin?.url),
            type, url, page)
    }
    private val character4Data = with(character4){
        CharacterRAM(id, name, species, status, gender, created, episode, image,
            CharacterRAM.Location(location?.id?:0, location?.name, location?.url),
            CharacterRAM.Origin(origin?.id?:0, origin?.name, origin?.url),
            type, url, page)
    }
    private val character5Data = with(character5){
        CharacterRAM(id, name, species, status, gender, created, episode, image,
            CharacterRAM.Location(location?.id?:0, location?.name, location?.url),
            CharacterRAM.Origin(origin?.id?:0, origin?.name, origin?.url),
            type, url, page)

    }
    private val character6Data = with(character6){
        CharacterRAM(id, name, species, status, gender, created, episode, image,
            CharacterRAM.Location(location?.id?:0, location?.name, location?.url),
            CharacterRAM.Origin(origin?.id?:0, origin?.name, origin?.url),
            type, url, page)
    }

    private val dataCharacters = DataCharacters.Success(characters = listOf(character1, character2, character3, character4, character5, character6))

    override suspend fun getAllCharacters(page: Int): DataCharacters<List<Character?>?>{
        return dataCharacters
    }

    override suspend fun getCharacter(idCharacter: Int): Character?{
        return character1
    }

    override suspend fun getLocalCharacters(page: Int): List<Character?> {
        return listOf()
    }

    override suspend fun getRemoteCharacters(page: Int): DataCharacters<List<Character?>?>{
        return dataCharacters
    }

    fun getRemoteCharactersData(page: Int): DataCharacters<List<CharacterRAM?>?>{
        return DataCharacters.Success(listOf(character1Data, character2Data, character3Data, character4Data, character5Data, character6Data))
    }

    companion object{
        const val PAGE = 1
    }
}