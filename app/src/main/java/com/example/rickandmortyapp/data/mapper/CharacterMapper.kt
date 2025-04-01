package com.example.rickandmortyapp.data.mapper

import com.example.rickandmortyapp.data.models.CharactersDTO.*
import com.example.rickandmortyapp.domain.models.Character

/**
 * @author Axel Sanchez
 */
fun CharacterRAM.toDomain(): Character {
    return Character(id, name, species, status, gender, created, episode, image, location, origin, type, url, page)
}