package com.dam.proteccioncivil.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Preferencias
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class MainRepository(private val context: Context, private val dataStore: DataStore<Preferences>) {
    private companion object {
        val TOKEN = stringPreferencesKey("token")
        val DEFAULT_TIME_SPLASH = intPreferencesKey("default_time_splash")
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
                preferences[TOKEN]
                    ?: context.getString(R.string.value_pref_token),
                preferences[DEFAULT_TIME_SPLASH]
                    ?: context.getString(R.string.value_pref_defaultsplashtime).toInt()
            )
        }

    suspend fun savePreferences(prefs: Preferencias) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = prefs.token
            preferences[DEFAULT_TIME_SPLASH] = prefs.defaultTimeSplash
        }
    }
}