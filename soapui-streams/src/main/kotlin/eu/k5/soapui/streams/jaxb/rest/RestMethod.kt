package eu.k5.soapui.streams.jaxb.rest

import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RestMethod(
    @XmlAttribute
    override var name: String? = null,
    @XmlElement
    override var description: String? = null,
    @XmlAttribute
    override var method: SuuRestMethod.HttpMethod? = null
) : SuuRestMethod {

    override val parameters: MutableList<RestParameter> = ArrayList()

    @XmlElement(name = "request")
    override val requests = ArrayList<RestRequest>()


    override fun createRequest(name: String): SuuRestRequest {
        val request = RestRequest(name)
        requests.add(request)
        return request
    }
}