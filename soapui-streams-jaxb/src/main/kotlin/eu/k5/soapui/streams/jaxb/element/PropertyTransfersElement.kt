package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class PropertyTransfersElement {


    @XmlElement(name = "name", namespace = NAMESPACE)
    var name: String? = null

    @XmlAttribute(name = "disabled")
    var disabled: Boolean? = null

    @XmlElement(name = "sourceType", namespace = NAMESPACE)
    var sourceType: String? = null

    @XmlElement(name = "sourceStep", namespace = NAMESPACE)
    var sourceStep: String? = null

    @XmlElement(name = "sourcePath", namespace = NAMESPACE)
    var sourcePath: String? = null

    @XmlElement(name = "sourceTransferType", namespace = NAMESPACE)
    var sourceTransferType: String? = null


    @XmlElement(name = "targetStep", namespace = NAMESPACE)
    var targetStep: String? = null

    @XmlElement(name = "targetType", namespace = NAMESPACE)
    var targetType: String? = null

    @XmlElement(name = "targetPath", namespace = NAMESPACE)
    var targetPath: String? = null

    @XmlElement(name = "targetTransferType", namespace = NAMESPACE)
    var targetTransferType: String? = null
}