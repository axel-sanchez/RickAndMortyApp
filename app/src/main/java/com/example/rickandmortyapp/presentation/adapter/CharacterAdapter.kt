package com.example.rickandmortyapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.databinding.ItemCharacterBinding
import com.example.rickandmortyapp.helpers.hide
import com.example.rickandmortyapp.helpers.load
import com.example.rickandmortyapp.helpers.show

/**
 * @author Axel Sanchez
 */
class CharacterAdapter(
    private val itemClick: (CharacterRAM?, ImageView) -> Unit?
) : PagingDataAdapter<CharacterRAM, CharacterAdapter.ViewHolder>(COMPARATOR) {

    inner class ViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharacterRAM?, itemClick: (CharacterRAM?, ImageView) -> Unit?, position: Int) {

            with(binding) {
                item?.let { character ->
                    itemView.setOnClickListener {
                        itemClick(character, ivCharacter)
                    }

                    character.name?.let { name ->
                        tvName.text = name
                    } ?: kotlin.run { tvName.hide() }

                    character.species?.let { species ->
                        tvSpecies.text = species
                    } ?: kotlin.run { tvSpecies.hide() }

                    character.gender?.let { gender ->
                        tvGender.text = gender
                    } ?: kotlin.run { tvGender.hide() }

                    character.image?.let { urlImage ->
                        if (urlImage.isNotEmpty()) ivCharacter.load(urlImage)
                    } ?: kotlin.run { ivCharacter.hide() }

                    if (position == itemCount - 1) {
                        vSeparator.hide()
                    } else {
                        vSeparator.show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerRowBinding: ItemCharacterBinding =
            ItemCharacterBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), itemClick, position)


    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<CharacterRAM>() {
            override fun areItemsTheSame(oldItem: CharacterRAM, newItem: CharacterRAM): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterRAM, newItem: CharacterRAM): Boolean {
                return oldItem == newItem
            }
        }
    }
}