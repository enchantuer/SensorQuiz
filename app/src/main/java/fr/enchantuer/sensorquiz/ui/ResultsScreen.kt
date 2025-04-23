package fr.enchantuer.sensorquiz.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import fr.enchantuer.sensorquiz.ui.theme.LavenderPurple
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme
import fr.enchantuer.sensorquiz.ui.theme.violetGradientBackground
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ResultsScreen(
    onReplayClick: () -> Unit,
    onHomeClick: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    questionViewModel: QuestionViewModel = viewModel()
) {
    var listenerDB: ListenerRegistration? = null
    val playerList = remember { mutableStateOf<List<Pair<String, Int>>>(emptyList()) }

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
            .fillMaxSize()
            .violetGradientBackground()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Score(
            score = uiState.score,
            modifier = Modifier.fillMaxWidth()
        )
        Statistics(
            correctAnswers = uiState.correctAnswers,
            wrongAnswers = uiState.currentQuestionCount - uiState.correctAnswers
        )
        if (questionViewModel.gameMode.value == GameMode.MULTI) {
            val firestore = Firebase.firestore
            val lobbyCode = questionViewModel.lobbyCode.value
            val lobbyRef = firestore.collection("lobbies").document(lobbyCode)

            LaunchedEffect(true) {
                listenerDB = lobbyRef.addSnapshotListener { snapshot, _ ->
                    if (snapshot != null && snapshot.exists()) {
                        val playersMap = snapshot.get("players") as? Map<*, *>
                        val playerScores = playersMap?.mapNotNull { entry ->
                            val playerData = entry.value as? Map<*, *>
                            val name = playerData?.get("name") as? String
                            val score = (playerData?.get("score") as? Long)?.toInt()
                            if (name != null && score != null) name to score else null
                        } ?: emptyList()
                        playerList.value = playerScores.sortedByDescending { it.second }
                    }
                }
            }

            PlayerListScore(
                playerList = playerList.value,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (questionViewModel.gameMode.value == GameMode.SOLO) {
                    Button(
                        onClick = onReplayClick,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LavenderPurple,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(6.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.replay).uppercase(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                Button(
                    onClick = onHomeClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LavenderPurple,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(6.dp)
                ) {
                    Text(
                        text = stringResource(R.string.home).uppercase(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
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
            modifier = Modifier.padding(16.dp),
        ) {
            itemsIndexed(playerList) { index, player ->
                StatisticsItem(
                    text = player.first,
                    value = player.second.toString(),
                )
                if (index < playerList.size - 1) {
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
fun Score(score: Int, modifier: Modifier = Modifier) {
    Card {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.score).uppercase(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp)
            )
            Text(
                text = score.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp)
            )
        }
    }
}

@Composable
fun Statistics(correctAnswers: Int, wrongAnswers: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.statistics),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp)
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
fun StatisticsItem(text: String, value: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(text = text, textAlign = TextAlign.Start, modifier = Modifier.weight(1f))
        Text(text = value, textAlign = TextAlign.End)
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    SensorQuizTheme {
        ResultsScreen(
            modifier = Modifier.fillMaxSize(),
            onReplayClick = {},
            onHomeClick = {},
            navController = rememberNavController()
        )
    }
}