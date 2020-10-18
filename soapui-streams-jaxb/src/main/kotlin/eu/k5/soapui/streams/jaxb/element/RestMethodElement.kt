package eu.k5.soapui.streams.jaxb.element

import eu.k5.soapui.streams.model.rest.SuuRestMethod
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.NONE)
class RestMethodElement {


    @XmlAttribute(name = "name")
    var name: String? = null

    @XmlAttribute(name = "method")
    var method: String? = null

    @XmlElement(name = "request", namespace = NAMESPACE)
    val requests: List<RestRequestElement>? = ArrayList()

    @XmlElementWrapper(name = "parameters", namespace = NAMESPACE)
    @XmlElement(name = "parameter", namespace = NAMESPACE)
    var parameters: List<ParameterElement>? = ArrayList()
}
