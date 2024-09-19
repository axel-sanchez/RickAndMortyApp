package com.example.rickandmortyapp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.core.MyApplication
import com.example.rickandmortyapp.databinding.FragmentCharactersBinding
import com.example.rickandmortyapp.domain.usecase.GetAllCharactersUseCase
import com.example.rickandmortyapp.helpers.hide
import com.example.rickandmortyapp.helpers.show
import com.example.rickandmortyapp.presentation.adapter.CharacterAdapter
import com.example.rickandmortyapp.presentation.viewmodel.CharactersViewModel
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.helpers.Constants.ID_CHARACTER
import com.example.rickandmortyapp.helpers.Constants.ID_IMAGE_VIEW
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

    private val charactersAdapter by lazy { CharacterAdapter(itemClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.characters.collectLatest { pagingData ->
                with(binding) {
                    with(binding.rvCharacters) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = charactersAdapter
                    }

                    charactersAdapter.addLoadStateListener { loadState ->
                        when(loadState.refresh){
                            is LoadState.NotLoading -> {
                                rvCharacters.show()
                                cpiLoading.hide()
                                cvEmptyState.hide()
                            }
                            is LoadState.Loading -> {
                                rvCharacters.hide()
                                cpiLoading.show()
                                cvEmptyState.hide()
                            }
                            is LoadState.Error -> {
                                rvCharacters.hide()
                                cpiLoading.hide()
                                cvEmptyState.show()
                                tvErrorText.text = getString(R.string.error)
                            }
                        }
                    }

                    charactersAdapter.submitData(pagingData)
                }
            }
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