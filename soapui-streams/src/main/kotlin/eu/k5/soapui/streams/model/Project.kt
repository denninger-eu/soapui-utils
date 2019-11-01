package eu.k5.soapui.streams.model

import eu.k5.soapui.streams.model.rest.RestService
import eu.k5.soapui.streams.model.rest.SuuRestService
import java.io.StringWriter
import javax.xml.bind.JAXBContext
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


    override fun toString(): String {
        return "$name $restServices"
    }

    override fun createRestService(name: String): RestService {
        val restService = RestService(name)
        restServices.add(restService)
        return restService
    }

    fun toXml(): String {
        return Companion.toXml(this)
    }

    companion object {
        private val CONTEXT = JAXBContext.newInstance(Project::class.java)

        fun toXml(project: Project): String {
            val writer = StringWriter()
            CONTEXT.createMarshaller().marshal(project, writer)
            return writer.toString()
        }
    }
}