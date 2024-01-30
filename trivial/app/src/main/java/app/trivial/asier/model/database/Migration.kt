package app.trivial.asier.model.database

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration

class Migration : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema
        if (oldVersion == 0L) {
            schema.create("Score")
                    .addField("score", Int::class.java)
                    .addField("name", String::class.java, FieldAttribute.REQUIRED)
            //            oldVersion++;
        }
    }
}