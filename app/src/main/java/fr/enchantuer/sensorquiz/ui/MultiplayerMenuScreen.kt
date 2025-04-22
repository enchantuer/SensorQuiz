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
import androidx.compose.foundation.background
import fr.enchantuer.sensorquiz.ui.theme.LavenderPurple
import androidx.compose.foundation.layout.fillMaxSize
import fr.enchantuer.sensorquiz.ui.theme.violetGradientBackground

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import android.content.Intent
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource


import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.height

@Composable
fun MultiplayerMenuScreen(
    onHostClick: () -> Unit = {},
    onJoinClick: (String) -> Unit = {},
    onNextClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var codeInput by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .violetGradientBackground()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 🔹 Titre
            Text(
                text = "Prêt à jouer ?\nEntre ton code ! 🎮",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )

            // 🔹 Image
            Image(
                painter = painterResource(id = R.drawable.multijoueur),
                contentDescription = "Illustration multijoueur",
                modifier = Modifier
                    .width(160.dp)
                    .padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(80.dp))

            // 🔹 Bloc blanc
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Champ de texte
                    OutlinedTextField(
                        value = codeInput,
                        onValueChange = { codeInput = it },
                        label = { Text("Code d'invitation") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 🔹 Bouton "Suivant"
                    Button(
                        onClick = { onNextClick() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LavenderPurple,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(6.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Suivant")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Suivant",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    // 🔹 Bouton "Créer une partie" sans icône
                    Button(
                        onClick = { onHostClick() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LavenderPurple,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(6.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            "Créer une partie",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            // Snackbar
            SnackbarHost(hostState = snackbarHostState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MultiplayerMenuScreenPreview() {
    SensorQuizTheme {
        MultiplayerMenuScreen(onHostClick = {}, onJoinClick = {})
    }
}