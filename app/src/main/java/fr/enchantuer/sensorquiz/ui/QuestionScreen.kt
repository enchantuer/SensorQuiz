package fr.enchantuer.sensorquiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R

@Composable
fun QuestionScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Question(
            question = "placeholder",
            imageId = R.drawable.ic_launcher_foreground,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Choices(
            choices = listOf("AAAAAAAAAAAAAAAAAAA", "BBBBBB", "Autre")
        )
    }
}

@Composable
fun Question(
    question: String,
    imageId: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "",
        )
        Text(
            textAlign = TextAlign.Center,
            text = question,
        )
    }
}

@Composable
fun Choices(
    choices: List<String>,
    modifier: Modifier = Modifier,
) {
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ChoiceButton(choices[0], modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .onGloballyPositioned { layoutCoordinates ->
                    buttonSize = layoutCoordinates.size
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            ChoiceButton(choices[1], modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
            )
        }
        if (choices.size > 2) {
            Spacer(modifier = Modifier.height(16.dp))
            ChoiceButton(choices[2], modifier = Modifier
                .width(with(LocalDensity.current) { buttonSize.width.toDp() })
                .height(with(LocalDensity.current) { buttonSize.height.toDp() })
            )
        }
    }
}

@Composable
fun ChoiceButton(
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = {}
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
fun QuestionScreenPreview() {
    QuestionScreen(modifier = Modifier.fillMaxHeight())
}