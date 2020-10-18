package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.RestServiceElement
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService

class RestServiceJaxb(
    private val element: RestServiceElement
) : SuuRestService {

    override var name: String
        get() = element.name ?: ""
        set(value) {
            TODO("Not yet implemented")
        }
    override var description: String?
        get() = element.description
        set(value) {
            TODO("Not yet implemented")
        }
    override var basePath: String
        get() = element.basePath ?: ""
        set(value) {
            TODO("Not yet implemented")

        }
    override val resources: List<SuuRestResource>
        get() = element.resources?.map { RestResourceJaxb(it, null) }.orEmpty()

    override val endpoints: List<String>
        get() = TODO("Not yet implemented")

    override fun addEndpoint(endpoint: String) {
        TODO("Not yet implemented")
    }

    override fun removeEndpoint(endpoint: String) {
        TODO("Not yet implemented")
    }

    override fun createResource(name: String, path: String): SuuRestResource {
        TODO("Not yet implemented")
    }

}