package com.dam.proteccioncivil.ui.screens

import androidx.lifecycle.ViewModel
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.model.VehiculoPreventivo

class VehiculosPreventivosVM : CRUD<VehiculoPreventivo>, ViewModel() {
    override fun getAll(): List<VehiculoPreventivo> {
        TODO("Not yet implemented")
    }

    override fun getAllBy(fieldname: String, value: String): List<VehiculoPreventivo> {
        TODO("Not yet implemented")
    }

    override fun deleteBy(fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun update(instance: VehiculoPreventivo, fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun setNew(instance: VehiculoPreventivo) {
        TODO("Not yet implemented")
    }
}