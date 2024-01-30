package app.trivial.asier.controller.main

import app.trivial.asier.controller.BaseFragment

open class MainBaseFragment : BaseFragment() {

    var title: String? = null

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setActionBarSubtitle(title)
    }

    fun setTitle(title: String?): MainBaseFragment {
        this.title = title
        return this
    }
}