package com.dam.proteccioncivil.ui.screens.usuarios

import androidx.lifecycle.ViewModel
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.model.Usuario

class UsuariosVM: CRUD<Usuario>, ViewModel() {
    override fun getAll(): List<Usuario> {
        TODO("Not yet implemented")
    }

    override fun getAllBy(fieldname: String, value: String): List<Usuario> {
        TODO("Not yet implemented")
    }

    override fun deleteBy(fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun update(instance: Usuario, fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun setNew(instance: Usuario) {
        TODO("Not yet implemented")
    }
}