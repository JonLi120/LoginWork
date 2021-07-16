package com.messon.noodoe.work.module

sealed class ApiState

object ApiLoading : ApiState()

object ApiSuccess : ApiState()

data class ApiFinally(val msg : String) : ApiState()