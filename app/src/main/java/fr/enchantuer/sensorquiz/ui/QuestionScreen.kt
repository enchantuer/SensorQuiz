package fr.enchantuer.sensorquiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.data.AnswerState
import fr.enchantuer.sensorquiz.data.Answers
import fr.enchantuer.sensorquiz.data.QuestionType
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme

@Composable
fun QuestionScreen(
    onGameOver: () -> Unit,
    modifier: Modifier = Modifier,
    questionViewModel: QuestionViewModel = viewModel(),
) {
    val uiState by questionViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isGameOver) {
        if (uiState.isGameOver) {
            onGameOver()
        }
    }

    // Centrer le contenu verticalement et horizontalement
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Question(
                question = uiState.currentQuestion,
                imageId = R.drawable.ic_launcher_foreground,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Choices(
                choices = uiState.answers ?: Answers("True", "False"),
                questionType = uiState.questionType,
                answerState = uiState.answerState,
                userAnswer = questionViewModel.userAnswer,
                onClick = { choice ->
                    questionViewModel.updateUserAnswer(choice)
                    questionViewModel.checkAnswer()
                }
            )
        }
    }
}

@Composable
fun Question(
    question: String,
    imageId: Int,
    modifier: Modifier = Modifier,
) {
    Card (modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "",
            )
            Text(
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                text = question,
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
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .weight(1f), // Aligne la hauteur minimale des boutons A et B
            horizontalArrangement = Arrangement.Center
        ) {
            ChoiceButton(
                choices.answer1,
                answerState = answerState,
                userAnswer = userAnswer,
                onClick = { onClick(choices.answer1) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight() // Prend la hauteur du plus grand bouton
            )
            Spacer(modifier = Modifier.width(16.dp))
            ChoiceButton(
                choices.answer2,
                answerState = answerState,
                userAnswer = userAnswer,
                onClick = { onClick(choices.answer2) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }

        if (questionType === QuestionType.THREE_CHOICES) {
            Spacer(modifier = Modifier.height(16.dp))
            val other = stringResource(R.string.other)
            ChoiceButton(
                choice = other,
                userAnswer = userAnswer,
                answerState = answerState,
                onClick = { onClick(other) },
                modifier = Modifier
                    .fillMaxWidth(0.5f) // Même largeur approximatif que A et B
                    .weight(1f) // Prend la même hauteur que A et B
            )
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
        userAnswer == choice  && answerState == AnswerState.CORRECT -> Color.Green
        userAnswer == choice  && answerState == AnswerState.WRONG -> Color.Red
        else -> MaterialTheme.colorScheme.primary
    }

    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(text = choice)
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionScreenPreview() {
    SensorQuizTheme {
        QuestionScreen(onGameOver = {})
    }
}