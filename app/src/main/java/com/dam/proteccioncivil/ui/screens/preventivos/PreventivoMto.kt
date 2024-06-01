package com.dam.proteccioncivil.ui.screens.preventivos

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatVisibleDate
import com.dam.proteccioncivil.ui.dialogs.DlgSeleccionFecha
import com.dam.proteccioncivil.ui.screens.usuarios.UsuariosVM
import com.dam.proteccioncivil.ui.theme.AppColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PreventivoMto(
    preventivosVM: PreventivosVM,
    usuariosVM: UsuariosVM,
    onNavUsuarioDetail: () -> Unit,
    modifier: Modifier
) {
    val scrollState = rememberScrollState()

    if (preventivosVM.preventivoBusState.fechaABorrar != "") {
        preventivosVM.delFecha()
    }

    if (preventivosVM.preventivoMtoState.codPreventivo != -1) {
        preventivosVM.setFechas()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Escudo caravaca de la cruz",
            modifier = Modifier.fillMaxSize(),
        )
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Card(
                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(AppColors.Posit)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Titulo")
                    OutlinedTextField(
                        value = "", onValueChange = {}, modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Fechas"
                    )
                    Box(
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, Color.Black),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .fillMaxWidth()
                    ) {
                        LazyRow {
                            items(preventivosVM.preventivoMtoState.fechas) { it ->
                                Row {
                                    IconButton(
                                        onClick = {
                                            if (it != null) {
                                                preventivosVM.setFechaBorrar(it)
                                            }
                                        },
                                        modifier = Modifier
                                            .padding(8.dp)
                                    ) {
                                        if (it != null) {
                                            Text(
                                                text = FormatVisibleDate.use(it).substring(0, 5),
                                                style = TextStyle(fontSize = 12.sp)
                                            )
                                        }
                                    }
                                }
                            }
                            if (preventivosVM.preventivoMtoState.fechas.size < 7) {
                                item {
                                    IconButton(
                                        onClick = {
                                            preventivosVM.setShowDlgDate(true)
                                        },
                                        modifier = Modifier
                                            .padding(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Añadir usuario"
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Usuarios"
                    )
                    Box(
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, Color.Black),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .fillMaxWidth()
                    ) {
                        LazyRow {
                            items(preventivosVM.preventivoMtoState.usuarios!!) { it ->
                                Row {
                                    IconButton(
                                        onClick = {
                                            usuariosVM.cloneUsuarioMtoState(it)
                                            onNavUsuarioDetail()
                                        },
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .background(
                                                if (it.codUsuario % 2 == 0) {
                                                    Color.LightGray
                                                } else {
                                                    Color.White
                                                }
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AccountBox,
                                            contentDescription = "Añadir usuario"
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                        },
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .background(
                                                if (it.codUsuario % 2 == 0) {
                                                    Color.LightGray
                                                } else {
                                                    Color.White
                                                }
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AccountBox,
                                            contentDescription = "Añadir usuario"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Modelo")
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row {
                    Switch(
                        checked = false,
                        onCheckedChange = {}
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Riesgo", modifier = Modifier.align(Alignment.CenterVertically))
                }
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Descripción"
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { }
                ) {
                    Text(text = "Cancelar")
                }
                Spacer(modifier = Modifier.width(100.dp))
                Button(
                    onClick = {}
                ) {
                    Text(text = "Añadir")
                }
            }
            if (preventivosVM.preventivoBusState.showDlgDate) {
                DlgSeleccionFecha(
                    onClick = {
                        preventivosVM.setShowDlgDate(false)
                        preventivosVM.setNewFecha(it)
                    },
                    modifier = modifier,
                    onDismiss = {
                        preventivosVM.setShowDlgDate(false)
                    }
                )
            }
        }
    }
}
