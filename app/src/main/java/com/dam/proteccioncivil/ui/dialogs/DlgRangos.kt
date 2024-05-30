package com.dam.proteccioncivil.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.LabelledSwitch
import com.dam.proteccioncivil.ui.theme.AppColors


@Composable
fun DlgRangos(
    onCancelarClick: () -> Unit,
    onAplicarClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var rango by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = onCancelarClick,
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(AppColors.DialogColors)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.range_select), color = Color.Black,
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    textDecoration = TextDecoration.Underline,
                )
                Spacer(modifier = modifier.height(32.dp))
                LabelledSwitch(
                    checked = rango == "Voluntario",
                    label = "Voluntario",
                    onCheckedChange = { rango = "Voluntario" },
                    roundedInt = 16,
                    color = Color.Cyan
                )
                LabelledSwitch(
                    checked = rango == "Voluntario",
                    label = "Voluntario",
                    onCheckedChange = { rango = "Voluntario" },
                    roundedInt = 16,
                    color = Color.Cyan

                )
                LabelledSwitch(
                    checked = rango == "Voluntario",
                    label = "Voluntario",
                    onCheckedChange = { rango = "Voluntario" },
                    roundedInt = 16,
                    color = Color.Cyan
                )
                Spacer(modifier = modifier.height(24.dp))
            }
        }
    }
}
