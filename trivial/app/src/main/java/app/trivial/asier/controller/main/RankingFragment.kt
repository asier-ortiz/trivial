package app.trivial.asier.controller.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener
import app.trivial.asier.R
import app.trivial.asier.controller.main.RankingFragment.ScoresAdapter.ScoreViewHolder
import app.trivial.asier.model.app.App
import app.trivial.asier.model.app.AppManager
import app.trivial.asier.model.app.manager.EntityManager
import app.trivial.asier.model.database.Score
import io.realm.Realm
import io.realm.RealmResults

class RankingFragment : MainBaseFragment() {

    companion object {
        private lateinit var fragment: RankingFragment

        fun newInstance(): RankingFragment {
            fragment = RankingFragment()
            fragment.setTitle(App.instance.getString(R.string.ranking))
            return fragment
        }
    }

    private lateinit var rankingRV: RecyclerView
    private lateinit var emptyRankingTV: TextView
    private lateinit var scoresAdapter: ScoresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activity != null) {
            val realm = Realm.getInstance(AppManager.config!!)
            scoresAdapter = ScoresAdapter(EntityManager.instance?.scoreRepository?.getAllScores(realm)!!)
            realm.close()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ranking, container, false)
        rankingRV = view.findViewById(R.id.rankingRV)
        rankingRV.adapter = scoresAdapter
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rankingRV.layoutManager = layoutManager
        rankingRV.itemAnimator = DefaultItemAnimator()
        val dividerItemDecoration = DividerItemDecoration(rankingRV.context, layoutManager.orientation)
        val context = context

        if (context != null) {
            dividerItemDecoration.setDrawable(context.resources.getDrawable(R.drawable.list_item_divider, context.theme))
        }

        rankingRV.addItemDecoration(dividerItemDecoration)

        rankingRV.addOnItemTouchListener(object : SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return false
            }
        })

        emptyRankingTV = view.findViewById(R.id.emptyRankingTV)
        return view
    }

    override fun onResume() {
        super.onResume()

        if (activity != null) {
            val activity = activity as MainActivity?
            activity?.enableBackNavigation(true)
            activity?.invalidateOptionsMenu()

            if (scoresAdapter.scores.size > 0) {
                rankingRV.visibility = View.VISIBLE
                emptyRankingTV.visibility = View.GONE

            } else {
                rankingRV.visibility = View.GONE
                emptyRankingTV.visibility = View.VISIBLE
            }
        }
    }

    private inner class ScoresAdapter(val scores: RealmResults<Score>) : RecyclerView.Adapter<ScoreViewHolder>() {

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ScoreViewHolder {
            val itemView = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.list_item_score, viewGroup, false)
            return ScoreViewHolder(itemView)
        }

        override fun onBindViewHolder(viewHolder: ScoreViewHolder, pos: Int) {
            val score = scores[pos]
            viewHolder.bindScore(score)
        }

        override fun getItemCount(): Int {
            return scores.size
        }

        private inner class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val positionTV: TextView = itemView.findViewById(R.id.positionTV)
            private val nameTV: TextView = itemView.findViewById(R.id.nameTV)
            private val medalIV: ImageView = itemView.findViewById(R.id.medalIV)
            private val scoreTV: TextView = itemView.findViewById(R.id.scoreTV)

            fun bindScore(score: Score?) {
                val pos = adapterPosition + 1
                val posString = "$pos."
                positionTV.text = posString
                nameTV.text = score!!.name
                scoreTV.text = score.score.toString()
                when (pos) {
                    1 -> {
                        medalIV.setImageResource(R.drawable.gold)
                        medalIV.visibility = View.VISIBLE
                    }
                    2 -> {
                        medalIV.setImageResource(R.drawable.silver)
                        medalIV.visibility = View.VISIBLE
                    }
                    3 -> {
                        medalIV.setImageResource(R.drawable.bronze)
                        medalIV.visibility = View.VISIBLE
                    }
                    else -> medalIV.visibility = View.GONE
                }
            }
        }
    }
}