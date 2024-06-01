package com.dam.proteccioncivil.ui.screens.anuncios

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatDate
import com.dam.proteccioncivil.ui.theme.AppColors
import java.time.LocalDate


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnunciosMto(
    anunciosVM: AnunciosVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    refresh: () -> Unit,
    modifier: Modifier,
    onCancel: () -> Unit
) {

    var mensage: String
    val contexto = LocalContext.current

    if (anunciosVM.anunciosMtoState.codAnuncio == "0") {
        anunciosVM.setFechaPublicacion(FormatDate.use(LocalDate.now().toString()))
    }

    when (anunciosVM.anunciosMessageState) {
        is AnunciosMessageState.Loading -> {
        }

        is AnunciosMessageState.Success -> {
            mensage = if (anunciosVM.anunciosMtoState.codAnuncio.equals("0")) {
                getString(contexto, R.string.anuncio_create_success)
            } else {
                getString(contexto, R.string.anuncio_edit_success)
            }
            onShowSnackBar(mensage, true)
            anunciosVM.resetInfoState()
            anunciosVM.resetAnuncioMtoState()
            anunciosVM.getAll()
            refresh()
        }

        is AnunciosMessageState.Error -> {
            mensage = if (anunciosVM.anunciosMtoState.codAnuncio.equals("0")) {
                getString(contexto, R.string.anuncio_create_failure)
            } else {
                getString(contexto, R.string.anuncio_edit_failure)
            }
            mensage = mensage + ": " + (anunciosVM.anunciosMessageState as AnunciosMessageState.Error).err
            onShowSnackBar(mensage, false)
            anunciosVM.resetInfoState()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = getString(contexto, R.string.fondo_desc),
            modifier = Modifier.fillMaxSize(),
        )
        Card(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
                .heightIn(180.dp, 280.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(AppColors.Posit)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    OutlinedTextField(
                        value = anunciosVM.anunciosMtoState.texto,
                        onValueChange = { anunciosVM.setTexto(it) },
                        label = {
                            Text(
                                text = stringResource(id = R.string.lit_texto),
                                color = AppColors.Black
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        isError = !anunciosVM.anunciosMtoState.datosObligatorios,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Black,
                            errorBorderColor = Color.Red,
                            errorLabelColor = Color.Red,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                        )
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    anunciosVM.resetAnuncioMtoState()
                 //   anunciosVM.setLoading(true)
                    onCancel()
                },
            //    enabled = !anunciosVM.anunciosBusState.loading,
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.RojoError)
            ) {
                Text(text = stringResource(id = R.string.opc_cancel), color = AppColors.Black)
            }
            Spacer(modifier = Modifier.width(100.dp))
            Button(
                enabled =  anunciosVM.anunciosMtoState.datosObligatorios && anunciosVM.hasStateChanged(),
                onClick = {
                    if (anunciosVM.anunciosMtoState.codAnuncio.equals("0")) {
                        anunciosVM.setFechaPublicacion(FormatDate.use())
                        anunciosVM.setNew()
                    } else {
                        anunciosVM.update()
                    }
             //       anunciosVM.setLoading(true)
                },
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.Blue)
            ) {
                Text(
                    text =
                    if (anunciosVM.anunciosMtoState.codAnuncio.equals("0")) {
                        stringResource(id = R.string.opc_create)
                    } else {
                        stringResource(id = R.string.opc_edit)
                    },
                    color = AppColors.Black
                )
            }
        }
    }
}