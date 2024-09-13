package com.example.rickandmortyapp.data.service

import com.example.rickandmortyapp.data.models.DataCharacters
import com.example.rickandmortyapp.helpers.Constants.GET_CHARACTERS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Axel Sanchez
 */
interface ApiServiceCharacter {
    @GET(GET_CHARACTERS)
    suspend fun getCharacters(@Query("page") page: Int): Response<DataCharacters?>
}