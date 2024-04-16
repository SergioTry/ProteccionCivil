package com.dam.proteccioncivil.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosVM
import com.dam.proteccioncivil.pantallas.chat.PantallaMensajes
import com.dam.proteccioncivil.pantallas.home.MainScreen
import com.dam.proteccioncivil.ui.theme.ProteccionCivilTheme
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.exposed.sql.Database
import java.sql.Connection


//class Anuncio(id: EntityID<Int>) : IntEntity(id) {
//    companion object : IntEntityClass<Anuncio>()
//    var sequelId by StarWarsFilms.sequelId
//    var name     by StarWarsFilms.name
//    var director by StarWarsFilms.director
//}


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
    Chat(title = R.string.screen_name_chat),
    PreventivosMto(title = R.string.screen_name_preventidos_mto)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainApp(
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

    val menuOptions = mapOf(
        Icons.Default.Home to stringResource(R.string.screen_name_home),
        Icons.Default.Person to stringResource(R.string.screen_name_calendar),
        Icons.Default.AccountBox to stringResource(R.string.screen_name_news),
        Icons.Default.Notifications to stringResource(R.string.screen_name_chat),
        Icons.Default.Build to stringResource(R.string.screen_name_vehicles),
    )


    var connection: Connection;

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        && windowSize == WindowWidthSizeClass.Compact
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                MainTopAppBar(
                    scope = scope,
                    isLandscape = false,
                    currentScreen = currentScreen,
                    canNavigateBack = false,
                    showLoginScreen = { navController.navigate(AppScreens.Login.name) },
                    showPrefScreen = { navController.navigate(AppScreens.Preferences.name) },
                    navigateUp = {
                        //backButtonNavigation(currentScreen, navController)
                    }
                )
            },
            bottomBar = {
                MainBottomBar(
                    menuOptions = menuOptions,
                    navController = navController
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) {
            NavHostRoutes(
                navController,
                it,
                scope,
                snackbarHostState
            )
        }
    } else {
        //ModalNavigationDrawer?
    }

    val thread = Thread {
        val ConnectionClass = ConnectionClass()
        val db = ConnectionClass.getConexion()
//        val db = Database.connect("jdbc:mysql://34.175.113.180:3306/ProteccionCivil", driver = "com.mysql.jdbc.Driver",
//            user = "admin", password = "admin")
        if (db == null) {
            println("No hay conexión")
        } else {
            println("Conectado")
        }
        val anunciosVM = AnunciosVM()
        anunciosVM.printAnuncios()
    }
    thread.start() // Inicia el hilo
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
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.name,
        modifier = Modifier.padding(it)
    ) {
        composable(route = AppScreens.Home.name) {
            MainScreen()
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
    modifier: Modifier = Modifier
) {
    NavigationBar {
        for ((clave, valor) in menuOptions) {
            NavigationBarItem(
                icon = { Icon(clave, contentDescription = null) },
                label = { Text(valor) },
                selected = valor == AppScreens.Home.name,
                onClick = {
                    selectOption(
                        clave = clave,
                        navController = navController
                    )
                },
                enabled = true
            )
        }
    }
}

private fun selectOption(
    clave: ImageVector,
    navController: NavHostController
) {
    when (clave.name) {
        Icons.Default.Home.name -> navController.navigate(
            AppScreens.Home.name
        )

        Icons.Default.Person.name -> navController.navigate(
            AppScreens.Calendar.name
        )

        Icons.Default.AccountBox.name -> {
//            aulasVM.cargarAulas()
//            aulasVM.resetInfoState()
            navController.navigate(
                AppScreens.News.name
            )
        }

        Icons.Default.Notifications.name -> navController.navigate(
            AppScreens.Chat.name
        )

        else -> {
//            aulasVM.cargarAulas()
//            aulasVM.resetInfoState()
//            incsVM.cargarIncs()
//            incsVM.resetInfoState()
            navController.navigate(AppScreens.Vehicles.name)
        }
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
                IconButton(onClick = navigateUp) {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainAppPreview() {
    ProteccionCivilTheme {
        MainApp(WindowWidthSizeClass.Compact)
    }
}