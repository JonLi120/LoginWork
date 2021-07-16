package com.messon.noodoe.work.ui

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding4.view.clicks
import com.messon.noodoe.work.R
import com.messon.noodoe.work.databinding.FragmentLoginBinding
import com.messon.noodoe.work.module.ApiFinally
import com.messon.noodoe.work.ui.base.BaseFragment
import com.messon.noodoe.work.viewmodel.UserViewModel
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login, FragmentLoginBinding::inflate) {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: UserViewModel by viewModels { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeViewModel()
        setupListener()
    }

    private fun initView() {
        binding.edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun setupListener() {
        binding.btnLogin.clicks().throttleFirst(1, TimeUnit.SECONDS)
            .subscribe {
                val mail = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()

                viewModel.login(mapOf("username" to mail, "password" to password))
            }.addTo(mDisposable)
    }

    private fun observeViewModel() {
        viewModel.run {
            userInfoResponse.observe(viewLifecycleOwner) {
                if (requireActivity() is MainActivity) {
                    (requireActivity() as MainActivity).addUserConfigFragment(it.userId, it.mail, it.timeZone, it.token)
                }
            }

            loadState.observe(viewLifecycleOwner) {
                if (it is ApiFinally) {
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}