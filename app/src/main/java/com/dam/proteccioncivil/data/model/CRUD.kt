package com.dam.proteccioncivil.data.model

interface CRUD<T> {
    fun getAll()
    fun deleteBy()
    fun setNew()
    fun update()
}