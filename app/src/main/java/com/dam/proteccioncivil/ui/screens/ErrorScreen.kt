package com.dam.proteccioncivil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.R

@Composable
fun ErrorScreen(retryAction: () -> Unit, err: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.clouderror_icon_icons_com_54404),
            contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Text(text = err, modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}