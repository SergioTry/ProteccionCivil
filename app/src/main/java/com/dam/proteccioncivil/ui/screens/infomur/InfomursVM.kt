package com.dam.proteccioncivil.ui.screens.infomur

import androidx.lifecycle.ViewModel
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.model.Infomur

class InfomursVM : CRUD<Infomur>, ViewModel() {
    override fun getAll(): List<Infomur> {
        TODO("Not yet implemented")
    }

    override fun getAllBy(fieldname: String, value: String): List<Infomur> {
        TODO("Not yet implemented")
    }

    override fun deleteBy(fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun setNew(instance: Infomur) {
        TODO("Not yet implemented")
    }

    override fun update(instance: Infomur, fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

}