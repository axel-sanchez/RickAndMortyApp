package com.example.rickandmortyapp.domain.models

import com.example.rickandmortyapp.data.models.CharactersDTO

data class Character(
    val id: Int? = null,
    val name: String? = null,
    val species: String? = null,
    val status: String? = null,
    val gender: String? = null,
    val created: String? = null,
    val episode: List<String?>? = null,
    val image: String? = null,
    val location: CharactersDTO.CharacterRAM.Location? = null,
    val origin: CharactersDTO.CharacterRAM.Origin? = null,
    val type: String? = null,
    val url: String? = null,
    var page: Int? = null
)
