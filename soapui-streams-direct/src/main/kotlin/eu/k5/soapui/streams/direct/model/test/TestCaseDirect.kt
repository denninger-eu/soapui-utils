package eu.k5.soapui.streams.direct.model.test

import com.eviware.soapui.impl.rest.RestMethod
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.rest.RestResource
import com.eviware.soapui.impl.rest.RestService
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
import com.eviware.soapui.impl.wsdl.teststeps.PropertyTransfersTestStep
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import com.eviware.soapui.impl.wsdl.teststeps.WsdlDelayTestStep
import com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep
import com.eviware.soapui.impl.wsdl.teststeps.registry.RestRequestStepFactory
import com.eviware.soapui.model.testsuite.TestStep
import eu.k5.soapui.streams.direct.model.PropertiesDirect
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.model.test.*

class TestCaseDirect(
    private val testCase: WsdlTestCase
) : SuuTestCase {



    override var name: String
        get() = testCase.name
        set(value) {
            testCase.name = value
        }
    override var enabled: Boolean
        get() = !testCase.isDisabled
        set(value) {
            testCase.isDisabled = !value
        }

    override val steps
        get() = testCase.testStepList.filter {
            supported(
                it
            )
        }.map { mapTestStep(it) }.toMutableList()


    override val properties: SuuProperties
        get() = PropertiesDirect(testCase) { testCase.addProperty(it) }

    override fun <T : SuuTestStep> createStep(name: String, type: Class<T>): T {
        if (type == SuuTestStepPropertyTransfers::class.java) {
            return TestStepPropertyTransfersDirect(
                testCase.addTestStep(
                    "transfer",
                    name
                ) as PropertyTransfersTestStep
            ) as T
        } else if (type == SuuTestStepDelay::class.java) {
            return TestStepDelayDirect(testCase.addTestStep("delay", name) as WsdlDelayTestStep) as T
        } else if (type == SuuTestStepRestRequest::class.java) {
            return TestStepRestRequestDirect(testCase.addTestStep("restrequest", name) as RestTestRequestStep) as T
        } else if (type == SuuTestStepProperties::class.java) {
            return TestStepPropertiesDirect(testCase.addTestStep("properties", name) as WsdlPropertiesTestStep) as T
        }

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createScriptStep(name: String): SuuTestStepScript {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun findRestServiceOrCreate(restService: SuuRestService): RestService {
        val existingServices = testCase.project.interfaceList.filterIsInstance(RestService::class.java)
        val existing = existingServices.firstOrNull { it.name == restService.name }
        if (existing != null) {
            return existing
        }
        TODO("create restservice")
    }

    override fun createRestRequestStep(
        name: String,
        restService: SuuRestService,
        restResources: List<SuuRestResource>,
        restMethod: SuuRestMethod
    ): SuuTestStepRestRequest {

        val existingRestService = findRestServiceOrCreate(restService)
        val existingRestResource = findRestResourceOrCreate(existingRestService, restResources)
        val existingMethod = findRestMethodOrCreate(existingRestResource, restMethod)


        val config = RestRequestStepFactory().createNewTestStep(existingMethod, name)
        val testStep = testCase.addTestStep(config)

        return TestStepRestRequestDirect(testStep as RestTestRequestStep)


    }

/*    private fun findRestRequestOrCreate(restMethod: RestMethod, restRequest: SuuRestRequest): RestRequest {
        val existingRequest = restMethod.requestList.firstOrNull { it.name == restRequest.name }
        if (existingRequest != null) {
            return existingRequest
        }
        TODO("create")

    }*/

    private fun findRestMethodOrCreate(restResource: RestResource, restMethod: SuuRestMethod): RestMethod {
        val existingRestMethod = restResource.restMethodList.firstOrNull { it.name == restMethod.name }
        if (existingRestMethod != null) {
            return existingRestMethod
        }
        TODO("create")
    }

    private fun findRestResourceOrCreate(restService: RestService, restResources: List<SuuRestResource>): RestResource {
        var existingResource = restService.resourceList.firstOrNull { it.name == restResources[0].name }
        if (restResources.size > 1) {
            for (restResource in restResources.subList(1, restResources.size)) {
                existingResource = existingResource?.childResourceList?.firstOrNull { it.name == restResource.name }
                if (existingResource == null) {
                    TODO("create")
                }
            }
        }
        if (existingResource != null) {
            return existingResource
        }
        TODO("implement create")
    }


    companion object {
        private val stepFactories = HashMap<Class<out Any>, (TestStep) -> SuuTestStep>()

        init {
            stepFactories[WsdlDelayTestStep::class.java] = {
                TestStepDelayDirect(it as WsdlDelayTestStep)
            }
            stepFactories[PropertyTransfersTestStep::class.java] = {
                TestStepPropertyTransfersDirect(it as PropertyTransfersTestStep)
            }

            stepFactories[RestTestRequestStep::class.java] = {
                TestStepRestRequestDirect(it as RestTestRequestStep)
            }
            stepFactories[WsdlPropertiesTestStep::class.java] = {
                TestStepPropertiesDirect(it as WsdlPropertiesTestStep)
            }
        }

        fun supported(testStep: TestStep): Boolean = stepFactories.containsKey(testStep.javaClass)


        fun mapTestStep(testStep: TestStep): SuuTestStep {
            val factory = stepFactories[testStep.javaClass]!!
            return factory(testStep)
        }
    }

}