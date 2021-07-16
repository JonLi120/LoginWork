package com.messon.noodoe.work.util

import com.messon.noodoe.work.BuildConfig
import com.messon.noodoe.work.module.Api.APP_ID
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit

object OkHttpUtil {

    fun createOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()

                val newRequest = originalRequest
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Parse-Application-Id", APP_ID)
                    .build()

                try {
                    val response = chain.proceed(newRequest)

                    checkHttpCode(response)

                    val body = response.body
                    val bodyString = checkResponseBody(body)

                    response.newBuilder().body(bodyString.toResponseBody(body?.contentType())).build()
                } catch (e: Exception) {
                    e.printStackTrace()
                    throw e
                }
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                            HttpLoggingInterceptor.Level.BODY
                        } else {
                            HttpLoggingInterceptor.Level.NONE
                        }
            })
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

    @Throws(IOException::class)
    private fun checkHttpCode(response: okhttp3.Response) {
        when (response.code) {
            403 -> throw IOException("目前不支援此地區使用")
            in 400 .. 499 -> {
                val bodyString = checkResponseBody(response.body)
                val json = JSONObject(bodyString)
                try {
                    throw IOException(json.get("error").toString())
                } catch (e: JSONException) {
                    throw IOException("發生意外錯誤，請稍後再試")
                }
            }
            in 500 .. 599 -> {
                throw IOException("伺服器端發生未知的錯誤")
            }
        }
    }

    @Throws(IOException::class)
    private fun checkResponseBody(body: ResponseBody?): String {
        val bodyString = body?.string()
        if (body != null && isJsonValid(bodyString!!)) {
            return bodyString
        } else {
            throw IOException("發生意外錯誤，請稍後再試")
        }
    }

    private fun isJsonValid(jsonStr: String): Boolean {
        try {
            JSONObject(jsonStr)
        } catch (e: IOException) {
            try {
                JSONArray(jsonStr)
            } catch (e: IOException) {
                return false
            }
        }
        return true
    }
}