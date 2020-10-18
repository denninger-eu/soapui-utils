package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.RestResourceElement
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameter
import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestResource

class RestResourceJaxb(
    private val element: RestResourceElement,
    private val parentResource: SuuRestResource?
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
        get() = ParametersJaxb(element.parameters.orEmpty(), SuuRestParameter.Location.RESOURCE)

    override val methods: List<SuuRestMethod>
        get() = element.methods?.map { RestMethodJaxb(it) }.orEmpty()

    override val parent: SuuRestResource?
        get() = parentResource

    override val childResources: List<SuuRestResource>
        get() = element.resources?.map { RestResourceJaxb(it, this) }.orEmpty()

    override fun createMethod(name: String): SuuRestMethod {
        TODO("Not yet implemented")
    }

    override fun createChildResource(name: String, path: String): SuuRestResource {
        TODO("Not yet implemented")
    }

}
