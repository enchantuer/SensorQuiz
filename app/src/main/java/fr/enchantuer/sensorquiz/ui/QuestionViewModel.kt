package fr.enchantuer.sensorquiz.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.enchantuer.sensorquiz.data.*
import fr.enchantuer.sensorquiz.data.QuestionsCategories.educationQuestions
import fr.enchantuer.sensorquiz.data.QuestionsCategories.worldCultureQuestions
import fr.enchantuer.sensorquiz.data.QuestionsCategories.entertainmentQuestions
import fr.enchantuer.sensorquiz.data.QuestionsCategories.logicMemoryQuestions
import fr.enchantuer.sensorquiz.data.QuestionsCategories.techQuestions
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

    var userAnswer by mutableStateOf("")
        private set

    var selectedCategory: String? = null

    private var dynamicQuestionList: List<Question> = questionList

    private fun pickRandomQuestion() {
        val available = dynamicQuestionList.filterNot { usedQuestions.contains(it.id) }
        if (available.isEmpty()) return

        val newQuestion = available.random()
        usedQuestions.add(newQuestion.id)
        _currentQuestion.value = newQuestion
    }

    fun updateUserAnswer(answer: String) {
        if (_uiState.value.answerState == AnswerState.NONE) userAnswer = answer
    }

    fun checkAnswer() {
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
                currentState.copy(answerState = AnswerState.WRONG)
            }
        }

        viewModelScope.launch {
            delay(200)
            nextQuestion()
        }
    }

    private fun nextQuestion() {
        if (usedQuestions.size == dynamicQuestionList.size) {
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

    fun restart() {
        usedQuestions.clear()
        loadQuestionsForCategory()
        pickRandomQuestion()
        _uiState.value = QuestionUiState(
            currentQuestion = _currentQuestion.value?.question ?: "",
            questionType = _currentQuestion.value?.type ?: QuestionType.TWO_CHOICES,
            answers = _currentQuestion.value?.answers,
        )
    }

    fun loadQuestionsForCategory() {
        dynamicQuestionList = when (selectedCategory) {
            "Education" -> educationQuestions
            "WorldCulture" -> worldCultureQuestions
            "Entertainment" -> entertainmentQuestions
            "LogicMemory" -> logicMemoryQuestions
            "Tech" -> techQuestions
            else -> questionList
        }
    }

    init {
        restart()
    }
}
