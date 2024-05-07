package com.dam.proteccioncivil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

    override fun onStop() {
        super.onStop()
        mainVM?.savePreferences()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProteccionCivilTheme {

    }
}