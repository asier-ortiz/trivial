package app.trivial.asier.model.database

import io.realm.RealmObject
import io.realm.annotations.Required

data class Category(var code: Int, @Required var name: String) : RealmObject()