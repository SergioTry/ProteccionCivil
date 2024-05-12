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

                val key = if (field.name.equals(
                        "texto",
                        ignoreCase = true
                    )
                ) "Texto" else if (field.name.equals(
                        "fechaPublicacion",
                        ignoreCase = true
                    )
                ) "FechaPublicacion" else field.name

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
//                val fechaActual = LocalDate.now()
//                val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
//                fechaActual.format(formato)
                "11/12/2024"
            }
            return fechaFormateada
        }
    }
}