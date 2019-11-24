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
    override val endpoints: List<String>
        get() = ArrayList()

    override fun addEndpoint(endpoint: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeEndpoint(endpoint: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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