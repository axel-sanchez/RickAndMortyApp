package com.example.rickandmortyapp.helpers

import androidx.test.platform.app.InstrumentationRegistry

/**
 * @author Axel Sanchez
 */
object Constants {
    const val ID_CHARACTER = "idCharacter"
    const val ID_IMAGE_VIEW = "imageView"
    const val BASE_URL = "https://rickandmortyapi.com/api/"

    const val MAX_PAGE = 42 //SI EL SERVICIO CAMBIA LA CANTIDAD DE PÁGINAS ESTO PUEDE FALLAR

    //Endpoints
    const val GET_CHARACTERS = "character/"

    val isRunningTest = try {
        InstrumentationRegistry.getInstrumentation()
        true
    } catch (e: IllegalStateException) {
        false
    }
}