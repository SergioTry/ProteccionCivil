package com.dam.proteccioncivil.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.ui.screens.ErrorScreen
import com.dam.proteccioncivil.ui.screens.LoadingScreen
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
        is AnunciosUiState.Success -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AnunciosUiState.Error -> ErrorScreen(
            retryAction,
            anunciosUiState.err,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun AnunciosListadoScreen(
    anuncios: List<Anuncio>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        this.items(items = anuncios, key = { anuncio -> anuncio.codAnuncio }) { anuncio ->
            Text(text = anuncio.codAnuncio.toString())
            Text(text = anuncio.texto)
        }
    }
}

