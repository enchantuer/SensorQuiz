package fr.enchantuer.sensorquiz.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.enchantuer.sensorquiz.data.AnswerState
import fr.enchantuer.sensorquiz.data.Answers
import fr.enchantuer.sensorquiz.data.MAX_NUMBER_OF_QUESTIONS
import fr.enchantuer.sensorquiz.data.QuestionType
import fr.enchantuer.sensorquiz.ui.theme.LavenderPurple
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme
import fr.enchantuer.sensorquiz.ui.theme.violetGradientBackground

@Composable
fun QuestionScreen(
    onGameOver: () -> Unit,
    modifier: Modifier = Modifier,
    questionViewModel: QuestionViewModel = viewModel(),
) {
    val uiState by questionViewModel.uiState.collectAsState()

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE)
    val useSensor = sharedPreferences.getBoolean("use_sensor", true)

    if (useSensor) {
        val lifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {
                        Log.d("SensorTest", "Sensor Registered")
                        questionViewModel.startSensor(uiState.questionType)
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        Log.d("SensorTest", "Sensor Unregistered")
                        questionViewModel.stopSensor()
                    }
                    else -> {}
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
                questionViewModel.stopSensor()
            }
        }
    }

    if (useSensor) {
        LaunchedEffect(uiState.answerState) {
            if (uiState.answerState == AnswerState.NONE) {
                questionViewModel.startSensor(uiState.questionType)
            }
        }
    }

    LaunchedEffect(uiState.isGameOver) {
        if (uiState.isGameOver) {
            onGameOver()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .violetGradientBackground()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .align(Alignment.Center),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 🔼 Barre de progression
                ProgressHeader(current = uiState.currentQuestionCount, total = MAX_NUMBER_OF_QUESTIONS)

                // 🧠 Question centrée avec texte agrandi
                Text(
                    text = uiState.currentQuestion,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    lineHeight = 38.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // ✅ Réponses
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ChoiceButton(
                            choice = uiState.answers?.answer1 ?: "Vrai",
                            answerState = uiState.answerState,
                            userAnswer = questionViewModel.userAnswer,
                            onClick = {
                                questionViewModel.updateUserAnswer(uiState.answers?.answer1 ?: "Vrai")
                                questionViewModel.checkAnswer()
                            },
                            modifier = Modifier.weight(1f)
                        )

                        ChoiceButton(
                            choice = uiState.answers?.answer2 ?: "Faux",
                            answerState = uiState.answerState,
                            userAnswer = questionViewModel.userAnswer,
                            onClick = {
                                questionViewModel.updateUserAnswer(uiState.answers?.answer2 ?: "Faux")
                                questionViewModel.checkAnswer()
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (uiState.questionType == QuestionType.THREE_CHOICES) {
                        // ✅ Bouton "Autre" centré
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ChoiceButton(
                                choice = "Autre",
                                answerState = uiState.answerState,
                                userAnswer = questionViewModel.userAnswer,
                                onClick = {
                                    questionViewModel.updateUserAnswer("Autre")
                                    questionViewModel.checkAnswer()
                                },
                                modifier = Modifier.fillMaxWidth(0.5f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Question(
    question: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = question,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun Choices(
    onClick: (choice: String) -> Unit,
    choices: Answers,
    questionType: QuestionType,
    answerState: AnswerState,
    userAnswer: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ChoiceButton(
                    choice = choices.answer1,
                    answerState = answerState,
                    userAnswer = userAnswer,
                    onClick = { onClick(choices.answer1) },
                    modifier = Modifier.weight(1f)
                )
                ChoiceButton(
                    choice = choices.answer2,
                    answerState = answerState,
                    userAnswer = userAnswer,
                    onClick = { onClick(choices.answer2) },
                    modifier = Modifier.weight(1f)
                )
            }

            if (questionType == QuestionType.THREE_CHOICES) {
                val other = "Autre"
                ChoiceButton(
                    choice = other,
                    userAnswer = userAnswer,
                    answerState = answerState,
                    onClick = { onClick(other) },
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
            }
        }
    }
}

@Composable
fun ChoiceButton(
    choice: String,
    answerState: AnswerState,
    userAnswer: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = when {
        userAnswer == choice && answerState == AnswerState.CORRECT -> Color.Green
        userAnswer == choice && answerState == AnswerState.WRONG -> Color.Red
        else -> LavenderPurple
    }

    Button(
        modifier = modifier,
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = choice,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun ProgressHeader(current: Int, total: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${"%02d".format(current)} Question",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "$current sur $total",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black.copy(alpha = 0.3f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(total) { index ->
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .background(
                                color = if (index < current) LavenderPurple else Color.LightGray,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionScreenPreview() {
    SensorQuizTheme {
        QuestionScreen(
            onGameOver = {}
        )
    }
}