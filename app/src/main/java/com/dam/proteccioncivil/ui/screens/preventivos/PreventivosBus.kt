package com.dam.proteccioncivil.ui.screens.preventivos


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.ComboBox
import com.dam.proteccioncivil.data.model.FormatVisibleDate
import com.dam.proteccioncivil.data.model.HasNonNullElement
import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.esMayorDeEdad
import com.dam.proteccioncivil.data.model.filtrosPreventivos
import com.dam.proteccioncivil.data.model.filtrosPreventivosLimitados
import com.dam.proteccioncivil.data.model.meses
import com.dam.proteccioncivil.ui.dialogs.DlgConfirmacion
import com.dam.proteccioncivil.ui.dialogs.DlgSeleccionMes
import com.dam.proteccioncivil.ui.theme.AppColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PreventivosBus(
    preventivos: List<Preventivo>,
    preventivosVM: PreventivosVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier,
    onNavUp: () -> Unit,
    onNavDetail: () -> Unit,
    refresh: () -> Unit
) {
    val mensage: String
    val contexto = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var exposed by remember { mutableStateOf(false) }

    var preventivosFiltrados by remember { mutableStateOf(preventivos) }

    if (preventivosVM.preventivoBusState.textoBusqueda != "" && preventivosVM.preventivoBusState.lanzarBusqueda) {
        preventivosFiltrados = preventivos.filter {
            it.titulo.lowercase()
                .contains(preventivosVM.preventivoBusState.textoBusqueda.lowercase())
        }
        preventivosVM.setLanzarBusqueda(false)
    } else if (preventivosVM.preventivoBusState.lanzarBusqueda) {
        preventivosFiltrados = preventivos
        preventivosVM.setLanzarBusqueda(false)
    }

    when (preventivosVM.preventivosMessageState) {
        is PreventivosMessageState.Loading -> {
        }

        is PreventivosMessageState.Success -> {
            mensage = when (preventivosVM.preventivoBusState.isBorrado) {
                true -> getString(contexto, R.string.preventivo_delete_success)
                false -> getString(contexto, R.string.apuntado_success)
                else -> getString(contexto, R.string.desapuntado_success)
            }
            preventivosVM.setIsBorrado(null)
            onShowSnackBar(mensage, true)
            preventivosVM.resetPreventivoState()
            preventivosVM.resetInfoState()
            preventivosVM.getAll()
            refresh()
        }

        is PreventivosMessageState.Error -> {
            mensage = getString(
                contexto,
                R.string.preventivo_delete_failure
            ) + ": " + (preventivosVM.preventivosMessageState as PreventivosMessageState.Error).err
            onShowSnackBar(mensage, false)
            preventivosVM.resetInfoState()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = getString(contexto, R.string.fondo_desc),
            modifier = modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(top = 4.dp, start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                ComboBox(
                    onSelectedChange = {
                        preventivosVM.setComboBoxOptionSelected(it)
                        exposed = false
                        if (preventivosVM.preventivoBusState.comboBoxOptionSelected == "Mes") {
                            preventivosVM.setShowDlgSeleccionMes(true)
                        } else {
                            preventivosVM.getAll()
                            refresh()
                        }
                    },
                    onExpandedChange = { exposed = it },
                    expanded = exposed,
                    options = if (esMayorDeEdad(Token.fechaNacimiento!!)) filtrosPreventivos else filtrosPreventivosLimitados,
                    optionSelected = preventivosVM.preventivoBusState.comboBoxOptionSelected,
                    enabled = preventivosVM.preventivosUiState != PreventivosUiState.Loading,
                    modifier = Modifier
                        .weight(4f)
                )
                OutlinedTextField(
                    value = preventivosVM.preventivoBusState.textoBusqueda,
                    onValueChange = { preventivosVM.setTextoBusqueda(it) },
                    label = {
                        Text(
                            stringResource(id = R.string.buscar_lit),
                        )
                    },
                    modifier = Modifier
                        .weight(6f),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                        focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            preventivosVM.setLanzarBusqueda(true)
                        }
                    ),
                    enabled = preventivosVM.preventivosUiState != PreventivosUiState.Loading,
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary),
                    trailingIcon = {
                        Row {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Color.Blue)
                                    .clickable { preventivosVM.setLanzarBusqueda(true) }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = getString(
                                        contexto,
                                        R.string.buscar_desc
                                    ),
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Color.Red)
                                    .clickable {
                                        preventivosVM.setLanzarBusqueda(false)
                                        preventivosVM.setTextoBusqueda("")
                                        preventivosFiltrados = preventivos
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = getString(
                                        contexto,
                                        R.string.buscar_desc
                                    ),
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                )
            }
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                content = {
                    items(preventivosFiltrados) {
                        PreventivoCard(
                            preventivo = it,
                            onNavUp = onNavUp,
                            modifier = modifier,
                            preventivosVM = preventivosVM,
                            contexto = contexto,
                            action = {
                                preventivosVM.update()
                                preventivosVM.setAction(null)
                            },
                            onNavDetail = { onNavDetail() },
                            apuntado = it.usuarios?.firstOrNull { it.codUsuario == Token.codUsuario } != null
                        )
                    }
                }
            )
        }
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
                    refresh()
                },
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                containerColor = AppColors.Blue,
                modifier = modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CloudSync,
                    contentDescription = getString(contexto, R.string.refresh_desc),
                    tint = AppColors.White
                )
            }
            if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                FloatingActionButton(
                    onClick = {
                        preventivosVM.resetPreventivoState()
                        onNavUp()
                    },
                    containerColor = AppColors.Blue,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = getString(contexto, R.string.anadir_desc),
                        tint = AppColors.White
                    )
                }
            }
        }
    }
    if (preventivosVM.preventivoBusState.showDlgSeleccionMes) {
        DlgSeleccionMes(
            onCancelarClick = { preventivosVM.setShowDlgSeleccionMes(false) },
            onAplicarClick = { numeroMes ->
                preventivosVM.setComboBoxOptionSelected(meses[numeroMes - 1])
                preventivosVM.setShowDlgSeleccionMes(false)
                preventivosVM.getAll()
                refresh()
            }
        )
    }
}

@Composable
fun PreventivoCard(
    preventivo: Preventivo,
    preventivosVM: PreventivosVM,
    onNavUp: () -> Unit,
    onNavDetail: () -> Unit,
    modifier: Modifier,
    contexto: Context,
    action: () -> Unit,
    apuntado: Boolean
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(270.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(AppColors.Posit)
    ) {
        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(if (preventivo.riesgo.toInt() == 0) R.drawable.preventivos else R.drawable.siren_transparent_png),
                    contentDescription = stringResource(if (preventivo.riesgo.toInt() == 0) R.string.preventivos_image else R.string.preventivos_riesgo_image),
                    modifier = modifier
                        .size(80.dp)
                )
                Spacer(modifier = modifier.width(8.dp))
                Column(
                    modifier = modifier
                        .weight(1f)
                        .heightIn(max = 60.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = preventivo.titulo,
                        color = Color.Black
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        preventivosVM.resetPreventivoState()
                        preventivosVM.clonePreventivoState(preventivo)
                        onNavDetail()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.RemoveRedEye,
                            contentDescription = getString(contexto, R.string.eliminar_desc),
                            tint = AppColors.Black
                        )
                    }
                    if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                        IconButton(onClick = {
                            preventivosVM.resetPreventivoState()
                            preventivosVM.clonePreventivoState(preventivo)
                            preventivosVM.setIsBorrado(true)
                            preventivosVM.setShowDlgBorrar(true)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = getString(contexto, R.string.eliminar_desc),
                                tint = AppColors.Black
                            )
                        }
                        IconButton(onClick = {
                            preventivosVM.resetPreventivoState()
                            preventivosVM.clonePreventivoState(preventivo)
                            preventivosVM.updateOriginalState()
                            onNavUp()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = getString(contexto, R.string.editar_desc),
                                tint = AppColors.Black
                            )
                        }
                    }
                }
            }
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        preventivosVM.resetPreventivoState()
                        preventivosVM.clonePreventivoState(preventivo)
                        preventivosVM.setIsBorrado(
                            if (apuntado) {
                                null
                            } else {
                                false
                            }
                        )
                        preventivosVM.setAction(
                            if (apuntado) {
                                "delete"
                            } else {
                                "add"
                            }
                        )
                        preventivosVM.setShowDlgBorrar(true)
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = if (!apuntado) stringResource(id = R.string.apuntar_lit) else stringResource(
                            id = R.string.desapuntar_lit
                        )
                    )
                }
                if (!HasNonNullElement.use(
                        listOf(
                            preventivo.fechaDia2,
                            preventivo.fechaDia3,
                            preventivo.fechaDia4,
                            preventivo.fechaDia5,
                            preventivo.fechaDia6,
                            preventivo.fechaDia7
                        )
                    )
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.fecha_preventivo_lit),
                            color = Color.Black
                        )
                        DayItem(
                            date = preventivo.fechaDia1,
                            modifier = modifier,
                            multipleDays = false
                        )
                    }
                } else {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.fechas_preventivo_lit),
                            color = Color.Black
                        )
                        LazyRow(modifier = modifier.width(290.dp)) {
                            items(
                                listOf(
                                    preventivo.fechaDia1,
                                    preventivo.fechaDia2,
                                    preventivo.fechaDia3,
                                    preventivo.fechaDia4,
                                    preventivo.fechaDia5,
                                    preventivo.fechaDia6,
                                    preventivo.fechaDia7
                                )
                            ) { it ->
                                DayItem(date = it, modifier = modifier, multipleDays = true)
                            }
                        }
                    }
                }
            }
            if (preventivosVM.preventivoBusState.showDlgBorrar) {
                if (preventivosVM.preventivoBusState.isBorrado != null && preventivosVM.preventivoBusState.isBorrado!!) {
                    DlgConfirmacion(
                        mensaje = R.string.preventivo_delete_confirmation,
                        onCancelarClick = {
                            preventivosVM.setShowDlgBorrar(false)
                        },
                        onAceptarClick = {
                            preventivosVM.setShowDlgBorrar(false)
                            preventivosVM.deleteBy()
                        }
                    )
                } else {
                    DlgConfirmacion(
                        mensaje = R.string.apuntado_desapuntado_confirmation,
                        onCancelarClick = {
                            preventivosVM.setShowDlgBorrar(false)
                        },
                        onAceptarClick = {
                            preventivosVM.setShowDlgBorrar(false)
                            action()
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun DayItem(date: String?, modifier: Modifier, multipleDays: Boolean) {
    if (date != null) {
        Column(
            modifier = modifier
                .padding(4.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                .padding(8.dp)
                .then(
                    if (multipleDays) modifier.fillMaxWidth() else modifier.width(80.dp)
                )
        ) {
            if (multipleDays) {
                Checkbox(
                    checked = true,
                    onCheckedChange = {},
                    modifier = modifier.align(Alignment.CenterHorizontally)
                )
            }
            Text(
                text = FormatVisibleDate.use(date),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.align(Alignment.CenterHorizontally),
                color = Color.Black
            )
        }
    }
}