package eu.k5.soapui.streams.jaxb.element

import eu.k5.soapui.streams.model.test.SuuTestStep
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class TestStepElement {


    @XmlAttribute(name = "name")
    var name: String? = null

    @XmlAttribute(name = "type")
    var type: String? = null

    @XmlAttribute(name = "disabled")
    var disabled: Boolean? = null

    @XmlElement(name = "description", namespace = NAMESPACE)
    var description: String? = null

    @XmlElement(name = "properties", namespace = NAMESPACE)
    var properties: PropertiesElement? = null

    @XmlElement(name = "config", namespace = NAMESPACE)
    var config: ConfigElement? = null


}