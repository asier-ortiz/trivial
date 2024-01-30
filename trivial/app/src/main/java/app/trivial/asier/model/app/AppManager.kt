package app.trivial.asier.model.app

import android.content.Context
import android.util.Log
import app.trivial.asier.model.app.manager.EntityManager
import app.trivial.asier.model.database.Answer
import app.trivial.asier.model.database.Category
import app.trivial.asier.model.database.Migration
import app.trivial.asier.model.database.Question
import app.trivial.asier.model.util.SharedPreferencesUtil.get
import app.trivial.asier.model.util.SharedPreferencesUtil.put
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.exceptions.RealmError

class AppManager {

    companion object {
        private val TAG = AppManager::class.java.simpleName

        @get:Synchronized
        var instance: AppManager? = null
            get() {
                if (field == null) {
                    field = AppManager()
                }
                return field
            }
            private set

        var config: RealmConfiguration? = null
            private set
    }

    private lateinit var realm: Realm
    private var isMainActivityRunning = false

    fun init() {
        val context = App.instance.applicationContext
        Realm.init(context)

        config = RealmConfiguration.Builder()
            .name(AppParameters.DATABASE_NAME)
            .schemaVersion(1)
            .migration(Migration())
            .build()

        realm = Realm.getInstance(config!!)

        try {
            Realm.compactRealm(config!!)
        } catch (e: RealmError) {
            Log.i(TAG, "Compact RealmError: " + e.localizedMessage)
        }

        EntityManager.instance!!.init()
        val dataInserted = get(context, AppParameters.PREFERENCES_REALM_DATA_INSERTED, false) as Boolean

        if (!dataInserted) {
            insertData(context)
        }
    }

    fun setIsMainActivityRunning(isMainActivityRunning1: Boolean) {
        this.isMainActivityRunning = isMainActivityRunning1
    }

    private fun insertData(context: Context) {

        val realm = Realm.getInstance(config!!)

        // GEOGRAFIA
        var category = EntityManager.instance!!.categoryRepository.insertCategory(
            Category(AppParameters.CATEGORY_CODE_Geography, "Geografía"), realm
        )
        var answers = RealmList<Answer?>()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Alemán", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Suizo", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Francés", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Inglés", false), realm
            )
        )
        var question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál es el idioma más hablado en Suiza?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Ecuador", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Nicaragua", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Bolivia", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Paraguay", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué país está entre Perú y Colombia?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Rin", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Danubio", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Volga", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Sena", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál es el río más largo de Europa Occidental?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("El lago Leman", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("El lago Onega", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("El lago Peipus", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("El lago Vänern", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué lago baña la ciudad de Ginebra?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Hungría", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Austria", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Eslovaquia", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Rumanía", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿En qué país europeo se habla el magyar?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Mac", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Sen", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Ich", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Ova", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué palabra significa “hijo de” en los apellidos escoceses?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Jakarta", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Surabaya", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Bandung", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Semarang", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál es la capital de Indonesia?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Argentina", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Chile", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Uruguay", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Bolivia", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿En qué país se encuentra el pico Aconcagua?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Norte", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Sur", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Este", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Oeste", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿En qué hemisferio se encuentra Jamaica?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Grecia", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Malta", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Turquía", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Chipre", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿A qué país pertenece la isla de Creta?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )

        // ENTRETENIMIENTO
        category = EntityManager.instance!!.categoryRepository.insertCategory(
            Category(AppParameters.CATEGORY_CODE_Entertainment, "Entretenimiento"), realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Yogui", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Bubu", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Winnie Pooh", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Baloo", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál es el oso más famoso del parque nacional de Yellowstone?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Clint Eastwood", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("John Wayne", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Robert Duval", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Gary Cooper", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué actor, que no era el feo ni el malo, era el bueno?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Federico Fellini", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Pier Paolo Pasolini", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Roberto Rosselinni", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Bernardo Bertolucci", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Con qué director de cine italiano se casó la actriz Giulietta Masina?"),
            realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Rosalía", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Juanes", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Shakira", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Carlos Vives", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Quién fue la gran ganadora de los Grammy Latinos 2018?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Mudito", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Sabio", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Gruñón", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Dormilón", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál de los Siete Enanitos no tenía barba?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Antonio Banderas", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Javier Bardem", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Eduardo Noriega", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Javier Cámara", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué conocido actor español protagonizó “La máscara del Zorro” en 1998?"),
            realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Bossa Nova", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Bachata", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Indie\u200B Jazz", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Polka", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál es el tipo de música por el que se conoció mundialmente Astrud Gilberto?"),
            realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Yesterday", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Yellow Submarine", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Hey Jude", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Here Comes the Sun", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué canción de Los Beatles ha sido la más grabada?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Charlie Chaplin", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Himmler", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Vladimir Karpov", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Hermann Obrecht", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Quién dijo: Hitler me ha copiado el bigote?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Walt Disney", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Leopold Stokowski ", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Alejandro Villeli", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Deems Taylor", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Quién fue la primera voz de Mickey Mouse?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )

        // HISTORIA
        category = EntityManager.instance!!.categoryRepository.insertCategory(
            Category(AppParameters.CATEGORY_CODE_History, "Historia"), realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Catalina de Aragón", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Isabel de Aragón", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Juana la Loca", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("María de Aragón", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué reina británica era hija de los Reyes Católicos?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Francia", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Bélgica", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Luxemburgo", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Suiza", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué país fue llamado la Galia por los romanos?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Waterloo", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Gettysburg", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Trafalgar", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Termópilas", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué batalla crucial tuvo lugar en 1815?\n"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Venecia", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Roma", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Florencia", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Milán", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál era la ciudad hogar de Marco Polo?\n"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Tiberio", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Claudio", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Nerón", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Adriano", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Quién era el emperador de Roma cuando murió Jesús?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Carabinieri", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("KGB", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Mosad", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Caschi blu", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cómo se conoce a la policía italiana?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("La de Primo de Rivera", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("La de Francisco Franco", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("La de Manuel Antonio Noriega", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("La de Fulgencio Batista", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál fue la dictadura que comenzó en España en el año 1923?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("George Washington", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("John Adams", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Thomas Jefferson", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Abraham Lincoln", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Quién fue el primer presidente de los Estados Unidos?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Liberia", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Sierra Leona", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Costa de Marfil", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Ghana", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué país africano fue fundado en 1847 por esclavos americanos liberados?"),
            realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Hendaya", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Irún", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("San uan de Luz", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Bayona", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿En qué ciudad se entrevistaron Franco y Hitler?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )

        // ARTE Y LITERATURA
        category = EntityManager.instance!!.categoryRepository.insertCategory(
            Category(AppParameters.CATEGORY_CODE_ArtAndLiterature, "Arte y literatura"), realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Testamentos", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Testimonios", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Tests de examen", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Testaferros", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué escribía un testador?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Gulliver", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Capitán Nemo", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Marco Polo", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Robinson Crusoe", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Quién visitó un país gobernado por caballos?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Todos para uno y uno para todos", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("No hay que ir para atrás ni para darse impulso", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Aprende a vivir y sabrás morir bien", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Por Crom", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál era el lema de los Tres Mosqueteros?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("El Acueducto", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("El Alcázar", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Castillo de Coca", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Iglesia de Sotosalbos", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué tiene en Segovia 128 arcos?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Verdi", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Vivaldi", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Puccini", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Salieri", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Quién fue el italiano que puso música al Othelo de Shakespeare?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("H.G. Wells", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Isaac Asimov", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Julio Verne", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Mary Shelley", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Quién escribió La Guerra de los Mundos en 1898?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Francés", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Latín", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Italiano", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Griego", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿De qué asignatura fue catedrático Antonio Machado?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Emilio Salgari", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Mark Twain", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Alejandro Dumas", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Julio Verne", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Quién escribió las aventuras de Sandokán?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("El Zorro", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("El llanero solitario", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Batman", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("El Guerrero del Antifaz", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál es la identidad secreta de Don Diego de la Vega?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("La Torre Eiffel", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Catedral de Notre Dame", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Basilica de Sacré Coeur", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Palacio de Versalles", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué mide en París alrededor de 333 metros?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )

        // CIENCIA Y NATURALEZA
        category = EntityManager.instance!!.categoryRepository.insertCategory(
            Category(AppParameters.CATEGORY_CODE_ScienceAndNature, "Ciencia y naturaleza"), realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Neuronas", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Fibroblastos", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Fibroblastos", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Glóbulos blancos", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cómo se llaman las células nerviosas?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Un brillante", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Un diamante", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué es más valioso, un brillante o un diamante?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Paloma", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Tucán", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Golondrina", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Águila", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál es el pájaro símbolo de la paz?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Diciembre", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Agosto", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Mayo", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Septiembre", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿En qué mes el sol está más cerca de la Tierra?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Espalda", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Pies", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Muslos", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Manos", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿En qué parte del cuerpo se encuentra la piel más gruesa?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("A las alturas", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("A los espacios pequeños", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("A la oscuridad", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("A los espacios abiertos", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿A qué le tiene miedo una persona que sufre vértigo?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Hematología", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Cardiología", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Neurología", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Radiografía", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cómo se llama la ciencia que estudia la sangre?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Pila", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Bombilla", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Telégrafo", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Motor de combustión", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué fabricó Alessandro Volta, por primera vez, en 1800?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Olfato", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Vista", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Gusto", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Oido", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál de los cinco sentidos se desarrolla el primero?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Índice", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Pulgar", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Meñique", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Anular", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál es el dedo más sensible de la mano?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )

        // DEPORTES Y PASATIEMPOS
        category = EntityManager.instance!!.categoryRepository.insertCategory(
            Category(AppParameters.CATEGORY_CODE_SportsAndHobbies, "Deportes y pasatiempos"), realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Caballo", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Reina", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Alfíl", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Peón", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué pieza de ajedrez puede hacer un movimiento en forma de L?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Judokas", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Karatekas", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Ninjas", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Shamurais", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cómo se llaman los deportistas que practican el judo?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Baloncesto", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Beisbol", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Futbol", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Hockey Hielo", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué deporte practican los Harlem Globetrotters?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Veintiuno", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Doce", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("No hay sets", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Cuarenta y dos", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿A cuántos puntos se disputa un set en el tenis de mesa?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Sangría", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Zurracapote", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Calimocho", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Blood Mary", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Qué obtienes si añades fruta fresca al vino tinto?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Atlético de Madrid", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Real Madrid", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Real Betis", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Athletic Club", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿A qué equipo de fútbol pertenecía el estadio Metropolitano?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Francia", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Suiza", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Holanda", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Bélgica", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿En qué país se encuentra el circuito de Le Mans?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("España y Estados Unidos", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Yogoslavia y Rusia", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("España y Rusia", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Estados Unidos y Yugoslavia", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Quién fueron los finalistas de baloncesto en los Juegos Olímpicos de Los Ángeles de 1984?"),
            realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Cassius Clay", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Mike Tyson", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Floyd Mayweather", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Rocky Balboa", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuál era el nombre anterior de Muhammad Alí?"), realm
        )
        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        answers = RealmList()
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Seis", true), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Cinco", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Siete", false), realm
            )
        )
        answers.add(
            EntityManager.instance!!.answerRepository.insertAnswer(
                Answer("Doce", false), realm
            )
        )
        question = EntityManager.instance!!.questionRepository.insertQuestion(
            Question("¿Cuántos jugadores tiene un equipo de voleibol ?"), realm
        )

        EntityManager.instance!!.questionRepository.addAnswersAndCategoryToQuestion(
            answers,
            category,
            question,
            realm
        )
        realm.close()
        put(context, AppParameters.PREFERENCES_REALM_DATA_INSERTED, true)
    }
}