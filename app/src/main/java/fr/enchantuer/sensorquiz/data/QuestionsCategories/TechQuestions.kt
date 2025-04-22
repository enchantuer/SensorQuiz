package fr.enchantuer.sensorquiz.data.QuestionsCategories

import fr.enchantuer.sensorquiz.data.Answers
import fr.enchantuer.sensorquiz.data.Question
import fr.enchantuer.sensorquiz.data.QuestionType

val techQuestions = listOf(
    Question(
        id = 1,
        question = "HTML est un langage de programmation ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Non"
    ),
    Question(
        id = 2,
        question = "Que signifie l’abréviation 'USB' ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Universal Serial Bus", "Ultimate System Backup"),
        correctAnswer = "Universal Serial Bus"
    ),
    Question(
        id = 3,
        question = "Lequel est un système d’exploitation ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Windows", "Google"),
        correctAnswer = "Windows"
    ),
    Question(
        id = 4,
        question = "Le cloud computing permet de stocker des données en ligne ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Oui"
    ),
    Question(
        id = 5,
        question = "Quel est le navigateur développé par Google ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Chrome", "Firefox"),
        correctAnswer = "Chrome"
    ),
    Question(
        id = 6,
        question = "Lequel est un langage utilisé pour créer des applications Android ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Kotlin", "Python"),
        correctAnswer = "Kotlin"
    ),
    Question(
        id = 7,
        question = "RAM signifie 'Mémoire vive' ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Oui"
    ),
    Question(
        id = 8,
        question = "Lequel est une plateforme de gestion de code source ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("GitHub", "Outlook"),
        correctAnswer = "GitHub"
    ),
    Question(
        id = 9,
        question = "La 5G est plus rapide que la 4G ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Oui", "Non"),
        correctAnswer = "Oui"
    ),
    Question(
        id = 10,
        question = "Que signifie 'AI' ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Intelligence Artificielle", "Interface Automatique"),
        correctAnswer = "Intelligence Artificielle"
    ),
    Question(
        id = 11,
        question = "Les cookies sur un site web sont dangereux ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Non", "Oui"),
        correctAnswer = "Non"
    ),
    Question(
        id = 12,
        question = "Un fichier .jpg est un type de fichier... ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Image", "Texte"),
        correctAnswer = "Image"
    ),
    Question(
        id = 13,
        question = "TikTok est une application de messagerie ?",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Non", "Oui"),
        correctAnswer = "Non"
    ),
    Question(
        id = 14,
        question = "Que signifie le 'C' dans 'CPU' ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Central", "Control"),
        correctAnswer = "Central"
    ),
    Question(
        id = 15,
        question = "Un antivirus sert à... ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Protéger contre les logiciels malveillants", "Créer des mots de passe"),
        correctAnswer = "Protéger contre les logiciels malveillants"
    )
)