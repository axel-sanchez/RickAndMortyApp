package com.example.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.domain.usecase.GetCharacterUseCase
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class DetailsViewModel(private val getCharacterUseCase: GetCharacterUseCase) : ViewModel() {

    private val characterLiveData: MutableLiveData<CharacterRAM?> =
        MutableLiveData<CharacterRAM?>()

    private fun setListData(result: CharacterRAM?) {
        characterLiveData.postValue(result)
    }

    fun getCharacter(idCharacter: Int) {
        viewModelScope.launch {
            setListData(getCharacterUseCase.call(idCharacter))
        }
    }

    fun getCharacterLiveData(): LiveData<CharacterRAM?> {
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