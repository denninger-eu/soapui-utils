package eu.k5.soapui.streams.jaxb.element

import eu.k5.soapui.streams.model.rest.SuuRestMethod
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RestMethodElement {


    @XmlAttribute(name = "name")
    var name: String? = null

    @XmlAttribute(name = "method")
    var method: String? = null

    @XmlElement(name = "request", namespace = NAMESPACE)
    val requests: List<RestRequestElement>? = ArrayList()
}
