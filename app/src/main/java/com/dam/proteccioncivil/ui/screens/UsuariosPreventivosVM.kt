package com.dam.proteccioncivil.ui.screens

import androidx.lifecycle.ViewModel
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.model.UsuarioPreventivo
import com.dam.proteccioncivil.data.model.UsuariosPreventivos

class UsuariosPreventivosVM: CRUD<UsuariosPreventivos,UsuarioPreventivo >, ViewModel() {
    override fun getAll(): List<UsuariosPreventivos> {
        TODO("Not yet implemented")
    }

    override fun getAllBy(fieldname: String, value: String): List<UsuariosPreventivos> {
        TODO("Not yet implemented")
    }

    override fun deleteBy(fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun update(instance: UsuarioPreventivo, fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun setNew(instance: UsuarioPreventivo) {
        TODO("Not yet implemented")
    }
}