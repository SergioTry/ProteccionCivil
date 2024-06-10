package com.dam.proteccioncivil.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Preferencias
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class MainRepository(private val context: Context, private val dataStore: DataStore<Preferences>) {
    private companion object {
        val USERNAME = stringPreferencesKey("username")
        val PASSWORD = stringPreferencesKey("password")
        val INITIALIZATION_VECTOR = stringPreferencesKey("initialization_vector")
    }

    fun getPreferences(): Flow<Preferencias> =
        dataStore.data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            Preferencias(
                preferences[USERNAME]
                    ?: context.getString(R.string.value_pref_token),
                preferences[PASSWORD]
                    ?: context.getString(R.string.value_pref_token),
                preferences[INITIALIZATION_VECTOR]
                    ?: "",
            )
        }

    suspend fun savePreferences(prefs: Preferencias) {
        dataStore.edit { preferences ->
            preferences[USERNAME] = prefs.username
            preferences[PASSWORD] = prefs.password
            preferences[INITIALIZATION_VECTOR] = prefs.iv
        }
    }
}