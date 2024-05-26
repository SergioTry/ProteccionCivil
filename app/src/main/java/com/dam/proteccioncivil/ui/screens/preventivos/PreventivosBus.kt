package com.dam.proteccioncivil.ui.screens.preventivos

//import com.dam.proteccioncivil.data.model.Dia
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatDate
import com.dam.proteccioncivil.data.model.HasNonNullElement
import com.dam.proteccioncivil.data.model.Preventivo

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
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .border(BorderStroke(1.dp, Color.Black))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Filtrar preventivos",
                        color = Color.Black,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { /*TODO*/ }, modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(8.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(preventivos) {
                        PreventivoCard(
                            it
                        )
                    }
                }
            )
        }
        if (true) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = {},
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Añadir")
                }
            }
        }
    }
}

@Composable
fun PreventivoCard(preventivo: Preventivo) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
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
                    modifier = Modifier
                        .padding(6.dp)
                        .size(40.dp)
                        .align(Alignment.Top)
                )
                Text(
                    text = preventivo.titulo,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                if (true) {
                    Spacer(modifier = Modifier.width(180.dp))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
                    }
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(start = 122.dp)
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
                    Row {
                        Spacer(modifier = Modifier.width(30.dp))
                        Text(
                            text = "Fecha del preventivo: " + preventivo.fechaDia1
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Fechas del preventivo: "
                        )
                        LazyRow {
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
                                horizontalDayList(it)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.width(36.dp))
        }
    }
}

@Composable
fun horizontalDayList(date: String?) {
    if (date != null) {
        Column {
            Checkbox(checked = true, onCheckedChange = {})
            Text(text = FormatDate.use(date))
        }
    }
}
