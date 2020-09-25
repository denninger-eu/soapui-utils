package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.RestResourceElement
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestResource

class RestResourceJaxb(
    private val element: RestResourceElement
) : SuuRestResource {

    override var name: String
        get() = element.name ?: ""
        set(value) {}
    override var description: String?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var path: String?
        get() = element.path
        set(value) {}
    override val parameters: SuuRestParameters
        get() = TODO("Not yet implemented")

    override val methods: List<SuuRestMethod>
        get() = element.methods?.map { RestMethodJaxb(it) }.orEmpty()

    override val childResources: List<SuuRestResource>
        get() = element.resources?.map { RestResourceJaxb(it) }.orEmpty()

    override fun createMethod(name: String): SuuRestMethod {
        TODO("Not yet implemented")
    }

    override fun createChildResource(name: String, path: String): SuuRestResource {
        TODO("Not yet implemented")
    }

}
