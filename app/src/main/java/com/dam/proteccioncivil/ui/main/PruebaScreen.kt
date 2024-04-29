package com.dam.proteccioncivil.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosUiState

@Composable
fun PruebaScreen(
    anunciosUiState: AnunciosUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (anunciosUiState) {
        is AnunciosUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AnunciosUiState.Success -> AnunciosPruebaScreen(
            anunciosUiState.anuncios, contentPadding = contentPadding, modifier = modifier.fillMaxWidth()
        )
        is AnunciosUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun AnunciosPruebaScreen(
    anuncios: List<Anuncio>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = anuncios, key = { anuncio ->  anuncio.CodAnuncio}) { anuncio ->
            Text(text = anuncio.CodAnuncio.toString())
            Text(text = anuncio.Texto)
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loadingcircles_102612),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.clouderror_icon_icons_com_54404), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}