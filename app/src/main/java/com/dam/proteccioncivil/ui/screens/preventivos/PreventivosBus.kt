package com.dam.proteccioncivil.ui.screens.preventivos


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatVisibleDate
import com.dam.proteccioncivil.data.model.HasNonNullElement
import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PreventivosBus(
    preventivos: List<Preventivo>,
    preventivosVM: PreventivosVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier,
    onNavUp: () -> Unit,
    refresh: () -> Unit
) {
    val mensage: String
    val contexto = LocalContext.current
    val options = listOf("Opción 1", "Opción 2", "Opción 3")
    val focusRequester = remember { FocusRequester() }

    when (preventivosVM.preventivosMessageState) {
        is PreventivosMessageState.Loading -> {
        }

        is PreventivosMessageState.Success -> {
            mensage = ContextCompat.getString(
                contexto,
                R.string.usuario_delete_success
            )
            onShowSnackBar(mensage, true)
            preventivosVM.resetPreventivoState()
            // preventivosVM.resetInfoState()
            preventivosVM.getAll()
            refresh()
        }

        is PreventivosMessageState.Error -> {
            mensage = ContextCompat.getString(
                contexto,
                R.string.usuario_delete_failure
            )
            onShowSnackBar(mensage, false)
            //preventivosVM.resetInfoState()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = getString(contexto, R.string.fondo_desc),
            modifier = modifier.fillMaxSize(),
        )
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .border(BorderStroke(1.dp, Color.Black))
                    .height(75.dp)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = "", // Aquí puedes poner el valor del campo de texto
                            onValueChange = { /* Aquí puedes manejar el cambio del valor */ },
                            label = {
                                Text(
                                    stringResource(id = R.string.filtro_lit),
                                    color = Color.Black
                                )
                            },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(
                                    onClick = { preventivosVM.setExpanded(true) }
                                ) {
                                    Icon(Icons.Filled.ArrowDropDown, contentDescription = getString(contexto, R.string.drop_down_desc))
                                }
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1.5f)
                                .padding(8.dp)
                                .focusRequester(focusRequester),
                            textStyle = TextStyle(color = AppColors.Black)
                        )
                        DropdownMenu(
                            expanded = preventivosVM.preventivoBusState.expanded,
                            onDismissRequest = { preventivosVM.setExpanded(false) }
                        ) {
                            options.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        preventivosVM.setExpanded(false)
                                    }
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .weight(2f)
                            .padding(8.dp)
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = "",
                            onValueChange = { },
                            label = {
                                Text(
                                    stringResource(id = R.string.buscar_lit),
                                    color = Color.Black
                                )
                            },
                            modifier = Modifier
                                .weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                errorBorderColor = Color.White,
                                unfocusedBorderColor = Color.White
                            ), textStyle = TextStyle(color = AppColors.Black)
                        )
                        IconButton(
                            onClick = { /* TODO: Implement search action */ },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = getString(contexto, R.string.buscar_desc),
                                tint = AppColors.Black
                            )
                        }
                    }
                }
            }
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                content = {
                    items(preventivos) {
                        PreventivoCard(
                            preventivo = it,
                            onNavUp = onNavUp,
                            modifier = modifier,
                            preventivosVM = preventivosVM,
                            contexto = contexto
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
                    contentDescription = ContextCompat.getString(contexto, R.string.refresh_desc),
                    tint = AppColors.White
                )
            }
            if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                FloatingActionButton(
                    onClick = {},
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
}

@Composable
fun PreventivoCard(
    preventivo: Preventivo,
    preventivosVM: PreventivosVM,
    onNavUp: () -> Unit,
    modifier: Modifier,
    contexto: Context
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(270.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(AppColors.posit)
    ) {
        Column {
            Row(modifier = modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(
                        id =
                        if (preventivo.riesgo.toInt() == 0) {
                            R.drawable.pcc_icono
                        } else {
                            R.drawable.pcc_icono
                        }
                    ),
                    contentDescription = null,
                    modifier = modifier
                        .padding(6.dp)
                        .size(80.dp)
                        .align(Alignment.Top)
                )
                Text(
                    text = preventivo.titulo,
                    modifier = modifier
                        .align(Alignment.CenterVertically),
                    color = Color.Black
                )
                if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            preventivosVM.resetPreventivoState()
                            preventivosVM.clonePreventivoState(preventivo)
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
                    onClick = { /*TODO*/ },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Asignar")
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
