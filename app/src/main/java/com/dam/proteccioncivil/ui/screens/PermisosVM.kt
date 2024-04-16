package com.dam.proteccioncivil.ui.screens

import androidx.lifecycle.ViewModel
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.model.Permiso
import com.dam.proteccioncivil.data.model.Permisos

class PermisosVM: CRUD<Permisos,Permiso >, ViewModel() {
    override fun getAll(): List<Permisos> {
        TODO("Not yet implemented")
    }

    override fun getAllBy(fieldname: String, value: String): List<Permisos> {
        TODO("Not yet implemented")
    }

    override fun deleteBy(fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun update(instance: Permiso, fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun setNew(instance: Permiso) {
        TODO("Not yet implemented")
    }
}