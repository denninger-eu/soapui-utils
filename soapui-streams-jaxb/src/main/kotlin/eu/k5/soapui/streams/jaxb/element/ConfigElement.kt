package eu.k5.soapui.streams.jaxb.element

import org.w3c.dom.Element
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAnyElement
import javax.xml.bind.annotation.XmlSeeAlso

@XmlSeeAlso(ConfigPropertyTransfersStepElement::class, ConfigRestRequestStepElement::class)
@XmlAccessorType(XmlAccessType.NONE)
open class ConfigElement {

    @XmlAnyElement
    var options: List<Element>? = ArrayList()


    fun getValue(key: String): String? {
        return options?.filter { it.localName == key }?.firstOrNull()?.textContent
    }
}