package eu.k5.soapui.executor

import okhttp3.OkHttpClient

class ClientFactory {
    fun client(): OkHttpClient {
        return OkHttpClient()
    }

}