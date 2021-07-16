package com.messon.noodoe.work.module.dto

import com.google.gson.annotations.SerializedName

data class UserInfoResponse (
    @SerializedName("objectId") val userId: String,
    @SerializedName("username") val mail: String,
    @SerializedName("timezone") val timeZone: Int,
    @SerializedName("sessionToken") val token: String
)

data class UserUpdatedResponse(
    @SerializedName("updatedAt") val updatedAt: String
)