package com.example.rickandmortyapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rickandmortyapp.core.MyApplication
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.databinding.FragmentDetailsBinding
import com.example.rickandmortyapp.domain.usecase.GetCharacterUseCase
import com.example.rickandmortyapp.helpers.Constants.ID_CHARACTER
import com.example.rickandmortyapp.helpers.hide
import com.example.rickandmortyapp.helpers.load
import com.example.rickandmortyapp.helpers.show
import com.example.rickandmortyapp.presentation.viewmodel.DetailsViewModel
import javax.inject.Inject

/**
 * @author Axel Sanchez
 */
class DetailsFragment : Fragment() {

    private var idCharacter: Int = 0

    @Inject
    lateinit var getCharacterUseCase: GetCharacterUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).component.inject(this)
    }

    private val viewModel: DetailsViewModel by viewModels(
        factoryProducer = { DetailsViewModel.DetailsViewModelFactory(getCharacterUseCase) }
    )

    private var fragmentDetailsBinding: FragmentDetailsBinding? = null
    private val binding get() = fragmentDetailsBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentDetailsBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idCharacter = arguments?.getInt(ID_CHARACTER) ?: 0

        viewModel.getCharacter(idCharacter)

        viewModel.getCharacterLiveData().observe(viewLifecycleOwner) { character ->
            updateView(character)
        }
    }

    private fun updateView(character: CharacterRAM?) {

        with(binding) {
            ivImage.load(character?.image)

            character.let { character ->
                character?.name?.let { title ->
                    tvName.text = title
                } ?: tvName.hide()

                character?.species?.let { species ->
                    tvSpecies.text = species
                }?: tvSpecies.hide()

                character?.gender?.let { gender ->
                    tvGender.text = gender
                }?: tvGender.hide()

                character?.status?.let { status ->
                    tvStatus.text = status
                }?: tvStatus.hide()

                character?.origin?.name?.let { origin ->
                    tvOrigin.text = origin
                }?: tvOrigin.hide()

                character?.location?.name?.let { location ->
                    tvLocation.text = location
                }?: tvLocation.hide()
            }
        }
    }
}