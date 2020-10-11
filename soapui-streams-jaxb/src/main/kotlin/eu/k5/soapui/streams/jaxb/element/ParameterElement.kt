package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement


@XmlAccessorType(XmlAccessType.NONE)
class ParameterElement {
    @XmlElement(name = "name", namespace = NAMESPACE)
    var name: String? = null

    @XmlElement(name = "value", namespace = NAMESPACE)
    var value: String? = null

    @XmlElement(name = "style", namespace = NAMESPACE)
    var style: String? = null

    @XmlElement(name = "default", namespace = NAMESPACE)
    var default: String? = null

}
