package eu.k5.soapui.streams.model.rest

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RestService(
    @XmlAttribute
    override var name: String? = null
) : SuuRestService {
    override fun createResource(name: String, path: String): SuuResource {
        val resource = Resource()
        resource.name = name
        resource.path = path
        return resource
    }


    @XmlElement
    override var description: String? = null
    @XmlElement
    override var basePath: String? = null

    @XmlElement(name = "resource")
    override val resources = ArrayList<Resource>()

    override fun addResource(resource: SuuResource) {
        resources.add(resource as Resource)
    }

    override fun getResource(name: String): SuuResource? {
        return resources.first { it.name == name }
    }



    override fun toString(): String {
        return "$name $resources"
    }
}