package com.dam.proteccioncivil.ui.screens.calendario

import androidx.compose.ui.graphics.Color
import android.provider.CalendarContract
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dam.proteccioncivil.MainApplication
import com.dam.proteccioncivil.data.model.Guardia
import com.dam.proteccioncivil.data.model.Infomur
import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Servicio
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.repository.GuardiasRepository
import com.dam.proteccioncivil.data.repository.InfomursRepository
import com.dam.proteccioncivil.data.repository.PreventivosRepository
import com.google.gson.JsonParser
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarioVM(
    private val guardiasRepository: GuardiasRepository,
    private val infomursRepository: InfomursRepository,
    private val preventivoRepository: PreventivosRepository
) : ViewModel() {

    var calendarioUiState: CalendarioUiState by mutableStateOf(CalendarioUiState.Loading)
        private set


    fun getAll() {
        viewModelScope.launch {
            calendarioUiState = CalendarioUiState.Loading
            calendarioUiState = try {
                val guardias = guardiasRepository.getGuardiasUsuario(Token.codUsuario!!)
                val infomurs = infomursRepository.getInfomursUsuario(Token.codUsuario!!)
                val preventivos = preventivoRepository.getPreventivosUsuario(Token.codUsuario!!)
                val servicios =
                    createServicios(guardias = guardias, infomurs = infomurs, preventivos =  preventivos).groupBy { it.fecha }
                CalendarioUiState.Success(servicios = servicios)
            } catch (e: IOException) {
                CalendarioUiState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (get) ", errorBodyString)
                    CalendarioUiState.Error(error)
                } else {
                    CalendarioUiState.Error("Error")
                }
            }
        }
    }

    fun createServicios(
        guardias: List<Guardia>,
        preventivos: List<Preventivo>,
        infomurs: List<Infomur>
    ): List<Servicio> {
        val serviciosMap = mutableMapOf<LocalDate, Servicio>()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")

        guardias.forEach { guardia ->
            val fecha = LocalDate.parse(guardia.fechaGuardia,formatter)
            val servicio = serviciosMap[fecha] ?: Servicio(fecha, null, null, null)
            serviciosMap[fecha] = servicio.copy(guardia = guardia)
        }

        preventivos.forEach { preventivo ->
            val fechas = listOfNotNull(
                preventivo.fechaDia1,
                preventivo.fechaDia2,
                preventivo.fechaDia3,
                preventivo.fechaDia4,
                preventivo.fechaDia5,
                preventivo.fechaDia6,
                preventivo.fechaDia7
            )
            fechas.forEach { fechaStr ->
                val fecha = LocalDate.parse(fechaStr,formatter)
                val servicio = serviciosMap[fecha] ?: Servicio(fecha, null, null, null)
                serviciosMap[fecha] = servicio.copy(preventivo = preventivo)
            }
        }

        infomurs.forEach { infomur ->
            val fecha = LocalDate.parse(infomur.fechaInfomur,formatter)
            val servicio = serviciosMap[fecha] ?: Servicio(fecha, null, null, null)
            serviciosMap[fecha] = servicio.copy(infomur = infomur)
        }

        return serviciosMap.values.toList()
    }
    fun getServicioColors(servicio: Servicio?): List<Color> {
        if (servicio == null) {
            return emptyList()
        }

        val colors = mutableListOf<Color>()

        if (servicio.guardia != null) {
            colors.add(Color(255, 165, 0))
        }

        if (servicio.infomur != null) {
            colors.add(Color.Blue)
        }

        if (servicio.preventivo != null) {
            colors.add(if (servicio.preventivo.riesgo.toInt() == 0) Color.Green else Color.Red)
        }

        return colors
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val guardiasRepository = application.container.guardiasRepository
                val infomursRepository = application.container.infomursRepository
                val preventivosRepository = application.container.preventivosRepository
                CalendarioVM(
                    guardiasRepository = guardiasRepository,
                    infomursRepository = infomursRepository,
                    preventivoRepository = preventivosRepository
                )
            }
        }
    }
}