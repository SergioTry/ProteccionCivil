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
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.dam.proteccioncivil.ui.dialogs.DlgRecursos
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosMto
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosScreen
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosVM
import com.dam.proteccioncivil.ui.screens.login.LoginScreen
import com.dam.proteccioncivil.ui.screens.login.LoginVM
import com.dam.proteccioncivil.ui.screens.preferencias.PrefScreen
import com.dam.proteccioncivil.ui.screens.splash.SplashScreen
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
    Vehicles(title = R.string.screen_name_vehicles),
    VehiclesMto(title = R.string.screen_name_vehicles_mto),
    Anuncios(title = R.string.screen_name_announces),
    AnunciosMto(title = R.string.screen_name_announces_mto),
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

    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        AppScreens.valueOf(backStackEntry?.destination?.route ?: AppScreens.Home.name)
    val snackbarHostState = remember() { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val loginVM: LoginVM =
        viewModel(factory = LoginVM.Factory)

    val anunciosVM: AnunciosVM =
        viewModel(factory = AnunciosVM.Factory)

    val menuOptions = mapOf(
        Icons.Default.Home to stringResource(R.string.screen_name_home),
        Icons.Default.CalendarMonth to stringResource(R.string.screen_name_calendar),
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
                        scope = scope,
                        isLandscape = false,
                        currentScreen = currentScreen,
                        canNavigateBack = (currentScreen.name != AppScreens.Splash.name),
                        showLoginScreen = { navController.navigate(AppScreens.Login.name) },
                        showPrefScreen = { navController.navigate(AppScreens.Preferences.name) },
                        showAnoScreen = {
                            anunciosVM.getAll()
                            navController.navigate(AppScreens.Anuncios.name)
                        },
                        navigateUp = {
                            backButtonNavigation(currentScreen, navController)
                        }
                    )
                }
            },
            bottomBar = {
                if (currentScreen != AppScreens.Splash && currentScreen != AppScreens.Login) {
                    MainBottomBar(
                        menuOptions = menuOptions,
                        navController = navController,
                        anunciosVM = anunciosVM,
                        mainVM = mainVM
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
                mainVM,
                loginVM,
            )
        }
    }
    if (mainVM.uiMainState.showDlgRecursos) {
        DlgRecursos(
            onCancelarClick = { mainVM.setShowDlgRecursos(false) },
            onVehiculosClick = { mainVM.setShowDlgRecursos(false) },
            onVoluntariosClick = { mainVM.setShowDlgRecursos(false) })
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
    mainVM: MainVM,
    loginVM: LoginVM
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Splash.name,
        modifier = Modifier.padding(it)
    ) {
        composable(route = AppScreens.Splash.name) {
            SplashScreen(mainVM, {
                if (it) {
                    navController.navigate(AppScreens.Login.name)
                } else {
                    navController.navigate(AppScreens.Home.name)
                }
            }, "0.0.0")
        }
        composable(route = AppScreens.Home.name) {
            MainScreen()
        }
        composable(route = AppScreens.Login.name) {
            LoginScreen(
                version = "0.0.0", mainVM = mainVM, loginVM = loginVM, onNavUp = {
                    selectOption(Icons.Default.Home, navController, anunciosVM, mainVM = mainVM)
                },
                savedToken = mainVM.uiPrefState.token.isNotBlank() && mainVM.uiPrefState.token.isNotEmpty()
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
        composable(route = AppScreens.Vehicles.name) {

        }

        composable(route = AppScreens.AnunciosMto.name) {
            AnunciosMto(anunciosVM = anunciosVM,
                refresh = { navController.navigate(AppScreens.Anuncios.name) },
                onShowSnackBar = { scope.launch { snackbarHostState.showSnackbar(it) } })
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

        composable(route = AppScreens.Chat.name) {
            PantallaMensajes()
        }
    }
}

@Composable
fun MainBottomBar(
    menuOptions: Map<ImageVector, String>,
    navController: NavHostController,
    anunciosVM: AnunciosVM,
    mainVM: MainVM,
    modifier: Modifier = Modifier
) {
    NavigationBar {
        for ((clave, valor) in menuOptions) {
            if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                NavigationBarItem(
                    icon = { Icon(clave, contentDescription = null) },
                    label = { Text(valor) },
                    selected = false,
                    onClick = {
                        selectOption(
                            clave = clave,
                            navController = navController,
                            anunciosVM = anunciosVM,
                            mainVM = mainVM
                        )
                    },
                    enabled = true
                )
            } else {
                if (clave.name == Icons.Default.Apps.name) {
                    if (Token.conductor == 1) {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.DirectionsCar, contentDescription = null) },
                            label = { Text(stringResource(R.string.screen_name_vehicles)) },
                            selected = false,
                            onClick = {
                                selectOption(
                                    clave = Icons.Default.DirectionsCar,
                                    navController = navController,
                                    anunciosVM = anunciosVM,
                                    mainVM = mainVM
                                )
                            },
                            enabled = true
                        )
                    }
                } else {
                    NavigationBarItem(
                        icon = { Icon(clave, contentDescription = null) },
                        label = { Text(valor) },
                        selected = false,
                        onClick = {
                            selectOption(
                                clave = clave,
                                navController = navController,
                                anunciosVM = anunciosVM,
                                mainVM = mainVM
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
    anunciosVM: AnunciosVM,
    mainVM: MainVM
) {
    when (clave.name) {
        Icons.Default.Home.name -> {

            navController.navigate(
                AppScreens.Home.name
            )
        }

        Icons.Default.CalendarMonth.name -> {
            anunciosVM.getAll()
            navController.navigate(
                AppScreens.Calendar.name
            )
        }

        Icons.Default.WorkOutline.name -> {
//            aulasVM.cargarAulas()
//            aulasVM.resetInfoState()

        }

        Icons.AutoMirrored.Filled.Chat.name -> navController.navigate(
            AppScreens.Chat.name
        )

        else -> {
//            aulasVM.cargarAulas()
//            aulasVM.resetInfoState()
//            incsVM.cargarIncs()
//            incsVM.resetInfoState()

            when (Token.rango) {
                "Voluntario" -> {
                    if (Token.conductor == 1) {
                        navController.navigate(AppScreens.Vehicles.name)
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
        "DptosBus", "AulasBus", "IncsBus" -> navController.navigate(
            AppScreens.Home.name
        )

        else -> navController.navigateUp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    scope: CoroutineScope,
    isLandscape: Boolean,
    currentScreen: AppScreens,
    canNavigateBack: Boolean,
    showLoginScreen: () -> Unit,
    showPrefScreen: () -> Unit,
    showAnoScreen: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }
    val activity = (LocalContext.current as? Activity)

    CenterAlignedTopAppBar(
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
                        contentDescription = null
                    )
                }
            } else if (isLandscape && currentScreen.name != AppScreens.Splash.name) {
//                IconButton(onClick = { scope.launch { drawerState.open() } }) {
//                    Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
//                }
            }
        },
        actions = {
            if (currentScreen.name.equals(AppScreens.Home.name)) {
                Row() {
                    IconButton(onClick = { showAnoScreen() }) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
                    }
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
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
                                //mainVM.setShowDlgSalir(true)
                            })
                    }
//                if (mainVM.uiMainState.showDlgSalir) {
//                    showMenu = false
//                    DlgConfirmacion(
//                        mensaje = R.string.txt_salir,
//                        onCancelarClick = { mainVM.setShowDlgSalir(false) },
//                        onAceptarClick = {
//                            mainVM.setShowDlgSalir(false)
//                            activity?.finish()
//                        })
//                }
                }
            } else {
//                when (currentScreen.name) {
//                    "AulasBus" -> {
//                        // Validación de que solo se pueda filtrar si eres admin
//                        if (mainVM.uiMainState.dptoLog != null && mainVM.uiMainState.dptoLog!!.id == 0) {
//                            IconButton(onClick = { showFilterScreen() }) {
//                                Icon(
//                                    imageVector = Icons.Filled.FilterAlt,
//                                    contentDescription = null
//                                )
//                            }
//                        }
//                    }
//
//                    "IncsBus" -> {
//                        IconButton(onClick = { showFilterScreen() }) {
//                            Icon(
//                                imageVector = Icons.Filled.FilterAlt,
//                                contentDescription = null
//                            )
//                        }
//                    }
//                }
            }

        }
    )
}