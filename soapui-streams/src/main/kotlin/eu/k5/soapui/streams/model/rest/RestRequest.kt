package eu.k5.soapui.streams.model.rest

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RestRequest(
    @XmlAttribute
    override var name: String? = null,
    @XmlElement
    override var description: String? = null
) : SuuRestRequest {

    override val parameters: MutableList<RestParameter> = ArrayList()

}