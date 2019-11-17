package eu.k5.soapui.streams.jaxb.rest

import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RestRequest(
    @XmlAttribute
    override var name: String = "",
    @XmlElement
    override var description: String? = null
) : SuuRestRequest {
    override val headers: List<SuuRestRequest.Header>
        get() = ArrayList()

    override fun removeHeader(key: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOrUpdateHeader(header: SuuRestRequest.Header) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var content: String? = null


    override var parameters: SuuRestParameters
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

}