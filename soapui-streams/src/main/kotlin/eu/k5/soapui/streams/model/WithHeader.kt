package eu.k5.soapui.streams.model

interface WithHeader {
    val headers: List<Header>

    fun getHeader(key: String) = headers.firstOrNull { it.key == key }

    fun removeHeader(key: String)

    fun addOrUpdateHeader(header: Header)

}