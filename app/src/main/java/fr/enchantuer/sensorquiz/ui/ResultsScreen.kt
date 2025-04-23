package fr.enchantuer.sensorquiz.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.SensorQuizScreen
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme

@Composable
fun ResultsScreen(
    onReplayClick: () -> Unit,
    onHomeClick: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    questionViewModel: QuestionViewModel = viewModel()
) {
    val playerList = remember { mutableStateOf<List<Pair<String, Int>>>(emptyList()) }
    // Restart the game when the user leave the result screen
    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination.route != SensorQuizScreen.Results.name) questionViewModel.restart()
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    val uiState by questionViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Score(
            score = uiState.score,
            modifier = Modifier.fillMaxWidth())
        Statistics(
            correctAnswers = uiState.correctAnswers,
            wrongAnswers = uiState.currentQuestionCount - uiState.correctAnswers
        )
        Log.d("ResultScreen", "GameMode: ${questionViewModel.gameMode.value}")
        if (questionViewModel.gameMode.value == GameMode.MULTI) {
            val firestore = Firebase.firestore
            val lobbyCode = questionViewModel.lobbyCode.value
            val lobbyRef = firestore.collection("lobbies").document(lobbyCode)

            LaunchedEffect(true) {
                Log.d("ResultScreen", "Launch effect lobbyCode: $lobbyCode")
                lobbyRef.addSnapshotListener { snapshot, _ ->
                    // Get a list of <playerName, score>
                    if (snapshot != null && snapshot.exists()) {
                        Log.d("ResultScreen", "snapshot: $snapshot")
                        val playersMap = snapshot.get("players") as? Map<*, *>
                        val playerScores = playersMap?.mapNotNull { entry ->
                            val playerData = entry.value as? Map<*, *>
                            val name = playerData?.get("name") as? String
                            val score = (playerData?.get("score") as? Long)?.toInt() // Firestore stocke les nombres en Long
                            if (name != null && score != null) name to score else null
                        } ?: emptyList()
                        Log.d("ResultScreen", "playerScores: $playerScores")
                        playerList.value = playerScores.sortedByDescending { it.second }
                    }
                }
            }

            PlayerListScore(
                playerList = playerList.value,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            if (questionViewModel.gameMode.value == GameMode.SOLO) {
                ResultsButton(
                    modifier = Modifier.weight(1f),
                    text = R.string.replay,
                    onClick = onReplayClick
                )
            }
            ResultsButton(
                modifier = Modifier.weight(1f),
                text = R.string.home,
                onClick = onHomeClick
            )
        }
    }
}

@Composable
fun PlayerListScore(
    playerList: List<Pair<String, Int>>,
    modifier: Modifier = Modifier,
) {
    SettingCard(
        clickable = false,
        onClick = {},
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp),
        ) {
            itemsIndexed(playerList) { index, player ->
                StatisticsItem(
                    text = player.first,
                    value = player.second.toString(),
                )
                if (index < playerList.size - 1) { // Évite d'ajouter un divider après la dernière ligne
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.LightGray
                    )
                }
            }
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
    correctAnswers: Int,
    wrongAnswers: Int,
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
                text = stringResource(R.string.correct_answers),
                value = correctAnswers.toString(),
            )
            StatisticsItem(
                text = stringResource(R.string.wrong_answers),
                value = wrongAnswers.toString(),
            )
        }
    }
}

@Composable
fun StatisticsItem(
    text: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    SensorQuizTheme {
        ResultsScreen(modifier = Modifier.fillMaxSize(), onReplayClick = {}, onHomeClick = {}, navController = rememberNavController())
    }
}