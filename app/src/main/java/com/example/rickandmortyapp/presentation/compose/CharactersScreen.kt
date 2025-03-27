package com.example.rickandmortyapp.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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

    Scaffold(
        topBar = {
            CustomToolbar(title = stringResource(R.string.characters), showBackButton = false)
        },
        content = {
            ConstraintLayout(
                modifier = Modifier
                    .background(color = colorResource(id = R.color.background_card_gray))
                    .fillMaxSize()
                    .padding(paddingValues = it)
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
    )
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

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 8.dp, end = 8.dp,
                                top = if (index == 0) 8.dp else 0.dp,
                                bottom = 8.dp
                            )
                            .clickable {
                                navigateDetailsScreen(character?.id.toString())
                            },
                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = Color.White
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = rememberImagePainter(character?.image),
                                contentDescription = "imagen del personaje en el item",
                                modifier = Modifier

                                    .width(120.dp)
                                    .height(120.dp), contentScale = ContentScale.Crop
                            )

                            Column(modifier = Modifier.padding(start = 16.dp)) {

                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, end = 10.dp),
                                    text = character?.name ?: "",
                                    style = MaterialTheme.typography.h6,
                                    fontWeight = FontWeight.Bold,
                                    softWrap = true
                                )

                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, end = 10.dp),
                                    text = character?.status.toString(),
                                    style = MaterialTheme.typography.body2,
                                    color = Color.Black,
                                    softWrap = true,
                                    fontSize = 16.sp
                                )

                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, end = 10.dp),
                                    text = character?.species.toString(),
                                    style = MaterialTheme.typography.body2,
                                    color = Color.Black,
                                    softWrap = true,
                                    fontSize = 16.sp
                                )
                            }

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