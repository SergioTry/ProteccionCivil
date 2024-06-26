package com.dam.proteccioncivil.ui.main

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.screens.calendario.CalendarioVM
import com.dam.proteccioncivil.ui.screens.vehiculos.VehiculosVM


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    navController: NavHostController,
    currentScreen: AppScreens,
    canNavigateBack: Boolean,
    showLoginScreen: () -> Unit,
    showPrefScreen: () -> Unit,
    showDatosPersonalesScreen: () -> Unit,
    showSobreScreen: () -> Unit,
    calendarioVM: CalendarioVM,
    showAnoScreen: () -> Unit,
    showDlgSalir: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        colors =
        TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        ),
        title = {
            if (currentScreen.title != R.string.screen_name_splash) {
                Text(text = stringResource(currentScreen.title))
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                if (!Token.username.isNullOrBlank()) {
                    IconButton(
                        onClick = {
                            navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
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
                        tint = MaterialTheme.colorScheme.background
                    )
                }
            }
        },
        actions = {
            if (currentScreen.name == AppScreens.Home.name) {
                Row {
                    IconButton(onClick = {
                        showAnoScreen()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                    IconButton(onClick = {
                        showMenu = !showMenu
                    }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert, contentDescription = null,
                            tint = MaterialTheme.colorScheme.background
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

                        DropdownMenuItem(text = { Text(text = stringResource(R.string.screen_name_personal_data)) },
                            onClick = {
                                showDatosPersonalesScreen()
                                showMenu = false
                            })

                        DropdownMenuItem(text = { Text(text = stringResource(R.string.screen_name_about)) },
                            onClick = {
                                showSobreScreen()
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

@Composable
fun MainBottomBar(
    menuOptions: Map<ImageVector, String>,
    navController: NavHostController,
    mainVM: MainVM,
    resetFilters: () -> Unit,
    vehiculosVM: VehiculosVM
) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
        for ((clave, valor) in menuOptions) {
            if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                NavigationBarItem(
                    icon = {
                        Icon(
                            clave, contentDescription = null,
                            tint = MaterialTheme.colorScheme.background
                        )
                    },
                    label = {
                        Text(
                            text = valor,
                            color = MaterialTheme.colorScheme.background
                        )
                    },
                    selected = false,
                    onClick = {
                        selectOption(
                            clave = clave,
                            navController = navController,
                            mainVM = mainVM,
                            resetFilters = resetFilters
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
                                    tint = MaterialTheme.colorScheme.background
                                )
                            },
                            label = {
                                Text(
                                    stringResource(R.string.screen_name_vehicles),
                                    color = MaterialTheme.colorScheme.background
                                )
                            },
                            selected = false,
                            onClick = {
                                vehiculosVM.getAll()
                                selectOption(
                                    clave = Icons.Default.DirectionsCar,
                                    navController = navController,
                                    mainVM = mainVM,
                                    resetFilters
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
                                tint = MaterialTheme.colorScheme.background
                            )
                        },
                        label = { Text(valor, color = MaterialTheme.colorScheme.background) },
                        selected = false,
                        onClick = {
                            selectOption(
                                clave = clave,
                                navController = navController,
                                mainVM = mainVM,
                                resetFilters
                            )
                        },
                        enabled = true
                    )
                }
            }
        }
    }
}

@Composable
fun CustomSnackBar(
    snackbarHostState: SnackbarHostState,
    context: Context
) {
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    SnackbarHost(snackbarHostState) {
        // No se ha encontrado una manera simple de asignar un booleano para indicar si
        // el snackBar es de tipo success o error, por lo que se hace uso del parámetro
        // predefinido actionLabel para indicar el tipo.

        val isSuccess = it.visuals.actionLabel != null
        Snackbar(
            containerColor = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            val painter: Painter =
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = if (isSuccess) R.drawable.check else R.drawable.error)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true) // Transición de carga activa
                        }).build(), imageLoader = imageLoader
                )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSuccess) {
                    Image(
                        painter = painter,
                        contentDescription = getString(context, R.string.guardia_image_desc),
                        modifier = Modifier
                            .weight(0.15f)
                    )

                } else {
                    Image(
                        painter = painter,
                        contentDescription = getString(context, R.string.guardia_image_desc),
                        modifier = Modifier
                            .size(50.dp)
                            .weight(0.15f)
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(0.7f),
                    text = it.visuals.message,
                    color = MaterialTheme.colorScheme.tertiary
                )
                IconButton(
                    modifier = Modifier
                        .weight(0.15f),
                    onClick = {
                        snackbarHostState.currentSnackbarData?.dismiss()
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = getString(context, R.string.close_icon_desc),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

        }
    }
}

