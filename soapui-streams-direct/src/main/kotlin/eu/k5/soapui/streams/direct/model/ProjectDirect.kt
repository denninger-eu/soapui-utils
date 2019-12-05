package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.impl.wsdl.WsdlInterface
import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.impl.wsdl.WsdlTestSuite
import eu.k5.soapui.streams.direct.model.rest.RestServiceDirect
import eu.k5.soapui.streams.direct.model.test.TestSuiteDirect
import eu.k5.soapui.streams.direct.model.wsdl.WsdlServiceDirect
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.SuuTestSuite
import eu.k5.soapui.streams.model.wsdl.SuuWsdlService
import java.nio.file.Path

class ProjectDirect(
    private val wsdlProject: WsdlProject
) : SuProject {

    override var name: String
        get() = wsdlProject.name ?: ""
        set(value) {
            wsdlProject.name = value
        }

    override var description: String?
        get() = wsdlProject.description
        set(value) {
            wsdlProject.description = value
        }

    override val properties: SuuProperties
        get() = PropertiesDirect(wsdlProject) { wsdlProject.addProperty(it) }


    override val restServices: List<SuuRestService>
        get() = wsdlProject.interfaceList.filterIsInstance<RestService>().map {
            RestServiceDirect(
                it
            )
        }

    override val wsdlServices: List<SuuWsdlService>
        get() = wsdlProject.interfaceList.filterIsInstance<WsdlInterface>().map {
            WsdlServiceDirect(it)
        }

    override fun createRestService(name: String): SuuRestService {
        val newRestService = wsdlProject.addNewInterface(name, "rest") as RestService
        return RestServiceDirect(newRestService)
    }

    override val testSuites: List<TestSuiteDirect>
        get() = wsdlProject.testSuiteList.filterIsInstance<WsdlTestSuite>().map {
            TestSuiteDirect(
                it
            )
        }

    override fun createTestSuite(name: String): SuuTestSuite {
        val newTestSuite = wsdlProject.addNewTestSuite(name)
        return TestSuiteDirect(newTestSuite)
    }


    fun save(path: Path) {
        wsdlProject.saveIn(path.toFile())
    }
}