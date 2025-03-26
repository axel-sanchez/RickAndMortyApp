package com.example.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.rickandmortyapp.core.DataCharacters
import com.example.rickandmortyapp.data.models.CharactersDTO.*
import com.example.rickandmortyapp.domain.usecase.GetAllCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class CharactersViewModel(private val getAllCharactersUseCase: GetAllCharactersUseCase): ViewModel() {

    private val listData: MutableStateFlow<DataCharacters<List<CharacterRAM?>?>> =
        MutableStateFlow(DataCharacters.Loading)


    fun setData(result: DataCharacters<List<CharacterRAM?>?>) {
        listData.value = result
    }

    fun getCharacters(page: Int) {
        viewModelScope.launch {
            setData(getAllCharactersUseCase.call(page))
        }
    }

    fun getCharacterLiveData(): StateFlow<DataCharacters<List<CharacterRAM?>?>> {
        return listData
    }

    class SearchViewModelFactory(private val getAllCharactersUseCase: GetAllCharactersUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetAllCharactersUseCase::class.java).newInstance(getAllCharactersUseCase)
        }
    }
}