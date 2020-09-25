package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RestResourceElement {

    @XmlAttribute(name = "name")
    var name: String? = null

    @XmlAttribute(name = "path")
    var path: String? = null

    @XmlElement(name = "resource", namespace = NAMESPACE)
    var resources: List<RestResourceElement>? = ArrayList()

    @XmlElement(name = "method", namespace = NAMESPACE)
    var methods: List<RestMethodElement>? = ArrayList()
}
