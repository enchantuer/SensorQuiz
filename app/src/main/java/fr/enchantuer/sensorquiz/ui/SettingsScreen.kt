package fr.enchantuer.sensorquiz.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    val settingsList = listOf(
        Pair(R.string.vibrate, true),
        Pair(R.string.hidden_other_choice, false),
        Pair(R.string.lean_to_answear, true),
        Pair(R.string.shake_for_other_choice, true),
        Pair(R.string.sensor_question, true),
        Pair(R.string.sensor_question, true),
        Pair(R.string.sensor_question, true),
    )

    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box {
                // Player Icon
                Icon(
                    painter = painterResource(R.drawable.player),
                    contentDescription = stringResource(R.string.player_icon),
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.secondary)
                )
                // Edit Button
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape)
                        .border(5.dp, color = MaterialTheme.colorScheme.background, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_button),
                        tint = Color.Black
                    )
                }
            }
        }
        item {
            PseudoSettingItem()
        }
        item {
            SoundSettingItem()
        }
        items(settingsList) { item ->
            SettingItem(
                text = item.first,
                initialChecked = item.second
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
    initialChecked: Boolean
) {
    var checked by rememberSaveable { mutableStateOf(initialChecked) }

    SettingCard(
        clickable = true,
        onClick = { checked = !checked }
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
                onCheckedChange = { checked = it }
            )
        }
    }
}

@Composable
fun SoundSettingItem() {
    var volume by rememberSaveable { mutableFloatStateOf(0.5f) }

    SettingCard(
        clickable = false,
        onClick = {}
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp)
        ) {
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Volume Bas")
            Slider(
                value = volume,
                onValueChange = { volume = it },
                modifier = Modifier.weight(1f),
                colors = SliderDefaults.colors(
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Volume Haut")
        }
    }
}

@Composable
fun PseudoSettingItem() {
    var pseudo by rememberSaveable { mutableStateOf("Joueur 1") }
    val focusRequester = remember { FocusRequester() }

    SettingCard(
        clickable = true,
        onClick = { focusRequester.requestFocus() },
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
                onValueChange = { pseudo = it },
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