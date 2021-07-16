package com.messon.noodoe.work.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding4.view.clicks
import com.messon.noodoe.work.R
import com.messon.noodoe.work.databinding.FragmentUserConfigBinding
import com.messon.noodoe.work.module.ApiFinally
import com.messon.noodoe.work.module.ApiLoading
import com.messon.noodoe.work.module.ApiSuccess
import com.messon.noodoe.work.module.ExtraKey.EXTRA_MAIL
import com.messon.noodoe.work.module.ExtraKey.EXTRA_TIMEZONE
import com.messon.noodoe.work.module.ExtraKey.EXTRA_TOKEN
import com.messon.noodoe.work.module.ExtraKey.EXTRA_USER_ID
import com.messon.noodoe.work.ui.base.BaseFragment
import com.messon.noodoe.work.util.argumentDelegate
import com.messon.noodoe.work.viewmodel.UserViewModel
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserConfigFragment : BaseFragment<FragmentUserConfigBinding>(R.layout.fragment_user_config, FragmentUserConfigBinding::inflate) {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: UserViewModel by viewModels { factory }

    private val userId by argumentDelegate<String>(EXTRA_USER_ID)
    private val mail by argumentDelegate<String>(EXTRA_MAIL)
    private val timeZone by argumentDelegate<Int>(EXTRA_TIMEZONE)
    private val token by argumentDelegate<String>(EXTRA_TOKEN)

    private val timeZoneData by lazy {
        resources.getStringArray(R.array.array_timezone).asList()
    }

    private val adapter by lazy {
        ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, timeZoneData)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeViewModel()
        setupListener()
    }

    private fun initView() {
        binding.tvEmail.text = mail

        val dataIndex = viewModel.searchDataIndex(timeZoneData, timeZone.toString())
        binding.exposedDropdown.setText(timeZoneData[dataIndex], false)

        binding.exposedDropdown.setAdapter(adapter)
    }

    private fun setupListener() {
        binding.btnLogout.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).backLoginFragment()
            }
        }.addTo(mDisposable)

        binding.exposedDropdown.setOnItemClickListener { _, _, position, _ ->
            viewModel.update(userId, token, timeZoneData[position].toInt())
        }
    }

    private fun observeViewModel() {
        viewModel.run {
            loadState.observe(viewLifecycleOwner) {
                when (it) {
                    is ApiSuccess -> Toast.makeText(requireContext(), "Update Success!!", Toast.LENGTH_SHORT).show()
                    is ApiFinally -> Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                    else -> {}
                }
            }
        }
    }
}