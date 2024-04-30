package com.dam.proteccioncivil.data.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.dam.proteccioncivil.R
import androidx.datastore.preferences.core.Preferences

class AppDatastore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = context.resources.getString(R.string.db_name)
    )

    fun getDataStore(): DataStore<Preferences> {
        return context.dataStore
    }
}
