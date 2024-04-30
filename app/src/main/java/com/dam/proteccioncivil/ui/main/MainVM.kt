package com.dam.proteccioncivil.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.dam.proteccioncivil.data.repository.MainRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import java.io.IOException

class MainVM(private val mainRepository: MainRepository) : ViewModel() {
    var uiMainState by mutableStateOf(MainState())
        private set
    var uiInfoState: MainInfoState by mutableStateOf(MainInfoState.Loading)
        private set
    var uiPrefState by mutableStateOf(PrefState())
        private set
    suspend fun getPreferences() {
        viewModelScope.async {
            mainRepository.getPreferences().take(1).collect {
                uiPrefState = uiPrefState.copy(
                    token = it.token,
                    defaultTimeSplash = it.defaultTimeSplash.toString()
                )
                uiMainState = uiMainState.copy(
                    token = uiPrefState.token
                )
            }
        }.await()
    }

    fun savePreferences() {
        try {
            uiPrefState.defaultTimeSplash.toInt()
            viewModelScope.launch {
                uiInfoState = try {
                    mainRepository.savePreferences(uiPrefState.toPreferencias())
                    MainInfoState.Success
                } catch (e: IOException) {
                    MainInfoState.Error
                }
            }
        } catch (e: Exception) {
            uiInfoState = MainInfoState.Error
        }
    }
}