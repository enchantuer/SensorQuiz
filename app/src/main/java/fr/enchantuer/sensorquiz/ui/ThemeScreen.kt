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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.ui.theme.LavenderPurple
import fr.enchantuer.sensorquiz.ui.theme.violetGradientBackground

@Composable
fun ThemeScreen(
    onNextButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .violetGradientBackground(), // ✅ FOND DÉGRADÉ RÉTABLI
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 80.dp), // remonter la Card
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // ✅ Logo statique sans animation
            Image(
                painter = painterResource(id = R.drawable.logo_sensor_quiz),
                contentDescription = "Logo SensorQuiz",
                modifier = Modifier
                    .height(300.dp)
                    .padding(top = 16.dp)
            )

            // ✅ Card contenant les boutons
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
                    ThemeButton("🎓 Éducation") { onNextButtonClick("Éducation") }
                    ThemeButton("🌍 Monde et culture") { onNextButtonClick("Monde et culture") }
                    ThemeButton("🎭 Divertissement") { onNextButtonClick("Divertissement") }
                    ThemeButton("🧠 Logique & mémoire") { onNextButtonClick("Logique & mémoire") }
                    ThemeButton("📱 Tech & numérique") { onNextButtonClick("Tech & numérique") }
                }
            }
        }
    }
}

@Composable
fun ThemeButton(
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