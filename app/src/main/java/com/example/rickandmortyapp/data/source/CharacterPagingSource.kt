package com.example.rickandmortyapp.data.source

import android.net.http.HttpException
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.data.service.ApiServiceCharacter
import java.io.IOException

/**
 * @author Axel Sanchez
 */

private const val STARTING_PAGE_INDEX = 1
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
            val response = service.getCharacters(page).body()?.results?.filterNotNull() ?: listOf()
            LoadResult.Page(
                data = response,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: IOException){
            return LoadResult.Error(e)
        } catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }
}