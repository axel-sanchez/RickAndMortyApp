package com.example.rickandmortyapp.di.component

import com.example.rickandmortyapp.di.module.ApplicationModule
import com.example.rickandmortyapp.navigation.Destinations
import com.example.rickandmortyapp.presentation.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(charactersScreen: Destinations.CharactersScreen)
    fun inject(detailsScreen: Destinations.DetailsScreen)
    fun inject(mainActivity: MainActivity)
}