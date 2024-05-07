package com.dam.proteccioncivil.ui.screens.preferencias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.main.MainVM


@Composable
fun PrefScreen(
    mainVM: MainVM,
    onNavUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top
        ) {
            LabelledSwitch(
                checked = mainVM.uiPrefState.token.isNotEmpty() && mainVM.uiPrefState.token.isNotBlank(),
                //label = stringResource(R.string.txt_show_log_on_start),
                label = "Inicio de sesión automático",
                onCheckedChange = {
                    if (mainVM.uiPrefState.token.isNotEmpty() && mainVM.uiPrefState.token.isNotBlank()) {
                        mainVM.resetToken()
                    } else {
                        mainVM.setToken(Token.token!!)
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Blue,
                    uncheckedThumbColor = Color.Gray
                )
            )
        }
    }
}


@Composable
fun LabelledSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    label: String,
    onCheckedChange: ((Boolean) -> Unit),
    colors: SwitchColors = SwitchDefaults.colors()
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Switch
            )
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = 16.dp)
        )

        Switch(
            checked = checked,
            onCheckedChange = null,
            colors = colors,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}