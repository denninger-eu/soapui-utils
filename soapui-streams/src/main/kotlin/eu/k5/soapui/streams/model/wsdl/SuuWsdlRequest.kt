package eu.k5.soapui.streams.model.wsdl

import eu.k5.soapui.streams.model.Header

interface SuuWsdlRequest {
    var name: String
    var description: String?
    val content: String

    val headers: List<Header>

    fun getHeader(key: String) = headers.firstOrNull { it.key == key }

    fun removeHeader(key: String)

    fun addOrUpdateHeader(header: Header)
}
