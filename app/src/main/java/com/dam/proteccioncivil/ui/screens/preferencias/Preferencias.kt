package com.dam.proteccioncivil.ui.screens.preferencias

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.LabelledSwitch
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.main.MainVM


@Composable
fun Preferencias(
    mainVM: MainVM,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(4.dp)
        ) {
            LabelledSwitch(
                checked = mainVM.uiPrefState.username.isNotEmpty() || mainVM.uiPrefState.password.isNotEmpty(),
                label = stringResource(id = R.string.remember_credentials),
                onCheckedChange = {
                    if (mainVM.uiPrefState.username.isNotEmpty() || mainVM.uiPrefState.password.isNotEmpty()) {
                        mainVM.resetCredentials()
                    } else {
                        mainVM.setCredentials(
                            mapOf(
                                "Username" to Token.username!!,
                                "Password" to Token.password!!
                            )
                        )
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = Color.Gray,
                    checkedTrackColor = Color.Gray,
                    uncheckedBorderColor = MaterialTheme.colorScheme.secondary,
                    checkedBorderColor = MaterialTheme.colorScheme.secondary
                ),
                roundedInt = 12,
                backgroundColor = MaterialTheme.colorScheme.background,
                textColor = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}
