package app.trivial.asier.model.database

import io.realm.Realm

class AnswerRepository {

    fun insertAnswer(answer: Answer, realm: Realm): Answer {
        realm.beginTransaction()
        val insertedAnswer = realm.copyToRealm(answer)
        realm.commitTransaction()
        return insertedAnswer
    }
}