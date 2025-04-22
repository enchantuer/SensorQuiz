package fr.enchantuer.sensorquiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.SensorQuizScreen
import fr.enchantuer.sensorquiz.ui.theme.LavenderPurple
import fr.enchantuer.sensorquiz.ui.theme.violetGradientBackground

@Composable
fun MenuScreen(
    canResume: Boolean,
    onNextButtonClick: (String, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .violetGradientBackground(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_sensor_quiz),
                contentDescription = "Logo SensorQuiz",
                modifier = Modifier
                    .height(300.dp)
                    .padding(top = 15.dp)
            )

            // Card
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

                    // Mode solo → passe vers ThemeScreen avec null
                    MenuButton(text = "🎯 Mode solo") {
                        onNextButtonClick(SensorQuizScreen.Theme.name, null)
                    }

                    // Défi en ligne → passe vers MultiplayerMenu sans catégorie
                    MenuButton(text = "🧩 Défi en ligne") {
                        onNextButtonClick(SensorQuizScreen.MultiplayerMenu.name, null)
                    }

                    // Préférences → idem
                    MenuButton(text = "⚙️ Préférences") {
                        onNextButtonClick(SensorQuizScreen.Settings.name, null)
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
        modifier = Modifier.fillMaxWidth(),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = LavenderPurple,
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
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
            .violetGradientBackground(),
        onNextButtonClick = { _, _ -> }
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
        onNextButtonClick = { _, _ -> }
    )
}