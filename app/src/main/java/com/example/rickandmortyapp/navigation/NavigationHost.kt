package com.example.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortyapp.helpers.Constants.ID_CHARACTER
import com.example.rickandmortyapp.navigation.Destinations.*
import com.example.rickandmortyapp.presentation.compose.CharactersScreen
import com.example.rickandmortyapp.presentation.compose.DetailsScreen
import com.example.rickandmortyapp.presentation.viewmodel.CharactersViewModel
import com.example.rickandmortyapp.presentation.viewmodel.DetailsViewModel

/**
 * @author Axel Sanchez
 */

@Composable
fun NavigationHost(charactersViewModel: CharactersViewModel, detailsViewModel: DetailsViewModel) {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = CharactersScreen.route){
        composable(CharactersScreen.route){
            CharactersScreen(
                charactersViewModel,
                navigateDetailsScreen = { idCharacter ->
                    navController.navigate(DetailsScreen.createRoute(idCharacter))
                }
            )
        }

        composable(DetailsScreen.route){ navBackStackEntry ->
            val idCharacter = navBackStackEntry.arguments?.getString(ID_CHARACTER)?:""
            DetailsScreen(idCharacter.toInt(), detailsViewModel)
        }
    }
}