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

    val isRunningTest = try {
        InstrumentationRegistry.getInstrumentation()
        true
    } catch (e: IllegalStateException) {
        false
    }
}