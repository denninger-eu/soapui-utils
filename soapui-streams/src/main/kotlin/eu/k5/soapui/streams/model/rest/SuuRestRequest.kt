package eu.k5.soapui.streams.model.rest

import eu.k5.soapui.streams.jaxb.rest.RestParameter
import java.util.*

interface SuuRestRequest {

    var name: String

    var description: String?

    val parameters: SuuRestParameters

    val headers: List<Header>

    var content: String?

    fun getHeader(key: String) = headers.firstOrNull { it.key == key }

    fun removeHeader(key: String)

    fun addOrUpdateHeader(header: Header)

    class Header(
        val key: String,
        val value: List<String>
    ) {
        constructor(key: String, value: String) : this(key, listOf(value))
    }

}