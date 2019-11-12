package eu.k5.soapui.streams.model

import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.SuuTestSuite
import eu.k5.soapui.visitor.listener.SuListener

interface SuProject {
    var name: String

    var description: String?

    val restServices: List<SuuRestService>

    fun getRestService(name: String): SuuRestService? = restServices.firstOrNull { it.name == name }

    fun createRestService(name: String): SuuRestService

    val testSuites: List<SuuTestSuite>

    fun getTestSuite(name: String): SuuTestSuite? = testSuites.firstOrNull { it.name == name }

    fun createTestSuite(name: String): SuuTestSuite

}
