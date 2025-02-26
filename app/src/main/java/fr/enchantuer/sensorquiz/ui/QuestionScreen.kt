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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme

@Composable
fun QuestionScreen(
    onNextButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Centrer le contenu verticalement et horizontalement
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Question(
                question = "Quel est la capital de la France ?",
                imageId = R.drawable.ic_launcher_foreground,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Choices(
                choices = Pair("Nantes", "Paris"),
                onClick = { onNextButtonClick() },
                canOther = true
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
    choices: Pair<String, String>,
    onClick: () -> Unit,
    canOther: Boolean,
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
                choices.first,
                onClick = onClick,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight() // Prend la hauteur du plus grand bouton
            )
            Spacer(modifier = Modifier.width(16.dp))
            ChoiceButton(
                choices.second,
                onClick = onClick,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }

        if (canOther) {
            Spacer(modifier = Modifier.height(16.dp))
            ChoiceButton(
                stringResource(R.string.other),
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth(0.5f) // Même largeur approximatif que A et B
                    .weight(1f) // Prend la même hauteur que A et B
            )
        }
    }
}

@Composable
fun ChoiceButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionScreenPreview() {
    SensorQuizTheme {
        QuestionScreen(onNextButtonClick = {})
    }
}