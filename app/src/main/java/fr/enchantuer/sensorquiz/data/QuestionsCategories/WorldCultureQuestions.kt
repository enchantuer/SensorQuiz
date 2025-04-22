package fr.enchantuer.sensorquiz.data.QuestionsCategories

import fr.enchantuer.sensorquiz.data.Answers
import fr.enchantuer.sensorquiz.data.Question
import fr.enchantuer.sensorquiz.data.QuestionType

val worldCultureQuestions = listOf(
    Question(
        id = 1,
        question = "Quel est le plus grand désert du monde ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Sahara", "Gobi"),
        correctAnswer = "Sahara"
    ),
    Question(
        id = 2,
        question = "L’anglais est-il la langue la plus parlée au monde ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Non"
    ),
    Question(
        id = 3,
        question = "Quel pays a inventé les Jeux Olympiques ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Grèce", "Italie"),
        correctAnswer = "Grèce"
    ),
    Question(
        id = 4,
        question = "La muraille de Chine est visible depuis la Lune ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Non"
    ),
    Question(
        id = 5,
        question = "Quelle mer borde l'Égypte à l'est ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Mer Rouge", "Mer Méditerranée"),
        correctAnswer = "Mer Rouge"
    ),
    Question(
        id = 6,
        question = "Les pyramides d'Égypte sont situées à ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Gizeh", "Alexandrie"),
        correctAnswer = "Gizeh"
    ),
    Question(
        id = 7,
        question = "Quel est le plus long fleuve du monde ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Nil", "Amazone"),
        correctAnswer = "Amazone"
    ),
    Question(
        id = 8,
        question = "L’Alhambra est situé en Espagne ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Oui"
    ),
    Question(
        id = 9,
        question = "Quel est le monument le plus visité au monde ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Tour Eiffel", "Statue de la Liberté"),
        correctAnswer = "Tour Eiffel"
    ),
    Question(
        id = 10,
        question = "Le Taj Mahal est situé en Inde ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Oui"
    ),
    Question(
        id = 11,
        question = "Le mont Everest se trouve dans quel pays ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Népal", "Chine"),
        correctAnswer = "Népal"
    ),
    Question(
        id = 12,
        question = "La danse samba vient du Brésil ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Oui"
    ),
    Question(
        id = 13,
        question = "Quel pays a le plus de langues officielles ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Afrique du Sud", "Inde"),
        correctAnswer = "Afrique du Sud"
    ),
    Question(
        id = 14,
        question = "La fête du nouvel an chinois change-t-elle de date chaque année ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Oui"
    ),
    Question(
        id = 15,
        question = "Quel est le symbole principal du Japon ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Mont Fuji", "Cerisiers"),
        correctAnswer = "Mont Fuji"
    )
)