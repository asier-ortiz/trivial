package app.trivial.asier.model.database

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Required

data class Question(@Required var question: String) : RealmObject() {
    lateinit var category: Category
    lateinit var answers: RealmList<Answer>
}