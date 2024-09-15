package com.example.rickandmortyapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmortyapp.helpers.Constants

data class DataCharacters(
    val info: Info? = null,
    val results: List<CharacterRAM?>? = null,
    var apiError: Constants.ApiError? = null
) {
    data class Info(
        val count: Int? = null,
        val next: String? = null,
        val pages: Int? = null,
        val prev: Any? = null
    )

    @Entity data class CharacterRAM(
        @PrimaryKey val id: Int? = null,
        val name: String? = null,
        val species: String? = null,
        val status: String? = null,
        val gender: String? = null,
        val created: String? = null,
        val episode: List<String?>? = null,
        val image: String? = null,
        val location: Location? = null,
        val origin: Origin? = null,
        val type: String? = null,
        val url: String? = null,
        var page: Int? = null
    ) {
        @Entity data class Location(
            @PrimaryKey(autoGenerate = true) val id: Int,
            val name: String? = null,
            val url: String? = null
        )

        @Entity data class Origin(
            @PrimaryKey(autoGenerate = true) val id: Int,
            val name: String? = null,
            val url: String? = null
        )
    }
}