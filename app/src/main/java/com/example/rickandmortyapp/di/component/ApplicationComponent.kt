package com.example.rickandmortyapp.di.component

import com.example.rickandmortyapp.di.module.ApplicationModule
import com.example.rickandmortyapp.presentation.ui.CharactersFragment
import com.example.rickandmortyapp.presentation.ui.DetailsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(charactersFragment: CharactersFragment)
    fun inject(detailsFragment: DetailsFragment)
}