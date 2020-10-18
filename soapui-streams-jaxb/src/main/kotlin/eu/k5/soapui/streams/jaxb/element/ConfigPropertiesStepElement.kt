package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "PropertiesStep", namespace = NAMESPACE)
class ConfigPropertiesStepElement : ConfigElement() {
    @XmlElement(name = "properties", namespace = NAMESPACE)
    var properties: PropertiesElement? = null
}