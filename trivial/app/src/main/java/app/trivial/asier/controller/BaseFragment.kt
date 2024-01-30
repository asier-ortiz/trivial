package app.trivial.asier.controller

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    private val actionBar: ActionBar?
        get() = (requireActivity() as BaseActivity).supportActionBar

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (actionBar != null) {
            actionBar!!.setShowHideAnimationEnabled(false)
        }
    }

    override fun onPause() {
        super.onPause()
        retainInstance = true
    }

    override fun onResume() {
        super.onResume()
        retainInstance = retainInstance
    }

    fun reload(fragment: Fragment?) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .detach(fragment!!)
            .attach(fragment)
            .commit()
    }

    fun hideKeyboard() {
        (requireActivity() as BaseActivity).hideKeyboard()
    }
}