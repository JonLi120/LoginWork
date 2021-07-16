package com.messon.noodoe.work.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.messon.noodoe.work.module.ApiState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.io.Serializable

abstract class BaseViewModel : ViewModel(), Serializable {
    protected val mDisposable = CompositeDisposable()
    protected val _loadStatus = MutableLiveData<ApiState>()

    val loadState: LiveData<ApiState> get() = _loadStatus

    override fun onCleared() {
        super.onCleared()
        mDisposable.clear()
    }

}