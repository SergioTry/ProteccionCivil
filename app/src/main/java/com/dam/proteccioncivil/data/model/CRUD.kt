package com.dam.proteccioncivil.data.model

interface CRUD<T,S> {
    fun getAll(): List<T>
    fun getAllBy(fieldname: String, value: String): List<T>
    fun deleteBy(fieldname: String, value: String)
    fun setNew(instance: S)
    fun update(instance: S,fieldname: String, value: String)
}