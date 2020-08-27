package eu.k5.soapui.streams.model

interface WithHeader {
    val headers: List<Header>

    val additionalHeaders: List<Header>

    fun allHeaders(): List<Header> {
        val result = ArrayList(headers)
        result.addAll(additionalHeaders)
        return result
    }


    fun getHeader(key: String) = headers.firstOrNull { it.key == key }

    fun removeHeader(key: String)

    fun addOrUpdateHeader(header: Header)


}