package app.trivial.asier.model.app.manager

import app.trivial.asier.model.database.AnswerRepository
import app.trivial.asier.model.database.CategoryRepository
import app.trivial.asier.model.database.QuestionRepository
import app.trivial.asier.model.database.ScoreRepository

class EntityManager {

    companion object {
        var instance: EntityManager? = null
            get() {
                if (field == null) {
                    field = EntityManager()
                }
                return field
            }
            private set
    }

    lateinit var questionRepository: QuestionRepository
    lateinit var answerRepository: AnswerRepository
    lateinit var categoryRepository: CategoryRepository
    lateinit var scoreRepository: ScoreRepository

    fun init() {
        questionRepository = QuestionRepository()
        answerRepository = AnswerRepository()
        categoryRepository = CategoryRepository()
        scoreRepository = ScoreRepository()
    }
}