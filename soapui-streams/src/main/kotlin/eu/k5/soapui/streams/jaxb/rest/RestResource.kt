package eu.k5.soapui.streams.jaxb.rest

import eu.k5.soapui.streams.model.rest.SuuResource
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
data class RestResource(
    @XmlAttribute
    override var name: String? = null,

    @XmlElement
    override var path: String? = null
) : SuuResource {


    @XmlElement
    override var description: String? = null

    @XmlElement(name = "resource")
    override val resources = ArrayList<RestResource>()
    @XmlElement
    override val methods = ArrayList<RestMethod>()


    override val parameters: MutableList<RestParameter> = ArrayList()


    override fun createMethod(name: String): RestMethod {
        val method = RestMethod(name = name)
        methods.add(method)
        return method
    }

    override fun createChildResource(name: String, path: String): RestResource {
        val resource = RestResource(name = name, path = path)
        resources.add(resource)
        return resource
    }

}