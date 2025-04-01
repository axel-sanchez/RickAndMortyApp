package com.example.rickandmortyapp.core.helpers

/**
 * @author Axel Sanchez
 */
sealed class DataCharacters<out T>{
    data class Success<T>(val characters: T) : DataCharacters<T>()
    data class Error(val apiError: ApiError) : DataCharacters<Nothing>()
    object Loading : DataCharacters<Nothing>()
}

enum class ApiError(var error: String) {
    GENERIC("Hubo un error al obtener los personajes"),
    GENERIC_DETAILS("Hubo un error al obtener los detalles del personaje"),
    EMPTY_CHARACTERS("No se obtuvo ningún personaje"),
    NETWORK_ERROR("Hubo un error en la conexión de internet")
}