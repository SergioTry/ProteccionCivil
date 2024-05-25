package com.dam.proteccioncivil.data.model

import java.time.LocalDate
import java.time.LocalDateTime
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

