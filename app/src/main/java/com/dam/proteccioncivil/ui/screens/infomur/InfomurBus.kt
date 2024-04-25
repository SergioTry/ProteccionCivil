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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dam.proteccioncivil.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Infomur(val text: String, val date: LocalDate, val user1: String, val user2: String)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InfomurBusScreen() {
    val sampleMessages = listOf(
        "cxosa", "cxosa",
        "cxosa", "cxosa"
    )
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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = {
                items(sampleMessages.size) { index ->
                    InfomurCard(infomur = Infomur("Descripción", LocalDate.now(), "pepe", "paco"))
                }
            }
        )
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
fun InfomurCard(infomur: Infomur) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = "Infomur ${infomur.date.format(formatter)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = infomur.text,
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = infomur.user1,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
                Text(
                    text = infomur.user2,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
            if (true) {
                Spacer(modifier = Modifier.width(140.dp))
                Column(modifier = Modifier.padding(12.dp)) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun InfomurBusScreenPreview() {
    InfomurBusScreen()
}