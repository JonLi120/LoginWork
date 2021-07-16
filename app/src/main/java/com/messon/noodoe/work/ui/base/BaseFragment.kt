package com.messon.noodoe.work.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.messon.noodoe.work.di.Injectable
import io.reactivex.rxjava3.disposables.CompositeDisposable

typealias Inflate<I> = (LayoutInflater, ViewGroup?, Boolean) -> I

abstract class BaseFragment<VB: ViewBinding>(@LayoutRes layoutId: Int, val inflate: Inflate<VB>) : Fragment(layoutId), Injectable {

    private var _binding: VB? = null
    val binding get() = _binding!!
    protected val mDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mDisposable.clear()
        _binding = null
    }
}