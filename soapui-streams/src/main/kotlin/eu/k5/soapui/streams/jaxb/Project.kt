package eu.k5.soapui.streams.jaxb

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.jaxb.rest.RestService
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.test.SuuTestSuite
import eu.k5.soapui.streams.model.wsdl.SuuWsdlService
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
    override val wsdlServices: List<SuuWsdlService>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val properties: SuuProperties by lazy {
        TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    }

    @XmlElement
    override var description: String? = null


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
        return toXml(this)
    }


    override val testSuites: List<SuuTestSuite>
        get() = ArrayList()

    override fun createTestSuite(name: String): SuuTestSuite {

        TODO()
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