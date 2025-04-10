package com.example.rickandmortyapp.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.domain.models.Character
import com.example.rickandmortyapp.presentation.viewmodel.DetailsViewModel

/**
 * @author Axel Sanchez
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailsScreen(idCharacter: Int, viewModel: DetailsViewModel, navController: NavHostController){

    viewModel.getCharacter(idCharacter)

    val character: Character? by viewModel.getCharacterStateFlow()
        .collectAsState()

    Scaffold(
        topBar = {
            CustomToolbar(title = stringResource(R.string.details), showBackButton = true, onBackButtonClick = {
                navController.popBackStack()
            })
        },
        content = {
            ConstraintLayout(
                modifier = Modifier
                    .padding(paddingValues = it)
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.background_card_gray))
            ) {

                val (ivImage, loading, cardInfo) = createRefs()

                character?.let {

                    val painter = rememberImagePainter(data = character?.image)
                    val state = painter.state

                    Image(
                        modifier = Modifier
                            .constrainAs(ivImage) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                            }
                            .padding(top = 8.dp)
                            .height(200.dp)
                            .width(200.dp),
                        painter = painter,
                        contentDescription = null
                    )

                    if (state is ImagePainter.State.Loading){
                        Loading(modifier = Modifier.constrainAs(loading) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })
                    }

                    Card(
                        modifier = Modifier
                            .constrainAs(cardInfo) {
                                top.linkTo(ivImage.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = Color.White
                    ) {
                        Column {
                            Text(
                                text = it.name?:"",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .testTag("CharacterName")
                                    .padding(top = 16.dp)
                                    .align(CenterHorizontally)
                            )

                            Row(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = stringResource(R.string.gender_title),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .padding(top = 8.dp, start = 8.dp)
                                    )
                                    Text(
                                        text = it.gender?:"",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(top = 4.dp, start = 8.dp)
                                    )

                                    Text(
                                        text = stringResource(R.string.location_title),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .padding(top = 12.dp, start = 8.dp)
                                    )
                                    Text(
                                        text = it.location?.name?:"",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(top = 4.dp, start = 8.dp)
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = stringResource(R.string.specie_title),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .padding(top = 8.dp)
                                    )
                                    Text(
                                        text = it.species?:"",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                    )

                                    Text(
                                        text = stringResource(R.string.status_title),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .padding(top = 12.dp)
                                    )
                                    Text(
                                        text = it.status?:"",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}