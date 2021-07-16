package com.messon.noodoe.work.repo

import com.messon.noodoe.work.module.dto.UserInfoResponse
import com.messon.noodoe.work.module.dto.UserUpdateRequest
import com.messon.noodoe.work.module.dto.UserUpdatedResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ServiceApi {

    @GET("/api/login")
    fun login(@QueryMap map : Map<String, String>): Single<UserInfoResponse>

    @PUT("/api/users/{user_id}")
    fun updateUser(@Path("user_id") userId: String,
                   @Header("X-Parse-Session-Token") token: String,
                   @Body request: UserUpdateRequest
    ): Single<UserUpdatedResponse>
}