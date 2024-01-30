package app.trivial.asier.model.database

import io.realm.Realm
import io.realm.RealmList
import java.util.*

class QuestionRepository {

    fun insertQuestion(question: Question, realm: Realm): Question {
        realm.beginTransaction()
        val insertedQuestion = realm.copyToRealm(question)
        realm.commitTransaction()
        return insertedQuestion
    }

    fun addAnswersAndCategoryToQuestion(
        answers: RealmList<Answer?>,
        category: Category,
        question: Question,
        realm: Realm
    ) {
        realm.beginTransaction()
        question.category = category
        question.answers.addAll(answers)
        realm.commitTransaction()
    }

    fun getRandomQuestions(quantity: Int, realm: Realm): ArrayList<Question?> {
        val questions = ArrayList(realm.where(Question::class.java).findAll())
        questions.shuffle()
        return ArrayList(questions.subList(0, quantity))
    }
}