package com.dam.proteccioncivil.ui.screens.infomur

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatDate
import com.dam.proteccioncivil.data.model.Infomur
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.dialogs.DlgConfirmacion
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InfomurBus(
    infomurs: List<Infomur>,
    infomursVM: InfomursVM,
    onShowSnackBar: (String) -> Unit,
    modifier: Modifier,
    onNavUp: () -> Unit,
    refresh: () -> Unit
) {
    val mensage: String
    val contexto = LocalContext.current

    when (infomursVM.infomursMessageState) {
        is InfomursMessageState.Loading -> {
        }

        is InfomursMessageState.Success -> {
            mensage = getString(
                contexto,
                R.string.infomurs_delete_success
            )
            onShowSnackBar(mensage)
            infomursVM.resetInfomurMtoState()
            infomursVM.resetInfoState()
            infomursVM.getAll()
            refresh()
        }

        is InfomursMessageState.Error -> {
            mensage = getString(
                contexto,
                R.string.infomurs_delete_failure
            )
            onShowSnackBar(mensage)
            infomursVM.resetInfoState()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Escudo caravaca de la cruz",
            modifier = modifier.fillMaxSize(),
        )
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            content = {
                items(infomurs) { it ->
                    InfomurCard(
                        infomur = it,
                        onNavUp = { onNavUp() },
                        infomursVM = infomursVM,
                        modifier = modifier,
                        refresh = { refresh() })
                }
            }
        )
        if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = {
                        infomursVM.resetInfomurMtoState()
                        //TODO delete esto es solo para probar
                        infomursVM.setFechaInfomur(LocalDate.now().toString())
                        onNavUp()
                    },
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Añadir")
                }
            }
        }
        if (infomursVM.showDlgConfirmation) {
            DlgConfirmacion(
                mensaje = 0,
                onCancelarClick = {
                    infomursVM.showDlgConfirmation = false
                    refresh()
                },
                onAceptarClick = {
                    infomursVM.showDlgConfirmation = false
                    infomursVM.deleteBy()
                }
            )
        }
    }
}

@Composable
fun InfomurCard(
    infomur: Infomur,
    onNavUp: () -> Unit,
    modifier: Modifier,
    infomursVM: InfomursVM,
    refresh: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = "Infomur ${FormatDate.use(infomur.fechaInfomur)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = modifier
                        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = infomur.descripcion,
                    modifier = modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = infomur.codUsuario1.toString(),
                    modifier = modifier
                        .padding(start = 8.dp)
                )
                Text(
                    text = infomur.codUsuario2.toString(),
                    modifier = modifier
                        .padding(start = 8.dp)
                )
            }
            if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                Column(modifier = modifier.padding(12.dp)) {
                    IconButton(onClick = {
                        infomursVM.resetInfomurMtoState()
                        infomursVM.cloneInfomurMtoState(infomur)
                        infomursVM.showDlgConfirmation = true
                        refresh()
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    IconButton(onClick = {
                        infomursVM.resetInfomurMtoState()
                        infomursVM.cloneInfomurMtoState(infomur)
                        onNavUp()
                    }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
                    }
                }
            }
        }
    }
}
