package com.dam.proteccioncivil.data.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

                val key = field.name.get(0).uppercaseChar() + field.name.substring(1)

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
                    LocalDate.parse(fecha)
                } catch (e: Exception) {
                    LocalDate.now()
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