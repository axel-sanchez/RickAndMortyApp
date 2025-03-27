package com.example.rickandmortyapp.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author Axel Sanchez
 */
@Composable
fun Loading(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorCard(message: String, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = 4.dp,
    ) {
        Text(
            text = message,
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            softWrap = true,
            style = TextStyle(fontSize = 18.sp)
        )
    }
}

@Composable
fun CustomToolbar(
    title: String,
    showBackButton: Boolean = false,
    onBackButtonClick: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        backgroundColor = Color.Black,
        navigationIcon = if (showBackButton) {
            {
                IconButton(onClick = onBackButtonClick) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back", tint = Color.White)
                }
            }
        } else {
            null
        }
    )
}

@Preview
@Composable
fun CustomToolbarPreview() {
    CustomToolbar(title = "Detalles", showBackButton = true)
}