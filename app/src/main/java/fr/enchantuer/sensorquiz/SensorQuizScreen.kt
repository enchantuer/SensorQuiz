package fr.enchantuer.sensorquiz

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.enchantuer.sensorquiz.ui.LobbyScreen
import fr.enchantuer.sensorquiz.ui.LocalisationScreen
import fr.enchantuer.sensorquiz.ui.MenuScreen
import fr.enchantuer.sensorquiz.ui.MultiplayerMenuScreen
import fr.enchantuer.sensorquiz.ui.QuestionScreen
import fr.enchantuer.sensorquiz.ui.ResultsScreen
import fr.enchantuer.sensorquiz.ui.SettingsScreen
import fr.enchantuer.sensorquiz.ui.ThemeScreen
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
                Text(
                    text = stringResource(currentScreen.title),
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                ) {
                    LinearProgressIndicator(
                        progress = { questionCount.first.toFloat() / questionCount.second },
                        modifier = Modifier
                            .fillMaxHeight(),
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
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SensorQuizScreen.valueOf(
        backStackEntry?.destination?.route ?: SensorQuizScreen.Menu.name
    )

    Scaffold (
        topBar ={
            SensorQuizTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                canAccessSettings = false,
                openSetting = {}
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
                )
            }

            composable(route = SensorQuizScreen.Settings.name) {
                SettingsScreen()
            }

            composable(route = SensorQuizScreen.Theme.name) {
                ThemeScreen(
                    modifier = Modifier.fillMaxSize(),
                    onNextButtonClick = {
                        navController.navigate(SensorQuizScreen.Localisation.name)
                    }
                )
            }

            composable(route = SensorQuizScreen.Localisation.name) {
                LocalisationScreen(
                    modifier = Modifier.fillMaxSize(),
                    onNextButtonClick = {
                        navController.navigate(SensorQuizScreen.Question.name) {
                            popUpTo(SensorQuizScreen.Menu.name) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            composable(route = SensorQuizScreen.Question.name) {
                QuestionScreen(
                    modifier = Modifier.fillMaxSize(),
                    onNextButtonClick = {
                        navController.navigate(SensorQuizScreen.Results.name) {
                            popUpTo(SensorQuizScreen.Menu.name) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            composable(route = SensorQuizScreen.Results.name) {
                ResultsScreen(
                    modifier = Modifier.fillMaxSize(),
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
                    }
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
    Localisation(R.string.choose_localisation),
    Question(R.string.Question),
    Results(R.string.resultat),
    MultiplayerMenu(R.string.multiplayer_menu),
    Lobby(R.string.lobby),
}

@Preview
@Composable
fun TopAppBarPreview() {
    SensorQuizTheme {
        SensorQuizTopAppBar(canNavigateBack = true, canAccessSettings = true, navigateUp = {}, openSetting = {}, currentScreen = SensorQuizScreen.Menu)
    }
}

@Preview
@Composable
fun TopAppBarEmptyPreview() {
    SensorQuizTheme {
        SensorQuizTopAppBar(canNavigateBack = false, canAccessSettings = false, navigateUp = {}, openSetting = {}, currentScreen = SensorQuizScreen.Menu)
    }
}

@Preview
@Composable
fun TopAppBarQuestionCountPreview() {
    SensorQuizTheme {
        SensorQuizTopAppBar(canNavigateBack = true, canAccessSettings = true, navigateUp = {}, openSetting = {}, questionCount = Pair(2, 10), currentScreen = SensorQuizScreen.Menu)
    }
}