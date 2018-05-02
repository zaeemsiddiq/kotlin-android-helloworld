package com.kotlin.siddiqz.kotlin_android_helloworld

import okhttp3.*
import java.io.IOException

class HttpUtils {

    companion object {
        val JSON = MediaType.parse("application/json; charset=utf-8")
    }

    lateinit var client: OkHttpClient

    init {
        client = OkHttpClient()
    }

    fun post(url: String, postBody: String) {
        HttpUtils
        val body = RequestBody.create(JSON, postBody)
        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }
            override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
        })
    }

}