package com.example.rickandmortyapp.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.rickandmortyapp.core.helpers.ApiError
import com.example.rickandmortyapp.core.helpers.DataCharacters
import com.example.rickandmortyapp.presentation.viewmodel.CharactersViewModel
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.domain.models.Character

/**
 * @author Axel Sanchez
 */
@Composable
fun CharactersScreen(viewModel: CharactersViewModel, navigateDetailsScreen: (String) -> Unit) {

    val dataCharacters: DataCharacters<List<Character?>?> by viewModel.getCharacterStateFlow()
        .collectAsState()

    val isLoading: Boolean by viewModel.getIsLoading().collectAsState()

    val listState = rememberLazyListState()

    // Detectar si estamos cerca del final de la lista
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = listState.layoutInfo.totalItemsCount
            totalItems > 10 && lastVisibleItem >= totalItems - 5
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { shouldLoadMore.value }
            .collect { shouldLoad ->
                if (shouldLoad) {
                    viewModel.getCharacters(viewModel.currentPage)
                }
            }
    }

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

                ErrorState(modifier = Modifier.testTag("EmptyStateCard").constrainAs(emptyState) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, dataCharacters)

                if (dataCharacters is DataCharacters.Loading) {
                    Loading(modifier = Modifier.testTag("ProgressIndicator").constrainAs(loading) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                }

                CharacterList(dataCharacters, navigateDetailsScreen, listState, isLoading)
            }
        }
    )
}

@Composable
fun CharacterList(
    dataCharacters: DataCharacters<List<Character?>?>,
    navigateDetailsScreen: (String) -> Unit,
    listState: LazyListState,
    isLoading: Boolean
) {
    if (dataCharacters is DataCharacters.Success) {
        if (!dataCharacters.characters.isNullOrEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()
                .testTag("CharacterList"), state = listState) {
                itemsIndexed(dataCharacters.characters) { index, character ->

                    Card(
                        modifier = Modifier
                            .testTag("CharacterItem")
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

                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorState(modifier: Modifier, dataCharacters: DataCharacters<List<Character?>?>) {
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