package com.dam.proteccioncivil.ui.screens.preventivos

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
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.model.FormatDate
import com.dam.proteccioncivil.data.model.ObjectToStringMap
import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.esMayorDeEdad
import com.dam.proteccioncivil.data.model.meses
import com.dam.proteccioncivil.data.model.timeoutMillis
import com.dam.proteccioncivil.data.repository.PreventivosRepository
import com.dam.proteccioncivil.data.repository.UsuariosRepository
import com.dam.proteccioncivil.data.repository.VehiculosRepository
import com.google.gson.JsonParser
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale

class PreventivosVM(
    private val preventivoRepository: PreventivosRepository,
    private val usuariosRepository: UsuariosRepository,
    private val vehiculosRepository: VehiculosRepository
) : CRUD<Preventivo>, ViewModel() {
    var uiPreventivoState: StateFlow<PrevState> = MutableStateFlow(PrevState())
        private set

    var preventivosUiState: PreventivosUiState by mutableStateOf(PreventivosUiState.Loading)
        private set

    var preventivosMessageState: PreventivosMessageState by mutableStateOf(PreventivosMessageState.Loading)
        private set

    var preventivoBusState: PreventivoBusState by mutableStateOf(PreventivoBusState())

    var preventivoMtoState: PreventivoMtoState by mutableStateOf(PreventivoMtoState())

    var originalPreventivosMtoState = preventivoMtoState.copy()

    fun datosObligatorios(): Boolean {
        return preventivoMtoState.fechas.isNotEmpty()
                && preventivoMtoState.titulo.isNotBlank()
                && preventivoMtoState.descripcion.isNotBlank()
    }

    fun convertFechas() {
        preventivoMtoState.fechas =
            preventivoMtoState.fechas.filterNotNull().sortedDescending().toMutableList()
        val fechas = preventivoMtoState.fechas + List(7 - preventivoMtoState.fechas.size) { null }

        preventivoMtoState = fechas[0]?.let {
            preventivoMtoState.copy(
                fechaDia1 = fechas[0]!!,
                fechaDia2 = fechas.getOrNull(1),
                fechaDia3 = fechas.getOrNull(2),
                fechaDia4 = fechas.getOrNull(3),
                fechaDia5 = fechas.getOrNull(4),
                fechaDia6 = fechas.getOrNull(5),
                fechaDia7 = fechas.getOrNull(6)
            )
        }!!
    }


    fun updateOriginalState() {
        originalPreventivosMtoState = preventivoMtoState.copy()
    }

    fun setUsuarioBorrar(usuarioBorrar: Boolean) {
        preventivoBusState = preventivoBusState.copy(usuarioBorrar = usuarioBorrar)
    }

    fun setIsBorrado(isBorrado: Boolean?) {
        preventivoBusState = preventivoBusState.copy(isBorrado = isBorrado)
    }

    fun hasStateChanged(): Boolean {
        val current = preventivoMtoState.copy(datosObligatorios = false)
        val original = originalPreventivosMtoState.copy(datosObligatorios = false)
        return current != original
    }

    fun setNewFecha(fecha: String) {
        var fechas = preventivoMtoState.fechas
        fechas.add(fecha)
        preventivoMtoState = preventivoMtoState.copy(
            fechas = fechas
        )
    }

    fun delFecha() {
        if (preventivoBusState.fechaABorrar != "") {
            var fechas = preventivoMtoState.fechas
            fechas.remove(preventivoBusState.fechaABorrar)
            preventivoMtoState = preventivoMtoState.copy(
                fechas = fechas
            )
            setFechaBorrar("")
        }
    }

    fun setFechasForDetail() {
        preventivoMtoState.fechas.clear()
        preventivoMtoState.fechas.addAll(
            listOf(
                FormatDate.use(preventivoMtoState.fechaDia1),
                if (!preventivoMtoState.fechaDia2.isNullOrEmpty()) FormatDate.use(
                    preventivoMtoState.fechaDia2
                ) else null,
                if (!preventivoMtoState.fechaDia3.isNullOrEmpty()) FormatDate.use(
                    preventivoMtoState.fechaDia3
                ) else null,
                if (!preventivoMtoState.fechaDia4.isNullOrEmpty()) FormatDate.use(
                    preventivoMtoState.fechaDia4
                ) else null,
                if (!preventivoMtoState.fechaDia5.isNullOrEmpty()) FormatDate.use(
                    preventivoMtoState.fechaDia5
                ) else null,
                if (!preventivoMtoState.fechaDia6.isNullOrEmpty()) FormatDate.use(
                    preventivoMtoState.fechaDia6
                ) else null,
                if (!preventivoMtoState.fechaDia7.isNullOrEmpty()) FormatDate.use(
                    preventivoMtoState.fechaDia7
                ) else null,
            )
        )
    }

    fun setFechas() {
        if (!hasStateChanged()) {
            preventivoMtoState.fechas.clear()
            preventivoMtoState.fechas.addAll(
                listOf(
                    FormatDate.use(preventivoMtoState.fechaDia1),
                    if (!preventivoMtoState.fechaDia2.isNullOrEmpty()) FormatDate.use(
                        preventivoMtoState.fechaDia2
                    ) else null,
                    if (!preventivoMtoState.fechaDia3.isNullOrEmpty()) FormatDate.use(
                        preventivoMtoState.fechaDia3
                    ) else null,
                    if (!preventivoMtoState.fechaDia4.isNullOrEmpty()) FormatDate.use(
                        preventivoMtoState.fechaDia4
                    ) else null,
                    if (!preventivoMtoState.fechaDia5.isNullOrEmpty()) FormatDate.use(
                        preventivoMtoState.fechaDia5
                    ) else null,
                    if (!preventivoMtoState.fechaDia6.isNullOrEmpty()) FormatDate.use(
                        preventivoMtoState.fechaDia6
                    ) else null,
                    if (!preventivoMtoState.fechaDia7.isNullOrEmpty()) FormatDate.use(
                        preventivoMtoState.fechaDia7
                    ) else null,
                )
            )
            preventivoMtoState.fechas =
                preventivoMtoState.fechas.filterNotNull().filter { it.isNotBlank() }.toMutableList()
            originalPreventivosMtoState.fechas.clear()
            originalPreventivosMtoState.fechas.addAll(
                listOf(
                    FormatDate.use(originalPreventivosMtoState.fechaDia1),
                    if (!originalPreventivosMtoState.fechaDia2.isNullOrEmpty()) FormatDate.use(
                        originalPreventivosMtoState.fechaDia2
                    ) else null,
                    if (!originalPreventivosMtoState.fechaDia3.isNullOrEmpty()) FormatDate.use(
                        originalPreventivosMtoState.fechaDia3
                    ) else null,
                    if (!originalPreventivosMtoState.fechaDia4.isNullOrEmpty()) FormatDate.use(
                        originalPreventivosMtoState.fechaDia4
                    ) else null,
                    if (!originalPreventivosMtoState.fechaDia5.isNullOrEmpty()) FormatDate.use(
                        originalPreventivosMtoState.fechaDia5
                    ) else null,
                    if (!originalPreventivosMtoState.fechaDia6.isNullOrEmpty()) FormatDate.use(
                        originalPreventivosMtoState.fechaDia6
                    ) else null,
                    if (!originalPreventivosMtoState.fechaDia7.isNullOrEmpty()) FormatDate.use(
                        originalPreventivosMtoState.fechaDia7
                    ) else null,
                )
            )
            originalPreventivosMtoState.fechas =
                originalPreventivosMtoState.fechas.filterNotNull().filter { it.isNotBlank() }
                    .toMutableList()
        }
    }

    fun setFechaBorrar(fecha: String) {
        preventivoBusState = preventivoBusState.copy(
            fechaABorrar = fecha
        )
    }

    fun setIsDetail(isDetail: Boolean) {
        preventivoBusState = preventivoBusState.copy(
            isDetail = isDetail
        )
    }

    fun resetInfoState() {
        preventivosMessageState = PreventivosMessageState.Loading
    }

    fun setCodPreventivo(codPreventivo: Int) {
        preventivoMtoState = preventivoMtoState.copy(
            codPreventivo = codPreventivo
        )
    }

    fun setAction(action: String?) {
        preventivoBusState = preventivoBusState.copy(
            action = action
        )
    }

    fun setLanzarBusqueda(lanzar: Boolean) {
        preventivoBusState = preventivoBusState.copy(
            lanzarBusqueda = lanzar,
            textoBusqueda = preventivoBusState.textoBusqueda,
            isDetail = preventivoBusState.lanzarBusqueda,
            showDlgBorrar = preventivoBusState.showDlgBorrar,
            showDlgDate = preventivoBusState.showDlgDate
        )
    }

    fun setTextoBusqueda(texto: String) {
        preventivoBusState = preventivoBusState.copy(
            textoBusqueda = texto
        )
    }

    fun setShowDlgBorrar(showDlgBorrar: Boolean) {
        preventivoBusState = preventivoBusState.copy(
            showDlgBorrar = showDlgBorrar
        )
    }

    fun setShowDlgDate(showDlgDate: Boolean) {
        preventivoBusState = preventivoBusState.copy(
            showDlgDate = showDlgDate
        )
    }

    fun setComboBoxOptionSelected(option: String) {
        preventivoBusState = preventivoBusState.copy(
            comboBoxOptionSelected = option
        )
    }

    fun setShowDlgSeleccionMes(show: Boolean) {
        preventivoBusState = preventivoBusState.copy(
            showDlgSeleccionMes = show
        )
    }

    fun resetFilter() {
        preventivoBusState = preventivoBusState.copy(
            comboBoxOptionSelected = "",
            textoBusqueda = "",
            lanzarBusqueda = false,
        )
    }

    fun setDescripcion(descripcion: String) {
        preventivoMtoState = preventivoMtoState.copy(
            descripcion = descripcion
        )
    }

    fun setTitulo(titulo: String) {
        preventivoMtoState = preventivoMtoState.copy(
            titulo = titulo
        )
    }

    fun setRiesgo(riesgo: Boolean) {
        preventivoMtoState = preventivoMtoState.copy(
            riesgo = if (riesgo) 1 else 0
        )
    }

    fun resetPreventivoState() {
        preventivoMtoState = PreventivoMtoState()
    }

    fun clonePreventivoState(preventivo: Preventivo) {
        preventivoMtoState = preventivoMtoState.copy(
            codPreventivo = preventivo.codPreventivo,
            titulo = preventivo.titulo,
            riesgo = preventivo.riesgo,
            descripcion = preventivo.descripcion,
            fechaDia1 = preventivo.fechaDia1,
            fechaDia2 = preventivo.fechaDia2,
            fechaDia3 = preventivo.fechaDia3,
            fechaDia4 = preventivo.fechaDia4,
            fechaDia5 = preventivo.fechaDia5,
            fechaDia6 = preventivo.fechaDia6,
            fechaDia7 = preventivo.fechaDia7,
            usuarios = preventivo.usuarios,
            vehiculos = preventivo.vehiculos
        )
    }

    override fun getAll() {
        viewModelScope.launch {
            preventivosUiState = PreventivosUiState.Loading
            preventivosUiState = try {
                var preventivos: List<Preventivo>?
                withTimeout(timeoutMillis * 2) {
                    val opcionSeleccionada = preventivoBusState.comboBoxOptionSelected

                    preventivos = when (opcionSeleccionada.lowercase(Locale.getDefault())) {
                        "riesgo" -> preventivoRepository.getPreventivos(riesgo = true)
                        "sin riesgo" -> preventivoRepository.getPreventivos(riesgo = false)
                        "" -> preventivoRepository.getPreventivos(
                            riesgo =
                            if (!esMayorDeEdad(Token.fechaNacimiento!!))
                                false
                            else
                                null
                        )

                        else -> preventivoRepository.getPreventivos(
                            riesgo =
                            if (!esMayorDeEdad(Token.fechaNacimiento!!))
                                false
                            else
                                null,
                            mes = meses.indexOf(opcionSeleccionada) + 1
                        )
                    }
                    if (preventivos!!.isNotEmpty()) {
                        preventivos!!.forEach {
                            val usuarios =
                                usuariosRepository.getUsuariosPreventivo(it.codPreventivo)
//                            val vehiculos =
//                                vehiculosRepository.getVehiculosPreventivo(it.codPreventivo)
                            it.usuarios = usuarios
                            //it.vehiculos = vehiculos
                        }
                    }
                }
                if (preventivos != null) {
                    PreventivosUiState.Success(preventivos = preventivos!!)
                } else {
                    PreventivosUiState.Error("Error, no se ha recibido respuesta del servidor")
                }
            } catch (e: IOException) {
                PreventivosUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("PreventivosVM (get) ", errorBodyString)
                    PreventivosUiState.Error(error)
                } else {
                    PreventivosUiState.Error("Error")
                }
            } catch (ex: TimeoutCancellationException) {
                PreventivosUiState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    override fun deleteBy() {
        viewModelScope.launch {
            preventivosMessageState = PreventivosMessageState.Loading
            preventivosMessageState = try {
                preventivoRepository.deletePreventivo(preventivoMtoState.codPreventivo)
                PreventivosMessageState.Success
            } catch (e: IOException) {
                PreventivosMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (delete) ", errorBodyString)
                    PreventivosMessageState.Error(error)
                } else {
                    PreventivosMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                PreventivosMessageState.Success
            } catch (ex: TimeoutCancellationException) {
                PreventivosMessageState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    override fun setNew() {
        viewModelScope.launch {
            preventivosMessageState = PreventivosMessageState.Loading
            preventivosMessageState = try {
                preventivoRepository.setPreventivo(
                    ObjectToStringMap.use(
                        preventivoMtoState.toPreventivo(
                            preventivoMtoState
                        )
                    )
                )
                PreventivosMessageState.Success
            } catch (e: IOException) {
                PreventivosMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (delete) ", errorBodyString)
                    PreventivosMessageState.Error(error)
                } else {
                    PreventivosMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                PreventivosMessageState.Success
            } catch (ex: TimeoutCancellationException) {
                PreventivosMessageState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    override fun update() {
        viewModelScope.launch {
            preventivosMessageState = PreventivosMessageState.Loading
            preventivosMessageState = try {
                val params = if (preventivoBusState.action.isNullOrEmpty()) {
                    ObjectToStringMap.use(preventivoMtoState.toPreventivo(preventivoMtoState))
                } else {
                    mapOf("CodUsuario" to Token.codUsuario.toString())
                }
                preventivoRepository.updPreventivo(
                    preventivoMtoState.codPreventivo,
                    params,
                    preventivoBusState.action
                )
                PreventivosMessageState.Success
            } catch (e: IOException) {
                PreventivosMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (delete) ", errorBodyString)
                    PreventivosMessageState.Error(error)
                } else {
                    PreventivosMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                PreventivosMessageState.Success
            } catch (ex: TimeoutCancellationException) {
                PreventivosMessageState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val preventivosRepository = application.container.preventivosRepository
                val usuariosRepository = application.container.usuariosRepository
                val vehiculosRepository = application.container.vehiculosRepository
                PreventivosVM(
                    preventivoRepository = preventivosRepository,
                    usuariosRepository = usuariosRepository,
                    vehiculosRepository = vehiculosRepository
                )
            }
        }
    }
}