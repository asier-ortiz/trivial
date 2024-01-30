package app.trivial.asier.model.database

import io.realm.Realm

class CategoryRepository {

    fun insertCategory(category: Category, realm: Realm): Category {
        realm.beginTransaction()
        val insertedCategory = realm.copyToRealm(category)
        realm.commitTransaction()
        return insertedCategory
    }
}