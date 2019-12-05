package eu.k5.soapui.streams.model.rest

import eu.k5.soapui.streams.model.Header

interface SuuRestRequest {

    var name: String

    var description: String?

    val parameters: SuuRestParameters

    var content: String?

    val headers: List<Header>

    fun getHeader(key: String) = headers.firstOrNull { it.key == key }

    fun removeHeader(key: String)

    fun addOrUpdateHeader(header: Header)

}