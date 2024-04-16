package com.dam.proteccioncivil.ui.screens.infomur

import androidx.lifecycle.ViewModel
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.model.Infomurs

class InfomursVM : CRUD<Infomurs,Infomurs>, ViewModel() {
    override fun getAll(): List<Infomurs> {
        TODO("Not yet implemented")
    }

    override fun getAllBy(fieldname: String, value: String): List<Infomurs> {
        TODO("Not yet implemented")
    }

    override fun deleteBy(fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun update(instance: Infomurs, fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun setNew(instance: Infomurs) {
        TODO("Not yet implemented")
    }
}