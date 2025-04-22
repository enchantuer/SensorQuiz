package fr.enchantuer.sensorquiz.data.QuestionsCategories

import fr.enchantuer.sensorquiz.data.Answers
import fr.enchantuer.sensorquiz.data.Question
import fr.enchantuer.sensorquiz.data.QuestionType

val logicMemoryQuestions = listOf(
    Question(
        id = 1,
        question = "Si 2 + 3 = 10 et 7 + 2 = 63, alors 6 + 5 = ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("66", "55"),
        correctAnswer = "66"
    ),
    Question(
        id = 2,
        question = "Une horloge indique 3:15. Quelle est l’angle entre les aiguilles ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("7,5°", "0°"),
        correctAnswer = "7,5°"
    ),
    Question(
        id = 3,
        question = "Complète la séquence : 2, 4, 8, 16, ...",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("32", "30"),
        correctAnswer = "32"
    ),
    Question(
        id = 4,
        question = "Laquelle n’est pas un carré parfait ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("50", "49"),
        correctAnswer = "50"
    ),
    Question(
        id = 5,
        question = "Un train électrique va vers l'est. Dans quelle direction va la fumée ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Aucune", "Vers l'est"),
        correctAnswer = "Aucune"
    ),
    Question(
        id = 6,
        question = "Le mot 'RADAR' est-il un palindrome ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Oui"
    ),
    Question(
        id = 7,
        question = "Quel nombre complète la suite : 1, 3, 6, 10, 15, ... ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("21", "20"),
        correctAnswer = "21"
    ),
    Question(
        id = 8,
        question = "Une pièce a deux faces. Si je la retourne 100 fois, combien de faces reste-t-il ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("2", "1"),
        correctAnswer = "2"
    ),
    Question(
        id = 9,
        question = "Que manque-t-il ? 🟨🟦🟩🟥... ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("🟪", "⬛️"),
        correctAnswer = "🟪"
    ),
    Question(
        id = 10,
        question = "Si demain est jeudi, quel jour était-il avant-hier ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Mardi", "Lundi"),
        correctAnswer = "Mardi"
    ),
    Question(
        id = 11,
        question = "Combien y a-t-il de lettres dans le mot 'Mémoire' ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("7", "8"),
        correctAnswer = "7"
    ),
    Question(
        id = 12,
        question = "Un homme a quatre filles, chaque fille a un frère. Combien d’enfants a-t-il ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("5", "8"),
        correctAnswer = "5"
    ),
    Question(
        id = 13,
        question = "Une minute a combien de secondes ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("60", "100"),
        correctAnswer = "60"
    ),
    Question(
        id = 14,
        question = "Un triangle a combien de côtés ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("3", "4"),
        correctAnswer = "3"
    ),
    Question(
        id = 15,
        question = "La mémoire vive est-elle permanente ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Non", "Oui"),
        correctAnswer = "Non"
    )
)