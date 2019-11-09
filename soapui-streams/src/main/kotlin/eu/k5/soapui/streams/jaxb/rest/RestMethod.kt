package eu.k5.soapui.streams.jaxb.rest

import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RestMethod(
    @XmlAttribute
    override var name: String = "",
    @XmlElement
    override var description: String? = null,
    @XmlAttribute
    override var httpMethod: SuuRestMethod.HttpMethod? = null
) : SuuRestMethod {

    override var parameters: SuuRestParameters
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    @XmlElement(name = "request")
    override val requests = ArrayList<RestRequest>()


    override fun createRequest(name: String): SuuRestRequest {
        val request = RestRequest(name)
        requests.add(request)
        return request
    }
}