package fr.enchantuer.sensorquiz

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.enchantuer.sensorquiz.ui.LobbyScreen
import fr.enchantuer.sensorquiz.ui.MenuScreen
import fr.enchantuer.sensorquiz.ui.MultiplayerMenuScreen
import fr.enchantuer.sensorquiz.ui.QuestionScreen
import fr.enchantuer.sensorquiz.ui.QuestionViewModel
import fr.enchantuer.sensorquiz.ui.ResultsScreen
import fr.enchantuer.sensorquiz.ui.SettingsScreen
import fr.enchantuer.sensorquiz.ui.ThemeScreen
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.navArgument

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorQuizTopAppBar(
    currentRoute: String?,
    canNavigateBack: Boolean,
    canAccessSettings: Boolean,
    navigateUp: () -> Unit,
    openSetting: () -> Unit,
    modifier: Modifier = Modifier,
    questionCount: Pair<Int, Int>? = null,
) {
    Log.d("SensorQuizTopAppBar", "currentRoute: $currentRoute")
    val baseRoute = currentRoute?.split("/")?.first()
    Log.d("SensorQuizTopAppBar", "baseRoute: $baseRoute")
    val title = stringResource(SensorQuizScreen.valueOf(baseRoute ?: SensorQuizScreen.Menu.name).title)

    CenterAlignedTopAppBar(
        title = {
            if (questionCount == null) {
                Text(
                    text = title,
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.height(IntrinsicSize.Min)
                ) {
                    LinearProgressIndicator(
                        progress = { questionCount.first.toFloat() / questionCount.second },
                        modifier = Modifier.fillMaxHeight(),
                        trackColor = MaterialTheme.colorScheme.inversePrimary
                    )
                    Text(
                        text = "${questionCount.first} / ${questionCount.second}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelSmall.copy(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(2f, 2f),
                                blurRadius = 8f
                            )
                        )
                    )
                }
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (canAccessSettings) {
                IconButton(onClick = openSetting) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = stringResource(R.string.setting_button)
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun SensorQuizApp(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val questionViewModel : QuestionViewModel = viewModel()
    val sensorController = remember {
        SensorTiltDetection(sensorManager) { tilt ->
            Log.d("SensorTest", "onTiltDetected: $tilt")
            questionViewModel.chooseAnswerUsingSensor(tilt)
        }
    }



    // Important : ne pas réinitialiser à chaque recomposition
    LaunchedEffect(Unit) {
        questionViewModel.setSensorController(sensorController)
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        topBar = {
            SensorQuizTopAppBar(
                currentRoute = currentRoute,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                canAccessSettings = false,
                openSetting = { navController.navigate(SensorQuizScreen.Settings.name) }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = SensorQuizScreen.Menu.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = SensorQuizScreen.Menu.name) {
                MenuScreen(
                    modifier = Modifier.fillMaxSize(),
                    canResume = false,
                    onNextButtonClick = {
                        navController.navigate(it)
                    }
//                    onNextButtonClick = { destination, category ->
//                        selectedCategory = category
//                        navController.navigate(destination)
//                    }
                )
            }

            composable(route = SensorQuizScreen.Settings.name) {
                SettingsScreen()
            }

            composable(
                route = SensorQuizScreen.Theme.name,
            ) {
                val isLobby = questionViewModel.lobbyCode.value.isNotEmpty()
                ThemeScreen(
                    modifier = Modifier.fillMaxSize(),
                    questionViewModel = questionViewModel,
                    onNextButtonClick = {
                        if (isLobby) {
                            navController.navigateUp() // Go back to Lobby
                        } else {
                            questionViewModel.restart()
                            navController.navigate(SensorQuizScreen.Question.name) // Go to Question
                        }
                    }
                )
            }

            composable(route = SensorQuizScreen.Question.name) {
                QuestionScreen(
                    modifier = Modifier.fillMaxSize(),
                    onGameOver = {
                        navController.navigate(SensorQuizScreen.Results.name) {
                            popUpTo(SensorQuizScreen.Menu.name) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    questionViewModel = questionViewModel
                )
            }

            composable(route = SensorQuizScreen.Results.name) {
                ResultsScreen(
                    modifier = Modifier.fillMaxSize(),
                    questionViewModel = questionViewModel,
                    onReplayClick = {
                        navController.navigate(SensorQuizScreen.Question.name) {
                            popUpTo(SensorQuizScreen.Menu.name) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onHomeClick = {
                        navController.navigate(SensorQuizScreen.Menu.name) {
                            popUpTo(SensorQuizScreen.Menu.name) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    navController = navController
                )
            }

            composable(route = SensorQuizScreen.MultiplayerMenu.name) {
                MultiplayerMenuScreen(
                    modifier = Modifier.fillMaxSize(),
                    onHostClick = {
                        Log.d("SensorQuizApp", "Host screen route, lobbyCode: $it")
                        navController.navigate("${SensorQuizScreen.Lobby.name}/$it/true")
                    },
                    onJoinClick = {
                        navController.navigate("${SensorQuizScreen.Lobby.name}/$it/false")
                    }
                )
            }

            composable(
                route = "${SensorQuizScreen.Lobby.name}/{lobbyCode}/{isHost}",
                arguments = listOf(
                    navArgument("lobbyCode") { type = NavType.StringType },
                    navArgument("isHost") { type = NavType.BoolType }
                )
            ) {
                Log.d("SensorQuizApp", "Lobby screen route")
                val lobbyCode = it.arguments?.getString("lobbyCode") ?: ""
                val isHost = it.arguments?.getBoolean("isHost") ?: false
                Log.d("SensorQuizApp", "lobbyCode: $lobbyCode, isHost: $isHost")
                LobbyScreen(
                    modifier = Modifier.fillMaxSize(),
                    onStartGame = {
                        navController.navigate(SensorQuizScreen.Question.name) {
                            popUpTo(SensorQuizScreen.Menu.name) {
                                saveState = true
                            }
                        }
                    },
                    onThemeClick = {
                        navController.navigate(SensorQuizScreen.Theme.name)
                    },
                    lobbyCode = lobbyCode,
                    questionViewModel = questionViewModel,
                    isHost = isHost,
                )
            }
        }
    }
}

enum class SensorQuizScreen(@StringRes val title: Int) {
    Menu(R.string.app_name),
    Settings(R.string.setting),
    Theme(R.string.choose_theme),
    Question(R.string.Question),
    Results(R.string.resultat),
    MultiplayerMenu(R.string.multiplayer_menu),
    Lobby(R.string.lobby),
}

/*
@Preview
@Composable
fun TopAppBarPreview() {
    SensorQuizTheme {
        SensorQuizTopAppBar(
            canNavigateBack = true,
            canAccessSettings = true,
            navigateUp = {},
            openSetting = {},
            currentScreen = SensorQuizScreen.Menu
        )
    }
}*/
