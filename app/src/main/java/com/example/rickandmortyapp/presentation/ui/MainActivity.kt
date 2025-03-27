package com.example.rickandmortyapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.viewModels
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.core.MyApplication
import com.example.rickandmortyapp.domain.usecase.GetAllCharactersUseCase
import com.example.rickandmortyapp.domain.usecase.GetCharacterUseCase
import com.example.rickandmortyapp.navigation.NavigationHost
import com.example.rickandmortyapp.presentation.theme.RickAndMortyAppTheme
import com.example.rickandmortyapp.presentation.viewmodel.CharactersViewModel
import com.example.rickandmortyapp.presentation.viewmodel.DetailsViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var getAllCharactersUseCase: GetAllCharactersUseCase

    @Inject
    lateinit var getCharacterUseCase: GetCharacterUseCase

    private val charactersViewModel: CharactersViewModel by viewModels(
        factoryProducer = { CharactersViewModel.SearchViewModelFactory(getAllCharactersUseCase) }
    )

    private val detailsViewModel: DetailsViewModel by viewModels(
        factoryProducer = { DetailsViewModel.DetailsViewModelFactory(getCharacterUseCase) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as MyApplication).component.inject(this)

        setContent {
            RickAndMortyAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background){
                    NavigationHost(charactersViewModel, detailsViewModel)
                }
            }
        }
    }
}