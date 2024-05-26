package com.dam.proteccioncivil.pantallas.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.ui.main.MainVM

@Composable
fun MainScreen(
    mainVM: MainVM,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        BackHandler(enabled = true, onBack = {
            mainVM.setShowDlgSalir(true)
        })
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo_removebg_gimp),
            contentDescription = "Escudo de Caravaca De La Cruz",
            modifier = modifier.fillMaxSize(),
        )
    }
}
