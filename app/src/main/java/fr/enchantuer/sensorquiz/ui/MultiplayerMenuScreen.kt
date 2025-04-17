package fr.enchantuer.sensorquiz.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.data.Question
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme

@Composable
fun MultiplayerMenuScreen(
    onHostClick: (String) -> Unit,
    onJoinClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FirebaseAuth.getInstance().signInAnonymously()
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                val user = FirebaseAuth.getInstance().currentUser
                val userId = user?.uid
                Log.d("FirebaseAuth", "User ID: $userId")
            } else {
                Log.e("FirebaseAuth", "Sign in failed", task.exception)
            }
        }

   Column(
       modifier = modifier
           .padding(16.dp)
           .width(IntrinsicSize.Min),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
   ) {
       Button(
           onClick = { createLobby(
               userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
               name = "test",
               onCreated = onHostClick
           ) },
           modifier = Modifier.fillMaxWidth()
       ) {
           Text(
               text = stringResource(R.string.host)
           )
       }

       var codeInput by remember { mutableStateOf("") }
       OutlinedTextField(
           modifier = Modifier
               .fillMaxWidth(),
           singleLine = true,
           value = codeInput,
           onValueChange = { codeInput = it },
           label = { Text(text = stringResource(R.string.input_code) + " : ") },
           trailingIcon = {
               IconButton(onClick = { joinLobby(
                   userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                   name = "test2",
                   code = codeInput,
                   onJoined = onJoinClick
               ) }) {
                   Icon(
                       imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                       contentDescription = stringResource(R.string.join)
                   )
               }
           },
       )
   }
}

fun createLobby(userId: String, name: String, onCreated: (String) -> Unit) {
    Log.d("createLobby", "userId: $userId")
    val lobbyCode = generateCode()
    Log.d("createLobby", "lobbyCode: $lobbyCode")
    val lobbyData = mapOf(
        "hostId" to userId,
        "status" to "waiting",
        "players" to mapOf(userId to mapOf("name" to name, "score" to 0)),
        "questions" to listOf<Question>(),
    )
    Log.d("createLobby", "lobbyData: $lobbyData")
    Firebase.firestore.collection("lobbies").document(lobbyCode).set(lobbyData)
        .addOnSuccessListener {
            Log.d("createLobby", "Lobby created successfully")
            onCreated(lobbyCode)
        }
    Log.d("createLobby", "Lobby creation failed")
}

fun joinLobby(userId: String, name: String, code: String, onJoined: (String) -> Unit) {
    val lobbyRef = Firebase.firestore.collection("lobbies").document(code)
    lobbyRef.get().addOnSuccessListener { document ->
        if (document.exists() && document.getString("status") == "waiting") {
            lobbyRef.update("players.$userId", mapOf("name" to name, "score" to 0))
            onJoined(code)
        }
    }
}

fun generateCode(): String {
    val allowedChars = ('A'..'Z') + ('0'..'9')
    return (1..6).map { allowedChars.random() }.joinToString("")
}

@Preview(showBackground = true)
@Composable
fun MultiplayerMenuScreenPreview() {
    SensorQuizTheme {
        MultiplayerMenuScreen(onHostClick = {}, onJoinClick = {})
    }
}