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


    override val parameters: MutableList<RestParameter> = ArrayList()

    @XmlElement(name = "resource")
    private val resources = ArrayList<Resource>()
    @XmlElement
    private val methods = ArrayList<RestMethod>()

    override fun addResource(resource: SuuResource) {
        resources.add(resource as Resource)
    }

    override fun addMethod(suuRestMethod: SuuRestMethod) {
        methods.add(suuRestMethod as RestMethod)
    }

}