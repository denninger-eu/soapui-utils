package eu.k5.soapui.streams.direct.model.rest

import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.support.types.StringToStringMap
import com.eviware.soapui.support.types.StringToStringsMap
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

    override val headers: List<SuuRestRequest.Header>
        get() = request.requestHeaders.map { mapHeader(it) }


    override fun removeHeader(key: String) {
        request.requestHeaders.remove(key)
    }

    override fun addOrUpdateHeader(header: SuuRestRequest.Header) {
        val requestHeaders = request.requestHeaders
        var existing = requestHeaders[header.key]
        if (existing != null) {
            existing.clear()
            existing.addAll(header.value)
            request.setRequestHeaders(requestHeaders)
        } else {
            requestHeaders.put(header.key, header.value)
            request.setRequestHeaders(requestHeaders)
        }
    }

    companion object {
        fun mapHeader(entry: Map.Entry<String, MutableList<String>>): SuuRestRequest.Header {
            return SuuRestRequest.Header(entry.key, ArrayList(entry.value))
        }
    }

}