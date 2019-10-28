package eu.k5.soapui.streams.model.rest

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RestService(
    @XmlAttribute
    override var name: String? = null,
    @XmlElement
    override var description: String? = null

) : SuuRestService {

    @XmlElement(name = "resource")
    private val resources = ArrayList<Resource>()

    override fun addResource(resource: SuuResource) {
        resources.add(resource as Resource)
    }

    override fun toString(): String {
        return "$name $resources"
    }
}