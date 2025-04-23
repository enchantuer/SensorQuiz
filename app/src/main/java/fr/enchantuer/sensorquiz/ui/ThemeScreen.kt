package fr.enchantuer.sensorquiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    onNextButtonClick: () -> Unit,
    questionViewModel: QuestionViewModel,
    modifier: Modifier = Modifier
) {
    fun next(category: String) {
        questionViewModel.setCategory(category)
        onNextButtonClick()
    }
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .violetGradientBackground()
            .verticalScroll(scrollState),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_sensor_quiz),
                contentDescription = "Logo SensorQuiz",
                modifier = Modifier
                    .height(300.dp)
                    .padding(top = 16.dp)
            )

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
                    ThemeButton("🎓 Éducation") { next("Education") }
                    ThemeButton("🌍 Monde et culture") { next("WorldCulture") }
                    ThemeButton("🎭 Divertissement") { next("Entertainment") }
                    ThemeButton("🧠 Logique & mémoire") { next("LogicMemory") }
                    ThemeButton("📱 Tech & numérique") { next("Tech") }
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