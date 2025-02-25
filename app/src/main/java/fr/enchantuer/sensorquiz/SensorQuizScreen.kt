package fr.enchantuer.sensorquiz

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorQuizTopAppBar(
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
                    text = stringResource(R.string.app_name),
                )
            } else {
                LinearProgressIndicator(
                    progress = { questionCount.first.toFloat() / questionCount.second }
                )
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

@Preview
@Composable
fun TopAppBarPreview() {
    SensorQuizTopAppBar(canNavigateBack = true, canAccessSettings = true, navigateUp = {}, openSetting = {})
}

@Preview
@Composable
fun TopAppBarEmptyPreview() {
    SensorQuizTopAppBar(canNavigateBack = false, canAccessSettings = false, navigateUp = {}, openSetting = {})
}

@Preview
@Composable
fun TopAppBarQuestionCountPreview() {
    SensorQuizTopAppBar(canNavigateBack = true, canAccessSettings = true, navigateUp = {}, openSetting = {}, questionCount = Pair(2, 10))
}