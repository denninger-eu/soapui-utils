package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RestRequestElement {

    @XmlAttribute(name = "name")
    var name: String? = null

    @XmlAttribute
    var mediaType: String? = null

    @XmlElement(name = "description", namespace = NAMESPACE)
    var description: String? = null
}
