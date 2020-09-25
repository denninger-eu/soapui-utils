package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.RestMethodElement
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestRequest

class RestMethodJaxb(
    private val element: RestMethodElement
) : SuuRestMethod {

    override val parameters: SuuRestParameters
        get() = TODO("Not yet implemented")
    override var name: String
        get() = element.name ?: ""
        set(value) {}
    override var description: String?
        get() = TODO("Not yet implemented")
        set(value) {}


    override var httpMethod: SuuRestMethod.HttpMethod?
        get() = SuuRestMethod.HttpMethod.forName(element.method ?: "")
        set(value) {}
    override val requests: List<SuuRestRequest>
        get() = element.requests?.map { RestRequestJaxb(it) }.orEmpty()

    override fun createRequest(name: String): SuuRestRequest {
        TODO("Not yet implemented")
    }

    companion object {
    }
}