package com.dam.proteccioncivil.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.ui.main.AppScreens
import com.dam.proteccioncivil.ui.main.MainVM
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    mainVM: MainVM,
    showLogin: (Boolean) -> Unit,
    version: String
) {
    LaunchedEffect(key1 = true) {
        mainVM.getPreferences()
        delay(mainVM.uiPrefState.defaultTimeSplash.toInt() * 1000L)
        showLogin(mainVM.uiPrefState.token.isEmpty() || mainVM.uiPrefState.token.isBlank())
    }
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "SplashScreen image",
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = "¡Bienvenido!",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Versión: $version",
                modifier = Modifier.padding(16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.End
            )
        }
    }
}
