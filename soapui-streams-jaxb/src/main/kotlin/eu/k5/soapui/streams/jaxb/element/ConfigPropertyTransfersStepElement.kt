package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "PropertyTransfersStep", namespace = NAMESPACE)
class ConfigPropertyTransfersStepElement : ConfigElement() {

    @XmlElement(name = "transfers", namespace = NAMESPACE)
    var transfers: List<PropertyTransfersElement>? = ArrayList()

}