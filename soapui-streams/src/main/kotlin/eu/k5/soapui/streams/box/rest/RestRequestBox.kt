package eu.k5.soapui.streams.box.rest

import eu.k5.soapui.streams.box.Box
import eu.k5.soapui.streams.jaxb.rest.RestParameter
import eu.k5.soapui.streams.model.rest.SuuRestRequest

class RestRequestBox(
    val box: Box
) : SuuRestRequest {


    private val restRequest: RequestRequestYaml
        get() = box.load(RequestRequestYaml::class.java)

    override var name: String? = restRequest.name
    override var description: String? = restRequest.description


    override var content: String?
        get() = box.loadSection("content")
        set(value) = TODO("implement")

    override val parameters: MutableList<RestParameter>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    class RequestRequestYaml {
        var name: String? = null
        var description: String? = null

    }
}