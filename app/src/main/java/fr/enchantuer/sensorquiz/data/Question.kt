package fr.enchantuer.sensorquiz.data

const val MAX_NUMBER_OF_QUESTIONS = 3

data class Question(
    val id: Int,
    val question: String,
    val type: QuestionType,
    val answers: Answers?,
    val correctAnswer: String,
)

data class Answers(
    val answer1: String,
    val answer2: String,
)

enum class AnswerState {
    CORRECT,
    WRONG,
    NONE
}

enum class QuestionType {
    TWO_CHOICES,
    THREE_CHOICES,
    SENSOR
}

val questionList = listOf(
    Question(1, "Question 1", QuestionType.TWO_CHOICES, Answers("Oui", "Non"), "Non"),
    Question(2, "Question 2", QuestionType.THREE_CHOICES, Answers("A", "B"), "B"),
    Question(3, "Capital de la france", QuestionType.THREE_CHOICES, Answers("Paris", "Nantes"), "Paris"),
    Question(4, "Le chat est un felin", QuestionType.TWO_CHOICES, Answers("Oui", "Non"), "Oui"),
)
