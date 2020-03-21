package eu.k5.soapui.streams.direct.model.rest

import com.eviware.soapui.impl.rest.RestRequest
import eu.k5.soapui.streams.model.Header
import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestRequest

class RestRequestDirect(
    private val request: RestRequest

) : SuuRestRequest {

    override var name: String
        get() = request.name ?: ""
        set(value) {
            request.name = value
        }
    override var description: String?
        get() = request.description
        set(value) {
            request.description = value
        }

    override var content: String?
        get() = request.requestContent
        set(value) {
            request.requestContent = value
        }

    override val parameters: SuuRestParameters =
        RestParametersDirect(
            request.params,
            RestParametersDirect.Owner.REQUEST
        )

    override val headers: List<Header>
        get() = appendDefaultHeaders(request.requestHeaders.map { mapHeader(it) })


    private fun appendDefaultHeaders(headers: List<Header>): List<Header> {
        if (request.mediaType == null || contains(headers, "Content-Type")) {
            return headers
        }
        val newHeaders = ArrayList(headers);
        newHeaders.add(Header("Content-Type", request.mediaType))
        return newHeaders
    }

    override fun removeHeader(key: String) {
        request.requestHeaders.remove(key)
    }

    override fun addOrUpdateHeader(header: Header) {
        val requestHeaders = request.requestHeaders
        var existing = requestHeaders[header.key]
        if (existing != null) {
            existing.clear()
            existing.addAll(header.value)
            request.requestHeaders = requestHeaders
        } else {
            requestHeaders.put(header.key, header.value)
            request.requestHeaders = requestHeaders
        }
    }

    companion object {
        fun mapHeader(entry: Map.Entry<String, MutableList<String>>): Header {
            return Header(entry.key, ArrayList(entry.value))
        }
    }

    private fun contains(headerList: List<Header>, key: String): Boolean =
        headerList.firstOrNull { it.key.equals(key, true) } != null
}