package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.*

@XmlRootElement
@XmlType(name = "RestService", namespace = NAMESPACE)
@XmlAccessorType(XmlAccessType.NONE)
class RestServiceElement(

) : InterfaceElement() {

    @XmlAttribute(name = "name")
    var name: String? = null

    @XmlAttribute(name = "basePath")
    var basePath: String? = null

    @XmlElement(name = "description", namespace = NAMESPACE)
    var description: String? = null


    @XmlAttribute(name = "disabled")
    var disabled: Boolean? = null

    @XmlElement(name = "resource", namespace = NAMESPACE)
    var resources: List<RestResourceElement>? = ArrayList()
}