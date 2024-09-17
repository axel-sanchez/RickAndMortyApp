package com.example.rickandmortyapp.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortyapp.data.service.ApiServiceCharacter
import com.example.rickandmortyapp.data.models.DataCharacters
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.helpers.Constants
import com.example.rickandmortyapp.helpers.Constants.PAGE_SIZE
import com.example.rickandmortyapp.helpers.NetworkHelper
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface CharacterRemoteSource {
    suspend fun getAllCharacters(page: Int): MutableLiveData<DataCharacters>
    fun getCharacters(): Flow<PagingData<CharacterRAM>>
}

@Singleton
class CharacterRemoteSourceImpl @Inject constructor(private val service: ApiServiceCharacter,
                                                    private val networkHelper: NetworkHelper
) : CharacterRemoteSource {

    override fun getCharacters(): Flow<PagingData<CharacterRAM>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = {CharacterPagingSource(service)}
        ).flow
    }
    override suspend fun getAllCharacters(page: Int): MutableLiveData<DataCharacters> {
        val mutableLiveData = MutableLiveData<DataCharacters>()

        try {
            if (!networkHelper.isOnline()) {
                mutableLiveData.value = DataCharacters(apiError = Constants.ApiError.NETWORK_ERROR)
                return mutableLiveData
            }

            val response = service.getCharacters(page)
            if (response.isSuccessful) {
                Log.i("Successful Response", response.toString())

                response.body()?.let { result ->
                    mutableLiveData.value = DataCharacters(results = result.results?: listOf())
                } ?: kotlin.run {
                    mutableLiveData.value = DataCharacters(apiError = Constants.ApiError.GENERIC)
                }
            } else {
                Log.i("Error Response", response.errorBody().toString())
                val apiError = Constants.ApiError.GENERIC
                apiError.error = response.message()
                mutableLiveData.value = DataCharacters(apiError = apiError)
            }
        } catch (e: IOException) {
            mutableLiveData.value = DataCharacters(apiError = Constants.ApiError.GENERIC)
            Log.e(
                "CharacterRemoteSourceImpl",
                e.message?:"Error al obtener los personajes"
            )
            e.printStackTrace()
        }

        return mutableLiveData
    }
}