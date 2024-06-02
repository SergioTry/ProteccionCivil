package com.dam.proteccioncivil.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.pantallas.chat.PantallaMensajes
import com.dam.proteccioncivil.pantallas.home.MainScreen
import com.dam.proteccioncivil.ui.dialogs.DlgConfirmacion
import com.dam.proteccioncivil.ui.dialogs.DlgPassword
import com.dam.proteccioncivil.ui.dialogs.DlgRecursos
import com.dam.proteccioncivil.ui.dialogs.DlgServicios
import com.dam.proteccioncivil.ui.screens.about.AboutScreen
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosMto
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosScreen
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosVM
import com.dam.proteccioncivil.ui.screens.calendario.CalendarioScreen
import com.dam.proteccioncivil.ui.screens.calendario.CalendarioVM
import com.dam.proteccioncivil.ui.screens.guardia.GuardiaMto
import com.dam.proteccioncivil.ui.screens.guardia.GuardiasScreen
import com.dam.proteccioncivil.ui.screens.guardia.GuardiasVM
import com.dam.proteccioncivil.ui.screens.infomur.InfomurMto
import com.dam.proteccioncivil.ui.screens.infomur.InfomursScreen
import com.dam.proteccioncivil.ui.screens.infomur.InfomursVM
import com.dam.proteccioncivil.ui.screens.login.LoginScreen
import com.dam.proteccioncivil.ui.screens.login.LoginVM
import com.dam.proteccioncivil.ui.screens.preferencias.Preferencias
import com.dam.proteccioncivil.ui.screens.preventivos.PreventivoMto
import com.dam.proteccioncivil.ui.screens.preventivos.PreventivosScreen
import com.dam.proteccioncivil.ui.screens.preventivos.PreventivosVM
import com.dam.proteccioncivil.ui.screens.splash.SplashScreen
import com.dam.proteccioncivil.ui.screens.usuarios.DatosPersonales
import com.dam.proteccioncivil.ui.screens.usuarios.UsuariosMto
import com.dam.proteccioncivil.ui.screens.usuarios.UsuariosScreen
import com.dam.proteccioncivil.ui.screens.usuarios.UsuariosVM
import com.dam.proteccioncivil.ui.screens.vehiculos.VehiculoMto
import com.dam.proteccioncivil.ui.screens.vehiculos.VehiculosScreen
import com.dam.proteccioncivil.ui.screens.vehiculos.VehiculosVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class AppScreens(@StringRes val title: Int) {
    Splash(title = R.string.screen_name_splash),
    Home(title = R.string.screen_name_home),
    Login(title = R.string.screen_name_login),
    Preferences(title = R.string.screen_name_preferences),
    Calendar(title = R.string.screen_name_calendar),
    News(title = R.string.screen_name_news),
    NewsMto(title = R.string.screen_name_news_mto),
    Vehiculos(title = R.string.screen_name_vehicles),
    VehiculosMto(title = R.string.screen_name_vehicles_mto),
    Anuncios(title = R.string.screen_name_announces),
    AnunciosMto(title = R.string.screen_name_announces_mto),
    Usuarios(title = R.string.screen_name_users),
    UsuariosMto(title = R.string.screen_name_users_mto),
    Guardias(title = R.string.screen_name_guards),
    GuardiasMto(title = R.string.screen_name_guards_mto),
    Infomurs(title = R.string.screen_name_infomurs),
    InfomursMto(title = R.string.screen_name_infomurs_mto),
    Chat(title = R.string.screen_name_chat),
    Preventivos(title = R.string.screen_name_preventivos),
    PreventivosMto(title = R.string.screen_name_preventivos_mto),
    Recursos(title = R.string.screen_name_resources),
    Sobre(title = R.string.screen_name_about),
    DatosPersonales(title = R.string.screen_name_personal_data),
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainApp(
    mainVM: MainVM,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        AppScreens.valueOf(backStackEntry?.destination?.route ?: AppScreens.Home.name)
    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val loginVM: LoginVM =
        viewModel(factory = LoginVM.Factory)

    val usuariosVM: UsuariosVM =
        viewModel(factory = UsuariosVM.Factory)

    val anunciosVM: AnunciosVM =
        viewModel(factory = AnunciosVM.Factory)

    val guardiasVM: GuardiasVM =
        viewModel(factory = GuardiasVM.Factory)

    val infomursVM: InfomursVM =
        viewModel(factory = InfomursVM.Factory)

    val calendarioVM: CalendarioVM =
        viewModel(factory = CalendarioVM.Factory)

    val vehiculosVM: VehiculosVM =
        viewModel(factory = VehiculosVM.Factory)

    val preventivosVM: PreventivosVM =
        viewModel(factory = PreventivosVM.Factory)

    val menuOptions = mapOf(
        Icons.Default.Home to stringResource(R.string.screen_name_home),
        Icons.Filled.Work to stringResource(R.string.screen_name_servicios),
        Icons.Default.Apps to stringResource(R.string.screen_name_resources),
        Icons.AutoMirrored.Filled.Chat to stringResource(R.string.screen_name_chat),
    )

    // En futuras versiones se implementará la vista landscape y la vista
    // para diferentes tamaños de pantalla.

    Scaffold(
        modifier = modifier,
        topBar = {
            if (currentScreen != AppScreens.Splash) {
                MainTopAppBar(
                    currentScreen = currentScreen,
                    canNavigateBack = (currentScreen.name != AppScreens.Splash.name && currentScreen.name != AppScreens.Home.name),
                    showLoginScreen = { navController.navigate(AppScreens.Login.name) },
                    showPrefScreen = { navController.navigate(AppScreens.Preferences.name) },
                    showAnoScreen = {
                        anunciosVM.getAll()
                        navController.navigate(AppScreens.Anuncios.name)
                    },
                    showDlgSalir = {
                        mainVM.setShowDlgSalir(true)
                    },
                    navigateUp = {
                        navController.navigateUp()
                    },
                    navController = navController,
                    calendarioVM = calendarioVM,
                    showDatosPersonalesScreen = {
                        usuariosVM.getUsuarioById()
                        navController.navigate(AppScreens.DatosPersonales.name)
                    },
                    showSobreScreen = { navController.navigate(AppScreens.Sobre.name) }
                )
            }
        },
        bottomBar = {
            if (currentScreen != AppScreens.Splash && currentScreen != AppScreens.Login) {
                MainBottomBar(
                    menuOptions = menuOptions,
                    navController = navController,
                    mainVM = mainVM,
                    resetFilters = {
                        resetFilters(usuariosVM, vehiculosVM, preventivosVM)
                    }
                )
            }
        },
        snackbarHost = {
            CustomSnackBar(
                snackbarHostState = snackbarHostState,
                context = context
            )
        },
    ) {
        NavHostRoutes(
            navController,
            it,
            scope,
            snackbarHostState,
            anunciosVM,
            usuariosVM,
            guardiasVM,
            infomursVM,
            calendarioVM,
            vehiculosVM,
            preventivosVM,
            mainVM,
            loginVM
        )
    }

    if (mainVM.uiMainState.showDlgPassword) {
        DlgPassword(
            usuariosVM = usuariosVM,
            mainVM = mainVM,
            onShowSnackBar = {
                scope.launch {
                    snackbarHostState.showSnackbar(it)
                }
            },
//            onPasswordChanged = {
//                usuariosVM.resetUsuarioMtoState()
//                mainVM.setShowDlgPassword(false)
//            },
            backToLogin = {
                mainVM.setShowDlgPassword(false)
                usuariosVM.resetUsuarioMtoState()
                navController.navigate(AppScreens.Login.name)
            })
    }
    if (mainVM.uiMainState.showDlgRecursos) {
        DlgRecursos(
            onCancelarClick = { mainVM.setShowDlgRecursos(false) },
            onVehiculosClick = {
                mainVM.setShowDlgRecursos(false)
                vehiculosVM.getAll()
                navController.navigate(AppScreens.Vehiculos.name)
            },
            onVoluntariosClick = {
                mainVM.setShowDlgRecursos(false)
                usuariosVM.getAll()
                navController.navigate(AppScreens.Usuarios.name)
            })
    }
    if (mainVM.uiMainState.showDlgSalir) {
        DlgConfirmacion(
            onCancelarClick = { mainVM.setShowDlgSalir(false) },
            onAceptarClick = {
                mainVM.setShowDlgSalir(false)
                activity?.finish()
            },
            mensaje = R.string.exit_confirmation
        )
    }
    if (mainVM.uiMainState.showDlgServicios) {
        DlgServicios(
            onCancelarClick = { mainVM.setShowDlgServicios(false) },
            onPreventivosClick = {
                mainVM.setShowDlgServicios(false)
                preventivosVM.getAll()
                navController.navigate(AppScreens.Preventivos.name)
            },
            onGuardiasClick = {
                mainVM.setShowDlgServicios(false)
                guardiasVM.getAll()
                navController.navigate(AppScreens.Guardias.name)
            },
            onInfomursClick = {
                mainVM.setShowDlgServicios(false)
                infomursVM.getAll()
                navController.navigate(AppScreens.Infomurs.name)
            })
    }
}

/**
 * Anotación de la navegación
 *
 * La app solo tendrá el botón de ir hacia atrás
 * en caso de que sea un admin el que haya hecho
 * inicio de sesión y se encuentre en una pantalla
 * de mantenimineto. En caso de que cualquier usuario
 * que haya hecho inicio sesión, pulse el botón de ir hacia
 * atrás del móvil, la app mostrará la pantalla de inicio.
 */

@Composable
private fun NavHostRoutes(
    navController: NavHostController,
    it: PaddingValues,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    anunciosVM: AnunciosVM,
    usuariosVM: UsuariosVM,
    guardiasVM: GuardiasVM,
    infomursVM: InfomursVM,
    calendarioVM: CalendarioVM,
    vehiculosVM: VehiculosVM,
    preventivosVM: PreventivosVM,
    mainVM: MainVM,
    loginVM: LoginVM
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = AppScreens.Splash.name,
        modifier = Modifier.padding(it)
    ) {
        composable(route = AppScreens.Splash.name) {
            SplashScreen(mainVM, loginVM, usuariosVM, {
                if (it) {
                    navController.navigate(AppScreens.Login.name)
                } else {
                    navController.navigate(AppScreens.Home.name)
                }
            }, getString(context, R.string.version))
        }
        composable(route = AppScreens.Home.name) {
            MainScreen(mainVM)
        }
        composable(route = AppScreens.Sobre.name) {
            AboutScreen(version = getString(context, R.string.version))
        }
        composable(route = AppScreens.Login.name) {
            LoginScreen(
                version = getString(context, R.string.version),
                mainVM = mainVM,
                loginVM = loginVM,
                onNavUp = {
                    selectOption(
                        clave = Icons.Default.Home,
                        navController = navController,
                        mainVM = mainVM,
                        resetFilters = {
                            resetFilters(usuariosVM, vehiculosVM, preventivosVM)
                        }
                    )
                },
                savedToken = loginVM.uiLoginState.username.isNotEmpty() && loginVM.uiLoginState.password.isNotEmpty(),
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            )
        }
        composable(route = AppScreens.DatosPersonales.name) {
            DatosPersonales(
                mainVM = mainVM,
                usuariosUiState = usuariosVM.usuariosUiState,
                usuariosVM = usuariosVM,
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                })
        }
        composable(route = AppScreens.Preferences.name) {
            Preferencias(
                mainVM = mainVM
            )
        }

        composable(route = AppScreens.Guardias.name) {
            GuardiasScreen(
                guardiasUiState = guardiasVM.guardiasUiState,
                guardiasVM = guardiasVM,
                retryAction = { guardiasVM.getAll() },
                onNavUp = {
                    guardiasVM.setIsDetail(false)
                    navController.navigate(AppScreens.GuardiasMto.name)
                },
                onNavDetail = {
                    guardiasVM.setIsDetail(true)
                    navController.navigate(AppScreens.GuardiasMto.name)
                },
                refresh = {
                    navController.popBackStack()
                    navController.navigate(AppScreens.Guardias.name)
                },
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            )
        }

        composable(route = AppScreens.GuardiasMto.name) {
            GuardiaMto(
                guardiasVM = guardiasVM,
                refresh = {
                    navController.popBackStack(AppScreens.Guardias.name, true)
                    navController.navigate(AppScreens.Guardias.name)
                },
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                users = guardiasVM.users,
                modifier = Modifier
            )
        }

        composable(route = AppScreens.Infomurs.name) {
            InfomursScreen(
                infomursUiState = infomursVM.infomursUiState,
                infomursVM = infomursVM,
                retryAction = { infomursVM.getAll() },
                onNavUp = {
                    infomursVM.setIsDetail(false)
                    navController.navigate(AppScreens.InfomursMto.name)
                },
                onNavDetail = {
                    infomursVM.setIsDetail(true)
                    navController.navigate(AppScreens.InfomursMto.name)
                },
                refresh = {
                    navController.popBackStack()
                    navController.navigate(AppScreens.Infomurs.name)
                },
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                })
        }

        composable(route = AppScreens.InfomursMto.name) {
            InfomurMto(
                infomursVM = infomursVM,
                refresh = {
                    navController.popBackStack(AppScreens.Infomurs.name, true)
                    navController.navigate(AppScreens.Infomurs.name)
                },
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                users = infomursVM.users,
                modifier = Modifier
            )
        }

        composable(route = AppScreens.Anuncios.name) {
            AnunciosScreen(
                anunciosUiState = anunciosVM.anunciosUiState,
                anunciosVM = anunciosVM,
                retryAction = {
                    anunciosVM.getAll()
                },
                onNavUp = { navController.navigate(AppScreens.AnunciosMto.name) },
                refresh = {
                    navController.popBackStack()
                    navController.navigate(AppScreens.Anuncios.name)
                },
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                })
        }

        composable(route = AppScreens.AnunciosMto.name) {
            AnunciosMto(
                anunciosVM = anunciosVM,
                refresh = {
                    navController.popBackStack(AppScreens.Anuncios.name, true)
                    navController.navigate(AppScreens.Anuncios.name)
                },
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                modifier = Modifier,
                onCancel = {
                    navController.popBackStack()
                    //  anunciosVM.setLoading(false)
                }
            )
        }

        composable(route = AppScreens.Usuarios.name) {
            UsuariosScreen(
                usuariosUiState = usuariosVM.usuariosUiState,
                usuariosVM = usuariosVM,
                retryAction = { usuariosVM.getAll() },
                onNavUp = {
                    usuariosVM.setIsDetail(false)
                    navController.navigate(AppScreens.UsuariosMto.name)
                },
                onNavDetail = {
                    usuariosVM.setIsDetail(true)
                    navController.navigate(AppScreens.UsuariosMto.name)
                },
                refresh = {
                    navController.popBackStack()
                    navController.navigate(AppScreens.Usuarios.name)
                },
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                })
        }

        composable(route = AppScreens.UsuariosMto.name) {
            UsuariosMto(usuariosVM = usuariosVM,
                refresh = {
                    navController.popBackStack(AppScreens.Usuarios.name, true)
                    navController.navigate(AppScreens.Usuarios.name)
                },
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                })
        }

        composable(route = AppScreens.Vehiculos.name) {
            VehiculosScreen(
                vehiculosUiState = vehiculosVM.vehiculosUiState,
                vehiculosVM = vehiculosVM,
                retryAction = { vehiculosVM.getAll() },
                onNavUp = {
                    vehiculosVM.setIsDetail(false)
                    navController.navigate(AppScreens.VehiculosMto.name)
                },
                onNavDetail = {
                    vehiculosVM.setIsDetail(true)
                    navController.navigate(AppScreens.VehiculosMto.name)
                },
                refresh = {
                    navController.popBackStack()
                    navController.navigate(AppScreens.Vehiculos.name)
                },
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                })
        }

        composable(route = AppScreens.VehiculosMto.name) {
            VehiculoMto(vehiculosVM = vehiculosVM,
                refresh = {
                    navController.popBackStack(AppScreens.Vehiculos.name, true)
                    navController.navigate(AppScreens.Vehiculos.name)
                },
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                })
        }

        composable(route = AppScreens.Calendar.name) {
            CalendarioScreen(
                calendarioUiState = calendarioVM.calendarioUiState,
                calendarioVM = calendarioVM,
                retryAction = { calendarioVM.getAll() },
                refresh = {},
                onShowSnackBar = {}
            )
        }

        composable(route = AppScreens.Preventivos.name) {
            PreventivosScreen(
                preventivosUiState = preventivosVM.preventivosUiState,
                preventivosVM = preventivosVM,
                retryAction = { usuariosVM.getAll() },
                onNavUp = {
                    preventivosVM.setIsDetail(false)
                    navController.navigate(AppScreens.PreventivosMto.name)
                },
                onNavDetail = {
                    preventivosVM.setIsDetail(true)
                    navController.navigate(AppScreens.PreventivosMto.name)
                },
                refresh = {
                    navController.popBackStack()
                    navController.navigate(AppScreens.Preventivos.name)
                },
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                })
        }

        composable(route = AppScreens.PreventivosMto.name) {
            PreventivoMto(
                preventivosVM = preventivosVM,
                usuariosVM = usuariosVM,
                onNavUsuarioDetail = {
                    usuariosVM.setIsDetail(true)
                    navController.navigate(AppScreens.UsuariosMto.name)
                },
                modifier = Modifier,
                onShowSnackBar = { mensaje, isSuccess ->
                    scope.launch {
                        if (isSuccess) {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                "",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbarHostState.showSnackbar(
                                mensaje,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                refresh = {
                    navController.popBackStack(AppScreens.Preventivos.name, true)
                    navController.navigate(AppScreens.Preventivos.name)
                })
        }

        composable(route = AppScreens.Chat.name) {
            PantallaMensajes()
        }
    }
}


fun selectOption(
    clave: ImageVector,
    navController: NavHostController,
    mainVM: MainVM,
    resetFilters: () -> Unit
) {
    resetFilters()
    navController.popBackStack(AppScreens.Home.name, false)
    when (clave.name) {
        Icons.Default.Home.name -> {
            navController.navigate(
                AppScreens.Home.name
            )
        }

        Icons.Filled.Work.name -> {
            mainVM.setShowDlgServicios(true)
        }

        Icons.AutoMirrored.Filled.Chat.name -> navController.navigate(
            AppScreens.Chat.name
        )

        else -> {
            when (Token.rango) {
                "Voluntario" -> {
                    if (Token.conductor == 1) {
                        navController.navigate(AppScreens.Vehiculos.name)
                    }
                }

                else -> {
                    // Jefes de servicio y admins
                    mainVM.setShowDlgRecursos(true)
                }
            }
        }
    }
}

private fun resetFilters(
    usuariosVM: UsuariosVM,
    vehiculosVM: VehiculosVM,
    preventivosVM: PreventivosVM
) {
    usuariosVM.resetFilter()
    vehiculosVM.resetFilter()
    preventivosVM.resetFilter()
}


/*
 ______     ____  __                    __   __
|  _ \ \   / /  \/  |   /\        /\    \ \ / /
| |_) \ \_/ /| \  / |  /  \      /  \    \ V /
|  _ < \   / | |\/| | / /\ \    / /\ \    > <
| |_) | | |  | |  | |/ ____ \  / ____ \  / . \
|____/  |_|  |_|  |_/_/    \_\/_/    \_\/_/ \_\

*/