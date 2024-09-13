package com.example.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.rickandmortyapp.data.models.DataCharacters
import com.example.rickandmortyapp.domain.usecase.GetAllCharactersUseCase
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class CharactersViewModel(private val getAllCharactersUseCase: GetAllCharactersUseCase): ViewModel() {

    private val listData: MutableLiveData<DataCharacters> =
        MutableLiveData<DataCharacters>()


    fun setListData(result: DataCharacters) {
        listData.postValue(result)
    }

    fun getCharacters(page: Int) {
        viewModelScope.launch {
            setListData(getAllCharactersUseCase.call(page))
        }
    }

    fun getCharacterLiveData(): LiveData<DataCharacters> {
        return listData
    }

    class SearchViewModelFactory(private val getAllCharactersUseCase: GetAllCharactersUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetAllCharactersUseCase::class.java).newInstance(getAllCharactersUseCase)
        }
    }
}