package com.example.rickandmortyapp.helpers

import androidx.test.platform.app.InstrumentationRegistry

/**
 * @author Axel Sanchez
 */
object Constants {
    const val ID_CHARACTER = "idCharacter"
    const val ID_IMAGE_VIEW = "imageView"
    const val BASE_URL = "https://rickandmortyapi.com/api/"

    //Endpoints
    const val GET_CHARACTERS = "character/"

    enum class ApiError(var error: String) {
        GENERIC("Hubo un error al obtener los personajes"),
        GENERIC_DETAILS("Hubo un error al obtener los detalles del personaje"),
        EMPTY_CHARACTERS("No se obtuvo ningún personaje"),
        NETWORK_ERROR("Hubo un error en la conexión de internet")
    }

    val isRunningTest = try {
        InstrumentationRegistry.getInstrumentation()
        true
    } catch (e: IllegalStateException) {
        false
    }
}