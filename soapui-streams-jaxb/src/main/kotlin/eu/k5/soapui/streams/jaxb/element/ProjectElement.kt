package eu.k5.soapui.streams.jaxb.element

import javax.xml.bind.annotation.*

@XmlRootElement(name = "soapui-project", namespace = NAMESPACE)
@XmlAccessorType(XmlAccessType.NONE)
class ProjectElement {

    var id: String? = null


    @XmlAttribute(name = "name")
    var name: String? = null


    val interfaces: List<InterfaceElement>? = ArrayList()

    @XmlElement(name = "properties", namespace = NAMESPACE)
    var properties: PropertiesElement? = null

    @XmlElement(name = "testSuite", namespace = NAMESPACE)
    var testSuites: List<TestSuiteElement>? = ArrayList()
}