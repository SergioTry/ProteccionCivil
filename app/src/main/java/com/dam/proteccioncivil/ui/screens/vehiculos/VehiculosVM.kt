package com.dam.proteccioncivil.ui.screens.vehiculos

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
import com.dam.proteccioncivil.data.model.ObjectToStringMap
import com.dam.proteccioncivil.data.model.ShortToBoolean
import com.dam.proteccioncivil.data.model.Vehiculo
import com.dam.proteccioncivil.data.model.timeoutMillis
import com.dam.proteccioncivil.data.repository.VehiculosRepository
import com.google.gson.JsonParser
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

class VehiculosVM(private val vehiculosRepository: VehiculosRepository) : CRUD<Vehiculo>,
    ViewModel() {
    var vehiculosUiState: VehiculosUiState by mutableStateOf(VehiculosUiState.Loading)
        private set

    var vehiculosMessageState: VehiculoMessageState by mutableStateOf(VehiculoMessageState.Loading)
        private set
    var vehiculosMtoState by mutableStateOf(VehiculoMtoState())
        private set

    var vehiculosBusState by mutableStateOf(VehiculosBusState())

    fun setLanzarBusqueda(lanzar: Boolean) {
        vehiculosBusState = vehiculosBusState.copy(
            lanzarBusqueda = lanzar,
            textoBusqueda = vehiculosBusState.textoBusqueda,
            isDetail = vehiculosBusState.isDetail,
            showDlgConfirmation = vehiculosBusState.showDlgConfirmation,
            showDlgDate = vehiculosBusState.showDlgDate
        )
    }

    fun setTextoBusqueda(texto: String) {
        vehiculosBusState = vehiculosBusState.copy(
            lanzarBusqueda = vehiculosBusState.lanzarBusqueda,
            textoBusqueda = texto,
            isDetail = vehiculosBusState.isDetail,
            showDlgConfirmation = vehiculosBusState.showDlgConfirmation,
            showDlgDate = vehiculosBusState.showDlgDate
        )
    }

    fun setIsDetail(isDetail: Boolean) {
        vehiculosBusState = vehiculosBusState.copy(
            lanzarBusqueda = vehiculosBusState.lanzarBusqueda,
            textoBusqueda = vehiculosBusState.textoBusqueda,
            isDetail = isDetail,
            showDlgConfirmation = vehiculosBusState.showDlgConfirmation,
            showDlgDate = vehiculosBusState.showDlgDate
        )
    }

    fun setShowDlgBorrar(showDlgBorrar: Boolean) {
        vehiculosBusState = vehiculosBusState.copy(
            lanzarBusqueda = vehiculosBusState.lanzarBusqueda,
            textoBusqueda = vehiculosBusState.textoBusqueda,
            isDetail = vehiculosBusState.isDetail,
            showDlgConfirmation = showDlgBorrar,
            showDlgDate = vehiculosBusState.showDlgDate
        )
    }

    fun setShowDlgDate(showDlgDate: Boolean) {
        vehiculosBusState = vehiculosBusState.copy(
            lanzarBusqueda = vehiculosBusState.lanzarBusqueda,
            textoBusqueda = vehiculosBusState.textoBusqueda,
            isDetail = vehiculosBusState.isDetail,
            showDlgConfirmation = vehiculosBusState.showDlgConfirmation,
            showDlgDate = showDlgDate
        )
    }

    fun resetInfoState() {
        vehiculosMessageState = VehiculoMessageState.Loading
    }

    override fun getAll() {
        viewModelScope.launch {
            vehiculosUiState = VehiculosUiState.Loading
            vehiculosUiState = try {
                var vehiculos: List<Vehiculo>?
                withTimeout(timeoutMillis) {
                    vehiculos = vehiculosRepository.getVehiculos()
                }
                if (vehiculos != null) {
                    VehiculosUiState.Success(vehiculos!!)
                } else {
                    VehiculosUiState.Error("Error, no se ha recibido respuesta del servidor")
                }
            } catch (e: IOException) {
                VehiculosUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("VehiculosVM (getAll) ", errorBodyString)
                    VehiculosUiState.Error(error)
                } else {
                    VehiculosUiState.Error("Error")
                }
            } catch (ex: TimeoutCancellationException) {
                VehiculosUiState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    override fun deleteBy() {
        viewModelScope.launch {
            vehiculosMessageState = VehiculoMessageState.Loading
            vehiculosMessageState = try {
                withTimeout(timeoutMillis) {
                    vehiculosRepository.deleteVehiculo(vehiculosMtoState.codVehiculo.toInt())
                }
                VehiculoMessageState.Success
            } catch (e: IOException) {
                VehiculoMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("VehiculosVM (delete) ", errorBodyString)
                    VehiculoMessageState.Error(error)
                } else {
                    VehiculoMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                VehiculoMessageState.Success
            } catch (ex: TimeoutCancellationException) {
                VehiculoMessageState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    override fun update() {
        viewModelScope.launch {
            vehiculosMessageState = VehiculoMessageState.Loading
            vehiculosMessageState = try {
                withTimeout(timeoutMillis) {
                    vehiculosRepository.updateVehiculo(
                        vehiculosMtoState.codVehiculo.toInt(),
                        ObjectToStringMap.use(vehiculosMtoState.toVehiculo())
                    )
                }
                VehiculoMessageState.Success
            } catch (e: IOException) {
                VehiculoMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("VehiculosVM (update) ", errorBodyString)
                    VehiculoMessageState.Error(error)
                } else {
                    VehiculoMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                //Esta excepcion se lanza cuando recibimos el 204, ya que este al no tener body provoca
                //error aunque el comportamiento es el que queremos, de ahi que al tratarla se maneje
                //como success
                VehiculoMessageState.Success
            } catch (ex: TimeoutCancellationException) {
                VehiculoMessageState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    override fun setNew() {
        viewModelScope.launch {
            vehiculosMessageState = VehiculoMessageState.Loading
            vehiculosMessageState = try {
                val vehiculoMap = ObjectToStringMap.use(vehiculosMtoState.toVehiculo())
                withTimeout(timeoutMillis) {
                    vehiculosRepository.setVehiculo(vehiculoMap)
                }
                VehiculoMessageState.Success
            } catch (e: IOException) {
                VehiculoMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("VehiculosVM (alta) ", errorBodyString)
                    VehiculoMessageState.Error(error)
                } else {
                    VehiculoMessageState.Error("Error")
                }
            } catch (ex: TimeoutCancellationException) {
                VehiculoMessageState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    fun resetVehiculoMtoState() {
        vehiculosMtoState = vehiculosMtoState.copy(
            "0",
            "",
            "",
            "",
            "0",
            false,
            null,
            null,
            null,
            false
        )
    }

    fun cloneVehiculoMtoState(vehiculo: Vehiculo) {
        vehiculosMtoState = vehiculosMtoState.copy(
            vehiculo.codVehiculo.toString(),
            vehiculo.matricula,
            vehiculo.marca,
            vehiculo.modelo,
            vehiculo.km.toString(),
            ShortToBoolean.use(vehiculo.disponible),
            vehiculo.fechaMantenimiento,
            vehiculo.descripcionMantenimiento,
            datosObligatorios = true
        )
    }

    fun setMatricula(matricula: String) {
        vehiculosMtoState = vehiculosMtoState.copy(
            codVehiculo = vehiculosMtoState.codVehiculo,
            matricula = matricula,
            marca = vehiculosMtoState.marca,
            modelo = vehiculosMtoState.modelo,
            km = vehiculosMtoState.km,
            disponible = vehiculosMtoState.disponible,
            fechaMantenimiento = vehiculosMtoState.fechaMantenimiento,
            descripcion = vehiculosMtoState.descripcion,
            datosObligatorios = (vehiculosMtoState.codVehiculo != "" &&
                    matricula != "" &&
                    vehiculosMtoState.marca != "" &&
                    vehiculosMtoState.modelo != "" &&
                    vehiculosMtoState.km != "" &&
                    vehiculosMtoState.fechaMantenimiento != "" &&
                    vehiculosMtoState.descripcion != "")
        )
    }

    fun setMarca(marca: String) {
        vehiculosMtoState = vehiculosMtoState.copy(
            codVehiculo = vehiculosMtoState.codVehiculo,
            matricula = vehiculosMtoState.matricula,
            marca = marca,
            modelo = vehiculosMtoState.modelo,
            km = vehiculosMtoState.km,
            disponible = vehiculosMtoState.disponible,
            fechaMantenimiento = vehiculosMtoState.fechaMantenimiento,
            descripcion = vehiculosMtoState.descripcion,
            datosObligatorios = (vehiculosMtoState.codVehiculo != "" &&
                    vehiculosMtoState.matricula != "" &&
                    marca != "" &&
                    vehiculosMtoState.modelo != "" &&
                    vehiculosMtoState.km != "" &&
                    vehiculosMtoState.fechaMantenimiento != "" &&
                    vehiculosMtoState.descripcion != "")
        )
    }

    fun setModelo(modelo: String) {
        vehiculosMtoState = vehiculosMtoState.copy(
            codVehiculo = vehiculosMtoState.codVehiculo,
            matricula = vehiculosMtoState.matricula,
            marca = vehiculosMtoState.marca,
            modelo = modelo,
            km = vehiculosMtoState.km,
            disponible = vehiculosMtoState.disponible,
            fechaMantenimiento = vehiculosMtoState.fechaMantenimiento,
            descripcion = vehiculosMtoState.descripcion,
            datosObligatorios = (vehiculosMtoState.codVehiculo != "" &&
                    vehiculosMtoState.matricula != "" &&
                    vehiculosMtoState.marca != "" &&
                    modelo != "" &&
                    vehiculosMtoState.km != "" &&
                    vehiculosMtoState.fechaMantenimiento != "" &&
                    vehiculosMtoState.descripcion != "")
        )
    }

    fun setKm(km: String) {
        vehiculosMtoState = vehiculosMtoState.copy(
            codVehiculo = vehiculosMtoState.codVehiculo,
            matricula = vehiculosMtoState.matricula,
            marca = vehiculosMtoState.marca,
            modelo = vehiculosMtoState.modelo,
            km = km,
            disponible = vehiculosMtoState.disponible,
            fechaMantenimiento = vehiculosMtoState.fechaMantenimiento,
            descripcion = vehiculosMtoState.descripcion,
            datosObligatorios = (vehiculosMtoState.codVehiculo != "" &&
                    vehiculosMtoState.matricula != "" &&
                    vehiculosMtoState.marca != "" &&
                    vehiculosMtoState.modelo != "" &&
                    km != "" &&
                    vehiculosMtoState.fechaMantenimiento != "" &&
                    vehiculosMtoState.descripcion != "")
        )
    }

    fun setDisponible(disponible: Boolean) {
        vehiculosMtoState = vehiculosMtoState.copy(
            codVehiculo = vehiculosMtoState.codVehiculo,
            matricula = vehiculosMtoState.matricula,
            marca = vehiculosMtoState.marca,
            modelo = vehiculosMtoState.modelo,
            km = vehiculosMtoState.km,
            disponible = disponible,
            fechaMantenimiento = vehiculosMtoState.fechaMantenimiento,
            descripcion = vehiculosMtoState.descripcion,
            datosObligatorios = (vehiculosMtoState.codVehiculo != "" &&
                    vehiculosMtoState.matricula != "" &&
                    vehiculosMtoState.marca != "" &&
                    vehiculosMtoState.modelo != "" &&
                    vehiculosMtoState.km != "" &&
                    vehiculosMtoState.fechaMantenimiento != "" &&
                    vehiculosMtoState.descripcion != "")
        )
    }

    fun setFechaMantenimiento(fechaMantenimiento: String) {
        vehiculosMtoState = vehiculosMtoState.copy(
            codVehiculo = vehiculosMtoState.codVehiculo,
            matricula = vehiculosMtoState.matricula,
            marca = vehiculosMtoState.marca,
            modelo = vehiculosMtoState.modelo,
            km = vehiculosMtoState.km,
            disponible = vehiculosMtoState.disponible,
            fechaMantenimiento = fechaMantenimiento,
            descripcion = vehiculosMtoState.descripcion,
            datosObligatorios = (vehiculosMtoState.codVehiculo != "" &&
                    vehiculosMtoState.matricula != "" &&
                    vehiculosMtoState.marca != "" &&
                    vehiculosMtoState.modelo != "" &&
                    vehiculosMtoState.km != "" &&
                    fechaMantenimiento != "" &&
                    vehiculosMtoState.descripcion != "")
        )
    }

    fun setDescripcion(descripcion: String) {
        vehiculosMtoState = vehiculosMtoState.copy(
            codVehiculo = vehiculosMtoState.codVehiculo,
            matricula = vehiculosMtoState.matricula,
            marca = vehiculosMtoState.marca,
            modelo = vehiculosMtoState.modelo,
            km = vehiculosMtoState.km,
            disponible = vehiculosMtoState.disponible,
            fechaMantenimiento = vehiculosMtoState.fechaMantenimiento,
            descripcion = descripcion,
            datosObligatorios = (vehiculosMtoState.codVehiculo != "" &&
                    vehiculosMtoState.matricula != "" &&
                    vehiculosMtoState.marca != "" &&
                    vehiculosMtoState.modelo != "" &&
                    vehiculosMtoState.km != "" &&
                    vehiculosMtoState.fechaMantenimiento != "" &&
                    descripcion != "")
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val vehiculosRepository = application.container.vehiculosRepository
                VehiculosVM(vehiculosRepository = vehiculosRepository)
            }
        }
    }
}