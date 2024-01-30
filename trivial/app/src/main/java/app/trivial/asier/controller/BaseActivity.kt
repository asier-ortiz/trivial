package app.trivial.asier.controller

import android.annotation.SuppressLint
import android.app.Activity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import app.trivial.asier.R
import com.google.android.material.snackbar.Snackbar
import java.util.*

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    lateinit var container: View
    var isMain = true

    override fun onBackPressed() {
        hideKeyboard()

        if (supportFragmentManager.backStackEntryCount <= 1) {

            if (isMain) {
                Snackbar.make(container, resources.getString(R.string.exit), Snackbar.LENGTH_LONG)
                    .setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimaryLight))
                    .setAction(resources.getString(R.string.yes)) { super@BaseActivity.finishAffinity() }
                    .show()

            } else {
                super.onBackPressed()
            }

        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun openFragment(fragment: Fragment?, tag: String?, backStack: Boolean = true) {
        hideKeyboard()

        if (backStack) {

            if (supportFragmentManager.backStackEntryCount == 0) {
                supportFragmentManager.beginTransaction().add(container.id, fragment!!, tag)
                    .addToBackStack(tag).commit()

            } else {
                supportFragmentManager.beginTransaction().replace(container.id, fragment!!, tag)
                    .addToBackStack(tag).commit()
            }

        } else {
            supportFragmentManager.beginTransaction().replace(container.id, fragment!!, tag)
                .commit()
        }
    }

    fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        if (currentFocus != null && currentFocus!!.windowToken != null) {
            Objects.requireNonNull(inputMethodManager)
                .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}