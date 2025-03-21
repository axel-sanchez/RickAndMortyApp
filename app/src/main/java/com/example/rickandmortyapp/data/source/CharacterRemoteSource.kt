package com.example.rickandmortyapp.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.rickandmortyapp.core.ApiError
import com.example.rickandmortyapp.core.DataCharacters
import com.example.rickandmortyapp.data.service.ApiServiceCharacter
import com.example.rickandmortyapp.data.models.CharactersDTO
import com.example.rickandmortyapp.data.models.CharactersDTO.*
import com.example.rickandmortyapp.helpers.Constants
import com.example.rickandmortyapp.helpers.NetworkHelper
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface CharacterRemoteSource {
    suspend fun getAllCharacters(page: Int): MutableLiveData<DataCharacters<List<CharacterRAM?>?>>
}

@Singleton
class CharacterRemoteSourceImpl @Inject constructor(private val service: ApiServiceCharacter,
                                                    private val networkHelper: NetworkHelper
) : CharacterRemoteSource {
    override suspend fun getAllCharacters(page: Int): MutableLiveData<DataCharacters<List<CharacterRAM?>?>> {
        val mutableLiveData = MutableLiveData<DataCharacters<List<CharacterRAM?>?>>()

        try {
            if (!networkHelper.isOnline()) {
                mutableLiveData.value = DataCharacters.Error(apiError = ApiError.NETWORK_ERROR)
                return mutableLiveData
            }

            val response = service.getCharacters(page)
            if (response.isSuccessful) {
                Log.i("Successful Response", response.toString())

                response.body()?.let { result ->
                    mutableLiveData.value = DataCharacters.Success(characters = result.results?: listOf())
                } ?: kotlin.run {
                    mutableLiveData.value = DataCharacters.Error(apiError = ApiError.GENERIC)
                }
            } else {
                Log.i("Error Response", response.errorBody().toString())
                val apiError = ApiError.GENERIC
                apiError.error = response.message()
                mutableLiveData.value = DataCharacters.Error(apiError = apiError)
            }
        } catch (e: IOException) {
            mutableLiveData.value = DataCharacters.Error(apiError = ApiError.GENERIC)
            Log.e(
                "CharacterRemoteSourceImpl",
                e.message?:"Error al obtener los personajes"
            )
            e.printStackTrace()
        }

        return mutableLiveData
    }
}