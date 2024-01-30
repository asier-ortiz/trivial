package app.trivial.asier.model.database

import io.realm.RealmObject
import io.realm.annotations.Required

data class Score(var score: Int = 0, @Required var name: String) : RealmObject()