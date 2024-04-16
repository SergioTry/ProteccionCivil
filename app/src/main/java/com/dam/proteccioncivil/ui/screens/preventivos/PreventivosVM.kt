package com.dam.proteccioncivil.ui.screens.preventivos

import androidx.lifecycle.ViewModel
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Preventivos

class PreventivosVM: CRUD<Preventivos,Preventivo >, ViewModel() {
    override fun getAll(): List<Preventivos> {
        TODO("Not yet implemented")
    }

    override fun getAllBy(fieldname: String, value: String): List<Preventivos> {
        TODO("Not yet implemented")
    }

    override fun deleteBy(fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun update(instance: Preventivo, fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun setNew(instance: Preventivo) {
        TODO("Not yet implemented")
    }
}