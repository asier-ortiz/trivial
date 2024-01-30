package app.trivial.asier.controller.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import app.trivial.asier.R
import app.trivial.asier.controller.BaseActivity
import app.trivial.asier.model.app.AppManager
import app.trivial.asier.model.app.AppParameters
import app.trivial.asier.model.app.manager.EntityManager
import app.trivial.asier.model.database.Question
import io.realm.Realm
import java.util.*

class MainActivity : BaseActivity() {

    companion object {
        private const val QUESTIONS_PER_GAME = 10
    }

    private lateinit var toolbar: Toolbar
    lateinit var questions: ArrayList<Question?>

    private val currentFragment: Fragment?
        get() {
            if (supportFragmentManager.backStackEntryCount == 0) {
                return null
            }
            val tag = supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name
            return supportFragmentManager.findFragmentByTag(tag)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        isMain = true
        container = findViewById(R.id.mainFL)
        navigateToNewGame()
    }

    override fun onResume() {
        super.onResume()
        AppManager.instance!!.setIsMainActivityRunning(true)
    }

    override fun onPause() {
        super.onPause()
        AppManager.instance!!.setIsMainActivityRunning(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_action_bar, menu)
        val fragment = currentFragment
        var showRanking = true
        if (fragment is QuestionFragment
            || fragment is EndGameFragment
            || fragment is RankingFragment
        ) {
            showRanking = false
        }
        menu.findItem(R.id.main_menu_action_bar_ranking).isVisible = showRanking
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.main_menu_action_bar_ranking) {
            navigateToRanking()
            true
        } else {
            false
        }
    }

    private fun loadRandomQuestions() {
        val realm = Realm.getInstance(AppManager.config!!)
        questions = EntityManager.instance!!.questionRepository.getRandomQuestions(
            QUESTIONS_PER_GAME,
            realm
        )
        realm.close()
    }

    fun setActionBarSubtitle(subtitle: String?) {
        toolbar.setTitle(R.string.app_name)
        toolbar.subtitle = subtitle
    }

    fun enableBackNavigation(enable: Boolean) {
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(enable)
        toolbar.setNavigationIcon(if (enable) R.drawable.ic_back else R.drawable.logo_small)
        toolbar.setNavigationOnClickListener(if (enable) View.OnClickListener { onBackPressed() } else null)
    }

    fun navigateToNewGame() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack(
                fragmentManager.getBackStackEntryAt(0).id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        val newGameFragment = NewGameFragment.newInstance()
        openFragment(newGameFragment, newGameFragment?.title)
    }

    fun navigateToQuestion() {
        loadRandomQuestions()
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack(
                fragmentManager.getBackStackEntryAt(0).id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        val questionFragment: QuestionFragment = QuestionFragment.newInstance()
        openFragment(questionFragment, questionFragment.title)
    }

    fun navigateToResult(score: Int) {
        loadRandomQuestions()
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack(
                fragmentManager.getBackStackEntryAt(0).id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        val endGameFragment = EndGameFragment.newInstance()
        val bundle = Bundle()
        bundle.putInt(AppParameters.BUNDLE_SCORE, score)
        endGameFragment.arguments = bundle
        openFragment(endGameFragment, endGameFragment.title)
    }

    private fun navigateToRanking() {
        val contactFragment = RankingFragment.newInstance()
        openFragment(contactFragment, contactFragment.title, true)
    }
}