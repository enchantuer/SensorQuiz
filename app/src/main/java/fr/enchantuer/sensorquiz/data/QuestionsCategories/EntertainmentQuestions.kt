package fr.enchantuer.sensorquiz.data.QuestionsCategories

import fr.enchantuer.sensorquiz.data.Answers
import fr.enchantuer.sensorquiz.data.Question
import fr.enchantuer.sensorquiz.data.QuestionType

val entertainmentQuestions = listOf(
    Question(
        id = 1,
        question = "Quel film a remporté l’Oscar du meilleur film en 1994 ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Forrest Gump", "Pulp Fiction"),
        correctAnswer = "Forrest Gump"
    ),
    Question(
        id = 2,
        question = "Michael Jackson était surnommé le roi de la pop.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Vrai"
    ),
    Question(
        id = 3,
        question = "Quel super-héros est connu sous le nom de Bruce Wayne ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Batman", "Iron Man"),
        correctAnswer = "Batman"
    ),
    Question(
        id = 4,
        question = "La série 'Friends' se déroule à New York.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Vrai"
    ),
    Question(
        id = 5,
        question = "Quel acteur incarne Iron Man dans l’univers Marvel ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Robert Downey Jr.", "Chris Evans"),
        correctAnswer = "Robert Downey Jr."
    ),
    Question(
        id = 6,
        question = "Le film 'Titanic' a été réalisé par Steven Spielberg.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Faux"
    ),
    Question(
        id = 7,
        question = "Quel personnage est un sorcier célèbre dans les livres de J.K. Rowling ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Harry Potter", "Frodon"),
        correctAnswer = "Harry Potter"
    ),
    Question(
        id = 8,
        question = "Le film 'Avatar' se passe sur la planète Mars.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Faux"
    ),
    Question(
        id = 9,
        question = "Quel chanteur a popularisé la chanson 'Shape of You' ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Ed Sheeran", "Justin Bieber"),
        correctAnswer = "Ed Sheeran"
    ),
    Question(
        id = 10,
        question = "La saga 'Star Wars' contient des sabres lasers.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Vrai"
    ),
    Question(
        id = 11,
        question = "Quel film d’animation met en scène un jouet nommé Buzz l’Éclair ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Toy Story", "Cars"),
        correctAnswer = "Toy Story"
    ),
    Question(
        id = 12,
        question = "La musique reggae vient du Brésil.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Faux"
    ),
    Question(
        id = 13,
        question = "Quel film Disney raconte l’histoire de Simba ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Le Roi Lion", "Aladdin"),
        correctAnswer = "Le Roi Lion"
    ),
    Question(
        id = 14,
        question = "Netflix est une plateforme de streaming.",
        type = QuestionType.TWO_CHOICES,
        answers = Answers("Vrai", "Faux"),
        correctAnswer = "Vrai"
    ),
    Question(
        id = 15,
        question = "Quel film met en scène un requin géant nommé Bruce ?",
        type = QuestionType.THREE_CHOICES,
        answers = Answers("Les Dents de la mer", "Finding Nemo"),
        correctAnswer = "Les Dents de la mer"
    )
)
