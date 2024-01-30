package app.trivial.asier.controller.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.trivial.asier.R
import app.trivial.asier.model.app.App
import com.google.android.material.button.MaterialButton

class NewGameFragment : MainBaseFragment(), View.OnClickListener {

    companion object {
        lateinit var fragment: NewGameFragment

        fun newInstance(): NewGameFragment {
            fragment = NewGameFragment()
            fragment.setTitle(App.instance.getString(R.string.new_game))
            return fragment
        }
    }

    private lateinit var newB: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_game, container, false)
        newB = view.findViewById(R.id.newB)
        newB.setOnClickListener(this)
        return view
    }

    override fun onResume() {
        super.onResume()
        if (activity != null) {
            val activity = activity as MainActivity?
            activity?.enableBackNavigation(false)
            activity?.invalidateOptionsMenu()
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.newB) {
            if (activity != null && isAdded) {
                val activity = activity as MainActivity?
                activity?.navigateToQuestion()
            }
        }
    }
}