package com.example.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.rickandmortyapp.domain.usecase.GetAllCharactersUseCase

/**
 * @author Axel Sanchez
 */
class CharactersViewModel(getAllCharactersUseCase: GetAllCharactersUseCase): ViewModel() {

    val characters = getAllCharactersUseCase.call().cachedIn(viewModelScope)

    class SearchViewModelFactory(private val getAllCharactersUseCase: GetAllCharactersUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetAllCharactersUseCase::class.java).newInstance(getAllCharactersUseCase)
        }
    }
}