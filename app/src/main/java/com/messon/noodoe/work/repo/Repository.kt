package com.messon.noodoe.work.repo

import com.messon.noodoe.work.module.dto.UserUpdateRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val service: ServiceApi){
    fun login(map: Map<String, String>) = service.login(map)
}

class UserRepository @Inject constructor(private val service: ServiceApi) {
    fun updateUser(userId: String, token: String, timeZone: UserUpdateRequest) = service.updateUser(userId, token, timeZone)
}