package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.NONE)
class RestRequestElement {

    @XmlAttribute(name = "name")
    var name: String? = null

    @XmlAttribute
    var mediaType: String? = null

    @XmlElement(name = "description", namespace = NAMESPACE)
    var description: String? = null

    @XmlElement(name = "request", namespace = NAMESPACE)
    var content: String? = null

    @XmlElement(name = "settings", namespace = NAMESPACE)
    var settings: SettingsElement? = null

    @XmlElementWrapper(name = "parameters", namespace = NAMESPACE)
    @XmlElement(name = "parameter", namespace = NAMESPACE)
    var parameters: List<ParameterElement>? = ArrayList()

    @XmlElement(name = "assertion", namespace = NAMESPACE)
    var assertions: List<AssertionElement>? = ArrayList()

}
