package fr.enchantuer.sensorquiz.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import fr.enchantuer.sensorquiz.SensorTiltDetection
import fr.enchantuer.sensorquiz.TiltValue
import fr.enchantuer.sensorquiz.data.AnswerState
import fr.enchantuer.sensorquiz.data.MAX_NUMBER_OF_QUESTIONS
import fr.enchantuer.sensorquiz.data.Question
import fr.enchantuer.sensorquiz.data.QuestionType
import fr.enchantuer.sensorquiz.data.questionList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {
    private val _currentQuestion = MutableStateFlow<Question?>(null)

    private val _uiState = MutableStateFlow(QuestionUiState())
    val uiState: StateFlow<QuestionUiState> = _uiState.asStateFlow()

    private var usedQuestions: MutableSet<Int> = mutableSetOf()

    private var _gameMode = mutableStateOf(GameMode.SOLO)
    val gameMode: State<GameMode> = _gameMode
    private var _lobbyCode = mutableStateOf("")
    val lobbyCode: State<String> = _lobbyCode

    // Liste des questions envoyées par l'hôte (mode multijoueur)
    private var hostQuestions: List<Question> = emptyList()
    private var currentQuestionIndex = 0


    var userAnswer by mutableStateOf("")
        private set

    private var sensorController: SensorTiltDetection? = null

    fun setLobbyCode(lobbyCode: String) {
        _lobbyCode.value = lobbyCode
    }

    fun setSensorController(controller: SensorTiltDetection) {
        this.sensorController = controller
    }

    fun startSensor(questionType: QuestionType) {
        sensorController?.questionType = questionType
        sensorController?.startListening()
    }

    fun stopSensor() {
        sensorController?.stopListening()
    }

    fun setQuestions(questions: List<Question>) {
        hostQuestions = questions
        _gameMode.value = GameMode.MULTI
        nextQuestion()
    }

    private fun pickRandomQuestion() {
        // Continue picking up a new random question until you get one that hasn't been used before
        val newQuestion = questionList.random()
        if (usedQuestions.contains(newQuestion.id)) {
            return pickRandomQuestion()
        } else {
            usedQuestions.add(newQuestion.id)
            _currentQuestion.value = newQuestion
        }
    }

    fun updateUserAnswer(answer: String) {
        if (_uiState.value.answerState == AnswerState.NONE) userAnswer = answer
    }
    fun chooseAnswerUsingSensor(tilt: TiltValue) {
        Log.d("SensorTest", "chooseAnswerUsingSensor: $tilt | ${_currentQuestion.value?.question} | ${_uiState.value.currentQuestion} | ${_uiState.value.currentQuestionCount}")
        when (tilt) {
            TiltValue.LEFT -> updateUserAnswer(_currentQuestion.value?.answers?.answer1 ?: "")
            TiltValue.RIGHT -> updateUserAnswer(_currentQuestion.value?.answers?.answer2 ?: "")
            TiltValue.SHAKE -> {
                if (_currentQuestion.value?.type == QuestionType.THREE_CHOICES) {
                    updateUserAnswer("Autre")
                }
            }
            else -> {}
        }
        checkAnswer()
    }

    fun checkAnswer() {
        // If the user has already answered, do nothing
        if (_uiState.value.answerState != AnswerState.NONE) return

        if (userAnswer.equals(_currentQuestion.value?.correctAnswer, ignoreCase = true)) {
            _uiState.update { currentState ->
                currentState.copy(
                    answerState = AnswerState.CORRECT,
                    score = currentState.score.inc(),
                    correctAnswers = currentState.correctAnswers.inc()
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    answerState = AnswerState.WRONG,
                )
            }
        }

        if (_gameMode.value == GameMode.MULTI) {
            // Update Score on firebase
            val firestore = Firebase.firestore
            val lobbyRef = firestore.collection("lobbies").document(_lobbyCode.value)
            lobbyRef.update("players.${Firebase.auth.currentUser?.uid}.score", _uiState.value.score)

        }

        viewModelScope.launch {
            delay(200) // Attendre 200ms avant de passer à la prochaine question
            nextQuestion()
        }
    }

    private fun nextQuestion() {
        if (_gameMode.value == GameMode.MULTI) {
            if (currentQuestionIndex < hostQuestions.size) {
                _currentQuestion.value = hostQuestions[currentQuestionIndex]
                currentQuestionIndex++
                _uiState.update { currentState ->
                    currentState.copy(
                        answerState = AnswerState.NONE,
                        currentQuestionCount = currentQuestionIndex,
                        currentQuestion = _currentQuestion.value?.question ?: "",
                        questionType = _currentQuestion.value?.type ?: QuestionType.TWO_CHOICES,
                        answers = _currentQuestion.value?.answers
                    )
                }
            } else {
                _uiState.update { currentState ->
                    currentState.copy(
                        answerState = AnswerState.NONE,
                        isGameOver = true
                    )
                }
            }
        } else {
            if (usedQuestions.size == MAX_NUMBER_OF_QUESTIONS || usedQuestions.size == questionList.size) {
                _uiState.update { currentState ->
                    currentState.copy(
                        answerState = AnswerState.NONE,
                        isGameOver = true
                    )
                }
            } else {
                pickRandomQuestion()
                _uiState.update { currentState ->
                    currentState.copy(
                        answerState = AnswerState.NONE,
                        currentQuestionCount = currentState.currentQuestionCount.inc(),

                        currentQuestion = _currentQuestion.value?.question ?: "",
                        questionType = _currentQuestion.value?.type ?: QuestionType.TWO_CHOICES,
                        answers = _currentQuestion.value?.answers
                    )
                }
            }
            updateUserAnswer("")
        }
    }

    fun restart() {
        usedQuestions.clear()
        pickRandomQuestion()
        _gameMode.value = GameMode.SOLO
        _lobbyCode.value = ""
        hostQuestions = emptyList()
        currentQuestionIndex = 0
        _uiState.value = QuestionUiState(
            currentQuestion = _currentQuestion.value?.question ?: "",
            questionType = _currentQuestion.value?.type ?: QuestionType.TWO_CHOICES,
            answers = _currentQuestion.value?.answers,
        )
    }

    init {
        restart()
    }
}

enum class GameMode {
    SOLO,
    MULTI
}