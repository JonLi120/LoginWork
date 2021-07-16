package com.messon.noodoe.work.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.messon.noodoe.work.R
import com.messon.noodoe.work.databinding.ActivityMainBinding
import com.messon.noodoe.work.module.ExtraKey.EXTRA_MAIL
import com.messon.noodoe.work.module.ExtraKey.EXTRA_TIMEZONE
import com.messon.noodoe.work.module.ExtraKey.EXTRA_TOKEN
import com.messon.noodoe.work.module.ExtraKey.EXTRA_USER_ID
import com.messon.noodoe.work.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addLoginFragment()
    }

    private fun addLoginFragment() {
        hideKeyboard()
        supportFragmentManager.commit {
            add<LoginFragment>(R.id.container)
        }
    }

    fun addUserConfigFragment(userId: String, mail: String, timeZone: Int, token: String) {
        supportFragmentManager.commit {
            add<UserConfigFragment>(R.id.container,
                UserConfigFragment::class.simpleName,
                bundleOf(EXTRA_USER_ID to userId, EXTRA_MAIL to mail, EXTRA_TIMEZONE to timeZone, EXTRA_TOKEN to token)
            )
            addToBackStack(UserConfigFragment::class.simpleName)
        }
    }

    fun backLoginFragment() {
        supportFragmentManager.popBackStackImmediate()
    }

    private fun hideKeyboard() {
        val view = currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}