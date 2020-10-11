package eu.k5.soapui.streams.jaxb.element

import eu.k5.soapui.streams.box.rest.RestParametersBox
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.NONE)
class RestResourceElement {

    @XmlAttribute(name = "name")
    var name: String? = null

    @XmlAttribute(name = "path")
    var path: String? = null


    @XmlElementWrapper(name = "parameters", namespace = NAMESPACE)
    @XmlElement(name = "parameter", namespace = NAMESPACE)
    var parameters: List<ParameterElement>? = ArrayList()

    @XmlElement(name = "resource", namespace = NAMESPACE)
    var resources: List<RestResourceElement>? = ArrayList()

    @XmlElement(name = "method", namespace = NAMESPACE)
    var methods: List<RestMethodElement>? = ArrayList()
}
