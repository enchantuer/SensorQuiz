package fr.enchantuer.sensorquiz.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.SensorQuizScreen
import fr.enchantuer.sensorquiz.ui.components.HorizontalButtonsList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import fr.enchantuer.sensorquiz.ui.theme.LavenderPurple
import fr.enchantuer.sensorquiz.ui.theme.violetGradientBackground
//Cadre blanc
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.text.font.FontWeight
//Ajout logo
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource



@Composable
fun MenuScreen(
    canResume: Boolean,
    onNextButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
        .fillMaxSize()
        .violetGradientBackground(),
        contentAlignment = Alignment.Center
    ) {
        // On empile l'image (en haut) et la card (en dessous)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 🔹 LOGO EN HAUT
            Image(
                painter = painterResource(id = R.drawable.logo_sensor_quiz),
                contentDescription = "Logo SensorQuiz",
                modifier = Modifier
                    .height(300.dp)
                    .padding(top = 15.dp)
            )

            // 🔹 BOUTONS DANS LA CARD
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
                    if (canResume) {
                        MenuButton(text = stringResource(R.string.resume)) { }
                    }

                    MenuButton(text = "🎯 Mode solo") {
                        onNextButtonClick(SensorQuizScreen.Theme.name)
                    }

                    MenuButton(text = "🧩 Défi en ligne") {
                        onNextButtonClick(SensorQuizScreen.MultiplayerMenu.name)
                    }

                    MenuButton(text = "⚙️ Préférences") {
                        onNextButtonClick(SensorQuizScreen.Settings.name)
                    }
                }
            }
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        elevation = ButtonDefaults.buttonElevation( // 🌑 Petite ombre
            defaultElevation = 6.dp,
            pressedElevation = 8.dp
        ),
        colors = ButtonDefaults.buttonColors( // 🎨 Couleur de fond violette
            containerColor = LavenderPurple,
            contentColor = Color.White // 🖤 Texte en noir
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy( // 🅱️ Texte gras
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        canResume = false,
        modifier = Modifier
            .fillMaxSize()
            .violetGradientBackground(), // ✅ le dégradé violet ici
        onNextButtonClick = {}
    )
}

@Preview
@Composable
fun MenuScreenResumePreview() {
    MenuScreen(
        canResume = true,
        modifier = Modifier
            .fillMaxSize()
            .background(LavenderPurple),
        onNextButtonClick = {}
    )
}