package com.dam.proteccioncivil.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.dam.proteccioncivil.ui.screens.preferencias.PrefScreen
import com.dam.proteccioncivil.ui.screens.splash.SplashScreen
import com.dam.proteccioncivil.ui.screens.usuarios.UsuariosMto
import com.dam.proteccioncivil.ui.screens.usuarios.UsuariosScreen
import com.dam.proteccioncivil.ui.screens.usuarios.UsuariosVM
import com.dam.proteccioncivil.ui.screens.vehiculos.VehiculoMto
import com.dam.proteccioncivil.ui.screens.vehiculos.VehiculosScreen
import com.dam.proteccioncivil.ui.screens.vehiculos.VehiculosVM
import com.dam.proteccioncivil.ui.theme.AppColors
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
    PreventivosMto(title = R.string.screen_name_preventidos_mto),
    Recursos(title = R.string.screen_name_resources)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainApp(
    mainVM: MainVM,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val activity = (LocalContext.current as? Activity)
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        AppScreens.valueOf(backStackEntry?.destination?.route ?: AppScreens.Home.name)
    val snackbarHostState = remember() { SnackbarHostState() }

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

    val menuOptions = mapOf(
        Icons.Default.Home to stringResource(R.string.screen_name_home),
        Icons.Default.WorkOutline to stringResource(R.string.screen_name_preventidos),
        Icons.Default.Apps to stringResource(R.string.screen_name_resources),
        Icons.AutoMirrored.Filled.Chat to stringResource(R.string.screen_name_chat),
    )

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        && windowSize == WindowWidthSizeClass.Compact
    ) {
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
                            backButtonNavigation(currentScreen, navController)
                        },
                        navController = navController,
                        calendarioVM = calendarioVM
                    )
                }
            },
            bottomBar = {
                if (currentScreen != AppScreens.Splash && currentScreen != AppScreens.Login) {
                    MainBottomBar(
                        menuOptions = menuOptions,
                        navController = navController,
                        mainVM = mainVM,
                        calendarioVM = calendarioVM
                    )
                }
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                mainVM,
                loginVM
            )
        }
    }
    if (mainVM.uiMainState.showDlgPassword) {
        DlgPassword(usuariosVM = usuariosVM,
            onEstablecerClick = {
                usuariosVM.changePassword()
            },
            onShowSnackBar = {
                scope.launch { snackbarHostState.showSnackbar(it) }
            },
            onPasswordChanged = {
                usuariosVM.resetUsuarioMtoState()
                mainVM.setShowDlgPassword(false)
            },
            backToLogin = {
                mainVM.setShowDlgPassword(false)
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
            mensaje = R.string.question_exit
        )
    }
    if (mainVM.uiMainState.showDlgServicios) {
        DlgServicios(
            onCancelarClick = { mainVM.setShowDlgServicios(false) },
            onPreventivosClick = { mainVM.setShowDlgServicios(false) },
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
    mainVM: MainVM,
    loginVM: LoginVM
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Splash.name,
        modifier = Modifier.padding(it)
    ) {
        composable(route = AppScreens.Splash.name) {
            SplashScreen(mainVM, loginVM, {
                if (it) {
                    navController.navigate(AppScreens.Login.name)
                } else {
                    navController.navigate(AppScreens.Home.name)
                }
            }, "0.0.0")
        }
        composable(route = AppScreens.Home.name) {
            MainScreen(mainVM)
        }
        composable(route = AppScreens.Login.name) {
            LoginScreen(
                version = "0.0.0",
                mainVM = mainVM,
                loginVM = loginVM,
                onNavUp = {
                    selectOption(
                        clave = Icons.Default.Home,
                        navController = navController,
                        mainVM = mainVM,
                        calendarioVM = calendarioVM
                    )
                },
                savedToken = loginVM.uiLoginState.username.isNotEmpty() && loginVM.uiLoginState.password.isNotEmpty()
            )
        }
        composable(route = AppScreens.Preferences.name) {
            PrefScreen(
                mainVM = mainVM,
                onNavUp = {
                    navController.navigate(AppScreens.Home.name)
                }
            )
        }

        composable(route = AppScreens.Guardias.name) {
            GuardiasScreen(
                guardiasUiState = guardiasVM.guardiasUiState,
                guardiasVM = guardiasVM,
                retryAction = { guardiasVM::getAll },
                onNavUp = { navController.navigate(AppScreens.GuardiasMto.name) },
                refresh = { navController.navigate(AppScreens.Guardias.name) },
                onShowSnackBar = { scope.launch { snackbarHostState.showSnackbar(it) } })
        }

        composable(route = AppScreens.GuardiasMto.name) {
            GuardiaMto(guardiasVM = guardiasVM,
                refresh = { navController.navigate(AppScreens.Guardias.name) },
                onShowSnackBar = { scope.launch { snackbarHostState.showSnackbar(it) } })
        }

        composable(route = AppScreens.Infomurs.name) {
            InfomursScreen(
                infomursUiState = infomursVM.infomursUiState,
                infomursVM = infomursVM,
                retryAction = { infomursVM::getAll },
                onNavUp = { navController.navigate(AppScreens.InfomursMto.name) },
                refresh = { navController.navigate(AppScreens.Infomurs.name) },
                onShowSnackBar = { scope.launch { snackbarHostState.showSnackbar(it) } })
        }

        composable(route = AppScreens.InfomursMto.name) {
            InfomurMto(infomursVM = infomursVM,
                refresh = { navController.navigate(AppScreens.Infomurs.name) },
                onShowSnackBar = { scope.launch { snackbarHostState.showSnackbar(it)}},
                users = infomursVM.users
            )
        }

        composable(route = AppScreens.Anuncios.name) {
            AnunciosScreen(
                anunciosUiState = anunciosVM.anunciosUiState,
                anunciosVM = anunciosVM,
                retryAction = { anunciosVM::getAll },
                onNavUp = { navController.navigate(AppScreens.AnunciosMto.name) },
                refresh = { navController.navigate(AppScreens.Anuncios.name) },
                onShowSnackBar = { scope.launch { snackbarHostState.showSnackbar(it) } })
        }

        composable(route = AppScreens.AnunciosMto.name) {
            AnunciosMto(anunciosVM = anunciosVM,
                refresh = { navController.navigate(AppScreens.Anuncios.name) },
                onShowSnackBar = { scope.launch { snackbarHostState.showSnackbar(it) } },
                modifier = Modifier)
        }

        composable(route = AppScreens.Usuarios.name) {
            UsuariosScreen(
                usuariosUiState = usuariosVM.usuariosUiState,
                usuariosVM = usuariosVM,
                retryAction = { usuariosVM::getAll },
                onNavUp = { navController.navigate(AppScreens.UsuariosMto.name) },
                refresh = { navController.navigate(AppScreens.Usuarios.name) },
                onShowSnackBar = { scope.launch { snackbarHostState.showSnackbar(it) } })
        }

        composable(route = AppScreens.UsuariosMto.name) {
            UsuariosMto(usuariosVM = usuariosVM,
                refreshNav = { navController.navigate(AppScreens.Usuarios.name) },
                refresh = { navController.navigate(AppScreens.UsuariosMto.name) },
                onShowSnackBar = { scope.launch { snackbarHostState.showSnackbar(it) } })
        }

        composable(route = AppScreens.Vehiculos.name) {
            VehiculosScreen(
                vehiculosUiState = vehiculosVM.vehiculosUiState,
                vehiculosVM = vehiculosVM,
                retryAction = { vehiculosVM::getAll },
                onNavUp = { navController.navigate(AppScreens.VehiculosMto.name) },
                refresh = { navController.navigate(AppScreens.Vehiculos.name) },
                onShowSnackBar = { scope.launch { snackbarHostState.showSnackbar(it) } })
        }

        composable(route = AppScreens.VehiculosMto.name) {
            VehiculoMto(vehiculosVM = vehiculosVM,
                retryAction = { vehiculosVM::getAll },
                onNavUp = { navController.navigate(AppScreens.VehiculosMto.name) },
                refresh = { navController.navigate(AppScreens.Vehiculos.name) },
                onShowSnackBar = { scope.launch { snackbarHostState.showSnackbar(it) } })
        }

        composable(route = AppScreens.Calendar.name) {
            CalendarioScreen(
                calendarioUiState = calendarioVM.calendarioUiState,
                calendarioVM = calendarioVM,
                retryAction = {},
                refresh = {},
                onShowSnackBar = {}
            )
        }

        composable(route = AppScreens.Chat.name) {
            PantallaMensajes()
        }
    }
}

@Composable
fun MainBottomBar(
    menuOptions: Map<ImageVector, String>,
    navController: NavHostController,
    calendarioVM: CalendarioVM,
    mainVM: MainVM
) {
    NavigationBar(containerColor = AppColors.OrangeColor) {
        for ((clave, valor) in menuOptions) {
            if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                NavigationBarItem(
                    icon = {
                        Icon(
                            clave, contentDescription = null,
                            tint = Color.White
                        )
                    },
                    label = {
                        Text(
                            text = valor,
                            style = TextStyle(
                                color = Color.White
                            )
                        )
                    },
                    selected = false,
                    onClick = {
                        selectOption(
                            clave = clave,
                            navController = navController,
                            mainVM = mainVM,
                            calendarioVM = calendarioVM
                        )
                    },
                    enabled = true
                )
            } else {
                if (clave.name == Icons.Default.Apps.name) {
                    if (Token.conductor == 1) {
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    Icons.Default.DirectionsCar, contentDescription = null,
                                    tint = Color.White
                                )
                            },
                            label = { Text(stringResource(R.string.screen_name_vehicles)) },
                            selected = false,
                            onClick = {
                                selectOption(
                                    clave = Icons.Default.DirectionsCar,
                                    navController = navController,
                                    mainVM = mainVM,
                                    calendarioVM = calendarioVM
                                )
                            },
                            enabled = true
                        )
                    }
                } else {
                    NavigationBarItem(
                        icon = {
                            Icon(
                                clave, contentDescription = null,
                                tint = Color.White
                            )
                        },
                        label = { Text(valor) },
                        selected = false,
                        onClick = {
                            selectOption(
                                clave = clave,
                                navController = navController,
                                mainVM = mainVM,
                                calendarioVM = calendarioVM
                            )
                        },
                        enabled = true
                    )
                }
            }
        }
    }
}


private fun selectOption(
    clave: ImageVector,
    navController: NavHostController,
    calendarioVM: CalendarioVM,
    mainVM: MainVM
) {
    navController.popBackStack(AppScreens.Home.name, false)
    when (clave.name) {
        Icons.Default.Home.name -> {

            navController.navigate(
                AppScreens.Home.name
            )
        }

        Icons.Default.WorkOutline.name -> {
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

private fun backButtonNavigation(
    currentScreen: AppScreens,
    navController: NavHostController
) {
    //TODO
    when (currentScreen.name) {
        "", "AulasBus", "IncsBus" -> navController.navigate(
            AppScreens.Home.name
        )

        else -> navController.navigateUp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    navController: NavHostController,
    currentScreen: AppScreens,
    canNavigateBack: Boolean,
    showLoginScreen: () -> Unit,
    showPrefScreen: () -> Unit,
    calendarioVM: CalendarioVM,
    showAnoScreen: () -> Unit,
    showDlgSalir: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        colors =
        if (currentScreen.title != R.string.screen_name_splash) {
            TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = AppColors.OrangeColor,
                titleContentColor = AppColors.White
            )
        } else {
            TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = AppColors.White,
                titleContentColor = Color.Black
            )
        },
        title = {
            if (currentScreen.title != R.string.screen_name_splash) {
                Text(text = stringResource(currentScreen.title))
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick =
                    navigateUp
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            } else {
                IconButton(
                    onClick = {
                        calendarioVM.getAll()
                        navController.navigate(AppScreens.Calendar.name)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            if (currentScreen.name == AppScreens.Home.name) {
                Row() {
                    IconButton(onClick = { showAnoScreen() }) {
                        Icon(
                            imageVector = Icons.Default.Notifications, contentDescription = null,
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert, contentDescription = null,
                            tint = Color.White
                        )
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {

                        DropdownMenuItem(text = { Text(text = stringResource(R.string.screen_name_login)) },
                            onClick = {
                                showLoginScreen()
                                showMenu = false
                            })

                        DropdownMenuItem(text = { Text(text = stringResource(R.string.screen_name_preferences)) },
                            onClick = {
                                showPrefScreen()
                                showMenu = false
                            })

                        DropdownMenuItem(text = { Text(text = stringResource(R.string.menu_salir)) },
                            onClick = {
                                showDlgSalir()
                                showMenu = false
                            })
                    }
                }
            }
        }
    )
}