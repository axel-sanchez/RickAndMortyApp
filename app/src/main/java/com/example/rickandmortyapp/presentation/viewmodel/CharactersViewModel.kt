package com.example.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.rickandmortyapp.core.helpers.DataCharacters
import com.example.rickandmortyapp.domain.models.Character
import com.example.rickandmortyapp.domain.usecase.GetAllCharactersUseCase
import com.example.rickandmortyapp.core.helpers.Constants.MAX_PAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class CharactersViewModel(private val getAllCharactersUseCase: GetAllCharactersUseCase): ViewModel() {

    var currentPage = 1

    private val isLoading = MutableStateFlow(true)

    private val listData: MutableStateFlow<DataCharacters<List<Character?>?>> =
        MutableStateFlow(DataCharacters.Loading)

    init {
        getCharacters(currentPage)
    }

    fun setData(result: DataCharacters<List<Character?>?>) {
        if (result is DataCharacters.Success) {
            val newCharacters = result.characters ?: emptyList()

            if (newCharacters.isNotEmpty()) {
                val updatedList = (listData.value as? DataCharacters.Success)?.characters.orEmpty() + newCharacters
                listData.value = DataCharacters.Success(updatedList)
                currentPage++
            }
        } else {
            listData.value = result
        }
    }

    fun getCharacters(page: Int) {
        if (page > MAX_PAGE) {
            setIsLoading(false)
            return
        }
        viewModelScope.launch {
            setData(getAllCharactersUseCase.call(page))
        }
    }

    fun getIsLoading() = isLoading
    private fun setIsLoading(value: Boolean){
        isLoading.value = value
    }

    fun getCharacterStateFlow(): StateFlow<DataCharacters<List<Character?>?>> {
        return listData
    }

    class SearchViewModelFactory(private val getAllCharactersUseCase: GetAllCharactersUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetAllCharactersUseCase::class.java).newInstance(getAllCharactersUseCase)
        }
    }
}