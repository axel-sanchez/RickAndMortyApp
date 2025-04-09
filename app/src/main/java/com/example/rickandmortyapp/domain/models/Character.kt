package com.example.rickandmortyapp.domain.models

data class Character(
    val id: Int? = null,
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
){
    data class Location(
        val id: Int,
        val name: String? = null,
        val url: String? = null
    )

    data class Origin(
        val id: Int,
        val name: String? = null,
        val url: String? = null
    )
}