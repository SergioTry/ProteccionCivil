package com.dam.proteccioncivil.ui.screens.preferencias

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.main.MainVM
import com.dam.proteccioncivil.ui.theme.AppColors


@Composable
fun PrefScreen(
    mainVM: MainVM,
    onNavUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Blue)
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LabelledSwitch(
                checked = mainVM.uiPrefState.username.isNotEmpty() || mainVM.uiPrefState.password.isNotEmpty(),
                label = "Inicio de sesión automático",
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
                    checkedThumbColor = Color.Blue,
                    uncheckedThumbColor = Color.Gray,
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
    Card(
        modifier = modifier
            .padding(8.dp)
            .border(1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(AppColors.White),
        shape = RoundedCornerShape(16.dp)
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
                    .padding(end = 16.dp),
                style = TextStyle(Color.Black)
            )
            Switch(
                checked = checked,
                onCheckedChange = null,
                colors = colors,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}