package com.messon.noodoe.work.module.dto

import com.google.gson.annotations.SerializedName

data class UserUpdateRequest(
    @SerializedName("timezone") val timeZone: Int
)