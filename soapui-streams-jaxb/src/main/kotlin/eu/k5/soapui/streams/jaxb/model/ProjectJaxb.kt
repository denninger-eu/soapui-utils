package eu.k5.soapui.streams.jaxb.model

import eu.k5.soapui.streams.jaxb.element.ProjectElement
import eu.k5.soapui.streams.jaxb.element.PropertiesElement
import eu.k5.soapui.streams.jaxb.element.RestServiceElement
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.SuuTestSuite
import eu.k5.soapui.streams.model.wsdl.SuuWsdlService
import java.lang.UnsupportedOperationException

class ProjectJaxb(
    private val element: ProjectElement
) : SuProject {

    override var name: String
        get() = element.name ?: ""
        set(value) = throw UnsupportedOperationException()

    override var description: String?
        get() = TODO("Not yet implemented")
        set(value) {}
    override val properties: SuuProperties
        get() = PropertiesJaxb(element.properties ?: PropertiesElement())

    override val restServices: List<SuuRestService>
        get() = element.interfaces?.filterIsInstance<RestServiceElement>()
            ?.map { RestServiceJaxb(it) }.orEmpty()

    override fun getRestService(name: String): RestServiceJaxb? = restServices.firstOrNull { it.name == name } as RestServiceJaxb?

    override fun createRestService(name: String): SuuRestService = throw UnsupportedOperationException()

    override val testSuites: List<SuuTestSuite>
        get() = element.testSuites?.map { TestSuiteJaxb(it, this) } ?: ArrayList()

    override fun createTestSuite(name: String): SuuTestSuite = throw UnsupportedOperationException()

    override val wsdlServices: List<SuuWsdlService>
        get() = emptyList()

    override fun createWsdlService(name: String): SuuWsdlService = throw UnsupportedOperationException()

}