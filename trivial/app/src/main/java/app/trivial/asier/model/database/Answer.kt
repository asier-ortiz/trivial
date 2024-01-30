package app.trivial.asier.model.database

import io.realm.RealmObject
import io.realm.annotations.Required

data class Answer(@Required var answer: String, @Required var correct: Boolean) : RealmObject()