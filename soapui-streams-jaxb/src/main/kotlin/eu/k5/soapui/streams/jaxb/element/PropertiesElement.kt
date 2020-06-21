package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class PropertiesElement {

    @XmlElement(name = "property")
    var properties: List<PropertyElement>? = null

    @XmlAccessorType(XmlAccessType.NONE)
    class PropertyElement {

        @XmlElement(name = "name")
        var name: String? = null

        @XmlElement(name = "value")
        var value: String? = null
    }
}