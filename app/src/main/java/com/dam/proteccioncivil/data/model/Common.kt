package com.dam.proteccioncivil.data.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
