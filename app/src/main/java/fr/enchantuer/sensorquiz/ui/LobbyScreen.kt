package fr.enchantuer.sensorquiz.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ListenerRegistration
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.ui.theme.LavenderPurple
import fr.enchantuer.sensorquiz.data.Question
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme
import fr.enchantuer.sensorquiz.ui.theme.violetGradientBackground
import kotlinx.coroutines.launch

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
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry
) {
    Log.d("LobbyScreen", "First lobbyCode: $lobbyCode")
    val playerId = Firebase.auth.currentUser?.uid ?: ""

    val firestore = Firebase.firestore
    val lobbyRef = firestore.collection("lobbies").document(lobbyCode)

    var listener: ListenerRegistration? = null

    val players = remember { mutableStateListOf<String>() }
    var status by remember { mutableStateOf("waiting") }
    var category by remember { mutableStateOf("") }

    val savedStateHandle = navBackStackEntry.savedStateHandle
    var isInLobby by remember {
        mutableStateOf(savedStateHandle.get<Boolean>("isInLobby") ?: false)
    }

    LaunchedEffect(key1 = true) {
        savedStateHandle.remove<Boolean>("isInLobby")
    }

    // Listener Firestore
    Log.d("LobbyScreen", "lobbyCode: $lobbyCode")
    LaunchedEffect(true) {
        Log.d("LobbyScreen", "Launch effect lobbyCode: $lobbyCode")
        questionViewModel.setLobbyCode(lobbyCode)
        listener = lobbyRef.addSnapshotListener { snapshot, _ ->
            Log.d("LobbyScreen", "listener lobbyCode: $lobbyCode")
            if (snapshot != null && snapshot.exists()) {
                status = snapshot.getString("status") ?: "waiting"
                val playerMap = snapshot.get("players") as? Map<*, *>
                players.clear()
                players.addAll(playerMap?.values?.mapNotNull { (it as? Map<*, *>)?.get("name") as? String } ?: emptyList())
                category = snapshot.getString("category") ?: ""
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
        if (isHost) {
            val updatedLobbyData = mapOf(
                "status" to "started", // Statut mis à jour
                "questions" to questionViewModel.generateQuestions(), // Liste des questions envoyée
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

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .violetGradientBackground()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Bloc violet transparent (code + copier + partager)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xCCB39DDB)
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x33FFFFFF), RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0x66FFFFFF), RoundedCornerShape(16.dp))
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = lobbyCode,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(lobbyCode))
                            scope.launch {
                                snackbarHostState.showSnackbar("Code copié dans le presse-papiers")
                            }
                        },
                        modifier = Modifier.weight(1f),
                        elevation = ButtonDefaults.buttonElevation(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = LavenderPurple
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Copier", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                    }

                    Button(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, lobbyCode)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        },
                        modifier = Modifier.weight(1f),
                        elevation = ButtonDefaults.buttonElevation(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = LavenderPurple
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Partager")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Partager", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }

        // Bloc blanc avec boutons
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
                Settings(
                    onThemeClick = {
                        if (isHost) {
                            isInLobby = false
                            onThemeClick()
                        }
                    },
                    category = category
                )
                PlayerList(playerList = players)
                if (isHost) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { startGame() },
                            modifier = Modifier.weight(0.5f),
                            elevation = ButtonDefaults.buttonElevation(6.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LavenderPurple,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Suivant", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }
        }
        // Snackbar pour les messages (ex: code copié)
        SnackbarHost(hostState = snackbarHostState)
    }

    DisposableEffect(key1 = lobbyCode, key2 = isInLobby) {
        onDispose {
            listener?.remove()
            // Appelle la fonction pour supprimer le joueur lorsqu'il quitte ou l'app
            if (status == "waiting" && isInLobby) {
                questionViewModel.setLobbyCode("")
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
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
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
    category: String,
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
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.choose_theme),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LobbyScreenPreview() {
    SensorQuizTheme {
//        LobbyScreen(onStartClick = {}, onThemeClick = {})
    }
}