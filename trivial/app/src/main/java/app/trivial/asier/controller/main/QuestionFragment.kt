package app.trivial.asier.controller.main

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.trivial.asier.R
import app.trivial.asier.controller.DialogManager.showCorrectDialog
import app.trivial.asier.controller.DialogManager.showInCorrectDialog
import app.trivial.asier.controller.DialogManager.showTimeOutDialog
import app.trivial.asier.controller.main.QuestionFragment.AnswersAdapter.AnswerViewHolder
import app.trivial.asier.model.app.App
import app.trivial.asier.model.app.AppParameters
import app.trivial.asier.model.database.Answer
import com.google.android.material.card.MaterialCardView
import java.util.*

class QuestionFragment : MainBaseFragment() {

    companion object {
        private lateinit var fragment: QuestionFragment

        fun newInstance(): QuestionFragment {
            fragment = QuestionFragment()
            fragment.setTitle(App.instance.getString(R.string.question_number).let { String.format(it, 1) })
            return fragment
        }
    }

    private val TAG = javaClass.simpleName
    private val MAX_TIME: Long = 10000
    private val SCORE_MULTIPLIER = 100
    private lateinit var questionCV: MaterialCardView
    private lateinit var timerTV: TextView
    private lateinit var categoryTV: TextView
    private lateinit var questionTV: TextView
    private lateinit var answerRV: RecyclerView
    private lateinit var answersAdapter: AnswersAdapter
    private var questionIndex = -1
    private var remainingTime = 0f
    private lateinit var countDownTimer: CountDownTimer
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity != null) {
            answersAdapter = AnswersAdapter(ArrayList())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        questionCV = view.findViewById(R.id.questionCV)
        timerTV = view.findViewById(R.id.timerTV)
        categoryTV = view.findViewById(R.id.categoryTV)
        questionTV = view.findViewById(R.id.questionTV)
        answerRV = view.findViewById(R.id.answersRV)
        answerRV.adapter = answersAdapter
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        answerRV.layoutManager = layoutManager
        answerRV.itemAnimator = DefaultItemAnimator()
        return view
    }

    override fun onResume() {
        super.onResume()
        if (activity != null) {
            val activity = activity as MainActivity?
            activity?.enableBackNavigation(false)
            activity?.invalidateOptionsMenu()
            updateUI()
        }
    }

    fun updateUI() {
        if (activity != null) {
            val activity = activity as MainActivity?
            questionIndex += 1

            if (questionIndex < activity?.questions!!.size) {
                val question = activity.questions[questionIndex]
                questionTV.text = question!!.question
                val category = question.category
                categoryTV.text = category.name

                when (category.code) {
                    AppParameters.CATEGORY_CODE_Geography -> questionCV.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorGeography, null))
                    AppParameters.CATEGORY_CODE_Entertainment -> questionCV.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorEntertainment, null))
                    AppParameters.CATEGORY_CODE_History -> questionCV.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorHistory, null))
                    AppParameters.CATEGORY_CODE_ArtAndLiterature -> questionCV.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorArtAndLiterature, null))
                    AppParameters.CATEGORY_CODE_ScienceAndNature -> questionCV.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorScienceAndNature, null))
                    AppParameters.CATEGORY_CODE_SportsAndHobbies -> questionCV.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorSportsAndHobbies, null))
                }

                activity.setActionBarSubtitle(App.instance.getString(R.string.question_number).let { String.format(it, questionIndex + 1) })
                val answers = ArrayList(question.answers)
                answers.shuffle()
                answersAdapter.answers = answers
                answersAdapter.notifyDataSetChanged()
                remainingTime = MAX_TIME.toFloat()
                timerTV.text = String.format(getString(R.string.remaining_time), remainingTime / 1000)
                countDownTimer.cancel()

                countDownTimer = object : CountDownTimer(MAX_TIME, 10) {
                    override fun onTick(millisUntilFinished: Long) {
                        remainingTime = millisUntilFinished / 1000.0f
                        timerTV.text = String.format(getString(R.string.remaining_time), remainingTime)
                    }

                    override fun onFinish() {
                        remainingTime = 0.0f
                        timerTV.text = String.format(getString(R.string.remaining_time), remainingTime)
                        showTimeOutDialog(context!!, DialogInterface.OnCancelListener { updateUI() })
                    }
                }
                countDownTimer.start()

            } else {
                countDownTimer.cancel()
                activity.navigateToResult(score)
            }
        }
    }

    inner class AnswersAdapter internal constructor(var answers: ArrayList<Answer?>) : RecyclerView.Adapter<AnswerViewHolder>() {

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AnswerViewHolder {
            val itemView = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.list_item_answer, viewGroup, false)
            return AnswerViewHolder(itemView)
        }

        override fun onBindViewHolder(viewHolder: AnswerViewHolder, pos: Int) {
            val answer = answers[pos]
            viewHolder.bindAnswer(answer)
        }

        override fun getItemCount(): Int {
            return answers.size
        }

        inner class AnswerViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val answerLetterTV: TextView = itemView.findViewById(R.id.answerLetterTV)
            private val answerTV: TextView = itemView.findViewById(R.id.answerTV)

            init {
                itemView.setOnClickListener {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        val answer = answers[pos]
                        if (answer != null) {
                            countDownTimer.cancel()
                            if (answer.correct) {
                                score += (remainingTime * SCORE_MULTIPLIER).toInt()
                                Log.i(TAG, "Puntuacion: $score")
                                showCorrectDialog(context!!, DialogInterface.OnCancelListener { updateUI() })
                            } else {
                                showInCorrectDialog(context!!, DialogInterface.OnCancelListener { updateUI() })
                            }
                        }
                    }
                }
            }

            fun bindAnswer(answer: Answer?) {
                var letter = "A. "
                when (answers.indexOf(answer)) {
                    1 -> letter = "B. "
                    2 -> letter = "C. "
                    3 -> letter = "D. "
                }
                answerLetterTV.text = letter
                answerTV.text = answer!!.answer
            }
        }
    }
}