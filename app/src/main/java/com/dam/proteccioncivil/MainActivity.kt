package com.dam.proteccioncivil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dam.proteccioncivil.ui.main.MainApp
import com.dam.proteccioncivil.ui.main.MainVM
import com.dam.proteccioncivil.ui.theme.ProteccionCivilTheme

class MainActivity : ComponentActivity() {
    private var mainVM: MainVM? = null

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProteccionCivilTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    this.mainVM = viewModel(factory = MainVM.Factory)
                    MainApp(mainVM!!, windowSize.widthSizeClass)
                }
            }
        }
    }

    // Se usa este evento y no el onStop porque al pulsar la opción de salir
    // desde el menú desplegame, la aplicación se cierra pero se queda ejecutando
    // en segundo plano hasta que el usuario no se meta al menú de pestañas del
    // dispositivo y la cierre definitivamente. Esto se hace para mejorar
    // la eficiencia del sistema operativo. Por lo tanto, el evento onStop no se
    // dispara hasta que no se cierre completamente la app, por eso se usa el evento que
    // le precede, el onPause.
    override fun onPause() {
        super.onPause()
        mainVM?.savePreferences()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProteccionCivilTheme {

    }
}