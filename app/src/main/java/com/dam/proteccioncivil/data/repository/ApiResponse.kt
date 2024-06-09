package com.dam.proteccioncivil.data.repository


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiResponse(
    @SerialName("error") val error: Boolean,
    @SerialName("status") val status: Int,
    @SerialName("body") val body: JsonElement
)


