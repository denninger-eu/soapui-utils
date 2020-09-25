package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.RestRequestElement
import eu.k5.soapui.streams.model.Header
import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestRequest

class RestRequestJaxb(
    private val element: RestRequestElement
) : SuuRestRequest {

    override var name: String
        get() = element.name ?: ""
        set(value) {}
    override var description: String?
        get() = element.description ?: ""
        set(value) {}
    override val parameters: SuuRestParameters
        get() = TODO("Not yet implemented")
    override var content: String?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var mediaType: String?
        get() = element.mediaType ?: ""
        set(value) {}
    override val headers: List<Header>
        get() = TODO("Not yet implemented")

    override fun removeHeader(key: String) {
        TODO("Not yet implemented")
    }

    override fun addOrUpdateHeader(header: Header) {
        TODO("Not yet implemented")
    }

}
