package com.example.rickandmortyapp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.core.MyApplication
import com.example.rickandmortyapp.data.models.DataCharacters
import com.example.rickandmortyapp.databinding.FragmentCharactersBinding
import com.example.rickandmortyapp.domain.usecase.GetAllCharactersUseCase
import com.example.rickandmortyapp.helpers.hide
import com.example.rickandmortyapp.helpers.show
import com.example.rickandmortyapp.presentation.adapter.CharacterAdapter
import com.example.rickandmortyapp.presentation.viewmodel.CharactersViewModel
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.helpers.Constants
import com.example.rickandmortyapp.helpers.Constants.ID_CHARACTER
import com.example.rickandmortyapp.helpers.Constants.ID_IMAGE_VIEW
import javax.inject.Inject

/**
 * @author Axel Sanchez
 */
class CharactersFragment : Fragment() {

    @Inject lateinit var getAllCharactersUseCase: GetAllCharactersUseCase

    private var fragmentCharactersBinding: FragmentCharactersBinding? = null
    private val binding get() = fragmentCharactersBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentCharactersBinding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentCharactersBinding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).component.inject(this)
    }

    private val viewModel: CharactersViewModel by viewModels(
        factoryProducer = { CharactersViewModel.SearchViewModelFactory(getAllCharactersUseCase) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCharacters(1)

        viewModel.getCharacterLiveData().observe(viewLifecycleOwner) { response ->
            updateView(response)
        }
    }

    private fun updateView(dataCharacters: DataCharacters) {
        with(binding) {

            dataCharacters.results?.let { characters ->
                if (characters.isEmpty()) {
                    rvCharacters.hide()
                    tvErrorText.text = Constants.ApiError.EMPTY_CHARACTERS.error
                    cvEmptyState.show()
                } else {
                    rvCharacters.show()
                    setAdapter(characters)
                }
            }?: kotlin.run {
                tvErrorText.text = dataCharacters.apiError?.error
                cvEmptyState.show()
                rvCharacters.hide()
            }
            cpiLoading.hide()
        }
    }

    private fun setAdapter(characters: List<CharacterRAM?>) {
        val charactersAdapter = CharacterAdapter(characters, itemClick)
        with(binding.rvCharacters) {
            layoutManager = LinearLayoutManager(context)
            adapter = charactersAdapter
        }
    }

    private val itemClick = { character: CharacterRAM?, imageView: ImageView ->
        character?.let {
            val bundle = bundleOf(ID_CHARACTER to it.id)
            val extras = FragmentNavigatorExtras(
                imageView to ID_IMAGE_VIEW
            )
            findNavController().navigate(R.id.action_charactersFragment_to_detailsFragment, bundle, null, extras)
        }
    }
}