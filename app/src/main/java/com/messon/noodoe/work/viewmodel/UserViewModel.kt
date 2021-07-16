package com.messon.noodoe.work.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.messon.noodoe.work.module.ApiFinally
import com.messon.noodoe.work.module.ApiSuccess
import com.messon.noodoe.work.module.dto.UserInfoResponse
import com.messon.noodoe.work.module.dto.UserUpdateRequest
import com.messon.noodoe.work.repo.LoginRepository
import com.messon.noodoe.work.repo.UserRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepo: UserRepository,
                                        private val loginRepo: LoginRepository): BaseViewModel() {

    private val _userInfoResponse = MutableLiveData<UserInfoResponse>()
    val userInfoResponse: LiveData<UserInfoResponse> get() = _userInfoResponse

    fun login(map: Map<String, String>) {
        loginRepo.login(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                _userInfoResponse.value = it
            }, onError = {
                it.printStackTrace()
                _loadStatus.value = it.message?.let { massage -> ApiFinally(massage) }
            })
            .addTo(mDisposable)
    }

    fun update(userId: String, token: String, timeZone: Int) {
        userRepo.updateUser(userId, token, UserUpdateRequest(timeZone))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _loadStatus.value = ApiSuccess
                },
                onError = {
                    it.printStackTrace()
                    _loadStatus.value = it.message?.let { massage -> ApiFinally(massage) }
                }
            )
    }

    fun searchDataIndex(data: List<String>, target: String): Int {
        return data.indexOfFirst { s -> s.toInt() == target.toInt() }
    }
}