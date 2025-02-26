package fr.enchantuer.sensorquiz.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme

@Composable
fun MultiplayerMenuScreen(
    onHostClick: () -> Unit,
    onJoinClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
   Column(
       modifier = modifier
           .padding(16.dp)
           .width(IntrinsicSize.Min),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
   ) {
       Button(
           onClick = onHostClick,
           modifier = Modifier.fillMaxWidth()
       ) {
           Text(
               text = stringResource(R.string.host)
           )
       }

       var codeInput by remember { mutableStateOf("") }
       OutlinedTextField(
           modifier = Modifier
               .fillMaxWidth(),
           singleLine = true,
           value = codeInput,
           onValueChange = { codeInput = it },
           label = { Text(text = stringResource(R.string.input_code) + " : ") },
           trailingIcon = {
               IconButton(onClick = { onJoinClick(codeInput) }) {
                   Icon(
                       imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                       contentDescription = stringResource(R.string.join)
                   )
               }
           },
       )
   }
}

@Preview(showBackground = true)
@Composable
fun MultiplayerMenuScreenPreview() {
    SensorQuizTheme {
        MultiplayerMenuScreen(onHostClick = {}, onJoinClick = {})
    }
}