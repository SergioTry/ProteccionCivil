package com.dam.proteccioncivil.ui.screens.vehiculos

import androidx.lifecycle.ViewModel
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.model.Vehiculo

class VehiculosVM : CRUD<Vehiculo>, ViewModel() {
    override fun getAll(): List<Vehiculo> {
        TODO("Not yet implemented")
    }

    override fun getAllBy(fieldname: String, value: String): List<Vehiculo> {
        TODO("Not yet implemented")
    }

    override fun deleteBy(fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun update(instance: Vehiculo, fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun setNew(instance: Vehiculo) {
        TODO("Not yet implemented")
    }
}