package eu.k5.soapui.streams.direct.model.wsdl

import com.eviware.soapui.impl.wsdl.WsdlRequest
import eu.k5.soapui.streams.direct.model.rest.RestRequestDirect
import eu.k5.soapui.streams.model.Header
import eu.k5.soapui.streams.model.wsdl.SuuWsdlRequest

class WsdlRequestDirect(
    private val wsdlRequest: WsdlRequest
) : SuuWsdlRequest {
    override fun markLostAndFound() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override var name: String
        get() = wsdlRequest.name
        set(value) {
            wsdlRequest.name = value
        }

    override var description: String?
        get() = wsdlRequest.description
        set(value) {
            wsdlRequest.description = value
        }

    override var content: String
        get() = wsdlRequest.requestContent
        set(value) {
            wsdlRequest.requestContent = value
        }

    override val headers: List<Header>
        get() = wsdlRequest.requestHeaders.map { mapHeader(it) }


    override fun removeHeader(key: String) {
        wsdlRequest.requestHeaders.remove(key)
    }

    override fun addOrUpdateHeader(header: Header) {
        val requestHeaders = wsdlRequest.requestHeaders
        var existing = requestHeaders[header.key]
        if (existing != null) {
            existing.clear()
            existing.addAll(header.value)
            wsdlRequest.setRequestHeaders(requestHeaders)
        } else {
            requestHeaders.put(header.key, header.value)
            wsdlRequest.setRequestHeaders(requestHeaders)
        }
    }

    companion object {
        fun mapHeader(entry: Map.Entry<String, MutableList<String>>): Header {
            return Header(entry.key, ArrayList(entry.value))
        }
    }

}
