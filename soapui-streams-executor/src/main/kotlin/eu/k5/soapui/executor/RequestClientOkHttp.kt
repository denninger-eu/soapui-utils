package eu.k5.soapui.executor

import okhttp3.OkHttpClient
import okhttp3.Request


class RequestClientOkHttp {

    fun call(): String? {

        val url = ""

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build();

        val response = client.newCall(request).execute();
        return response.body()?.string();

    }
}