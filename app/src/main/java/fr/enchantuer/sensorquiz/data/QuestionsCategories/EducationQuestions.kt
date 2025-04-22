package fr.enchantuer.sensorquiz.data.QuestionsCategories

import fr.enchantuer.sensorquiz.data.Answers
import fr.enchantuer.sensorquiz.data.Question
import fr.enchantuer.sensorquiz.data.QuestionType

val educationQuestions = listOf(
    Question(
        id = 1,
        question = "Quelle est la capitale de l'Italie ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Rome", "Milan"),
        correctAnswer = "Rome"
    ),
    Question(
        id = 2,
        question = "La Terre tourne autour du Soleil.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Vrai"
    ),
    Question(
        id = 3,
        question = "Quel est le résultat de 9 x 6 ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("54", "45"),
        correctAnswer = "54"
    ),
    Question(
        id = 4,
        question = "L’eau gèle à 0 degré Celsius.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Vrai"
    ),
    Question(
        id = 5,
        question = "Quelle est la langue la plus parlée au monde ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Chinois", "Anglais"),
        correctAnswer = "Chinois"
    ),
    Question(
        id = 6,
        question = "L’homme a marché sur la Lune en 1969.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Vrai"
    ),
    Question(
        id = 7,
        question = "Quel est l’organe principal du système circulatoire ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Cœur", "Poumon"),
        correctAnswer = "Cœur"
    ),
    Question(
        id = 8,
        question = "Les plantes ont besoin de lumière pour pousser.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Vrai"
    ),
    Question(
        id = 9,
        question = "Quel est le plus grand océan de la planète ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Océan Pacifique", "Océan Atlantique"),
        correctAnswer = "Océan Pacifique"
    ),
    Question(
        id = 10,
        question = "Un kilogramme est plus lourd qu’un gramme.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Vrai"
    ),
    Question(
        id = 11,
        question = "Quel scientifique a développé la théorie de la relativité ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Einstein", "Newton"),
        correctAnswer = "Einstein"
    ),
    Question(
        id = 12,
        question = "Les continents bougent lentement chaque année.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Vrai"
    ),
    Question(
        id = 13,
        question = "Combien y a-t-il de planètes dans le système solaire ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("8", "9"),
        correctAnswer = "8"
    ),
    Question(
        id = 14,
        question = "La photosynthèse produit de l’oxygène.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Vrai"
    ),
    Question(
        id = 15,
        question = "Quel est le symbole chimique de l’eau ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("H2O", "O2"),
        correctAnswer = "H2O"
    )
)