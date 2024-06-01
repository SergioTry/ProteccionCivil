package com.dam.proteccioncivil.ui.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.ui.theme.AppColors

@Composable
fun SobreScreen(
    version: String
) {
    val scrollState = rememberScrollState()
    val contexto = LocalContext.current

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.logochirinostransparente),
                contentDescription = getString(contexto, R.string.logo_chirinos_desc),
                modifier = Modifier.size(160.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.pcc_icono),
                contentDescription = getString(contexto, R.string.logo_proteccion_civil_desc),
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                colors = CardColors(
                    containerColor = AppColors.Posit,
                    contentColor = Color.Black,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.LightGray
                )
            ) {
                Text(
                    text = stringResource(id = R.string.sobre_text),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = stringResource(id = R.string.sobre_descrip_text),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(12.dp)
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
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

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SobreScreen(version = "1.0.0")
}

