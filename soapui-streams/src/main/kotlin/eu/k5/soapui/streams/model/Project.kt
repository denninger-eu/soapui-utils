package eu.k5.soapui.streams.model

import eu.k5.soapui.streams.model.rest.RestService
import eu.k5.soapui.streams.model.rest.SuuRestService
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
data class Project(
    override var name: String = ""
) : SuProject {


    @XmlElement
    override val restServices: MutableList<RestService> = ArrayList()

    override fun addRestService(restService: SuuRestService) {
        restServices.add(restService as RestService)
    }

    override fun toString(): String {
        return name + " " + restServices.toString()
    }

    override fun createRestService(name: String): SuuRestService {
        return RestService(name)
    }

}