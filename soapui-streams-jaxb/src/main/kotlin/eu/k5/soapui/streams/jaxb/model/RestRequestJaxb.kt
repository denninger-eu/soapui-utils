package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.RestRequestElement
import eu.k5.soapui.streams.model.Header
import eu.k5.soapui.streams.model.rest.SuuRestParameter
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
        get() = ParametersJaxb(element.parameters.orEmpty(), SuuRestParameter.Location.RESOURCE_OVERRIDE)

    override var content: String?
        get() = element.content ?: ""
        set(value) {}
    override var mediaType: String?
        get() = element.mediaType ?: ""
        set(value) {}
    override val headers: List<Header>
        get() = element.settings?.entries()?.map { Header(it.key ?: "", it.value ?: "") }.orEmpty()

    override fun removeHeader(key: String) {
        TODO("Not yet implemented")
    }

    override fun addOrUpdateHeader(header: Header) {
        TODO("Not yet implemented")
    }

}
