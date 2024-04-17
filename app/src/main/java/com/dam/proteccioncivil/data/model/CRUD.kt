package com.dam.proteccioncivil.data.model

interface CRUD<T> {
    fun getAll(): List<T>
    fun getAllBy(fieldname: String, value: String): List<T>
    fun deleteBy(fieldname: String, value: String)
    fun setNew(instance: T)
    fun update(instance: T,fieldname: String, value: String)
}