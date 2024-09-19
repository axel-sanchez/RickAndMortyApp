package com.example.rickandmortyapp.data.source

import android.net.http.HttpException
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.data.service.ApiServiceCharacter
import com.example.rickandmortyapp.helpers.Constants.STARTING_PAGE_INDEX
import java.io.IOException

/**
 * @author Axel Sanchez
 */

class CharacterPagingSource(private val service: ApiServiceCharacter): PagingSource<Int, CharacterRAM>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterRAM>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    @RequiresApi(34)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterRAM> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {

            val response = service.getCharacters(page)

            if (response.isSuccessful) {
                Log.i("Successful Response", response.toString())
                val characters = response.body()?.results?.filterNotNull() ?: listOf()
                LoadResult.Page(
                    data = characters,
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    nextKey = if (characters.isEmpty()) null else page + 1
                )
            } else {
                Log.i("Error Response", response.errorBody().toString())
                LoadResult.Error(Throwable(response.errorBody().toString()))
            }
        } catch (e: IOException){
            e.printStackTrace()
            return LoadResult.Error(e)
        } catch (e: HttpException){
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }
}