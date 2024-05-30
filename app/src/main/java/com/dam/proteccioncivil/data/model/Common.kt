package com.dam.proteccioncivil.data.model

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.spr.jetpack_loading.components.indicators.BallClipRotateMultipleIndicator
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

//Tiempo máximo de espera
val timeoutMillis: Long = 3500

class ObjectToStringMap {
    companion object {
        fun use(obj: Any): Map<String, String> {
            val map = mutableMapOf<String, String>()

            val declaredFields = obj.javaClass.declaredFields.filter {
                !it.name.startsWith('$') &&
                        !it.name.equals(
                            "companion",
                            ignoreCase = true
                        )
            }

            for (field in declaredFields) {
                field.isAccessible = true
                val value = field.get(obj)

                val strValue = value?.toString() ?: "null"

                var key = field.name.get(0).uppercaseChar() + field.name.substring(1)

                if (key == "Dni") {
                    key = "DNI"
                }

                map[key] = strValue
            }

            return map
        }
    }
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        BallClipRotateMultipleIndicator(
            color = Color(255, 165, 0),
            canvasSize = 170F,
            penThickness = 8.dp
        )
    }
}

class FormatDate {
    companion object {
        fun use(fecha: String? = null): String {
            val fechaFormateada = if (fecha != null) {
                val fechaParseada = try {
                    ZonedDateTime.parse(fecha).toLocalDate()
                } catch (e: DateTimeParseException) {
                    try {
                        LocalDateTime.parse(fecha).toLocalDate()
                    } catch (e: DateTimeParseException) {
                        LocalDate.now()
                    }
                }
                val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                fechaParseada.format(formato)
            } else {
                val fechaActual = LocalDate.now()
                val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                fechaActual.format(formato)
            }
            return fechaFormateada
        }
    }
}

class FormatVisibleDate {
    companion object {
        fun use(fecha: String? = null): String {
            val fechaFormateada = if (fecha != null) {
                val fechaParseada = try {
                    ZonedDateTime.parse(fecha).toLocalDate()
                } catch (e: DateTimeParseException) {
                    try {
                        LocalDateTime.parse(fecha).toLocalDate()
                    } catch (e: DateTimeParseException) {
                        LocalDate.now()
                    }
                }
                val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                fechaParseada.format(formato)
            } else {
                val fechaActual = LocalDate.now()
                val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                fechaActual.format(formato)
            }
            return fechaFormateada
        }
    }
}

class HasNonNullElement {
    companion object {
        fun use(list: List<Any?>): Boolean {
            for (item in list) {
                if (item != null) {
                    return true
                }
            }
            return false
        }
    }
}

class ShortToBoolean {
    companion object {
        fun use(short: Short? = null): Boolean {
            if (short == null || short == 0.toShort()) {
                return false
            } else {
                return true
            }
        }
    }
}

fun esMayorDeEdad(fechaNacimiento: LocalDateTime): Boolean {
    val ahora = LocalDateTime.now()
    val edad = Period.between(fechaNacimiento.toLocalDate(), ahora.toLocalDate()).years
    return edad >= 18
}

@Composable
fun LabelledSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    label: String,
    onCheckedChange: ((Boolean) -> Unit),
    colors: SwitchColors = SwitchDefaults.colors(),
    roundedInt: Int,
    color: Color
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, color = Color.Black),
                shape = RoundedCornerShape(roundedInt.dp)
            )
            .height(56.dp)
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Switch
            )
            .background(color)
            .padding(8.dp)
    )
    {
        Text(
            text = label,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp, end = 16.dp),
            style = TextStyle(Color.Black)
        )
        Switch(
            checked = checked,
            onCheckedChange = null,
            colors = colors,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboBox(
    onSelectedChange: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    expanded: Boolean,
    options: List<String>,
    optionSelected: String?,
    modifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange(!expanded) },
        modifier = modifier
    ) {
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
            ),
            readOnly = true,
            value = optionSelected ?: "",
            onValueChange = { },
            label = { Text("Filtro") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            // Sin este modifier no funciona nada por el focus request
            modifier = Modifier
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        onSelectedChange(selectionOption)
                        onExpandedChange(false)
                    },
                    text = { Text(text = selectionOption) }
                )
            }
            DropdownMenuItem(
                onClick = {
                    onSelectedChange("")
                    onExpandedChange(false)
                },
                text = { Text(text = "BorrarFiltro",color = Color.Red) }
            )
        }
    }
}

val filtrosUsuarios = listOf("+18", "Conductor","Rango")