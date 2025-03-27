package fr.enchantuer.sensorquiz.ui

import fr.enchantuer.sensorquiz.data.AnswerState
import fr.enchantuer.sensorquiz.data.Answers
import fr.enchantuer.sensorquiz.data.QuestionType

data class QuestionUiState (
    val currentQuestion: String = "",
    val questionType: QuestionType = QuestionType.TWO_CHOICES,
    val answers: Answers? = null,

    val answerState: AnswerState = AnswerState.NONE,
    val score: Int = 0,
    val currentQuestionCount: Int = 1,
    val correctAnswers: Int = 0,

    val isGameOver: Boolean = false,
)