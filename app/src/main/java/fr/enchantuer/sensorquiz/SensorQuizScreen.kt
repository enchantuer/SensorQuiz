package fr.enchantuer.sensorquiz

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import fr.enchantuer.sensorquiz.ui.*
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorQuizTopAppBar(
    currentScreen: SensorQuizScreen,
    canNavigateBack: Boolean,
    canAccessSettings: Boolean,
    navigateUp: () -> Unit,
    openSetting: () -> Unit,
    modifier: Modifier = Modifier,
    questionCount: Pair<Int, Int>? = null
) {
    CenterAlignedTopAppBar(
        title = {
            if (questionCount == null) {
                Text(text = stringResource(currentScreen.title))
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
    val questionViewModel: QuestionViewModel = viewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SensorQuizScreen.valueOf(
        backStackEntry?.destination?.route ?: SensorQuizScreen.Menu.name
    )

    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            SensorQuizTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                canAccessSettings = false,
                openSetting = {
                    navController.navigate(SensorQuizScreen.Settings.name)
                }
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
                    onNextButtonClick = { destination, category ->
                        selectedCategory = category
                        navController.navigate(destination)
                    }
                )
            }

            composable(route = SensorQuizScreen.Settings.name) {
                SettingsScreen()
            }

            composable(route = SensorQuizScreen.Theme.name) {
                ThemeScreen(
                    modifier = Modifier.fillMaxSize(),
                    selectedCategory = selectedCategory,
                    onNextButtonClick = {
                        questionViewModel.selectedCategory = selectedCategory
                        questionViewModel.restart()
                        navController.navigate(SensorQuizScreen.Question.name)
                    }
                )
            }

            composable(route = SensorQuizScreen.Question.name) {
                QuestionScreen(
                    modifier = Modifier.fillMaxSize(),
                    selectedCategory = questionViewModel.selectedCategory ?: "Education",
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
                        navController.navigate(SensorQuizScreen.Lobby.name)
                    },
                    onJoinClick = {
                        navController.navigate(SensorQuizScreen.Lobby.name)
                    },
                    onNextClick = {
                        navController.navigate(SensorQuizScreen.Question.name)
                    }
                )
            }

            composable(route = SensorQuizScreen.Lobby.name) {
                LobbyScreen(
                    modifier = Modifier.fillMaxSize(),
                    onStartClick = {
                        navController.navigate(SensorQuizScreen.Question.name) {
                            popUpTo(SensorQuizScreen.Menu.name) {
                                saveState = true
                            }
                        }
                    },
                    onThemeClick = {
                        navController.navigate(SensorQuizScreen.Theme.name)
                    }
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
}