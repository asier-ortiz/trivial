package app.trivial.asier.controller.main

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import app.trivial.asier.R
import app.trivial.asier.model.app.App
import app.trivial.asier.model.app.AppManager
import app.trivial.asier.model.app.AppParameters
import app.trivial.asier.model.app.manager.EntityManager
import app.trivial.asier.model.database.Score
import com.google.android.material.textfield.TextInputLayout
import io.realm.Realm
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import java.util.*

class EndGameFragment : MainBaseFragment(), View.OnClickListener {

    companion object {
        private lateinit var fragment: EndGameFragment

        fun newInstance(): EndGameFragment {
            fragment = EndGameFragment()
            fragment.title = App.instance.getString(R.string.end_game)
            return fragment
        }
    }

    private lateinit var konfettiView: KonfettiView
    private lateinit var scoreTV: TextView
    private lateinit var rankingTV: TextView
    private lateinit var medalIV: ImageView
    private lateinit var nameTIL: TextInputLayout
    private lateinit var nameET: EditText
    private lateinit var nameIB: ImageButton
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            score = bundle.getInt(AppParameters.BUNDLE_SCORE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_end_game, container, false)
        konfettiView = view.findViewById(R.id.viewKonfetti)
        scoreTV = view.findViewById(R.id.scoreTV)
        rankingTV = view.findViewById(R.id.rankingTV)
        medalIV = view.findViewById(R.id.medalIV)
        nameTIL = view.findViewById(R.id.nameTIL)
        nameET = view.findViewById(R.id.nameET)
        nameET.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveScore()
                handled = true
            }
            handled
        }
        nameIB = view.findViewById(R.id.nameIB)
        nameIB.setOnClickListener(this)
        return view
    }

    override fun onResume() {
        super.onResume()
        if (activity != null) {
            val activity = activity as MainActivity
            activity.enableBackNavigation(false)
            activity.invalidateOptionsMenu()
            updateUI()
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.nameIB) {
            saveScore()
        }
    }

    private fun updateUI() {
        scoreTV.text = score.toString()
        val realm = Realm.getInstance(AppManager.config!!)
        val pos = EntityManager.instance!!.scoreRepository.getPositionForScore(score, realm)
        realm.close()
        rankingTV.text = pos.toString()
        when (pos) {
            1 -> {
                medalIV.setImageResource(R.drawable.gold)
                medalIV.visibility = View.VISIBLE
                showKonfetti()
            }

            2 -> {
                medalIV.setImageResource(R.drawable.silver)
                medalIV.visibility = View.VISIBLE
                showKonfetti()
            }

            3 -> {
                medalIV.setImageResource(R.drawable.bronze)
                medalIV.visibility = View.VISIBLE
                showKonfetti()
            }

            else -> medalIV.visibility = View.GONE
        }
    }

    private fun showKonfetti() {
        konfettiView.post {
            if (context != null) {
                val colors = ArrayList<Int>()
                colors.add(ContextCompat.getColor(requireContext(), R.color.colorKontefetti1))
                colors.add(ContextCompat.getColor(requireContext(), R.color.colorKontefetti2))
                colors.add(ContextCompat.getColor(requireContext(), R.color.colorKontefetti3))
                colors.add(ContextCompat.getColor(requireContext(), R.color.colorKontefetti4))
                konfettiView.build()
                    .addColors(colors)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.RECT, Shape.CIRCLE)
                    .addSizes(Size(12, 5F))
                    .setPosition(-50f, konfettiView.width + 50f, -50f, -50f)
                    .streamFor(300, 5000L)
            }
        }
    }

    private fun saveScore() {
        hideKeyboard()
        val name = nameET.text.toString().trim { it <= ' ' }
        var cancel = false
        var focusView: View? = null
        if (TextUtils.isEmpty(name)) {
            nameTIL.isErrorEnabled = true
            nameTIL.error = getString(R.string.name_required)
            focusView = nameET
            cancel = true
        } else {
            nameTIL.error = ""
            nameTIL.isErrorEnabled = false
        }
        if (cancel) {
            focusView?.requestFocus()
        } else {
            val realm = Realm.getInstance(AppManager.config!!)
            EntityManager.instance?.scoreRepository?.insertScore(Score(score, name), realm)
            realm.close()
            if (activity != null) {
                (activity as MainActivity?)!!.navigateToNewGame()
            }
        }
    }
}