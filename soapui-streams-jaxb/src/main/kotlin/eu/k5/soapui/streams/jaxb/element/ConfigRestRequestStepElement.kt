package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "RestRequestStep", namespace = NAMESPACE)
class ConfigRestRequestStepElement : ConfigElement() {

    @XmlAttribute(name = "service")
    var service: String? = null

    @XmlAttribute(name = "methodName")
    var methodName: String? = null

    @XmlAttribute(name = "resourcePath")
    var resourcePath: String? = null


    @XmlElement(name = "restRequest", namespace = NAMESPACE)
    var restRequest: RestRequestElement? = null

}