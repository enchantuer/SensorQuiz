package fr.enchantuer.sensorquiz.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme

@Composable
fun ResultsScreen(
    onReplayClick: () -> Unit,
    onHomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Score(score = 124, modifier = Modifier.fillMaxWidth())
        Statistics()
        Objectives()
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            ResultsButton(
                modifier = Modifier.weight(1f),
                text = R.string.replay,
                onClick = onReplayClick
            )
            ResultsButton(
                modifier = Modifier.weight(1f),
                text = R.string.home,
                onClick = onHomeClick
            )
        }
    }
}

@Composable
fun Score(
    score: Int,
    modifier: Modifier = Modifier
) {
    Card {
        Column(
            modifier = modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.score).uppercase(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp)
            )
            Text(
                text = score.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun ResultsButton(
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(text).uppercase(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun Statistics(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.statistics),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp)
            )
            StatisticsItem(
                text = R.string.correct_answers,
                value = "10",
            )
            StatisticsItem(
                text = R.string.wrong_answers,
                value = "2",
            )
        }
    }
}

@Composable
fun StatisticsItem(
    @StringRes text: Int,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = stringResource(text),
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun Objectives(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
    ) {
        LazyRow(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(8) {
                Badge(
                    icon = R.drawable.player,
                    text = "Badge 1 dsds dsdsds d s d sd sds ",
                    progressCount = 25,
                    progressObjective = 50
                )
            }
        }
    }
}

@Composable
fun Badge(
    @DrawableRes icon: Int,
    text: String,
    progressCount: Int,
    progressObjective: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(128.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(64.dp)
            )
            Text(
                text = text,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                LinearProgressIndicator(
                    progress = { progressCount.toFloat() / progressObjective },
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(96.dp),
                    trackColor = MaterialTheme.colorScheme.inversePrimary
                )
                Text(
                    text = "$progressCount / $progressObjective",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall.copy(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(2f, 2f),
                            blurRadius = 8f
                        )
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    SensorQuizTheme {
        ResultsScreen(modifier = Modifier.fillMaxSize(), onReplayClick = {}, onHomeClick = {})
    }
}