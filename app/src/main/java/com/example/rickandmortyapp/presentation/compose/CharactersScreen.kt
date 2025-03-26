package com.example.rickandmortyapp.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.rickandmortyapp.core.ApiError
import com.example.rickandmortyapp.core.DataCharacters
import com.example.rickandmortyapp.data.models.CharactersDTO.*
import com.example.rickandmortyapp.presentation.viewmodel.CharactersViewModel
import com.example.rickandmortyapp.R

/**
 * @author Axel Sanchez
 */
@Composable
fun CharactersScreen(viewModel: CharactersViewModel, navigateDetailsScreen: (String) -> Unit) {

    viewModel.getCharacters(1)

    val dataCharacters: DataCharacters<List<CharacterRAM?>?> by viewModel.getCharacterLiveData()
        .collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (emptyState, loading) = createRefs()

        ErrorState(modifier = Modifier.constrainAs(emptyState) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, dataCharacters)

        if (dataCharacters is DataCharacters.Loading) {
            Loading(modifier = Modifier.constrainAs(loading) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        }

        CharacterList(dataCharacters, navigateDetailsScreen)
    }
}

@Composable
fun CharacterList(
    dataCharacters: DataCharacters<List<CharacterRAM?>?>,
    navigateDetailsScreen: (String) -> Unit
) {
    if (dataCharacters is DataCharacters.Success) {
        if (!dataCharacters.characters.isNullOrEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(dataCharacters.characters) { index, character ->
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigateDetailsScreen(character?.id.toString())
                            }) {
                        val (cardImage, txtName, txtPrice, txtSpecies, divider) = createRefs()
                        Card(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .height(150.dp)
                                .width(150.dp)
                                .padding(10.dp)
                                .constrainAs(cardImage) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(txtName.start)
                                }
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                    data = character?.image
                                ),
                                contentDescription = "imagen del producto en el item"
                            )
                        }

                        Text(
                            modifier = Modifier
                                .constrainAs(txtName) {
                                    top.linkTo(cardImage.top)
                                    start.linkTo(cardImage.end)
                                    end.linkTo(parent.end)
                                    width = Dimension.fillToConstraints
                                }
                                .padding(top = 10.dp, end = 10.dp),
                            text = character?.name ?: "",
                            softWrap = true
                        )

                        Text(
                            modifier = Modifier
                                .constrainAs(txtPrice) {
                                    top.linkTo(txtName.bottom)
                                    start.linkTo(cardImage.end)
                                    end.linkTo(parent.end)
                                    width = Dimension.fillToConstraints
                                }
                                .padding(top = 10.dp, end = 10.dp),
                            text = character?.status.toString(),
                            softWrap = true,
                            fontSize = 25.sp
                        )

                        Text(
                            modifier = Modifier
                                .constrainAs(txtSpecies) {
                                    top.linkTo(txtPrice.bottom)
                                    start.linkTo(cardImage.end)
                                    end.linkTo(parent.end)
                                    width = Dimension.fillToConstraints
                                }
                                .padding(top = 10.dp, end = 10.dp),
                            text = character?.species.toString(),
                            softWrap = true,
                            fontSize = 25.sp
                        )

                        if (index != dataCharacters.characters.size - 1) {
                            Divider(
                                modifier = Modifier.constrainAs(divider) {
                                    top.linkTo(cardImage.bottom)
                                },
                                color = colorResource(id = R.color.separator_gray),
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorState(modifier: Modifier, dataCharacters: DataCharacters<List<CharacterRAM?>?>) {
    when (dataCharacters) {
        is DataCharacters.Success -> {
            if (dataCharacters.characters.isNullOrEmpty()) {
                ErrorCard(ApiError.EMPTY_CHARACTERS.error, modifier)
            }
        }
        is DataCharacters.Error -> {
            ErrorCard(dataCharacters.apiError.error, modifier)
        }
        else -> {}
    }
}