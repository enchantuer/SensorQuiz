package fr.enchantuer.sensorquiz.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ListenerRegistration
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.data.MAX_NUMBER_OF_QUESTIONS
import fr.enchantuer.sensorquiz.data.Question
import fr.enchantuer.sensorquiz.data.questionList
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme

data class LobbyData(
    val questions: List<Question> = emptyList()
)

@Composable
fun LobbyScreen(
    onThemeClick: () -> Unit,
    lobbyCode: String,
    isHost: Boolean,
    onStartGame: () -> Unit,
    questionViewModel: QuestionViewModel,
    modifier: Modifier = Modifier
) {
    Log.d("LobbyScreen", "First lobbyCode: $lobbyCode")
    val playerId = Firebase.auth.currentUser?.uid ?: ""

    val firestore = Firebase.firestore
    val lobbyRef = firestore.collection("lobbies").document(lobbyCode)

    var listener: ListenerRegistration? = null

    val players = remember { mutableStateListOf<String>() }
    var status by remember { mutableStateOf("waiting") }

    // Listener Firestore
    Log.d("LobbyScreen", "lobbyCode: $lobbyCode")
    LaunchedEffect(true) {
        Log.d("LobbyScreen", "Launch effect lobbyCode: $lobbyCode")
        listener = lobbyRef.addSnapshotListener { snapshot, _ ->
            Log.d("LobbyScreen", "listener lobbyCode: $lobbyCode")
            if (snapshot != null && snapshot.exists()) {
                status = snapshot.getString("status") ?: "waiting"
                val playerMap = snapshot.get("players") as? Map<*, *>
                players.clear()
                players.addAll(playerMap?.values?.mapNotNull { (it as? Map<*, *>)?.get("name") as? String } ?: emptyList())

                if (status == "started") {
                    Log.d("LobbyScreen", "Game started")
                    questionViewModel.restart()
                    Log.d("LobbyScreen", "questionViewModel.restart()")
                    val questions = snapshot.toObject(LobbyData::class.java)?.questions ?: emptyList()
                    Log.d("LobbyScreen", "questions: $questions")
                    questionViewModel.setQuestions(questions)
                    Log.d("LobbyScreen", "questionViewModel.setQuestions(snapshot.get('questions') as List<Question>)")
                    questionViewModel.setLobbyCode(lobbyCode)
                    Log.d("LobbyScreen", "questionViewModel.setLobbyCode(lobbyCode)")
                    onStartGame()
                    Log.d("LobbyScreen", "onStartGame()")
                }
            }
        }
    }

    fun removePlayerFromLobby(playerId: String) {
        lobbyRef.update("players.$playerId", FieldValue.delete())
            .addOnSuccessListener {
                Log.d("LobbyScreen", "Player $playerId removed successfully")
            }
            .addOnFailureListener { e ->
                Log.e("LobbyScreen", "Error removing player with ID $playerId", e)
            }
    }

    fun startGame() {
        fun generateQuestions(): List<Question> {
            return questionList.shuffled().take(MAX_NUMBER_OF_QUESTIONS)
        }

        if (isHost) {
            val updatedLobbyData = mapOf(
                "status" to "started", // Statut mis à jour
                "questions" to generateQuestions(), // Liste des questions envoyée
                "currentQuestionIndex" to 0 // Réinitialisation de l'index des questions
            )

            // Ici tu enverras ces données à Firebase en utilisant le lobbyCode
            lobbyRef.update(updatedLobbyData)
                .addOnSuccessListener {
                    Log.d("Lobby", "La partie a bien démarré")
                }
                .addOnFailureListener { exception ->
                    Log.e("Lobby", "Erreur lors de la mise à jour du lobby: ${exception.message}")
                }
        }
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CodeCard(code = lobbyCode)

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
//            Settings(onThemeClick = onThemeClick)
            PlayerList(playerList = players)
            if (isHost) {
                Button(
                    onClick = { startGame() },
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(4.dp),
                ) {
                    Text(
                        text = stringResource(R.string.start),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }

    DisposableEffect(key1 = lobbyCode) {
        onDispose {
            listener?.remove()
            // Appelle la fonction pour supprimer le joueur lorsqu'il quitte ou l'app
            if (status == "waiting") {
                removePlayerFromLobby(playerId) // Remplace par la variable du joueur qui quitte
            }
        }
    }
}

@Composable
fun PlayerList(
    playerList: List<String>,
    modifier: Modifier = Modifier,
) {
    SettingCard(
        clickable = false,
        onClick = {},
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 4.dp),
        ) {
            itemsIndexed(playerList) { index, player ->
                Row {
                    Text(
                        text = player,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
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
fun Settings(
    onThemeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onThemeClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 16.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.choose_theme),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Catégorie 1",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun CodeCard(
    code: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
    ) {
       Text(
           text = code.uppercase(),
           style = MaterialTheme.typography.headlineMedium,
           modifier = Modifier
               .padding(16.dp)
       )
    }
}

@Preview(showBackground = true)
@Composable
fun LobbyScreenPreview() {
    SensorQuizTheme {
//        LobbyScreen(onStartClick = {}, onThemeClick = {})
    }
}