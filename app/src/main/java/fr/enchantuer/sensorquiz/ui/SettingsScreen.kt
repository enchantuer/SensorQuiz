package fr.enchantuer.sensorquiz.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.platform.LocalContext
import fr.enchantuer.sensorquiz.ui.theme.violetGradientBackground

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE)

    val settingsList = listOf(
        Pair(R.string.use_sensor, "use_sensor"),
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .violetGradientBackground()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            PseudoSettingItem(
                initialPseudo = sharedPreferences.getString("user_pseudo", "Joueur") ?: "Joueur",
                onPseudoChanged = { newPseudo ->
                    sharedPreferences.edit().putString("user_pseudo", newPseudo).apply()
                }
            )
        }
        items(settingsList) { item ->
            SettingItem(
                text = item.first,
                initialChecked = sharedPreferences.getBoolean(item.second, true),
                onCheckedChange = { isChecked ->
                    sharedPreferences.edit().putBoolean(item.second, isChecked).apply()
                }
            )
        }
        // Add padding to the bottom of the lazy column
        item {
            Spacer(modifier = Modifier)
        }
    }
}

@Composable
fun SettingCard(
    clickable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.clickable(enabled = clickable, onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        content = content
    )
}

@Composable
fun SettingItem(
    @StringRes text: Int,
    initialChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var checked by rememberSaveable { mutableStateOf(initialChecked) }

    SettingCard(
        clickable = true,
        onClick = {
            checked = !checked
            onCheckedChange(checked)
        },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(text),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    onCheckedChange(it)
                }
            )
        }
    }
}

@Composable
fun PseudoSettingItem(
    initialPseudo: String,
    onPseudoChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var pseudo by rememberSaveable { mutableStateOf(initialPseudo) }
    val focusRequester = remember { FocusRequester() }

    SettingCard(
        clickable = true,
        onClick = { focusRequester.requestFocus() },
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text(
                text = stringResource(R.string.pseudo),
                style = MaterialTheme.typography.bodyLarge
            )
            OutlinedTextField(
                value = pseudo,
                onValueChange = {
                    pseudo = it
                    onPseudoChanged(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                singleLine = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SensorQuizTheme {
        SettingsScreen()
    }
}