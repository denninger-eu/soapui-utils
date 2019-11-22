package eu.k5.soapui.streams.jaxb.rest

import eu.k5.soapui.streams.model.rest.SuuRestService
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RestService(
    @XmlAttribute
    override var name: String = ""
) : SuuRestService {

    @XmlElement
    override var description: String? = null
    @XmlElement
    override var basePath: String = ""
    @XmlElement(name = "resource")
    override val resources = ArrayList<RestResource>()


    override fun createResource(name: String, path: String): RestResource {
        val resource = RestResource()
        resource.name = name
        resource.path = path
        resources.add(resource)
        return resource
    }

    override fun toString(): String {
        return "$name $resources"
    }
}