package com.dam.proteccioncivil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.spr.jetpack_loading.components.indicators.BallClipRotateMultipleIndicator

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .zIndex(1f)
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        BallClipRotateMultipleIndicator(
            color = Color(255, 165, 0),
            canvasSize = 170F,
            penThickness = 8.dp
        )
    }
}

