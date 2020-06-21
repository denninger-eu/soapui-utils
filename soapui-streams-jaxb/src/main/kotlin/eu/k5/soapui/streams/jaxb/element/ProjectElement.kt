package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.*

@XmlRootElement(name = "soapui-project", namespace = "http://eviware.com/soapui/config")
@XmlAccessorType(XmlAccessType.NONE)
class ProjectElement {

    var id: String? = null


    @XmlAttribute(name = "name")
    var name: String? = null


    val interfaces: List<InterfaceElement>? = ArrayList()

    @XmlElement(name = "properties", namespace = "http://eviware.com/soapui/config")
    var properties: PropertiesElement? = null
}