package eu.k5.soapui.streams.model.rest

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
data class Resource(
    @XmlAttribute
    override var name: String? = null,

    @XmlElement
    override var path: String? = null
) : SuuResource {

    @XmlElement
    override var description: String? = null


    override fun getChildResource(name: String): SuuResource? = resources.find { it.name == name }


    override val parameters: MutableList<RestParameter> = ArrayList()

    @XmlElement(name = "resource")
    override val resources = ArrayList<Resource>()
    @XmlElement
    override val methods = ArrayList<RestMethod>()

    override fun addResource(resource: SuuResource) {
        resources.add(resource as Resource)
    }

    override fun addMethod(suuRestMethod: SuuRestMethod) {
        methods.add(suuRestMethod as RestMethod)
    }

    override fun getMethod(name: String): SuuRestMethod? {
        return methods.first { it.name == name }
    }

    override fun createMethod(name: String): SuuRestMethod {
        val method = RestMethod(name = name)
        methods.add(method)
        return method
    }
}