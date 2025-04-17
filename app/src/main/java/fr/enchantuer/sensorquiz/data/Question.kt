package fr.enchantuer.sensorquiz.data

const val MAX_NUMBER_OF_QUESTIONS = 15

data class Question(
    val id: Int = 0,
    val question: String = "",
    val type: QuestionType = QuestionType.TWO_CHOICES,
    val answers: Answers = Answers(),
    val correctAnswer: String = "",
)

data class Answers(
    val answer1: String = "",
    val answer2: String = "",
)

enum class AnswerState {
    CORRECT,
    WRONG,
    NONE
}

enum class QuestionType {
    TWO_CHOICES,
    THREE_CHOICES
}

val questionList = listOf(
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
    ),
    Question(
        id = 16,
        question = "Quel est le plus grand désert du monde ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Sahara", "Gobi"),
        correctAnswer = "Sahara"
    ),
    Question(
        id = 17,
        question = "L’anglais est-il la langue la plus parlée au monde ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Non"
    ),
    Question(
        id = 18,
        question = "Quel pays a inventé les Jeux Olympiques ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Grèce", "Italie"),
        correctAnswer = "Grèce"
    ),
    Question(
        id = 19,
        question = "La muraille de Chine est visible depuis la Lune ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Non"
    ),
    Question(
        id = 20,
        question = "Quelle mer borde l'Égypte à l'est ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Mer Rouge", "Mer Méditerranée"),
        correctAnswer = "Mer Rouge"
    ),
    Question(
        id = 21,
        question = "Les pyramides d'Égypte sont situées à ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Gizeh", "Alexandrie"),
        correctAnswer = "Gizeh"
    ),
    Question(
        id = 22,
        question = "Quel est le plus long fleuve du monde ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Nil", "Amazone"),
        correctAnswer = "Amazone"
    ),
    Question(
        id = 23,
        question = "L’Alhambra est situé en Espagne ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Oui"
    ),
    Question(
        id = 24,
        question = "Quel est le monument le plus visité au monde ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Tour Eiffel", "Statue de la Liberté"),
        correctAnswer = "Tour Eiffel"
    ),
    Question(
        id = 25,
        question = "Le Taj Mahal est situé en Inde ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Oui"
    ),
    Question(
        id = 26,
        question = "Le mont Everest se trouve dans quel pays ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Népal", "Chine"),
        correctAnswer = "Népal"
    ),
    Question(
        id = 27,
        question = "La danse samba vient du Brésil ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Oui"
    ),
    Question(
        id = 28,
        question = "Quel pays a le plus de langues officielles ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Afrique du Sud", "Inde"),
        correctAnswer = "Afrique du Sud"
    ),
    Question(
        id = 29,
        question = "La fête du nouvel an chinois change-t-elle de date chaque année ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Oui"
    ),
    Question(
        id = 30,
        question = "Quel est le symbole principal du Japon ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Mont Fuji", "Cerisiers"),
        correctAnswer = "Mont Fuji"
    )
)
