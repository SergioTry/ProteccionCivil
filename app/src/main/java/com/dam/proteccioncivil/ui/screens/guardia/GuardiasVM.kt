package com.dam.proteccioncivil.ui.screens.guardia

import androidx.lifecycle.ViewModel
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.model.Guardia

class GuardiasVM : CRUD<Guardia>, ViewModel() {
    override fun getAll(): List<Guardia> {
        TODO("Not yet implemented")
    }

    override fun getAllBy(fieldname: String, value: String): List<Guardia> {
        TODO("Not yet implemented")
    }

    override fun deleteBy(fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun update(instance: Guardia, fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun setNew(instance: Guardia) {
        TODO("Not yet implemented")
    }
}