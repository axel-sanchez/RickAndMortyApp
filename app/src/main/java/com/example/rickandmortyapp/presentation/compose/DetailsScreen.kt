package com.example.rickandmortyapp.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.rickandmortyapp.data.models.CharactersDTO.*
import com.example.rickandmortyapp.presentation.viewmodel.DetailsViewModel

/**
 * @author Axel Sanchez
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailsScreen(idCharacter: Int, viewModel: DetailsViewModel){

    viewModel.getCharacter(idCharacter)

    val character: CharacterRAM? by viewModel.getCharacterStateFlow()
        .collectAsState()

    val painter = rememberImagePainter(data = character?.image)
    val state = painter.state

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val (tvName, ivImage, tvSpecies, tvGender, tvStatus, tvOrigin, tvLocation, loading) = createRefs()

        character?.let {

            Image(
                modifier = Modifier.constrainAs(ivImage){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
                painter = painter,
                contentDescription = "imagen del caracter en la descripcion"
            )

            if (state is ImagePainter.State.Loading){
                Loading(modifier = Modifier.constrainAs(loading) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            }

            Text(
                text = it.name?:"",
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(tvName){
                    top.linkTo(ivImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.padding(top = 25.dp)
            )

            Text(
                text = it.species?:"",
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(tvSpecies){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tvName.bottom)
                }.padding(top = 25.dp)
            )

            Text(
                text = it.gender?:"",
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(tvGender){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tvSpecies.bottom)
                }.padding(top = 25.dp)
            )

            Text(
                text = it.status?:"",
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(tvStatus){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tvGender.bottom)
                }.padding(top = 25.dp)
            )

            Text(
                text = it.origin?.name?:"",
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(tvOrigin){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tvStatus.bottom)
                }.padding(top = 25.dp)
            )

            Text(
                text = it.location?.name?:"",
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(tvLocation){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tvOrigin.bottom)
                }.padding(top = 25.dp)
            )
        }
    }
}