package com.example.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.rickandmortyapp.data.models.CharactersDTO.*
import com.example.rickandmortyapp.domain.usecase.GetCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class DetailsViewModel(private val getCharacterUseCase: GetCharacterUseCase) : ViewModel() {

    private val characterLiveData: MutableStateFlow<CharacterRAM?> =
        MutableStateFlow(null)

    private fun setListData(result: CharacterRAM?) {
        characterLiveData.value = result
    }

    fun getCharacter(idCharacter: Int) {
        viewModelScope.launch {
            setListData(getCharacterUseCase.call(idCharacter))
        }
    }

    fun getCharacterStateFlow(): StateFlow<CharacterRAM?> {
        return characterLiveData
    }

    class DetailsViewModelFactory(private val getCharacterUseCase: GetCharacterUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetCharacterUseCase::class.java)
                .newInstance(getCharacterUseCase)
        }
    }
}