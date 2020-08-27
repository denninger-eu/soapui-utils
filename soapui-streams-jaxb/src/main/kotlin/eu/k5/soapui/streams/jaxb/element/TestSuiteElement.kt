package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class TestSuiteElement {

    @XmlAttribute(name = "name")
    var name: String? = null

    @XmlAttribute(name = "disabled")
    var disabled: Boolean? = null

    @XmlElement(name = "properties", namespace = NAMESPACE)
    var properties: PropertiesElement? = null
}