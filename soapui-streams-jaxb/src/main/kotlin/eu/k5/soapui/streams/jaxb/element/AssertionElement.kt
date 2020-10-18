package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.Element
import javax.xml.bind.JAXBElement
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.NONE)
class AssertionElement {

    @XmlAttribute(name = "type")
    var type: String? = null

    @XmlAttribute(name = "name")
    var name: String? = null


    @XmlAnyElement
    @XmlElementWrapper(name = "configuration", namespace = NAMESPACE)
    var configurations: List<org.w3c.dom.Element>? = ArrayList()


    val options: Map<String, String> by lazy {
        val result = HashMap<String, String>()
        for (element in configurations.orEmpty()) {
            result[element.localName] = element.textContent?.toString() ?: ""
        }
        result
    }

}