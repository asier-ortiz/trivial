package app.trivial.asier.model.database

import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

class ScoreRepository {

    fun insertScore(score: Score, realm: Realm): Score {
        realm.beginTransaction()
        val insertedScore = realm.copyToRealm(score)
        realm.commitTransaction()
        return insertedScore
    }

    fun getAllScores(realm: Realm): RealmResults<Score> {
        return realm.where(Score::class.java).sort("score", Sort.DESCENDING).findAll()
    }

    fun getPositionForScore(score: Int, realm: Realm): Int {
        var pos = 0
        val scores = getAllScores(realm)
        for (i in scores.indices) {
            val realmScore = scores[i]
            if (realmScore != null && score >= realmScore.score) {
                pos = i
                break
            }
        }
        return pos + 1
    }
}