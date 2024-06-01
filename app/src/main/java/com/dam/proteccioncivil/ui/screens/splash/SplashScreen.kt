package com.dam.proteccioncivil.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.ui.main.MainVM
import com.dam.proteccioncivil.ui.screens.login.LoginVM
import com.dam.proteccioncivil.ui.screens.usuarios.UsuariosVM

@Composable
fun SplashScreen(
    mainVM: MainVM,
    loginVM: LoginVM,
    usuariosVM: UsuariosVM,
    showLogin: (Boolean) -> Unit,
    version: String
) {
    val contexto = LocalContext.current

    LaunchedEffect(key1 = true) {
        mainVM.getPreferences(loginVM, showLogin,usuariosVM)
    }
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.pcc_icono),
                contentDescription = getString(contexto, R.string.logo_proteccion_civil_desc),
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = stringResource(id = R.string.bienvenido_lit),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = stringResource(id = R.string.version_text) + " " + version,
                modifier = Modifier
                    .padding(16.dp),
                fontSize = 10.sp,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}
