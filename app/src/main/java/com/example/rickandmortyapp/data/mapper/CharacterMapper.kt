package com.example.rickandmortyapp.data.mapper

import com.example.rickandmortyapp.data.models.CharactersDTO.*
import com.example.rickandmortyapp.domain.models.Character

/**
 * @author Axel Sanchez
 */
fun CharacterRAM.toDomain(): Character {
    var locationDomain: Character.Location? = null
    var originDomain: Character.Origin? = null
    location?.let {
        locationDomain = Character.Location(it.id, it.name, it.url)
    }
    origin?.let {
        originDomain = Character.Origin(it.id, it.name, it.url)
    }
    return Character(id, name, species, status, gender, created, episode, image, locationDomain, originDomain, type, url, page)
}