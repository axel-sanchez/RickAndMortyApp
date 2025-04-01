package com.example.rickandmortyapp.data.source

import android.util.Log
import com.example.rickandmortyapp.core.helpers.ApiError
import com.example.rickandmortyapp.core.helpers.DataCharacters
import com.example.rickandmortyapp.data.service.ApiServiceCharacter
import com.example.rickandmortyapp.data.models.CharactersDTO.*
import com.example.rickandmortyapp.core.helpers.NetworkHelper
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface CharacterRemoteSource {
    suspend fun getAllCharacters(page: Int): DataCharacters<List<CharacterRAM?>?>
}

@Singleton
class CharacterRemoteSourceImpl @Inject constructor(private val service: ApiServiceCharacter,
                                                    private val networkHelper: NetworkHelper
) : CharacterRemoteSource {
    override suspend fun getAllCharacters(page: Int): DataCharacters<List<CharacterRAM?>?> {

        try {
            if (!networkHelper.isOnline()) {
                return DataCharacters.Error(apiError = ApiError.NETWORK_ERROR)
            }

            val response = service.getCharacters(page)
            if (response.isSuccessful) {
                Log.i("Successful Response", response.toString())

                response.body()?.let { result ->
                    return DataCharacters.Success(characters = result.results?: listOf())
                } ?: kotlin.run {
                    return DataCharacters.Error(apiError = ApiError.GENERIC)
                }
            } else {
                Log.i("Error Response", response.errorBody().toString())
                val apiError = ApiError.GENERIC
                apiError.error = response.message()
                return DataCharacters.Error(apiError = apiError)
            }
        } catch (e: IOException) {
            Log.e(
                "CharacterRemoteSourceImpl",
                e.message?:"Error al obtener los personajes"
            )
            e.printStackTrace()
            return DataCharacters.Error(apiError = ApiError.GENERIC)
        }
    }
}