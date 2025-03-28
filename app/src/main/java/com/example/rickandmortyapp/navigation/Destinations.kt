package com.example.rickandmortyapp.navigation

import com.example.rickandmortyapp.helpers.Constants.ID_CHARACTER


/**
 * @author Axel Sanchez
 */
sealed class Destinations(
    var route: String
){
    object CharactersScreen: Destinations("charactersScreen")

    object DetailsScreen: Destinations("detailsScreen/{$ID_CHARACTER}"){
        fun createRoute(idCharacter: String) = "detailsScreen/$idCharacter"
    }
}
