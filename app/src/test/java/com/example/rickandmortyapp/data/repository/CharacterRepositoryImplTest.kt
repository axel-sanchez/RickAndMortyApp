package com.example.rickandmortyapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.rickandmortyapp.data.repository.FakeRepository.Companion.PAGE
import com.example.rickandmortyapp.data.source.CharacterLocalSource
import com.example.rickandmortyapp.data.source.CharacterRemoteSource
import com.example.rickandmortyapp.domain.repository.CharacterRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.BDDMockito.*

class CharacterRepositoryImplTest{
    private val fakeRepository = FakeRepository()

    private val characterRemoteSource: CharacterRemoteSource =
        mock(CharacterRemoteSource::class.java)
    private val characterLocalSource: CharacterLocalSource =
        mock(CharacterLocalSource::class.java)
    private val characterRepository: CharacterRepository = CharacterRepositoryImpl(characterRemoteSource, characterLocalSource)

    @Test
    fun should_calls_to_getRemoteCharacters_when_there_are_not_local_characters(){
        runBlocking {
            val mutableListData = MutableLiveData(fakeRepository.getRemoteCharacters(PAGE))
            given(characterRepository.getLocalCharacters(PAGE)).willReturn(listOf())
            given(characterRemoteSource.getAllCharacters(PAGE)).willReturn(mutableListData)
            characterRepository.getAllCharacters(PAGE)
            verify(characterRemoteSource).getAllCharacters(PAGE)
        }
    }

    @Test
    fun should_not_calls_to_getRemoteCharacters_when_there_are_local_characters(){
        runBlocking {
            given(characterRepository.getLocalCharacters(PAGE)).willReturn(listOf(fakeRepository.character1))
            characterRepository.getAllCharacters(PAGE)
            verify(characterRemoteSource, never()).getAllCharacters(PAGE)
        }
    }
}